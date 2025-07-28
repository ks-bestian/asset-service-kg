package kr.co.bestiansoft.ebillservicekg.sed_jk.client.cds;

import org.springframework.http.HttpMethod;

import lombok.Getter;

@Getter
public enum CdsApi {
    USER_AUTH_METHODS("/api/user-auth-methods", HttpMethod.POST),
    GET_PIN_CODE("/api/get-pin-code", HttpMethod.POST),
    ACCOUNT_AUTH("/api/account/auth", HttpMethod.POST),
    GET_CERT_INFO("/api/get-cert-info", HttpMethod.POST),
    GET_SIGN_FOR_HASH("/api/get-sign/for-hash", HttpMethod.POST),
    CHECK_SIGN_FOR_HASH("/api/check-sign/for-hash", HttpMethod.POST);

    private final String path;
    private final HttpMethod httpMethod;

    CdsApi(String path, HttpMethod httpMethod) {
        this.path = path;
        this.httpMethod = httpMethod;
    }
}
