package kr.co.our.mapper;
import java.util.List;

import kr.co.our.domain.Member;
import kr.co.our.domain.MemberAuth;


public interface MemberMapper {

	public Member readByUserId(String userId);
	
	public void create(Member member) throws Exception;

	public Member read(int userNo) throws Exception;

	public void update(Member member) throws Exception;

	public void delete(int userNo) throws Exception;

	public List<Member> list() throws Exception;

	public void createAuth(MemberAuth memberAuth) throws Exception;

	public void deleteAuth(int userNo) throws Exception;

	public int countAll() throws Exception;
	
	
}
