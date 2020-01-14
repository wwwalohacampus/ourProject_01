package kr.co.our.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.our.common.security.domain.CustomUser;
import kr.co.our.domain.BasicInfo;
import kr.co.our.domain.Member;
import kr.co.our.domain.PageRequest;
import kr.co.our.domain.Pagination;
import kr.co.our.domain.RoomInfo;
import kr.co.our.domain.RoomInfoDetailImg;
import kr.co.our.domain.RoomInfoImg;
import kr.co.our.domain.RoomPrice;
import kr.co.our.domain.SpaceInfo;
import kr.co.our.service.RoomService;
import kr.co.our.service.SpaceService;
import kr.co.our.util.MediaUtils;
import kr.co.our.util.UploadFileUtils;

@Controller
@RequestMapping("/space/")
public class RoomController {
	
	private static final Logger log = LoggerFactory.getLogger(RoomController.class);
	
	@Value("${upload.space.room.path}")
	private String roomUploadPath;
	
	@Autowired
	private SpaceService spaceService;
	
	@Autowired
	private RoomService roomService;

	// 세부 공간 목록
	@RequestMapping(value ="{spaceNum}/room/manage", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_HOST')")
	public String roomList(@PathVariable("spaceNum") Integer spaceNum, @ModelAttribute("pgrq") PageRequest pageRequest, Model model, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member =  customUser.getMember();
		String hostId = member.getUserId();
		
		
		model.addAttribute("spaceNum", spaceNum);
		
		Map map = new HashMap();
		map.put("spaceNum", spaceNum);
		map.put("hostId", hostId);
		map.put("pageStart", pageRequest.getPageStart());
		map.put("sizePerPage", pageRequest.getSizePerPage());
		map.put("searchType", pageRequest.getSearchType());
		map.put("keyword", pageRequest.getKeyword());
		
		
		List<RoomInfo> roomList = roomService.roomList(map);
		
		model.addAttribute("room", roomService.roomList(map));
		
		
		model.addAttribute("list", roomList);
		model.addAttribute("spaceNum", spaceNum);
		
		Pagination pagination = new Pagination();
		pagination.setPageRequest(pageRequest);
		
		// 페이지 네비게이션 정보에 검색처리된 게시글 건수를 저장한다.
		pagination.setTotalCount(spaceService.spaceCount(pageRequest));
		
		model.addAttribute("pagination", pagination);
		
		return "/space/room/manage";
	}
	
	
	// 룸 기본정보 등록하기 - 화면
	@RequestMapping(value ="{spaceNum}/room/room_info", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_HOST')")
	public String roomInfoRegisterForm(@PathVariable("spaceNum") Integer spaceNum, Model model, Authentication authentication ) throws Exception {
		RoomInfo roomInfo = new RoomInfo();
		roomInfo.setSpaceNum(spaceNum);
		BasicInfo basicInfo = spaceService.basicInfoRead(spaceNum);
		String spaceCategory = basicInfo.getSpaceCategory();
		
		List<String> cateList = new ArrayList<String>();
		if( spaceCategory == null || spaceCategory.length() == 0) {
			
		} else {
			String [] cate = spaceCategory.split(",");
			for (String c : cate) {
				if( c != null)
					cateList.add(c);
			}
		}
		
		
		model.addAttribute("cateList", cateList);
		model.addAttribute(roomInfo);
		
		return "/space/room/room_info";
	}
	
	
	// 룸 기본정보 등록하기 - 등록처리
	@RequestMapping(value = "{spaceNum}/room/room_info", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_HOST')")
	public String roomInfoRegister(@PathVariable("spaceNum") Integer spaceNum, RoomInfo roomInfo, RoomInfoImg roomInfoImg, RoomInfoDetailImg roomInfoDetailImg, RedirectAttributes rttr, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member =  customUser.getMember();
		String hostId = member.getUserId();
		
		roomInfo.setCreatedBy(hostId);
		
		roomService.roomInfoRegister(roomInfo);
		Map map = new HashMap();
		map.put("spaceNum", spaceNum);
		map.put("hostId", hostId);
		Integer roomNum = roomService.roomInfoReadRecent(map);
		RoomPrice roomPrice = new RoomPrice();
		roomPrice.setSpaceNum(spaceNum);
		roomPrice.setRoomNum(roomNum);
		roomPrice.setCreatedBy(hostId);
		
		roomService.roomPriceRegister(roomPrice);
		
		
		// room_info_img 테이블에 룸 사진정보 추가	- #대표 이미지  
		roomInfoImg.setSpaceNum(spaceNum);
		roomInfoImg.setRoomNum(roomNum);
		roomInfoImg.setHostId(hostId);
		roomInfoImg.setImgType("main");
		
		roomService.roomInfoImgRegister(roomInfoImg);
		
		// room_info_img 테이블에 룸 사진정보 추가   - #상세 이미지
		roomInfoDetailImg.setSpaceNum(spaceNum);
		roomInfoDetailImg.setRoomNum(roomNum);
		roomInfoDetailImg.setHostId(hostId);
		roomInfoDetailImg.setImgType("detail");
		
		roomService.roomInfoDetailImgRegister(roomInfoDetailImg);
		
		// space_info 테이블 - room_status : "CHECK" 삽입
		SpaceInfo spaceInfo = new SpaceInfo();
		spaceInfo.setSpaceNum(spaceNum);
		spaceInfo.setRoomStatus("CHECK");
		roomService.roomStatusModify(spaceInfo);
		
		return "redirect:/space/" + spaceNum + "/room/" + roomNum + "/price_info";
	}
	
	
	// 룸 가격정보 수정하기 - 화면
	@RequestMapping(value ="{spaceNum}/room/{roomNum}/price_info", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_HOST')")
	public String priceInfoModifyForm(@PathVariable("spaceNum") Integer spaceNum, @PathVariable("roomNum") Integer roomNum, Model model, Authentication authentication ) throws Exception {		
		Map map = new HashMap();
		map.put("spaceNum", spaceNum);
		map.put("roomNum", roomNum);
		
		RoomPrice roomPrice = roomService.roomPriceRead(map);
		
		model.addAttribute(spaceNum);
		model.addAttribute(roomNum);
		model.addAttribute(roomPrice);
		
		return "/space/room/modify/price_info";
	}
	
	
	// 룸 가격정보 수정하기 - 수정처리
	@RequestMapping(value = "{spaceNum}/room/{roomNum}/price_info", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_HOST')")
	public String priceInfoModify(@PathVariable("spaceNum") Integer spaceNum, @PathVariable("roomNum") Integer roomNum, RoomPrice roomPrice, RedirectAttributes rttr, Authentication authentication ) throws Exception {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member =  customUser.getMember();
		String hostId = member.getUserId();
		
		roomPrice.setUpdatedBy(hostId);
		
		log.info(roomPrice.getRoomNum() + "");
		
		roomService.roomPriceModify(roomPrice);
		
		return "redirect:/space/" + spaceNum + "/room/manage";
	}
	
	
	// 룸 기본정보 수정하기 - 화면
	@RequestMapping(value ="{spaceNum}/room/{roomNum}/room_info", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_HOST')")
	public String roomInfoModifyForm(@PathVariable("spaceNum") Integer spaceNum, @PathVariable("roomNum") Integer roomNum, Model model, Authentication authentication ) throws Exception {		
		Map map = new HashMap();
		map.put("spaceNum", spaceNum);
		map.put("roomNum", roomNum);
		RoomInfo roomInfo = roomService.roomInfoRead(map);
		
		BasicInfo basicInfo  = spaceService.basicInfoRead(spaceNum);
		String spaceCategory = basicInfo.getSpaceCategory();
		String roomCategory  = roomInfo.getRoomCategory();
		
		List<String[]> cateList = new ArrayList<String[]>();
		if( spaceCategory == null || spaceCategory.length() == 0) {
			
		} else {
			String [] sCate = spaceCategory.split(",");
			if( roomCategory == null || roomCategory.length() == 0 ) {
				for(String sC : sCate) {
					String[] allList = { sC, null };
					cateList.add(allList);
				}
			} else {
				String [] rCate = roomCategory.split(",");
				for (String sC : sCate) {
					String[] checkedList = { sC, null };
					cateList.add(checkedList);
				}
				for( int i = 0 ; i < cateList.size() ; i++) {
					for (String rC : rCate) {
						if( cateList.get(i)[0].equals(rC) ){
							cateList.remove(i);
							String[] checkedList = { rC, rC };
							cateList.add(checkedList);
						} else {
							
						}
					}
				}
				
			}
		}
		
		
		// 옵션 유형 나눠주기
		String roomOption = roomInfo.getRoomOption();
		
		if( roomOption == null || roomOption.length() == 0) {
			
		} else {
			String [] cate = roomOption.split(",");
			
			for (String c : cate) {
				switch (c) {
				case "TV/프로젝터": 			roomInfo.setRoomOpt1(c); break;
				case "인터넷/와이파이": 		roomInfo.setRoomOpt2(c); break;
				case "복사/인쇄기": 			roomInfo.setRoomOpt3(c); break;
				case "화이트보드": 			roomInfo.setRoomOpt4(c); break;
				case "음향/마이크": 			roomInfo.setRoomOpt5(c); break;
				case "PC/노트북": 			roomInfo.setRoomOpt6(c); break;
				
				}
			}
		}
		
		
		model.addAttribute("cateList", cateList);		
		
		model.addAttribute(spaceNum);				//  th:vaule="${spaceNum}
		model.addAttribute(roomNum);				
		model.addAttribute(roomInfo);				// th:object="${roomInfo}"     
													// th:value="*{roomInfo.spaceNum}"
		return "/space/room/modify/room_info";
	}
		
	
	// 룸 기본정보 수정하기 - 수정처리
	@RequestMapping(value ="{spaceNum}/room/{roomNum}/room_info", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_HOST')")
	public String roomInfoModify(@PathVariable("spaceNum") Integer spaceNum, @PathVariable("roomNum") Integer roomNum, RoomInfo roomInfo, RoomInfoImg roomInfoImg, RoomInfoDetailImg roomInfoDetailImg, RedirectAttributes rttr, Authentication authentication ) throws Exception {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member =  customUser.getMember();
		String hostId = member.getUserId();
		
		roomInfo.setUpdatedBy(hostId);
		roomInfoImg.setHostId(hostId);
		roomInfoDetailImg.setHostId(hostId);
		
		log.info(roomInfoImg.toString());
		log.info(roomInfoDetailImg.toString());
		
		roomService.roomInfoModify(roomInfo, roomInfoImg, roomInfoDetailImg);
		
		
		return "redirect:/space/" + spaceNum + "/room/" + roomNum + "/price_info";
	}
	
	
	// 룸 정보 삭제하기 - 삭제처리
	@RequestMapping(value ="/room/delete", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_HOST')")
	public String roomInfoRemove(Integer spaceNum, Integer roomNum, RoomInfo roomInfo, RedirectAttributes rttr, Authentication authentication ) throws Exception {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member =  customUser.getMember();
		String hostId = member.getUserId();
		
		log.info("spaceNum : " + spaceNum + "  |  roomNum : " + roomNum);
		
		Map map = new HashMap();
		map.put("spaceNum", spaceNum);
		map.put("roomNum", roomNum);
		
		roomService.roomInfoRemove(map);
		
		
		return "redirect:/space/" + spaceNum + "/room/manage";
	}
	
	
	// (룸 사진정보) 파일업로드 - 비동기 처리
	@ResponseBody
	@RequestMapping(value = "/room/uploadMainImg", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> uploadMainImg(MultipartFile file) throws Exception {
		log.info("originalName : " +  file.getOriginalFilename());
		
		String savedName = UploadFileUtils.uploadFile(roomUploadPath, file.getOriginalFilename(), file.getBytes());
		
		return new ResponseEntity<String>(savedName, HttpStatus.CREATED);
	}
	
	// (룸 사진정보) 파일업로드 - 비동기 처리
	@ResponseBody
	@RequestMapping(value = "/room/uploadDetailImg", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> uploadDetailImg(MultipartFile file) throws Exception {
		log.info("originalName : " +  file.getOriginalFilename());
		
		String savedName = UploadFileUtils.uploadFile(roomUploadPath, file.getOriginalFilename(), file.getBytes());
		
		return new ResponseEntity<String>(savedName, HttpStatus.CREATED);
	}
	
	// (룸 사진 정보) 업로드된 파일 제목/썸네일 미리보기
	@ResponseBody
	@RequestMapping("/room/displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception {
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
	
		log.info("FILE NAME: " + fileName);
		
		try {
			
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
			log.info("FILE FORMAT: " + formatName);
			
			MediaType mType = MediaUtils.getMediaType(formatName);
			
			HttpHeaders headers = new HttpHeaders();
			
			in = new FileInputStream(roomUploadPath + fileName);
			
			if ( mType != null) {
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
	
	// (룸 사진정보) 저장된 파일 불러오기
	@RequestMapping("/room/getAttach/{spaceNum}/{roomNum}")
	@ResponseBody
	public List<RoomInfoImg> getAttach(@PathVariable("spaceNum") Integer spaceNum, @PathVariable("roomNum") Integer roomNum) throws Exception{
		Map map = new HashMap();
		map.put("spaceNum", spaceNum);
		map.put("roomNum", roomNum);
		
		List<RoomInfoImg> roomInfoImgList = roomService.getAttach(map);
		for (RoomInfoImg roomInfoImg : roomInfoImgList) {
			log.info(roomInfoImg.toString());
		}
		
		
		return roomInfoImgList;
	}

}
	 	













