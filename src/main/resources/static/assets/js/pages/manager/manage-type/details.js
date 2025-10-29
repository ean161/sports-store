$(document).ready(function () {
    $("#ptd-add-property-btn").on("click", async function () {
        addProperty("ptd-properties");
    });
});

async function loadDetails(id) {
    let res = await post("/manager/type/details", {
        id: id
    });

    $("#ptd-product-type-header").html(res.data.name);
    $("#ptd-id").val(res.data.id);
    $("#ptd-name").val(res.data.name);

    $("#ptd-properties").html(`<p>No item found</p>`);
    for (let p in res.data.productPropertyFields) {
        addProperty("ptd-properties", res.data.productPropertyFields[p].name);
    }

    $("#ptd-remove-btn").attr("onclick", `remove('${id}')`);
}

async function details(id) {
    await loadDetails(id);
    modal("product-type-details");
}
