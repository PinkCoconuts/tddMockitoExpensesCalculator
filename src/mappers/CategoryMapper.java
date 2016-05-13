package mappers;

import entity.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
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
        System.out.println( "hi" );
        PreparedStatement preparedStatement = null;
        String SQLString = "SELECT * FROM Category_TBL WHERE ID = ?";
        Category foundCategory = new Category();

        try {
            preparedStatement = connection.prepareStatement( SQLString );
            preparedStatement.setInt( 1, categoryId );
            ResultSet rs = preparedStatement.executeQuery();

            if ( rs.next() ) {
                int selectedCatId = rs.getInt( 1 );
                String selectedCatName = rs.getString( 2 );

                foundCategory = new Category( selectedCatId, selectedCatName );
            } else {
                logger.warning( "Found nothing - getCategoryByID - CategoryID " + categoryId );
                return null;
            }
        } catch ( SQLException e ) {
            logger.severe( "Exception - getCategoryByID - Execution SQL Exception : [ " + e + " ]" );
            return null;
        } finally {
            try {
                if ( preparedStatement != null ) {
                    preparedStatement.close();
                }
            } catch ( SQLException e ) {
                logger.warning( "Exception - getCategoryByID - Closing SQL Exception : [ " + e + " ]" );
            }
        }
        logger.info( "Successfully retrieved category : " + foundCategory.toString() );
        return foundCategory;
    }

    public Category insertCategory( Connection connection, Logger logger, Category object ) {
        PreparedStatement preparedStatement = null;
        int categoryID = 0;
        Category insertedCategory;

        String SQLString = "select CATEGORY_ID_SEQ.nextval from dual";
        try {
            preparedStatement = connection.prepareStatement( SQLString );
            ResultSet rs = preparedStatement.executeQuery();
            try {
                if ( rs.next() ) {
                    categoryID = rs.getInt( 1 );
                }
            } finally {
                try {
                    rs.close();
                } catch ( Exception e ) {
                    logger.warning( "Exception - insertCategory (Part 1) - ResultSet : " + e );
                }
            }
        } catch ( SQLException e ) {
            logger.severe( "Exception - insertCategory (Part 1) - Execution SQL Exception : [ " + e + " ]" );
            return null;
        } finally {
            try {
                if ( preparedStatement != null ) {
                    preparedStatement.close();
                }
            } catch ( SQLException e ) {
                logger.warning( "Exception - insertCategory (Part1) - Closing SQL Exception : [ " + e + " ]" );
            }
        }

        String insertQuery = "INSERT INTO CATEGORY_TBL (ID, NAME) VALUES (?, ?)";
        preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( insertQuery );

            preparedStatement.setInt( 1, categoryID );
            preparedStatement.setString( 2, object.getName() );

            preparedStatement.executeUpdate();

        } catch ( SQLException e ) {
            logger.severe( "Method insertCategory - SQL Exception :"
                    + " [ " + e + " ], Category content : [" + object.toString() + "]" );
            return null;
        } finally {
            try {
                if ( preparedStatement != null ) {
                    preparedStatement.close();
                }
            } catch ( SQLException e ) {
                logger.warning( "Method insertCategory - Closing Statement exception :"
                        + " [ " + e + " ], Category content : [" + object.toString() + "]" );
            }
        }
        logger.info( "Successfully inserted category : " + object.toString() );
        insertedCategory = new Category( categoryID, object.getName() );
        return insertedCategory;
    }

    public int updateCategory( Connection connection, Logger logger, int categoryId, Category newObject ) {
        PreparedStatement preparedStatement = null;
        String updateQuery = "UPDATE Category_TBL SET NAME = ? WHERE ID = ?";

        try {
            preparedStatement = connection.prepareStatement( updateQuery );

            preparedStatement.setString( 1, newObject.getName() );
            preparedStatement.setInt( 2, categoryId );

            preparedStatement.executeUpdate();
        } catch ( SQLException e ) {
            logger.severe( "Method updateCategory - SQL Exception :"
                    + " [ " + e + " ], Category content : [" + newObject.toString() + "]" );
            return -1;
        } finally {
            try {
                if ( preparedStatement != null ) {
                    preparedStatement.close();
                }
            } catch ( SQLException e ) {
                logger.warning( "Method updateCategory - Closing Statement exception :"
                        + " [ " + e + " ], Category content : [" + newObject.toString() + "]" );
            }
        }
        logger.info( "Successfully updated category : " + newObject.toString() );
        return 1;
    }

    public int deleteCategory( Connection connection, Logger logger, int categoryId ) {
        return 1;
    }

    public int wipeCategoryTable( Connection connection, Logger logger ) {
        Statement statement = null;
        String sql;
        String[] databasesToWipe = { "MONTH_TRANSACTION_TBL", "CATEGORY_TBL" };

        for ( int i = 0; i < databasesToWipe.length; i++ ) {
            try {
                statement = connection.createStatement();
                sql = "DELETE FROM " + databasesToWipe[ i ];
                statement.executeUpdate( sql );
            } catch ( SQLException e ) {
                logger.severe( "Method wipeCategoryTable - SQL Exception while trying to wipe out table " + databasesToWipe[ i ] + " : " + e );
                return -1;
            } finally {
                try {
                    if ( statement != null ) {
                        statement.close();
                    }
                } catch ( SQLException e ) {
                    logger.warning( "[SQL Exception while trying to close the prepared statement song for wiping out table " + databasesToWipe[ i ] + " : " + e );
                }
            }
            logger.info( "Successfully wiped out database " + databasesToWipe[ i ] );

        }
        return 1;
    }
}
