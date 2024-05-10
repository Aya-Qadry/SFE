$(document).ready(function () {

    $('.tbl #editButton').click(function (e) {
        e.preventDefault();
        var href = '';
        href = $(this).attr('href');
        // $("#infoButton").attr('href',href);
        $.get(href, function (unit, status) {
            $('#editModalCenter #name').val(unit.name);
            $('#editModalCenter #unit_id').val(unit.unit_id);
            $('#editModalCenter #abbreviation').val(unit.abbreviation);
        });
        $('#editModalCenter').modal('show');
        $(function () {
            $('#updateform').validate({
                rules: {
                    name: 'required',
                    abbreviation: 'required',
                },
                messages: {
                    name: 'Fill the unit name field',
                    abbreviation: 'Fill the abbreviation field',
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

     // delete button
     var modal = $('#removeModalCenter');
    var error = $('#error');

    // Attach a click event listener to the Delete button
    $('.tbl #deleteButton').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var unitId = $(this).data('unit-id');
        // Perform the AJAX request to get additional data
        $.ajax({
            url: '/adminhome/configuration/getUnitErrors/' + unitId,
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

        // Perform the user deletion when the user confirms
        $('#delRef').attr('href', href);
    });

    //add modal
    $("#showModal").click(function (e) {
        e.preventDefault();
        $("#addModal").modal('show');
        $(function () {
            $('#addform').validate({
                rules: {
                    name: 'required',
                    abbreviation: 'required',
                },
                messages: {
                    name: 'Fill the unit name field',
                    abbreviation: 'Fill the abbreviation field',
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
    var tableBody = document.getElementById("unitsTable").getElementsByTagName('tbody')[0];
    tableBody.innerHTML = window.originalTableContent;
}

function perfomLiveSearch() {
    var searchQuery = document.getElementById("search").value;
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/adminhome/configuration/units/search?query=" + encodeURIComponent(searchQuery), true);
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
    var tableBody = document.getElementById("unitsTable").getElementsByTagName('tbody')[0];
    // Clear the existing table rows

    tableBody.innerHTML = "";

    // Generate new table rows with the filtered data

    for (var i = 0; i < filteredData.length; i++) {
        var unit = filteredData[i];
        var row = document.createElement("tr");

        var unitnameCell = document.createElement("td");
        unitnameCell.textContent = unit.name;

        var abbreviation = document.createElement("td");
        abbreviation.textContent = unit.abbreviation;

        var date = document.createElement("td");
        var formattedDate = new Date(unit.date).toLocaleString('en-US', {
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
        deleteButton.setAttribute("href", "/adminhome/configuration/deleteUnit/" + unit.unit_id);
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
            var unitId = $(this).data('unit-id');
    
            $.ajax({
                url: '/adminhome/configuration/getUnitErrors/' + unitId,
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
        deleteButton.setAttribute("data-unit-id", unit.unit_id);
        
        actionsCell.appendChild(deleteButton);

        var updateButton = document.createElement("a");
        updateButton.setAttribute("href", "/adminhome/configuration/editUnit/" + unit.unit_id);
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
            $.get(href, function (unit, status) {
                $('#editModalCenter #name').val(unit.name);
                $('#editModalCenter #unit_id').val(unit.unit_id);
                $('#editModalCenter #abbreviation').val(unit.abbreviation); 
            });
            $('#editModalCenter').modal('show');
            $(function () {
                $('#updateform').validate({
                    rules: {
                        name: 'required',
                        abbreviation: 'required', 
                    },
                    messages: {
                        name: 'Fill the unit name field',
                        abbreviation: 'Fill the abbreviation field', 
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


        row.appendChild(unitnameCell);
        row.appendChild(abbreviation); 
        row.appendChild(date);
        row.appendChild(actionsCell);
        // tableBody.children.setAttribute("style","adding : 22px 25px");

        tableBody.appendChild(row);
    }
} 