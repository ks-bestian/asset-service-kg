package kr.co.bestiansoft.ebillservicekg.bill.review.billAll.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.vo.MtngAllVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.vo.MtngFileVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billAll.vo.BillAllVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;

@Mapper
public interface BillAllMapper {

    List<BillAllVo> selectListBill(HashMap<String, Object> param);
    /* Basic information */
    BillAllVo selectBill(HashMap<String, Object> param);
    /* document */
    List<EbsFileVo> selectListBillFile(HashMap<String, Object> param);

    List<BillAllVo> selectListBillCmt(HashMap<String, Object> param);

    /* committee meeting list*/
    List<MtngAllVo> selectListCmtMeeting(HashMap<String, Object> param);

    /* Main meeting list*/
    List<MtngAllVo> selectListMainMeeting(HashMap<String, Object> param);

    /* Party meeting list*/
    List<MtngAllVo> selectListPartyMeeting(HashMap<String, Object> param);

    /* meeting result file list*/
    List<MtngFileVo> selectListMettingResultFile(Long mtngId);

    List<BillAllVo> selectListBillEtcInfo(HashMap<String, Object> param);

    /* Bill Etc info file list*/
    List<EbsFileVo> selectListBillEtcFile(HashMap<String, Object> param);

    List<EbsFileVo> selectListBillEtcFile2(HashMap<String, Object> param);

    /* Bill monitor list*/
    List<BillAllVo> selectListBillMonitor(HashMap<String, Object> param);

    List<BillAllVo> countBillByPpslKnd(HashMap<String, Object> param);
    
    List<BillAllVo> countBillByPoly(HashMap<String, Object> param);
    
    List<BillAllVo> countBillByCmt(HashMap<String, Object> param);
}
