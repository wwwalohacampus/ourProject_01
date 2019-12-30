package kr.co.our.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.our.common.security.domain.CustomUser;
import kr.co.our.domain.AccountsInfo;
import kr.co.our.domain.BasicInfo;
import kr.co.our.domain.Member;
import kr.co.our.domain.PageRequest;
import kr.co.our.domain.RefundInfo;
import kr.co.our.domain.ReservInfo;
import kr.co.our.domain.RoomInfo;
import kr.co.our.domain.SpaceBasicImg;
import kr.co.our.service.ReserveService;
import kr.co.our.service.RoomService;
import kr.co.our.service.SpaceService;

@Controller
@RequestMapping("/reserve")
public class ReserveController {
	
	
	private static final Logger log = LoggerFactory.getLogger(ReserveController.class);
	
	@Autowired
	private SpaceService spaceService;
	
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private ReserveService reserveService;
	
	
	
	/* 공간 예약 페이지 */
	@RequestMapping(value = "/space/{spaceNum}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_MEMBER')")
	public String reservePage(ReservInfo reservInfo, @PathVariable("spaceNum") Integer spaceNum, @ModelAttribute("pgrq") PageRequest pageRequest, Locale locale, Model model, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member =  customUser.getMember();
		String userId = member.getUserId();
		
		BasicInfo basicInfo = spaceService.basicInfoRead(spaceNum);
		SpaceBasicImg spaceBasicImg = spaceService.spaceBasicImgRead(spaceNum);
		AccountsInfo accountsInfo = spaceService.accountsInfoRead(spaceNum);
		RefundInfo refundInfo = spaceService.refundInfoRead(spaceNum);

//		log.info(refundInfo.toString());
		
		Integer roomNum = reservInfo.getRoomNum();
		
		Map map = new HashMap();
		map.put("spaceNum", spaceNum);
		map.put("roomNum", roomNum);
		
		RoomInfo roomInfo = roomService.roomWithPrice(map);
		
		// 옵션 유형 나눠주기
//		String roomOption = roomInfo.getRoomOption();
//		
//		if( roomOption == null || roomOption.length() == 0) {
//			
//		} else {
//			String [] cate = roomOption.split(",");
//			
//			for (String c : cate) {
//				switch (c) {
//				case "TV/프로젝터": 			roomInfo.setRoomOpt1(c); break;
//				case "인터넷/와이파이": 		roomInfo.setRoomOpt2(c); break;
//				case "복사/인쇄기": 			roomInfo.setRoomOpt3(c); break;
//				case "화이트보드": 			roomInfo.setRoomOpt4(c); break;
//				case "음향/마이크": 			roomInfo.setRoomOpt5(c); break;
//				case "PC/노트북": 			roomInfo.setRoomOpt6(c); break;
//				
//				}
//			}
//		}
		
//		Date reservDate = reservInfo.getReservDate();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//		String formattedDate = dateFormat.format(reservDate);
//		model.addAttribute("strReservDate", formattedDate );
		
		
		model.addAttribute("basicInfo", basicInfo);
		model.addAttribute("spaceBasicImg", spaceBasicImg);
		model.addAttribute("refundInfo", refundInfo);
		model.addAttribute("accountsInfo", accountsInfo);
		model.addAttribute("roomInfo", roomInfo);
		model.addAttribute("spaceNum", spaceNum);
		model.addAttribute("userId", userId);
		model.addAttribute("reservInfo", reservInfo);
		
		
//		log.info(reservInfo.toString());
//		log.info(roomInfo.toString());
		
		
		return "/reserve/reserve";
	}
	
	
	
	/* 공간 예약 처리 */
	@RequestMapping(value = "/space", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_MEMBER')")
	public String roomDetailPagePost(ReservInfo reservInfo, RedirectAttributes rttr, Authentication authentication) throws Exception {
		log.info(reservInfo.toString());
		
		reserveService.reserveInfoRegister(reservInfo);
		
		int spaceNum = reservInfo.getSpaceNum();
		int roomNum = reservInfo.getRoomNum();
		String userId = reservInfo.getUserId();
		
		
		
		rttr.addFlashAttribute("reservInfo", reservInfo);
		
		return "redirect:/reserve/space/success?spaceNum=" + spaceNum + "&roomNum=" + roomNum;
	}
	
	
	/* 공간 예약 완료 페이지 */
	@RequestMapping(value = "/space/success", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_MEMBER')")
	public String reserveSuccessPage(ReservInfo reservInfo, Integer spaceNum, Integer roomNum, @ModelAttribute("pgrq") PageRequest pageRequest, Locale locale, Model model, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member =  customUser.getMember();
		String userId = member.getUserId();

		Map map = new HashMap();
		map.put("spaceNum", spaceNum);
		map.put("roomNum", roomNum);
		map.put("userId", userId);
		
		reservInfo = reserveService.reserveInfoReadRecent(map);
		
		BasicInfo basicInfo = spaceService.basicInfoRead(spaceNum);
		AccountsInfo accountsInfo = spaceService.accountsInfoRead(spaceNum);
		RefundInfo refundInfo = spaceService.refundInfoRead(spaceNum);
		
		map = new HashMap();
		map.put("spaceNum", spaceNum);
		map.put("roomNum", roomNum);
		
		RoomInfo roomInfo = roomService.roomWithPrice(map);
		
		model.addAttribute("spaceNum", spaceNum);
		model.addAttribute("roomNum", roomNum);
		model.addAttribute("reservInfo", reservInfo);
		model.addAttribute("basicInfo", basicInfo);
		model.addAttribute("roomInfo", roomInfo);
		model.addAttribute("refundInfo", refundInfo);
		model.addAttribute("accountsInfo", accountsInfo);
		model.addAttribute("userId", userId);
		model.addAttribute("reservInfo", reservInfo);
		
		log.info("Success : " + reservInfo.toString());
		
		return "/reserve/success";
	}
	

	
}
