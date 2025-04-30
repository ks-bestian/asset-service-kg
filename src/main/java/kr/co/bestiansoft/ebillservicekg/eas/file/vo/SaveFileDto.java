package kr.co.bestiansoft.ebillservicekg.eas.file.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SaveFileDto {
    String orgFileName;
    String fileId;
    int fileSize;
    String fileExt;
}
