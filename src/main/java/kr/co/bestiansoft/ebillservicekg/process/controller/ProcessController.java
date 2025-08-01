package kr.co.bestiansoft.ebillservicekg.process.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.process.service.ProcessService;
import kr.co.bestiansoft.ebillservicekg.process.vo.ProcessVo;
import lombok.RequiredArgsConstructor;

@Tag(name = "Process API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class ProcessController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessController.class);
	private final ProcessService processService;

    @Operation(summary = "Process taskmanagement", description = "Process taskmanagement")
    @PostMapping(value = "/process/taskMng/taskUpdate")
    public ResponseEntity<CommonResponse> handleTask(@RequestBody ProcessVo processVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "OK", processService.handleTask(processVo)), HttpStatus.CREATED);
    }


    @Operation(summary = "Process step management", description = "Process step management")
    @PostMapping(value = "/process/processMng/handleProcessStep")
    public ResponseEntity<CommonResponse> handleProcessStep(@RequestBody ProcessVo processVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "OK", processService.handleProcess(processVo)), HttpStatus.CREATED);
    }


}