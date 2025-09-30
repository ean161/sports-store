$(document).ready(function() {
    $("#request-recovery-password-form").on("submit", async function (event) {
        event.preventDefault();
        var data = $(this).serialize();

        var res = await post("/recovery-password", data);
    });

    $("#recovery-password-form").on("submit", async function (event) {
        event.preventDefault();
        var data = $(this).serialize();

        var res = await post("/recovery-password/recovery", data);
    });
});