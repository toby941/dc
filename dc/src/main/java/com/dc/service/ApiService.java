package com.dc.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.app.VelocityEngine;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.dc.web.controller.RequestXml;
import com.dc.web.controller.RequestXml.Param;
import com.dc.web.socket.SocketClient;

@Service
public class ApiService {

    // 转发ipad请求socket端口
    private Integer clientSocketport;
    // 响应文件
    private String readFile;
    // 写请求文件
    private String writeFile;
    // 监听文件更新socket端口
    private Integer resurceUpdateSocketPort;
    // 资源文件存放目录
    private String updateFilePath;
    // 资源文件名,多个文件以英文逗号分割
    private String updateFileNames;

    private final String TX_Login_Request = "DL   008\r\n1234567890 ${Username} ${Password}";
    private final String TX_Login_Res = "DL   008\r\n{status} ${info}";

    @Autowired
    private VelocityEngine velocityEngine;

    public Integer getClientSocketport() {
        return clientSocketport;
    }

    public String getReadFile() {
        return readFile;
    }

    public Integer getResurceUpdateSocketPort() {
        return resurceUpdateSocketPort;
    }

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public String getWriteFile() {
        return writeFile;
    }

    public String handleRequest(String psentity) throws JDOMException, IOException {
        InputStream in = IOUtils.toInputStream(psentity);
        RequestXml requestXml = new RequestXml(in);
        IOUtils.closeQuietly(in);
        String result = null;
        String response = null;
        Map<String, String> model = requestXml.getParamsAsMap();
        if (requestXml.isLogin()) {
            result = merge(TX_Login_Request, requestXml);
            writeToFile(result);
            boolean isLogin = SocketClient.notice();
            if (isLogin) {
                List<String> responseFile = readFile();
                if (CollectionUtils.isNotEmpty(responseFile) && responseFile.size() >= 2) {
                    String status = responseFile.get(2).substring(0, 1);
                    if (!"1".equals(status)) {
                        model.put("errInfo", "用户错误");
                    }
                    else {
                        model.put("errInfo", "");
                    }
                }
            }
        }
        response = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, requestXml.getAction() + ".vm", model);

        return response;
    }

    @PostConstruct
    public void init() {
        SocketClient.port = clientSocketport;
        try {
            updateFileSocket(resurceUpdateSocketPort);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String merge(final String txTemplete, RequestXml requestXml) {
        List<RequestXml.Param> params = requestXml.getParams();
        String result = txTemplete;
        for (Param param : params) {
            String name = param.getName();
            String value = param.getValue();
            result = result.replace("${" + name + "}", value);
        }
        return result;
    }

    public List<String> readFile() throws IOException {
        return FileUtils.readLines(new File(readFile), "UTF-8");
    }

    public void setClientSocketport(Integer clientSocketport) {
        this.clientSocketport = clientSocketport;
    }

    public void setReadFile(String readFile) {
        this.readFile = readFile;
    }

    public void setResurceUpdateSocketPort(Integer resurceUpdateSocketPort) {
        this.resurceUpdateSocketPort = resurceUpdateSocketPort;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public void setWriteFile(String writeFile) {
        this.writeFile = writeFile;
    }

    public void updateFile() throws IOException {
        String[] fileNames = updateFileNames.split(",");
        for (String fileName : fileNames) {
            File f = new File(updateFilePath + fileName);
            if (f.exists()) {
                String content = FileUtils.readFileToString(f);
                System.out.println(content);
            }
        }

    }

    public String getUpdateFilePath() {
        return updateFilePath;
    }

    public void setUpdateFilePath(String updateFilePath) {
        this.updateFilePath = updateFilePath;
    }

    public String getUpdateFileNames() {
        return updateFileNames;
    }

    public void setUpdateFileNames(String updateFileNames) {
        this.updateFileNames = updateFileNames;
    }

    public void updateFileSocket(Integer port) throws IOException {
        DatagramSocket socket = new DatagramSocket(port);
        byte[] buff = new byte[4096];
        DatagramPacket inPacket = new DatagramPacket(buff, 4096);
        while (true) {
            socket.receive(inPacket);
            String revice = new String(buff, 0, inPacket.getLength());
            if ("done".equals(revice)) {
                updateFile();
            }
        }
    }

    public void writeToFile(String content) throws IOException {
        File f = new File(writeFile);
        if (f.exists()) {
            FileUtils.forceDelete(f);
        }
        FileUtils.writeStringToFile(f, content, "UTF-8");
    }
}
