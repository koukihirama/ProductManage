package model.entity;

import java.io.Serializable;

public class CategoryBean implements Serializable {

    private int id;         // カテゴリID
    private String name;    // カテゴリ名

    // ★ JavaBean ルール：引数なしコンストラクタ
    public CategoryBean() {
    }

    // 便利用コンストラクタ（任意だけどあると楽）
    public CategoryBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // ★ getter / setter（プロパティごとにペアで用意）

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}