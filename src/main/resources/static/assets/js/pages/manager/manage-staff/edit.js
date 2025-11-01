$(document).ready(function () {
    $("#edit-staff-form").on("submit", async function (event) {
        event.preventDefault();
        await edit($(this).serialize());
    });
});

async function edit(data) {
    let res = await post("/manager/staff/edit", data);

    if (res.code === 1) {
        list.ajax.reload();
        modal("staff-details");
    }
}