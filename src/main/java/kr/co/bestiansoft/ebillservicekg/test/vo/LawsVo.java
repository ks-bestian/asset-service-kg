package kr.co.bestiansoft.ebillservicekg.test.vo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LawsVo {
	private Long id;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String createdBy;
	private String descriptionKg;
	private String descriptionRu;
	private LocalDateTime publishDate;
	private String registrationNumber;
	private String slug;
	private Boolean status;
	private String titleKg;
	private String titleRu;
	private Integer viewedFull;
	private Integer viewedHalf;
	private String updatedBy;
}
