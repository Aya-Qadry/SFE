    console.log("hello");
    document.addEventListener('DOMContentLoaded', () => {
        const togglePassword = document.querySelector('#togglePassword');
        const password = document.querySelector('#password');
    
        togglePassword.addEventListener('click', () => {
        const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
        password.setAttribute('type', type);
        togglePassword.classList.toggle('bi-eye');
        });
    });

    const check = document.getElementById("rememberMe") , 
    email_in = document.getElementById("emailaddress") , 
    pass = document.getElementById("password");

  if(localStorage.checkbox && localStorage.checkbox!==""){
    check.setAttribute("checked","checked");
    email_in.value=localStorage.username;
    pass.value=localStorage.password ; 
  }else{
    check.removeAttribute("checked");
    email_in.value="";
    pass.value="";
  }
  function submit(){
    if(check.checked && email_in.value!==""){
      localStorage.username = email_in.value;
      localStorage.password = pass.value ; 
      localStorage.checkbox = check.value;
    }else{
      localStorage.username="";
      localStorage.checkbox="";
      localStorage.password=""
    }
  }