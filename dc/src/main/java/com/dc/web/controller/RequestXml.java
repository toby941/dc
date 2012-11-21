package com.dc.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class RequestXml {

	public static void main(String[] args) throws JDOMException, IOException {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF8\" ?><Request action=\"Login\"><Param name=\"Username\"></Param><Param name=\"Password\"></Param><Param name=\"MacAddr\"></Param></Request>";
		InputStream in = IOUtils.toInputStream(xml);
		RequestXml requestXml = new RequestXml(in);
		System.out.println(ReflectionToStringBuilder.toString(requestXml));
	}
	public RequestXml() {
		super();
	}

	/**
	 * <Request action="Login"> <Param name="Username"></Param> <Param
	 * name="Password"></Param> <Param name="MacAddr"></Param> </Request>
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
		List<Element> list = root.getChildren();
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

	public class Param {
		private String name;
		private String value;
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
