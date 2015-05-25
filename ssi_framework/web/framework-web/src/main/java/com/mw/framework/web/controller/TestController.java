package com.mw.framework.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
	
	@RequestMapping("/test/myInfo")
	public String myInfo(String name, ModelMap model){
		System.out.println(name);
		model.addAttribute("name", name);
		return "name";
	}

}
