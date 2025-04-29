package org.zerock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.dto.BoardVO;
import org.zerock.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

/*
/board/boardList -> 전체데이터 반환
/board/view -> 상세 페이지 
 */

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Log4j
public class BoardController {
	
	private final BoardService boardService;
	
	//localhost:8080/board/boardList -> 전체 리스트 목록
	//localhost:8080/board/ -> 전체 리스트 목록
	@GetMapping({"/", "/boardList"})
	public String boardList(Model model) {
		
		List<BoardVO> list = boardService.selectListBoard();
		
		model.addAttribute("boardList", list);
		
		return "boardList";
	}
	
	//localhost:8080/board/register -> 멤버 등록 화면 출력
	@GetMapping("/register")
	public String register() {
		return "boardRegister";	
	}
	
	//localhost:8080/board/register(post) -> 등록화면에서 입력한 데이터를 기반으로 DB등록
	@PostMapping("/register")
	public String insertBoard(BoardVO vo) {
		boardService.insertBoard(vo);
		return "redirect:/board/boardList";	
	}
	
	//localhost:8080/board/view -> num(기본키) 해당하느 상세 페이지 
	@GetMapping("/view")
	public String viewBoard(@RequestParam int num, Model model) {
		boardService.updateReadCount(num); // 조회수 증가
		
					//DB에서 num(기본키) 62번 전체 데이터를 가져와서 vo 저장 (62는 DB에 존재하는 기본키)
		BoardVO vo = boardService.selectOneByNum(num);
		
		// vo 저장된 num(62(예시))번 데이터를 board변수에 담아서 boardView.jsp에 전달
		model.addAttribute("board", vo);  
		return "boardView";
	}
	
	//localhost:8080/board/check(get) -> 삭제, 수정시 입력화면 출력 
	@GetMapping("/check")
	public String check(@RequestParam int num, Model model) {
		model.addAttribute("num", num); //모델에 값을 담아서 jsp파일로 전달
		return "checkBoard";
	}
	
	//localhost:8080/board/check(post) -> 삭제, 수정시 DB조회해서 비밀번호 체크
	@PostMapping("/check")
	public String CheckPost(@RequestParam int num, @RequestParam String pass, Model model) {
		//log.info("check Post => " + num + " : " + pass);
		
		//서비스 호출해서 pass가 맞으면 삭제하고, 틀리면 비밀번호가 틀리다고 뷰에 전달
		boolean check = boardService.checkPassword(num, pass);
		
		if(check) {
			//비밀번호 맞음
			model.addAttribute("num", num);
			return "checkSuccess";
		}else {
			//비밀번호 틀렸다
			model.addAttribute("message", "비밀번호가 틀렸습니다.");
			model.addAttribute("num", num);
			return "checkBoard";
		}
	}
	
	//localhost:8080/board/delete => num 해당하는 데이터(DB) 삭제
	@GetMapping("/delete")
	public String deleteGet(@RequestParam int num) {
		boardService.deleteBoard(num);
		return "redirect:/board/boardList";
	}
	
	//localhost:8080/board/update(get) -> 수정화면 출력
	@GetMapping("/update")
	public String updateGet(@RequestParam int num, Model model) {
//		log.info("vo : " + vo);
		BoardVO vo = boardService.selectOneByNum(num);
		model.addAttribute("board", vo);
	    return "boardUpdate";
	}
	
	//localhost:8080/board/update(post) -> 수정화면에서 입력한 내용을 DB 반영
	@PostMapping("/update")
	public String updatePost(BoardVO vo) {
		boardService.updateBoard(vo);
	    return "redirect:/board/view?num="+vo.getNum();
	}
	
	
}