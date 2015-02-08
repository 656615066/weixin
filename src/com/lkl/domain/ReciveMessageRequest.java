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
	private Date createTime ;
	/**
	 * 接受消息类型
	 * @author lkl
	 *
	 */
	private MessageType msgType ;
	/**
	 * 接受消息类型
	 */
	private String content;
	/**
	 * 消息id，64位整型 
	 */
	private long msgId ; 
}
