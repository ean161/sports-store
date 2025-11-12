// $(document).ready(function () {
//     $("#ptd-add-property-btn").on("click", async function () {
//         addPropertyFieldInput("NEW-ID", "ptd-properties");
//     });
// });

async function loadDetails(id) {
    let res = await post("/manager/stock/details", {
        id: id
    });

    $("#sa-product").val(res.data.product.id);
    $("#sa-quantity").val(res.data.amount);
    loadProperties("sa", res.data.product.productType.productPropertyFields, res.data.product.productPropertyData);

    $("#form-header").text(`Manage stock ${res.data.product.title}`);

    let propsNames = ``;
    for (let item in res.data.productPropertyData) {
        let obj = res.data.productPropertyData[item];

        propsNames += `${obj.productPropertyField.name}: ${obj.data} <span class="text-gray-300">|</span> `;
        $(`#data-id-${obj.id}`).click();
    }

    $("#form-desc").html(propsNames.substring(0, propsNames.length - 38));
}

async function details(id) {
    resetForm();
    toggleDetails(true);
    await loadDetails(id);
    modal("modify-stock");
}