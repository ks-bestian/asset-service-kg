package kr.co.bestiansoft.ebillservicekg.bill.review.billAll.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.repository.AgreeMapper;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo.AgreeVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.vo.MtngAllVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billAll.repository.BillAllMapper;
import kr.co.bestiansoft.ebillservicekg.bill.review.billAll.service.BillAllService;
import kr.co.bestiansoft.ebillservicekg.bill.review.billAll.vo.BillAllResponse;
import kr.co.bestiansoft.ebillservicekg.bill.review.billAll.vo.BillAllVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class BillAllServiceImpl implements BillAllService {
    private final BillAllMapper billAllMapper;
    private final AgreeMapper agreeMapper;


    @Override
    public List<BillAllVo> getBillList(HashMap<String, Object> param) {

        List<BillAllVo> result = billAllMapper.selectListBill(param);
        return result;
    }

    @Override
    public BillAllResponse getBillById(String billId, HashMap<String, Object> param) {

    	BillAllResponse billRespanse = new BillAllResponse();
    	param.put("billId", billId);

    	/* Bill basic info */
    	BillAllVo billBasicInfo = billAllMapper.selectBill(param);

    	/* Proposer List */
    	List<AgreeVo> proposerList = agreeMapper.selectAgreeProposerList(billId);
    	/* Proposer String */
    	String proposerItem = proposerList.stream().map(AgreeVo::getMemberNm).collect(Collectors.joining(", "));
    	billBasicInfo.setProposerItems(proposerItem);

    	/* Bill doc list*/
    	List<EbsFileVo> billFileList = billAllMapper.selectListBillFile(param);

    	/*committee list*/
    	List<BillAllVo> cmtList = billAllMapper.selectListBillCmt(param);

    	/* committee meeting list*/
    	List<MtngAllVo> cmtMtList = billAllMapper.selectListCmtMeeting(param);

    	/* main meeting list*/
    	List<MtngAllVo> mainMtList = billAllMapper.selectListMainMeeting(param);

    	/* Party meeting list*/
    	List<MtngAllVo> partyMtList = billAllMapper.selectListPartyMeeting(param);

    	/* Bill etc info */
    	List<BillAllVo> etcInfoList = billAllMapper.selectListBillEtcInfo(param);

    	billRespanse.setBillBasicInfo(billBasicInfo);
    	billRespanse.setProposerList(proposerList);
    	billRespanse.setBillFileList(billFileList);
    	billRespanse.setCmtList(cmtList);
    	billRespanse.setCmtMtList(cmtMtList);
    	billRespanse.setMainMtList(mainMtList);
    	billRespanse.setPartyMtList(partyMtList);
    	billRespanse.setEtcInfoList(etcInfoList);

        return billRespanse;
    }


}