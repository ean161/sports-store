$(document).ready(function () {
    $("#apply-voucher-btn").on("click", async function () {
        checkVoucher();
    });
});

async function checkVoucher() {
    let res = await post("/voucher/check", {
        code: $("#voucher-code").val(),
        total: $("#cart-total-raw").text()
    });

    let dis = res.data;
    if (res.data) {
        dis = res.data;
    } else {
        dis = 0;
    }

    $("#discount").text(`-${dis}₫`);
    $("#total-cart").text(`${parseInt($("#cart-total-raw").text()) - res.data + parseInt($("#shipping-fee-raw").text())}`);
}




