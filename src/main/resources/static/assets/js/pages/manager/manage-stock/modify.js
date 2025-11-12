$(document).ready(function () {
    $("#modify-stock-btn-navbar").on("click", async function () {
        resetForm();
        toggleDetails(false);
        modal("modify-stock");
    });

    $("#modify-stock-form").on("submit", async function (event) {
        event.preventDefault();

        if (!$(this).find("#sa-product").val()) {
            out.error("Please select product before.");
            return;
        }
        await modify($(this).serialize());
    });

    $("#sa-product").on("change", async function () {
        $("#sa-properties").html(``);

        let id = $(this).val();
        if (!id) {
            return;
        }

        let prod = await post("/manager/stock/product", {
            id
        });

        loadProperties("sa", prod.data.productType.productPropertyFields, prod.data.productPropertyData);
    });

    $("#import-btn").on("click", function () {
        $("#modify-type").val("IMPORT");
        $("#modify-stock-form").submit();
    });

    $("#export-btn").on("click", function () {
        $("#modify-type").val("EXPORT");
        $("#modify-stock-form").submit();
    });

    $("#reset-btn").on("click", function () {
        $("#sa-quantity").val(0);
        $("#modify-type").val("SET");
        $("#modify-stock-form").submit();
    });

    $("#set-btn").on("click", function () {
        $("#modify-type").val("SET");
        $("#modify-stock-form").submit();
    });
});

async function modify(data) {
    let res = await post("/manager/stock/modify", data);

    if (res.code === 1) {
        list.ajax.reload();
        modal("modify-stock");
    }
}