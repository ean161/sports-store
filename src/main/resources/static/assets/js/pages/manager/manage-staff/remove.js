async function remove(id) {
    let res = await post("/manager/staff/remove", {
        id: id
    });

    if (res.code === 1) {
        list.ajax.reload();
        modal("staff-details");
    }
}