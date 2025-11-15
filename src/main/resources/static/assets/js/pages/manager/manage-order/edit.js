$(document).ready(function () {
    $("#edit-order-history-form").on("submit", async function (event) {
        event.preventDefault();
        await edit($(this).serialize());
    });

    $(".cstate-btns").on("click", async function () {
        await edit($(this).attr("data-state"));
    });
});

async function edit(status) {
    let res = await post("/manager/order/edit", {
        id: $("#oh-id").val(),
        status: status
    });

    if (res.code === 1) {
        modal("order-details");
        list.ajax.reload();
    }
}