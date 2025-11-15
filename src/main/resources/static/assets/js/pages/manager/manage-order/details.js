async function loadDetails(id) {
    let res = await post("/manager/order/details", {
        id: id
    });

    let items = ``;
    $(".cstate-btns").hide();
    $("#oh-detail-header").html(res.data.sign);
    $("#oh-id").val(res.data.id);
    $("#oh-user").val(res.data.user.username);
    $("#oh-sign").val(res.data.sign);
    $("#oh-status").val(res.data.status);
    $("#oh-payment-type").val(res.data.paymentType);
    $("#oh-total-price").val(`${res.data.totalPrice}₫`);
    $("#oh-created-at").val(res.data.createdAt);

    switch (res.data.status) {
        case "PENDING_APPROVAL":
            $("#cstate-pending-order").show();
            break;
        case "PENDING_ORDER":
            $("#cstate-in-transit").show();
            break;
        case "IN_TRANSIT":
            $("#cstate-success").show();
            $("#cstate-cancel").show();
            break;
        case "REFUNDING":
            $("#cstate-cancel").show();
            break;
        default:
            break;
    }

    $("#oh-modifier").val(res.data.manager ? res.data.manager.fullName : "Not edited yet.")

    for (let iKey in res.data.productSnapshots) {
        let item = res.data.productSnapshots[iKey];
        console.log(item);
        items += `<tr>
                <td>${item.title}</td>
                <td>${item.quantity}</td>
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