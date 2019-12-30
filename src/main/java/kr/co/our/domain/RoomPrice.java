package kr.co.our.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomPrice implements Serializable {

	private static final long serialVersionUID = 3658994399153904583L;
	
	private int			spaceNum;
	private int			roomNum;
	private int			monPrice;
	private int			tuePrice;
	private int			wedPrice;
	private int			thuPrice;
	private int			friPrice;
	private int			satPrice;
	private int			sunPrice;
	private int 		holPrice;
	private Date		createdAt;
	private String		createdBy;
	private Date		updatedAt;
	private String		updatedBy;

	
}
