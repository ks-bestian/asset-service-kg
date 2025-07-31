package kr.co.bestiansoft.ebillservicekg.asset.amsImg.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.bestiansoft.ebillservicekg.asset.amsImg.service.AmsImgService;
import kr.co.bestiansoft.ebillservicekg.asset.amsImg.vo.AmsImgVo;
import kr.co.bestiansoft.ebillservicekg.asset.equip.service.EquipService;
import kr.co.bestiansoft.ebillservicekg.asset.equip.vo.EquipRequest;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "이미지 API")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/img")
public class ImgController {
    private final AmsImgService amsImgService;


    @Operation(summary = "상세 이미지 목록 조회", description = "상세 이미지 목록 조회한다.")
    @GetMapping(value = "/detail")
    public ResponseEntity<CommonResponse> getImgListByEqpmntId(@RequestParam String eqpmntId) {
    	List<String> id = Arrays.asList(eqpmntId);
        List<AmsImgVo> tempImgList = amsImgService.getImgListByEqpmntId(id);
        List<AmsImgVo> imgList = tempImgList.stream()
                .filter(item -> "detail".equals(item.getImgSe()))
                .collect(Collectors.toList());
        
        return new ResponseEntity<>(new CommonResponse(200, "OK", imgList), HttpStatus.OK);
    }

    




}
