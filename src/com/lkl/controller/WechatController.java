package com.lkl.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
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

import com.lkl.util.WeChatContant;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

@Controller
@RequestMapping("/weixin")
public class WechatController {
	private Logger logger = LoggerFactory.getLogger(WechatController.class);
		
	@RequestMapping(value = "/",method=RequestMethod.GET)
	public void indexGet(Map<String,Object> context,
			@RequestParam(required=false) String signature,
			@RequestParam(required=false) String timestamp ,
			@RequestParam(required=false) String nonce ,
			@RequestParam(required=false) String echostr,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
			
			PrintWriter writer = response.getWriter();
			writer.write(echostr);
			writer.flush();
			writer.close();
				
	}
	
	@RequestMapping(value = "/",method=RequestMethod.POST)
	public void indexPost(Map<String,Object> context,
			@RequestParam(required=false) String signature,
			@RequestParam(required=false) String timestamp ,
			@RequestParam(required=false) String nonce ,
			@RequestParam(required=false) String echostr,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
			Object[] obj = parseXML(request);
			//logger.info(result);
			
			PrintWriter writer = response.getWriter();
			writer.write(obj[0].toString());
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
	
	private Object[] parseXML(HttpServletRequest request) throws IOException, ParserConfigurationException, SAXException{
		Object[] result = new Object[3];
		
		String xmltext = getReviceString(request) ;
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		StringReader sr = new StringReader(xmltext);
		InputSource is = new InputSource(sr);
		Document document = db.parse(is);

		Element root = document.getDocumentElement();
		NodeList nodelist1 = root.getElementsByTagName("ToUserName");
		NodeList nodelist2 = root.getElementsByTagName("FromUserName");
		NodeList nodelist3 = root.getElementsByTagName("CreateTime");
		NodeList nodelist4 = root.getElementsByTagName("MsgType");
		NodeList nodelist5 = root.getElementsByTagName("Content");
		NodeList nodelist6 = root.getElementsByTagName("MsgId");
		
		logger.info("ToUserName"+nodelist1.item(0).getTextContent());
		logger.info("FromUserName="+nodelist2.item(0).getTextContent());
		logger.info("CreateTime="+nodelist3.item(0).getTextContent());
		logger.info("MsgType="+nodelist4.item(0).getTextContent());
		logger.info("Content="+nodelist5.item(0).getTextContent());
		logger.info("MsgId="+nodelist6.item(0).getTextContent());
		
		result[0] = nodelist5.item(0).getTextContent();
		result[2] = nodelist2.item(0).getTextContent();
		return result;
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