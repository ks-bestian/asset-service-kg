package kr.co.bestiansoft.ebillservicekg.eas.approval.service.impl;

import kr.co.bestiansoft.ebillservicekg.eas.approval.repository.ApprovalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ApprovalServiceImpl {

    private final ApprovalRepository approvalRepository;

}
