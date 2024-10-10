package kr.co.bestiansoft.ebillservicekg.admin.log.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.admin.log.domain.LogList;
import kr.co.bestiansoft.ebillservicekg.admin.log.domain.LogRead;
import kr.co.bestiansoft.ebillservicekg.admin.log.domain.Logs;
import kr.co.bestiansoft.ebillservicekg.admin.log.service.LogService;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.ListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "접속이력 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
public class LogController {

    private final LogService logService;

    @ApiOperation(value = "접속이력 리스트 조회", notes = "접속이력 리스트를 조회한다.")
    @GetMapping("/api/logs")
    public ResponseEntity<ListResponse<Logs>> logList(@ModelAttribute LogRead logRead) {

        log.info("logRead : {}", logRead);

        LogList logList = logService.logList(logRead);

        ListResponse<Logs> logsResponse = new ListResponse<>(logList.getCount(), logList.getLogs());

        return new ResponseEntity<>(logsResponse, HttpStatus.OK);

    }

    @ApiOperation(value = "접속이력 저장", notes = "인터셉터에서 접속이력을 저장한다.")
    @PostMapping("/api/save-route")
    public ResponseEntity saveRoute(@Valid @ModelAttribute LogRead accessHistRead) {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
