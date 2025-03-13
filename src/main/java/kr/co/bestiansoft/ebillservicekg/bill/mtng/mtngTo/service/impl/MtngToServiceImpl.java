package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.aspose.slides.internal.ax.av;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.repository.MtngToMapper;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.service.MtngToService;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.repository.MtngAllMapper;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.AgendaVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.MemberVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.vo.MtngFileVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.vo.MtngToVo;
import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.process.service.ProcessService;
import kr.co.bestiansoft.ebillservicekg.process.vo.ProcessVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MtngToServiceImpl implements MtngToService {

    private final MtngToMapper mtngToMapper;
    private final MtngAllMapper mtngAllMapper;
    private final ComFileService comFileService;
    private final ProcessService processService;

    @Override
    public List<MtngToVo> getMtngToList(HashMap<String, Object> param) {
        List<MtngToVo> result = mtngToMapper.selectListMtngTo(param);
        return result;
    }

    @Override
    public MtngToVo getMtngToById(Long mtngId, HashMap<String, Object> param) {

    	param.put("mtngId", mtngId);

    	/* 회의 정보*/
    	MtngToVo dto = mtngToMapper.selectMtngTo(param);

    	/*회의 결과 문서*/
    	List<MtngFileVo> reportList = mtngAllMapper.selectListMtngFile(param);
    	dto.setReportList(reportList);

    	/* 안건  */
    	List<AgendaVo> agendaList = mtngToMapper.selectListMtngAgenda(param);
    	dto.setAgendaList(agendaList);

    	/* 참석자 - selectListMtngAttendant */
    	List<MemberVo> attendantList = mtngToMapper.selectListMtngAttendant(param);
    	String names = attendantList.stream().map(MemberVo::getMemberNm).collect(Collectors.joining(", "));
    	dto.setAttendants(names);
    	dto.setAttendantList(attendantList);

        return dto;
    }

    @Transactional
	@Override
	public MtngToVo createMtngResult(MtngToVo paramVo) throws Exception {

		String loginUserId = new SecurityInfoUtil().getAccountId();

		String jsonString = paramVo.getJsonData();
		ObjectMapper objectMapper = new ObjectMapper();
		MtngToVo mtngToVo = objectMapper.readValue(jsonString, MtngToVo.class);

		List<AgendaVo> agendaList = mtngToVo.getAgendaList();
		List<MemberVo> attendantList = mtngToVo.getAttendantList();

		mtngToVo.setRegId(loginUserId);
		mtngToVo.setModId(loginUserId);
		mtngToMapper.updateMtngTo(mtngToVo);

		//참석자 수정
		mtngToMapper.deleteMtngToAttendant(mtngToVo);

		for(MemberVo vo:attendantList) {
			vo.setRegId(loginUserId);
			vo.setMtngId(mtngToVo.getMtngId());
			vo.setAtdtDeptNm(vo.getDeptNm());
			vo.setAtdtUserId(vo.getMemberId());
			vo.setAtdtUserNm(vo.getMemberNm());
			mtngToMapper.insertEbsMtngAttendant(vo);
		}

		//안건 수정
		mtngToMapper.deleteMtngToAgenda(mtngToVo);

		int idx = 1;
		for(AgendaVo vo:agendaList) {
			vo.setMtngId(mtngToVo.getMtngId());
			vo.setOrd(idx++);
			vo.setRegId(loginUserId);
			mtngToMapper.insertMtngToAgenda(vo);
		}

		//회의 결과파일등록
		comFileService.saveFileEbsMtng(paramVo.getFiles(), paramVo.getFileKindCds(), mtngToVo.getMtngId());

		//추가 - 내 문서함에서 파일 업로드(20250221 조진호)
		comFileService.saveFileEbsMtng(paramVo.getMyFileIds(), paramVo.getFileKindCds2(), mtngToVo.getMtngId());

		if("2".equals(mtngToVo.getMtngTypeCd())) { //본회의
			for(AgendaVo aVo:agendaList) {
				ProcessVo pVo = new ProcessVo();
				pVo.setBillId(aVo.getBillId());
				pVo.setStepId("1900");//법적행위관리
				processService.handleProcess(pVo);	
			}
		}
		
		return mtngToVo;
	}




	@Override
	public MtngToVo reportMtngTo(MtngToVo mtngFromVo) throws Exception {

		String userId = new SecurityInfoUtil().getAccountId();

		String jsonString = mtngFromVo.getJsonData();
		ObjectMapper objectMapper = new ObjectMapper();
		MtngToVo agendaVo = objectMapper.readValue(jsonString, MtngToVo.class);
		String deptCd = agendaVo.getDeptCd();//commitee or edt deptCd
		Long mtngId = agendaVo.getMtngId();
		String statCd = agendaVo.getStatCd();

		MtngToVo mtParam = new MtngToVo();
		mtParam.setMtngId(mtngId);
		mtParam.setModId(userId);
		mtParam.setStatCd(statCd);
		mtngToMapper.updateMtngToStatus(mtParam);// commitee meeting status update

		for(AgendaVo aVo :agendaVo.getAgendaList()) {

			if("M".equals(aVo.getCmtSeCd())) {//소관위
				ProcessVo pVo = new ProcessVo();
				pVo.setBillId(aVo.getBillId());
				pVo.setStepId("1500");//위원회 회의결과등록
				processService.handleProcess(pVo);
			} else {//관련위

			}
		}

		return agendaVo;
	}





	@Override
	public List<MemberVo> getMemberList(HashMap<String, Object> param) {
		return mtngToMapper.selectListMember(param);
	}

	@Override
	public void deleteMtng(List<Long> mtngIds) {
		// TODO 회의 취소 - 알림발송 구현 해야함

        for (Long mtngId : mtngIds) {
    		mtngToMapper.deleteMtngToAgenda(mtngId);
    		mtngToMapper.deleteMtngToAttendant(mtngId);
    		mtngToMapper.deleteMtngTo(mtngId);
        }

	}

	@Override
	public int updateMtngFileDel(HashMap<String, Object> param) {
		String modId = new SecurityInfoUtil().getAccountId();
		param.put("modId", modId);
		return mtngToMapper.updateMtngFileDel(param);
	}




}