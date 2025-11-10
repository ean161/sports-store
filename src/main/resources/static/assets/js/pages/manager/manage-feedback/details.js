async function loadDetails(id) {
    let res = await post("/manager/feedback/details", {
        id: id
    });

    $("#fd-header").html(res.data.title);
    $("#fd-id").val(res.data.id);
    $("#fd-user").val(res.data.user.username);
    $("#fd-product").val(res.data.product.title);
    $("#fd-comment").val(res.data.comment);
    $("#fd-rating").val(res.data.rating);
    $("#fd-remove-btn").attr("onclick", `remove('${id}')`);
}

async function details(id) {
    await loadDetails(id);
    modal("feedback-details");
}