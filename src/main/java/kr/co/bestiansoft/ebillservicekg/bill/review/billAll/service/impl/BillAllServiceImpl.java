package kr.co.bestiansoft.ebillservicekg.bill.review.billAll.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.repository.AgreeMapper;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo.AgreeVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.vo.MtngAllVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billAll.repository.BillAllMapper;
import kr.co.bestiansoft.ebillservicekg.bill.review.billAll.service.BillAllService;
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

        List<BillAllVo> result = billAllMapper.selectBillList(param);
        return result;
    }

    @Override
    public BillAllVo getBillById(String billId, HashMap<String, Object> param) {
    	param.put("billId", billId);
    	/* 기본정보 */
    	BillAllVo dto = billAllMapper.selectBillById(param);
    	
        // Null 체크 추가
        if (dto == null) {
            dto = new BillAllVo(); // Null일 경우 기본 객체로 초기화
        }
    	List<AgreeVo> proposerList = agreeMapper.selectAgreeProposerList(billId);
    	StringBuilder proposerItems = new StringBuilder(); // StringBuilder 사용 권장
    	for(int i=0; i<proposerList.size();i++) {
    		if("lng_type_1".equals(param.get("lang"))) {
    			proposerItems.append(proposerList.get(i).getMemberNmKg());
    		}else if("lng_type_2".equals(param.get("lang"))) {
    			proposerItems.append(proposerList.get(i).getMemberNmRu());
    		}else {
    			proposerItems.append(proposerList.get(i).getMemberNmKg());
    		}	
    		
    	    // 마지막 항목이 아니라면 콤마 추가
    	    if (i < proposerList.size() - 1) {
    	        proposerItems.append(", ");
    	    }
    	}
    	dto.setProposerItems(proposerItems.toString());	
    	
    	/* 문서 */
    	List<EbsFileVo> fileList = billAllMapper.selectBillFile(param);
    	dto.setFileList(fileList);
    	
    	/*소관위 기본정보*/
    	BillAllVo cmtData = billAllMapper.selectBillCmtInfo(param);
    	dto.setCmtData(cmtData);
    	
    	/*소관위 회의정보*/
    	List<MtngAllVo> cmtAgendaList = billAllMapper.selectBillCmtMtng(param);
    	dto.setCmtAgendaList(cmtAgendaList);
    	
    	/*관련위 기본정보*/
    	BillAllVo relCmtData = billAllMapper.selectBillRelCmtInfo(param);
    	dto.setRelData(relCmtData);
    	/*관련위 회의정보*/
    	List<MtngAllVo> relCmtAgendaList = billAllMapper.selectBillRelCmtMtng(param);
    	dto.setRelAgendaList(relCmtAgendaList);
    	
    	/*본회의 회의정보*/
    	
    	/*정부 이송*/
    	
        return dto;
    }

	@Override
	public BillAllVo getBillDetailById(String billId, HashMap<String, Object> param) {
    	param.put("billId", billId);
    	
    	BillAllVo dto = new BillAllVo();
    	dto.setBillId(billId);

    	/* 문서 */
    	List<EbsFileVo> fileList = billAllMapper.selectBillFile(param);
    	dto.setFileList(fileList);

    	/* ebs_masgter_detail 정보 */
    	List<BillAllVo> masterDetailList = billAllMapper.selectBillMastarDetail(param);
    	dto.setMasterDetailList(masterDetailList);
    	
        return dto;
	}
}