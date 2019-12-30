package kr.co.our.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomInfo implements Serializable {

	private static final long serialVersionUID = -1090013707683829639L;
	
	private int			spaceNum;
	private int			roomNum;
	private String		roomName;
	private String		roomIntro;
	private String		roomCategory;
	private String		roomOption;
	private String		roomOpt1;
	private String		roomOpt2;
	private String		roomOpt3;
	private String		roomOpt4;
	private String		roomOpt5;
	private String		roomOpt6;
	private int			reservMinTime;
	private int			reservMinMan;
	private int			reservMaxMan;
	private Date		createdAt;
	private String		createdBy;
	private Date		updatedAt;
	private String		updatedBy;
	
	private int			price;
	
}
