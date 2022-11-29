export function processError(response) {
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