package kr.co.our.mapper;

import java.util.List;

import kr.co.our.domain.Board;
import kr.co.our.domain.PageRequest;

public interface BoardMapper {
	
	public void create(Board board) throws Exception;
	
	public List<Board> list() throws Exception;
	
	public Board read(Integer boardNo) throws Exception;
	
	public void update(Board board) throws Exception;
	
	public void delete(Integer boardNo) throws Exception;
	
	// 페이지요청 정보를 매개변수로 받아 페이징 처리를 한 게시글 목록을 반환한다.
	public List<Board> list(PageRequest pageRequest) throws Exception;
	
	// 게시글 전체 건수를 반환한다.
	public int count(PageRequest pageRequest) throws Exception;
}
