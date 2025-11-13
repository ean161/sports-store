$(document).ready(function () {
    $("#disable-btn").on("click", async function () {
        disableVoucher();
    });
});

async function disableVoucher() {
    let res = await post("/manager/voucher/disable", {
        id: $("#vd-id").val()
    });

    if (res.code === 1) {
        list.ajax.reload();
        modal("voucher-details");
    }
}