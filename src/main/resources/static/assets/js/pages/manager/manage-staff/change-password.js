$(document).ready(function () {
    $("#change-password-staff-form").on("submit", async function (event) {
        event.preventDefault();
        await changePassword($(this).serialize());
    });
});

function changePasswordForm(id, fullName) {
    modal("staff-details");
    $("#scp-id").val(id);
    $("#scp-full-name-header").html(fullName);
    modal("change-password-staff");
}

async function changePassword(data) {
    let res = await post("/manager/staff/change-password", data);

    if (res.code === 1) {
        list.ajax.reload();
        modal("change-password-staff");
    }
}