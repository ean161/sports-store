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

            let url = `/manager/product/list?page=${page}&size=${size}&sort=${sortCol},${sortDir}`;
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
            {data: "id", orderable: false},
            {data: "title", orderable: false},
            {data: "description", orderable: false},
            {data: "price", orderable: false},
            {
                data: "productType", render: (data, meta, row) => {
                    return data.name;
                }
            },
            {
                data: "productCollection", render: (data, meta, row) => {
                    return data.name;
                }
            },
            {data: "quantity", orderable: true},

            {
                data: "action", render: function (data, type, row) {
                    return `<button onclick="details('${row.id}')" class="inline-flex w-full justify-center rounded-md bg-blue-600 px-3 py-2 text-sm font-semibold text-white shadow-xs hover:bg-blue-500 sm:w-auto cursor-pointer  cursor-pointer">Details</button>`;
                }
            }

        ]
    });

    $("#edit-product-form").on("submit", async function (event) {
        event.preventDefault();
        await edit($(this).serialize());
    });

    $("#add-product-form").on("submit", async function (event) {
        event.preventDefault();
        await add($(this).serialize());
    });

    $("#add-product-btn-navbar").on("click", async function () {
        modal("add-product");
    });
});

async function loadDetails(id) {
    let res = await post("/manager/product/details", {
        id: id
    });

    $("#pd-product-header").html(res.data.name);
    $("#pd-id").val(res.data.id);
    $("#pd-title").val(res.data.title);
    $("#pd-description").val(res.data.description);
    $("#pd-price").val(res.data.price);
    $("#pd-type").val(res.data.productType.id);
    $("#pd-collection").val(res.data.productCollection.id);
    $("#pd-quantity").val(res.data.quantity);

    $("#pd-remove-btn").attr("onclick", `remove('${id}')`);
}



async function details(id) {
    await loadDetails(id);
    modal("product-details");
}

async function remove(id) {
    let res = await post("/manager/product/remove", {
        id: id
    });

    if (res.code === 1) {
        list.ajax.reload();
        modal("product-details");
    }
}

async function edit(data) {
    let res = await post("/manager/product/edit", data);

    if (res.code === 1) {
        list.ajax.reload();
        modal("product-details");
    }
}

async function add(data) {
    let res = await post("/manager/product/add", data);

    if (res.code === 1) {
        list.ajax.reload();
        $("#add-product-form")[0].reset();
        modal("add-product");
    }
}