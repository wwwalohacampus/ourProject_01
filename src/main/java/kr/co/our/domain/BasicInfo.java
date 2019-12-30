package kr.co.our.domain;
/*
 -- (공간) 기본 정보 : space_basic
CREATE TABLE `ourt01`.`space_basic` (
  `space_num` 				BIGINT NOT NULL AUTO_INCREMENT,
  `host_id` 				VARCHAR(45) NOT NULL,
  `space_category`			INT NOT NULL,
  `space_name` 				VARCHAR(45) NOT NULL,
  `space_head` 				VARCHAR(100) NOT NULL,
  `space_intro` 			VARCHAR(2000) NOT NULL,
  `space_tag` 				VARCHAR(45) NOT NULL,
  `space_caution`			VARCHAR(45) NOT NULL,
  `space_url` 				VARCHAR(45) NOT NULL,
  `space_photo` 			VARCHAR(200) NOT NULL,
  `space_addr` 				VARCHAR(100) NOT NULL,
  `space_addrdetail` 		VARCHAR(100) NOT NULL,
  `created_at` 				TIMESTAMP NOT NULL,
  `created_by` 				VARCHAR(45) NOT NULL,
  `updated_at` 				TIMESTAMP NULL,
  `updated_by` 				VARCHAR(45) NULL,
  PRIMARY KEY (`space_num`)
  );
 */

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicInfo implements Serializable {

	private static final long serialVersionUID = -7899358188002509114L;
	
	
	private int 			spaceNum;
	private String 			hostId;
	private String			spaceCategory;
	private String			spaceCategory1;
	private String			spaceCategory2;
	private String			spaceCategory3;
	private String			spaceCategory4;
	private String			spaceCategory5;
	private String			spaceCategory6;
	private String			spaceName;
	private String			spaceHead;
	private String			spaceIntro;
	private String			spaceTag;
	private String			spaceCaution;
	private String			spaceUrl;
	private String			spaceAddr;
	private String			spaceAddrdetail;
	private Date 			createdAt;
	private String 			createdBy;
	private Date 			updatedAt;
	private String 			updatedBy;
	
	private String			fullName;
	
	
}









