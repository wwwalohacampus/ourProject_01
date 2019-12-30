package kr.co.our.service;

import java.util.Map;

import kr.co.our.domain.ReservInfo;

public interface ReserveService {
		
	public void reserveInfoRegister(ReservInfo reserveInfo) throws Exception;
	
	public ReservInfo reserveInfoReadRecent(Map map) throws Exception;
	
}
