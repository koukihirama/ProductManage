package servlet;                                                   // このクラスは servlet パッケージに属する、という宣言

import java.io.IOException;                                        // 入出力関連の例外（主にレスポンス書き込みなど）を扱うクラス
import java.sql.SQLException;                                      // JDBC（DB操作）中のエラーを表す例外クラス

import jakarta.servlet.RequestDispatcher;                          // JSP などに処理をフォワードするためのクラス
import jakarta.servlet.ServletException;                           // サーブレットで発生した一般的な例外を表すクラス
import jakarta.servlet.annotation.WebServlet;                      // サーブレットにURLパターンを紐付けるためのアノテーション
import jakarta.servlet.http.HttpServlet;                           // HTTP用サーブレットの基本クラス
import jakarta.servlet.http.HttpServletRequest;                    // HTTPリクエストの情報（パラメータなど）を表すクラス
import jakarta.servlet.http.HttpServletResponse;                   // HTTPレスポンスの情報（ステータスや出力）を表すクラス
import model.dao.CategoryDAO;                                      // カテゴリのDB操作を行うDAOクラス
import model.entity.CategoryBean;                                  // 1件分のカテゴリ情報を持つJavaBean

@WebServlet("/category-register")                                  // このサーブレットを /category-register というURLに対応させる設定
public class CategoryRegisterServlet extends HttpServlet {         // カテゴリ登録用のサーブレットクラス宣言（HttpServletを継承）

    private static final long serialVersionUID = 1L;               // シリアライズ用のバージョンID（お約束として入れておく定数）

    public CategoryRegisterServlet() {                             // デフォルトコンストラクタ（特に処理はしていない）
        super();                                                   // 親クラス(HttpServlet)のコンストラクタ呼び出し
    }

    // ▼ GET リクエストが来たときはフォーム画面に飛ばす（直接URL叩かれた用）
    @Override
    protected void doGet(HttpServletRequest request,               // ブラウザからの GET リクエストを処理するメソッド
                         HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher rd =
                request.getRequestDispatcher("/category-register.jsp"); // 表示用のJSP（フォーム画面）を指定してディスパッチャを取得
        rd.forward(request, response);                                  // JSP に処理をフォワードして画面表示を任せる
    }

    // ▼ フォームからの POST を受け取ってDBにINSERTするメイン処理
    @Override
    protected void doPost(HttpServletRequest request,              // フォーム送信(POST)を処理するメソッド
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");                     // リクエストボディの文字コードをUTF-8として解釈（日本語対策）

        String idParam = request.getParameter("categoryId");       // フォームの name="categoryId" の値を文字列として取得
        String name = request.getParameter("categoryName");        // フォームの name="categoryName" の値を文字列として取得

        String errorMessage = null;                                // エラー内容を格納するための変数（なければnullのまま）
        Integer id = null;                                         // 数値変換後のカテゴリIDを入れておくための変数

        // ---- 必須チェック ----
        if (idParam == null || idParam.isBlank()                   // IDがnullまたは空/空白のみならNG
         || name == null || name.isBlank()) {                      // 名前がnullまたは空/空白のみでもNG
            errorMessage = "カテゴリIDとカテゴリ名は必須です。";       // 必須エラーメッセージをセット
        } else {
            // ---- 数値チェック ----
            try {
                id = Integer.parseInt(idParam);                    // 文字列のIDをintに変換（成功すればidに代入）
            } catch (NumberFormatException e) {                    // 数字に変換できなかった場合
                errorMessage = "カテゴリIDは数値で入力してください。"; // 数値エラーメッセージをセット
            }
        }

        // エラーがあればフォームに戻す
        if (errorMessage != null) {                                // errorMessageがセットされていれば何かしらエラーが発生している
            request.setAttribute("error", errorMessage);           // エラーメッセージをリクエスト属性に格納（JSPで表示する用）
            request.setAttribute("categoryId", idParam);           // 入力されていたIDを再表示用に保存
            request.setAttribute("categoryName", name);            // 入力されていたカテゴリ名も再表示用に保存

            RequestDispatcher rd =
                    request.getRequestDispatcher("/category-register.jsp"); // もう一度フォームJSPを指定
            rd.forward(request, response);                        // 同じリクエスト内でフォーム画面に戻る（入力値・エラーを表示）
            return;                                               // ここでメソッドを終了し、これ以降の処理（INSERT）は行わない
        }

        // ---- バリデーションOK → Bean に詰める ----
        CategoryBean category = new CategoryBean();               // 1件分のカテゴリを表すBeanを生成
        category.setId(id);                                       // 数値変換済みのIDをセット
        category.setName(name);                                   // カテゴリ名をセット

        CategoryDAO dao = new CategoryDAO();                      // DB操作を行うDAOのインスタンスを生成
        try {
            dao.insert(category);                                 // DAOのinsertメソッドを呼び出してDBに1件登録
        } catch (SQLException e) {                                // DB操作中にSQL例外が出た場合
            throw new ServletException(e);                        // ServletExceptionに包んでコンテナに投げる（エラーページなどに飛ばせる）
        }

        // 登録成功 → 一覧へリダイレクト
        response.sendRedirect("category-list");                   // category-list にリダイレクトして一覧サーブレットを呼ぶ（PRGパターン）
    }
}                                                                  // CategoryRegisterServlet クラスの終わり}