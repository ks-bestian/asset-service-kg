package kr.co.bestiansoft.ebillservicekg.common.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageUtil {

    public static Pageable formatting(Pageable pageable) {

        int pageNumber = pageable.getPageNumber();
        if(pageable.getPageNumber() <= 1){
            pageNumber = 1;
        }

        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageable.getPageSize());

        return pageRequest;
    }
}
