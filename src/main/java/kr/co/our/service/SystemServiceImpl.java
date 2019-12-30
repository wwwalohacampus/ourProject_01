package kr.co.our.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.our.domain.SpaceInfo;
import kr.co.our.mapper.SystemMapper;

@Service
public class SystemServiceImpl implements SystemService {

	@Autowired
	private SystemMapper mapper;
	
	@Override
	public List<SpaceInfo> spaceInfoList(String checkStatus) throws Exception {
		return mapper.spaceInfoList(checkStatus);
	}

	@Override
	public void checkStatusModify(Integer spaceNum) throws Exception {
		mapper.checkStatusUpdate(spaceNum);
	}

	
	
}
