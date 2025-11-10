async function loadDetails(id) {
    let res = await post("/manager/order/details", {
        id: id
    });

    $("#oh-detail-header").html(res.data.sign);
    $("#oh-id").val(res.data.id);
    $("#oh-user").val(res.data.user.username);
    $("#oh-sign").val(res.data.sign);
    $("#oh-status").val(res.data.status);
    $("#oh-payment-type").val(res.data.paymentType);
}

async function details(id) {
    await loadDetails(id);
    modal("order-details");
}