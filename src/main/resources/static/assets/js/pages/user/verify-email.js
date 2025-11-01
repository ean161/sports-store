$(document).ready(function() {

    $("#verify-email-btn").on("click", async function () {
        await requestVerifyEmail();
    })
});

async function requestVerifyEmail() {
    let res = await post("/verify-email/request");

    if (res.code === 1) {
        // list.ajax.reload();
        // modal("change-password-staff");
    }
}