package kr.co.our.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

import kr.co.our.common.security.domain.CustomUser;
import kr.co.our.domain.Member;
import kr.co.our.domain.PageRequest;
import kr.co.our.service.HomeService;

@Controller
public class HomeController {
	
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private HomeService service;

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
//	@PreAuthorize("hasRole('ROLE_MEMBER')")
	public String home(@ModelAttribute("pgrq") PageRequest pageRequest, Locale locale, Model model) {
		
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		
		Map map =  new HashMap();
		map.put("pageStart", pageRequest.getPageStart());
		map.put("sizePerPage", pageRequest.getSizePerPage());
		map.put("keyword", pageRequest.getKeyword());
		
		
		
		return "home";
	}
	
	
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public void searchList(@ModelAttribute("pgrq") PageRequest pageRequest, Model model, Authentication authentication) throws Exception {
		
		String keyword = pageRequest.getKeyword();
		
		Map map = new HashMap();
		map.put("pageStart", pageRequest.getPageStart());
		map.put("sizePerPage", pageRequest.getSizePerPage());
		map.put("keyword", keyword);
		
		model.addAttribute("list", service.basicInfoList(map));
		model.addAttribute("keyword", keyword);
		
//		return "search";
	}
	
	
	@RequestMapping(value = "/sample", method = RequestMethod.GET)
	public void sample(Model model) throws Exception {
		
		
	}
	
}








