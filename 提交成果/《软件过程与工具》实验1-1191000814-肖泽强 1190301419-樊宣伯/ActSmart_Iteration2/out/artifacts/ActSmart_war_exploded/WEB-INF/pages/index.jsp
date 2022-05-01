<%--
  Created by IntelliJ IDEA.
  User: hp
  Date: 2021/10/14
  Time: 19:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
  input{
    width: 50px;
  }
</style>
<html>
  <head>
    <title>习题生成设置</title>
    <base href="http://localhost:8080/ActSmart/">
  </head>

  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script>
    $(function(){
      $(":submit").click(function(){
        const count = $(":text#count").val()
        const p = /\s*\d+\s*/
        const item = $(":checkbox:checked")
        if(p.test(count) === false){
          alert("数量只能填入数字")
          return false
        }
        if(count > 100){
          alert("别整太多题了,会很累的")
          return false
        }
        if(item.length === 0){
          alert("请至少选择一种题型")
          return false
        }
      })
    })
  </script>

  <body>
  <div style="border: orange solid; text-align: center">
  <h3>加减法练习生成设置</h3>
  <form action="create">
    <label>
      数量:
      <input type="text" name="count" id="count" value="10"><br/><br/>
      范围:
      <select name="range">
        <option value="10">10以内</option>
        <option value="100" selected="selected">100以内</option>
        <option value="1000">1000以内</option>
      </select><br/><br/>
      题型:<br/>
      加法<input type="checkbox" name="add" checked="checked" value="1"><br/>
      减法<input type="checkbox" name="sub" checked="checked" value="1"><br/>
      乘法<input type="checkbox" name="mul" value="1"><br/>
      除法<input type="checkbox" name="div" value="1"><br/><br/>
      <input type="submit" value="生成">
    </label>
  </form>
  </div>
  </body>
</html>
