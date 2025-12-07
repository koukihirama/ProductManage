package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.CategoryBean;

public class CategoryDAO {

	public List<CategoryBean> findAll() throws SQLException {
	    List<CategoryBean> list = new ArrayList<>();

	    // ★ カラム名を実テーブルに合わせる
	    String sql = "SELECT id, category_name FROM categories ORDER BY id";

	    try (Connection con = ConnectionManager.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            CategoryBean c = new CategoryBean();
	            c.setId(rs.getInt("id"));
	            // ★ ここも同じカラム名を使う
	            c.setName(rs.getString("category_name"));
	            list.add(c);
	        }
	    }

	    return list;
	}
}
