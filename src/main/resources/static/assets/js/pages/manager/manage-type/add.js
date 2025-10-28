$(document).ready(function () {
    $("#add-product-type-btn-navbar").on("click", async function () {
        modal("add-product-type");
    });

    $("#add-product-type-form").on("submit", async function (event) {
        event.preventDefault();
        await add($(this).serialize());
    });

    $("#pta-add-property-btn").on("click", async function () {
        addProperty("pta-properties");
    });
});

async function add(data) {
    let res = await post("/manager/type/add", data);

    if (res.code === 1) {
        list.ajax.reload();
        $("#add-product-type-form")[0].reset();
        $("pta-properties").html(``);
        modal("add-product-type");
    }
}