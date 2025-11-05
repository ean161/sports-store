$(document).ready(function () {
    updateTotal();
});

function updateTotal() {
    let sum = 0;
    $("tbody tr[data-available='true']").each(function () {
        let totalText = $(this).find(".item-total").text();
        sum += parseFloat(totalText);
    });

    $(".payment span").text(sum + " ₫");
}