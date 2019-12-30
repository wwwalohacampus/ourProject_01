package kr.co.our.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservInfo implements Serializable {

	private static final long serialVersionUID = -7803217228700424280L;
	
	private int				reservNum;
	private int				spaceNum;
	private int				roomNum;
	private String			reservDate;
	private int				startTime;
	private int				endTime;
	private String			timeList;
	private int				totalTime;
	private int				reservMan;
	private int				totalPay;
	private String			payType;
	private String			userId;
	private String			userName;
	private String 			userTel;
	private String			userEmail;
	private String			userReq;
	private String			hostId;

	private Date 			createdAt;
	private String 			createdBy;
	private Date 			updatedAt;
	private String 			updatedBy;
	
	

}
