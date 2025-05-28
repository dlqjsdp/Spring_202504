package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/sample")
public class SampleController {
	
	@GetMapping("/all") // 로그인 or 로그인 안한 사람도 접근 가능
	public void doAll() {
		log.info("do all can access everybody");
	}
	
	@GetMapping("/member") // 로그인한 사람만 호출 가능
	public void doMember() {
		log.info("logined member");
	}
	
	@GetMapping("/admin") // 관리자만 호출 가능
	public void doAdmin() {
		log.info("admin only");
	}
}
