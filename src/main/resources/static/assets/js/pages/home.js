$(document).ready(function () {
    $("#search-bar").on("change", async function (event) {
        let key = $(this).val();
        console.log(`Searching: ${key}`);
        window.location.href = `/search?keyword=${key}`;
    });
});