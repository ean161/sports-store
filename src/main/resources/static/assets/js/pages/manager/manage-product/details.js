async function loadDetails(id) {
    let res = await post("/manager/product/details", {
        id: id
    });

    $("#pd-product-header").html(res.data.title);
    $("#pd-id").val(res.data.id);
    $("#pd-title").val(res.data.title);
    $("#pd-description").val(res.data.description);
    $("#pd-price").val(res.data.price);
    $("#pd-type").val(res.data.productType.id);
    $("#pd-collection").val(res.data.productCollection.id);
    $("#pd-quantity").val(res.data.quantity);

    loadProperties("pd-properties", res.data.productType.productPropertyFields);

    $("#pd-remove-btn").attr("onclick", `remove('${id}')`);
}

async function details(id) {
    await loadDetails(id);
    modal("product-details");
}