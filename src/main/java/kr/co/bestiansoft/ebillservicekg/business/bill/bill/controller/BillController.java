package kr.co.bestiansoft.ebillservicekg.business.bill.bill.controller;

import kr.co.bestiansoft.ebillservicekg.business.bill.bill.domain.BillList;
import kr.co.bestiansoft.ebillservicekg.business.bill.bill.domain.BillSearch;
import kr.co.bestiansoft.ebillservicekg.business.bill.bill.service.BillService;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.ListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BillController {

    private final BillService billService;

    @GetMapping("/api/bills")
    public ResponseEntity<ListResponse<BillList>> getBillList(@ModelAttribute BillSearch search) {

        return new ResponseEntity<>(billService.getBills(search), HttpStatus.OK);
    }
}
