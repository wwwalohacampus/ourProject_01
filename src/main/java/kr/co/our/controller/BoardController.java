package kr.co.our.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.our.common.domain.CodeLabelValue;
import kr.co.our.common.security.domain.CustomUser;
import kr.co.our.domain.Board;
import kr.co.our.domain.Member;
import kr.co.our.domain.PageRequest;
import kr.co.our.domain.Pagination;
import kr.co.our.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	
	private static final Logger log = LoggerFactory.getLogger(BoardController.class);

	
	@Autowired
	private BoardService service;
	
	
	// 등록 화면
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole({'ROLE_MEMBER' , 'ROLE_HOST'})")
	public void registerForm(Model model, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member =  customUser.getMember();
		
		Board board = new Board();
		// Login 한 userId 를 가져와서 board 에 넣어준다.
		board.setWriter(member.getUserId());
		
		// org.springframework.ui.Model  (인터페이스)
		// (메소드) --> Model addAttribute(Object value)
		// value를 추가. value의 패키지 이름을 제외한 단순 클래스 이름을 모델 이름으로 사용(첫 글자는 소문자). 
		model.addAttribute(board);
		
	}
	
	// 등록 처리
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole({'ROLE_MEMBER' , 'ROLE_HOST'})")
	public String register(Board board, RedirectAttributes rttr) throws Exception {
		service.register(board);
		
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		return "redirect:/board/list";
	}
	
	// 목록 화면
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	// 페이지요청 정보를 매개변수로 받고 다시 뷰에 전달한다.
	public void list(@ModelAttribute("pgrq") PageRequest pageRequest, Model model) throws Exception {
		// 뷰에 페이징 처리를 한 게시글 목록을 전달한다.
		model.addAttribute("list", service.list(pageRequest));
		
		// 페이징 네비게이션 정보를 뷰에 전달한다.
		Pagination pagination = new Pagination();
		pagination.setPageRequest(pageRequest);
		
		// 페이지 네비게이션 정보에 검색처리된 게시글 건수를 저장한다.
		pagination.setTotalCount(service.count(pageRequest));
		
		model.addAttribute("pagination", pagination);
		
		// 검색유형의 코드명과 코드값을 정의한다.
		List<CodeLabelValue> searchTypeCodeValueList = new ArrayList<CodeLabelValue>();
		searchTypeCodeValueList.add(new CodeLabelValue("n","---"));
		searchTypeCodeValueList.add(new CodeLabelValue("t","Title"));
		searchTypeCodeValueList.add(new CodeLabelValue("c","Content"));
		searchTypeCodeValueList.add(new CodeLabelValue("w","Writer"));
		searchTypeCodeValueList.add(new CodeLabelValue("tc","Title OR Content"));
		searchTypeCodeValueList.add(new CodeLabelValue("cw","Content OR Writer"));
		searchTypeCodeValueList.add(new CodeLabelValue("tcw","Title OR Content OR Writer"));
		
		model.addAttribute("searchTypeCodeValueList", searchTypeCodeValueList);
		
	}
	
	// 상세 화면
	@RequestMapping(value = "/read", method=RequestMethod.GET)
	public void read(int boardNo, @ModelAttribute("pgrq") PageRequest pageRequest, Model model) throws Exception {
//		model.addAttribute(service.read(boardNo));
		
		// 조회한 게시글 상세정보를 뷰에 전달한다.
		Board board = service.read(boardNo);
//		board.setPageRequest(pageRequest);
		
		
		model.addAttribute(board);
	}
	
	// 수정 화면
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	public void modifyForm(int boardNo, @ModelAttribute("pgrq") PageRequest pageRequest, Model model) throws Exception {
//		model.addAttribute(service.read(boardNo));
		
		// 조회한 게시글 상세정보를 뷰에 전달한다.
		Board board = service.read(boardNo);
		
//		board.setPageRequest(pageRequest);
		
		model.addAttribute(board);
	}
	
	
	// 수정 처리
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	public String modify(Board board, PageRequest pageRequest, RedirectAttributes rttr) throws Exception {
		
		service.modify(board);
		// RedirenctAttributes 객체에 일회성 데이터를 지정하여 전달한다.
		rttr.addAttribute("page", pageRequest.getPage());
		rttr.addAttribute("sizePerPage", pageRequest.getSizePerPage());
		
		// 검색유형과 검색어를 뷰에 전달한다.
		
		
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		
		return "redirect:/board/list";
	}
	
	// 삭제 처리
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	public String remove(int boardNo, RedirectAttributes rttr) throws Exception {
		service.remove(boardNo);
		
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		return "redirect:/board/list";
	}
	
}

















