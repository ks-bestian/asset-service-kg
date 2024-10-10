package kr.co.bestiansoft.ebillservicekg.business.bill.bill.domain;

import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.request.ListRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BillSearch extends ListRequest {

    private String billNo;
    private String billName;
    private String status;
    private String proposeDate;

}
