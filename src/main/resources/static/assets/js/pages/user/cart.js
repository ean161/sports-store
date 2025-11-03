$(document).ready(function () {
    $(".remove-item-btn").on("click", async function (event) {
        let id = $(this)[0].id.replaceAll("remove-item-", "");
        remove(id);

        $(`#item-${id}`).remove();
    });
});

async function remove(id) {
    await post("/cart/remove", {
        id: id
    });
}