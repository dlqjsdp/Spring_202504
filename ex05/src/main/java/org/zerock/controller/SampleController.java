package org.zerock.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.SampleVO;
import org.zerock.domain.Ticket;

import lombok.extern.log4j.Log4j;

@RestController // view 화면을 찾지 않고, response에 값만 전달
@RequestMapping("/sample")
@Log4j
public class SampleController {
	
//	@GetMapping(value = "getText", produces = "text/plain; charset=utf-8")
//	@GetMapping(value = "getText", produces = MediaType.TEXT_PLAIN_VALUE)
	@GetMapping(value = "getText", produces = MediaType.TEXT_HTML_VALUE)
	public String getText() {
		log.info("MIME TYPE : " + MediaType.TEXT_PLAIN_VALUE);
		
		return "안녕하세요";
	}
	
	@GetMapping(value = "/getSample", 
	produces = {MediaType.APPLICATION_JSON_VALUE, 
				MediaType.APPLICATION_XML_VALUE})
	public SampleVO getSample() {
		
		return new SampleVO(112, "스타", "로드");
		
	}
	
	@GetMapping(value = "/getList")
	public List<SampleVO> getList(){
		
		return IntStream.range(1, 10)
				.mapToObj(i -> new SampleVO(i, i+"First", i+"Last"))
				.collect(Collectors.toList());
		
		
		/*
		List<SampleVO> list = new ArrayList<SampleVO>();
		
		for(int i=1; i<9; i++) {
			SampleVO vo = new SampleVO(i, i+"FIRST", i+"LAST");
			list.add(vo);
		}
		return list;
		*/
	}
	
	@GetMapping(value = "/getMap",
			produces = {MediaType.APPLICATION_JSON_VALUE, 
					MediaType.APPLICATION_XML_VALUE}
			)
	public Map<String, SampleVO> getMap(){
		Map<String, SampleVO> map = new HashMap<String, SampleVO>();
		
		map.put("First", new SampleVO(111, "그루트", "주니어"));
		
		return map;
		
	}
	
	@GetMapping(value = "/check", 
			params = {"height", "weight"}, //매개변수 역할
			produces = {MediaType.APPLICATION_JSON_VALUE, 
					MediaType.APPLICATION_XML_VALUE}
			)
	public ResponseEntity<SampleVO> check(Double height, Double weight) {
		
		SampleVO vo = new SampleVO(0, ""+ height, ""+weight);
		
		ResponseEntity<SampleVO> result = null;
		
		if(height < 150) {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
		}else {
			result = ResponseEntity.status(HttpStatus.OK).body(vo);
		}
		return result;
	}
	
	@GetMapping(value = "product/{cat}/{pid}")
	public String[] getPath(
			@PathVariable("cat") String c1,
			@PathVariable("pid") String p1
			) {
		return new String[] {"category: " + c1, "productID: " + p1};
	}
	
	
	//@RequestBody Ticket ticket --> json 데이터를 입력 받는다! (요청측에서 json으로 서버에게 값을 전달)
	@PostMapping("/ticket") 
	public Ticket convnet(@RequestBody Ticket ticket) {
		log.info("ticket......."+ ticket);
		
		return ticket;
	}
	
}
