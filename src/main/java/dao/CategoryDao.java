package dao;

import dto.CommentData;
import entity.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryDao {
    /*
     * 添加目录
     * */
    int insertCategory(Category category);

    /*
     * 删除指定id的目录
     * */
    int deleteCategory(@Param("categoryId")long categoryId);

    /*
     * 更新目录数据
     * */
    int updateCategory(Category category);

    /*
     * 更新目录点击量
     * */
    int updateViewsNum(Category category);

    /*
     * 根据id查找目录
     * */
    Category queryByCategoryId(long categoryId);

    /*
     * 根据名字查找目录
     * */
    Category queryByCategoryName(String categoryName);

    /*
     * 获取所有目录
     * */
    List<Category> queryAllCategory();
    /*
     * 重新刷新自增id
     * */
    int updateAllId();
}
