package kr.co.our.service;

import java.util.List;
import java.util.Map;

import kr.co.our.domain.RoomInfo;
import kr.co.our.domain.RoomInfoDetailImg;
import kr.co.our.domain.RoomInfoImg;
import kr.co.our.domain.RoomPrice;
import kr.co.our.domain.SpaceInfo;

public interface RoomService {

	public List<RoomInfo> roomList(Map map) throws Exception;
	
	public void roomInfoRegister(RoomInfo roomInfo) throws Exception;
	
	public void roomPriceRegister(RoomPrice roomPrice) throws Exception;
	
	public Integer roomInfoReadRecent(Map map) throws Exception;
	
	public void roomPriceModify(RoomPrice roomPrice) throws Exception;
	
	public RoomPrice roomPriceRead(Map map) throws Exception;
	
	public RoomInfo roomInfoRead(Map map) throws Exception;
	
	public void roomInfoModify(RoomInfo roomInfo, RoomInfoImg roomInfoImg, RoomInfoDetailImg roomInfoDetailImg) throws Exception;
	
	public void roomInfoRemove(Map map) throws Exception;
	
	public void roomInfoImgRegister(RoomInfoImg roomInfoImg) throws Exception;
	
	public void roomInfoDetailImgRegister(RoomInfoDetailImg roomInfoDetailImg) throws Exception;
	
	public List<RoomInfoImg> getAttach(Map map) throws Exception;
	
	public void roomStatusModify(SpaceInfo spaceInfo) throws Exception;
	
	public List<RoomInfo> roomListwithPrice(Map map) throws Exception;

	public RoomInfo roomWithPrice(Map map) throws Exception;
	
}
