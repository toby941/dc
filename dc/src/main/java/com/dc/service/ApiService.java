package com.dc.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.dc.model.IpadRequestInfo;
import com.dc.web.controller.RequestXml;
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

    private Boolean devMode;

    private final String TX_Login_Request = "DL   008\r\n1234567890 ${Username} ${Password}";
    private final String TX_Login_Res = "DL   008\r\n{status} ${info}";

    @Autowired
    @Qualifier("ipadResponseVelocityEngine")
    private VelocityEngine ipadResponseVelocityEngine;

    @Autowired
    @Qualifier("txRequstVelocityEngine")
    private VelocityEngine txReRequestVelocityEngine;

    @Autowired
    private RxResponseResolve rxResponseResolve;

    public VelocityEngine getIpadResponseVelocityEngine() {
        return ipadResponseVelocityEngine;
    }

    public void setIpadResponseVelocityEngine(VelocityEngine ipadResponseVelocityEngine) {
        this.ipadResponseVelocityEngine = ipadResponseVelocityEngine;
    }

    public VelocityEngine getTxReRequestVelocityEngine() {
        return txReRequestVelocityEngine;
    }

    public void setTxReRequestVelocityEngine(VelocityEngine txReRequestVelocityEngine) {
        this.txReRequestVelocityEngine = txReRequestVelocityEngine;
    }

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
        return ipadResponseVelocityEngine;
    }

    public String getWriteFile() {
        return writeFile;
    }

    public String handleRequest(String psentity) throws JDOMException, IOException {
        InputStream in = IOUtils.toInputStream(psentity);
        RequestXml requestXml = new RequestXml(in);
        IOUtils.closeQuietly(in);
        String response = null;
        IpadRequestInfo requestInfo = getTxRequest(requestXml);
        String txRequestContent = merge4TxRequest(requestInfo, requestXml);
        writeToFile(txRequestContent);
        Map<String, Object> model = new HashMap<String, Object>();
        if (devMode || SocketClient.notice()) {
            model.put("ipad", requestInfo);
            List<String> responseFile = readFile();
            model = rxResponseResolve.resolve(responseFile, model, "操作失败", requestXml);
        }
        response =
                VelocityEngineUtils.mergeTemplateIntoString(ipadResponseVelocityEngine,
                        requestXml.getIpadResponseAction() + ".vm", model);
        return response;
    }

    public Boolean getDevMode() {
        return devMode;
    }

    public void setDevMode(Boolean devMode) {
        this.devMode = devMode;
    }

    @PostConstruct
    public void init() {
        SocketClient.port = clientSocketport;
        final Integer port4FileUpdate = resurceUpdateSocketPort;
        try {
            Thread t = new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        updateFileSocket(port4FileUpdate);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @param requestXml
     * @return
     */
    public IpadRequestInfo getTxRequest(RequestXml requestXml) {
        String sid = requestXml.getSid();
        IpadRequestInfo requestInfo = null;
        if (StringUtils.isNotBlank(sid)) {
            requestInfo = CacheService.getIpadInfo(sid);
        }
        else {
            requestInfo = new IpadRequestInfo(requestXml);
            CacheService.putIpadRequestInfo(requestInfo.getSid(), requestInfo);
        }
        return requestInfo;
    }

    /**
     * 拼装TX请求
     * 
     * @param requestInfo
     * @param requestXml
     * @return
     */
    public String merge4TxRequest(IpadRequestInfo requestInfo, RequestXml requestXml) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("ipad", requestInfo);
        String txRrequestContent =
                VelocityEngineUtils.mergeTemplateIntoString(txReRequestVelocityEngine, requestXml.getAction() + ".vm",
                        model);
        return txRrequestContent;
    }

    public List<String> readFile() throws IOException {
        File file = new File(readFile);
        if (file.exists()) {
            return FileUtils.readLines(file, "UTF-8");
        }
        return null;
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
        this.ipadResponseVelocityEngine = velocityEngine;
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
        if (port == null) {
            port = resurceUpdateSocketPort;
        }
        DatagramSocket socket = new DatagramSocket(port);
        // socket.setSoTimeout(5 * 1000);
        byte[] buff = new byte[4096];
        DatagramPacket inPacket = new DatagramPacket(buff, 4096);
        while (true) {
            socket.receive(inPacket);
            String revice = new String(buff, 0, inPacket.getLength());
            System.out.println("revice data：" + revice);
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
