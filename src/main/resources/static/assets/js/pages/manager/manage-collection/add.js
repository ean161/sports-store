$(document).ready(function () {
    $("#add-collection-btn-navbar").on("click", async function () {
        modal("add-collection");
    });

    $("#add-collection-form").on("submit", async function (event) {
        event.preventDefault();
        await add($(this).serialize());
    });
});

async function add(data) {
    let res = await post("/manager/collection/add", data);

    if (res.code === 1) {
        list.ajax.reload();
        modal("add-collection");
    }
}