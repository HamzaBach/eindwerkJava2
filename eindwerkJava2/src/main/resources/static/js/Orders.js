function EnableDisable(txtPassportNumber) {
        //Reference the Button.
        var btnSubmit = document.getElementById("saveOrderHeader");

        //Verify the OrderNumber has an order number.
        if (orderNumber.value.trim() != "Order nr°") {
            //Disable the Save header button when an order number is present.
            btnSubmit.disabled = true;
        } else {
            //Disable the TextBox when TextBox is empty.
            btnSubmit.disabled = false;
        }
    };