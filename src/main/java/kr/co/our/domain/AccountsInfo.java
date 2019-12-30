package kr.co.our.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountsInfo implements Serializable {
	
	private static final long serialVersionUID = 364821080834818848L;
	
	private int			spaceNum;
	private String		companyName;
	private String		ownerName;
	private String		bizRegnum;
	private String		bizCon;
	private String		bizKind;
	private String 		bizAddr;
	private String		bizEmail;
	private String		bizEmail1;
	private String		bizEmail2;
	private String		bizCaltel;
	private String		bizBankname;
	private String		bizBanknum;
	private String		bizDepositor;
	private Date		createdAt;
	private String		createdBy;
	private Date		updatedAt;
	private String		updatedBy;
	
	private Integer		spaceId;
	private String		accountsHostId;
	
	private String 		postType;
}
