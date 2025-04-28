package org.zerock.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.zerock.common.DBManager;
import org.zerock.dto.BoardVO;

@Repository
public class BoardRepository {

	@Autowired
	private DataSource dataSource;

	// 1. 전체 데이터 조회
	public List<BoardVO> selectAllBoards() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		String sql = "select * from board order by num desc";
		
		List<BoardVO> list = new ArrayList<BoardVO>();
		
		try {
			//1. DB연결
			conn = dataSource.getConnection();
			//2. sql전송
			pstmt = conn.prepareStatement(sql);
			//3. sql 맵핑
			//4. sql 실행
			rs =  pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVO bVo = new BoardVO();
				
				bVo.setNum(rs.getInt("num"));
				bVo.setName(rs.getString("name"));
				bVo.setPass(rs.getString("pass"));
				bVo.setEmail(rs.getString("email"));
				bVo.setTitle(rs.getString("title"));
				bVo.setContent(rs.getString("content"));
				bVo.setReadCount(rs.getInt("readcount"));
				bVo.setWriteDate(rs.getTimestamp("writedate"));
				
				list.add(bVo);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt, rs);
		}
		
		return list;
	} // end selectAllBoards
	
	// 2. 단건 데이터 조회
	public BoardVO selectOneByNum(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		String sql = "select * from board where num = ?";
		BoardVO bVo = new BoardVO();
		
		try {
			//1. DB연결
			conn = dataSource.getConnection();
			//2. sql전송
			pstmt = conn.prepareStatement(sql);
			//3. sql 맵핑
			pstmt.setInt(1, num);
			//4. sql 실행
			rs =  pstmt.executeQuery();
			
			if(rs.next()) {
				bVo.setNum(rs.getInt("num"));
				bVo.setName(rs.getString("name"));
				bVo.setPass(rs.getString("pass"));
				bVo.setEmail(rs.getString("email"));
				bVo.setTitle(rs.getString("title"));
				bVo.setContent(rs.getString("content"));
				bVo.setReadCount(rs.getInt("readcount"));
				bVo.setWriteDate(rs.getTimestamp("writedate"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt, rs);
		}
		
		return bVo;
	} // end selectOneByNum
	
	
	// 3. 데이터 추가
	/*
	 * NUM       NOT NULL NUMBER(5)      
		PASS               VARCHAR2(30)   
		NAME               VARCHAR2(30)   
		EMAIL              VARCHAR2(30)   
		TITLE              VARCHAR2(50)   
		CONTENT            VARCHAR2(1000) 
		READCOUNT          NUMBER(4)      
		WRITEDATE          DATE  
	 */
	public void insertBoard(BoardVO vo) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
	
		String sql = "insert into board(num, name, pass, email, title, content) "
				+ "values(board_seq.nextval, ?, ?, ?, ?, ?)";		
		try {
			//1. DB연결
			conn = dataSource.getConnection();
			//2. sql전송
			pstmt = conn.prepareStatement(sql);
			//3. sql 맵핑
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPass());
			pstmt.setString(3, vo.getEmail());
			pstmt.setString(4, vo.getTitle());			
			pstmt.setString(5, vo.getContent());
			//4. sql 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}

	} //end insertBoard
	
	
	// 4. 데이터 변경
	public void updateBoard(BoardVO vo) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
	
		String sql = "update board set name=?, pass=?, email=?, title=?, content=? where num=?";		
		try {
			//1. DB연결
			conn = dataSource.getConnection();
			//2. sql전송
			pstmt = conn.prepareStatement(sql);
			//3. sql 맵핑
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPass());
			pstmt.setString(3, vo.getEmail());
			pstmt.setString(4, vo.getTitle());			
			pstmt.setString(5, vo.getContent());
			pstmt.setInt(6, vo.getNum());
			//4. sql 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}

	} //end updateBoard
	
	// 5. 데이터 삭제
	public void deleteBoard(int num) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
	
		String sql = "delete from board where num=?";		
		try {
			//1. DB연결
			conn = dataSource.getConnection();
			//2. sql전송
			pstmt = conn.prepareStatement(sql);
			//3. sql 맵핑
			pstmt.setInt(1, num);
			//4. sql 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}

	} //end deleteBoard
	
	// 6. 조회수 증가
	public void updateReadCount(int num) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
	
		String sql = "update board set readcount = readcount+1 where num=?";		
		try {
			//1. DB연결
			conn = dataSource.getConnection();
			//2. sql전송
			pstmt = conn.prepareStatement(sql);
			//3. sql 맵핑
			pstmt.setInt(1, num);
			//4. sql 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}

	} //end updateReadCount
}