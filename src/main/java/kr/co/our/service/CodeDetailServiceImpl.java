package kr.co.our.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.our.domain.CodeDetail;
import kr.co.our.mapper.CodeDetailMapper;

@Service
public class CodeDetailServiceImpl implements CodeDetailService {

	@Autowired
	private CodeDetailMapper mapper;

	@Override
	public void register(CodeDetail codeDetail) throws Exception {
		String classCode = codeDetail.getClassCode();
		int maxSortSeq = mapper.getMaxSortSeq(classCode);
		
		System.out.println("@CodeDetailServiceImpl --  register()  -- classCode : " + classCode );
		System.out.println("@CodeDetailServiceImpl --  register()  -- maxSortSeq : " + maxSortSeq );
		
		codeDetail.setSortSeq(maxSortSeq + 1);
		
		mapper.create(codeDetail);
	}

	@Override
	public CodeDetail read(CodeDetail codeDetail) throws Exception {
		return mapper.read(codeDetail);
	}

	@Override
	public void modify(CodeDetail codeDetail) throws Exception {
		mapper.update(codeDetail);
	}

	@Override
	public void remove(CodeDetail codeDetail) throws Exception {
		mapper.delete(codeDetail);
	}

	@Override
	public List<CodeDetail> list() throws Exception {
		return mapper.list();
	}

}
