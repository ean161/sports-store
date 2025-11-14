// $(document).ready(function () {
//
//     // === APPLY VOUCHER ===
//     $("#apply-voucher-btn").on("click", async function (event) {
//         event.preventDefault();
//
//         const $btn = $(this);
//         if ($btn.prop("disabled")) return;
//         $btn.prop("disabled", true).text("Applying...");
//
//         const code = $("#voucher-code").val().trim();
//         const totalCart = $("#total-cart").text().replace(/[₫,]/g, "").trim();
//         const packId = $("#pack-id").val(); // Lấy packId từ hidden input
//
//         if (!code) {
//             alert("Please enter a voucher code!");
//             $btn.prop("disabled", false).text("Apply");
//             return;
//         }
//
//         try {
//             const res = await post("/voucher/apply", { code, totalCart, packId });
//
//             if (res && res.success) {
//                 const discount = res.data.discount || 0;
//                 const discountedPrice = res.data.discountedPrice;
//
//                 $("#discount").text(`-${discount}₫`);
//                 $("#total-cart").text(`${discountedPrice}₫`);
//
//                 alert(res.message || "Voucher applied successfully");
//             } else {
//                 alert(res.message || "Invalid voucher code");
//             }
//         } catch (err) {
//             console.error("Apply voucher error:", err);
//             alert(err.message || "Invalid voucher code");
//         } finally {
//             $btn.prop("disabled", false).text("Apply");
//         }
//     });
//
// });





$(document).ready(function () {
    let voucherApplied = false;

    $("#apply-voucher-btn").on("click", async function (event) {
        event.preventDefault();

        if (voucherApplied) {
            // out.error("Voucher already applied!");
            return;
        }

        const success = await applyVoucher();
        if (success) voucherApplied = true;
    });
});

async function applyVoucher() {
    const code = $("#voucher-code").val().trim();
    const totalCart = $("#total-cart").text().replace(/[₫,]/g, "").trim();

    if (!code) {
        out.error("Please enter a voucher code.");
        return false;
    }

    let res = await post("/voucher/apply", {
        code: code,
        totalCart: totalCart
    });

    if (res.code === 1 && res.data) {
        const discount = res.data.discount || 0;
        const discountedPrice = res.data.discountedPrice || totalCart;

        $(".text-red-600.font-medium").text(`-${discount.toLocaleString()}₫`);
        $("#total-cart").text(`${discountedPrice.toLocaleString()}₫`);
        return true;
    } else {
        // out.error(res.message || "Invalid voucher code");
        return false;
    }
}






