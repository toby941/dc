package com.dc.web.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class ApiControllerTest {

    // private static String host = "http://192.168.1.13:9091";

    // private static String host = "http://zhaduir.vicp.cc:8092";
    // private static String host = "http://517ps.eicp.net:8092";

    private static String host = "http://127.0.0.1:9091";

    public String request(String xml) throws ParseException, IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(host + "/api/ipad");
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("psentity", xml));
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        HttpResponse response = httpclient.execute(httpPost);
        String result = EntityUtils.toString(response.getEntity());
        System.err.println(result);
        return result;
    }

    // @Test
    public void request() throws ParseException, IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(host + "/api/txrx");
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        HttpResponse response = httpclient.execute(httpPost);
        System.err.println(EntityUtils.toString(response.getEntity()));
    }

    // @Test
    public void testAll() throws ClientProtocolException, IOException {
        String login =
                "<Request action=\"Login\"><Param name=\"Username\">002</Param><Param name=\"Password\">123123</Param><Param name=\"MacAddr\"></Param></Request>";
        String openTable = "<Request action=\"OpenTable\" sid=\"{0}\"><Param name=\"TableId\">007</Param></Request>";
        String orderMenu =
                "<Request action=\"OrderMenu\" sid=\"{0}\"><Menu id=\"03008\" pqty=\"1\" /><Menu id=\"03002\" pqty=\"1\" /><Menu id=\"05001\" pqty=\"1\" /><Menu id=\"05002\" pqty=\"1\" /><Menu id=\"02003\" pqty=\"1\" /></Request>";
        String orderList = "<Request action=\"GetOrderList\" sid=\"{0}\"/>";
        String loginResult = request(login);
        String sid = loginResult.substring(loginResult.indexOf("<SessionId>") + 11, loginResult.indexOf("</SessionId>"));
        System.out.println("sid: " + sid);
        request(MessageFormat.format(openTable, sid));
        // request(MessageFormat.format(orderMenu, sid));
        // request(MessageFormat.format(orderList, sid));
    }

    // @Test
    public void testLogin() throws ClientProtocolException, IOException {
        String xml =
                "<Request action=\"Login\"><Param name=\"Username\">002</Param><Param name=\"Password\">123123</Param><Param name=\"MacAddr\"></Param></Request>";
        request(xml);
    }

    // @Test
    public void testOpenTable() throws ParseException, IOException {
        String xml = "<Request action=\"OpenTable\" sid=\"1357228873132371000\"><Param name=\"TableId\">003</Param></Request>";
        request(xml);
    }

    // @Test
    public void testGetTables() throws ParseException, IOException {
        String xml = "<Request action=\"GetTables\" sid=\"4805629588930\"/>";
        request(xml);
    }

    // @Test
    public void testGetMenuList() throws ParseException, IOException {
        String xml = "<Request action=\"GetMenuList\" sid=\"1357228250385100000\"/>";
        request(xml);
    }

    @Test
    public void testGetMenuPackageList() throws ParseException, IOException {
        String xml = "<Request action=\"GetMenuPackageList\" sid=\"1357228873132371000\"/>";
        request(xml);
    }

    // @Test
    public void testGetSyncFileList() throws ParseException, IOException {
        String xml = "<Request action=\"GetSyncFileList\" sid=\"\"/>";
        request(xml);
    }

    // @Test
    public void orderMenu() throws ParseException, IOException {
        String xml =
                "<Request action=\"OrderMenu\" sid=\"1356100768933652000\"><Menu id=\"03008\" pqty=\"1\" /><Menu id=\"03002\" pqty=\"1\" /><Menu id=\"05001\" pqty=\"1\" /><Menu id=\"05002\" pqty=\"1\" /><Menu id=\"02003\" pqty=\"1\" /></Request>";
        request(xml);
    }

    // @Test
    public void orderAllTables() throws ParseException, IOException {
        String xml = "<Request action=\"GetAllTables\" sid=\"102357358329241\"></Request>";
        request(xml);
    }

    // @Test
    public void SwitchTable() throws ParseException, IOException {
        String xml = "<Request action=\"SwitchTable\" sid=\"1356100768933652000\"><Param name=\"TableId\">001</Param></Request>";
        request(xml);
    }

    // @Test
    public void getOrderList() throws ParseException, IOException {
        String xml = "<Request action=\"GetOrderList\" sid=\"1356100768933652000\"/>";
        request(xml);
    }
}
