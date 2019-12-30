package kr.co.our.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 	-- 회원 게시판 테이블
	CREATE TABLE board (
		board_no 	INT 			NOT NULL	AUTO_INCREMENT,
	    title		VARCHAR(200)	NOT NULL,
	    content		TEXT,
		writer		VARCHAR(50)		NOT NULL,
	    reg_date	TIMESTAMP		NOT NULL 	DEFAULT now(),
	    PRIMARY KEY (board_no)
	);

 */

// lombok 라이브러리 이용해서 생성자 + getter + setter 생성하기

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board implements Serializable {
	
	private static final long serialVersionUID = -6721989547209234511L;
	
	private int boardNo;
	private String title;
	private String content;
	private String writer;			// <-- setter 를 통해서 로그인한 userId 를 넣어준다.
	private Date regDate;
	
	// 페이지요청 정보를 멤버변수로 선언한다.
//	private PageRequest pageRequest;
	
	// 페이지요청 멤버변수를 사용하여 페이징정보를 반환한다.
	/*
	public int getPage() {
		return pageRequest.getPage();
	}
	
	public int getSizePerPage() {
		return pageRequest.getSizePerPage();
	}
	
	// 페이지요청 멤버변수의 Getter/Setter 메서드를 정의한다.
	public PageRequest getPageRequest() {
		return pageRequest;
	}
	
	public void setPageRequest(PageRequest pageRequest) {
		this.pageRequest = pageRequest;
	}
	*/
	
	
	
	
	
}

















