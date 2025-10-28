async function loadDetails(id) {
    let res = await post("/manager/type/details", {
        id: id
    });

    $("#ptd-product-type-header").html(res.data.name);
    $("#ptd-id").val(res.data.id);
    $("#ptd-name").val(res.data.name);
    $("#ptd-remove-btn").attr("onclick", `remove('${id}')`);
}

async function details(id) {
    await loadDetails(id);
    modal("product-type-details");
}
