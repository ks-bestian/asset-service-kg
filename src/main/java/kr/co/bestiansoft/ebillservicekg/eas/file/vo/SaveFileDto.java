package kr.co.bestiansoft.ebillservicekg.eas.file.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SaveFileDto {
    private String orgFileNm;
    private String fileId;
    private int fileSize;
    private String fileExt;
}
