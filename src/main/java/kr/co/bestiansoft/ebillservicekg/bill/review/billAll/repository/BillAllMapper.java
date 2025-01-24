package kr.co.bestiansoft.ebillservicekg.bill.review.billAll.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.vo.MtngAllVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billAll.vo.BillAllVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;

@Mapper
public interface BillAllMapper {
    List<BillAllVo> selectBillList (HashMap<String, Object> param);
    /* 기본정보 */
    BillAllVo selectBillById(HashMap<String, Object> param);
    /* 문서(회의별이 아니고 안건별 문서이므로 ebs_file에서 가져와야한다. */
    List<EbsFileVo> selectBillFile(HashMap<String, Object> param);
    /* 소관위 기본정보 */
    BillAllVo selectBillCmtInfo(HashMap<String, Object> param);
    /* 소관위 회의정보 */
    List<MtngAllVo> selectBillCmtMtng(HashMap<String, Object> param);
    /* 관련위 기본정보 */
    BillAllVo selectBillRelCmtInfo(HashMap<String, Object> param);
    /* 관련위 회의정보 */
    List<MtngAllVo> selectBillRelCmtMtng(HashMap<String, Object> param);
    /* ebs_master_detail 정보 */
    List<BillAllVo> selectBillMastarDetail(HashMap<String, Object> param);

}
