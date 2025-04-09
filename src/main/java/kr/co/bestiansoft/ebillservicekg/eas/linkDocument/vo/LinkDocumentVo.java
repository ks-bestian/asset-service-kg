package kr.co.bestiansoft.ebillservicekg.eas.linkDocument.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class LinkDocumentVo extends ComDefaultVO {
    private int linkId;
    private String fromDocId;
    private String toDocId;
    private String linkType;
}