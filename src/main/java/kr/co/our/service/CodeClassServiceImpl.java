package kr.co.our.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.our.domain.CodeClass;
import kr.co.our.mapper.CodeClassMapper;

@Service
public class CodeClassServiceImpl implements CodeClassService {

	@Autowired
	private CodeClassMapper mapper;

	@Override
	public void register(CodeClass codeClass) throws Exception {
		mapper.create(codeClass);
	}

	@Override
	public CodeClass read(String classCode) throws Exception {
		return mapper.read(classCode);
	}

	@Override
	public void modify(CodeClass codeClass) throws Exception {
		mapper.update(codeClass);
	}

	@Override
	public void remove(String classCode) throws Exception {
		mapper.delete(classCode);
	}

	@Override
	public List<CodeClass> list() throws Exception {
		return mapper.list();
	}

}
