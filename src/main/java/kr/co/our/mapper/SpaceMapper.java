package kr.co.our.mapper;

import java.util.List;
import java.util.Map;

import kr.co.our.domain.AccountsInfo;
import kr.co.our.domain.BasicInfo;
import kr.co.our.domain.ContactsInfo;
import kr.co.our.domain.PageRequest;
import kr.co.our.domain.RefundInfo;
import kr.co.our.domain.SpaceBasicImg;
import kr.co.our.domain.SpaceInfo;
import kr.co.our.domain.UsageInfo;

public interface SpaceMapper {
	
	public void basicInfoCreate(BasicInfo basicInfo) throws Exception;
	
	public List<BasicInfo> basicInfoList(Map map) throws Exception;
	
	public void spaceInfoCreate(SpaceInfo spaceInfo) throws Exception;
	
	public SpaceInfo spaceInfoRead(Integer spaceNum) throws Exception;
	
	public BasicInfo basicInfoRead(Integer spaceNum) throws Exception;
	
	public int spaceCountAll() throws Exception;
	
	public int spaceInfoReadSpaceId(int spaceNum) throws Exception;
	
	public int spaceInfoReadSpaceNum(int spaceId) throws Exception;
	
	public void contactsInfoCreate(ContactsInfo contactsInfo) throws Exception;
	
	public void usageInfoCreate(UsageInfo usageInfo) throws Exception;
	
	public void accountsInfoCreate(AccountsInfo accountsInfo) throws Exception;
	
	public void refundInfoCreate(RefundInfo refundInfo) throws Exception;
	
	public void basicInfoUpdate(BasicInfo basicInfo) throws Exception;
	
	public ContactsInfo contactsInfoRead(Integer spaceNum) throws Exception;
	
	public void contactsInfoUpdate(ContactsInfo contactsInfo) throws Exception;
	
	public UsageInfo usageInfoRead(Integer spaceNum) throws Exception;
	
	public void usageInfoUpdate(UsageInfo usageInfo) throws Exception;
	
	public AccountsInfo accountsInfoRead(Integer spaceNum) throws Exception;
	
	public RefundInfo refundInfoRead(Integer spaceNum) throws Exception;
	
	public void accountsInfoUpdate(AccountsInfo accountsInfo) throws Exception;
	
	public void refundInfoUpdate(RefundInfo refundInfo) throws Exception;
	
	public void spaceInfoDelete(Integer spaceNum) throws Exception;
	
	public void contactsStatusUpdate(Integer spaceNum) throws Exception;
	
	public void usageStatusUpdate(Integer spaceNum) throws Exception;
	
	public void accountsStatusUpdate(Integer spaceNum) throws Exception;
	
	public void refundStatusUpdate(Integer spaceNum) throws Exception;
	
	public void spaceNameUpdate(SpaceInfo spaceInfo) throws Exception;
	
	public void spaceImgCreate(SpaceBasicImg spaceBasicImg) throws Exception;
	
	public List<String> getAttach(Integer spaceNum) throws Exception;
	
	public void updateFile(SpaceBasicImg spaceBasicImg) throws Exception;
	
	public void deleteAttach(Integer spaceNum) throws Exception;
	
	public void replaceAttach(SpaceBasicImg spaceBasicImg) throws Exception;
	
	// 검색처리된 게시글의 건수를 반환한다.
	public int spaceCount(PageRequest pageRequest) throws Exception;
	
	public void updateCheckStatus(SpaceInfo spaceInfo) throws Exception;
	
	public void updatePublicStatus(SpaceInfo spaceInfo) throws Exception;
	
	public SpaceBasicImg spaceBasicImgRead(Integer spaceNum) throws Exception;
}




