package kr.co.bestiansoft.ebillservicekg.process.controller;

import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.process.service.ProcessService;
import kr.co.bestiansoft.ebillservicekg.process.vo.ProcessVo;
import lombok.RequiredArgsConstructor;

@Api(tags = "Process API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class ProcessController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessController.class);

	private final ProcessService processService;


    @ApiOperation(value = "process test", notes = "process test")
    @PostMapping("/testprocess/exe")
    public ResponseEntity<CommonResponse> testProcess(@RequestBody ProcessVo vo) throws Exception {

    	ProcessVo returnVo = processService.testProcess(vo);

    	return new ResponseEntity<>(new CommonResponse(200, "OK", returnVo), HttpStatus.OK);
    }

    @ApiOperation(value = "process test", notes = "process test")
    @PostMapping("/testprocess/autoexe")
    public ResponseEntity<CommonResponse> createProcessAuto(@RequestBody ProcessVo vo) throws Exception {

    	ProcessVo returnVo = processService.createProcessAuto(vo);

    	return new ResponseEntity<>(new CommonResponse(200, "OK", returnVo), HttpStatus.OK);
    }



}