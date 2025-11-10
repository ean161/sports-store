$(document).ready(function () {
    $("#edit-order-history-form").on("submit", async function (event) {
        event.preventDefault();
        await edit($(this).serialize());
    });
});

async function edit(data) {
    let res = await post("/manager/order/edit", data);

    if (res.code === 1) {
        modal("order-details");
        list.ajax.reload();
    }
}