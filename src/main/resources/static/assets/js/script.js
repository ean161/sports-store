var out;

$(document).ready(function() {
    out = new Notyf();
});

async function post(url, data = null) {
    let res = await $.ajax({
        url: url,
        type: "POST",
        data: data
    });

    if ("message" in res && res.message != null) {
        if (res.code === 0) {
            out.error(res.message);
        } else if (res.code === 1) {
            out.success(res.message);
        }
    }
}