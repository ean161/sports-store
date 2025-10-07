$(document).ready(function() {
    $("#edit-profile-form").on("submit", async function (event) {
        event.preventDefault();
        var data = $(this).serialize();

        var res = await post("/profile/edit", data);
    });
});