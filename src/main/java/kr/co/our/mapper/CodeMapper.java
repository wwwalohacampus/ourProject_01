package kr.co.our.mapper;
import java.util.List;

import kr.co.our.common.domain.CodeLabelValue;


public interface CodeMapper {
	
	public List<CodeLabelValue> getCodeClassList() throws Exception;

	public List<CodeLabelValue> getCodeList(String classCode) throws Exception;
	
}
