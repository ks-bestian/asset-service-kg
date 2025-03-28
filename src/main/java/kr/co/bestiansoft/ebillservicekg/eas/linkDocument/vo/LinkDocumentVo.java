package kr.co.bestiansoft.ebillservicekg.eas.linkDocument.vo;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class LinkDocumentVo {
    private int linkId;
    private String fromDocumentId;
    private String toDocumentId;
    private String linkType;
}