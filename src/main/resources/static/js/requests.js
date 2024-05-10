$(document).ready(function () {

    //info button
    $('.tbl #infoButton').click(function (e) {
        e.preventDefault();
        var href = '';
        href = $(this).attr('href');
        // $("#infoButton").attr('href',href);
        $.get(href, function (request, status) {
            // $('#infoModalCenter #name').val(request.name);
            $('#infoModalCenter #fullname').val(request.firstname);
            $('#infoModalCenter #cin').val(request.cin);
            $('#infoModalCenter #gender').val(request.gender);
            $('#infoModalCenter #emailaddress').val(request.emailaddress);
            $('#infoModalCenter #phonenumber').val(request.phonenumber);
        });
        $('#infoModalCenter').modal('show');
        $('#infoModalCenter #closeButton').click(function () {
            $('#infoModalCenter').modal('hide');
        });
    });

    // delete button
    $('.tbl #deleteButton').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#removeModalCenter #delRef').attr('href', href);
        $('#removeModalCenter').modal('show');
        $('#cancelButton , #closeButton').on('click', function () {
            $('#removeModalCenter').modal('hide');
        });
    });

    // enable button
    $('.tbl #enableButton').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#enableModal #confirmButton').attr('href', href);
        $('#enableModal').modal('show');
        $('#cancelButton').click(function () {
            $('#enableModal').modal('hide');
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
    var tableBody = document.getElementById("requestTable").getElementsByTagName('tbody')[0];
    tableBody.innerHTML = window.originalTableContent;
}


function perfomLiveSearch() {
    var searchQuery = document.getElementById("search").value;
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/adminhome/requests/search?query=" + encodeURIComponent(searchQuery), true);
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
    var tableBody = document.getElementById("requestTable").getElementsByTagName('tbody')[0];
    // Clear the existing table rows

    tableBody.innerHTML = "";


    // Generate new table rows with the filtered data

    for (var i = 0; i < filteredData.length; i++) {
        var request = filteredData[i];
        var row = document.createElement("tr");
 
        var requestIdCell = document.createElement("td");
        requestIdCell.textContent = request.id;
        var requestnameCell = document.createElement("td");
        requestnameCell.textContent = request.firstname + ' ' + request.lastname;

        var cin = document.createElement("td");
        cin.textContent = request.cin;

        var emailaddress = document.createElement("td");
        emailaddress.textContent = request.emailaddress;
        var phonenumber = document.createElement("td");
        phonenumber.textContent = request.phonenumber;

        var date = document.createElement("td");
        var formattedDate = new Date(request.date).toLocaleString('en-US', {
            year: 'numeric',
            month: 'short',
            day: 'numeric',
            hour: 'numeric',
            minute: 'numeric',
            hour12: false
        });
        date.textContent = formattedDate;


        var actionsCell = document.createElement("td");
        actionsCell.style.display="flex";
        actionsCell.style.alignItems="center";
        var deleteButton = document.createElement("a");
        deleteButton.setAttribute("href", "/adminhome/deleteRequest/" + request.id);
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
        infoButton.setAttribute("href", "/adminhome/showRequest/" + request.id);
        // infoButton.setAttribute("class", "btn");
        infoButton.setAttribute("id", "infoButton");

        var infoIcon = document.createElement("i");
        infoIcon.setAttribute("class", "far fa-info-circle");
        infoIcon.setAttribute("style", "color: #398fac;");
        infoButton.appendChild(infoIcon);
        infoButton.addEventListener('click', function (e) {
            e.preventDefault();
            var href = $(this).attr('href');
            $.get(href, function (request, status) {
                var fullname = request.firstname + ' ' + request.lastname;
                $('#infoModalCenter #request_id').val(request.id)
                $('#infoModalCenter #fullname').val(fullname);
                $('#infoModalCenter #cin').val(request.cin);
                $('#infoModalCenter #gender').val(request.gender);
                $('#infoModalCenter #emailaddress').val(request.emailaddress);
                $('#infoModalCenter #phonenumber').val(request.phonenumber);
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

        var enableButton = document.createElement("a");
        enableButton.setAttribute("href", "/adminhome/enableRequest/"+request.id);
        enableButton.setAttribute("id", "enableButton");

        var label = document.createElement("label");
        label.classList.add("switch");

        var input = document.createElement("input");
        input.setAttribute("type", "checkbox");

        var span = document.createElement("span");
        span.classList.add("slider");

        label.appendChild(input);
        label.appendChild(span);

        enableButton.appendChild(label);

        enableButton.addEventListener('click', function (e) {
                e.preventDefault();
                var href = $(this).attr('href');
                $('#enableModal #confirmButton').attr('href', href);
                $('#enableModal').modal('show');
                $('#cancelButton').click(function () {
                    $('#enableModal').modal('hide');
                });
        })

        actionsCell.appendChild(enableButton);

        row.appendChild(requestIdCell);
        row.appendChild(requestnameCell);
        row.appendChild(cin);
        row.appendChild(emailaddress);
        row.appendChild(phonenumber);
        row.appendChild(date);
        row.appendChild(actionsCell);
 
        tableBody.appendChild(row);
    }
} 