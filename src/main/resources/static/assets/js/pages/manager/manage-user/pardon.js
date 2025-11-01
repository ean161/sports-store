async function pardon(id) {
    let res = await post("/manager/user/pardon", {
        id: id
    });

    if (res.code === 1) {
        list.ajax.reload();
        modal("user-details");
    }
}