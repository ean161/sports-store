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