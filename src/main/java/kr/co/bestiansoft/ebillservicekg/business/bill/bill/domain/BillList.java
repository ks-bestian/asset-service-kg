package kr.co.bestiansoft.ebillservicekg.business.bill.bill.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class BillList {

    private String billNo;
    private String billName;
    private String proposer;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate proposeDate;
    private String committee;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate submitDate;
    private String status;

}
