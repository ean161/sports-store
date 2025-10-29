function loadProperties(id, list) {
    let elm = $(`#${id}`);

    for (let i in list) {
        let item = list[i];

        elm.append(`<div>
                <div class="flex justify-between items-center mb-4" id="property-${item.id}">
                    <h3 class="text-base font-semibold text-gray-900">
                        ${item.name}
                    </h3>
                    <span onclick="addPropertyDataInput('${item.id}')" class="inline-flex justify-center rounded-md bg-blue-600 px-3 py-2 text-sm font-semibold text-white shadow-xs hover:bg-blue-500 cursor-pointer">
                        Add data
                    </span>
                </div>
            </div>`);
    }
}

function addPropertyDataInput(id) {

}