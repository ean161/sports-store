$(document).ready(function () {
    // Submit feedback
    $("#feedback-form").on("submit", async function (event) {
        event.preventDefault();

        var feedbackId = $("#fb-product-id").data("feedback-id");
        var data = {
            id: feedbackId || $("#fb-product-id").val(),
            rating: $("#fb-rating").val(),
            content: $("#fb-content").val()
        };

        try {
            let url = feedbackId ? `/product/feedback/update` : `/product/feedback`;
            await post(url, data);
            setTimeout(() => window.location.reload(), 500);
        } catch (e) {
            showMessage(e.message || "Failed", "error");
        }
    });

    $(document).on("click", ".update-feedback-btn", function () {
        var id = $(this).data("id");
        var content = $(`#content-${id}`).val();
        var rating = $(`#rating-${id}`).val();

        $("#fb-content").val(content);
        $("#fb-rating").val(rating);
        $("#fb-product-id").data("feedback-id", id);

        $('html, body').animate({
            scrollTop: $("#feedback-form").offset().top - 100
        }, 500);
    });

    $(document).on("click", ".delete-feedback-btn", async function () {
        var id = $(this).data("id");
        try {
            await post(`/product/feedback/remove/${id}`);
            setTimeout(() => window.location.reload(), 500);
        } catch (e) {
            showMessage(e.message || "Failed", "error");
        }
    });
});
