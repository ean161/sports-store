var selectedItem = [];

$(document).ready(function () {
    $(".item-select").on("click", function () {
        let target = $(this)[0].name;
        console.log(selectedItem.indexOf(target));
        if (selectedItem.indexOf(target) === -1) {
            selectedItem.push(target);
        } else {
            selectedItem = selectedItem.filter(item => item !== target);
        }

        console.log(selectedItem);
        updateTotal();
    })

    $(".remove-item-btn").on("click", async function (event) {
        let id = $(this)[0].id.replaceAll("remove-item-", "");
        $(`#item-${id}`).remove();

        let itemCountElm = $("#in-cart-item-count");
        let itemCount = parseInt(itemCountElm.text());
        itemCountElm.text(itemCount - 1);

        updateTotal();
        await remove(id);
    });

    $(".increase-btn").on("click", async function () {
        let id = $(this).attr("id").replace("increase-", "");
        let qtyElem = $(`#quantity-${id}`);
        let quantity = parseInt(qtyElem.text()) + 1;

        let res = await updateQuantity(id, quantity);
        if (quantity >= 100 || res.code === 0) {
            return;
        }

        qtyElem.text(quantity);

        updateItemTotal(id);
        updateTotal();
    });

    $(".decrease-btn").on("click", async function () {
        let id = $(this).attr("id").replace("decrease-", "");
        let qtyElem = $(`#quantity-${id}`);
        let quantity = parseInt(qtyElem.text());

        let res = await updateQuantity(id, quantity);
        if (quantity <= 1 || res.code === 0) {
            return;
        }

        quantity--;
        qtyElem.text(quantity);
        await updateQuantity(id, quantity);

        updateItemTotal(id);
        updateTotal();
    });

    $(".selected-cart-item").on("click", async function () {
        updateTotal();
    });

    // $("#select-item-to-checkout-form").on("submit", async function () {
    //     event.preventDefault();
    //     var data = $(this).serialize();
    //
    //     console.log(data);
    // });

    updateTotal();
});

async function remove(id) {
    await post("/cart/remove", {
        id: id
    });
}

async function updateQuantity(id, quantity){
    return await post("/cart/update", {
        id: id,
        quantity: quantity
    });
}

function updateItemTotal(id) {
    let row = $(`#item-${id}`);
    let price = parseInt(row.find(".item-price").text());
    let quantity = parseInt(row.find(`#quantity-${id}`).text());
    let total = price * quantity;

    row.find(".item-total").text(total);
}

function updateTotal() {
    let totalCart = 0;
    $(".item-line").each(function() {
        let isSelect = $(this).find(".item-select");
        let totalElm = $(this).find(".item-total");
        let priceElm = $(this).find(".item-price");
        let quantityElm = $(this).find(".item-quantity");

        let price = parseInt(priceElm.text()) || 0;
        let qty = parseInt(quantityElm.text()) || 0;
        let totalPrice = price * qty;

        totalElm.text(totalPrice + '₫');
        if (isSelect.prop("checked")) {
            totalCart += totalPrice;
        }
    });

    $("#totalCart ").text(totalCart + '₫');
}