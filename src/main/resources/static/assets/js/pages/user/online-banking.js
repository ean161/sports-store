$(document).ready(function () {
    setInterval(paidCron, 3000);
});

async function paidCron() {
    let res = await post("/checkout/is-paid", {
        sign: $("#pack-sign").text()
    });

    if (res.data === true) {
        window.location.href = "/order-history";
    }
}