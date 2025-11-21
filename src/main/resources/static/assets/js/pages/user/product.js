var selectedProp = {};
var fullSelectedProp = {};

$(document).ready(function() {
    $("#item-form").on("submit", async function (event) {
        event.preventDefault();
        var data = $(this).serialize();

        if ($("#login-btn").text().indexOf("Login") != -1) {
            window.location.href = "/login";
            return;
        }

        var res = await post("/cart/add", data);
        $("#in-cart-item-count").text(res.data);
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
    });

    $(".real-option-data").on("click", async function () {
        let fieldPropId = $(this).attr("name");
        let propId = $(this).attr("id").replaceAll("property-data-id-", "");

        if ($(this).is(':checked'))
            fullSelectedProp[fieldPropId] = propId;
        else
            delete fullSelectedProp[fieldPropId];

        console.log(fullSelectedProp);
        $(".select-option").hide();
        let res = await post("/product/available-stock-prop", {
            id: $("#id").val(),
            props: Object.values(fullSelectedProp)
        });

        if (res.data.length) {
            for (let key in res.data) {
                let item = res.data[key];

                $(`.label-property-data-id-${item}`).show();
            }
        }
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