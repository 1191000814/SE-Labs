var Input_Username;
var Input_Password;
var Input_PasswordAgain;
var Input_Role;
var Button_Send;

function main(){
  Button_Send = document.getElementById("send");
  Input_Role = document.getElementById("role");
  Input_PasswordAgain = document.getElementById("password_again");
  Input_Password = document.getElementById("password");
  Input_Username = document.getElementById("username");
  setEventListener();
}

function setEventListener(){
  Button_Send.addEventListener("click", register);
}

function register(){
    // 获取请求数据
    let username = Input_Username["value"].trim();
    let input_password_again = Input_PasswordAgain["value"].trim();
    let input_password = Input_Password["value"].trim();
    let input_role = Input_Role["value"].trim(); // 角色 暂时还没用

    if(input_password !== input_password_again){
      alert("两次密码输入不一致");
      return;
    }

    if(input_password.length <= 0){
      alert("密码为空");
      return;
    }

    if(username.length <= 0){
      alert("用户名为空");
      return;
    }
    // 构造ajax报文
    ajaxPost(
        'http://'+HOST_PORT+'/retail/user',
        'register',
        {"username": username, "password": input_password},
        function(data){
            alert("注册成功，返回登录");
            location.href = "../html/login.html";
        },
        function(data){
          alert(data);
        }
    );
}
