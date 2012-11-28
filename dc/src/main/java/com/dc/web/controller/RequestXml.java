package com.dc.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.dc.constants.Constants;

public class RequestXml {

    public static void main(String[] args) throws JDOMException, IOException {
        String xml =
                "<Request action=\"Login\"><Param name=\"Username\"></Param><Param name=\"Password\"></Param><Param name=\"MacAddr\"></Param></Request>";
        InputStream in = IOUtils.toInputStream(xml);
        RequestXml requestXml = new RequestXml(in);
        System.out.println(ReflectionToStringBuilder.toString(requestXml));
    }

    public boolean isLogin() {
        return Constants.REQUEST_LOGIN.equals(action);
    }

    public boolean isOpenTable() {
        return Constants.REQUEST_OpenTable.equals(action);
    }

    public boolean isCloseTable() {
        return Constants.REQUEST_CloseTable.equals(action);
    }

    public boolean isGetMenuList() {
        return Constants.REQUEST_GetMenuList.equals(action);
    }

    /**
     * 是否 获取餐桌信息 请求
     * 
     * @return
     */
    public boolean isGetTables() {
        return Constants.REQUEST_GetTables.equals(action);
    }

    public boolean isNormalAction() {
        return Constants.REQUEST_Logout.equals(action) || Constants.REQUEST_OpenTable.equals(action)
                || Constants.REQUEST_CloseTable.equals(action) || Constants.REQUEST_OrderMenu.equals(action)
                || Constants.REQUEST_UrgeCate.equals(action) || Constants.REQUEST_DelayCate.equals(action)
                || Constants.REQUEST_Checkout.equals(action);
    }

    /**
     * 过滤响应内容一样的action
     * 
     * @return
     */
    public String getIpadResponseAction() {
        if (isNormalAction()) {
            return "OK";
        }
        else {
            return action;
        }
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }

    public RequestXml() {
        super();
    }

    /**
     * <Request action="Login"> <Param name="Username"></Param> <Param name="Password"></Param> <Param
     * name="MacAddr"></Param> </Request>
     * 
     * @param input
     * @throws JDOMException
     * @throws IOException
     */
    public RequestXml(InputStream input) throws JDOMException, IOException {
        super();
        Document doc = new SAXBuilder().build(input);
        Element root = doc.getRootElement();// 得到根节点
        action = root.getAttributeValue("action");
        sid = root.getAttributeValue("sid");
        List<Element> list = root.getChildren("Param");
        params = new ArrayList<RequestXml.Param>();
        for (Element e : list) {
            String name = e.getAttributeValue("name");
            String value = e.getValue();
            Param p = new Param(name, value);
            params.add(p);

        }
    }

    private String action;
    private String sid;
    private List<Param> params;

    /**
     * 将params以key-value形式转化为map
     * 
     * @return
     */
    public Map<String, String> getParamsAsMap() {
        Map<String, String> map = new HashMap<String, String>();
        if (params != null) {
            for (Param param : params) {
                String name = param.getName();
                String value = param.getValue();
                map.put(name, value);
            }
        }
        return map;
    }

    /**
     * 获取paramName 对应的 param value值
     * 
     * @param paramName
     * @return
     */
    public String getParamValue(String paramName) {
        if (params != null) {
            for (Param param : params) {
                String name = param.getName();
                if (name.equals(paramName)) {
                    return param.getValue();
                }
            }
        }
        return null;
    }

    public class Param {
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Param() {
            super();
        }

        @Override
        public String toString() {
            return "Param [name=" + name + ", value=" + value + "]";
        }

        public Param(String name, String value) {
            super();
            this.name = name;
            this.value = value;
        }
    }

}
