$(document).ready(function () {
    $("#edit-feedback-form").on("submit", async function (event) {
        event.preventDefault();
        await reply($(this).serialize());
    });
});

async function reply(data) {
    let res = await post("/manager/feedback/reply", data);

    if (res.code === 1) {
        modal("feedback-details");
        list.ajax.reload();
    }
}