package mappers;

import entity.Month;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MonthMapper {

    /*
     * Method for getting all month.
     *
     * Returns : 
     * Filled List<Month> if found
     * Empty List<Month> if not found
     * Boolean false if errors
     */
    public <T> T getMonths( Connection connection, Logger logger ) {
        ArrayList<Month> months = new ArrayList();

        String selectSQL = "SELECT ID, NAME FROM MONTH_TBL";

        Month month = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( selectSQL );
            ResultSet rs = preparedStatement.executeQuery();

            while ( rs.next() ) {

                month = new Month( rs.getInt( "ID" ), rs.getString( "NAME" ) );
                months.add( month );
            }
            rs.close();
            preparedStatement.close();
        } catch ( SQLException e ) {

            if ( logger != null ) {
                logger.log( Level.SEVERE, "Error in the getMonths method:"
                            + " {0}", e );
            } else {
                System.out.println( "Error in the getMonths method: "
                        + "Logger not initialized"
                        + "\nError in the getMonths method: " + e );
            }
            return ( T ) ( Boolean ) true;
        }
        return ( T ) months;
    }

    /*
     * Method for getting a month by its id
     *
     * Returns :
     * Filled Month object if found
     * Empty Month object if not found
     * Boolean false if errors
     */
    public <T> T getMonthByID( Connection connection, Logger logger, int monthId ) {
        String selectSQL = "SELECT NAME FROM MONTH_TBL WHERE ID = ?";

        Month month = null;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement( selectSQL );
            preparedStatement.setInt( 1, monthId );
            ResultSet rs = preparedStatement.executeQuery();

            if ( rs.next() ) {
                month = new Month( monthId, rs.getString( "NAME" ) );
            } else {
                month = new Month();
            }

            rs.close();
            preparedStatement.close();
        } catch ( SQLException e ) {

            if ( logger != null ) {
                logger.log( Level.SEVERE, "Error in the getMonthByID method:"
                            + " {0}", e );
            } else {
                System.out.println( "Error in the getMonthByID method: "
                        + "Logger not initialized"
                        + "\nError in the getMonthByID method: " + e );
            }
            return ( T ) ( Boolean ) true;
        }
        return ( T ) month;
    }

    /*
     * Method to insert a new month
     *
     * boolean Month object if inserted
     * boolean false if errors
     */
    public <T> T insertMonth( Connection connection, Logger logger, Month object ) {

        int nextId = 0;

        String sqlQuery = "select month_id_seq.nextval from dual";

        PreparedStatement preparedStatement;
        try {

            preparedStatement = connection.prepareStatement( sqlQuery );
            ResultSet rs = preparedStatement.executeQuery();

            boolean foundError = false;

            if ( rs.next() ) {
                nextId = rs.getInt( 1 );
            } else {
                foundError = true;
                if ( logger != null ) {
                    logger.log( Level.SEVERE, "Error in the insertMonth method:"
                                + " {0}", "Cannot obtain next id for month object" );
                } else {
                    System.out.println( "Error in the insertMonth method: "
                            + "Logger not initialized"
                            + "\nError in the insertMonth method: "
                            + "\"Cannot obtain next id for month object" );
                }
            }

            rs.close();
            preparedStatement.close();

            if ( foundError ) {
                return ( T ) ( Boolean ) false;
            }
        } catch ( SQLException e ) {

            if ( logger != null ) {
                logger.log( Level.SEVERE, "Error in the insertMonth method:"
                            + " {0}", e );
            } else {
                System.out.println( "Error in the insertMonth method: "
                        + "Logger not initialized"
                        + "\nError in the insertMonth method: " + e );
            }
            return ( T ) ( Boolean ) false;
        }

        sqlQuery = "INSERT INTO Month_tbl (id, name) VALUES (?, ?)";

        try {
            preparedStatement = connection.prepareStatement( sqlQuery );

            preparedStatement.setInt( 1, nextId );
            preparedStatement.setString( 2, object.getName() );

            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch ( SQLException e ) {
            if ( logger != null ) {
                logger.log( Level.SEVERE, "Error in the insertMonth (Part 2) method:"
                            + " {0}", e );
            } else {
                System.out.println( "Error in the insertMonth (Part 2) method: "
                        + "Logger not initialized"
                        + "\nError in the insertMonth (Part 2) method: " + e );
            }
            return ( T ) ( Boolean ) false;
        }

        Month returnMonth = new Month( nextId, object.getName() );
        return ( T ) returnMonth;
    }

    /*
     * Method to update a month
     *
     * Returns :
     * boolean Month object if updated
     * boolean false if errors
     */
    public <T> T updateMonth( Connection connection, Logger logger, int monthId,
            Month object ) {

        String updateQuery = "UPDATE MONTH_TBL SET NAME = ? WHERE ID = ?";

        PreparedStatement preparedStatement = null;
        try {

            preparedStatement = connection.prepareStatement( updateQuery );

            preparedStatement.setString( 1, object.getName() );
            preparedStatement.setInt( 2, monthId );

            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch ( SQLException e ) {

            if ( logger != null ) {
                logger.log( Level.SEVERE, "Error in the updateMonth method:"
                            + " {0}", e );
            } else {
                System.out.println( "Error in the updateMonth method: "
                        + "Logger not initialized"
                        + "\nError in the updateMonth method: " + e );
            }
            return ( T ) ( Boolean ) false;
        }

        Month returnMonth = new Month( monthId, object.getName() );
        return ( T ) returnMonth;
    }

    /*
     * Method to delete a month
     *
     * Returns :
     * boolean true if deleted
     * boolean false if errors
     */
    public boolean deleteMonth( Connection connection, Logger logger, int monthId ) {

        String deleleStatement = "delete from Month_tbl where id = ?";

        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement( deleleStatement );

            preparedStatement.setInt( 1, monthId );

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch ( Exception e ) {
            if ( logger != null ) {
                logger.log( Level.SEVERE, "Error in the deleteMonth method:"
                            + " {0}", e );
            } else {
                System.out.println( "Error in the deleteMonth method: "
                        + "Logger not initialized"
                        + "\nError in the deleteMonth method: " + e );
            }
            return false;
        }
        return true;
    }

    /*
     * Method to delete all months
     *
     * Returns :
     * boolean true if all deleted
     * boolean false if errors
     */
    public boolean deleteAllMonths( Connection connection, Logger logger ) {
        String deleteStatement = "delete from Month_tbl";

        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement( deleteStatement );

            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch ( Exception e ) {
            if ( logger != null ) {
                logger.log( Level.SEVERE, "Error in the deleteAllMonthTransactions method:"
                            + " {0}", e );
            } else {
                System.out.println( "Error in the deleteAllMonthTransactions method: "
                        + "Logger not initialized"
                        + "\nError in the deleteAllMonthTransactions method: " + e );
            }
            return false;
        }
        return true;
    }
}
