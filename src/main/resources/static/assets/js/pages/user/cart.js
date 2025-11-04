$(document).ready(function () {
    $(".remove-item-btn").on("click", async function (event) {
        let id = $(this)[0].id.replaceAll("remove-item-", "");
        remove(id);
        $(`#item-${id}`).remove();
        updateTotal();
    });

    $(".increase-btn").on("click", async function () {
        let id = $(this).attr("id").replace("increase-", "");
        let qtyElem = $(`#quantity-${id}`);
        let quantity = parseInt(qtyElem.text()) + 1;

        qtyElem.text(quantity);
        await updateQuantity(id, quantity);

        updateItemTotal(id);
        updateTotal();
    });

    $(".decrease-btn").on("click", async function () {
        let id = $(this).attr("id").replace("decrease-", "");
        let qtyElem = $(`#quantity-${id}`);
        let quantity = parseInt(qtyElem.text());

        if (quantity > 1) {
            quantity--;
            qtyElem.text(quantity);
            await updateQuantity(id, quantity);

            updateItemTotal(id);
            updateTotal();
        }
    });
    updateTotal();
});

async function remove(id) {
    await post("/cart/remove", {
        id: id
    });
}

async function updateQuantity(id, quantity){
    await post("/cart/update", {
        id: id,
        quantity: quantity
    })

}

function updateItemTotal(id) {
    let row = $(`#item-${id}`);
    let priceText = row.find(".item-price").text().replace(/[^\d]/g, "");
    let price = parseFloat(priceText);

    let quantity = parseInt(row.find(`#quantity-${id}`).text()) ;
    let total = price * quantity;

    row.find(".item-total").text(total.toLocaleString("vi-VN") + " ₫");
}


function updateTotal() {
    let sum = 0;
    $(".item-total").each(function () {
        let totalText = $(this).text().replace(/[^\d]/g, "");
        sum += parseFloat(totalText) ;
    });

    $(".check-out button span").text(sum.toLocaleString("vi-VN") + " ₫");
}






