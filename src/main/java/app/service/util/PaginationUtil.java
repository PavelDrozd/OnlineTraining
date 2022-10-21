package app.service.util;

import org.springframework.stereotype.Component;

@Component
public class PaginationUtil {

    public Integer getPage(String strPage, Long totalPages){
        Integer page;
        if (strPage == null || strPage.equals("") || strPage.matches(".*\\D+.*")) {
            page = 1;
        } else {
            page = Integer.parseInt(strPage);
            page = getValidPage(totalPages, page);
        }
        return page;
    }

    public Integer getPageSize(String strPageSize){
        int limit;
        if (strPageSize == null || strPageSize.equals("") || strPageSize.matches(".*\\D+.*")) {
            limit = 10;
        } else {
            limit = Integer.parseInt(strPageSize);
            if (limit < 1) {
                limit = 5;
            }
        }
        return limit;
    }

    public Long getTotalPages(Integer limit, Long totalEntities) {
        long pages = totalEntities / limit;
        int additionalPage = (totalEntities - (pages * limit) > 0) ? 1 : 0;
        return pages + additionalPage;
    }

    private Integer getValidPage(Long totalPages, Integer page) {
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages) {
            page = totalPages.intValue();
        }
        return page;
    }


}
