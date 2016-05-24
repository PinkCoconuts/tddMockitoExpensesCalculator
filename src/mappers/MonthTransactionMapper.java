package mappers;

import entity.MonthTransaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MonthTransactionMapper {

    /*
     * Method for getting all transactions.
     *
     * Returns : 
     * Filled List<TransactionMapper> if found
     * Empty List<TransactionMapper> if not found
     * Boolean false if errors
     */
    public <T> T getAllTransactions( Connection connection, Logger logger ) {
        ArrayList<MonthTransaction> monthTransactions = new ArrayList();

        String selectSQL = "SELECT * FROM MONTH_TRANSACTION_TBL ";

        MonthTransaction monthTransaction = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( selectSQL );
            ResultSet rs = preparedStatement.executeQuery();

            while ( rs.next() ) {
                monthTransaction
                        = new MonthTransaction(
                                rs.getInt( "ID" ), rs.getString( "NAME" ),
                                rs.getString( "TYPE" ), rs.getInt( "MONTH_ID" ),
                                rs.getInt( "CATEGORY_ID" ), rs.getDouble( "AMOUNT" )
                        );
                monthTransactions.add( monthTransaction );
            }

            rs.close();
            preparedStatement.close();
        } catch ( SQLException e ) {

            if ( logger != null ) {
                logger.log( Level.SEVERE, "Error in the getAllTransactions method:"
                            + " {0}", e );
            } else {
                System.out.println( "Error in the getAllTransactions method: "
                        + "Logger not initialized"
                        + "\nError in the getAllTransactions method: " + e );
            }
            return ( T ) ( Boolean ) false;
        }
        return ( T ) monthTransactions;
    }

    /*
     * Method for getting all transactions sorted by monthid and/or category id 
     * and/or type. It can also server as a method for getting all transactions.
     *
     * Returns : 
     * Filled List<TransactionMapper> if found
     * Empty List<TransactionMapper> if not found
     * Boolean false if errors
     */
    public <T> T getAllTransactions( Connection connection, Logger logger,
            int monthId, int categoryId, String type ) {
        List<MonthTransaction> transactionsList = new ArrayList();
        List<String> elementsToQuery = new ArrayList();

        StringBuilder sBuild = new StringBuilder();
        sBuild.append( "SELECT * FROM MONTH_TRANSACTION_TBL " );

        boolean isFound = false;

        if ( monthId > 0 ) {
            sBuild.append( "WHERE MONTH_ID = ? " );
            isFound = true;
            elementsToQuery.add( "month" );
        }

        if ( categoryId > 0 ) {
            if ( isFound ) {
                sBuild.append( "AND CATEGORY_ID = ? " );
            } else {
                sBuild.append( "WHERE CATEGORY_ID = ? " );
            }
            isFound = true;
            elementsToQuery.add( "category" );
        }

        if ( !type.isEmpty() && !"".equals( type ) ) {
            if ( isFound ) {
                sBuild.append( "AND TYPE = ? " );
            } else {
                sBuild.append( "WHERE TYPE = ? " );
            }
            elementsToQuery.add( "type" );
        }
        String sBuilderQueryStr = sBuild.toString();

        PreparedStatement preparedStatement = null;
        MonthTransaction monthTransaction = null;

        try {

            preparedStatement = connection.prepareStatement( sBuilderQueryStr );

            for ( int i = 0; i < elementsToQuery.size(); i++ ) {
                if ( "type".equals( elementsToQuery.get( i ) ) ) {
                    preparedStatement.setString( i + 1, type );
                } else if ( "category".equals( elementsToQuery.get( i ) ) ) {
                    preparedStatement.setInt( i + 1, categoryId );
                } else if ( "month".equals( elementsToQuery.get( i ) ) ) {
                    preparedStatement.setInt( i + 1, monthId );
                }
            }

            ResultSet rs = preparedStatement.executeQuery();

            while ( rs.next() ) {
                monthTransaction
                        = new MonthTransaction(
                                rs.getInt( "ID" ), rs.getString( "NAME" ),
                                rs.getString( "TYPE" ), rs.getInt( "MONTH_ID" ),
                                rs.getInt( "CATEGORY_ID" ), rs.getDouble( "AMOUNT" )
                        );
                transactionsList.add( monthTransaction );
            }

            rs.close();
            preparedStatement.close();
        } catch ( SQLException e ) {

            if ( logger != null ) {
                logger.log( Level.SEVERE, "Error in the getAllTransactions(2) method:"
                            + " {0}", e );
            } else {
                System.out.println( "Error in the getAllTransactions(2) method: "
                        + "Logger not initialized"
                        + "\nError in the getAllTransactions(2) method: " + e );
            }
            return ( T ) ( Boolean ) false;
        }
        return ( T ) transactionsList;
    }

    /*
     * Method for getting a transaction by its id
     *
     * Returns :
     * Filled TransactionMapper object if found
     * Empty TransactionMapper object if not found
     * Boolean false if errors
     */
    public <T> T getTransactionsByID( Connection connection, Logger logger,
            int id ) {
        String selectSQL = "SELECT * FROM MONTH_TRANSACTION_TBL WHERE ID = ?";

        MonthTransaction monthTransaction = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( selectSQL );
            preparedStatement.setInt( 1, id );
            ResultSet rs = preparedStatement.executeQuery();

            if ( rs.next() ) {
                monthTransaction
                        = new MonthTransaction(
                                rs.getInt( "ID" ), rs.getString( "NAME" ),
                                rs.getString( "TYPE" ), rs.getInt( "MONTH_ID" ),
                                rs.getInt( "CATEGORY_ID" ), rs.getDouble( "AMOUNT" )
                        );
            } else {
                monthTransaction = new MonthTransaction();
            }

            rs.close();
            preparedStatement.close();

        } catch ( SQLException e ) {
            if ( logger != null ) {
                logger.log( Level.SEVERE, "Error in the getTransactionsByID method:"
                            + " {0}", e );
            } else {
                System.out.println( "Error in the getTransactionsByID method: "
                        + "Logger not initialized"
                        + "\nError in the getAllTransactions method: " + e );
            }
            return ( T ) ( Boolean ) false;
        }
        return ( T ) monthTransaction;
    }

    /*
     * Method to insert a new month transaction
     *
     * boolean Month transaction object if inserted
     * boolean false if errors
     */
    public <T> T insertMonthTransaction( Connection connection, Logger logger,
            MonthTransaction object ) {

        int nextId = 0;

        String sqlQuery = "select MONTH_TRANSACTION_ID_SEQ.nextval from dual";

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
                    logger.log( Level.SEVERE, "Error in the insertMonthTransaction method:"
                                + " {0}", "Cannot obtain next id for month transaction object" );
                } else {
                    System.out.println( "Error in the insertMonthTransaction method: "
                            + "Logger not initialized"
                            + "\nError in the insertMonthTransaction method: "
                            + "\"Cannot obtain next id for month transaction object" );
                }
            }

            rs.close();
            preparedStatement.close();

            if ( foundError ) {
                return ( T ) ( Boolean ) false;
            }
        } catch ( SQLException e ) {
            if ( logger != null ) {
                logger.log( Level.SEVERE, "Error in the insertMonthTransaction method:"
                            + " {0}", e );
            } else {
                System.out.println( "Error in the insertMonthTransaction method: "
                        + "Logger not initialized"
                        + "\nError in the insertMonthTransaction method: " + e );
            }
            return ( T ) ( Boolean ) false;
        }

        sqlQuery = "INSERT INTO MONTH_TRANSACTION_TBL (id, month_id, name, amount,"
                + " type, category_id) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement( sqlQuery );

            preparedStatement.setInt( 1, nextId );
            preparedStatement.setInt( 2, object.getMonthId() );
            preparedStatement.setString( 3, object.getName() );
            preparedStatement.setDouble( 4, object.getAmount() );
            preparedStatement.setString( 5, object.getType() );
            preparedStatement.setInt( 6, object.getCategoryId() );

            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch ( SQLException e ) {
            if ( logger != null ) {
                logger.log( Level.SEVERE, "Error in the insertMonthTransaction (Part 2) method:"
                            + " {0}", e );
            } else {
                System.out.println( "Error in the insertMonthTransaction (Part 2) method: "
                        + "Logger not initialized"
                        + "\nError in the insertMonthTransaction (Part 2) method: " + e );
            }
            return ( T ) ( Boolean ) false;
        }

        MonthTransaction returnObject
                = new MonthTransaction( nextId, object.getName(), object.getType(),
                                        object.getMonthId(), object.getCategoryId(),
                                        object.getAmount() );
        return ( T ) returnObject;
    }

    /*
     * Method to update a month transaction
     *
     * Returns :
     * boolean Month transaction object if updated
     * boolean false if errors
     */
    public <T> T updateMonthTransaction( Connection connection, Logger logger,
            int monthTransactionID, MonthTransaction object ) {

        String updateQuery = "UPDATE MONTH_TRANSACTION_TBL SET MONTH_ID = ?, "
                + "NAME = ?, AMOUNT = ?, TYPE = ?, CATEGORY_ID = ? WHERE ID = ?";

        PreparedStatement preparedStatement = null;
        try {

            preparedStatement = connection.prepareStatement( updateQuery );

            preparedStatement.setInt( 1, object.getMonthId() );
            preparedStatement.setString( 2, object.getName() );
            preparedStatement.setDouble( 3, object.getAmount() );
            preparedStatement.setString( 4, object.getType() );
            preparedStatement.setInt( 5, object.getCategoryId() );
            preparedStatement.setInt( 6, monthTransactionID );

            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch ( SQLException e ) {

            if ( logger != null ) {
                logger.log( Level.SEVERE, "Error in the updateMonthTransaction method:"
                            + " {0}", e );
            } else {
                System.out.println( "Error in the updateMonthTransaction method: "
                        + "Logger not initialized"
                        + "\nError in the updateMonthTransaction method: " + e );
            }
            return ( T ) ( Boolean ) false;
        }

        MonthTransaction returnObject
                = new MonthTransaction( monthTransactionID, object.getName(),
                                        object.getType(), object.getMonthId(),
                                        object.getCategoryId(), object.getAmount() );
        return ( T ) returnObject;
    }

    /*
     * Method to delete a month transaction
     *
     * Returns :
     * boolean true if deleted
     * boolean false if errors
     */
    public boolean deleteMonthTransaction( Connection connection, Logger logger,
            int monthTransactionId ) {

        String deleteStatement = "delete from Month_transaction_tbl where id = ?";

        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement( deleteStatement );

            preparedStatement.setInt( 1, monthTransactionId );

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch ( Exception e ) {
            if ( logger != null ) {
                logger.log( Level.SEVERE, "Error in the deleteMonthTransaction method:"
                            + " {0}", e );
            } else {
                System.out.println( "Error in the deleteMonthTransaction method: "
                        + "Logger not initialized"
                        + "\nError in the deleteMonthTransaction method: " + e );
            }
            return false;
        }
        return true;
    }

    /*
     * Method to delete all month transactions
     *
     * Returns :
     * boolean true if all deleted
     * boolean false if errors
     */
    public boolean deleteAllMonthTransactions( Connection connection, Logger logger ) {
        String deleteStatement = "delete from MONTH_TRANSACTION_TBL";

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
