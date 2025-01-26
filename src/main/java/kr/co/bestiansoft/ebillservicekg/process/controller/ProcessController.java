package kr.co.bestiansoft.ebillservicekg.process.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import kr.co.bestiansoft.ebillservicekg.process.service.ProcessService;
import lombok.RequiredArgsConstructor;

@Api(tags = "Process API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class ProcessController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessController.class);

	private final ProcessService processService;




}