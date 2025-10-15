$(document).ready(function() {
    $("#login-form").on("submit", async function (event) {
        event.preventDefault();
        var data = $(this).serialize();

        var res = await post("/manager/login", data);
    });
});