package kr.co.our.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomInfoImg implements Serializable {

	private static final long serialVersionUID = 3129581765502981049L;
	
	private int				roomImgId;
	private int				spaceNum;
	private int				roomNum;
	private String			fullName;
	private String[] 		files;
	private String			imgType;
	private Date 			createdAt;
	private String 			createdBy;
	private Date 			updatedAt;
	private String 			updatedBy;
	
	private String			hostId;

}
