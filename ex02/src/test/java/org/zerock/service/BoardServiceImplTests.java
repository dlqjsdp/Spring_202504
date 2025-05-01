package org.zerock.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml", // service는 여기에 등록
	"file:src/main/webapp/WEB-INF/spring/root-context.xml" // DB연결은 여기에 했음
	})
@Log4j
public class BoardServiceImplTests {
	
	@Autowired
	private BoardService service;

	@Test
	public void testRegister() {
		BoardVO vo = BoardVO.builder()
				.title("서비스 제목")
				.content("서비스 내용")
				.writer("service00")
				.build();
		
		service.register(vo);
	}
	
	@Test
	public void testGetList() {
		service.getList().forEach(board -> log.info(board));
	}

}
