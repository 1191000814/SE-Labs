var Button_Query;
var Table_Warehouse;
var Remain_money; //销售额
var localData={
    allRemain:0 //remain 是销售额的默认值
}
var Username;
var Email;
var Status;
var input_username;

function main(){
    //Button_Query = document.getElementById("button_query"); // 查询按钮
    Table_Warehouse = document.getElementById("1");
    //Remain_money=document.getElementById("performance")
    //Remain_money.value=localData.allRemain;
    //Username=document.getElementById("username")
    //Email=document.getElementById("email")
    //Status=document.getElementById("status")


    setEventListener();
    query_self();
}

function setEventListener(){
    
}


function query_self(){
    let input_username = localStorage.getItem("username");
    //console.log("name哈哈哈:"+input_username)
     // 构造ajax报文
    ajaxPost(
         'http://'+HOST_PORT+'/retail/user',
         'get',
         {"username":input_username},
        function(data){
            console.log(data)
            //console.log("self")        
            
                //Remain_money.value=data["performance"];
                //Username.value=data["username"];
                //Email.value=data["email"];
                
                //console.log("销售额哈哈哈   "+data["performance"])

            let data_to_show = JSON.parse(JSON.stringify(data));
                
                    //data_to_show.push(data);
            data_to_show["performance"] = "<h3>"+data["performance"]+"</h3>"; 
            console.log("data_to_show: " , data_to_show)
         
            switch (data["status"]) {
                case 1:
                    data_to_show["status"] = "一级管理员"; 
                    break;
                case 2:
                    data_to_show["status"] = "二级管理员"; 
                    break;
                case 3:
                    data_to_show["status"] = "三级管理员"; 
                    break;    
            }


        
            appendJsonItemToTable(
                Table_Warehouse, 
                data_to_show, 
                ["username","email","status","performance"],
            );                
            console.log(Table_Warehouse)

        },
        function(data){
             alert(data);
        }
    );
}

   



