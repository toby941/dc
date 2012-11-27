package com.dc.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class ApiControllerTest {

	@Test
	public void test() throws ClientProtocolException, IOException {
		String xml = "<Request action=\"Login\"><Param name=\"Username\">123</Param><Param name=\"Password\">123123</Param><Param name=\"MacAddr\"></Param></Request>";

		HttpClient httpclient = new DefaultHttpClient();
		// HttpPost httpPost = new
		// HttpPost("http://192.168.1.13:9091/api/ipad");
		// http://zhaduir.vicp.cc:8092/
		HttpPost httpPost = new HttpPost("http://zhaduir.vicp.cc:8092/api/ipad");
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("psentity", xml));
		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
		HttpResponse response = httpclient.execute(httpPost);
		System.err.println(EntityUtils.toString(response.getEntity()));
	}

}
