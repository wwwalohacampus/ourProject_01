package kr.co.our.service;

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

public interface SpaceService {

	public void basicInfoRegister(BasicInfo basicInfo) throws Exception;
	
	public List<BasicInfo> basicInfoList(Map map) throws Exception;
	
	public void spaceInfoRegister(SpaceInfo spaceInfo) throws Exception;
	
	public SpaceInfo spaceInfoRead(Integer spaceNum) throws Exception;
	
	public BasicInfo basicInfoRead(Integer spaceNum) throws Exception;
	
	public int spaceCountAll() throws Exception;
	
	public int spaceInfoReadSpaceId(int spaceNum) throws Exception;
	
	public int spaceInfoReadSpaceNum(int spaceId) throws Exception;
	
	public void contactsInfoRegister(ContactsInfo contactsInfo) throws Exception;
	
	public void usageInfoRegister(UsageInfo usageInfo) throws Exception;
	
	public void accountsInfoRegister(AccountsInfo accountsInfo) throws Exception;
	
	public void refundInfoRegister(RefundInfo refundInfo) throws Exception;
	
	public void basicInfoModify(BasicInfo basicInfo, SpaceBasicImg  spaceBasicImg) throws Exception;
	
	public ContactsInfo contactsInfoRead(Integer spaceNum) throws Exception;
	
	public void contactsInfoModify(ContactsInfo contactsInfo) throws Exception;
	
	public UsageInfo usageInfoRead(Integer spaceNum) throws Exception;
	
	public void usageInfoModify(UsageInfo usageInfo) throws Exception;
	
	public AccountsInfo accountsInfoRead(Integer spaceNum) throws Exception;
	
	public RefundInfo refundInfoRead(Integer spaceNum) throws Exception;
	
	public void accountsInfoModify(AccountsInfo accountsInfo) throws Exception;
	
	public void refundInfoModify(RefundInfo refundInfo) throws Exception;
	
	public void spaceInfoRemove(Integer spaceNum) throws Exception;
	
	public void contactsStatusModify(Integer spaceNum) throws Exception;
	
	public void usageStatusModify(Integer spaceNum) throws Exception;
	
	public void accountsStatusModify(Integer spaceNum) throws Exception;
	
	public void refundStatusModify(Integer spaceNum) throws Exception;
	
	public void spaceNameModify(SpaceInfo spaceInfo) throws Exception;
	
	public void spaceImgRegister(SpaceBasicImg spaceBasicImg) throws Exception;
	
	public List<String> getAttach(Integer spaceNum) throws Exception;
	
	public void modifyFile(SpaceBasicImg spaceBasicImg) throws Exception;
	
	public int spaceCount(PageRequest pageRequest) throws Exception;
	
	public void modifyCheckStatus(SpaceInfo spaceInfo) throws Exception;
	
	public void modifyPublicStatus(SpaceInfo spaceInfo) throws Exception;
	
	public SpaceBasicImg spaceBasicImgRead (Integer spaceNum) throws Exception;
	
 }








