$(document).ready(function () {
    $("#add-stock-btn-navbar").on("click", async function () {
        modal("add-stock");
    });

    $("#add-stock-form").on("submit", async function (event) {
        event.preventDefault();
        await add($(this).serialize());
    });

    $("#sa-product").on("change", async function () {
        $("#sa-properties").html(``);

        let id = $(this).val();
        if (!id) {
            return;
        }

        let prod = await post("/manager/stock/product", {
            id
        });

        loadProperties("sa", prod.data.productType.productPropertyFields, prod.data.productPropertyData);
    });
});

async function add(data) {
    let res = await post("/manager/stock/add", data);

    if (res.code === 1) {
        list.ajax.reload();
        modal("add-stock");
    }
}

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
            <input name="${fieldId}" id="data-id-${id}" value="${id}" type="radio" />
            <label for="data-id-${id}">${value}</label>
        </div>`);
}