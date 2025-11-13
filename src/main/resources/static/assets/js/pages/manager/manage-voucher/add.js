$(document).ready(function () {
    $("#add-voucher-btn-navbar").on("click", async function () {
        $("#va-max-discount-value-container").hide();
        modal("add-voucher");
    });

    $("#va-discount-type").on("change", async function () {
        let val = $(this).val();

        if (val == "PERCENT") {
            $("#va-discount-value-title").text("Discount percent (%):");
            $("#va-max-discount-value").val(1);
            $("#va-max-discount-value-container").show();
        } else {
            $("#va-discount-value-title").text("Discount (₫):");
            $("#va-max-discount-value-container").hide();
            $("#va-max-discount-value").val(-1);
        }
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