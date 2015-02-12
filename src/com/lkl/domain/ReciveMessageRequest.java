package com.lkl.domain;

import java.util.Date;

public class ReciveMessageRequest {
	/**
	 * 开发者微信号 
	 */
	private String toUserName ;
	/**
	 * 发送方帐号（一个OpenID）
	 */
	private String fromUserName ;
	private String createTime ;
	/**
	 * 接受消息类型
	 * @author lkl
	 *
	 */
	private String msgType ;
	/**
	 * 接受消息类型
	 */
	private String content;
	/**
	 * 消息id，64位整型 
	 */
	private String msgId ;
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	} 
	
	
}
