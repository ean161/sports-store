$(document).ready(function () {
    $("#add-voucher-btn-navbar").on("click", async function () {
        modal("add-voucher");
    });

    $("#add-voucher-form").on("submit", async function (event) {
        event.preventDefault();
        await add($(this).serialize());
    });
});

async function add(data) {
    let res = await post("/manager/voucher/add", data);

    if (res.code === 1) {
        list.ajax.reload();
        $("#add-voucher-form")[0].reset();
        modal("add-voucher");
    }
}