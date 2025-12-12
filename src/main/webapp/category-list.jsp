<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.List, model.entity.CategoryBean" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>カテゴリリスト</title>
<style>
  table, th, td {
    border: 3px double #000;
    border-collapse: collapse;
}
</style>
</head>
<body>
  <h1>カテゴリリスト</h1>
  
  <p>
    <a href="category-register.jsp">カテゴリを新規登録する</a>
  </p>

  <table>
    <tr>
      <th>カテゴリID</th>
      <th>カテゴリ名</th>
    </tr>

<%
    // サーブレットから渡された「categories」を受け取る
    List<CategoryBean> list = (List<CategoryBean>) request.getAttribute("categories");
    if (list != null) {
        for (CategoryBean c : list) {
%>
    <tr>
      <td><%= c.getId() %></td>
      <td><%= c.getName() %></td>
    </tr>
<%
        }
    }
%>
  </table>
</body>
</html>