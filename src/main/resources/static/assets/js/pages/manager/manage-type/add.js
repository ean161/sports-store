$(document).ready(function () {
    $("#add-product-type-btn-navbar").on("click", async function () {
        modal("add-product-type");
    });

    $("#add-product-type-form").on("submit", async function (event) {
        event.preventDefault();
        await add($(this).serialize());
    });
});

async function add(data) {
    let res = await post("/manager/type/add", data);

    if (res.code === 1) {
        list.ajax.reload();
        modal("add-product-type");
    }
}