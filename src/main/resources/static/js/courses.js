$(function() {
    refresh();

    function refresh() {
        const queryString = $(".query-string").text();
        $.get("/api/courses?" + queryString, processCourse);
    }

    function processCourse(page) {
        updateQueryString(page);
        renderTable(page);
        renderPaginationButtons(page);
    }

    function updateQueryString(page) {
        $(".query-string").text(`page=${page.number}&size${page.size}`);
    }

    function renderTable(page) {
        const $tbody = $("tbody");
        $tbody.empty();
        page.content.forEach(c => renderTableRow(c, $tbody));
    }

    function renderTableRow(c, $tbody) {
        const $row = $(`
            <tr id ="row-${c.id}">
                <td>${c.name}</td>
                <td>
					<button class="view">View</button>
					<button class="edit">Edit</button>
					<button class="delete">Delete</button>
				</td>
			</tr>
			`);
		$row.find(".view").on("click", () => window.location.href = `/courses/${c.id}`);
		$row.find(".edit").on("click", () => window.location.href = `/courses/${c.id}/edit`);
		$row.find(".delete").on("click", () => $.ajax({
			type: "DELETE",
			url: `/api/courses/${c.id}`,
			success: refresh
		}));
		$tbody.append($row);
    }

    function renderPaginationButtons(page) {
    		$(".pagination button").off();
    		const prevPage = Math.max(0, page.number - 1);
    		const lastPage = page.totalPages - 1;
    		const nextPage = Math.min(lastPage, page.number + 1);
    		$(".first").on("click", () => $.get(`/api/courses?page=0&size=${page.size}`, processCourse));
    		$(".prev").on("click", () => $.get(`/api/courses?page=${prevPage}&size=${page.size}`, processCourse));
    		$(".current").text(page.number + 1);
    		$(".next").on("click", () => $.get(`/api/courses?page=${nextPage}&size=${page.size}`, processCourse));
    		$(".last").on("click", () => $.get(`/api/courses?page=${lastPage}&size=${page.size}`, processCourse));
    }
});