package kr.co.our.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.our.domain.CodeClass;
import kr.co.our.service.CodeClassService;

@Controller
@RequestMapping("/codeclass")
//관리자 권한을 가진 사용자만 접근이 가능하다.
//@PreAuthorize("hasRole('ROLE_ADMIN')")
public class CodeClassController {

	@Autowired
	private CodeClassService service;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registerForm(Model model) throws Exception {
		CodeClass codeClass = new CodeClass();

		model.addAttribute(codeClass);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@Validated CodeClass codeClass, BindingResult result,  RedirectAttributes rttr) throws Exception {
		if(result.hasErrors()) {
			return "codeclass/register";
		}
		service.register(codeClass);

		rttr.addFlashAttribute("msg", "SUCCESS");
		return "redirect:/codeclass/list";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model) throws Exception {
		model.addAttribute("list", service.list());
	}

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void read(String classCode, Model model) throws Exception {
		model.addAttribute(service.read(classCode));
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String remove(String classCode, RedirectAttributes rttr) throws Exception {

		service.remove(classCode);

		rttr.addFlashAttribute("msg", "SUCCESS");

		return "redirect:/codeclass/list";
	}

	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void modifyForm(String classCode, Model model) throws Exception {
		model.addAttribute(service.read(classCode));
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(@Validated CodeClass codeClass, BindingResult result, RedirectAttributes rttr) throws Exception {
		if(result.hasErrors()) {
			return "codeclass/modify";
		}
		
		service.modify(codeClass);
		rttr.addFlashAttribute("msg", "SUCCESS");

		return "redirect:/codeclass/list";
	}

}
