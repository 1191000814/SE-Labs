var Button_Query;
var Input_Warehouse;
var Input_Goods;
var Table_Warehouse;
var Button_Query1; 
var Input_goodsId; 
var Input_goodsNum; 
var Input_oldRepo 
var Input_newRepo 
var Input_repoId
var Button_Query2


var Remain_money; //积压金额
var localData={
    allRemain:0 //remain 是积压金额的默认值
    // 其实没啥用
    
}

// 进货
var Add_goods;
var Goods_id
var Goods_num
var Ware_id

function main(){
    Button_Query = document.getElementById("button_query"); // 查询按钮
    Input_Warehouse = document.getElementById("input_warehouse");
    Input_Goods = document.getElementById("input_goods");
    Table_Warehouse = document.getElementById("warehouse_table");
    Button_Query1 = document.getElementById("button_query1"); // 查询按钮
    Input_goodsId = document.getElementById("1")
    Input_goodsNum = document.getElementById("2")
    Input_oldRepo = document.getElementById("3")
    Input_newRepo = document.getElementById("4")
    Button_Query2 = document.getElementById("button_query2"); // 查询按钮
    // 进货
    Add_goods=document.getElementById("add_goods")
    Goods_id=document.getElementById("goods_id")
    Goods_num=document.getElementById("goods_num")
    Ware_id=document.getElementById("ware_id")
    // 积压金额：
    Remain_money=document.getElementById("remain_money")
    Remain_money.value=localData.allRemain;

    
    setEventListener();
    // query_warehouse();//不用查询，每个仓库点就好了1.7yxh
}

function setEventListener(){
    Button_Query.addEventListener("click", query_warehouse);
    Button_Query1.addEventListener("click", query_warehouse1);
    Add_goods.addEventListener("click",add_goods)
}
//1.7
function add_goods(){
    // console.log("1")
    let goods_id=Goods_id["value"]
    let goods_num=Goods_num["value"]
    let ware_id=Ware_id["value"]
    ajaxPost(
        'http://'+HOST_PORT+'/retail/repo',
        'add',
        {"goodsId":goods_id,"goodsNum":goods_num,"repoId":ware_id},
        function(data){
            console.log("success")
            clearTable(Table_Warehouse)

        },
        function(data){
            console.log("fail")
        }
    )
}

function query_warehouse(){
    // 获取数据
    warehouse_name = Input_Warehouse["value"]; // 哪个仓库
    goods_name = Input_Goods["value"]; // 哪个商品

     // 构造ajax报文
    ajaxPost(
         'http://'+HOST_PORT+'/retail/repo',
         'search',
         {"goodsName":goods_name, "repoId":warehouse_name},
        function(data){
            // alert("query_warehouse:"+localData.allRemain)
            Remain_money.value=parseFloat(0)
            // alert("remain   "+Remain_money.value)
            // alert("data[0].goodsName"+data[0].goodsName)

            clearTable(Table_Warehouse);
               
            for(let i=0; i<data.length; i++){
                // 计算remain的值：
               Remain_money.value=parseFloat(data[i].remain)+parseFloat( Remain_money.value)
                // 如何是数字相加而不是字符连接
                // console.log("remain   "+Remain_money.value)
                appendJsonItemToTable(
                    Table_Warehouse, 
                    data[i], 
                    ["goodsId","goodsName", "goodsNum","repoId","remain"],
                    // 新增了remain,是指每个货品项的
                 
                );
                
            }
        },
        function(data){
             alert(data);
        }
    );

    }

   function query_warehouse1(){
       //获取数据
    goods_Id = Input_goodsId["value"];//哪个商品
    goods_Num = Input_goodsNum["value"];//商品数量
    old_Repo = Input_oldRepo["value"];//原仓库号
    new_Repo = Input_newRepo["value"];//新仓库号

    //构造ajax报文
    ajaxPost(
        'http://'+HOST_PORT+'/retail/repo',
        'dispatch',
        {"goodsId":goods_Id, "goodsNum":goods_Num,"oldRepo":old_Repo,"newRepo":new_Repo},
       function(data){
           clearTable(Table_Warehouse);
       },
       function(data){
            alert(data);
       }
   );

   }

   function query_warehouse2(){
    repo_Id = Input_repoId["value"];//哪些仓库
    //构造ajax报文
    ajaxPost(
        'http://'+HOST_PORT+'/retail/repo',
        'getAll',
        {"repoId":repo_Id},
       function(data){
           clearTable(Table_Warehouse); 
           for(let i=0; i<data.length; i++){
               appendJsonItemToTableWithButtons(
                   Table_Warehouse, 
                   data[i], 
                   ["goodsId","goodsName", "goodsNum","repoId"],
                   [
                       CreateTableRowButton("恢复", function(Row){
                       Row.childNodes[1].innerHTML = 12;
                   }),
                       CreateTableRowButton("删除", function(Row){
                       Row.childNodes[1].innerHTML = Number(Row.childNodes[1].innerHTML) - 1;
                       })
                   ]
               );
               
           }
       },
       function(data){
            alert(data);
       }
   );

   }


