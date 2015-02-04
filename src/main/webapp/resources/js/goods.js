function changePriceState(checkbox, index) {
	var priceInput = document.getElementById("price" + index);
	if (checkbox.checked) {
		priceInput.value = ""
		priceInput.disabled = true;
	} else {
		priceInput.disabled = false;
	}
}

function validateForm(size) {
	var i, submit = true, errorClass = "red", modelPat = /^[a-zA-Z]{2}[\d]{3}$/, 
	datePat = /^(0?[1-9]|[12]\d|3[01])\-(0?[1-9]|[1][012])\-\d{4}$/, pricePat = /^[\d.,]*$/;
	$("input.red").removeClass(errorClass);
	for (i = 0; i < size; i++) {
		var producerInput = document.getElementById("producer" + i);
		if (producerInput.value === "") {
			producerInput.classList.add(errorClass);
			submit = false;
		}
		var modelInput = document.getElementById("model" + i);
		if (modelInput.value === "" || !modelPat.test(modelInput.value)) {
			modelInput.classList.add(errorClass);
			submit = false;
		}
		var dateInput = document.getElementById("date" + i);
		if (dateInput.value === "" || !datePat.test(dateInput.value)) {
			dateInput.classList.add(errorClass);
			submit = false;
		}
		var colorInput = document.getElementById("color" + i);
		if (colorInput.value === "") {
			colorInput.classList.add(errorClass);
			submit = false;
		}
		var priceInput = document.getElementById("price" + i);
		var notInStock = document.getElementById("notInStock" + i).checked;
		if ((priceInput.value.trim() === "" && !notInStock)
				|| !pricePat.test(priceInput.value.trim())) {
			priceInput.classList.add(errorClass);
			submit = false;
		}
	}
	return submit;
}
