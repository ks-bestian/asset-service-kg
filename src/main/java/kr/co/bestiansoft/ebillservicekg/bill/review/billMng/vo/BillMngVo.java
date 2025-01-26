package kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class BillMngVo extends ComDefaultVO {

    private String billId;// 안건아이디
    // 안건번호
    private String billNo;
    // 의안종류(법률안/예산안..)
    private String billKind;
    // 대수코드
    private String naTermCd;

    private String billName;

    // 의안명 - kg
    private String billNameKg;
    // 의안명 - ru
    private String billNameRu;
    // 제안자종류(정부/의원)
    private String ppslKndCd;
    // 발의자
    private String ppsrNm;
    // 발의자아이디
    private String ppsrId;
    // 제안일
    private String ppslDt;
    // 상태코드
    private String statCd;
    private String statNm;

    // 위원회아이디
    private String cmtId;
    // 위원회회부일
    private LocalDate submitDt;
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
    // 철회 사유-키르
    private String wtCnKg;
    // 철회 사유-러시아
    private String wtCnRu;

    // 비고
    private String etcKg;
    private String etcRu;

    //사회토론접수번호
    private String sclDscRcpNmb;
    //정부제출전자문서번호
    private String gvrnSbmsElctDcmnNmbr;

    // 목록 검색용 : 넘버링
    private String num;
    // 목록 검색용 : 의안명 검색
    private String searchBillName;
    // 언어 구분에 따른 상태코드명

    // 위원회 심사 차수
    private String cmtCnt;

    private List<ProposerVo> proposerList;

    //process info
    private String currentStepId;
    private String stepId;
    private Long taskId;
    private String taskStatus;
    private String bpInstanceId;
    private String assignedTo;
    private String trgtUserId;



}
