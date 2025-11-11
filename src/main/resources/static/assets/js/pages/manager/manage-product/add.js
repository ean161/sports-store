$(document).ready(function () {
    $("#add-product-btn-navbar").on("click", async function () {
        modal("add-product");
    });

    $("#add-product-form").on("submit", async function (event) {
        event.preventDefault();
        await add($(this).serialize());
    });

    $("#pa-type").on("change", async function (event) {
        $("#pa-properties").html(``);
        let selectedType = await post("/manager/product/get-type", {
            id: $(this).val()
        });

        loadProperties("pa", selectedType.data.productPropertyFields);
    });
});

async function add(data) {
    let res = await post("/manager/product/add", data);

    if (res.code === 1) {
        list.ajax.reload();
        $("#add-product-form")[0].reset();
        modal("add-product");
    }
}

function loadProperties(zone, fields, datas = null) {
    let elm = $(`#${zone}-properties`);

    for (let i in fields) {
        let item = fields[i];

        elm.append(`<div class="mb-4">
                <div class="flex justify-between items-center mb-4">
                    <h3 class="text-base font-semibold text-gray-900">
                        ${item.name}
                    </h3>
                    <span onclick="addPropertyDataInput('NEW-ID', '${item.id}', '${zone}-property-${item.id}')" class="inline-flex justify-center bg-blue-600 px-3 py-2 text-sm font-semibold text-white shadow-xs hover:bg-blue-500 cursor-pointer">
                        Add data
                    </span>
                </div>
                <div id="${zone}-property-${item.id}" class="space-y-4">
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