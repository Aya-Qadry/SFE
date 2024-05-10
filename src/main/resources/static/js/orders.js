$(document).ready(function () {

    $(document).on('click', '.tbl #deleteButton', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#removeModalCenter #delRef').attr('href', href);
        $('#removeModalCenter').modal('show');
        $('#cancelButton, #closeButton').on('click', function () {
            $('#removeModalCenter').modal('hide');
        });
    });
    var modal = $('#infoModalCenter');

    // Get the button that opens the modal
    var buttons = $('.show-order-details');

    // Get the <span> element that closes the modal
    var closeBtn = $('.close');

    // Loop through all buttons and attach a click event listener
    $(document).on('click', '.show-order-details', function () {
        var orderId = $(this).data('order-id');
        console.log("confirmed");
        fetchOrderProducts(orderId); // Fetch order products using AJAX
        modal.modal('show'); // Show the modal
    });

    // Close the modal when the user clicks on the <span> element
    closeBtn.on('click', function () {
        modal.modal('hide');
    });

    // Close the modal when the user clicks anywhere outside the modal
    modal.on('click', function (event) {
        if (event.target === modal[0]) {
            modal.modal('hide');
        }
    });


    // Function to fetch order products using AJAX
    function fetchOrderProducts(orderId) {
        // Make an AJAX request to retrieve the order products data
        // Replace the URL with your actual endpoint
        var url = '/adminhome/orders/showOrder/' + orderId;
        $.get(url)
            .done(function (orderProducts) {
                displayOrderProducts(orderProducts);
            })
            .fail(function (xhr, status, error) {
                console.error('Error fetching order products:', status, error);
            });
    }

    function displayOrderProducts(orderProducts) {
        var tbody = $('#orderProductsTable tbody');
        // var firstRow = $('#orderProductsTable span');
        // var table = $('#orderProductsTable ');
        // firstRow.empty();
        tbody.empty();
        var total = 0; // Variable to store the total of the order

        orderProducts.forEach(function (orderProduct) {
            var customerRow = $('<tr>');
            var customerCell = $('<td>').text(orderProduct.order.customer.firstname + ' ' + orderProduct.order.customer.lastname);

            var row = $('<tr>');
            var productNameCell = $('<td>').text(orderProduct.stock.product.name);
            var quantityCell = $('<td>').text(orderProduct.quantity);
            var priceCell = $('<td>').text(orderProduct.price);

            var totalPrice = orderProduct.quantity * orderProduct.price; // Calculate the total price for the order product
            var totalPriceCell = $('<td>').text(totalPrice + ' DH');
            total += totalPrice; // Update the total of the order

            // customerRow.append(customerCell);
            // firstRow.append(customerRow);

            row.append(productNameCell);
            row.append(quantityCell);
            row.append(priceCell);
            row.append(totalPriceCell);

            tbody.append(row);
            // table.append(firstRow);
            // table.append(tbody);
        });

        // Add a row for the total of the order
        var totalRow = $('<tr>');
        totalRow.append($('<td colspan="4">')); // Empty cells for customer name, product name, quantity, and price
        totalRow.append($('<td>').text('Total:'));
        totalRow.append($('<td>').text(total + ' DH'));
        tbody.append(totalRow);
    }


    $(window).on('click', function (event) {
        if ($(event.target).is(modal)) {
            modal.modal('hide');
        }
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
        console.log("bad");
        restoreTable();
    } else {
        perfomLiveSearch(inputValue);
    }
});


function restoreTable() {
    var tableBody = document.getElementById("ordersTable").getElementsByTagName("tbody")[0];
    tableBody.innerHTML = window.originalTableContent;
}


function perfomLiveSearch() {
    var searchQuery = document.getElementById("search").value;
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/adminhome/orders/search?query=" + encodeURIComponent(searchQuery), true);
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
    var tableBody = document.getElementById("ordersTable").getElementsByTagName("tbody")[0];
    // Clear the existing table rows

    while (tableBody.firstChild) {
        tableBody.removeChild(tableBody.firstChild);
    }

    for (var i = 0; i < filteredData.length; i++) {
        var order = filteredData[i];
        var row = document.createElement("tr");

        var orderIdCell = document.createElement("td");
        orderIdCell.textContent = order.order_id;

        var ordernameCell = document.createElement("td");
        var fullname = order.customer.firstname + ' ' + order.customer.lastname;
        ordernameCell.textContent = fullname;

        var shippingCell = document.createElement("td");
        shippingCell.textContent = order.shipping.name;

        var enabledCol = document.createElement('td');

        var date = document.createElement("td");
        var formattedDate = new Date(order.date).toLocaleString('en-US', {
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
        deleteButton.setAttribute("href", "/adminhome/orders/deleteOrder/" + order.order_id);
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


        var infoButton = document.createElement("button");
        // infoButton.setAttribute("href", "/adminhome/orders/showOrder/" + order.order_id);
        // infoButton.setAttribute("data-order-id", order.order_id);
        infoButton.classList.add("btn");
        infoButton.classList.add("show-order-details");
        var modal = $('#infoModalCenter');

        var infoIcon = document.createElement("i");
        infoIcon.setAttribute("class", "far fa-info-circle");
        infoIcon.setAttribute("style", "color: #398fac;");
        infoButton.appendChild(infoIcon);

        infoButton.addEventListener('click', function (e) {
            var orderId = $(this).data('order-id');

            if (orderId !== undefined) {
                fetchOrderProducts(orderId);
                modal.modal('show');
            } else {
                console.error('Invalid order ID');
            }

            $('#infoModalCenter #closeBtn').click(function () {
                $('#infoModalCenter').modal('hide');
            });
        });

        infoButton.setAttribute("data-order-id", order.order_id);
        // Function to fetch order products using AJAX
        function fetchOrderProducts(orderId) {
            // Make an AJAX request to retrieve the order products data
            // Replace the URL with your actual endpoint
            var url = '/adminhome/orders/showOrder/' + orderId;
            console.log("ici" + orderId);
            $.get(url)
                .done(function (orderProducts) {
                    displayOrderProducts(orderProducts);
                })
                .fail(function (xhr, status, error) {
                    console.error('Error fetching order products:', status, error);
                });
        }

        function displayOrderProducts(orderProducts) {
            var tbody = $('#orderProductsTable tbody');
            // var firstRow = $('#orderProductsTable span');
            // var table = $('#orderProductsTable ');
            // firstRow.empty();
            tbody.empty();
            var total = 0; // Variable to store the total of the order

            orderProducts.forEach(function (orderProduct) {
                var customerRow = $('<tr>');
                var customerCell = $('<td>').text(orderProduct.order.customer.firstname + ' ' + orderProduct.order.customer.lastname);

                var row = $('<tr>');
                var productNameCell = $('<td>').text(orderProduct.stock.product.name);
                var quantityCell = $('<td>').text(orderProduct.quantity);
                var priceCell = $('<td>').text(orderProduct.price);

                var totalPrice = orderProduct.quantity * orderProduct.price; // Calculate the total price for the order product
                var totalPriceCell = $('<td>').text(totalPrice + ' DH');
                total += totalPrice; // Update the total of the order

                // customerRow.append(customerCell);
                // firstRow.append(customerRow);

                row.append(productNameCell);
                row.append(quantityCell);
                row.append(priceCell);
                row.append(totalPriceCell);

                tbody.append(row);
                // table.append(firstRow);
                // table.append(tbody);
            });

            // Add a row for the total of the order
            var totalRow = $('<tr>');
            totalRow.append($('<td colspan="4">')); // Empty cells for customer name, product name, quantity, and price
            totalRow.append($('<td>').text('Total:'));
            totalRow.append($('<td>').text(total + ' DH'));
            tbody.append(totalRow);
        }


        actionsCell.appendChild(infoButton);


        row.appendChild(orderIdCell);
        row.appendChild(ordernameCell);
        row.appendChild(shippingCell);
        row.appendChild(date);
        row.appendChild(actionsCell);

        tableBody.appendChild(row);
    }
} 