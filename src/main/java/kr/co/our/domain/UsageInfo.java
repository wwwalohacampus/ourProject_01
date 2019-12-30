package kr.co.our.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsageInfo implements Serializable {
	
	private static final long serialVersionUID = 4219419985071787170L;
	
	private int 		spaceNum;
	private String		alldayYn;
	private String		startHour;
	private String		endHour;
	private String		holidayYn;
	private String		holiday;
	private String		holidayWeek;
	private String  	holidayWeek0;
	private String  	holidayWeek1;
	private String  	holidayWeek2;
	private String  	holidayWeek3;
	private String  	holidayWeek4;
	private String  	holidayWeek5;
	private String  	holidayWeek6;
	private String		holidayDate;
	private Date		createdAt;
	private String		createdBy;
	private Date		updatedAt;
	private String		updatedBy;
	
	private Integer		spaceId;
	private String		hostId;
	
	private String 		postType;
	
}
