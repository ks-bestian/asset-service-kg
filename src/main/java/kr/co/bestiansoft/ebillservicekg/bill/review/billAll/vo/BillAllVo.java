package kr.co.bestiansoft.ebillservicekg.bill.review.billAll.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.vo.MtngAllVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.vo.MtngFileVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class BillAllVo extends ComDefaultVO {

    private String billId;// 안건아이디
    // 안건번호
    private String billNo;
    // 의안종류(법률안/예산안..)
    private String billKind;
    // 대수코드
    private String naTermCd;
    // 의안명
    private String billName;
    // 의안명 - kg
    private String billNameKg;
    // 의안명 - ru
    private String billNameRu;
    // 제안자종류(정부/의원)
    private String ppslKndCd;
    private String ppslKndNm;
    // 발의자
    private String ppsrNm;
    // 발의자아이디
    private String ppsrId;
    // 제안일
    private String ppslDt;
    // 상태코드
    private String statCd;
    // 위원회아이디
    private String cmtId;
    // 위원회아이디
    private String cmtCd;
    // 위원회명
    private String cmtNm;
    // 위원회회부일
    private LocalDate submitDt;
    // 접수확인일
    private LocalDate confirmDt;
    // 본회의예정일
    private LocalDateTime plnrPrsntDt;
    // 본회의처리일
    private LocalDate plnrDt;
    // 본회의처리결과
    private String plnrResult;
    // 정부이송일
    private LocalDate gvrnTrsfDt;
    // 공포일자
    private LocalDate prmgDt;
    // 공포번호
    private String prmgNo;
    // 요약내용
    private String smrCn;
    // 제개정타입
    private String billTy;
    // 법률아이디
    private Long lawId;
    // 철회 사유
    private String wtCn;

    // 비고
    private String etc;
    private String etcKg;
    private String etcRu;

    // 목록 검색용 : 넘버링
    private String num;
    // 목록 검색용 : 의안명 검색
    private String searchBillName;
    // 언어 구분에 따른 상태코드명
    private String statNm;

    // 안건 접수일자
    private String rcpDt;
    // 발의자 명단 - 문자열로
    private String proposerItems;

    /* ebs_master_detail 관련 */
    private Long seq;
    private String clsCd;
    private String rmrk;
    private String mtnDt;
    private String lglRvwRsltCode;//법류검토결과
    private String lglRvwRsltNm;//법류검토결과
    private String lglActRsltCode;//법적행위결과코드
    private String rsltDt;
    private String rsltCode;
    private String rsltNm;
    private List<BillAllVo> masterDetailList; //회의정보
    private String prsdRjctDt;



    /* 소관위 관련 */
    private BillAllVo cmtData;
    private List<MtngAllVo> cmtAgendaList; //회의정보

    /* 관련위 관련 */
    private BillAllVo relData;
    private List<MtngAllVo> relAgendaList;

    /* 본회의 관련 */
    private List<BillAllVo> plenaryList;

    /* 파일 관련*/
    private List<EbsFileVo> fileList;




}
