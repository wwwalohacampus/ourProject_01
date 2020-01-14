package kr.co.our.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.our.domain.PageRequest;
import kr.co.our.domain.ReservInfo;
import kr.co.our.service.MemberService;

@Controller
@RequestMapping("/host")
public class HostController {
	
	
	private static final Logger log = LoggerFactory.getLogger(HostController.class);
	
	@Autowired
	private MemberService memberService;
	
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_MEMBER')")
	public void hostJoin(Model model, Authentication authentication) throws Exception {
		
		
	}
	
	
	
	
	
	

}
