package kr.co.bestiansoft.ebillservicekg.common.file.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.bestiansoft.ebillservicekg.common.file.service.TusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class TusController {

    private final TusService tusService;
/*
    @RequestMapping(value = {"/tus/upload", "/tus/upload/**"}, method = {RequestMethod.POST, RequestMethod.PATCH, RequestMethod.HEAD, RequestMethod.OPTIONS})
    @ResponseBody
    public void tusUpload(HttpServletRequest request, HttpServletResponse response) {
        try {
            tusService.uploadTus(request, response);
        } catch (Exception e) {
            log.error("TUS 업로드 처리 중 오류 발생", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
*/    
    @ResponseBody
    @RequestMapping(value = {"/tus/upload", "/tus/upload/**"})
    public void tusUpload(HttpServletRequest request, HttpServletResponse response){
        try {
            tusService.uploadTus(request, response);
        } catch (Exception e) {
            log.error("TUS 업로드 처리 중 오류 발생", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}