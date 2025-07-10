package kr.co.bestiansoft.ebillservicekg.sed_jk.services.common;

import kr.co.bestiansoft.ebillservicekg.sed_jk.dto.request.SignRequestDto;
import kr.co.bestiansoft.ebillservicekg.sed_jk.dto.response.CheckResponseDto;
import kr.co.bestiansoft.ebillservicekg.sed_jk.dto.response.SignResponseDto;


public interface DocumentSignService {
    public SignResponseDto signDocument(SignRequestDto signRequestDto);
//    public CheckResponseDto checkAgreement(Long agreementId);
//    public CheckResponseDto checkDocument(Long agreementId);
}
