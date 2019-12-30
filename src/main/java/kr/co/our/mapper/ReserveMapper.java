package kr.co.our.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.our.domain.ReservInfo;

public interface ReserveMapper {

	public void reserveInfoCreate(ReservInfo reserveInfo) throws Exception;
	
	public ReservInfo reserveInfoReadRecent(Map map) throws Exception;
}
