// function openNav() {
//   const nav = document.getElementById('side_nave');
//   const main = document.getElementById('main');
//   const container = document.getElementById("container-fluid");

//   const navStyle = window.getComputedStyle(nav);
//   const navWidth = navStyle.width;

//   if (nav.style.width != '0px') {
//     nav.style.width = '0px';
//     main.style.marginLeft = '0';
//     container.style.marginLeft = '0';
//   } else {
//     document.getElementById('side_nave').style.width = '310px';
//     document.getElementById('main').style.marginLeft = '310px';
//     document.getElementById('container-fluid').style.marginLeft = '310px';
//   }

// }
function toggleNav() {
  const nav = document.getElementById('side_nave');
  const main = document.getElementById('main');
  const container = document.getElementById("container-fluid");

  if (nav.style.width === '0px' || nav.style.width === '') {
    nav.style.width = '310px';
    main.style.marginLeft = '310px';
    container.style.marginLeft = '310px';
  } else {
    nav.style.width = '0px';
    main.style.marginLeft = '0';
    container.style.marginLeft = '0';
  }
}

// function closeNav() {
//   const nav = document.getElementById('side_nave');
//   const main = document.getElementById('main');
//   const container = document.getElementById("container-fluid");
//   nav.style.width = '0px';
//   main.style.marginLeft = '0';
//   container.style.marginLeft = '0';
// }


var linkColor = document.querySelectorAll('.nav_link');

function colorLink() {

  var parentLi = this.closest('li');
  linkColor.forEach(l => {
    parentLi.classList.add('active');

    l.classList.remove('active');

  });

}
linkColor.forEach(l => l.addEventListener('click', colorLink)); 

var collapseLinks = document.querySelectorAll('i[name="collapse_link"]');
collapseLinks.forEach(link => {
  link.addEventListener('click', function() {
    var collapseMenu = this.parentElement.nextElementSibling;
    collapseMenu.classList.toggle('showCollapse');

    var rotate = this;
    rotate.classList.toggle('rotate');
  });
});

var linkColor = document.querySelectorAll('.nav_link')
function colorLink(){
  linkColor.forEach(l=> l.classList.remove('active'))
  this.classList.add('active')
}
linkColor.forEach(l=> l.addEventListener('click', colorLink))

