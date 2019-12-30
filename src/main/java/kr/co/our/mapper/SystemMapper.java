package kr.co.our.mapper;

import java.util.List;

import kr.co.our.domain.SpaceInfo;

public interface SystemMapper {
	
	public List<SpaceInfo> spaceInfoList(String checkStatus) throws Exception;

	public void checkStatusUpdate(Integer spaceNum) throws Exception;
}
