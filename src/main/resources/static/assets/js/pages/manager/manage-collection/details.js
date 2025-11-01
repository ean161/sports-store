async function loadDetails(id) {
    let res = await post("/manager/collection/details", {
        id: id
    });

    $("#cd-collection-header").html(res.data.name);
    $("#cd-id").val(res.data.id);
    $("#cd-name").val(res.data.name);
    $("#cd-remove-btn").attr("onclick", `remove('${id}')`);
}

async function details(id) {
    await loadDetails(id);
    modal("collection-details");
}