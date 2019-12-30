package kr.co.our.mapper;

import java.util.List;
import java.util.Map;

import kr.co.our.domain.RoomInfo;
import kr.co.our.domain.RoomInfoDetailImg;
import kr.co.our.domain.RoomInfoImg;
import kr.co.our.domain.RoomPrice;
import kr.co.our.domain.SpaceBasicImg;
import kr.co.our.domain.SpaceInfo;

public interface RoomMapper {
	
	public List<RoomInfo> roomList (Map map) throws Exception;
	
	public void roomInfoCreate(RoomInfo roomInfo) throws Exception;
	
	public void roomPriceCreate(RoomPrice roomPrice) throws Exception;
	
	public Integer roomInfoReadRecent(Map map) throws Exception;
	
	public void roomPriceUpdate(RoomPrice roomPrice) throws Exception;
	
	public RoomPrice roomPriceRead(Map map) throws Exception;
	
	public RoomInfo roomInfoRead(Map map) throws Exception;
	
	public void roomInfoUpdate(RoomInfo roomInfo) throws Exception;
	
	public void roomInfoDelete(Map map) throws Exception;
	
	public void roomInfoImgCreate(RoomInfoImg roomInfoImg) throws Exception;
	
	public void roomInfoDetailImgCreate(RoomInfoDetailImg roomInfoDetailImg) throws Exception;
	
	public List<RoomInfoImg> getAttach(Map map) throws Exception;
	
	public void replaceAttach(RoomInfoImg roomInfoImg) throws Exception;
	
	public void replaceDetailAttach(RoomInfoDetailImg roomInfoDetailImg) throws Exception;
	
	public void deleteAttach(Map map) throws Exception;
	
	public void roomStatusUpdate(SpaceInfo spaceInfo) throws Exception;
	
	public List<RoomInfo> roomListwithPrice(Map map) throws Exception;

	public RoomInfo roomWithPrice(Map map) throws Exception;
	
}
