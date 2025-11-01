async function loadDetails(id) {
    let res = await post("/manager/staff/details", {
        id: id
    });

    $("#sd-username-header").html(res.data.username);
    $("#sd-id").val(res.data.id);
    $("#sd-username").val(res.data.username);
    $("#sd-password").val(res.data.password);
    $("#sd-full-name").val(res.data.fullName);

    $("#sd-remove-btn").attr("onclick", `remove('${id}')`);
    $("#sd-changePassword-btn").attr("onclick", `changePasswordForm('${id}', '${res.data.fullName}')`);
}

async function details(id) {
    await loadDetails(id);
    modal("staff-details");
}