$(document).ready(function () {
    $("#add-product-btn-navbar").on("click", async function () {
        modal("add-product");
    });

    $("#add-product-form").on("submit", async function (event) {
        event.preventDefault();
        await add($(this).serialize());
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