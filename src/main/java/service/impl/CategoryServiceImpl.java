package service.impl;

import dao.CategoryDao;
import dto.CategoryState;
import dto.InsertNewState;
import entity.Category;
import entity.New;
import enums.CategoryEnums;
import enums.InsertNewEnums;
import exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CategoryDao categoryDao;


    @Override
    @Transactional
    public CategoryState insertCategory(Category category)
            throws CategoryException,CategoryInsertException{
        try {
            if (category.getCategoryName() == null) {
                throw new CategoryInsertException(CategoryEnums.NAMENULL.getStateInfo());
            } else {
                Category categoryInTable = categoryDao.queryByCategoryName(category.getCategoryName());
                if (categoryInTable != null) {
                    throw new CategoryInsertException(CategoryEnums.EXIST.getStateInfo());
                } else {
                    int count = categoryDao.insertCategory(category);
                    if (count <= 0)//插入失败
                        throw new CategoryInsertException(CategoryEnums.FAIL.getStateInfo());
                    else {//插入成功
                        return new CategoryState(category.getCategoryId(), CategoryEnums.SUCCESS, category);
                    }
                }
            }
        }catch (CategoryInsertException e1) {
            logger.error(e1.getMessage(), e1);
            return new CategoryState(category.getCategoryId(), CategoryEnums.FAIL);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new CategoryState(category.getCategoryId(), CategoryEnums.INNER_ERROR);
        }
    }

    @Override
    @Transactional
    public CategoryState deleteCategory(long categoryId) {
        Category c = categoryDao.queryByCategoryId(categoryId);
        int countDelete = categoryDao.deleteCategory(categoryId);

        if (countDelete <= 0) {
            return new CategoryState(categoryId, CategoryEnums.FAIL);
        } else {
            int freshId = categoryDao.updateAllId();//删除一个目录后重新刷新所有目录id

            if(freshId <= 0)
                return new CategoryState(categoryId, CategoryEnums.FRESHFAIL);
            else
                return new CategoryState(categoryId, CategoryEnums.SUCCESS, c);
        }
    }

    @Override
    @Transactional
    public CategoryState updateCategory(Category c)
                   throws CategoryException, UpdateCategoryException{
        Category category = categoryDao.queryByCategoryId(c.getCategoryId());
        try {
            if (category == null) {
                return new CategoryState(c.getCategoryId(), CategoryEnums.NOTEXIST);
            } else {
                int countUpdate = categoryDao.updateCategory(c);
                if (countUpdate <= 0) {
                    throw new UpdateCategoryException(CategoryEnums.FAIL.getStateInfo());
                } else {
                    return new CategoryState(c.getCategoryId(), CategoryEnums.SUCCESS, c);
                }
            }
        } catch (UpdateCategoryException e1) {
            logger.error(e1.getMessage(), e1);
            return new CategoryState(c.getCategoryId(), CategoryEnums.FAIL);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new CategoryState(c.getCategoryId(), CategoryEnums.INNER_ERROR);
        }

    }

    @Override
    public List<Category> queryAllCategory() {
        return categoryDao.queryAllCategory();
    }

}
