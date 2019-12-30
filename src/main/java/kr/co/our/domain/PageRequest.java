package kr.co.our.domain;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PageRequest {
	
	private int page;
	private int sizePerPage;
	
	// 검색유형과 검색어를 멤버변수로 선언한다.
	private String searchType;
	private String keyword;
	
	public PageRequest() {
		this.page = 1;
		this.sizePerPage = 9;
	}
	
	public void setPage(int page) {
		if(page <= 0)  {
			this.page = 1;
			return;
		}
		
		this.page = page;
	}
	
	public void setSizePerPage(int size) {
		if(size <= 0 || size > 100) {
			this.sizePerPage = 3;
			return;
		}
		
		this.sizePerPage = size;
	}
	
	
	public int getPage() {
		return page;
	}
	
	// 마이바티스 SQL 매퍼를 위한 메서드
	public int getPageStart() {
		return (this.page - 1) * sizePerPage;
	}
	
	// 마이바티스 SQL 매퍼를 위한 메서드
	public int getSizePerPage() {
		return this.sizePerPage;
	}
	
	// 검색유형과 검색어를 멤버변수 Getter/Setter 메서드
	public String getSearchType() {
		return searchType;
	}
	
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	
	public String getKeyword() {
		return keyword;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	// 멤버변수를 활용하여 다양한 형태의 쿼리파라미터를 생성한다.
	public String toUriString() {
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
										.queryParam("page", this.page)
										.queryParam("size", this.sizePerPage)
										.queryParam("searchType", this.searchType)
										.queryParam("keyword", this.keyword)
										.build();
		
		return uriComponents.toUriString();
	}
	
	
	public String toUriString(int page) {
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
										.queryParam("page", page)
										.queryParam("size", this.sizePerPage)
										.queryParam("searchType", this.searchType)
										.queryParam("keyword", this.keyword)
										.build();
		
		return uriComponents.toUriString();
	}
	
	public String toUriStringByPage(int page) {
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
										.queryParam("page", page)
										.queryParam("size", this.sizePerPage)
										.build();
		
		return uriComponents.toUriString();
	}
}








