package kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CommonResponse {

    private int code;
    private String message;
    private Object data;
    private long totalCount;

    @Builder
    public CommonResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Builder
    public CommonResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    @Builder
    public CommonResponse(int code, String message, Object data, long totalCount) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.totalCount = totalCount;
    }


}
