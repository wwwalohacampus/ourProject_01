package kr.co.our.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpaceInfo implements Serializable {

	private static final long serialVersionUID = -3679929747842274622L;
	
	private int	 			spaceId;
	private String			spaceName;
	private int				spaceNum;
	private String			basicStatus;
	private String			contactsStatus;
	private String			usageStatus;
	private String			accountsStatus;
	private String			refundStatus;
	private String 			roomStatus;
	private String			checkStatus;
	private String			publicStatus;
	private Date 			createdAt;
	private String 			createdBy;
	private Date 			updatedAt;
	private String 			updatedBy;
}
