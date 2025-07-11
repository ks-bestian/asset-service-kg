package kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto.document.response;

import lombok.Data;

import java.util.List;

@Data
public class DocumentsListResult {
    private Integer totalItems;
    private Integer totalPages;
    private Integer currentPage;
    private List<DocumentContent> content;
}
