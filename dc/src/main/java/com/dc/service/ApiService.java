package com.dc.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.dc.model.IpadRequestInfo;
import com.dc.utils.PathUtils;
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

    public String getMenuFilePath() {
        return courseFilePath;
    }

    public void setMenuFilePath(String menuFilePath) {
        this.courseFilePath = menuFilePath;
    }

    public RxResponseResolve getRxResponseResolve() {
        return rxResponseResolve;
    }

    public void setRxResponseResolve(RxResponseResolve rxResponseResolve) {
        this.rxResponseResolve = rxResponseResolve;
    }

    private String courseFilePath;
    private String courseTabFilePath;
    private String courseTablePath;
    private String coursePackageFilePath;
    private String coursePackageContentFilePath;

    public String getCourseTablePath() {
        return courseTablePath;
    }

    public void setCourseTablePath(String courseTablePath) {
        this.courseTablePath = courseTablePath;
    }

    public String getCourseFilePath() {
        return courseFilePath;
    }

    public void setCourseFilePath(String courseFilePath) {
        this.courseFilePath = courseFilePath;
    }

    public String getCourseTabFilePath() {
        return courseTabFilePath;
    }

    public void setCourseTabFilePath(String courseTabFilePath) {
        this.courseTabFilePath = courseTabFilePath;
    }

    // 测试模式 开启 便于在没有socket模式下进行文件读写测试
    private Boolean devMode;

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

    private final Logger log = Logger.getLogger(ApiService.class);

    /**
     * 请求处理入口 根据不同的actioin分发
     * 
     * @param psentity
     * @return
     * @throws JDOMException
     * @throws IOException
     * @throws SQLException
     */
    public synchronized String handleRequest(String psentity) throws JDOMException, IOException {
        InputStream in = IOUtils.toInputStream(psentity);
        RequestXml requestXml = new RequestXml(in);
        IOUtils.closeQuietly(in);
        String response = null;
        Long nanoTime = System.nanoTime();
        log.warn("########handle begin id :" + nanoTime);
        Map<String, Object> model = new HashMap<String, Object>();
        IpadRequestInfo requestInfo = getTxRequest(requestXml);
        model.put("ipad", requestInfo);
        if (requestXml.isNeedWriteTx()) {
            String txRequestContent = merge4TxRequest(requestInfo, requestXml);
            System.out.println("write content: " + txRequestContent);
            log.warn("write content: " + txRequestContent);
            writeToFile(txRequestContent);
            if (devMode || SocketClient.noticeTCP()) {
                List<String> responseFile = readFile(readFile);
                log.warn("read content: " + ReflectionToStringBuilder.toString(responseFile));
                model = rxResponseResolve.resolve(responseFile, model, "操作失败", requestXml);
            }
        } else {
            model = rxResponseResolve.resolve(null, model, "操作失败", requestXml);
        }
        if (requestXml.isNeedClearSession() && (model.get("errInfo") == null)) {
            CacheService.clearSid(requestInfo.getSid());
        }

        response = VelocityEngineUtils.mergeTemplateIntoString(ipadResponseVelocityEngine, requestXml.getIpadResponseAction() + ".vm", model);
        log.warn("return ipad: " + response);
        log.warn("########handle end  id :" + nanoTime);
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
        PathUtils.courseFilePath = this.courseFilePath;
        PathUtils.courseTabPath = this.courseTabFilePath;
        PathUtils.courseTablePath = this.courseTablePath;
        PathUtils.coursePackageFilepath = this.coursePackageFilePath;
        PathUtils.coursePackageContentFilePath = this.coursePackageContentFilePath;
        final Integer port4FileUpdate = resurceUpdateSocketPort;

        try {
            rxResponseResolve.resolveGetSyncFileList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Thread t = new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        updateFileSocket(port4FileUpdate);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getCoursePackageFilePath() {
        return coursePackageFilePath;
    }

    public void setCoursePackageFilePath(String coursePackageFilePath) {
        this.coursePackageFilePath = coursePackageFilePath;
    }

    public String getCoursePackageContentFilePath() {
        return coursePackageContentFilePath;
    }

    public void setCoursePackageContentFilePath(String coursePackageContentFilePath) {
        this.coursePackageContentFilePath = coursePackageContentFilePath;
    }

    /**
     * 将requestXml转化为IpadRequestInfo 输出
     * 
     * @param requestXml
     * @return
     */
    public IpadRequestInfo getTxRequest(RequestXml requestXml) {
        String sid = StringUtils.trimToEmpty(requestXml.getSid());
        IpadRequestInfo requestInfo = null;
        if (StringUtils.isNotBlank(sid)) {
            requestInfo = CacheService.getIpadInfo(sid);

            // if
            // (!Constants.REQUEST_SwitchTable.equals(requestXml.getAction())) {
            log.error("old tableId:" + requestInfo.getTableId() + "  new table id" + requestXml.getParamValue("TableId"));
            System.err.println("old tableId:" + requestInfo.getTableId() + "  new table id" + requestXml.getParamValue("TableId"));
            requestInfo.addParams(requestXml);
            log.error("new  tableId:" + requestInfo.getTableId());
            System.err.println("new  tableId:" + requestInfo.getTableId());
            if (requestInfo.getTableId() != null && requestInfo.getTableId().length() > 0) {
                CacheService.saveSid(requestInfo.getTableId(), sid);
            }
            // }
        } else {
            requestInfo = new IpadRequestInfo(requestXml);
        }
        CacheService.putIpadRequestInfo(requestInfo.getSid(), requestInfo);
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
        requestXml.setTableId(requestInfo.getTableId());
        model.put("xml", requestXml);
        String txRrequestContent = VelocityEngineUtils.mergeTemplateIntoString(txReRequestVelocityEngine, requestXml.getAction() + ".vm", model);
        return txRrequestContent;
    }

    public List<String> readFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            return FileUtils.readLines(file, "GBK");
        }
        return null;
    }

    public String getCurrentTxAndRx() throws IOException {
        List<String> rx = readFile(readFile);
        List<String> tx = readFile(writeFile);
        String rxInOneLine = StringUtils.EMPTY;
        String txInOneLine = StringUtils.EMPTY;
        if (CollectionUtils.isNotEmpty(rx)) {
            rxInOneLine = StringUtils.join(rx, "\r\n");
        }
        if (CollectionUtils.isNotEmpty(tx)) {
            txInOneLine = StringUtils.join(tx, "\r\n");
        }
        return MessageFormat.format("rx file :{0}\r\n tx file: {1}", rxInOneLine, txInOneLine);
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
