$(document).ready(function () {
    $("#active-btn").on("click", async function () {
        activeVoucher();
    });
});

async function activeVoucher() {
    let res = await post("/manager/voucher/active", {
        id: $("#vd-id").val()
    });

    if (res.code === 1) {
        list.ajax.reload();
        modal("voucher-details");
    }
}