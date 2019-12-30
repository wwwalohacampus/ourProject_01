package kr.co.our.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundInfo implements Serializable {
	
	private static final long serialVersionUID = -2587879267801919851L;
	
	private int			spaceNum;
	private String		ddayOne;
	private String		ddayTwo;
	private String		ddayThree;
	private String		ddayFour;
	private String		ddayFive;
	private String		ddaySix;
	private String		ddaySeven;
	private String		ddayEight;
	private String		dday;
	private Date		createdAt;
	private String		createdBy;
	private Date		updatedAt;
	private String		updatedBy;
	
	private Integer		spaceId;
	private String 		refundHostId;
	
}
