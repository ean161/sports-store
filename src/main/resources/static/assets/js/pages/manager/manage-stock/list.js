var list;

$(document).ready(function () {
    list = $("#list").DataTable({
        processing: true,
        serverSide: true,
        language: datatableLang,
        columnDefs: [
            // { targets: "_all", className: "dt-nowrap" }
        ],
        ajax: function (data, callback, settings) {
            let page = Math.floor(data.start / data.length);
            let size = data.length;
            let sortCol = data.columns[data.order[0].column].data;
            let sortDir = data.order[0].dir;
            let search = data.search.value;

            let url = `/manager/stock/list?page=${page}&size=${size}&sort=${sortCol},${sortDir}`;
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
        columns: [
            {
                data: "id",
                orderable: false
            },
            {
                data: "product",
                orderable: false,
                render: function (data, type, row) {
                    return data.title;
                }
            },
            {
                data: "productPropertyData",
                orderable: false,
                render: function (data, type, row) {
                    let cell = ``;

                    for (let obj in data) {
                        let item = data[obj];
                        cell += `${item.productPropertyField.name}: ${item.data}<br>`;
                    }

                    return cell;
                }
            },
            {
                data: "amount",
                orderable: false
            },
            {
                data: "action",
                orderable: false,
                render: function (data, type, row) {
                    return `<button onclick="details('${row.id}')" class="inline-flex w-full justify-center bg-blue-600 px-3 py-2 text-sm font-semibold text-white shadow-xs hover:bg-blue-500 sm:w-auto cursor-pointer cursor-pointer">Edit</button>`;
                }
            }
        ]
    });
});

function loadProperties(zone, fields, datas = null) {
    let elm = $(`#${zone}-properties`);

    for (let i in fields) {
        let item = fields[i];

        elm.append(`<div class="mb-4">
                <div class="flex mb-4">
                    <h3 class="text-base font-semibold text-gray-900">
                        ${item.name}
                    </h3>
                </div>
                <div id="${zone}-property-${item.id}" class="space-y-4 flex space-x-8">
                </div>
            </div>`);
    }

    if (datas != null) {
        for (let j in datas) {
            let item = datas[j];
            addPropertyDataInput(item.id, item.productPropertyField.id, `${zone}-property-${item.productPropertyField.id}`, item.data, item.price);
        }
    }
}

function addPropertyDataInput(id, fieldId, htmlId, value = "", price = 0) {
    let elm = $(`#${htmlId}`);

    elm.append(`<div class="space-x-2 items-center w-auto">
            <input class="fields" name="${fieldId}" id="data-id-${id}" value="${id}" type="radio" />
            <label for="data-id-${id}">${value}</label>
        </div>`);
}

function resetForm() {
    $("#modify-stock-form")[0].reset();
    $("#sa-properties").html(``);
}

function toggleDetails(toggle) {
    if (!toggle) {
        $("#form-desc").hide();
        $("#form-header").text("Manage");
        $(".manage-fields").show();
    } else {
        $("#form-desc").show();
        $(".manage-fields").hide();
    }
}