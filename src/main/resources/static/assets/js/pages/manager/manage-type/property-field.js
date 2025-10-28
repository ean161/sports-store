$(document).on("click", ".remove-property-btn", function (event) {
    $(this).parent().remove();
});

function addProperty(id, value = "") {
    $(`#${id}`).append(`<div class="flex space-x-2">
                <div class="flex w-9/10 items-center rounded-md bg-white pl-3 outline-1 -outline-offset-1 outline-gray-300 has-[input:focus-within]:outline-2 has-[input:focus-within]:-outline-offset-2 has-[input:focus-within]:outline-indigo-600">
                    <input name="fields" type="text" class="block min-w-0 grow py-1.5 pr-3 pl-1 text-base text-gray-900 placeholder:text-gray-400 focus:outline-none sm:text-sm/6" value="${value}" />
                </div>
                <span class="remove-property-btn w-1/10 inline-flex justify-center rounded-md bg-red-600 px-3 py-2 text-md font-semibold text-white shadow-xs hover:bg-red-500 cursor-pointer">
                    -
                </span>
            </div>`);
}