$(document).ready(function() {
    $("#item-form").on("submit", async function (event) {
        event.preventDefault();
        var data = $(this).serialize();

        var res = await post("/cart/add", data);
    });

    $("#increase-btn").on("click", function() {
        let amount = parseInt($("#amount-input").val());
        $("#amount-input").val(amount + 1);
    });

    $("#decrease-btn").on("click", function() {
        let amount = parseInt($("#amount-input").val());
        if (amount > 1) {
            $("#amount-input").val(amount - 1);
        }
    });

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