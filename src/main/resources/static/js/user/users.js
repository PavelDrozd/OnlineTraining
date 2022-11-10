$(function() {
    refresh();

    function refresh() {
        const queryString = $(".query-string").text();
        $.get("/api/users?" + queryString, processUsers);
    }

    function processUsers(page) {
        updateQueryString(page);
        renderTable(page);
        renderPaginationButtons(page);
    }

    function updateQueryString(page) {
        $(".query-string").text(`page=${page.number}&size=${page.size}`);
    }

    function renderTable(page) {
        const $tbody = $("tbody");
        $tbody.empty();
        page.content.forEach(u => renderTableRow(u, $tbody));
    }

    function renderTableRow(u, $tbody) {
        const $row = $(`
            <tr id ="row-${u.id}">
                <td>${u.login}</td>
		        <td>
					<button class="view">View</button>
					<button class="edit">Edit</button>
					<button class="delete">Delete</button>
				</td>
			</tr>
			`);
		$row.find(".view").on("click", () => window.location.href = `/users/${u.id}`);
		$row.find(".edit").on("click", () => window.location.href = `/users/${u.id}/edit`);
		$row.find(".delete").on("click", () => $.ajax({
			type: "DELETE",
			url: `/api/users/${u.id}`,
			success: refresh
		}));
		$tbody.append($row);
    }

    function renderPaginationButtons(page) {
    		$(".pagination button").off();
    		const prevPage = Math.max(0, page.number - 1);
    		const lastPage = page.totalPages - 1;
    		const nextPage = Math.min(lastPage, page.number + 1);
    		$(".first").on("click", () => $.get(`/api/users?page=0&size=${page.size}`, processUsers));
    		$(".prev").on("click", () => $.get(`/api/users?page=${prevPage}&size=${page.size}`, processUsers));
    		$(".current").text(page.number + 1);
    		$(".next").on("click", () => $.get(`/api/users?page=${nextPage}&size=${page.size}`, processUsers));
    		$(".last").on("click", () => $.get(`/api/users?page=${lastPage}&size=${page.size}`, processUsers));
    }
});