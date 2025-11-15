$(document).ready(function () {
    $("#add-address-form").on("submit", async function (event) {
        event.preventDefault();
        var data = $(this).serialize();

        var res = await post("/address/add", data);
    });

    $("#edit-address-form").on("submit", async function (event) {
        event.preventDefault();
        var data = $(this).serialize();

        var res = await post("/address/edit", data);
    });

    $(".set-default-btn").on("click", async function () {
        var id = $(this).data("id");
        var res = await post(`/address/set-default/${id}`);
    });

    $("#province-select").on("change", async function (e) {
        var provinceId = $(this).val();
        var wardSelect = $("#ward-select");

        wardSelect.html('<option value="">-- Ward --</option>');

        if (provinceId) {
            try {
                var res = await post("/address/wards-by-province", { provinceId: provinceId });
                var wards = Object.values(res);

                if (Array.isArray(wards)) {
                    wards.forEach(function(ward) {
                        wardSelect.append(
                            $('<option></option>')
                                .attr('value', ward.id)
                                .text(ward.name)
                        );
                    });
                } else {
                    console.error("Cannot convert to array:", wards);
                }
            } catch (e) {
                console.error("Error loading wards:", e);
                alert("Error loading wards: " + e.message);
            }
        }
    });

    $(".delete-address-btn").on("click", async function () {
        var id = $(this).data("id");

        try {
            var res = await post(`/address/delete/${id}`)
            window.location.reload();
        } catch (e) {
            alert("Error: " + e.message);
        }
});
});