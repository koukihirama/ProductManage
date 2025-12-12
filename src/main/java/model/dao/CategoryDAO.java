package model.dao;                       // このクラスは model.dao パッケージに属してますよ、という宣言

import java.sql.Connection;              // DB との接続を表すクラス
import java.sql.PreparedStatement;       // プレースホルダ付き SQL を扱うためのクラス
import java.sql.ResultSet;              // SELECT の結果行を読み取るためのクラス
import java.sql.SQLException;           // JDBC 操作でエラーが出たときに投げられる例外クラス
import java.util.ArrayList;             // List の具体的な実装クラス（可変長配列）
import java.util.List;                  // 複数件の CategoryBean をまとめるためのインターフェース

import model.entity.CategoryBean;       // 1件分のカテゴリ情報を表す JavaBean（id と name を持つやつ）

public class CategoryDAO {              // Category に関する DB 操作をまとめた DAO クラスの宣言
                                        // Data Access Object の略で、「DB専用の窓口」みたいな役割

    public List<CategoryBean> findAll() throws SQLException {      // 全カテゴリを取得するメソッド
        List<CategoryBean> list = new ArrayList<>();               // 取得したカテゴリを詰めて返すための List を用意

        // ★ カラム名を実テーブルに合わせる
        String sql = "SELECT id, category_name FROM categories ORDER BY id";
        // 実際に DB に送る SQL 文
        // categories テーブルから id と category_name を取り出し、id 昇順で並べる

        try (Connection con = ConnectionManager.getConnection();   // DB との接続を取得（try-with-resources で自動クローズ）
             PreparedStatement ps = con.prepareStatement(sql);     // SQL を実行するための PreparedStatement を作成
             ResultSet rs = ps.executeQuery()) {                   // SELECT を実行して結果セットを取得

            while (rs.next()) {                                    // 1行ずつ結果を読み進める（行がある間ループ）
                CategoryBean c = new CategoryBean();               // 1レコード分のデータを入れるための Bean を生成
                c.setId(rs.getInt("id"));                          // 結果セットから「id」カラムを int として取得して Bean にセット
                // ★ ここも同じカラム名を使う
                c.setName(rs.getString("category_name"));          // 結果セットから「category_name」カラムを String として取得して Bean にセット
                list.add(c);                                       // 作った Bean を List に追加していく
            }
        }                                                           // ここを抜けると con / ps / rs は自動で close される

        return list;                                               // すべてのカテゴリを詰めた List を呼び出し元に返す
    }
    
    public int insert(CategoryBean category) throws SQLException { // 1件のカテゴリを DB に登録するメソッド
        String sql = "INSERT INTO categories (id, category_name) VALUES (?, ?)";
        // プレースホルダ (?) を使った INSERT 文。
        // 後で setInt/setString で ? の部分に値を入れる

        try (Connection con = ConnectionManager.getConnection();   // DB 接続を取得（また try-with-resources）
             PreparedStatement ps = con.prepareStatement(sql)) {   // 上の SQL を実行する PreparedStatement を生成

            ps.setInt(1, category.getId());                        // 1番目の ? にカテゴリID をセット
            ps.setString(2, category.getName());                   // 2番目の ? にカテゴリ名をセット

            return ps.executeUpdate();                             // SQL を実行。影響を受けた行数（通常 1）を返す
        }                                                          // ここを抜けると con / ps が自動クローズ
    }
}