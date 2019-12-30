package kr.co.our.mapper;

import java.util.List;
import java.util.Map;

import kr.co.our.domain.BasicInfo;

public interface HomeMapper {
	
	public List<BasicInfo> basicInfoList(Map map) throws Exception;
}
