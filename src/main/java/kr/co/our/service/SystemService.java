package kr.co.our.service;

import java.util.List;

import kr.co.our.domain.SpaceInfo;

public interface SystemService {

	public List<SpaceInfo> spaceInfoList(String checkStatus) throws Exception;
	
	public void checkStatusModify(Integer spaceNum) throws Exception;
	
}
