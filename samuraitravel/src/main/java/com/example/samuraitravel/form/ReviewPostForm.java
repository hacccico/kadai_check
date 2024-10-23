package com.example.samuraitravel.form;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewPostForm {
	private Integer id;
	
	private Integer houseId;
	
	private Integer userId;
	
	private String reviewStar;
	
	private String reviewComment;

}
