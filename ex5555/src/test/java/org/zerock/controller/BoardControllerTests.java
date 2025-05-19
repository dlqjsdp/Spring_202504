package org.zerock.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
	"file:src/main/webapp/WEB-INF/spring/root-context.xml"	
	})
@Log4j
@WebAppConfiguration //웹 애플리케이션 컨텍스트를 생성하기 위한 설정, WebApplicationContext를 주입받기 위해 필수
public class BoardControllerTests {

	@Autowired // 웹 관련 빈(생성된 객체) 관리
	private WebApplicationContext ctx; //Spring의 웹 애플리케이션 컨텍스트를 주입
	
	// 서버를 실행하지 않고도 HTTP 요청과 응답을 시뮬레이션 하기 위한 도구
	private MockMvc mockMvc; //서버 없이 컨트롤러를 테스트할 수 있게 해주는 핵심 도구
	
	@Before //Spring MVC 애플리케이션에서 통합 테스트 수행, 실제 서버를 실행하지 않고 컨트롤러 동작을 테스트 가능
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	} //테스트 실행 전에 mockMvc를 초기화, 이걸 통해 실제 요청 없이 컨트롤러를 호출
	
	@Test // 컨트롤러의 @GetMapping("/list")가 제대로 작동하는지 검증
	public void testList() throws Exception {
		log.info(
				mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))
				.andReturn()
				.getModelAndView()
				.getModelMap()
				);
	} // /board/list 요청을 시뮬레이션하여 목록 페이지의 ModelMap 확인
	
	
	@Test
	public void testRegister() throws Exception{
		String resultPage = mockMvc.perform(MockMvcRequestBuilders
				.post("/board/register")
				.param("title", "테스트 새글 제목")
				.param("content", "테스트 새글 내용")
				.param("writer", "테스트 새글 작성자") // 파라미터를 전송하여 등록
				//@RequestParam, 혹은 @ModelAttribute, 혹은 BoardVO 필드에 자동으로 바인딩됨
				).andReturn() // 요청 실행 결과 반환 → 실행된 요청의 응답 결과를 받는다
				.getModelAndView() // View와 Model 정보를 담은 객체 추출 → 응답 결과 중 ModelAndView 객체를 꺼낸다
				.getViewName();  // View 이름만 추출 → 뷰 이름(return "redirect:/board/list" 같은 값)을 반환
		
		log.info("======>" + resultPage);
	} 
	
	
	@Test
	public void testGet() throws Exception {
		log.info(
				mockMvc.perform(MockMvcRequestBuilders.get("/board/get")
				.param("bno", "3"))
				.andReturn()
				.getModelAndView()
				.getModelMap()
				);
	}
	
	
	@Test
	public void testDelete() throws Exception{
		String resultPage = mockMvc.perform(MockMvcRequestBuilders
				.post("/board/remove")
				.param("bno", "11")
				).andReturn()
				.getModelAndView()
				.getViewName();
		
		log.info("======>" + resultPage);
	}
	
	
	@Test
	public void testModify() throws Exception{
		String resultPage = mockMvc.perform(MockMvcRequestBuilders
				.post("/board/modify")
				.param("title", "수정 새글 제목")
				.param("content", "수정 새글 내용")
				.param("writer", "수정 새글 작성자")
				.param("bno", "9")
				).andReturn()
				.getModelAndView()
				.getViewName();
		
		log.info("======>" + resultPage);
	}

}
