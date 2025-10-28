async function ban(id) {
    let res = await post("/manager/user/ban", {
        id: id
    });

    if (res.code === 1) {
        list.ajax.reload();
        modal("user-details");
    }
}