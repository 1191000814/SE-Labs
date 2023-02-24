var Add_Client;
var Delete_Client;
var Show_Table;
var Customer_Name;
var Customer_Company;
var Customer_Phone;
var Customer_Method;

// 客户更新相关
var Div_Update;
var Company_Update;
var Phone_Update;
var Method_Update;
var Submit_Update;
var id_to_update;
var last_Row_to_update = null;
// 通过名字查询顾客  12.10 by yxh
var Search_client;
var Name_client;

function main(){
    Add_Client=document.getElementById("add_client");
    Delete_Client=document.getElementById("delete_client");
    Show_Table=document.getElementById("show_table");
    Customer_Name=document.getElementById("name");
    Customer_Company=document.getElementById("company");
    Customer_Phone=document.getElementById("phone");
    Customer_Method=document.getElementById("method");
// 更新相关的
    Div_Update = document.getElementById("div_update");
    Company_Update = document.getElementById("company_update");
    Phone_Update = document.getElementById("phone_update");
    Method_Update = document.getElementById("method_update");
    Submit_Update = document.getElementById("submit_update");
//  查询相关的 Name_client是查询时输入的名字 Search_client是查询的button
    Search_client=document.getElementById("search_client");
    Name_client=document.getElementById("name_client");
    query_customers("");
    setEventListener();
}
// 用户的查询功能（以及删除功能）
function query_customers(name=''){
    ajaxPost(
        'http://'+HOST_PORT+'/retail/customer',
        'search',
        {"name":name},
        function(data){
            let data_to_show = JSON.parse(JSON.stringify(data));
            for(let i=0;i<data.length;i++){
                data_to_show.push(data[i]);
                data_to_show[i]["method"] = data[i]["method"] === true? 
                "<h3 style='color:red'>批发</h3>":
                "<h3 style='color:green'>零售</h3>";
            }
            for(let i=0;i<data.length;i++){
                appendJsonItemToTableWithButtons(
                    Show_Table,
                    data_to_show[i],
                    ["name", "phone","company","method"],
                    [
                        CreateTableRowButton("修改",function(Row){
                            // 获取需要的参数
                            id_to_update = data[i]["id"];
                            Company_Update["value"] = data[i]["company"];
                            Phone_Update["value"] = data[i]["phone"];
                            Method_Update["value"] ="";
                            // 将更新对话框显示出来
                            Div_Update.style["display"] = "inline";
                            // 设置其它行为默认颜色
                            if(last_Row_to_update !== null){
                                for(let i=0; i<last_Row_to_update.childNodes.length-1; i++){
                                    last_Row_to_update.childNodes[i].style["color"] = "#4f6b72";
                                }
                            }
                            // 设置选中的行为红色
                            for(let i=0; i<Row.childNodes.length-1; i++){
                                Row.childNodes[i].style["color"] = "red";
                            }
                            

                            // 重新更新下一行的指针
                            last_Row_to_update = Row;
                        }),
                        CreateTableRowButton("删除", function(Row){
                            ajaxPost(
                                'http://'+HOST_PORT+'/retail/customer',
                                'delete',
                                {"id":data[i]["id"]},
                                function(data){
                                    clearTable(Show_Table);
                                    query_customers();
                                },
                                function(data){
                                    alert("删除失败：" + data);
                                }
                            );
                        }, "background-color:red;color:white;")
                    ]
                    );
            }
        },
        function(data){
            alert(data);
        }
    );
}

function setEventListener(){
    // 添加客户信息
    Add_Client.addEventListener("click",function(){
        let name=Customer_Name["value"];
        //--------  名字输入限定？------------------
        let company=Customer_Company ["value"];
        let phone=Customer_Phone["value"];
        let method = Customer_Method["value"].trim()=="批发"?true:false;

        ajaxPost(
            'http://'+HOST_PORT+'/retail/customer',
            'add',
            {"name": name,"company": company,"phone": phone,"method":method},
            function(data){
                clearTable(Show_Table);
                query_customers("*");
            },
            function(data){
                alert(data);
            }
        );
    });

    //更新客户信息
    Submit_Update.addEventListener('click', function(){
        let new_company = Company_Update["value"];
        let new_phone = Phone_Update["value"];
        let new_method = Method_Update["value"].trim()=="批发"?true:false;
        let id = id_to_update; // id由每一行的按钮在点击时提供
        Div_Update.style["display"] = "inline";
        ajaxPost(
            'http://'+HOST_PORT+'/retail/customer',
            'update',
            {"id": id,"company":new_company,"phone":new_phone, "method":new_method},
            function(data){
                clearTable(Show_Table);
                query_customers("*");
                Div_Update.style["display"] = "none";
                for(let i=0; i<Row.childNodes.length-1; i++){
                    Row.childNodes[i].style["color"] = "4f6b72";
                }
            },
            function(data){
                alert(data);
            }
        );
    });
    
    //通过名字查询顾客 12/10
    Search_client.addEventListener('click',function(){
        let name_client=Name_client["value"];
        if(name_client!==null){
            console.log("非空");
            // ajaxPost(
            //     'http://'+HOST_PORT+'/retail/customer',
            //     'search',
            //     {"name":name_client},
            //     function(data){
            //         console.log("onTrue");
            //         // 清空表格
            //         clearTable(Show_Table);
            //         // 再打印出查到的数据，data中是name查到的吗   {"name": name,"company": company,"phone": phone,"method":method}
            //         // query_customers();
            //         for(let i=0;i<data.length;i++){
            //             appendJsonItemToTable(Show_Table, data[i], ["name", "company","phone","method"]);
            //         }
            //     },
            //     function(){
            //         alert("查询失败：");
            //         console.log("data:"+data);
            //     }
            // );

            //12.10最终版本，没想到最后这么简单
            clearTable(Show_Table);
            query_customers(name_client);
        }else{
          alert("查询名字为空");
        }
        //健壮性，
    
        
    });

}