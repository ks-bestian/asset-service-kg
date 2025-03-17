package kr.co.bestiansoft.ebillservicekg.eas.workRequest.service.impl;

import kr.co.bestiansoft.ebillservicekg.eas.workRequest.repository.WorkRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class WorkRequestServiceImpl {

    private final WorkRequestRepository workRequestRepository;

}
