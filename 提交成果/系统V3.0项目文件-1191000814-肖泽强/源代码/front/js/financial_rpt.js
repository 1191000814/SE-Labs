

var Rpt_table;


function main(){

Rpt_table=document.getElementById("report")
   query_company()
}

//  'http://'+HOST_PORT+'/retail/draftOrder?action=getAll',
function query_company(){
    ajaxGet(
        'http://'+HOST_PORT+'/retail/company?action=getInfo',
       function(data){
            // alert("success!")
           
           console.log(data)
           
           
               appendJsonItemToTable(
                Rpt_table,
                data,
                ["cost","sales","remain","capital"]
               )
           
           
        },
        function(data){
            console.log("fail")
            alert(data);
        }
    
    );

}

