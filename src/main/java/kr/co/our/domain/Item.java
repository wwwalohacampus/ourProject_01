package kr.co.our.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item implements Serializable {

	private static final long serialVersionUID = -7456901591282626221L;
	
	private Integer itemId;

	private String itemName;

	private Integer price;

	private String description;
	
	private String[] files;

}
