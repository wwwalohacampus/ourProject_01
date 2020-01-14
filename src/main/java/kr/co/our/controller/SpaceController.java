package kr.co.our.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.our.common.domain.CodeLabelValue;
import kr.co.our.common.security.domain.CustomUser;
import kr.co.our.domain.AccountsInfo;
import kr.co.our.domain.BasicInfo;
import kr.co.our.domain.ContactsInfo;
import kr.co.our.domain.Member;
import kr.co.our.domain.PageRequest;
import kr.co.our.domain.Pagination;
import kr.co.our.domain.RefundInfo;
import kr.co.our.domain.ReservInfo;
import kr.co.our.domain.RoomInfo;
import kr.co.our.domain.SpaceBasicImg;
import kr.co.our.domain.SpaceInfo;
import kr.co.our.domain.UsageInfo;
import kr.co.our.service.RoomService;
import kr.co.our.service.SpaceService;
import kr.co.our.util.MediaUtils;
import kr.co.our.util.UploadFileUtils;


@Controller
@RequestMapping("/space")
public class SpaceController {
	
	private static final Logger logger = LoggerFactory.getLogger(SpaceController.class);
	
	@Value("${upload.path}")
	private String uploadPath;
	
	@Autowired
	private SpaceService service;
	
	@Autowired
	private RoomService roomService;

	// 공간 등록하기
	@RequestMapping(value ="", method = RequestMethod.GET)
	public void space(Model model) throws Exception {
		
	}
	
	
	// 공간 메뉴얼
	@RequestMapping(value = "/menual", method = RequestMethod.GET)
	public void menual(Model model) throws Exception {
//		model.addAttribute();
	}
	
	
	// 공간 기본정보 등록하기 - 화면
	@PreAuthorize("hasRole('ROLE_HOST')")
	@RequestMapping(value = "/basic_info", method = RequestMethod.GET)
	public void basicInfoRegisterForm(Model model, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member =  customUser.getMember();
		
		BasicInfo basicInfo = new BasicInfo();
		basicInfo.setHostId(member.getUserId());
		
		SpaceBasicImg spaceBasicImg = new SpaceBasicImg();
		
		model.addAttribute(basicInfo);
		model.addAttribute(spaceBasicImg);
		
	}
	
	// 공간 기본정보 등록하기 - 등록처리
	@RequestMapping(value = "/basic_info", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_HOST')")
	public String basicInfoRegister(BasicInfo basicInfo, SpaceBasicImg spaceBasicImg, RedirectAttributes rttr) throws Exception {
		
		int space_num = 0;
		int space_count = service.spaceCountAll();
		
		// spaceInfo 첫 데이터 삽입
		if( space_count  == 0 ) {
			space_num = 1; 
		} else {
			space_num = space_count + 1;
		}
		
		String host_id = basicInfo.getHostId();
		
		// 처음 공간 기본정보가 등록 시, 공간 상태정보가 등록된다.
		SpaceInfo spaceInfo = new SpaceInfo();
		spaceInfo.setSpaceNum(space_num);
		spaceInfo.setSpaceName(basicInfo.getSpaceName());
		spaceInfo.setBasicStatus("CHECK");
		spaceInfo.setCreatedBy(host_id);
		
		// space_info 테이블에 공간 상태정보 추가
		service.spaceInfoRegister(spaceInfo);
		
		
		// basic_info 테이블에 공간 기본정보 추가
		int space_id = service.spaceInfoReadSpaceId(space_num);
		space_num = service.spaceInfoReadSpaceNum(space_id);
			
		basicInfo.setSpaceNum(space_num);
		service.basicInfoRegister(basicInfo);
		
		// space_basic_img 테이블에 공간 사진정보 추가
		spaceBasicImg.setSpaceNum(space_num);
		spaceBasicImg.setHostId(host_id);
		service.spaceImgRegister(spaceBasicImg);
		
		
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		return "redirect:/space/contacts_info?spaceId=" + space_id;
		
	}
	
	
	
	
	// 목록 화면
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_HOST')")
	public void basicInfoList(@ModelAttribute("pgrq") PageRequest pageRequest, Model model, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member =  customUser.getMember();
		String hostId = member.getUserId();
		
		Map map = new HashMap();
		map.put("hostId", hostId);
		map.put("pageStart", pageRequest.getPageStart());
		map.put("sizePerPage", pageRequest.getSizePerPage());
		map.put("searchType", pageRequest.getSearchType());
		map.put("keyword", pageRequest.getKeyword());
		
		
		model.addAttribute("list", service.basicInfoList(map));
		
		// 페이징 네비게이션 정보를 뷰에 전달한다.
		Pagination pagination = new Pagination();
		pagination.setPageRequest(pageRequest);
		
		// 페이지 네비게이션 정보에 검색처리된 게시글 건수를 저장한다.
		pagination.setTotalCount(service.spaceCount(pageRequest));
		
		model.addAttribute("pagination", pagination);
		
		// 검색유형의 코드명과 코드값을 정의한다.
		List<CodeLabelValue> searchTypeCodeValueList = new ArrayList<CodeLabelValue>();
		searchTypeCodeValueList.add(new CodeLabelValue("n", "----------"));
		searchTypeCodeValueList.add(new CodeLabelValue("t", "공간 이름"));
		searchTypeCodeValueList.add(new CodeLabelValue("g", "공간 태그"));
		searchTypeCodeValueList.add(new CodeLabelValue("tg","공간 이름/공간 태그"));
		
		model.addAttribute("searchTypeCodeValueList", searchTypeCodeValueList);
	}
	
	
	
	// 공간 연락처정보 등록하기 - 화면
	@RequestMapping(value = "/contacts_info", method = RequestMethod.GET)
	public void contactsInfoRegisterForm(Integer spaceId, Integer spaceNum, Model model) throws Exception {
		ContactsInfo contactsInfo = new ContactsInfo();
		contactsInfo.setSpaceId(spaceId);
		model.addAttribute(contactsInfo);
	}	
	
	// 공간 연락처정보 등록하기 - 등록처리
	@RequestMapping( value = "/contacts_info", method = RequestMethod.POST)
	public String contactsInfoRegister(ContactsInfo contactsInfo, RedirectAttributes rttr, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member =  customUser.getMember();
		String email1 = contactsInfo.getSpaceEmail1();
		String email2 = contactsInfo.getSpaceEmail2();
		int space_num = contactsInfo.getSpaceNum();
		int space_id = contactsInfo.getSpaceId();
		contactsInfo.setSpaceEmail(email1 + "@" + email2);
		contactsInfo.setCreatedBy(member.getUserId());
		
		if( space_num == 0) {
			space_num = service.spaceInfoReadSpaceNum(contactsInfo.getSpaceId());
		}
		
		contactsInfo.setSpaceNum(space_num);
		service.contactsInfoRegister(contactsInfo);
		service.contactsStatusModify(space_num);

		String post_type = contactsInfo.getPostType();
		
		if( post_type.equals("except")) {
			return "redirect:/space/modify?spaceNum=" + contactsInfo.getSpaceNum();
		} 
		return "redirect:/space/usage_info?spaceId=" + space_id;
	}
	
	
	// 공간 이용정보 등록하기 - 화면
	@RequestMapping(value = "/usage_info", method = RequestMethod.GET)
	public void usageInfoRegisterForm(int spaceId, Model model) throws Exception {
		UsageInfo usageInfo = new UsageInfo();
		usageInfo.setSpaceId(spaceId);
		
		model.addAttribute(usageInfo);
	}
	
	// 공간 이용정보 등록하기 - 등록처리
	@RequestMapping(value = "/usage_info", method = RequestMethod.POST)
	public String usageInfoRegister(UsageInfo usageInfo, RedirectAttributes rttr, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member =  customUser.getMember();
		usageInfo.setCreatedBy(member.getUserId());
		
		int space_num = service.spaceInfoReadSpaceNum(usageInfo.getSpaceId());
		int space_id = usageInfo.getSpaceId();
		
		usageInfo.setSpaceNum(space_num);
		
		service.usageInfoRegister(usageInfo);	
		service.usageStatusModify(space_num);
		
		String post_type = usageInfo.getPostType();
		
		if( post_type.equals("except")) {
			return "redirect:/space/modify?spaceNum=" + usageInfo.getSpaceNum();
		} 
		return "redirect:/space/accounts_info?spaceId=" + space_id;
	}
	
	
	
	// 공간 정산/환불정보 등록하기 - 화면
	@RequestMapping(value = "/accounts_info", method = RequestMethod.GET)
	public void accountsInfoRegisterForm(Integer spaceId, Model model) throws Exception {

		AccountsInfo accountsInfo = new AccountsInfo();
		accountsInfo.setSpaceId(spaceId);
		RefundInfo refundInfo = new RefundInfo();
		refundInfo.setSpaceId(spaceId);
		
		model.addAttribute(accountsInfo);
		model.addAttribute(refundInfo);
	}
	
	// 공간 정산/환불 정보 등록하기 - 등록처리	
	@RequestMapping(value = "/accounts_info", method = RequestMethod.POST)
	public void accountsInfoRegister(AccountsInfo accountsInfo, RefundInfo refundInfo, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member =  customUser.getMember();
		accountsInfo.setCreatedBy(member.getUserId());
		refundInfo.setCreatedBy(member.getUserId());
				
		int space_num = service.spaceInfoReadSpaceNum(accountsInfo.getSpaceId());
		int space_id = accountsInfo.getSpaceId();
		
		accountsInfo.setSpaceNum(space_num);
		refundInfo.setSpaceNum(space_num);
				
		service.accountsInfoRegister(accountsInfo);
		service.refundInfoRegister(refundInfo);
		service.accountsStatusModify(space_num);
		service.refundStatusModify(space_num);
		
		String post_type = accountsInfo.getPostType();
		
	}
	

	
	// 공간 등록 리스트 - 화면
	// 공간 - (기본/연락처/이용/정산) 정보의 상태를 저장하는 테이블이 필요.
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void modifyForm(Integer spaceNum, Model model) throws Exception {
		SpaceInfo spaceInfo = service.spaceInfoRead(spaceNum);
		String basicStatus = spaceInfo.getBasicStatus();
		String contactsStatus = spaceInfo.getContactsStatus();
		String usageStatus = spaceInfo.getUsageStatus();
		String accountsStatus = spaceInfo.getAccountsStatus();
		String refundStatus = spaceInfo.getRefundStatus();
		String roomStatus = spaceInfo.getRoomStatus();
		String checkStatus = spaceInfo.getCheckStatus();
		String publicStatus = spaceInfo.getPublicStatus();
		String needStr = "";
		
		List<String> list = new ArrayList();
		if( basicStatus    != null ) 	{ list.add(spaceInfo.getBasicStatus()); } 	else { needStr += "기본, "; }
		if( contactsStatus != null )	{ list.add(spaceInfo.getContactsStatus()); } else { needStr += "연락처, "; }
		if( usageStatus 	 != null )  { list.add(spaceInfo.getUsageStatus()); } 	else { needStr += "이용, "; }
		if( accountsStatus != null ) 	{ list.add(spaceInfo.getAccountsStatus()); } else { needStr += "정산, "; }
		if( refundStatus	 != null )  { list.add(spaceInfo.getRefundStatus()); }   else { needStr += "환불, "; }
		if( roomStatus	 != null )  	{ list.add(spaceInfo.getRoomStatus()); }   else { needStr += "세부공간, "; }
		if( needStr != "" )  
			needStr = needStr.substring(0,needStr.lastIndexOf(","));
		
		boolean check = false;
		
		for (int i = 0; i < list.size() ; i++) {
			if(list.get(i).equals("CHECK")) {
				if(list.size() == 6)
					check = true;
			} else {
				check = false;
				break;
			}
		}
		if(check) {
			if(publicStatus != null) {
				model.addAttribute("CHECK", "CHECK");
				if( publicStatus.equals("CHECK")) {
					model.addAttribute("sb_msg", "공간이 런칭되었습니다!!! [비공개] 버튼을 눌러 공간을 비활성화할 수 있습니다.");
				} else if (publicStatus.equals("OFF")) {
					model.addAttribute("sb_msg", "공간이 비활성화되었습니다. [공개] 버튼을 눌러 활성화할 수 있습니다.");
				}
			}
			else if(checkStatus != null ) {
				if( checkStatus.equals("WAIT") ) {
					model.addAttribute("CHECK", "WAIT");
					model.addAttribute("sb_msg", "검수신청이 완료되었습니다. 검수가 완료되면 [공개/비공개] 버튼이 활성화 됩니다.");
				} else if( checkStatus.equals("CHECK") ) {
					model.addAttribute("CHECK", "WAIT");
					model.addAttribute("sb_msg", "축하합니다! 검수승인이 완료되었습니다. [공개] 버튼을 눌러 공간을 런칭하세요.");
				}
			} else {
				model.addAttribute("CHECK", check);
				model.addAttribute("sb_msg", "모든 정보가 입력 완료되었습니다. 검수신청 후, 해당 공간이 검수되면 런칭할 수 있습니다.");
			}
		} else {
			model.addAttribute("CHECK", check);
			model.addAttribute("sb_msg", "아직 모든 필수 정보가 입력되지 않았습니다. " + needStr + " (정보)를 입력해주세요.");
		}
		
		model.addAttribute(service.spaceInfoRead(spaceNum));
	}
	
	
	//  공간 등록 리스트 - 검수신청 처리
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_MEMBER')")
	public String Modify(SpaceInfo spaceInfo, String checkStatus, String publicStatus, RedirectAttributes rttr) throws Exception {
		Integer space_num = spaceInfo.getSpaceNum();
		logger.info("###### " + checkStatus);
		logger.info("###### " + publicStatus);
		
		/*
		 1. 검수 신청 				( 	checkStatus : "WAIT" 	/ publicStatus : null    )
		 2. 검수 신청 취소			( 	checkStatus : "CANCEL" 	/ publicStatus : null    )
		 2. 공개/비공개 전환			
		 	- 비공개				( 	checkStatus : "CHECK"   / publicStatus : "OFF"     )
		 	- 공개				( 	checkStatus : "CHECK"   / publicStatus : "CHECK"   )
		 */
		
		
		if( checkStatus != null && publicStatus != null ) {
			spaceInfo.setPublicStatus(publicStatus);
			service.modifyPublicStatus(spaceInfo);
			
			spaceInfo.setCheckStatus(checkStatus);
			service.modifyCheckStatus(spaceInfo);
		}
		
		if( publicStatus == null && checkStatus != null ) {
			spaceInfo.setCheckStatus(checkStatus);
			service.modifyCheckStatus(spaceInfo);
		}
		
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		return "redirect:/space/modify?spaceNum=" + space_num;
	}
	
	
	
	// 공간 기본정보 수정하기 - 화면
	@RequestMapping(value = "/modify/basic_info", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_HOST')")
	public void basicInfoModifyForm(Integer spaceNum, Model model, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member =  customUser.getMember();
		String host_id = member.getUserId();
		BasicInfo basicInfo = new BasicInfo();
		basicInfo = service.basicInfoRead(spaceNum);
		basicInfo.setHostId(host_id);
		
		SpaceBasicImg spaceBasicImg = new SpaceBasicImg();
		spaceBasicImg.setSpaceNum(spaceNum);
		spaceBasicImg.setHostId(host_id);
		
		
		
		// 공간 유형 나눠주기
		String spaceCategory = basicInfo.getSpaceCategory();
		
		if( spaceCategory == null || spaceCategory.length() == 0) {
			
		} else {
			String [] cate = spaceCategory.split(",");
			
			for (String c : cate) {
				switch (c) {
				case "회의실": basicInfo.setSpaceCategory1(c); break;
				case "세미나실": basicInfo.setSpaceCategory2(c); break;
				case "다목적홀": basicInfo.setSpaceCategory3(c); break;
				case "작업실": basicInfo.setSpaceCategory4(c); break;
				case "레저시설": basicInfo.setSpaceCategory5(c); break;
				case "파티룸": basicInfo.setSpaceCategory6(c); break;
				
				}
			}
		}

		// 공간카테고리 값이 중복되는 것을 방지
		basicInfo.setSpaceCategory(null);
		
		model.addAttribute(basicInfo);
		model.addAttribute(spaceBasicImg);
	}
	
	// 공간 기본정보 수정하기 - 수정처리
	@RequestMapping(value = "/modify/basic_info", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_HOST')")
	public String basicInfoModify(BasicInfo basicInfo, SpaceBasicImg spaceBasicImg, RedirectAttributes rttr) throws Exception {
		SpaceInfo spaceInfo = new SpaceInfo();
		spaceInfo.setSpaceNum(basicInfo.getSpaceNum());
		spaceInfo.setSpaceName(basicInfo.getSpaceName());
		
		String[] files = spaceBasicImg.getFiles();
		
		if( files != null) {
			for (int i = 0; i < files.length; i++) {
				logger.info("files[i] = " + files[i]);
			}
		}
		
//		service.modifyFile(spaceBasicImg);
		service.basicInfoModify(basicInfo, spaceBasicImg);
		service.spaceNameModify(spaceInfo);
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		return "redirect:/space/modify?spaceNum=" + basicInfo.getSpaceNum();
	}
	
	// 공간 연락처정보 수정하기 - 화면
	@RequestMapping(value = "/modify/contacts_info", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_HOST')")
	public void contactsInfoModifyForm(Integer spaceNum, Model model, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member =  customUser.getMember();
		ContactsInfo contactsInfo = new ContactsInfo();
		contactsInfo = service.contactsInfoRead(spaceNum);
		contactsInfo.setHostId(member.getUserId());
		
		
		
		String[] email = contactsInfo.getSpaceEmail().split("@");
		String email1 = "";  
		String email2 = "";	 
		if(email.length == 2) {
			email1 = email[0];
			email2 = email[1];
		}
		
		contactsInfo.setSpaceEmail1(email1);
		contactsInfo.setSpaceEmail2(email2);
		
		model.addAttribute(contactsInfo);
	}
	
	
	// 공간 연락처정보 수정하기 - 수정처리
	@RequestMapping(value = "/modify/contacts_info", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_HOST')")
	public String contactsInfoModify(ContactsInfo contactsInfo, RedirectAttributes rttr) throws Exception {
		String email1 = contactsInfo.getSpaceEmail1();
		String eamil2 = contactsInfo.getSpaceEmail2();
		contactsInfo.setSpaceEmail(email1 + "@" + eamil2);
		
		int space_num = contactsInfo.getSpaceNum();
		
		service.contactsInfoModify(contactsInfo);
		service.contactsStatusModify(space_num);
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		return "redirect:/space/modify?spaceNum=" + space_num;
	}
	
	
	// 공간 이용정보 수정하기 - 화면
	@RequestMapping(value = "/modify/usage_info", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_HOST')")
	public void usageInfoModifyForm(Integer spaceNum, Model model, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member =  customUser.getMember();
				
		UsageInfo usageInfo = new UsageInfo();
		usageInfo = service.usageInfoRead(spaceNum);
		usageInfo.setHostId(member.getUserId());
		
		// 휴무일 요일 나눠주기
		String holidayWeek = usageInfo.getHolidayWeek();
		
		if( holidayWeek == null || holidayWeek.length() == 0) {
			
		} else {
			String [] week = holidayWeek.split(",");
			
			for (String w : week) {
				switch (w) {
				case "월": usageInfo.setHolidayWeek0(w); break;
				case "화": usageInfo.setHolidayWeek1(w); break;
				case "수": usageInfo.setHolidayWeek2(w); break;
				case "목": usageInfo.setHolidayWeek3(w); break;
				case "금": usageInfo.setHolidayWeek4(w); break;
				case "토": usageInfo.setHolidayWeek5(w); break;
				case "일": usageInfo.setHolidayWeek6(w); break;
				
				}
			}
		}
		
		model.addAttribute(usageInfo);
	}
	

	// 공간 이용정보 수정하기 - 수정처리
	@RequestMapping(value = "/modify/usage_info", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_HOST')")
	public String usageInfoModify(UsageInfo usageInfo, RedirectAttributes rttr) throws Exception {
		
		service.usageInfoModify(usageInfo);
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		return "redirect:/space/modify?spaceNum=" + usageInfo.getSpaceNum();
	}
	
	
	
	// 공간 정산정보 수정하기 - 화면
	@RequestMapping(value = "/modify/accounts_info", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_HOST')")
	public void accountsInfoModifyForm(Integer spaceNum, Model model, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member =  customUser.getMember();
		
		AccountsInfo accountsInfo = new AccountsInfo();
		accountsInfo = service.accountsInfoRead(spaceNum);
		accountsInfo.setAccountsHostId(member.getUserId());
		
		String[] email = accountsInfo.getBizEmail().split("@");
		String email1 = "";
		String email2 = "";
		if(email.length == 2) {
			email1 = email[0];
			email2 = email[1];
		} 
		
		accountsInfo.setBizEmail1(email1);
		accountsInfo.setBizEmail2(email2);
		
		RefundInfo refundInfo = new RefundInfo();
		refundInfo = service.refundInfoRead(spaceNum);
		refundInfo.setRefundHostId(member.getUserId());
			
		model.addAttribute(accountsInfo);
		model.addAttribute(refundInfo);
	}
	
	
	// 공간 정산정보 수정하기 - 수정처리
	@RequestMapping(value = "/modify/accounts_info", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_HOST')")
	public String accountsInfoModify(AccountsInfo accountsInfo, RefundInfo refundInfo, RedirectAttributes rttr) throws Exception {
		String email1 = accountsInfo.getBizEmail1();
		String email2 = accountsInfo.getBizEmail2();
		accountsInfo.setBizEmail(email1 + "@" + email2);
		
		int space_num =  accountsInfo.getSpaceNum();
	
		service.accountsInfoModify(accountsInfo);
		service.refundInfoModify(refundInfo);
		service.accountsStatusModify(space_num);
		service.refundStatusModify(space_num);
		
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		return "redirect:/space/modify?spaceNum=" + space_num;
	}
	
	
	// 공간 삭제하기 - 삭제처리
	@RequestMapping(value = "/manage", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_HOST')")
	public String spaceInfoRemove(int spaceNum, RedirectAttributes rttr) throws Exception {
		service.spaceInfoRemove(spaceNum);
		return "redirect:/space/manage";
	}
	
	
	/*
	 * 	공간정보 중간 추가 (Create)
	 */
	
	
	// 공간 연락처 정보 중간 추가  - 화면
	@RequestMapping(value = "/create/contacts_info", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_HOST')")
	public void contactsInfoExceptRegisterForm(Integer spaceId, Integer spaceNum,  Model model) throws Exception {
		ContactsInfo contactsInfo = new ContactsInfo();
		contactsInfo.setSpaceId(spaceId);
		contactsInfo.setSpaceNum(spaceNum);
		
		model.addAttribute(contactsInfo);
	}	
	

	// 공간 이용정보 중간 추가 - 화면
	@RequestMapping(value = "/create/usage_info", method = RequestMethod.GET)
	public void usageInfoExceptRegisterForm(Integer spaceId, Integer spaceNum, Model model) throws Exception {
		UsageInfo usageInfo = new UsageInfo();
		usageInfo.setSpaceId(spaceId);
		usageInfo.setSpaceNum(spaceNum);
		
		model.addAttribute(usageInfo);
	}
	
	// 공간 정산/환불정보 중간 추가 - 화면
	@RequestMapping(value = "/create/accounts_info", method = RequestMethod.GET)
	public void accountsInfoExceptRegisterForm(Integer spaceId, Model model) throws Exception {
		int space_num = service.spaceInfoReadSpaceNum(spaceId);
		
		AccountsInfo accountsInfo = new AccountsInfo();
		accountsInfo.setSpaceId(spaceId);
		accountsInfo.setSpaceNum(space_num);
		RefundInfo refundInfo = new RefundInfo();
		refundInfo.setSpaceId(spaceId);
		refundInfo.setSpaceNum(space_num);
		
		model.addAttribute(accountsInfo);
		model.addAttribute(refundInfo);
	}
	
	
	// (공간 사진정보) 파일업로드  - 비동기 처리
	@ResponseBody
	@RequestMapping(value = "/uploadAjax", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception {
		logger.info("originalName : " + file.getOriginalFilename());
		
		String savedName = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
		
		return new ResponseEntity<String>(savedName, HttpStatus.CREATED);
	}
	
	// (공간 사진정보) 업로드된 파일 제목/썸네일 미리보기
	@ResponseBody
	@RequestMapping("/displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception {
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		
		logger.info("FILE NAME: " + fileName);

		try {					

			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
			logger.info("FILE FORMAT: "  + formatName);
			
			MediaType mType = MediaUtils.getMediaType(formatName);
			
			HttpHeaders headers = new HttpHeaders();

			in = new FileInputStream(uploadPath + fileName);
			
			if (mType != null) {
				headers.setContentType(mType);
			} else {
				fileName = fileName.substring(fileName.indexOf("_") + 1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");             
			}

			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		return entity;
	}
	
	// (공간 사진정보) 저장된 파일 불러오기
	@RequestMapping("/getAttach/{spaceNum}")
	@ResponseBody
	public List<String> getAttach(@PathVariable("spaceNum") Integer spaceNum) throws Exception{
		logger.info("getAttach spaceNum: " + spaceNum);
		return service.getAttach(spaceNum);
	}
	
	
	
	/* 공간 상세(룸) 조회 페이지 */
	@RequestMapping(value = "/{spaceNum}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_HOST')")
	public String roomDetailPage(@ModelAttribute("pgrq") PageRequest pageRequest, @PathVariable("spaceNum") Integer spaceNum, Model model, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member =  customUser.getMember();
		String userId = member.getUserId();
		
		logger.info("userId : " + userId);
		
		logger.info(spaceNum.toString());
		BasicInfo basicInfo = service.basicInfoRead(spaceNum);
		SpaceBasicImg spaceBasicImg = service.spaceBasicImgRead(spaceNum);
		RefundInfo refundInfo = service.refundInfoRead(spaceNum);
		
		Map map = new HashMap();
		map.put("spaceNum", spaceNum);
		List<RoomInfo> roomListwithPrice = roomService.roomListwithPrice(map);
		
		logger.info(roomListwithPrice.get(0).getCreatedBy());
		
		model.addAttribute("basicInfo", basicInfo);
		model.addAttribute("refundInfo", refundInfo);
		model.addAttribute("roomList", roomListwithPrice);
		model.addAttribute("spaceBasicImg", spaceBasicImg);
		model.addAttribute("spaceNum", spaceNum);
		model.addAttribute("userId", userId);
		
		return "/space/space";
	}
	
	
	/* 공간 상세(룸) 바로 예약하기 */
	@RequestMapping( value = "/{spaceNum}", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole({'ROLE_MEMBER' , 'ROLE_HOST'})")
	public String roomDetailPagePost(ReservInfo reservInfo, RedirectAttributes rttr, Authentication authentication) throws Exception {
		logger.info(reservInfo.toString());
		
		int spaceNum = reservInfo.getSpaceNum();
		
		rttr.addFlashAttribute("reservInfo", reservInfo);
		
		
		return "redirect:/reserve/space/" + spaceNum;
	}
	

	
}








