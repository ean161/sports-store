$(document).ready(function () {
    $("#add-staff-btn-navbar").on("click", async function () {
        modal("add-staff");
    });

    $("#add-staff-form").on("submit", async function (event) {
        event.preventDefault();
        await add($(this).serialize());
    });
});

async function add(data) {
    let res = await post("/manager/staff/add", data);

    if (res.code === 1) {
        list.ajax.reload();
        $("#add-staff-form")[0].reset();
        modal("add-staff");
    }
}