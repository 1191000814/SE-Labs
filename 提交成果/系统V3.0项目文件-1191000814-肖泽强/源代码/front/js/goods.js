var Add_Goods;
// var Delete_Goods;
var Show_Table;
var Goods_Name;
var Goods_Price;
var Query_Goods;
// 增添相关的4个id
var Add_name;
var Add_tradePrice;
var Add_cost;
var Add_price;
// 更新相关的几个id
var id_to_update;
var last_Row_to_update = null;

var Div_Update;
var Update_name;
var Update_tradePrice;
var Update_price;
var Update_cost;
var Update_submit;



function main(){

    // Delete_Goods=document.getElementById("delete_goods");   //delete在表里实现了
    Add_Goods=document.getElementById("add_goods");
    Show_Table=document.getElementById("show_table");
    Goods_Name=document.getElementById("goods_name");
    Goods_Price=document.getElementById("goods_price");
    Query_Goods=document.getElementById("query_goods")
    // 关于增加的四个参数   yxh
    Add_name=document.getElementById("add_name");
    Add_tradePrice=document.getElementById("add_tradePrice");
    Add_cost=document.getElementById("add_cost");
    Add_price=document.getElementById("add_price");
    // 关于更新的几个参数  yxh
    Div_Update=document.getElementById("div_update");
    Update_name=document.getElementById("update_name");
    Update_tradePrice=document.getElementById("update_tradePrice");
    Update_price=document.getElementById("update_price");
    Update_cost=document.getElementById("update_cost");
    Update_submit=document.getElementById("update_submit");
// //    打印
//     show_goods();
    query_goods("");
    setEventListener();
}


function query_goods(name=""){
    ajaxPost(
        'http://'+HOST_PORT+'/retail/goods',
        'search',
        {"name":name},
        function(data){
            let data_to_show = JSON.parse(JSON.stringify(data));          
            for(let i=0;i<data.length;i++){
                data_to_show[i]["profit"] = "<h3>"+data[i]["profit"]+"</h3>"; 
            }
            //console.log("data_to_show: " , data_to_show)
            for(let i=0;i<data.length;i++){
                appendJsonItemToTableWithButtons(
                Show_Table,
                data_to_show[i],
                ["name","id", "price","tradePrice","cost","profit"],
                [
                    
                    CreateTableRowButton("修改",function(Row){
                        id_to_update=data[i]["id"];

                        Div_Update.style["display"]="inline";

                         // 设置其它行为默认颜色
                         console.log("123");
                         console.log(id_to_update);
                         console.log(last_Row_to_update);
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
                    })   ,
                    CreateTableRowButton("删除", function(Row){
                        
                        ajaxPost(
                            'http://'+HOST_PORT+'/retail/goods',
                            'delete',
                            {"id":data[i]["id"]},
                            function(data){
                                clearTable(Show_Table);
                                // clear之后再重新打印一次
                                query_goods();
                            },
                            function(data){
                                // alert("删除失败...");
                                alert(data);
                            }
                        );

                    }, "background-color:red;color:white;"  )
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

    // 查询商品 成功
    Query_Goods.addEventListener("click", function(){
        let goods_name=Goods_Name["value"];
        ajaxPost(
            'http://'+HOST_PORT+'/retail/goods',
            'search',
            {'name':goods_name},
            function(data){
                clearTable(Show_Table);
                // for(let i=0;i<data.length;i++){
                //     appendJsonItemToTable(Show_Table, data[i], ["name","id", "price","tradePrice","cost"]);
                // }
                for(let i=0;i<data.length;i++){
                    appendJsonItemToTableWithButtons(
                    Show_Table,
                    data[i],
                    ["name","id", "price","tradePrice","cost","profit"],
                    [
                        CreateTableRowButton("删除", function(Row){
                            ajaxPost(
                                'http://'+HOST_PORT+'/retail/goods',
                                'delete',
                                {"id":data[i]["id"]},
                                function(data){
                                    clearTable(Show_Table);
                                    // clear之后再重新打印一次
                                    query_goods();
                                },
                                function(data){
                                    // alert("删除失败...");
                                    alert(data);
                                }
                            );
                        }),
                        CreateTableRowButton("修改",function(Row){
                            id_to_update=data[i]["id"];
    
                            Div_Update.style["display"]="inline";
    
                             // 设置其它行为默认颜色
                             console.log("123");
                             console.log(id_to_update);
                             console.log(last_Row_to_update);
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
                        })
                    ]
                    );
                }
            },
            function(data){
                clearTable(Show_Table);
                let data_to_show = JSON.parse(JSON.stringify(data));
                alert("查询商品错误:" + data_to_show);
            },
        );
    })

    //  增加货品 yxh 2021/12/8
    Add_Goods.addEventListener("click",function(){
        // let goods_name = Goods_Name["value"];
        // let goods_price=Goods_Price["value"];
        let goods_add_name=Add_name["value"];
        let goods_add_tradePrice=Add_tradePrice["value"];
        let goods_add_cost=Add_cost["value"];
        let goods_add_price=Add_price["value"];
        console.log("123");
        ajaxPost(
            'http://'+HOST_PORT+'/retail/goods',
            'add',
            // {"Goods_Name": goods_name,"Goods_Price": goods_price},
            {"name": goods_add_name,"tradePrice":goods_add_tradePrice,"price": goods_add_price,"cost":goods_add_cost},
            function(data){
                clearTable(Show_Table);
                query_goods();
            },
            function(data){
                alert("增加商品错误" + data);
            }
        );
    });

    

//---更新货品信息---------

    Update_submit.addEventListener('click',function(){
        let id=id_to_update;
     
        let update_tradePrice=Update_tradePrice["value"];
        let update_price=Update_price["value"];
        let update_cost=Update_cost["value"];
        // let update_submit=Update_submit["value"];
        Div_Update.style["display"]="inline";
        ajaxPost(
            'http://'+HOST_PORT+'/retail/goods',
            'update',
            {"id":id,"tradePrice":update_tradePrice,"price":update_price,"cost":update_cost},
            function(data){
                clearTable(Show_Table);
                query_goods("*");
                Div_Update.style["display"] = "none";
                // for(let i=0; i<Row.childNodes.length-1; i++){
                //     Row.childNodes[i].style["color"] = "4f6b72";
                // }
            },
            function(data){
                // alert(data);
                alert("123");
            }
        );
    });
}