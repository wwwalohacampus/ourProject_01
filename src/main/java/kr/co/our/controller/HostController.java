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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.our.common.security.domain.CustomUser;
import kr.co.our.domain.Member;
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
	public void hostJoinForm(Model model, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member =  customUser.getMember();
		
		model.addAttribute(member);
	}
	
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_MEMBER')")
	public String hostJoin(Member member, String email, RedirectAttributes rttr) throws Exception {
		Integer userNo = member.getUserNo();
		
		memberService.modifyAuth(userNo);
		
		rttr.addFlashAttribute("member", member);
		rttr.addFlashAttribute("email", email);
		
		return "redirect:/host/joinSuccess";
	}
	
	
	@RequestMapping(value = "/joinSuccess", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole({'ROLE_MEMBER' , 'ROLE_HOST'})")
	public void joinSuccess(Member member, String email, Model model, Authentication authentication) throws Exception {
		
		model.addAttribute(member);
		model.addAttribute(email);
	}
	
	
	

}
