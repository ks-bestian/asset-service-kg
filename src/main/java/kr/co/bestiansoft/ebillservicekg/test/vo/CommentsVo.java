package kr.co.bestiansoft.ebillservicekg.test.vo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CommentsVo {
	private Long id;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Boolean approved;
	private String content;
	private Boolean deleted;
	private Boolean edited;
	private Long esiUserId;
	private Long lawId;
	private Long parentId;
	private String createdBy;
	private String updatedBy;
	private String parentYn;

	private List<CommentsVo> children = new ArrayList<>();
	public void addChildren(CommentsVo children) { this.children.add(children); }
}
