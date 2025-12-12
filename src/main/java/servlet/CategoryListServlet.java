package servlet;                                              // このクラスは servlet パッケージに属してますよ、という宣言

import java.io.IOException;                                   // 入出力エラー（主にServletでの入出力）用の例外クラス
import java.sql.SQLException;                                 // JDBC操作中のエラーを表す例外クラス
import java.util.List;                                        // 複数件の CategoryBean をまとめるための List インターフェース

import jakarta.servlet.RequestDispatcher;                     // JSP などに処理を引き渡す（フォワードする）ためのクラス
import jakarta.servlet.ServletException;                      // サーブレット全般の例外クラス
import jakarta.servlet.annotation.WebServlet;                 // サーブレットのURLパターンを指定するためのアノテーション
import jakarta.servlet.http.HttpServlet;                      // HTTP用サーブレットの基本クラス
import jakarta.servlet.http.HttpServletRequest;               // クライアントからのリクエスト情報を表すクラス
import jakarta.servlet.http.HttpServletResponse;              // クライアントへのレスポンス情報を表すクラス
import model.dao.CategoryDAO;                                // カテゴリ情報をDBから取得するDAOクラス
import model.entity.CategoryBean;                            // 1件分のカテゴリデータを表すJavaBean

@WebServlet("/category-list")                                // このサーブレットを /category-list というURLに対応させる設定
public class CategoryListServlet extends HttpServlet {       // HttpServlet を継承してカテゴリ一覧用サーブレットを定義

    @Override                                                 // 親クラス(HttpServlet)の doGet をオーバーライドします、の印
    protected void doGet(HttpServletRequest request,          // ブラウザからの GET リクエストを処理するメソッド
                         HttpServletResponse response)
            throws ServletException, IOException {

        CategoryDAO dao = new CategoryDAO();                  // DB からカテゴリ一覧を取得するために DAO のインスタンスを生成

        try {
            List<CategoryBean> categories = dao.findAll();    // DAO の findAll() を呼んで、全カテゴリのリストを取得

            // JSP に渡す
            request.setAttribute("categories", categories);   // 取得したカテゴリリストを "categories" という名前でリクエスト属性にセット
                                                              // → JSP側で request.getAttribute("categories") で取り出せる

            // JSP にフォワード
            RequestDispatcher rd =
                    request.getRequestDispatcher("/category-list.jsp"); // 表示を担当する JSP のパスを指定してディスパッチャを取得
            rd.forward(request, response);                              // JSP に処理を引き渡して画面を表示

        } catch (SQLException e) {                         // DB処理中にエラーが起きた場合はこちらに来る
           
            throw new ServletException(e);                 // そのまま ServletException に包んで上位に投げる（コンテナに任せる）
        }
    }
}