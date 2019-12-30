package kr.co.our.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactsInfo implements Serializable {
	
	private static final long serialVersionUID = -5911188683696051081L;
	
	private String 		hostId;
	private int 		spaceNum;
	private String 		spaceEmail;
	private String 		spaceEmail1;
	private String 		spaceEmail2;
	private String		spaceCell;
	private	String		spaceTel;
	private Date		createdAt;
	private String 		createdBy;
	private Date		updatedAt;
	private String 		updatedBy;
	
	private Integer		spaceId;
	
	private String 		postType;
	
		
}
