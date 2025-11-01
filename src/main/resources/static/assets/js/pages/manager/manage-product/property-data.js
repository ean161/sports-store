$(document).on("click", ".remove-property-btn", function (event) {
    $(this).parent().remove();
});

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

function addPropertyDataInput(id, fieldId, htmlId, value = "", price = 0) {
    let elm = $(`#${htmlId}`);

    elm.append(`<div class="flex space-x-2">
            <input name="field-ids" value="${fieldId}" hidden />
            <input name="data-ids" value="${id}" hidden />
            <div class="flex w-6/10 items-center rounded-md bg-white pl-3 outline-1 -outline-offset-1 outline-gray-300 has-[input:focus-within]:outline-2 has-[input:focus-within]:-outline-offset-2 has-[input:focus-within]:outline-indigo-600">
                <input placeholder="Data" name="datas" type="text" class="block min-w-0 grow py-1.5 pr-3 pl-1 text-base text-gray-900 placeholder:text-gray-400 focus:outline-none sm:text-sm/6" value="${value}" />
            </div>
            <div class="flex w-3/10 items-center rounded-md bg-white pl-3 outline-1 -outline-offset-1 outline-gray-300 has-[input:focus-within]:outline-2 has-[input:focus-within]:-outline-offset-2 has-[input:focus-within]:outline-indigo-600">
                <input placeholder="Price" name="prices" type="number" class="block min-w-0 grow py-1.5 pr-3 pl-1 text-base text-gray-900 placeholder:text-gray-400 focus:outline-none sm:text-sm/6" value="${price}" />
            </div>
            <span class="remove-property-btn w-1/10 inline-flex justify-center bg-red-600 px-3 py-2 text-md font-semibold text-white shadow-xs hover:bg-red-500 cursor-pointer">
                -
            </span>
        </div>`);
}