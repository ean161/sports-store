async function loadDetails(id) {
    let res = await post("/manager/voucher/details", {
        id: id
    });
    $("#vd-voucher-header").html(res.data.name);
    $("#vd-id").val(res.data.id);
    $("#vd-code").val(res.data.code);
    $("#vd-created-date").val(res.data.createdAt);
    $("#vd-discount-type").val(res.data.discountType);
    $("#vd-discount-value").val(res.data.discountValue);
    $("#vd-expired-date").val(res.data.expiredAt);
    $("#vd-max-discount-value").val(res.data.maxDiscountValue);
    $("#vd-max-used").val(res.data.maxUsedCount);
    $("#vd-status").val(res.data.status);
    $("#vd-used-count").val(res.data.usedCount);

    $("#vd-remove-btn").attr("onclick", `remove('${id}')`);
}

async function details(id) {
    await loadDetails(id);
    modal("voucher-details");
}