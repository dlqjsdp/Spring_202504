package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.zerock.domain.BoardVO;

public interface BoardMapper {
	
	public List<BoardVO> getList();
	
	public BoardVO read(Long bno);
	
	public void insert(BoardVO board);
	
	public void insertSelectKey(BoardVO board);

	public int delete(Long bno);
	
	public int update(BoardVO board);
}
