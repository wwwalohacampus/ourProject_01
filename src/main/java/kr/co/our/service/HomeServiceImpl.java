package kr.co.our.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.our.domain.BasicInfo;
import kr.co.our.mapper.HomeMapper;

@Service
public class HomeServiceImpl implements HomeService {

	@Autowired
	private HomeMapper mapper;
	
	@Override
	public List<BasicInfo> basicInfoList(Map map) throws Exception {
		return mapper.basicInfoList(map);
	}

	
}
