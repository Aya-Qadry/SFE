document.addEventListener("DOMContentLoaded", function () {
    const citySelect = document.getElementById("city-select");
    const url = "http://api.geonames.org/searchJSON?q=*&country=MA&maxRows=1000&username=lamar";

    // Fetch the data from the GeoNames API
    fetch(url)
        .then(response => response.json())
        .then(data => {
            // Loop through the data and add options to the select element
            data.geonames.forEach(city => {
                const option = document.createElement("option");
                option.value = city.name;
                option.textContent = city.name;
                citySelect.appendChild(option);
            });
            var selectedCity = sessionStorage.getItem("selectedCity");
            if (selectedCity) {
                citySelect.value = selectedCity;
            }
        })
        .catch(error => console.error(error));

    // Event listener to store the selected city value in local storage
    citySelect.addEventListener("change", function () {
        var selectedValue = this.value;
        sessionStorage.setItem("selectedCity", selectedValue);
    });
});


const roleselect = document.getElementById('roleSelect');
const vendorselect = document.getElementById('vendorSelect');

roleselect.addEventListener('change', function () {

    if (roleselect.value !== '3') {
        vendorselect.setAttribute("disbaled", true);
    } else {
        vendorselect.removeAttribute("disabled");
    }
});

$(document).ready(function () {
    $('#form').submit(function (event) {

        var roleSelect = $('#roleSelect').val();
        var vendorSelect = $('#vendorSelect').val();
        var citySelect = $('#city-select').val();

        $('.alert').remove();

        if (roleSelect === '') {
            event.preventDefault();
            showAlert('Please select a role !', 'alert-danger');
        }
        if (roleSelect === '3' && vendorSelect === '1') {
            event.preventDefault();
            showAlert('Please select a vendor !', 'alert-danger');
        }
        if (citySelect === '') {
            event.preventDefault();
            showAlert('Please select a city !', 'alert-danger');
        }
    });
    function showAlert(message, type) {
        var alertDiv = $('<div>').addClass('alert ' + type).text(message);
        $('#form').prepend(alertDiv);
    }
});

document.addEventListener("DOMContentLoaded", function () {
    // Retrieve the selected role value from local storage (if available)
    var selectedValue = sessionStorage.getItem("selectedRole");

    if (selectedValue) {
        document.getElementById("roleSelect").value = selectedValue;
    }
    document.getElementById("roleSelect").addEventListener("change", function () {
        var selectedValue = this.value;
        sessionStorage.setItem("selectedRole", selectedValue);
    });
});

