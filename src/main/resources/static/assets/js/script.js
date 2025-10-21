var out;

$(document).ready(function () {
    out = new Notyf();
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
    let res = await $.ajax({
        url: url,
        type: "POST",
        data: data
    });

    if (res && typeof res === "object" && "code" in res) {
        if ("message" in res && res.message != null) {
            if (res.code === 0) {
                out.error(res.message);
            } else if (res.code === 1) {
                out.success(res.message);
            }
        }

        if ("data" in res) {
            if (res.data && typeof res.data === 'object' && !Array.isArray(res.data)) {
                if ("redirect" in res.data && res.data.redirect != null) {
                    setTimeout(() => {
                        window.location.href = res.data.redirect;
                    }, res.data.time || 0);
                }
            }
        }

        if (res.code === 2) {
            window.location.href = res.data;
        }
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
        window.location.href = `/collection/${id.split("-")[1]}`;
    }
}

function openModal(id) {
    $(`#${id}-btn`).click();
}