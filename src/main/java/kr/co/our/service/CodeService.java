package kr.co.our.service;

import java.util.List;

import kr.co.our.common.domain.CodeLabelValue;


public interface CodeService {

	public List<CodeLabelValue> getCodeClassList() throws Exception;

	public List<CodeLabelValue> getCodeList(String classCode) throws Exception;

}
