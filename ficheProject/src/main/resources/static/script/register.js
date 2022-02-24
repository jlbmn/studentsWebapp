$(document).ready(function() {
    var showPass = 0;
    $('.btn-show-pass').on('click', function() {
        if (showPass == 0) {
            $(this).next('input').attr('type', 'text');
            $(this).find('i').removeClass('mdi-eye');
            $(this).find('i').addClass('mdi-eye-off');
            showPass = 1;
        } else {
            $(this).next('input').attr('type', 'password');
            $(this).find('i').addClass('mdi-eye');
            $(this).find('i').removeClass('mdi-eye-off');
            showPass = 0;
        }

    });
});

function verifyPassword() {
    var pw = document.getElementById("confirm-pass").value;
    //check empty password field  
    if (confirm - pass == "") {
        document.getElementById("message").innerHTML = "**Fill the password please!";
        return false;
    }

    //minimum password length validation  
    if (confirm - pass.length < 8) {
        document.getElementById("message").innerHTML = "**Password length must be atleast 8 characters";
        return false;
    }

    //maximum length of password validation  
    if (confirm - pass.length > 15) {
        document.getElementById("message").innerHTML = "**Password length must not exceed 15 characters";
        return false;
    } else {
        alert("Password is correct");
    }
}
