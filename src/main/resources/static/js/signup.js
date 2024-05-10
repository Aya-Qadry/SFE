let c = 0;
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

// function PasswordsMatch(event) {
//   var password1 = document.getElementById('password').value;
//   var password2 = document.getElementById('passwordConfirm').value;

//   if (password1 !== password2) {
//     window.alert('Passwords do not match');
//     event.preventDefault(); // Prevent form submission
//   }
// }
function PasswordsMatch(event) {
  event.preventDefault(); // Prevent form submission

  var password = document.getElementById('password').value;
  var passwordConfirm = document.getElementById('passwordConfirm').value;
  var errorContainer = document.getElementById('errorContainer');
  var errors = [];

  // Validate password length
  if (password.length < 8) {
    errors.push('Password should be at least 8 characters long.');
  }

  // Validate capital letter
  if (!/[A-Z]/.test(password)) {
    errors.push('Password should contain at least one capital letter.');
  }

  // Validate lowercase letter
  if (!/[a-z]/.test(password)) {
    errors.push('Password should contain at least one lowercase letter.');
  }

  // Validate number
  if (!/\d/.test(password)) {
    errors.push('Password should contain at least one number.');
  }

  // Validate special character
  if (!/[!@#$%^&*]/.test(password)) {
    errors.push('Password should contain at least one special character (!@#$%^&*).');
  }

  // Check if passwords match
  if (password !== passwordConfirm) {
    errors.push('Passwords do not match.');
  }

  // Display errors or clear error container
  if (errors.length > 0) {
    errorContainer.innerHTML = errors.join('<br>');
    errorContainer.style.display = 'block';
  } else {
    errorContainer.innerHTML = '';
    errorContainer.style.display = 'none';
    document.getElementById('form').submit(); // Submit the form
  }
}
