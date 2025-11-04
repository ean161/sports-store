$(document).ready(function() {
    $("#item-form").on("submit", async function (event) {
        event.preventDefault();
        var data = $(this).serialize();

        var res = await post("/cart/add", data);
    });

    $("#increase-btn").on("click", function() {
        let amount = parseInt($("#amount-input").val());
        $("#amount-input").val(amount + 1);
    });

    $("#decrease-btn").on("click", function() {
        let amount = parseInt($("#amount-input").val());
        if (amount > 1) {
            $("#amount-input").val(amount - 1);
        }
    });

});