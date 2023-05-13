   
    var sidebarList = document.querySelectorAll('.sidebar ul li');
    sidebarList.forEach(item => {
    item.addEventListener('click', function() {
    const activeItem = document.querySelector('.sidebar ul li.active');
    activeItem.classList.remove('active');
    this.classList.add('active');
    // console.log("click");
  });
  });

  var subMenus = document.querySelectorAll('.sub-menu');
  
  // Loop through each sub-menu element
  for (var i = 0; i < subMenus.length; i++) {
    subMenus[i].style.display = 'none';
    
    subMenus[i].previousElementSibling.addEventListener('click', function(e) {
      //prevent the default link behavior
      e.preventDefault();
      // toggle
      if (this.nextElementSibling.style.display === 'none') {
        this.nextElementSibling.style.display = 'block';
      } else {
        this.nextElementSibling.style.display = 'none';
      }
    });
  }

