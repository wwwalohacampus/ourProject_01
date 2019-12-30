package kr.co.our.common.security;

import kr.co.our.common.security.domain.CustomUser;
import kr.co.our.domain.Member;
import kr.co.our.mapper.MemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	private MemberMapper memberMapper;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		logger.warn("Load User By UserName : " + userName);

		// userName means userid
		Member member = memberMapper.readByUserId(userName);

		logger.warn("queried by member mapper: " + member);

		return member == null ? null : new CustomUser(member);
	} 

}
