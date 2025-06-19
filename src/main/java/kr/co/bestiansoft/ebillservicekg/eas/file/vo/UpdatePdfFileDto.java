package kr.co.bestiansoft.ebillservicekg.eas.file.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UpdatePdfFileDto {
    private String pdfFileId;
    private String pdfFileNm;
}
