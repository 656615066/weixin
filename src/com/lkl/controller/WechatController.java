package com.lkl.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.lkl.domain.ReciveMessageRequest;
import com.lkl.util.Decript;
import com.lkl.util.WeChatContant;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

@Controller
@RequestMapping("/weixin")
public class WechatController {
	private Logger logger = LoggerFactory.getLogger(WechatController.class);
		
	@RequestMapping("/")
	public void index(Map<String,Object> context,
			@RequestParam(required=false) String signature,
			@RequestParam(required=false) String timestamp ,
			@RequestParam(required=false) String nonce ,
			@RequestParam(required=false) String echostr,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
			String result = parseRequest(request) ;
			PrintWriter writer = response.getWriter();
			writer.write(result);
			writer.flush();
			writer.close();
	}
	
	private String getReviceString(HttpServletRequest request) throws IOException{
		 StringBuffer sb = new StringBuffer();  
		 InputStream is = request.getInputStream();  
		 InputStreamReader isr = new InputStreamReader(is, "UTF-8");  
		 BufferedReader br = new BufferedReader(isr);  
		 String s = "";  
		 while ((s = br.readLine()) != null) {  
		     sb.append(s);  
		 }  
		String xml = sb.toString(); //次即为接收到微信端发送过来的xml数据}
		return xml ;
	}
	private String parseRequest(HttpServletRequest request) throws AesException, IOException{
		
		// 需要加密的明文
		String encodingAesKey = WeChatContant.ENCODINGAESKEY;
		String token = WeChatContant.TOKEN;
		
		String nonce = request.getParameter("nonce");
		String timestamp = request.getParameter("timestamp");
		String signature = request.getParameter("signature");
		String appId = WeChatContant.APPID;
		
		String postData = getReviceString(request) ;
		WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
		String userContent = pc.decryptMsg(signature, timestamp, nonce, postData);
		
System.out.println(userContent);
		String result = pc.encryptMsg("your content is "+userContent +
				" ;timestamp="+timestamp+" signature="+signature+ " nonce="+nonce,
				timestamp, nonce);
		return result ;
	}
}