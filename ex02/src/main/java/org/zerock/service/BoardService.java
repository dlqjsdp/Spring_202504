package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardService {
	
	public void register(BoardVO board);
	
	public BoardVO get(Long bno); // 1건 조회
	
	public boolean modify(BoardVO board); // 수정
	
	public boolean remove(Long bno); // 삭제
	
//	public List<BoardVO> getList(); // 전체 데이터 조회
	
	public List<BoardVO> getList(Criteria cri); // 전체 데이터 조회

}
