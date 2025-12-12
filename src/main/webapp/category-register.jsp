<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>カテゴリ登録</title>
</head>
<body>
  <h1>カテゴリ登録フォーム</h1>

  <!-- 一覧に戻るリンク -->
  <p><a href="category-list">カテゴリ一覧に戻る</a></p>

  <!-- カテゴリ登録フォーム -->
  <form action="category-register" method="post">
    <div>
      <label>カテゴリID：</label>
      <input type="text" name="categoryId">
    </div>
    <div>
      <label>カテゴリ名：</label>
      <input type="text" name="categoryName">
    </div>
    <div>
      <button type="submit">登録</button>
    </div>
  </form>
</body>
</html>