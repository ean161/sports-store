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
});

async function details(id) {
    let res = await post("/manager/user/user-details", {
        id: id
    });

    $("#ud-id").val(res.data.id);

    openModal("user-details");
}