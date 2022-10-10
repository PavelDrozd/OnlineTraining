package App.controller.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;

public class PagingUtil {

    public static Paging getPaging(HttpServletRequest req, int totalEntites) {
        int limit = getLimit(req);
        int totalPages = getTotalPages(totalEntites, limit);
        int page = getPage(req, totalPages);
        int offset = (page - 1) * limit;
        return new Paging(limit, offset, page, totalPages);
    }

    private static int getPage(HttpServletRequest req, int totalPages) {
        String currentPage = req.getParameter("page");
        int page;
        if (currentPage == null || currentPage.equals("") || currentPage.matches(".*\\D+.*")) {
            page = 1;
        } else {
            page = Integer.parseInt(currentPage);
            page = getValidPage(totalPages, page);
        }
        return page;
    }

    private static int getValidPage(int totalPages, int page) {
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages) {
            page = totalPages;
        }
        return page;
    }

    private static int getLimit(HttpServletRequest req) {
        String limitStr = req.getParameter("limit");
        int limit;
        if (limitStr == null || limitStr.equals("") || limitStr.matches(".*\\D+.*")) {
            limit = 10;
        } else {
            limit = Integer.parseInt(limitStr);
            if (limit < 1) {
                limit = 5;
            }
        }
        return limit;
    }

    private static int getTotalPages(int totalEntities, int limit) {
        int pages = totalEntities / limit;
        int additionalPage = (totalEntities - (pages * limit) > 0) ? 1 : 0;
        return pages + additionalPage;
    }

    @Data
    public static class Paging {
        private final int limit;
        private final int offset;
        private final int page;
        private final int totalPages;
    }

}
