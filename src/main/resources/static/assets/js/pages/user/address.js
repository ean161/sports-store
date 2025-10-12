$(document).ready(function() {
    $("#edit-address-form").on("submit", async function (event) {
        event.preventDefault();

        var data = $(this).serialize();

        var res = await post("/address/add", data);
    });
});
