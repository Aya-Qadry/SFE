   
  //   var sidebarList = document.querySelectorAll('.sidebar ul li');
  //   sidebarList.forEach(item => {
  //   item.addEventListener('click', function() {
  //   const activeItem = document.querySelector('.sidebar ul li.active');
  //   activeItem.classList.remove('active');
  //   this.classList.add('active');
  //   // console.log("click");
  // });
  // });

  // var subMenus = document.querySelectorAll('.sub-menu');
  
  // // Loop through each sub-menu element
  // for (var i = 0; i < subMenus.length; i++) {
  //   subMenus[i].style.display = 'none';
    
  //   subMenus[i].previousElementSibling.addEventListener('click', function(e) {
  //     //prevent the default link behavior
  //     e.preventDefault();
  //     // toggle
  //     if (this.nextElementSibling.style.display === 'none') {
  //       this.nextElementSibling.style.display = 'block';
  //     } else {
  //       this.nextElementSibling.style.display = 'none';
  //     }
  //   });
  // }

  let arrow = document.querySelectorAll(".arrow");
  for (var i = 0; i < arrow.length; i++) {
    arrow[i].addEventListener("click", (e)=>{
   let arrowParent = e.target.parentElement.parentElement;//selecting main parent of arrow
   arrowParent.classList.toggle("showMenu");
    });
  }
  let sidebar = document.querySelector(".sidebar");
  let sidebarBtn = document.querySelector("#side");
  console.log(sidebarBtn);
  sidebarBtn.addEventListener("click", ()=>{
    sidebar.classList.toggle("close");
  });

