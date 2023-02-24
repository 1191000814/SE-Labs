/*
*  Web Utils - 网页使用的ajax工具函数
*/

// var HOST_PORT = "18377ba7.cpolar.io"
// var HOST_PORT="44694618.cpolar.io"//1.4
// var HOST_PORT = "77fdc0c.cpolar.io"
// 3312a246.cpolar.io
var HOST_PORT="3312a246.cpolar.io"
/**
 * appendJsonItemToTable 将一个json字典添加到一个表的一行中。
 * obj_table html中的一个table标签
 * json_item 一个json字典，内含一个条目对应的数据
 * form_list 一个数组，里面是希望添加的属性(有序)
 */
function appendJsonItemToTable(obj_table, json_item, form_list){
  // 添加一行
  let tr = document.createElement("tr");
  obj_table.appendChild(tr);
  for(let i = 0; i < form_list.length; i++){
    // 添加一行中的一项
    let attrname_to_add = form_list[i];
    let td = document.createElement("td");
    td.innerHTML = json_item[attrname_to_add];
    tr.appendChild(td);
  }
}
//input框,草稿修改相关
// input_len是这个table有多少项，这个函数本质上来说只是添加某表格的一行，yxh为了给对象赋值id，1.8
function appendJsonItemToTable_input(obj_table, json_item, form_list,input_index){
  // 添加一行
  let tr = document.createElement("tr");
  obj_table.appendChild(tr);
  for(let i = 0; i < form_list.length; i++){

    if(form_list[i]=="discountRate"||form_list[i]=="num"){
      // console.log(form_list[i])
      // console.log("1")
      let attrname_to_add = form_list[i];
      let td = document.createElement("td");
      // td.innerHTML = json_item[attrname_to_add];//这次就直接添加input框
     
      let tetxbox=document.createElement("input")
      tetxbox.value=json_item[attrname_to_add]
      tetxbox.id=input_index*form_list.length+i;
      td.appendChild(tetxbox)
      tr.appendChild(td);
    }
    // 添加一行中的一项
    else{
      let attrname_to_add = form_list[i];
      let td = document.createElement("td");
      td.innerHTML = json_item[attrname_to_add];
      td.id=input_index*form_list.length+i;
      tr.appendChild(td);
    }
   
  }
}

/**
 * appendJsonItemToTableWithButtons 将一个json字典添加到一个表的一行中，并在最后一个td中加入一组button。
 * obj_table html中的一个table标签
 * json_item 一个json字典，内含一个条目对应的数据
 * form_list 一个数组，里面是希望添加的属性(有序)
 * button_list 一个数组，里面是希望添加的button（真正意义上的button标签）
 */
 function appendJsonItemToTableWithButtons(obj_table, json_item, form_list, button_list){
  // 添加一行
  let tr = document.createElement("tr");
  obj_table.appendChild(tr);
  for(let i = 0; i < form_list.length; i++){
    // 添加一行中的一项
    let attrname_to_add = form_list[i];
    let td = document.createElement("td");
    td.innerHTML = json_item[attrname_to_add];
    tr.appendChild(td);
  }
  // 处理Buttons
  let Td_ButtonGroup = document.createElement("td");
  let Div_Buttons = document.createElement("div");
  Div_Buttons.className = "table_row_div";
  for(let i=0; i< button_list.length; i++){
    // console.log(button_list[i]);
    Div_Buttons.appendChild(button_list[i]);
  }
  Td_ButtonGroup.appendChild(Div_Buttons);
  tr.appendChild(Td_ButtonGroup);
}


/**
 * clearTable 将一个table的所有数据项清空
 * obj_table 一个html的table标签
 */
function clearTable(table){
  let rowNum = table.rows.length;
  for(let i = 0; i < rowNum - 1; i++){
    table.deleteRow(1);
  }
}


/**
 * 使用ajax的Get方式访问url。
 * @param {*} url 请求的地址，get请求要记得携带参数。
 * @param {*} onTrue 如果返回的json的flag值为ture执行的函数
 * @param {*} onFalse 如果返回的json的flag值为false执行的函数
 * onTure 和 onFalse 都默认含有参数data,为返回的json字符串构造的对象。
 */
function ajaxGet(url, onTrue = function(){
}, onFalse = function(){
}){
  let ajax = new XMLHttpRequest();
  ajax.open('get', url);
  ajax.send(); // get请求无需发送参数
  ajax.onreadystatechange = function(){
    if(ajax.readyState === 4 && ajax.status === 200){
      let json_response = JSON.parse(ajax.responseText);
      let data = json_response["data"];
      if(json_response["flag"] === false){
        return onFalse(data);
      }
      return onTrue(data);
    }
  }
}

/**
 * 使用ajax的Post方式访问url。
 * @param {*} url 请求的地址
 * @param {*} action 请求的action参数
 * @param {*} args 访问的参数 是个键值对
 * @param {*} onTrue 如果返回的json的flag值为ture执行的函数
 * @param {*} onFalse 如果返回的json的flag值为false执行的函数
 * onTure 和 onFalse 都默认含有参数data,为返回的json字符串构造的对象
 */
function ajaxPost(url, action, args = {}, onTrue = function(){
}, onFalse = function(){
}){
  let ajax = new XMLHttpRequest();
  ajax.open('post', url);
  ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); // 设置参数为键值对格式
  ajax.send("action=" + action + "&data=" + JSON.stringify(args));
  console.log("action=" + action + "&data=" + JSON.stringify(args))
  ajax.onreadystatechange = function(){
    if(ajax.readyState === 4 && ajax.status === 200){
      if(ajax.readyState === 4 && ajax.status === 200){
        let json_response = JSON.parse(ajax.responseText);
        console.log(json_response);
        let data = json_response["data"];
        if(json_response["flag"] === false){
          return onFalse(data);
        }
        return onTrue(data);
      }
    }
  }
}




function CreateTableRowButton(innerHTML, onclick, styleText=""){
  // 复制用的代码
  let TableRowButton = document.createElement("button");
  TableRowButton.innerHTML = innerHTML;
  TableRowButton.className =  "table_row_button";
  TableRowButton.addEventListener("click", function(){
    return onclick(TableRowButton.parentElement.parentElement.parentElement);  
  });
  TableRowButton.style.cssText += styleText;
  return TableRowButton;
}

// SMART TABLE

// SMART 