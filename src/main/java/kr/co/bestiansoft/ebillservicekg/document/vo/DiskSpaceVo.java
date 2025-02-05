package kr.co.bestiansoft.ebillservicekg.document.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class DiskSpaceVo {

	private Long totalSpace;
	private Long freeSpace;
	private Long usedSpace;
}
