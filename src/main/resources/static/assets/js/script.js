var out;

$(document).ready(function () {
    out = new Notyf();

    $(".close-modal-btn").on("click", (e) => {
        modal(e.target.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.id);
    });

    $("#search-bar").on("change paste keyup", function () {
        console.log($(this).val());

    });
});

const datatableLang = {
    search: "Search&ensp;",
    infoEmpty: "",
    emptyTable: "No data available",
    zeroRecords: "No matching records found",
    info: "Showing _START_ to _END_ of _TOTAL_ entries",
    lengthMenu: ""
};

async function post(url, data = null) {
    let res = { code: 0 };

    try {
        const response = await $.ajax({
            url,
            type: "POST",
            data,
            dataType: "json",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8"
        });

        res = { ...response, code: 1 };

        if (response?.message) {
            out.success(response.message);
        }

    } catch (xhr) {
        const errorResponse = xhr?.responseJSON || {};
        const message = errorResponse.message || errorResponse.error || "Something was wrong.";

        res = { ...errorResponse, code: 0 };
        out.error(message);
    }

    if (res?.data && typeof res.data === "object" && !Array.isArray(res.data)) {
        const { redirect, time = 0 } = res.data;
        if (redirect) {
            setTimeout(() => (window.location.href = redirect), time);
        }
    }

    if (res.code === 2 && res.data) {
        window.location.href = res.data;
    }

    return res;
}

function scrollToId(id) {
    const element = document.getElementById(id);
    if (element) {
        element.scrollIntoView({
            behavior: "smooth",
            block: "center",
        });
        element.focus({preventScroll: true});
    } else {
        console.warn(`Element with id "${id}" not found.`);
    }
}

function productCollectionMove(id) {
    if (document.getElementById(id)) {
        scrollToId(id);
    } else {
        window.location.href = `/collection/${id.replaceAll("collection-", "")}`;
        console.log(id);
    }
}

function modal(id) {
    let elm = $(`#${id}`);

    if (elm.is(":hidden")) {
        elm.fadeIn(200);
    } else {
        elm.fadeOut(200);
    }
}
