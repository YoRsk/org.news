package service;

import dto.CategoryState;
import dto.CommentState;
import entity.Category;
import entity.Comment;

import java.util.List;

public interface CategoryService {

    /*
     * 添加目录
     * */
    CategoryState insertCategory(Category category);

    /*
     * 删除目录(则会将该目录下所有文章都删除
     * */
    CategoryState deleteCategory(long categoryId);

    /*
     * 更新目录
     * */
    CategoryState updateCategory(Category c);
    /*
     * 获取所有目录
     * */
    List<Category> queryAllCategory();


}
