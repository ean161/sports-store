$(document).ready(function () {
    $("#edit-user-form").on("submit", async function (event) {
        event.preventDefault();
        await edit($(this).serialize());
    });
});

async function edit(data) {
    let res = await post("/manager/user/edit", data);

    if (res.code === 1) {
        list.ajax.reload();
        modal("user-details");
    }
}