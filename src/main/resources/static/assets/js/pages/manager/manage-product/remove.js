async function remove(id) {
    let res = await post("/manager/product/remove", {
        id: id
    });

    if (res.code === 1) {
        list.ajax.reload();
        modal("product-details");
    }
}