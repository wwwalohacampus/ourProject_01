package kr.co.our.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.our.domain.AccountsInfo;
import kr.co.our.domain.BasicInfo;
import kr.co.our.domain.ContactsInfo;
import kr.co.our.domain.PageRequest;
import kr.co.our.domain.RefundInfo;
import kr.co.our.domain.SpaceBasicImg;
import kr.co.our.domain.SpaceInfo;
import kr.co.our.domain.UsageInfo;
import kr.co.our.mapper.SpaceMapper;


@Service
public class SpaceServiceImpl implements SpaceService {

	@Autowired
	private SpaceMapper mapper;

	@Override
	public void basicInfoRegister(BasicInfo basicInfo) throws Exception {
		mapper.basicInfoCreate(basicInfo);
	}

	@Override
	public List<BasicInfo> basicInfoList(Map map) throws Exception {
		return mapper.basicInfoList(map);
	}

	@Override
	public void spaceInfoRegister(SpaceInfo spaceInfo) throws Exception {
		mapper.spaceInfoCreate(spaceInfo);
	}

	@Override
	public SpaceInfo spaceInfoRead(Integer spaceNum) throws Exception {
		return mapper.spaceInfoRead(spaceNum);
	}

	@Override
	public int spaceCountAll() throws Exception {
		return mapper.spaceCountAll();
	}

	@Override
	public int spaceInfoReadSpaceId(int spaceNum) throws Exception {
		return mapper.spaceInfoReadSpaceId(spaceNum);
	}

	@Override
	public int spaceInfoReadSpaceNum(int spaceId) throws Exception {
		return mapper.spaceInfoReadSpaceNum(spaceId);
	}

	@Override
	public void contactsInfoRegister(ContactsInfo contactsInfo) throws Exception {
		mapper.contactsInfoCreate(contactsInfo);
	}

	@Override
	public void usageInfoRegister(UsageInfo usageInfo) throws Exception {
		mapper.usageInfoCreate(usageInfo);
	}

	@Override
	public void accountsInfoRegister(AccountsInfo accountsInfo) throws Exception {
		mapper.accountsInfoCreate(accountsInfo);
	}

	@Override
	public void refundInfoRegister(RefundInfo refundInfo) throws Exception {
		mapper.refundInfoCreate(refundInfo);
	}

	@Override
	public BasicInfo basicInfoRead(Integer spaceNum) throws Exception {
		return mapper.basicInfoRead(spaceNum);
	}

	@Override
	public void basicInfoModify(BasicInfo basicInfo, SpaceBasicImg spaceBasicImg) throws Exception {
		mapper.basicInfoUpdate(basicInfo);
		
		Integer spaceNum = basicInfo.getSpaceNum();

		mapper.deleteAttach(spaceNum);
		
		String[] files = spaceBasicImg.getFiles();
		String hostId = spaceBasicImg.getHostId();
		
		if(files == null) {
			return;
		}
		
		for(String fileName : files) {
			spaceBasicImg.setFullName(fileName);
			mapper.replaceAttach(spaceBasicImg);
		}
		
	}

	@Override
	public ContactsInfo contactsInfoRead(Integer spaceNum) throws Exception {
		return mapper.contactsInfoRead(spaceNum);
	}
	
	@Override
	public void contactsInfoModify(ContactsInfo contactsInfo) throws Exception {
		mapper.contactsInfoUpdate(contactsInfo);
	}
	
	@Override
	public UsageInfo usageInfoRead(Integer spaceNum) throws Exception {
		return mapper.usageInfoRead(spaceNum);
	}
	
	@Override
	public void usageInfoModify(UsageInfo usageInfo) throws Exception {
		mapper.usageInfoUpdate(usageInfo);
	}
	
	@Override
	public AccountsInfo accountsInfoRead(Integer spaceNum) throws Exception {
		return mapper.accountsInfoRead(spaceNum);
	}

	@Override
	public RefundInfo refundInfoRead(Integer spaceNum) throws Exception {
		return mapper.refundInfoRead(spaceNum);
	}

	@Override
	public void accountsInfoModify(AccountsInfo accountsInfo) throws Exception {
		mapper.accountsInfoUpdate(accountsInfo);
	}

	@Override
	public void refundInfoModify(RefundInfo refundInfo) throws Exception {
		mapper.refundInfoUpdate(refundInfo);
	}

	@Override
	public void spaceInfoRemove(Integer spaceNum) throws Exception {
		mapper.spaceInfoDelete(spaceNum);
	}

	@Override
	public void contactsStatusModify(Integer spaceNum) throws Exception {
		mapper.contactsStatusUpdate(spaceNum);
	}

	@Override
	public void usageStatusModify(Integer spaceNum) throws Exception {
		mapper.usageStatusUpdate(spaceNum);
	}

	@Override
	public void accountsStatusModify(Integer spaceNum) throws Exception {
		mapper.accountsStatusUpdate(spaceNum);
	}

	@Override
	public void refundStatusModify(Integer spaceNum) throws Exception {
		mapper.refundStatusUpdate(spaceNum);
	}

	@Override
	public void spaceNameModify(SpaceInfo spaceInfo) throws Exception {
		mapper.spaceNameUpdate(spaceInfo);
	}

	@Override
	public void spaceImgRegister(SpaceBasicImg spaceBasicImg) throws Exception {
		String[] files = spaceBasicImg.getFiles();
		
		if(files == null) {
			return;
		}
		
		for(String fileName : files) {
			spaceBasicImg.setFullName(fileName);
			mapper.spaceImgCreate(spaceBasicImg);
		}
		
	}

	@Override
	public List<String> getAttach(Integer spaceNum) throws Exception {
		return mapper.getAttach(spaceNum);
	}

	@Override
	public void modifyFile(SpaceBasicImg spaceBasicImg) throws Exception {
		mapper.updateFile(spaceBasicImg);
	}

	// 검색처리된 게시글 건수를 반환한다.
	@Override
	public int spaceCount(PageRequest pageRequest) throws Exception {
		return mapper.spaceCount(pageRequest);
	}

	@Override
	public void modifyCheckStatus(SpaceInfo spaceInfo) throws Exception {
		mapper.updateCheckStatus(spaceInfo);
	}

	@Override
	public void modifyPublicStatus(SpaceInfo spaceInfo) throws Exception {
		mapper.updatePublicStatus(spaceInfo);
	}

	@Override
	public SpaceBasicImg spaceBasicImgRead(Integer spaceNum) throws Exception {
		return mapper.spaceBasicImgRead(spaceNum);
	}

	
	
	
	
	

	
}
