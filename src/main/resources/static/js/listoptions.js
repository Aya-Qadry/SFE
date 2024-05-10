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
                    price: 'required',
                },
                messages: {
                    name: 'Fill the shipping type field',
                    description: 'Fill the description field',
                    price: 'Fill the price field',
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
    var modal = $('#removeModalCenter');
    var error = $('#error');

    // Attach a click event listener to the Delete button
    $('.tbl #deleteButton').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var optionId = $(this).data('option-id');

        $.ajax({
            url: '/adminhome/configuration/getOptionErrors/' + optionId,
            method: 'GET',
            success: function (response) {
                // Update the modal content with the additional data
                error.text(response);
                modal.modal('show'); // Show the modal
            }
        });

        $('#cancelButton, #closeButton').on('click', function () {
            modal.modal('hide'); // Hide the modal
        });

        // Perform the user deletion when the user confirms
        $('#delRef').attr('href', href);
    });

    //info button
    $('.tbl #infoButton').click(function (e) {
        e.preventDefault();
        var href = '';
        href = $(this).attr('href');
        // $("#infoButton").attr('href',href);
        $.get(href, function (option, status) {
            $('#infoModalCenter #name').val(option.name);
            $('#infoModalCenter #description').val(option.description);
            $('#infoModalCenter #price').val(option.price);
        });
        $('#infoModalCenter').modal('show');
        $('#closeBtn').click(function () {
            $('#infoModalCenter').modal('hide');
        });
    });

    $('.tbl #editButton').click(function (e) {
        e.preventDefault();
        var href = '';
        href = $(this).attr('href');
        // $("#infoButton").attr('href',href);
        $.get(href, function (option, status) {
            $('#editModalCenter #name').val(option.name);
            $('#editModalCenter #shipping_id').val(option.shipping_id);
            $('#editModalCenter #description').val(option.description);
            $('#editModalCenter #price').val(option.price);
        });
        $('#editModalCenter').modal('show');
        $(function () {
            $('#updateform').validate({
                rules: {
                    name: 'required',
                    description: 'required',
                    price: 'required',
                },
                messages: {
                    name: 'Fill the shipping type field',
                    description: 'Fill the description field',
                    price: 'Fill the price field',
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
        $('#closeBtn').click(function () {
            $('#editModalCenter').modal('hide');
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
    var tableBody = document.getElementById("optionsTable").getElementsByTagName('tbody')[0];
    tableBody.innerHTML = window.originalTableContent;
}

function perfomLiveSearch() {
    var searchQuery = document.getElementById("search").value;
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/adminhome/configuration/options/search?query=" + encodeURIComponent(searchQuery), true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            // Update the table with the filtered data
            var filteredData = JSON.parse(xhr.responseText);
            updateTable(filteredData);
        }
    };

    xhr.send();
}

// Function to update the table with filtered data
function updateTable(filteredData) {
    var tableBody = document.getElementById("optionsTable").getElementsByTagName('tbody')[0];
    // Clear the existing table rows

    tableBody.innerHTML = "";

    // Generate new table rows with the filtered data

    for (var i = 0; i < filteredData.length; i++) {
        var option = filteredData[i];
        var row = document.createElement("tr");

        var optionnameCell = document.createElement("td");
        optionnameCell.textContent = option.name;

        var description = document.createElement("td");
        description.textContent = option.description;

        var price = document.createElement("td");
        price.textContent = option.price;
        
        var enabledCol = document.createElement('td');

        var icon = document.createElement('i');
        var container = document.createElement("span");
     
        var enabled = option.enabled;
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
        var formattedDate = new Date(option.date).toLocaleString('en-US', {
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
        deleteButton.setAttribute("href", "/adminhome/configuration/deleteOption/" + option.shipping_id);
        // deleteButton.setAttribute("class", "btn");
        // deleteButton.setAttribute("style", "padding: 0;");
        deleteButton.setAttribute("id", "deleteButton");
        var error = $('#error');
        
        var deleteIcon = document.createElement("i");
        deleteIcon.setAttribute("class", "fal fa-trash-alt");
        deleteIcon.setAttribute("style", "color: #ff0000;");
        deleteButton.appendChild(deleteIcon);
        deleteButton.addEventListener('click', function (e) {
            e.preventDefault();
            var href = $(this).attr('href');
            var optionId = $(this).data('option-id');
    
            $.ajax({
                url: '/adminhome/configuration/getOptionErrors/' + optionId,
                method: 'GET',
                success: function (response) {
                    // Update the modal content with the additional data
                    error.text(response);
                    $('#removeModalCenter').modal('show');
                }
            });
    
            $('#cancelButton, #closeButton').on('click', function () {
                $('#removeModalCenter').modal('hide');
            });
    
            // Perform the user deletion when the user confirms
            $('#delRef').attr('href', href);
            $('#removeModalCenter').modal('show');
            $('#cancelButton , #closeButton').on('click', function () {
                $('#removeModalCenter').modal('hide');
            });
        })
        deleteButton.setAttribute("data-option-id", option.shipping_id);
        
        actionsCell.appendChild(deleteButton);

        var infoButton = document.createElement("a");
        infoButton.setAttribute("href", "/adminhome/configuration/showOption/" + option.shipping_id);
        infoButton.setAttribute("id", "infoButton");

        var infoIcon = document.createElement("i");
        infoIcon.setAttribute("class", "far fa-info-circle");
        infoIcon.setAttribute("style", "color: #398fac;");
        infoButton.appendChild(infoIcon);
        infoButton.addEventListener('click', function (e) {
            e.preventDefault();
            var href = $(this).attr('href');
            $.get(href, function (option, status) {
                $('#infoModalCenter #name').val(option.name);
                $('#infoModalCenter #description').val(option.description);
                $('#infoModalCenter #price').val(option.price);
            })
                .done(function () {
                    $('#infoModalCenter').modal('show');
                })
                .fail(function () {
                });

            $('#infoModalCenter #closeButton').click(function () {
                $('#infoModalCenter').modal('hide');
            });
        });
        actionsCell.appendChild(infoButton);

        
        var updateButton = document.createElement("a");
        updateButton.setAttribute("href", "/adminhome/configuration/editOption/" + option.shipping_id);
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
            console.log(href);
            // $("#infoButton").attr('href',href);
            $.get(href, function (option, status) {
                $('#editModalCenter #name').val(option.name);
                $('#editModalCenter #shipping_id').val(option.shipping_id);
                $('#editModalCenter #description').val(option.description);
                $('#editModalCenter #price').val(option.price);
            });
            $('#editModalCenter').modal('show');
            $(function () {
                $('#updateform').validate({
                    rules: {
                        name: 'required',
                        description: 'required',
                        price:'required',
                    },
                    messages: {
                        name: 'Fill the option name field',
                        description: 'Fill the description field',
                        price:'Fill the price field',
                    },
                    errorElement: 'p',
                    errorPlacement: function (error, element) {
                        error.appendTo(element.closest('tr').find('p'));
                    },
                    submitHandler: function (form) { 
                        form.submit();
                    }
                });
            });
            $('#editModalCenter #closeBtn').click(function () {
                $('#editModalCenter').modal('hide');
            });
        });
        actionsCell.appendChild(updateButton);


        row.appendChild(optionnameCell);
        row.appendChild(description);
        row.appendChild(price);
        row.appendChild(enabledCol);
        row.appendChild(date);
        row.appendChild(actionsCell);
        // tableBody.children.setAttribute("style","adding : 22px 25px");

        tableBody.appendChild(row);
    }
}  