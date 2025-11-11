var selectedProp = {};

$(document).ready(function() {
    $("#item-form").on("submit", async function (event) {
        event.preventDefault();
        var data = $(this).serialize();

        var res = await post("/cart/add", data);
    });

    $("#increase-btn").on("click", function() {
        let amount = parseInt($("#amount-input").val());
        if (amount >= 100) {
            return;
        }

        $("#amount-input").val(amount + 1);
        calcPrice();
    });

    $("#decrease-btn").on("click", function() {
        let amount = parseInt($("#amount-input").val());
        if (amount > 1) {
            $("#amount-input").val(amount - 1);
        }

        calcPrice();
    });

    $(".select-option").on("click", function () {
        let propId = $(this).attr("alt");
        let propPrice = parseInt($(this).attr("data"));

        selectedProp[propId] = propPrice;
        calcPrice();
    })

    $(".add-item-btn").on("click", async function (event) {
        if ($("#login-btn").text().indexOf("Login") != -1) {
            window.location.href = "/login";
            return;
        }

        let id = $(this)[0].id.replaceAll("add-item-", "");
        $(`#item-${id}`).add();

        let itemCountElm = $("#in-cart-item-count");
        let itemCount = parseInt(itemCountElm.text());
        itemCountElm.text(itemCount + 1);

        updateTotal();
        await remove(id);
    });
});

function calcPrice() {
    let prodPrice = parseInt($("#root-prod-price").val());
    let qty = parseInt($("#amount-input").val());
    let totalPrice = prodPrice;

    for (let f in selectedProp) {
        totalPrice += selectedProp[f];
    }

    $("#display-prod-price").text(`${totalPrice * qty}₫`)
}