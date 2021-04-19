package entity;


public class Category {
    private long categoryId;

    private String categoryName;
    /** 该类别下的浏览数量 */
    private long viewsNum;

    public Category(long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.viewsNum = 0;
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
        this.viewsNum = 0;
    }

    public Category() {
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getViewsNum() {
        return viewsNum;
    }

    public void setViewsNum(long viewsNum) {
        this.viewsNum = viewsNum;
    }
}
