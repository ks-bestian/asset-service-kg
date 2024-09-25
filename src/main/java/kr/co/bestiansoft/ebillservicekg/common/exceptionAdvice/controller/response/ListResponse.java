package kr.co.bestiansoft.ebillservicekg.common.exceptionAdvice.controller.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ListResponse<T> {

    private Long count;
    private int totalPage;
    private List<T> content;

    @Builder
    public ListResponse(Long count, int totalPage, List<T> content) {
        this.count = count;
        this.totalPage = totalPage;
        this.content = content;
    }

    public static <T> ListResponse<T> from(Long count, int totalPage, List<T> content) {
        return ListResponse.<T>builder()
                .count(count)
                .totalPage(totalPage)
                .content(content)
                .build();
    }

    public ListResponse<T> addContent(List<T> content) {
        this.content = content;

        return this;
    }

	public static ListResponse<String> from(List<String> users, Long count) {
		return ListResponse.<String>builder()
				.content(users)
				.count(count)
				.build();
	}
}
