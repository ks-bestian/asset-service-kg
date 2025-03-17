package kr.co.bestiansoft.ebillservicekg.eas.approval.service.impl;

import kr.co.bestiansoft.ebillservicekg.eas.approval.repository.ApprovalRepository;
import kr.co.bestiansoft.ebillservicekg.eas.approval.service.ApprovalService;
import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.ApprovalVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ApprovalServiceImpl implements ApprovalService {

    private final ApprovalRepository approvalRepository;

    public int insertApproval(ApprovalVo vo){
        return approvalRepository.insertApproval(vo);
    }

}
