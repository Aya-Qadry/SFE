$(document).ready(function () {
    // add button
    $("#showModal").click(function (e) {
        e.preventDefault();
        $("#addModal").modal('show');
        $(function () {
            $('#addform').validate({
                rules: {
                    name: 'required',
                    description: 'required',
                },
                messages: {
                    name: 'Fill the category name field',
                    description: 'Fill the description field',
                },
                errorElement: 'p',
                errorPlacement: function (error, element) {
                    error.appendTo(element.closest('tr').find('p'));
                },
                submitHandler: function (form) {
                    // Handle the form submission here
                    form.submit();
                }
            });
        });
        $('#closeButton , #cancelButton').on('click', function (e) {
            $("#addModal").modal('hide');
        });
    });

    // delete button
    var modal = $('#removeModalCenter');
    var error = $('#error');

    // Attach a click event listener to the Delete button
    $('.tbl #deleteButton').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var category_id = $(this).data('category-id');
        // Perform the AJAX request to get additional data
        $.ajax({
            url: '/adminhome/products/categories/getCatErrors/' + category_id,
            method: 'GET',
            success: function (response) {
                // Update the modal content with the additional data
                var errorContent = '';
                for (var i = 0; i < response.length; i++) {
                    errorContent += response[i] + '<br>'; // Add line break after each error
                }
                error.html(errorContent);
                modal.modal('show'); // Show the modal

            }
        });

        $('#cancelButton, #closeButton').on('click', function () {
            modal.modal('hide'); // Hide the modal
        });

        // Perform the category deletion when the category confirms
        $('#delRef').attr('href', href);
    });

    //info button
    $('.tbl #infoButton').click(function (e) {
        e.preventDefault();
        var href = '';
        href = $(this).attr('href');
        // $("#infoButton").attr('href',href);
        $.get(href, function (category, status) {
            $('#infoModalCenter #name').val(category.name);
            $('#infoModalCenter #description').val(category.description);
        });
        $('#infoModalCenter').modal('show');
        $('#closeBtn').click(function () {
            $('#infoModalCenter').modal('hide');
        });
    });

    //edit button
    $('.tbl #editButton').click(function (e) {
        e.preventDefault();
        var href = '';
        href = $(this).attr('href');
        // $("#infoButton").attr('href',href);
        $.get(href, function (category, status) {
            $('#editModalCenter #name').val(category.name);
            $('#editModalCenter #category_id').val(category.category_id);
            $('#editModalCenter #description').val(category.description);
        });
        $('#editModalCenter').modal('show');
        $('#close').click(function () {
            $('#editModalCenter').modal('hide');
        });
        $(function () {
            $('#updateform').validate({
                rules: {
                    name: 'required',
                    description: 'required',
                },
                messages: {
                    name: 'Fill the category name field',
                    description: 'Fill the description field',
                },
                errorElement: 'p', 
                errorPlacement: function (error, element) {
                    error.appendTo(element.closest('tr').find('p'));
                },
                submitHandler: function (form) {
                    // Handle the form submission here
                    form.submit();
                }
            });
        });
       
    });
});

document.addEventListener("DOMContentLoaded", function () {
    // Store the original HTML content of the table
    var originalTableContent = document.getElementById("tbody").innerHTML;

    // Assign the value to a global variable for access from other functions
    window.originalTableContent = originalTableContent;
});

var searchinput = document.getElementById('search');
searchinput.addEventListener('input', function () {
    var inputValue = this.value.trim();
    if (inputValue === "") {
        restoreTable();
    } else {
        perfomLiveSearch(inputValue);
    }
});


function restoreTable() {
    var tableBody = document.getElementById("catgeoriesTable").getElementsByTagName("tbody")[0];
    tableBody.innerHTML = window.originalTableContent;
}


function perfomLiveSearch() {
    var searchQuery = document.getElementById("search").value;
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/adminhome/products/categories/search?query=" + encodeURIComponent(searchQuery), true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            // Update the table with the filtered data
            var filteredData = JSON.parse(xhr.responseText);
            updateTable(filteredData);
        }
    };

    xhr.send();
}

function updateTable(filteredData) {
    var tableBody = document.getElementById("catgeoriesTable").getElementsByTagName("tbody")[0];
    // Clear the existing table rows

    while (tableBody.firstChild) {
        tableBody.removeChild(tableBody.firstChild);
    }

    for (var i = 0; i < filteredData.length; i++) {
        var category = filteredData[i];
        var row = document.createElement("tr");

        var categoryIdCell = document.createElement("td");
        categoryIdCell.textContent = category.category_id;

        var categorynameCell = document.createElement("td");
        categorynameCell.textContent = category.name;

        var descriptionCell = document.createElement("td");

        descriptionCell.style.display='-webkit-box';
        descriptionCell.style.maxWidth='400px';
        descriptionCell.style.width='400px';
        descriptionCell.style.webkitLineClamp='1';
        descriptionCell.style.webkitBoxOrient='vertical';
        descriptionCell.style.overflow='hidden';
        descriptionCell.style.textOverflow='ellipsis';
        descriptionCell.textContent = category.description;

        var enabledCol = document.createElement('td');

        var icon = document.createElement('i');
        var container = document.createElement("span");
     
        var enabled = category.enabled;
        if (enabled == false) {
            icon.classList.add("far", "fa-times-circle");
            icon.style.color = '#cb1010';
            
            container.appendChild(icon); 
            enabledCol.appendChild(container);
        }
        if (enabled == true) {
            icon.classList.add('fas', 'fa-check-circle');
            icon.style.color = '#44a758'; 
            container.appendChild(icon); 
            enabledCol.appendChild(container);
        }

        var date = document.createElement("td");
        var formattedDate = new Date(category.date).toLocaleString('en-US', {
            year: 'numeric',
            month: 'short',
            day: 'numeric',
            hour: 'numeric',
            minute: 'numeric',
            hour12: false
        });
        date.textContent = formattedDate;


        var actionsCell = document.createElement("td");
        var deleteButton = document.createElement("a");
        deleteButton.setAttribute("href", "/adminhome/products/categories/deleteCategory/" + category.category_id);
        // deleteButton.setAttribute("class", "btn");
        // deleteButton.setAttribute("style", "padding: 0;");
        deleteButton.setAttribute("id", "deleteButton");

        var deleteIcon = document.createElement("i");
        deleteIcon.setAttribute("class", "fal fa-trash-alt");
        deleteIcon.setAttribute("style", "color: #ff0000;");
        deleteButton.appendChild(deleteIcon);
        deleteButton.addEventListener('click', function (e) {
            e.preventDefault();
            var href = $(this).attr('href');
            $('#removeModalCenter #delRef').attr('href', href);
            $('#removeModalCenter').modal('show');
            $('#cancelButton , #closeButton').on('click', function () {
                $('#removeModalCenter').modal('hide');
            });
        })
        actionsCell.appendChild(deleteButton);

        var infoButton = document.createElement("a");
        infoButton.setAttribute("href", "/adminhome/products/categories/showCategory/" + category.category_id);
        // infoButton.setAttribute("class", "btn");
        infoButton.setAttribute("id", "infoButton");

        var infoIcon = document.createElement("i");
        infoIcon.setAttribute("class", "far fa-info-circle");
        infoIcon.setAttribute("style", "color: #398fac;");
        infoButton.appendChild(infoIcon);
        infoButton.addEventListener('click', function (e) {
            e.preventDefault();
            var href = $(this).attr('href');
            $.get(href, function (category, status) {
                $('#infoModalCenter #name').val(category.name);
                $('#infoModalCenter #description').val(category.description);
            })
                .done(function () {
                    $('#infoModalCenter').modal('show');
                })
                .fail(function () {
                });

            $('#infoModalCenter #closeBtn').click(function () {
                $('#infoModalCenter').modal('hide');
            });
        });
        actionsCell.appendChild(infoButton);

        var updateButton = document.createElement("a");
        updateButton.setAttribute("href", "/adminhome/products/categories/editCategory/" + category.category_id);
        // updateButton.setAttribute("class", "btn");
        updateButton.setAttribute("style", "margin:2px ;");
        var updateIcon = document.createElement("i");
        updateIcon.setAttribute("class", "fal fa-edit");
        updateIcon.setAttribute("style", "color: #37a136;");
        updateButton.appendChild(updateIcon);
        updateButton.addEventListener('click', function (e) {
            e.preventDefault();
            var href = '';
            href = $(this).attr('href');
            // $("#infoButton").attr('href',href);
            $.get(href, function (category, status) {
                $('#editModalCenter #name').val(category.name);
                $('#editModalCenter #category_id').val(category.category_id);
                $('#editModalCenter #description').val(category.description);
            });
            $('#editModalCenter').modal('show');
            $(function () {
                $('#updateform').validate({
                    rules: {
                        name: 'required',
                        description: 'required',
                    },
                    messages: {
                        name: 'Fill the category name field',
                        description: 'Fill the description field',
                    },
                    errorElement: 'p',
                    errorPlacement: function (error, element) {
                        error.appendTo(element.closest('tr').find('p'));
                    },
                    submitHandler: function (form) {
                        // Handle the form submission here
                        form.submit();
                    }
                });
            });
            $('#editModalCenter #closeBtn').click(function () {
                $('#editModalCenter').modal('hide');
            });

        });

        actionsCell.appendChild(updateButton);


        row.appendChild(categoryIdCell);
        row.appendChild(categorynameCell);
        row.appendChild(descriptionCell);
        row.appendChild(enabledCol);
        row.appendChild(date);
        row.appendChild(actionsCell);
        // tableBody.children.setAttribute("style","adding : 22px 25px");

        tableBody.appendChild(row);
    }
} 