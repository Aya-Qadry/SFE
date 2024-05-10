$(document).ready(function () {
    var modal = $('#removeModalCenter');
    var error = $('#error');

    // Attach a click event listener to the Delete button
    $('.tbl #deleteButton').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var userId = $(this).data('user-id');
        console.log("id " + userId);
        // Perform the AJAX request to get additional data
        $.ajax({
            url: '/adminhome/users/getUserErrors/' + userId,
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
        $("#infoButton").attr('href', href);
        $.get(href, function (user, status) {
            var fullname = user.firstname + ' ' + user.lastname;
            $('#infoModalCenter #user_id').val(user.user_id)
            $('#infoModalCenter #fullname').val(fullname);
            $('#infoModalCenter #cin').val(user.cin);
            // $('#infoModalCenter #role').val(user.roles);
            $('#infoModalCenter #gender').val(user.gender);
            $('#infoModalCenter #emailaddress').val(user.emailaddress);
            $('#infoModalCenter #phonenumber').val(user.phonenumber);
        });
        $('#infoModalCenter').modal('show');
        $('#infoModalCenter #closeButton').click(function () {
            $('#infoModalCenter').modal('hide');
        });
    })
});



document.addEventListener("DOMContentLoaded", function () {
    // Store the original HTML content of the table
    var originalTableContent = document.getElementById("tbody").innerHTML;

    // Assign the value to a global variable for access from other functions
    window.originalTableContent = originalTableContent;
});


var table = document.getElementById("usersTable");
for (var i = 0; i < table.rows.length; i++) {
    var roleElement = table.rows[i].querySelector('#role');

    if (roleElement) {
        var icon = document.createElement('i');
        var container = document.createElement("span");
        var text;
        var role = roleElement.textContent;

        if (role == "[ADMIN]") {
            roleElement.textContent = "";
            icon.classList.add("fas", "fa-user-shield");
            icon.style.color = '#e60000';
            text = document.createTextNode(" Admin");
            // roleElement.role = " Admin";

            container.appendChild(icon);
            container.appendChild(text);
            roleElement.appendChild(container);

        }
        if (role == "[VENDOR]") {
            roleElement.textContent = "";
            icon.classList.add('fas', 'fa-user-tag');
            icon.style.color = 'rgb(62, 92, 148)';
            text = document.createTextNode(" Vendor");
            // roleElement.role = " Vendor";

            container.appendChild(icon);
            container.appendChild(text);
            roleElement.appendChild(container);

        }
        if (role == "[CUSTOMER]") {
            roleElement.textContent = "";
            icon.classList.add('fas', 'fa-user-tie');
            icon.style.color = 'green';
            icon.style.marginRight = '8px';
            roleElement.setAttribute("data-role", "Customer");
            text = document.createTextNode("Customer");
            // roleElement.role = " Customer";

            container.appendChild(icon);
            container.appendChild(text);
            roleElement.appendChild(container);

        }

    }

}

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
    var tableBody = document.getElementById("usersTable").getElementsByTagName("tbody")[0];
    tableBody.innerHTML = window.originalTableContent;
}


function perfomLiveSearch() {
    var searchQuery = document.getElementById("search").value;
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/adminhome/users/search?query=" + encodeURIComponent(searchQuery), true);
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
    var tableBody = document.getElementById("usersTable").getElementsByTagName("tbody")[0];
    // Clear the existing table rows

    while (tableBody.firstChild) {
        tableBody.removeChild(tableBody.firstChild);
    }

    // Generate new table rows with the filtered data

    var modal = $('#removeModalCenter');
    var error = $('#error');
    for (var i = 0; i < filteredData.length; i++) {
        var user = filteredData[i];
        var row = document.createElement("tr");

        var userIdCell = document.createElement("td");
        userIdCell.textContent = user.user_id;
        var usernameCell = document.createElement("td");
        usernameCell.textContent = user.firstname + ' ' + user.lastname;

        var roleCol = document.createElement('td');
        console.log(user.roles[0].name);

        var icon = document.createElement('i');
        var container = document.createElement("span");
        var text;

        var roleName = user.roles[0].name;
        if (roleName == "ADMIN") {
            icon.classList.add("fas", "fa-user-shield");
            icon.style.color = '#e60000';
            text = document.createTextNode(" Admin");

            container.appendChild(icon);
            container.appendChild(text);
            roleCol.appendChild(container);
        }
        if (roleName == "VENDOR") {
            icon.classList.add('fas', 'fa-user-tag');
            icon.style.color = 'rgb(62, 92, 148)';
            text = document.createTextNode(" Vendor");

            container.appendChild(icon);
            container.appendChild(text);
            roleCol.appendChild(container);
        }
        if (roleName == "CUSTOMER") {
            icon.classList.add('fas', 'fa-user-tie');
            icon.style.color = 'green';
            icon.style.marginRight = '8px';

            text = document.createTextNode(" Customer");

            container.appendChild(icon);
            container.appendChild(text);
            roleCol.appendChild(container);

        }
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
        // deleteButton.setAttribute("href", "/adminhome/users/deleteUser/" + user.user_id);
        // deleteButton.setAttribute("class", "btn");
        // deleteButton.setAttribute("style", "padding: 0;");
        deleteButton.setAttribute("id", "deleteButton");

        var deleteIcon = document.createElement("i");
        deleteIcon.setAttribute("class", "fal fa-trash-alt");
        deleteIcon.setAttribute("style", "color: #ff0000;");
        deleteButton.appendChild(deleteIcon);
        deleteButton.addEventListener('click', function (e) {
            e.preventDefault();
            var userId = $(this).data('user-id');
            var href = "/adminhome/users/deleteUser/" + userId
            // Perform the AJAX request to get additional data
            $.ajax({
                url: '/adminhome/users/getUserErrors/' + userId,
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
        })
        deleteButton.setAttribute("data-user-id", user.user_id);

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
        row.appendChild(roleCol);
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