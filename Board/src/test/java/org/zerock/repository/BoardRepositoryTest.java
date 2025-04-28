package org.zerock.repository;

import static org.junit.Assert.*;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.dto.BoardVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		{
			"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
			"file:src/main/webapp/WEB-INF/spring/root-context.xml"
		}
	)
@Log4j
public class BoardRepositoryTest {

	@Autowired
	private BoardRepository boardRepository;
	
	@Test
	public void test() {
		log.info("boardRepository >> " + boardRepository);
	}
	
	@Test
	public void selectAlltest() {
		List<BoardVO> list = boardRepository.selectAllBoards();
		for(BoardVO vo : list)
			log.info(vo);
		
		log.info("------------------------");
		
		boardRepository.selectAllBoards()
			.forEach(board -> log.info(board));
	}
	
	@Test
	public void seletOneByNumTest() {
		BoardVO vo = boardRepository.selectOneByNum(1);
		log.info("vo >> " + vo);
		
	}
	
	@Test
	public void insertBoardTest() {
		BoardVO vo = new BoardVO();
		
		vo.setName("까미");
		vo.setPass("1234");
		vo.setEmail("test@test.com");
		vo.setTitle("우리 강아지");
		vo.setContent("성질 드러분 강아지");
		
		boardRepository.insertBoard(vo);
	}
	
	@Test
	public void updateBoardTest() {
		BoardVO vo = new BoardVO();
		
		vo.setName("뽀양");
		vo.setPass("1234");
		vo.setEmail("aaaa@aaaa.com");
		vo.setTitle("우리 고양이");
		vo.setContent("성질 착한 고양이");
		vo.setNum(42);
		
		boardRepository.updateBoard(vo);
	}
	
	@Test
	public void deleteBoardTest() {
		
		boardRepository.deleteBoard(50);
	}
	
	@Test
	public void updateReadCountTest() {
		boardRepository.updateReadCount(5);
	}


}
