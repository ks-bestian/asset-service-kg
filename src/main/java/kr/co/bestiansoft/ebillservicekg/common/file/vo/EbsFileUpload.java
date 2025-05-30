package kr.co.bestiansoft.ebillservicekg.common.file.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class EbsFileUpload {
	private MultipartFile file;
	private String fileId;
	private String fileKindCd;
	private String opbYn;
}
