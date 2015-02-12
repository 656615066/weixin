package com.lkl.controller.web;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lkl.controller.WechatController;


@Controller
@RequestMapping("/web")
public class UploadController {
	private Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	@RequestMapping(value = "/toload")
	public String toLoad(){
		return "web/upload";
	}
}
