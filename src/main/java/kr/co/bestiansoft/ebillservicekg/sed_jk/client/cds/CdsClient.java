package kr.co.bestiansoft.ebillservicekg.sed_jk.client.cds;

import kr.co.bestiansoft.ebillservicekg.sed_jk.client.cds.dto.request.*;
import kr.co.bestiansoft.ebillservicekg.sed_jk.client.cds.dto.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class CdsClient {
    private final RestClient restClient;

    @Value("${app.cds.url}")
    private String url;
    @Value("${app.cds.key}")
    private String key;
    @Value("${app.cds.user-agent-org-name}")
    private String userAgentOrgName;

    private <T, R> R sendPostRequest(CdsApi api, T requestBody, Class<R> responseType) {
        Consumer<HttpHeaders> headersConsumer = headers -> {
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + key);
            headers.add(HttpHeaders.USER_AGENT, userAgentOrgName);
        };

        log.info("Sending POST request to EAP API: {}/{}", url, api.getPath());

        try {
            return restClient.post()
                    .uri(url + api.getPath())
                    .headers(headersConsumer)
                    .body(requestBody)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (request, response) -> {
//                        String errorMessage = response.bodyTo(String.class);
                        log.error("EAP API Error: Status={}, Body={}", response.getStatusCode(), "errorMessage");
                        throw new RuntimeException("EAP API Error: " + response.getStatusCode() + " - " + "errorMessage");
                    })
                    .body(responseType);
        } catch (Exception e) {
            log.error("Error during EAP API call to {}: {}", api.getPath(), e.getMessage(), e);
            throw new RuntimeException("Failed to call EAP API: " + e.getMessage(), e);
        }
    }

    public AuthMethodResponse getUserAuthMethods(AuthMethodRequest request) {
        return sendPostRequest(CdsApi.USER_AUTH_METHODS, request, AuthMethodResponse.class);
    }

    public PinCodeResponse getPinCode(PinCodeRequest request) {
        return sendPostRequest(CdsApi.GET_PIN_CODE, request, PinCodeResponse.class);
    }

    public AuthResponse authenticate(AuthRequest request) {
        return sendPostRequest(CdsApi.ACCOUNT_AUTH, request, AuthResponse.class);
    }

    public CertInfoResponse getCertInfo(CertInfoRequest request) {
        return sendPostRequest(CdsApi.GET_CERT_INFO, request, CertInfoResponse.class);
    }

    public SignHashResponse signHash(SignHashRequest request) {
        return sendPostRequest(CdsApi.GET_SIGN_FOR_HASH, request, SignHashResponse.class);
    }

    public CheckSignResponse checkSign(CheckSignRequest request) {
        return sendPostRequest(CdsApi.CHECK_SIGN_FOR_HASH, request, CheckSignResponse.class);
    }
}
