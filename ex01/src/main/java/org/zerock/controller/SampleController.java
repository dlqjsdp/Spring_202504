package org.zerock.controller;

import java.text.SimpleDateFormat;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.SampleDTO;
import org.zerock.domain.TodoDTO;

import lombok.extern.log4j.Log4j;

@Controller

//localhost:8080/sample/*
@RequestMapping("/sample") // 일동의 폴더명
@Log4j
public class SampleController {
	
	/*
	 * 반환타입 : void
	 *  / : /view/sample.jsp
	 *  /basic : /view/sample/basic.jsp
	 *  
	 *  리턴이 void인 경우 : view화면은 경로명으로 찾는다 -> /view/sample.jsp
	 */
	
	//localhost:8080/sample/
	// /view/sample.jsp
	@RequestMapping("/")
	public void basic() {
		log.info("basic.............");
	}
	
	//localhost:8080/sample/basic
	// /view/sample/basic.jsp
	@RequestMapping(value="/basic", method= {
			RequestMethod.GET, RequestMethod.POST}) //get과 post 두가지 방식이라 반드시 배열{}로 묶어줘야함
	public void basicGet() {
		log.info("basic get................");
	}
	
	//localhost:8080/sample/basicOnlyGet
	// /view/sample/basicOnlyGet.jsp
	@GetMapping("/basicOnlyGet")
	public void basicGet2() {
		log.info("basic get only get.............");
	} //view/sample/basic.jsp 파일을 찾아간다
	
//	@GetMapping("/basicOnlyGet")
//	public String basicGet2() {
//		log.info("basic get only get.............");
//		return "aaa"; //view/aaa.jsp
//	} //String으로 하면 반환타입의 이름이 주소이름이 된다.
	
	
	//localhost:8080/sample/ex01?name=kim&age=20
	// /view/ex01.jsp
	@GetMapping("/ex01")
	public String ex01(SampleDTO dto) {
		log.info(dto);
		return "ex01";
	}
	
	// 기본자료형
	//localhost:8080/sample/ex02?name=kim&age=20
	// view/ex02.jsp + name, age
	@GetMapping("/ex02")
	public String ec01(@RequestParam("name") String name, 
			@RequestParam("age") int age, Model model) {
		log.info(name);
		log.info(age);
		model.addAttribute("name", name); //ex02 파일에 전달하는 정보
		model.addAttribute("age", age);
		return "ex02";
	}
	
//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		binder.registerCustomEditor(java.util.Date.class, 
//				new CustomDateEditor(dateFormat, false));
//	}
	
	
	//localhost:8080/sample/ex03?title=spring&dueDate=2025-5-1
	// view/ex03.jsp
	@GetMapping("/ex03")
	public String ex03(TodoDTO todoDTO) {
		log.info(todoDTO);
		return "ex03";
	}
	
	
	//localhost:8080/sample/ex04?name=park&age=22&page=5
	// view/sample/ex04.jsp +page, sampleDTO
	@GetMapping("/ex04")
	public String ex04(SampleDTO dto, @ModelAttribute("page") int page) {
		log.info(dto);
		log.info(page);
		return "sample/ex04";
	}
	
	//localhost:8080/sample/ex04_1?name=park&age=22&page=5
	// view/sample/ex04_1.jsp +page, sampleDTO
	@GetMapping("/ex04_1")
	public String ex04_1(SampleDTO dto, @RequestParam int page,
			Model model) {
		log.info(dto);
		log.info(page);
		model.addAttribute("aaa", dto);
		model.addAttribute("page", page);
		
		return "sample/ex04_1";
	}

	
	//localhost:8080/sample/ex04_1?name=park&age=22
	// view/sample/ex04_2.jsp +sampleDTO
	@GetMapping("/ex04_2")
	public String ex04_2(@ModelAttribute("sampleDTO") SampleDTO dto) {
		log.info(dto);
		return "sample/ex04_2";
	}
	
	
	//localhost:8080/sample/rttr?name=park&age=22
	//localhost:8080/sample/ex04
	@GetMapping("/rttr")
	public String rttr(SampleDTO dto , RedirectAttributes rttr) {
		rttr.addFlashAttribute("sampleDTO", dto);
		return "redirect:/sample/ex04";
	}
	
	//localhost:8080/sample/ex06
	// 자바 객체를 json변환해서 client에게 json 값만 전달
	@GetMapping("/ex06")
	// @ResponseBody => java객체를 json으로 변환
	public @ResponseBody SampleDTO ex06() {
		log.info("/ex06..........");
	 	
		/*
		SampleDTO dto = new SampleDTO();
		dto.setAge(10);
		dto.setName("홍길동");		
		return dto;
		*/
		
		return  SampleDTO.builder()
				.name("park")
				.age(29)
				.build();			
	}
	
	//localhost:8080/sample/ex06_1 +json데이타전달(postman등등....)
	@GetMapping("/ex06_1")
	//@RequestBody => json을 java 객체로 변환
	public String ex06_1(@RequestBody SampleDTO dto) {
		log.info("/ex06_1..........");
		log.info(dto);		
		return "/sample/ex06_1";
	}
	
	@GetMapping("/ex07")
	public ResponseEntity<String> ex07(){
		
		//{"name": "홍길동"}
		String msg = "{\"name\": \"홍길동\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json;charset=utf-8");
		return new ResponseEntity<String>(msg, headers, HttpStatus.ACCEPTED);
	}
	
	
}
	
