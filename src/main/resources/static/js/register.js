import {prepareCsrfRequestHeaders} from "./security-functions.js";
import {processError} from "./error-functions.js";

$(function() {
	const $createBtn = $("button.create");
	$createBtn.on("click", sendData);

	function sendData(e) {
		e.preventDefault();
		const firstName = $("#input-firstname").val();
		const lastName = $("#input-lastname").val();
		const patronymic = $("#input-patronymic").val();
		const email = $("#input-email").val();
		const personalInfo = {firstName, lastName, patronymic, email};
		const login = $("#input-login").val();
		const password = $("#input-password").val();
		const user = {login, personalInfo, password};
		const headers = prepareCsrfRequestHeaders();
		$.ajax({
			type: "POST",
			url: "/api/users/register", headers,
			data: JSON.stringify(user),
			success: processCreated,
			error: processError,
			dataType: "json",
			contentType: "application/json"
		});
	}

	function processCreated(data, status, $XHR) {
		$(".error").remove();
		if ($XHR.status === 201) {
			const uri = $XHR.getResponseHeader("Location");
			window.location.href = uri;
		} else {
			alert("Couldn't create user. Server error");
		}
	}



});
