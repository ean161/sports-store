$(document).ready(function () {
    $("#search-bar").on("change", async function (event) {
        let key = $(this).val();

        window.location.href = `/search?keyword=${key}`;
    });
});