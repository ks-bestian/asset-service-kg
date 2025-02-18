package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
    	dto.setAttendantList(attendantList);

        return dto;
    }

	@Override
	public MtngToVo createMtngTo(MtngToVo mtngToVo) {

		/*
		 * 1. 결과 등록 메뉴에서 결과 등록을 하면 회의 예정에 대한 회의결과 를 '임시저장(수정)' 한다는 개념으로 가야할듯.
		 * 		회의 결과 보고 처리가 최종.
		 * 2. 등록(수정)처리 여러번 할 경우 기존 파일 deleteYn 'Y'처리하고 입력하면 되지 않을까
		 * 3. 안건, 참석자의 경우 기존에 등록했던 행들은 결과만 업데이트 하고 신규 추가, 삭제된 내용은 바로바로 추가/삭제 할수 있게 해야할듯.
		 * 		기존 항목이었는지 구분 필요.
		 *
		 * */

		/* 등록자 아이디 세팅 */
		String regId = new SecurityInfoUtil().getAccountId();
		mtngToVo.setRegId(regId);
		mtngToVo.setModId(regId);
		/*회의 - ebs_mtng*/
		mtngToMapper.updateMtngTo(mtngToVo);
		log.info("mtngId2 : {}", mtngToVo.getMtngId());


		MultipartFile[] files = mtngToVo.getFiles();
		/*회의결과 보고서 등록 - ebs_mtng_file*/
		if(files != null && files.length>0) {

		    // 유효성 검사: 하나라도 파일이 유효하지 않으면 중단
		    boolean allValidFiles = Arrays.stream(files)
		                                  .allMatch(file -> file != null && !file.isEmpty());

		    if (allValidFiles) {
				mtngToMapper.deleteMtngToFile(mtngToVo);

				comFileService.saveFileEbsMtng(mtngToVo.getFiles(), mtngToVo.getFileKindCds(), mtngToVo.getMtngId());
		    }


		}else {
		    log.info("파일이 존재하지 않습니다. 파일을 첨부해주세요.");
		}



		//파일 정보를 가지고 있어서 null처리
		mtngToVo.setFiles(null);

		/*안건 ebs_mtng_agenda*/

		// 삭제 대상 추출 및 삭제
		mtngToMapper.deleteMtngToAgenda(mtngToVo);

		List<AgendaVo> agendaList = mtngToVo.getAgendaList();
		if(agendaList!=null) {
			for(int i=0;i<agendaList.size();i++) {
				log.info("billId : {}", agendaList.get(i).getBillId());
				log.info("agenda : {}", agendaList.get(i));
				AgendaVo agendaVo = new AgendaVo();
				agendaVo.setBillId(agendaList.get(i).getBillId());
				agendaVo.setMtngId(mtngToVo.getMtngId());
				agendaVo.setOrd(i+1);
				agendaVo.setRegId(regId);
				agendaVo.setRsltCd(agendaList.get(i).getRsltCd());
				agendaList.set(i, agendaVo);
				//존재하는 행인 경우 업데이트. 아닌경우 인서트
				mtngToMapper.updateMtngToAgenda(agendaList.get(i));
			}
		}


		/*참석자 ebs_mtng_attendant*/

		// 삭제 대상 추출 및 삭제
		mtngToMapper.deleteMtngToAttendant(mtngToVo);

		List<MemberVo> attendantList = mtngToVo.getAttendantList();
		if(attendantList!=null) {
			for(int i=0;i<attendantList.size();i++) {
				log.info("attendant : {}", attendantList.get(i));
				MemberVo memberVo = new MemberVo();
				memberVo.setMtngId(mtngToVo.getMtngId());
				memberVo.setAtdtUserId(attendantList.get(i).getAtdtUserId());
				memberVo.setAtdtUserNm(attendantList.get(i).getAtdtUserNm());
				//뷰테이블의 사용자 가져와서 쓰라고 하셨는데... 참석자 구분을 할만한 정보가 없음. 내부인지 외부인지 직원인지 의원인지 등등...
				//memberVo.setAtdtKind("ATT01");// 참석자 구분(의원)
				memberVo.setRegId(regId);
				memberVo.setAtdtDivCd(attendantList.get(i).getAtdtDivCd());
				attendantList.set(i, memberVo);
				//존재하는 행인 경우 업데이트. 아닌경우 인서트
				mtngToMapper.updateMtngToAttendant(attendantList.get(i));
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

//		for(AgendaVo aVo :agendaVo.getAgendaList()) {
//
//			if("M".equals(aVo.getCmtSeCd())) {//소관위
//				ProcessVo pVo = new ProcessVo();
//				pVo.setBillId(aVo.getBillId());
//				processService.handleProcess(pVo);
//			} else {//관련위
//
//			}
//		}

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