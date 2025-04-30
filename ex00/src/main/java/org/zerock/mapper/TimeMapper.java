package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.zerock.dto.BoardVO;

public interface TimeMapper {

	@Select("select sysdate from dual")
	public String getTime();
	
	public String getTime2();
	
	public List<BoardVO> selectAllList(); //DB에서 전체데이터 가져오기
	
	public BoardVO selectOneByNum(int num); // 단건 데이터 조회하기
	
	public void insertBoard(BoardVO vo);
	
	
}
