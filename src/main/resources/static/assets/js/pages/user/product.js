$(document).ready(function() {
    $("#item-form").on("submit", async function (event) {
        event.preventDefault();
        var data = $(this).serialize();

        var res = await post("/cart/add", data);
    });
});