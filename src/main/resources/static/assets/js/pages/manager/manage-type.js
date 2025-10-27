var list;

$(document).ready(function () {
    list = $("#list").DataTable({
        processing: true,
        serverSide: true,
        scrollX: true,
        language: datatableLang,
        columnDefs: [{targets: "_all", className: "dt-nowrap"}],
        ajax: function (data, callback, settings) {
            let page = Math.floor(data.start / data.length);
            let size = data.length;
            let sortCol = data.columns[data.order[0].column].data;
            let sortDir = data.order[0].dir;
            let search = data.search.value;

            let url = `/manager/type/list?page=${page}&size=${size}&sort=${sortCol},${sortDir}`;
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
        columns: [{data: "id"}, {data: "name", orderable: false}, {
            data: "action", orderable: false, render: function (data, type, row) {
                return `<button onclick="details('${row.id}')" class="inline-flex w-full justify-center rounded-md bg-blue-600 px-3 py-2 text-sm font-semibold text-white shadow-xs hover:bg-blue-500 sm:w-auto cursor-pointer  cursor-pointer">Details</button>`;
            }
        }]
    });

    $("#edit-product-type-form").on("submit", async function (event) {
        event.preventDefault();
        await edit($(this).serialize());
    });

    $("#add-product-type-form").on("submit", async function (event) {
        event.preventDefault();
        await add($(this).serialize());
    });

    $("#add-product-type-btn-navbar").on("click", async function () {
        modal("add-product-type");
    });

});


async function loadDetails(id) {
    let res = await post("/manager/type/details", {
        id: id
    });

    $("#ptd-product-type-header").html(res.data.name);
    $("#ptd-id").val(res.data.id);
    $("#ptd-name").val(res.data.name);
    $("#ptd-remove-btn").attr("onclick", `remove('${id}')`);
}

async function details(id) {
    await loadDetails(id);
    modal("product-type-details");
}

async function remove(id) {
    let res = await post("/manager/type/remove", {
        id: id
    });

    if (res.code === 1) {
        list.ajax.reload();
        modal("product-type-details");
    }
}

async function edit(data) {
    let res = await post("/manager/type/edit", data);

    if (res.code === 1) {
        list.ajax.reload();
        modal("product-type-details");
    }
}

async function add(data) {
    let res = await post("/manager/type/add", data);

    if (res.code === 1) {
        list.ajax.reload();
        modal("add-product-type");
    }
}