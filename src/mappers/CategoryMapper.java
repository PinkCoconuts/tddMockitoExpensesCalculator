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

    /*
     * Method for getting all categories.
     *
     * Returns : 
     * Filled List<Category> if found
     * Empty List<Category> if not found
     * Boolean false if errors
     */
    public <T> T getCategories( Connection connection, Logger logger ) {
        List<Category> categories = new ArrayList();

        String selectSQL = "SELECT ID, NAME FROM CATEGORY_TBL ";

        Category category = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( selectSQL );
            ResultSet resultSet = preparedStatement.executeQuery();

            while ( resultSet.next() ) {
                category = new Category(
                        resultSet.getInt( "ID" ), resultSet.getString( "NAME" )
                );
                categories.add( category );
            }

            resultSet.close();
            preparedStatement.close();
        } catch ( SQLException e ) {

            if ( logger != null ) {
                logger.log( Level.SEVERE, "Error in the getCategories method:"
                            + " {0}", e );
            } else {
                System.out.println( "Error in the getCategories method: "
                        + "Logger not initialized"
                        + "\nError in the getCategories method: " + e );
            }
            return ( T ) ( Boolean ) false;
        }

        return ( T ) categories;
    }

    /*
     * Method for getting a category by its id
     *
     * Returns :
     * Filled Category object if found
     * Empty Category object if not found
     * Boolean false if errors
     */
    public <T> T getCategoryByID( Connection connection, Logger logger, int categoryId ) {
        String SQLString = "SELECT * FROM Category_TBL WHERE ID = ?";

        Category category = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( SQLString );
            preparedStatement.setInt( 1, categoryId );
            ResultSet rs = preparedStatement.executeQuery();

            if ( rs.next() ) {
                category = new Category( rs.getInt( 1 ), rs.getString( 2 ) );
            } else {
                //Defeat testing null error in an easy way
                category = new Category(0, "");
            }

            rs.close();
            preparedStatement.close();
        } catch ( SQLException e ) {
            if ( logger != null ) {
                logger.log( Level.SEVERE, "Error in the getCategoryByID method:"
                            + " {0}", e );
            } else {
                System.out.println( "Error in the getCategoryByID method: "
                        + "Logger not initialized"
                        + "\nError in the getCategoryByID method: " + e );
            }
            return ( T ) ( Boolean ) false;
        }
        return ( T ) category;
    }

    /*
     * Method to insert a new category
     *
     * boolean category object if inserted
     * boolean false if errors
     */
    public <T> T insertCategory( Connection connection, Logger logger, Category object ) {
        
        int nextId = 0;

        String SQLString = "select CATEGORY_ID_SEQ.nextval from dual";

        PreparedStatement preparedStatement = null;
        try {

            preparedStatement = connection.prepareStatement( SQLString );
            ResultSet rs = preparedStatement.executeQuery();

            boolean foundError = false;

            if ( rs.next() ) {
                nextId = rs.getInt( 1 );
            } else {
                foundError = true;
                if ( logger != null ) {
                    logger.log( Level.SEVERE, "Error in the insertCategory method:"
                                + " {0}", "Cannot obtain next id for category object" );
                } else {
                    System.out.println( "Error in the insertCategory method: "
                            + "Logger not initialized"
                            + "\nError in the insertCategory method: "
                            + "\"Cannot obtain next id for category object" );
                }
            }

            rs.close();
            preparedStatement.close();

            if ( foundError ) {
                return ( T ) ( Boolean ) false;
            }
        } catch ( SQLException e ) {
            if ( logger != null ) {
                logger.log( Level.SEVERE, "Error in the insertCategory method:"
                            + " {0}", e );
            } else {
                System.out.println( "Error in the insertCategory method: "
                        + "Logger not initialized"
                        + "\nError in the insertCategory method: " + e );
            }
            return ( T ) ( Boolean ) false;
        }

        SQLString = "INSERT INTO CATEGORY_TBL (ID, NAME) VALUES (?, ?)";

        try {
            preparedStatement = connection.prepareStatement( SQLString );

            preparedStatement.setInt( 1, nextId );
            preparedStatement.setString( 2, object.getName() );

            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch ( SQLException e ) {
            if ( logger != null ) {
                logger.log( Level.SEVERE, "Error in the insertCategory (Part 2) method:"
                            + " {0}", e );
            } else {
                System.out.println( "Error in the insertCategory (Part 2) method: "
                        + "Logger not initialized"
                        + "\nError in the insertCategory (Part 2) method: " + e );
            }
            return ( T ) ( Boolean ) false;
        }
        
        object.setId( nextId );
        return ( T ) object;
    }

    /*
     * Method to update a category transaction
     *
     * Returns :
     * boolean Category object if updated
     * boolean false if errors
     */
    public <T> T updateCategory( Connection connection, Logger logger, int categoryId,
            Category object ) {
        String updateQuery = "UPDATE Category_TBL SET NAME = ? WHERE ID = ?";

        PreparedStatement preparedStatement = null;
        try {

            preparedStatement = connection.prepareStatement( updateQuery );

            preparedStatement.setString( 1, object.getName() );
            preparedStatement.setInt( 2, categoryId );

            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch ( SQLException e ) {
            if ( logger != null ) {
                logger.log( Level.SEVERE, "Error in the updateCategory method:"
                            + " {0}", e );
            } else {
                System.out.println( "Error in the updateCategory method: "
                        + "Logger not initialized"
                        + "\nError in the updateCategory method: " + e );
            }
            return ( T ) ( Boolean ) false;
        }

        object.setId( categoryId );
        return ( T ) object;
    }

    /*
     * Method to delete a category
     *
     * Returns :
     * boolean true if deleted
     * boolean false if errors
     */
    public boolean deleteCategory( Connection connection, Logger logger, int categoryId ) {

        String deleleStatement = "delete from CATEGORY_TBL where id = ?";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( deleleStatement );

            preparedStatement.setInt( 1, categoryId );

            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch ( SQLException e ) {
            if ( logger != null ) {
                logger.log( Level.SEVERE, "Error in the deleteCategory method:"
                            + " {0}", e );
            } else {
                System.out.println( "Error in the deleteCategory method: "
                        + "Logger not initialized"
                        + "\nError in the deleteCategory method: " + e );
            }
            return false;
        }
        return true;
    }

    public boolean wipeCategoryTable( Connection connection, Logger logger ) {

        String[] databasesToWipe = { "MONTH_TRANSACTION_TBL", "CATEGORY_TBL" };

        Statement statement = null;

        String sqlQuery = "";

        for ( int i = 0; i < databasesToWipe.length; i++ ) {
            try {
                statement = connection.createStatement();
                sqlQuery = "DELETE FROM " + databasesToWipe[ i ];
                statement.executeUpdate( sqlQuery );
                statement.close();
            } catch ( SQLException e ) {
                if ( logger != null ) {
                    logger.log( Level.SEVERE, "Error in the wipeCategoryTable method:"
                                + " {0}", e );
                } else {
                    System.out.println( "Error in the wipeCategoryTable method: "
                            + "Logger not initialized"
                            + "\nError in the wipeCategoryTable method: " + e );
                }
                return false;
            }
        }
        return true;
    }
}
