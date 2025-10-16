$(document).ready(function() {
    $("#change-password-form").on("submit", async function (event) {
        event.preventDefault();
        var data = $(this).serialize();

        var res = await post("/change-password", data);
    });
});
