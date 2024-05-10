$(document).ready(function () {
  var modal = $('#removeModalCenter');
  var error = $('#error');

  // Attach a click event listener to the Delete button
  $('.card #delete').on('click', function (event) {
    event.preventDefault();
    var href = $(this).attr('href');
    var productId = $(this).data('product-id');
    // Perform the AJAX request to get additional data
    $.ajax({ 
      url: '/adminhome/products/getProductErrors/' + productId,
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

  $('.card #deleteButton').on('click', function (event) {
    event.preventDefault();
    var href = $(this).attr('href');
    $('#removeModalCenter #delRef').attr('href', href);

    $('#removeModalCenter').modal('show');
    $('#cancelButton , #closeButton').on('click', function () {
        $('#removeModalCenter').modal('hide');
    });
});



  var $gridContainer = $('.grid-container');
  var originalState = $gridContainer.html(); // Store the original state of the container

  $('#search').on('input', function () {
    var query = $(this).val().toLowerCase().trim();
    console.log(query);
    if (query === "") {
      $gridContainer.html(originalState);
      return;
    } else {
      $.ajax({
        url: '/adminhome/products/search',
        method: 'GET',
        data: { query: query },
        success: function (response) {
          console.log(response);


          var html = '';
          var colCount = 0;
          response.forEach(function (product) {
            if (colCount === 0) {
              html += '<div class="row">';
            }

            html += '<div class="col"  id="grid-item">';
            html += '<div class="card">';
            html += '<img src="' + product.photosImagePath + '" class="product-img">';
            html += '<div class="card-content">';
            html += '<h2>' + product.name + '</h2>';
            html += '<p class="description">' + product.description + '</p>';
            html += '<a href="/adminhome/stock/addToStock/' + product.product_id + '" class="btn btn-danger"><i class="fa fa-plus"></i> Add to stock</a>';
            html += '</div>';
            html += '</div>';
            html += '</div>';

            colCount++;

            if (colCount === 3) {
              html += '</div>';
              colCount = 0;
            }
          });

          if (colCount !== 0) {
            html += '</div>';
          }

          $gridContainer.html(html);
        }
      });
    }
  });
});
