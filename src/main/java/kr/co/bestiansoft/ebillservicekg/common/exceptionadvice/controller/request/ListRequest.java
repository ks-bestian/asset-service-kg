package kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.request;

import lombok.Data;

@Data
public class ListRequest {

    private Long page;
    private Long size;


    public void setPage(Long page) {

        this.page = page-1;
    }

}
