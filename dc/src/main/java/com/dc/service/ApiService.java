package com.dc.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

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

    @Autowired
    private VelocityEngine velocityEngine;

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    private String writeFile;

    private Integer clientSocketport;

    @PostConstruct
    public void init() {
        SocketClient.port = clientSocketport;
    }

    public String getWriteFile() {
        return writeFile;
    }

    public void setWriteFile(String writeFile) {
        this.writeFile = writeFile;
    }

    public Integer getClientSocketport() {
        return clientSocketport;
    }

    public void setClientSocketport(Integer clientSocketport) {
        this.clientSocketport = clientSocketport;
    }

    private final String TX_Login = "DL   008\r\n1234567890 ${Username} ${Password}";

    public String handleRequest(String psentity) throws JDOMException, IOException {
        InputStream in = IOUtils.toInputStream(psentity);
        RequestXml requestXml = new RequestXml(in);
        IOUtils.closeQuietly(in);
        String result = null;
        String response = null;
        if (requestXml.isLogin()) {
            result = merge(TX_Login, requestXml);
            Map<String, String> model = requestXml.getParamsAsMap();
            response =
                    VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, requestXml.getAction() + ".vm", model);
        }
        writeToFile(result);
        SocketClient.notice();
        return response;
    }

    public void writeToFile(String content) throws IOException {
        File f = new File(writeFile);
        if (f.exists()) {
            FileUtils.forceDelete(f);
        }
        FileUtils.writeStringToFile(f, content, "UTF-8");
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
}
