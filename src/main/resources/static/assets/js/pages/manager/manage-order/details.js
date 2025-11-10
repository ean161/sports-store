async function loadDetails(id) {
    let res = await post("/manager/order/details", {
        id: id
    });

    let items = ``;
    $("#oh-detail-header").html(res.data.sign);
    $("#oh-id").val(res.data.id);
    $("#oh-user").val(res.data.user.username);
    $("#oh-sign").val(res.data.sign);
    $("#oh-status").val(res.data.status);
    $("#oh-payment-type").val(res.data.paymentType);

    for (let iKey in res.data.items) {
        let pack = res.data.items[iKey];
        let item = pack.productSnapshot;
        items += `<tr>
                <td>${item.title}</td>
                <td>${pack.quantity}</td>
                <td>`;

        for (let pKey in item.productPropertySnapshots) {
            let propertyItem = item.productPropertySnapshots[pKey];
            items += `${propertyItem.name}: ${propertyItem.data}<br>`;
        }

        items += `</td>
            </tr>
            <tr><td colspan="3"><hr></td></tr>`;
    }

    $("#oh-items").html(items);
}

async function details(id) {
    await loadDetails(id);
    modal("order-details");
}