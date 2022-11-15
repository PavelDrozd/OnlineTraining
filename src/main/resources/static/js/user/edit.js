$(function() {
	const $createBtn = $("button.create");
	$createBtn.on("click", sendData);

	function sendData(e) {
		e.preventDefault();
		const user = $("user");
		const firstName = $("#input-firstname").val();
		const lastName = $("#input-lastname").val();
		const email = $("#input-email").val();
		const personalInfo = {firstName, lastName, email};
		const user = {login, personalInfo, password};
		const id = $("#input-id").val();
		$.ajax({
			type: "PUT",
			url: "/api/users/{id}",
			data: JSON.stringify(user),
			success: processCreated,
			error: processError,
			dataType: "json",
			contentType: "application/json"
		});
	}

	function processError(response) {
    		$(".error").remove();
    		if (response.status === 422) {
    			const validationError = response.responseJSON;
    			for (const field in validationError.messages) {
    				validationError.messages[field].forEach(msg => {
    					$("form").prepend($(`<div class="error">${field}: ${msg}</div>`));
    				})
    			}
    		}
    }

	function processCreated(data, status, $XHR) {
		$(".error").remove();
		if ($XHR.status === 204) {
			const uri = $XHR.getResponseHeader("Location");
			window.location.href = uri;
		} else {
			alert("Couldn't create book. Server error");
		}
	}

});
