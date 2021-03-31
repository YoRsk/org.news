package dto;

import entity.Category;
import enums.CategoryEnums;

public class CategoryState {
    private long categoryId;

    private int state;

    private String stateInfo;

    private Category category;

    public CategoryState(long categoryId, CategoryEnums categoryEnums) {
        this.categoryId = categoryId;
        this.state = categoryEnums.getState();
        this.stateInfo = categoryEnums.getStateInfo();
    }
    public CategoryState(long categoryId, CategoryEnums categoryEnums,Category category) {
        this.categoryId = categoryId;
        this.state = categoryEnums.getState();
        this.stateInfo = categoryEnums.getStateInfo();
        this.category = category;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
