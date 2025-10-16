$(document).ready(function() {
    $("#add-address-form").on("submit", async function (event) {
        event.preventDefault();
        var data = $(this).serialize();

        var res = await post("/address/add", data);
    });
});
