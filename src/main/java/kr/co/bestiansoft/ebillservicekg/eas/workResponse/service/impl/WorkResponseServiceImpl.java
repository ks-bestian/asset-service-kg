package kr.co.bestiansoft.ebillservicekg.eas.workResponse.service.impl;


import kr.co.bestiansoft.ebillservicekg.eas.workResponse.repository.WorkResponseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class WorkResponseServiceImpl {
    private final WorkResponseRepository workResponseRepository;
}
