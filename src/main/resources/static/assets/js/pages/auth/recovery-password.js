$(document).ready(function() {
    $("#recovery-password-form").on("submit", async function (event) {
        event.preventDefault();
        var data = $(this).serialize();

        var res = await post("/recovery-password", data);
    });
});