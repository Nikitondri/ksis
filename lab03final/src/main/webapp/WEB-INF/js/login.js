// function checkPass() {
//     if (document.getElementById('passwordRegister').value === document.getElementById('passwordRepeat').value) {
//         return true;
//     } else {
//         alert("Hello");
//         return false;
//     }
// }

var check = function() {
    if (document.getElementById('passwordRegister').value ==
        document.getElementById('passwordRepeat').value) {
        // document.getElementById('message').style.color = 'green';
        document.getElementById('message').innerHTML = '';
        document.getElementById("registerButton").disabled = false;
    } else {
        document.getElementById('message').style.color = 'red';
        document.getElementById('message').innerHTML = 'not matching';
        document.getElementById("registerButton").disabled = true;
    }
}
