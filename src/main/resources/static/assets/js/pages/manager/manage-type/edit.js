$(document).ready(function () {
    $("#edit-product-type-form").on("submit", async function (event) {
        event.preventDefault();
        await edit($(this).serialize());
    });
});

async function edit(data) {
    let res = await post("/manager/type/edit", data);

    if (res.code === 1) {
        list.ajax.reload();
        $("#edit-product-type-form")[0].reset();
        $("ptd-properties").html(``);
        modal("product-type-details");
    }
}