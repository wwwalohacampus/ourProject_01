package kr.co.our.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.our.domain.RoomInfo;
import kr.co.our.domain.RoomInfoDetailImg;
import kr.co.our.domain.RoomInfoImg;
import kr.co.our.domain.RoomPrice;
import kr.co.our.domain.SpaceInfo;
import kr.co.our.mapper.RoomMapper;


@Service
public class RoomServiceImpl implements RoomService {
	
	
	private static final Logger log = LoggerFactory.getLogger(RoomServiceImpl.class);


	@Autowired
	private RoomMapper mapper;
	
	@Override
	public List<RoomInfo> roomList(Map map) throws Exception {
		return mapper.roomList(map);
	}

	@Override
	public void roomInfoRegister(RoomInfo roomInfo) throws Exception {
		mapper.roomInfoCreate(roomInfo);
	}

	@Override
	public void roomPriceRegister(RoomPrice roomPrice) throws Exception {
		mapper.roomPriceCreate(roomPrice);
	}

	@Override
	public Integer roomInfoReadRecent(Map map) throws Exception {
		return mapper.roomInfoReadRecent(map);
	}

	@Override
	public void roomPriceModify(RoomPrice roomPrice) throws Exception {
		mapper.roomPriceUpdate(roomPrice);
	}

	@Override
	public RoomPrice roomPriceRead(Map map) throws Exception {
		return mapper.roomPriceRead(map);
	}

	@Override
	public RoomInfo roomInfoRead(Map map) throws Exception {
		return mapper.roomInfoRead(map);
	}

	@Override
	public void roomInfoModify(RoomInfo roomInfo, RoomInfoImg roomInfoImg, RoomInfoDetailImg roomInfoDetailImg) throws Exception {
		mapper.roomInfoUpdate(roomInfo);
		
		Integer spaceNum = roomInfo.getSpaceNum();
		Integer roomNum  = roomInfo.getRoomNum();
		
		Map map = new HashMap();
		map.put("spaceNum", spaceNum);
		map.put("roomNum", roomNum);
		
		mapper.deleteAttach(map);
		
		String[] files 	= roomInfoImg.getFiles();
		
		// 메인 이미지
		if(files == null) {
			
		} 
		else {
			
			for (String fileName : files) {
				roomInfoImg.setFullName(fileName);
				roomInfoImg.setImgType("main");
				mapper.replaceAttach(roomInfoImg);
			}
		}
		
		
		// 상세 이미지
		files = roomInfoDetailImg.getDetailFiles();
		if(files == null) {
			
		}
		else {
			
			for (String fileName : files) {
				roomInfoDetailImg.setFullName(fileName);
				roomInfoDetailImg.setImgType("detail");
				mapper.replaceDetailAttach(roomInfoDetailImg);
			}
		}
		
		
	}

	@Override
	public void roomInfoRemove(Map map) throws Exception {
		mapper.roomInfoDelete(map);
	}

	@Override
	public void roomInfoImgRegister(RoomInfoImg roomInfoImg) throws Exception {
		String[] files = roomInfoImg.getFiles();
		
		if(files == null) {
			return;
		}
		
		for(String fullName : files) {
			roomInfoImg.setFullName(fullName);
			mapper.roomInfoImgCreate(roomInfoImg);
		}
		
	}

	@Override
	public void roomInfoDetailImgRegister(RoomInfoDetailImg roomInfoDetailImg) throws Exception {
		String[] files = roomInfoDetailImg.getDetailFiles();
		
		if(files == null) {
			return;
		}
		
		for(String fullName : files) {
			roomInfoDetailImg.setFullName(fullName);
			mapper.roomInfoDetailImgCreate(roomInfoDetailImg);
		}
	}

	@Override
	public List<RoomInfoImg> getAttach(Map map) throws Exception {
		return mapper.getAttach(map);
	}

	@Override
	public void roomStatusModify(SpaceInfo spaceInfo) throws Exception {
		mapper.roomStatusUpdate(spaceInfo);
	}

	@Override
	public List<RoomInfo> roomListwithPrice(Map map) throws Exception {
		Calendar cal = Calendar.getInstance(); 
		SimpleDateFormat formatter =new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
		 
		//현재 일자의 요일
		Date currentTime = new Date();
		String dTime = formatter.format(currentTime);
		cal.setTime(formatter.parse(dTime));
		int dayNum = cal.get(Calendar.DAY_OF_WEEK) - 1;
  	    String[] weekDay = { "일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일" };
  	    
  	    log.info(weekDay[dayNum]);
  	    
  	    map.put("weekDay", weekDay[dayNum]);
  	    
		return mapper.roomListwithPrice(map);
	}

	@Override
	public RoomInfo roomWithPrice(Map map) throws Exception {
		Calendar cal = Calendar.getInstance(); 
		SimpleDateFormat formatter =new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
		 
		//현재 일자의 요일
		Date currentTime = new Date();
		String dTime = formatter.format(currentTime);
		cal.setTime(formatter.parse(dTime));
		int dayNum = cal.get(Calendar.DAY_OF_WEEK) - 1;
  	    String[] weekDay = { "일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일" };
  	    
  	    log.info(weekDay[dayNum]);
  	    
  	    map.put("weekDay", weekDay[dayNum]);
		
		return mapper.roomWithPrice(map);
	}
	
	
	
	
	
	
	
}
