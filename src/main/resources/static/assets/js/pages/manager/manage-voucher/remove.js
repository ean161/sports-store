async function remove(id) {
    let res = await post("/manager/voucher/remove", {
        id: id
    });

    if (res.code === 1) {
        list.ajax.reload();
        modal("voucher-details");
    }
}