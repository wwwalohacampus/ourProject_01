package kr.co.our.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.our.domain.ReservInfo;
import kr.co.our.mapper.ReserveMapper;


@Service
public class ReserveServiceImpl implements ReserveService {
	
	
	private static final Logger log = LoggerFactory.getLogger(ReserveServiceImpl.class);


	@Autowired
	private ReserveMapper mapper;
	

	@Override
	public void reserveInfoRegister(ReservInfo reserveInfo) throws Exception {
		mapper.reserveInfoCreate(reserveInfo);
	}


	@Override
	public ReservInfo reserveInfoReadRecent(Map map) throws Exception {
		System.out.println("map - spaceNum : " +  map.get("spaceNum"));
		return mapper.reserveInfoReadRecent(map);
	}
	
	
	
	

}
