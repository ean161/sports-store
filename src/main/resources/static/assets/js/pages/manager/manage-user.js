var list;

$(document).ready(function () {
    list = $("#list").DataTable({
        processing: true,
        serverSide: true,
        scrollX: true,
        language: datatableLang,
        columnDefs: [
            {targets: "_all", className: "dt-nowrap"}
        ],
        ajax: function (data, callback, settings) {
            let page = Math.floor(data.start / data.length);
            let size = data.length;
            let sortCol = data.columns[data.order[0].column].data;
            let sortDir = data.order[0].dir;
            let search = data.search.value;

            let url = `/manager/user/list?page=${page}&size=${size}&sort=${sortCol},${sortDir}`;
            if (search) {
                url += `&search=${encodeURIComponent(search)}`;
            }

            $.getJSON(url, function (res) {
                callback({
                    draw: data.draw,
                    recordsTotal: res.totalElements,
                    recordsFiltered: res.totalElements,
                    data: res.content
                });
            });
        },
        order: [[0, "desc"]],
        columns: [
            { data: "id", orderable: false },
            { data: "username", orderable: false },
            { data: "fullName", orderable: false },
            {
                data: "status",
                orderable: false,
                render: function (data, type, row) {
                    return `<span class="text-${
                        data == "ACTIVE"
                            ? `green`
                            : data == "BANNED"
                                ? `red`
                                : `grey`
                    }-800">${data}</span>`;
                }
            },
            {
                data: "gender",
                orderable: false,
                render: function (data, type, row) {
                    return `<span>${!data ? `Female` : `Male`}</span>`;
                }
            },
            {
                data: "action",
                orderable: false,
                render: function (data, type, row) {
                    return `<button onclick="details('${row.id}')" class="inline-flex w-full justify-center rounded-md bg-blue-600 px-3 py-2 text-sm font-semibold text-white shadow-xs hover:bg-blue-500 sm:w-auto cursor-pointer">Details</button>`;
                }
            }
        ]
    });

    $("#edit-user-form").on("submit", async function (event) {
        event.preventDefault();
        var data = $(this).serialize();

        var res = await post("/manager/user/edit", data);

        if (res.code === 1) {
            list.ajax.reload();
        }
    });
});

async function loadDetails(id) {
    let res = await post("/manager/user/details", {
        id: id
    });

    $("#ud-username-header").html(res.data.username);
    $("#ud-id").val(res.data.id);
    $("#ud-username").val(res.data.username);
    $("#ud-full-name").val(res.data.fullName);
    $("#ud-status").val(res.data.status);
    $("#ud-gender").val(!res.data.gender ? "Female" : "Male");

    if (res.data.status === "BANNED") {
        $("#action-btns").html(`<button id="pardon-btn" type="button" command="close" class="inline-flex justify-center rounded-md bg-blue-600 px-3 py-2 text-sm font-semibold text-white shadow-xs hover:bg-blue-500">Pardon</button>`);
        $("#pardon-btn").attr("onclick", `pardon('${res.data.id}')`);
    } else if(res.data.status === "ACTIVE"){
        $("#action-btns").html(`<button id="ban-btn" type="button" command="close" class="inline-flex justify-center rounded-md bg-red-600 px-3 py-2 text-sm font-semibold text-white shadow-xs hover:bg-red-500">Ban</button>`);
        $("#ban-btn").attr("onclick", `ban('${res.data.id}')`);
    }
}

async function details(id) {
    await loadDetails(id);

    openModal("user-details");
}

async function ban(id) {
    let res = await post("/manager/user/ban", {
        id: id
    });

    loadDetails(id);
    list.ajax.reload();
}

async function pardon(id) {
    let res = await post("/manager/user/pardon", {
        id: id
    });

    loadDetails(id);
    list.ajax.reload();
}