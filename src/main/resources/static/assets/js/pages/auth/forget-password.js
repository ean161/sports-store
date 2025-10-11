$(document).ready(function() {
    $("#request-forget-password-form").on("submit", async function (event) {
        event.preventDefault();
        var data = $(this).serialize();

        var res = await post("/forget-password", data);
    });

    $("#forget-password-form").on("submit", async function (event) {
        event.preventDefault();
        var data = $(this).serialize();

        var res = await post("/forget-password/forget", data);
    });
});