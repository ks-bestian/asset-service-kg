package kr.co.bestiansoft.ebillservicekg.test.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class TestVo extends ComDefaultVO {

	private Long seq;
	private String title;
	private String contents;
}
