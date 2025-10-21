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

            let url = `/manager/collection/list?page=${page}&size=${size}&sort=${sortCol},${sortDir}`;
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
            {data: "name", orderable: false},
            {
                data: "action",
                orderable: false,
                render: function (data, type, row) {
                    return `<button onclick="details('${row.id}')" class="inline-flex w-full justify-center rounded-md bg-blue-600 px-3 py-2 text-sm font-semibold text-white shadow-xs hover:bg-blue-500 sm:w-auto cursor-pointer">Details</button>`;
                }
            }

        ]
    });

    $("#edit-collection-form").on("submit", async function (event) {
        event.preventDefault();
        var data = $(this).serialize();

        var res = await post("/manager/collection/edit", data);
        if (res.code === 1) {
            list.ajax.reload();
        }
    });

    $("#add-collection-form").on("submit", async function (event) {
        event.preventDefault();
        var data = $(this).serialize();

        var res = await post("/manager/collection/add", data);
        if (res.code === 1) {
            list.ajax.reload();
        }
    });

    $("#add-collection-btn-navbar").on("click", async function () {
        openModal("add-collection");
    });
});


async function loadDetails(id) {
    let res = await post("/manager/collection/details", {
        id: id
    });

    $("#cd-collection-header").html(res.data.name);
    $("#cd-id").val(res.data.id);
    $("#cd-name").val(res.data.name);
}

async function details(id) {
    await loadDetails(id);

    openModal("collection-details");
}

async function add() {
    openModal("add-collection");
}
