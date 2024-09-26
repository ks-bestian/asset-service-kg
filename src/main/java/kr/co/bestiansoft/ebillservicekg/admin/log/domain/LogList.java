package kr.co.bestiansoft.ebillservicekg.admin.log.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class LogList {

    private Long count;
    private List<Logs> logs;


}
