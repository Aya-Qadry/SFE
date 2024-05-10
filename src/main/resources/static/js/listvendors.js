$(document).ready(function () {

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

    //info button
    $('.tbl #infoButton').click(function (e) {
        e.preventDefault();
        var href = '';
        href = $(this).attr('href');
        // $("#infoButton").attr('href',href);
        $.get(href, function (vendor, status) {
            var fullname = vendor.firstname + ' ' + vendor.lastname;
            $('#infoModalCenter #fullname').val(fullname);
            $('#infoModalCenter #cin').val(vendor.cin);
            $('#infoModalCenter #gender').val(vendor.gender);
            $('#infoModalCenter #emailaddress').val(vendor.emailaddress);
            $('#infoModalCenter #phonenumber').val(vendor.phonenumber);
        });
        $('#infoModalCenter').modal('show');
        $('#infoModalCenter #closeButton').click(function () {
            $('#infoModalCenter').modal('hide');
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
    var tableBody = document.getElementById("vendorsTable").getElementsByTagName("tbody")[0];
    tableBody.innerHTML = window.originalTableContent;
}



function perfomLiveSearch() {
    var searchQuery = document.getElementById("search").value;
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/adminhome/vendors/search?query=" + encodeURIComponent(searchQuery), true);
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
    var tableBody = document.getElementById("vendorsTable").getElementsByTagName("tbody")[0];
    // Clear the existing table rows

    while (tableBody.firstChild) {
        tableBody.removeChild(tableBody.firstChild);
    }

    // Generate new table rows with the filtered data

    for (var i = 0; i < filteredData.length; i++) {
        var user = filteredData[i];
        var row = document.createElement("tr");

        var userIdCell = document.createElement("td");
        userIdCell.textContent = user.user_id;
        var usernameCell = document.createElement("td");
        usernameCell.textContent = user.firstname + ' ' + user.lastname;
 
        var cin = document.createElement("td");
        cin.textContent = user.cin;
        var gender = document.createElement("td");
        gender.textContent = user.gender;
        var emailaddress = document.createElement("td");
        emailaddress.textContent = user.emailaddress;
        var phonenumber = document.createElement("td");
        phonenumber.textContent = user.phonenumber; 

        var date = document.createElement("td");
        var formattedDate = new Date(user.date).toLocaleString('en-US', {
          year: 'numeric',
          month: 'short',
          day: 'numeric',
          hour: 'numeric',
          minute: 'numeric', 
          hour12: false
        });
        date.textContent = formattedDate;
        
        // var roleRow = document.createElement("td");
        // roleRow.textContent = user.roles ; 
        // console.log(user.roles.name);

        var actionsCell = document.createElement("td");
        var deleteButton = document.createElement("a");
        deleteButton.setAttribute("href", "/adminhome/deleteVendor/" + user.user_id);
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
        infoButton.setAttribute("href", "/adminhome/users/showUser/" + user.user_id);
        // infoButton.setAttribute("class", "btn");
        infoButton.setAttribute("id", "infoButton");

        var infoIcon = document.createElement("i");
        infoIcon.setAttribute("class", "far fa-info-circle");
        infoIcon.setAttribute("style", "color: #398fac;");
        infoButton.appendChild(infoIcon);
        infoButton.addEventListener('click', function (e) {
            e.preventDefault();
            var href = $(this).attr('href');
            $.get(href, function (user, status) {
                var fullname = user.firstname + ' ' + user.lastname;
                $('#infoModalCenter #user_id').val(user.user_id)
                $('#infoModalCenter #fullname').val(fullname);
                $('#infoModalCenter #cin').val(user.cin);
                $('#infoModalCenter #gender').val(user.gender);
                $('#infoModalCenter #emailaddress').val(user.emailaddress);
                $('#infoModalCenter #phonenumber').val(user.phonenumber);
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
        updateButton.setAttribute("href", "/adminhome/users/updateUser/" + user.user_id);
        // updateButton.setAttribute("class", "btn");
        updateButton.setAttribute("style", "margin:2px ;");
        var updateIcon = document.createElement("i");
        updateIcon.setAttribute("class", "fal fa-edit");
        updateIcon.setAttribute("style", "color: #37a136;");
        updateButton.appendChild(updateIcon);
        actionsCell.appendChild(updateButton);


        row.appendChild(userIdCell);
        row.appendChild(usernameCell); 
        row.appendChild(cin);
        row.appendChild(gender);
        row.appendChild(emailaddress);
        row.appendChild(phonenumber);
        row.appendChild(date);
        row.appendChild(actionsCell);
        // tableBody.children.setAttribute("style","adding : 22px 25px");

        tableBody.appendChild(row);
    }
}
px