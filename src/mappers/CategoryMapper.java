package mappers;

import entity.Category;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {

    public List<Category> getCategories( Connection connection ) {
        return new ArrayList<Category>();
    }

    public Category getCategoryByID( Connection connection, int categoryId ) {
        return new Category();
    }

    public int insertCategory( Connection connection, Category object ) {
        return 1;
    }

    public int updateCategory( Connection connection, int categoryId ) {
        return 1;
    }

    public int deleteCategory( Connection connection, int categoryId ) {
        return 1;
    }
}
