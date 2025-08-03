package kr.co.bestiansoft.ebillservicekg.common.file.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.desair.tus.server.TusFileUploadService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TusController {

    private final TusFileUploadService tusService;

    @PostMapping("/")
    public void uploadWithTus(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	tusService.process(request, response);
    }
    
    @PatchMapping("/**")
    public void processPatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	tusService.process(request, response);
    }

    @RequestMapping(value = "/**", method = RequestMethod.HEAD)
    public void processHead(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	tusService.process(request, response);
    }

    @RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
    public void processOptions(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setStatus(HttpServletResponse.SC_OK);
    }
}