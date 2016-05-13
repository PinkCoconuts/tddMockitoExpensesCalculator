package mappers;

import entity.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class CategoryMapper {

    public List<Category> getCategories( Connection connection, Logger logger ) {
        List<Category> categories = new ArrayList();
        categories.add( new Category( 1, "Music" ) );
        categories.add( new Category( 2, "Food" ) );
        categories.add( new Category( 3, "Bills" ) );
        return categories;
    }

    public Category getCategoryByID( Connection connection, Logger logger, int categoryId ) {
        return new Category( 1, "Music" );
    }

    public int insertCategory( Connection connection, Logger logger, Category object ) {
        return 1;
    }

    public int updateCategory( Connection connection, Logger logger, int categoryId, Category newObject ) {
        return 1;
    }

    public int deleteCategory( Connection connection, Logger logger, int categoryId ) {
        return 1;
    }

    public int wipeCategoryTable( Connection connection, Logger logger ) {
        return 1;
    }
}
