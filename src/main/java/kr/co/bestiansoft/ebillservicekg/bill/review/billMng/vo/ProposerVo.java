package kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo;

import java.time.LocalDateTime;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class ProposerVo extends ComDefaultVO {

	//의안id
    private String billId;
    //의원id
    private String proposerId;
    private String proposerNm;

    //순서
    private int ord;

    //정당
    private String polyCd;
    private String polyNm;

    //위원회
    private String cmitCd;
    private String cmitNm;

    //프로필 사진
    private String profileImgPath;

    //서명일시
    private LocalDateTime signDt;
    //서명 취소일시
    private LocalDateTime signCncDt;

    //철회일시
    private LocalDateTime wtDt;
    //철회취소일시
    private LocalDateTime wtCncDt;

    // 목록 검색용 : 넘버링
    private String num;

    //서명여부
    private String agreeYn;

}
