package com.pk.et.wm.web.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
@RequestMapping("/wm")
public class WealthManagerController {
	
	@RequestMapping("/equity")
	public ModelAndView showEquityForm(@RequestParam("symbol") String symbol){
		ModelAndView mv=new ModelAndView("equity");
		
		return mv;
	}

}
