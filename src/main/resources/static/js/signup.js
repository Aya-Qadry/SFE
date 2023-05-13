
    //let is a keyword in JavaScript used to declare a block-scoped variable. It allows you to declare a variable 
    //that is limited in scope to the block, statement, or expression on which it is used, rather than the entire function.
    let c = 0 ; 
    document.addEventListener('DOMContentLoaded', () => {
      const togglePassword = document.querySelector('#togglePassword');
      const password = document.querySelector('#password');
    
      togglePassword.addEventListener('click', () => {
        const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
        password.setAttribute('type', type);
        togglePassword.classList.toggle('bi-eye');
      });
    });
    document.addEventListener('DOMContentLoaded', () => {
      const togglePasswordConfirm = document.querySelector('#togglePasswordConfirm');
      const passwordConfirm = document.querySelector('#passwordConfirm');

      togglePasswordConfirm.addEventListener('click', () => {
        const type = passwordConfirm.getAttribute('type') === 'password' ? 'text' : 'password';
        passwordConfirm.setAttribute('type', type);
        togglePasswordConfirm.classList.toggle('bi-eye');
      });
    });

      var popup = document.getElementById('popup');
      function PasswordsMatch(){
        var message = document.getElementById('message').textContent;
        var password1 = document.getElementById('password');
        var password2 = document.getElementById('passwordConfirm');
        password2.setCustomValidity('');
        if((password1.value!==password2.value)){
          document.getElementById('passwordConfirm').setCustomValidity('Passwords do not match');
          PasswordsMatch();
        }else {
          document.getElementById('passwordConfirm').setCustomValidity('');
        }
        if(message!=''){
          // document.getElementById("popup").style.display = "block";
          window.alert(message);
        }
      }

    
   
    