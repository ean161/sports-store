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
            {data: "id"},
            {data: "username", orderable: false},
            {data: "fullName", orderable: false},
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
                return `<span>${data == "FALSE" ? "Female" : "Male"}</span>`;
                }
            },
            {
                data: "action",
                render: function (data, type, row) {
                    return ` <span class="text-red-800 cursor-pointer rounded-xl bg-red-200">Set Status
                        </span>`;
                }
            },
            // { data: "createdDateDisplay", orderable: false },
            // {
            //     data: "actions",
            //     orderable: false,
            //     render: function (data, type, row) {
            //         if (data == true) {
            //             return `<button class="uk-button uk-button-secondary" onclick="revokeLicense(${row.id})">Thu hồi</button>`;
            //         }
            //
            //         return ``;
            //     }
            // }
        ]
    });
});