async function loadDetails(id) {
    $("#voucher-details-form")[0].reset();
    let res = await post("/manager/voucher/details", {
        id: id
    });

    if (res.data.expiredAt == -1) {
        $("#vd-expired-at-container").hide();
    } else {
        $("#vd-expired-at-container").show();
    }

    if (res.data.discountType == "STATIC") {
        $("#vd-max-discount-value-container").hide();
    } else {
        $("#vd-max-discount-value-container").show();
    }

    if (res.data.status == "ACTIVE") {
        $("#active-btn").hide();
        $("#disable-btn").show();
    } else {
        $("#active-btn").show();
        $("#disable-btn").hide();
    }

    $("#vd-voucher-header").html(res.data.code);
    $("#vd-id").val(res.data.id);
    $("#vd-code").val(res.data.code);
    $("#vd-discount-type").val(res.data.discountType);
    $("#vd-discount-value").val(`${res.data.discountValue}${res.data.discountType == "PERCENT" ? "%" : "₫"}`);
    $("#vd-max-discount-value").val(res.data.maxDiscountValue);
    $("#vd-used").val(`${res.data.usedCount}/${res.data.maxUsedCount}`);
    $("#vd-status").val(res.data.status);
    $("#vd-expired-at").val(res.data.expiredAt);
    $("#vd-created-at").val(res.data.createdAt);

    $("#vd-remove-btn").attr("onclick", `disableVoucher('${id}')`);
}

async function details(id) {
    await loadDetails(id);
    modal("voucher-details");
}