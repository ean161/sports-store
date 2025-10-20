var out;

$(document).ready(function () {
    out = new Notyf();
});

const datatableLang = {
    search: "Tìm kiếm&ensp;",
    infoEmpty: "",
    emptyTable: "Không có dữ liệu",
    zeroRecords: "Không tìm thấy dữ liệu",
    info: "Hiển thị từ dòng _START_ đến _END_, tổng _TOTAL_ dòng",
    lengthMenu: ""
};

async function post(url, data = null) {
    let res = await $.ajax({
        url: url,
        type: "POST",
        data: data
    });

    if ("code" in res) {
        if ("message" in res) {
            if (res.code === 0) {
                out.error(res.message);
            } else if (res.code === 1) {
                out.success(res.message);
            }
        }

        if ("data" in res) {
            if (typeof res.data === 'object') {
                if ("redirect" in res.data && "time" in res.data) {
                    setTimeout(() => {
                        window.location.href = res.data.redirect;
                    }, res.data.time);
                }
            }
        }

        if (res.code === 2) {
            window.location.href = res.data;
        }
    }

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