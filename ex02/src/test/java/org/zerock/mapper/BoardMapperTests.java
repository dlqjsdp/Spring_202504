package org.zerock.mapper;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTests {

	@Autowired
	private BoardMapper mapper;
	
	@Test
	public void testRead() {
		log.info(mapper.read(1L)); //존재하는 게시물 번호로 테스트
	}
	
	@Test
	public void testGetList() {
		List<BoardVO> list = mapper.getList(); 
		//mapper.getList()는 BoardMapper.xml에 정의된 SQL을 실행
		// -> 그 결과로 게시글들을 담은 리스트가 반환되고, list에 저장됨
		
		for(BoardVO vo : list) { //list에 있는 각 게시글(BoardVO 객체)을 하나씩 꺼내서
			log.info(vo); //log.info(vo)로 출력 (→ 콘솔에 객체 정보가 로그로 출력됨)
		} //BoardVO 클래스에 toString()이 잘 구현되어 있어야 로그에 예쁘게 출력 (@Data로 lombok)
	}
	
	@Test
	public void testInsert() { // 데이터 insert하는 코드
		BoardVO vo = BoardVO.builder()
				.title("test title")
				.content("test content")
				.writer("test00")
				.build();
		
		mapper.insert(vo);
	}
	
	@Test
	public void testInsertKey() { // 데이터 insert와 함께 insert하는 데이터의 bno값도 확인할 수 있는 코드
		BoardVO vo = BoardVO.builder()
				.title("test title")
				.content("test content")
				.writer("test00")
				.build();
		//BoardVO 객체를 생성
		// bno는 아직 지정하지 않음 (→ DB에서 시퀀스로 채울 예정)
		// Lombok의 @Builder 덕분에 간결하게 객체 생성 가능
		
		mapper.insertSelectKey(vo); // 이 호출 시 아래 XML의 SQL이 실행
	}
	
	@Test
	public void testDelete() { 
		int result = mapper.delete(7L);
		// mapper.delete(7L)을 실행하여 bno가 7인 게시글을 삭제
		// BoardMapper 인터페이스의 delete() 메서드 호출
		log.info("result >>> " + result);
		// bno = 7인 게시글이 있다면 1행 삭제되고 result = 1
		// 없으면 삭제 실패 → result = 0
	}
	
	@Test
	public void testUpdate() { // bno = 6을 수정하는 테스트
		BoardVO vo = BoardVO.builder()
				.title("수정 제목")
				.content("수정 내용")
				.writer("update00")
				.bno(6L)		
				.build();
		// BoardVO 객체를 생성 (Lombok의 @Builder 사용)
		// bno = 6인 게시글을 찾아서, 제목·내용·작성자를 새 값으로 변경
		// 이 객체는 수정 쿼리의 #{title} 등으로 매핑
		
		int result = mapper.update(vo); // BoardMapper의 update() 메서드 호출
		log.info("result >>> " + result);
		// bno = 6이 존재하면 1행 수정 → result = 1,
		// 없으면 수정 실패 → result = 0
	}
	
	@Test
	public void testPaggin() {
		List<BoardVO> list = 
				mapper.getListWithPaging(new Criteria(3, 10));
		
		list.forEach(board -> log.info(board));
	}

}
