package kr.co.our.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.our.common.domain.CodeLabelValue;
import kr.co.our.mapper.CodeMapper;

@Service
public class CodeServiceImpl implements CodeService {

	@Autowired
	private CodeMapper mapper;

	@Override
	public List<CodeLabelValue> getCodeClassList() throws Exception {
		return mapper.getCodeClassList();
	}

	@Override
	public List<CodeLabelValue> getCodeList(String classCode) throws Exception {
		return mapper.getCodeList(classCode);
	}

}
