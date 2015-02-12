package com.lkl.controller.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lkl.controller.BaseController;

@Controller
@RequestMapping("/web/worker")
public class WorkerController extends BaseController{
	
	@RequestMapping(value = "/addView")
	public String toadd(){
		return "web/worker/addView";
	}
	
	@RequestMapping(value = "/add")
	@ResponseBody
	public String add(
			Map<String,Object> context,
			@RequestParam(required=true) String kf_account,
			@RequestParam(required=true) String nickname ,
			@RequestParam(required=true) String password){
		
		return "web/worker/addView";
	}
	
}
