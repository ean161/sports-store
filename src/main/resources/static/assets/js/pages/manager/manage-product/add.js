$(document).ready(function () {
    $("#add-product-btn-navbar").on("click", async function () {
        modal("add-product");
    });

    $("#add-product-form").on("submit", async function (event) {
        event.preventDefault();
        await add($(this).serialize());
    });

    $("#pa-type").on("change", async function (event) {
        $("#pa-properties").html(``);
        let selectedType = await post("/manager/product/get-type", {
            id: $(this).val()
        });

        loadProperties("pa", selectedType.data.productPropertyFields);
    });
});

async function add(data) {
    let res = await post("/manager/product/add", data);

    if (res.code === 1) {
        list.ajax.reload();
        $("#add-product-form")[0].reset();
        modal("add-product");
    }
}