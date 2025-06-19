package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DocumentUserDto {
    private String userId;
    private String userNm;
    private String deptCd;
}
