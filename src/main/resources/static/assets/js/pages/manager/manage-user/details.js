async function loadDetails(id) {
    let res = await post("/manager/user/details", {
        id: id
    });

    $("#ud-username-header").html(res.data.username);
    $("#ud-id").val(res.data.id);
    $("#ud-username").val(res.data.username);
    $("#ud-full-name").val(res.data.fullName);
    $("#ud-status").val(res.data.status);

    if (res.data.gender) {
        $("#ud-gender-male").attr("checked", true);
    } else {
        $("#ud-gender-female").attr("checked", true);
    }

    if (res.data.status === "BANNED") {
        $("#action-btns").html(`<button id="pardon-btn" type="button" class="inline-flex justify-center rounded-md bg-blue-600 px-3 py-2 text-sm font-semibold text-white shadow-xs hover:bg-blue-500  cursor-pointer">Pardon</button>`);
        $("#pardon-btn").attr("onclick", `pardon('${res.data.id}')`);
    } else if (res.data.status === "ACTIVE") {
        $("#action-btns").html(`<button id="ban-btn" type="button" class="inline-flex justify-center rounded-md bg-red-600 px-3 py-2 text-sm font-semibold text-white shadow-xs hover:bg-red-500  cursor-pointer">Ban</button>`);
        $("#ban-btn").attr("onclick", `ban('${res.data.id}')`);
    }
}

async function details(id) {
    await loadDetails(id);
    modal("user-details");
}