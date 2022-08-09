package App.controller.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;

public class PagingUtil {

    public static Paging getPaging(HttpServletRequest req, long totalEntites) {
        int limit = getLimit(req);
        long totalPages = getTotalPages(totalEntites, limit);
        long page = getPage(req, totalPages);
        long offset = (page - 1) * limit;
        return new Paging(limit, offset, page, totalPages);
    }

    private static long getPage(HttpServletRequest req, long totalPages) {
        String currentPage = req.getParameter("page");
        long page;
        if (currentPage == null || currentPage.equals("") || currentPage.matches(".*\\D+.*")) {
            page = 1;
        } else {
            page = Long.parseLong(currentPage);
            page = getValidPage(totalPages, page);
        }
        return page;
    }

    private static long getValidPage(long totalPages, long page) {
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

    private static long getTotalPages(long totalEntities, int limit) {
        long pages = totalEntities / limit;
        int additionalPage = (totalEntities - (pages * limit) > 0) ? 1 : 0;
        return pages + additionalPage;
    }

    @Data
    public static class Paging {
        private final int limit;
        private final long offset;
        private final long page;
        private final long totalPages;
    }

}
