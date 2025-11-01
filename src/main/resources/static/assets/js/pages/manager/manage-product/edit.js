$(document).ready(function () {
    $("#edit-product-form").on("submit", async function (event) {
        event.preventDefault();
        await edit($(this).serialize());
    });
});

async function edit(data) {
    let res = await post("/manager/product/edit", data);

    if (res.code === 1) {
        list.ajax.reload();
        modal("product-details");
    }
}