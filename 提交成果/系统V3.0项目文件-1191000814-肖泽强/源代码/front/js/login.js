var Input_Password;
var Input_Username;
var Button_Submit;

function main(){
  Input_Password = document.getElementById("password");
  Input_Username = document.getElementById("username");
  Button_Submit = document.getElementById("login_button");
  setEventListener();
}

function setEventListener(){
  Button_Submit.addEventListener("click", function(){
    // 获取请求数据
    let password = Input_Password["value"];
    let username = Input_Username["value"];
    ajaxPost(
        'http://'+HOST_PORT+'/retail/user',
        'login',
        {"username": username, "password": password},
        function(data){
          localStorage.setItem("status", data["status"]);
          localStorage.setItem("email", data["email"])
          localStorage.setItem("username", username);
          localStorage.setItem("localId",data["id"])//1.8
          location.href = "../html/self.html"
        },
        function(data){
          alert(data);
        }
    );
  });
}