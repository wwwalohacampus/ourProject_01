package kr.co.our.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpaceBasicImg implements Serializable {
	
	private static final long serialVersionUID = 1540456903334619411L;

	private int				spaceImgId;
	private int 			spaceNum;
	private String			fullName;
	private String[] 		files;
	private Date 			createdAt;
	private String 			createdBy;
	private Date 			updatedAt;
	private String 			updatedBy;
	
	private String			hostId;
	
}
