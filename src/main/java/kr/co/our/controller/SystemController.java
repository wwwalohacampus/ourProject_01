package kr.co.our.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.our.domain.SpaceInfo;
import kr.co.our.service.SystemService;

@Controller
@RequestMapping("/system")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class SystemController {

	
	private static final Logger log = LoggerFactory.getLogger(SystemController.class);
	
	@Autowired
	private SystemService service;
	
	
	// 공간 검수하기 - 화면 (목록)
	@RequestMapping(value = "/space/space_check", method = RequestMethod.GET)
	public void space(Model model) throws Exception {
		String checkStatus = "WAIT";
		List<SpaceInfo> checkedList = service.spaceInfoList(checkStatus);
		model.addAttribute("wlist", checkedList);
		
		checkStatus = "CHECK";
		checkedList = service.spaceInfoList(checkStatus);
		model.addAttribute("clist", checkedList);
		
	}
	
	
	// 공간 검수하기 - 검수처리
	@RequestMapping(value = "/space/space_check", method = RequestMethod.POST)
	public String spaceCheck(Integer spaceNum, RedirectAttributes rttr) throws Exception {
		
		service.checkStatusModify(spaceNum);
		
		return "redirect:/system/space/space_check";
	}
	
	
	
	

}
