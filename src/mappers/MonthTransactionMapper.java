package mappers;

import entity.Month;
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

    //here I think we also need get AllTransactions by category
    public List<MonthTransaction> getAllTransactions( Connection connection ) {
        ArrayList<MonthTransaction> monthTransactions = new ArrayList();

        MonthTransaction monthTransaction = null;
        PreparedStatement preparedStatement = null;
        String selectSQL = "SELECT ID, MONTH_ID, NAME, AMOUNT, TYPE, CATEGORY_ID FROM MONTH_TRANSACTION_TBL ";
        try {
            preparedStatement = connection.prepareStatement( selectSQL );
            ResultSet rs = preparedStatement.executeQuery();
            while ( rs.next() ) {
                monthTransaction = new MonthTransaction( rs.getInt( "ID" ),
                                                         rs.getString( "NAME" ), rs.getString( "TYPE" ),
                                                         rs.getInt( "MONTH_ID" ), rs.getInt( "CATEGORY_ID" ),
                                                         rs.getDouble( "AMOUNT" ) );
                monthTransactions.add( monthTransaction );
            }
            rs.close();
            preparedStatement.close();
        } catch ( SQLException ex ) {
            System.out.println( "Error in the getMonthById method: " + ex );
            Logger.getLogger( MonthMapper.class.getName() ).log( Level.SEVERE, null, ex );
        }
        return monthTransactions;
    }

    /*
     * Abstract mapper to get transactions sorted by monthid, category id and type.
     */
    public List<MonthTransaction> getAllTransactions( Connection connection,
            Logger logger, int monthId, int categoryId, String type ) {
        
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
        } catch ( SQLException ex ) {
            //logger error
            System.out.println( "Error in the getAllTransactions method: " + ex );
            return null;
        }
        return transactionsList;
    }

    //here I think it should be category instead of type. 
    //And again, we need one more method for getting all transactions for one specific month, without taking into consideration the category
    public List<MonthTransaction> getSpecificTransactionsByMonthID( Connection connection,
            int monthId ) {
        ArrayList<MonthTransaction> monthTransactions = new ArrayList();
        MonthTransaction monthTransaction = new MonthTransaction();

        PreparedStatement preparedStatement = null;
        String selectSQL = "SELECT ID, NAME, AMOUNT, TYPE, CATEGORY_ID FROM MONTH_TRANSACTION_TBL "
                + "WHERE MONTH_ID = ?";
        try {
            preparedStatement = connection.prepareStatement( selectSQL );
            preparedStatement.setInt( 1, monthId );
            ResultSet rs = preparedStatement.executeQuery();
            while ( rs.next() ) {
                monthTransaction = new MonthTransaction( rs.getInt( "ID" ),
                                                         rs.getString( "NAME" ), rs.getString( "TYPE" ),
                                                         monthId, rs.getInt( "CATEGORY_ID" ),
                                                         rs.getDouble( "AMOUNT" ) );
                monthTransactions.add( monthTransaction );
            }
            rs.close();
            preparedStatement.close();
        } catch ( SQLException ex ) {
            System.out.println( "Error in the getSpecificTransactionsByMonthID method: " + ex );
            Logger.getLogger( MonthTransactionMapper.class.getName() ).log( Level.SEVERE, null, ex );
        }
        return monthTransactions;
    }

    public List<MonthTransaction> getSpecificTransactionsByCategoryID( Connection connection,
            int categoryId ) {
        ArrayList<MonthTransaction> monthTransactions = new ArrayList();
        MonthTransaction monthTransaction = null;

        PreparedStatement preparedStatement = null;
        String selectSQL = "SELECT ID, NAME, AMOUNT, TYPE, MONTH_ID FROM MONTH_TRANSACTION_TBL "
                + "WHERE CATEGORY_ID = ?";
        try {
            preparedStatement = connection.prepareStatement( selectSQL );
            preparedStatement.setInt( 1, categoryId );
            ResultSet rs = preparedStatement.executeQuery();
            while ( rs.next() ) {
                monthTransaction = new MonthTransaction( rs.getInt( "ID" ),
                                                         rs.getString( "NAME" ), rs.getString( "TYPE" ),
                                                         rs.getInt( "MONTH_ID" ), categoryId,
                                                         rs.getDouble( "AMOUNT" ) );
                monthTransactions.add( monthTransaction );
            }
            rs.close();
            preparedStatement.close();
        } catch ( SQLException ex ) {
            System.out.println( "Error in the getSpecificTransactionsByMonthID method: " + ex );
            Logger.getLogger( MonthTransactionMapper.class.getName() ).log( Level.SEVERE, null, ex );
        }
        return monthTransactions;
    }

    public List<MonthTransaction> getSpecificTransactionsByType( Connection connection,
            String type ) {
        ArrayList<MonthTransaction> monthTransactions = new ArrayList();
        MonthTransaction monthTransaction = null;

        PreparedStatement preparedStatement = null;
        String selectSQL = "SELECT ID, NAME, AMOUNT, CATEGORY_ID, MONTH_ID FROM MONTH_TRANSACTION_TBL "
                + "WHERE TYPE = ?";
        try {
            preparedStatement = connection.prepareStatement( selectSQL );
            preparedStatement.setString( 1, type );
            ResultSet rs = preparedStatement.executeQuery();
            while ( rs.next() ) {
                monthTransaction = new MonthTransaction( rs.getInt( "ID" ),
                                                         rs.getString( "NAME" ), type,
                                                         rs.getInt( "MONTH_ID" ), rs.getInt( "CATEGORY_ID" ),
                                                         rs.getDouble( "AMOUNT" ) );
                monthTransactions.add( monthTransaction );
            }
            rs.close();
            preparedStatement.close();
        } catch ( SQLException ex ) {
            System.out.println( "Error in the getSpecificTransactionsByType method: " + ex );
            Logger.getLogger( MonthTransactionMapper.class.getName() ).log( Level.SEVERE, null, ex );
        }
        return monthTransactions;
    }

    public MonthTransaction getSpecificTransactionsByID( Connection connection,
            int id ) {
        MonthTransaction monthTransaction = new MonthTransaction();

        PreparedStatement preparedStatement = null;
        String selectSQL = "SELECT ID, MONTH_ID, NAME, AMOUNT, TYPE, CATEGORY_ID FROM MONTH_TRANSACTION_TBL "
                + "WHERE ID = ?";
        try {
            preparedStatement = connection.prepareStatement( selectSQL );
            preparedStatement.setInt( 1, id );
            ResultSet rs = preparedStatement.executeQuery();
            while ( rs.next() ) {
                monthTransaction.setId( rs.getInt( "ID" ) );
                monthTransaction.setMonthId( rs.getInt( "MONTH_ID" ) );
                monthTransaction.setName( rs.getString( "NAME" ) );
                monthTransaction.setAmount( rs.getDouble( "AMOUNT" ) );
                monthTransaction.setType( rs.getString( "TYPE" ) );
                monthTransaction.setCategoryId( rs.getInt( "CATEGORY_ID" ) );
            }
            rs.close();
            preparedStatement.close();
        } catch ( SQLException ex ) {
            System.out.println( "Error in the getSpecificTransactionsByMonthID method: " + ex );
            Logger.getLogger( MonthTransactionMapper.class.getName() ).log( Level.SEVERE, null, ex );
        }
        return monthTransaction;
    }

    public MonthTransaction insertMonthTransaction( Connection connection, MonthTransaction object ) {
        int nextId = 0;
        String sqlIdentifier = "select MONTH_TRANSACTION_ID_SEQ.nextval from dual";
        PreparedStatement pst;
        try {
            pst = connection.prepareStatement( sqlIdentifier );
            ResultSet rs = pst.executeQuery();
            if ( rs.next() ) {
                nextId = rs.getInt( 1 );
            }
        } catch ( SQLException ex ) {
            Logger.getLogger( MonthMapper.class.getName() ).log( Level.SEVERE, null, ex );
        }
        String insertTableSQL;
        PreparedStatement preparedStatement;
        insertTableSQL = "INSERT INTO MONTH_TRANSACTION_TBL (id, month_id, name, amount, type, category_id) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement( insertTableSQL );

            preparedStatement.setInt( 1, nextId );
            preparedStatement.setInt( 2, object.getMonthId() );
            preparedStatement.setString( 3, object.getName() );
            preparedStatement.setDouble( 4, object.getAmount() );
            preparedStatement.setString( 5, object.getType() );
            preparedStatement.setInt( 6, object.getCategoryId() );

            preparedStatement.executeUpdate();
            preparedStatement.close();
            object.setId( nextId );
            return object;
        } catch ( SQLException e ) {
            System.out.println( "Exception in insert Month transaction mapper: " + e );
            return null;
        }
    }

    public MonthTransaction updateMonthTransaction( Connection connection, int monthTransactionId,
            MonthTransaction newObject ) {
        MonthTransaction monthTransaction = new MonthTransaction();
        PreparedStatement preparedStatement = null;
        String updateQuery = "UPDATE MONTH_TRANSACTION_TBL SET MONTH_ID = ?, NAME = ?, AMOUNT = ?, TYPE = ?, CATEGORY_ID = ? WHERE ID = ?";

        try {
            preparedStatement = connection.prepareStatement( updateQuery );

            preparedStatement.setInt( 1, newObject.getMonthId() );
            preparedStatement.setString( 2, newObject.getName() );
            preparedStatement.setDouble( 3, newObject.getAmount() );
            preparedStatement.setString( 4, newObject.getType() );
            preparedStatement.setInt( 5, newObject.getCategoryId() );
            preparedStatement.setInt( 6, monthTransactionId );

            preparedStatement.executeUpdate();
            monthTransaction.setId( monthTransactionId );
            monthTransaction.setMonthId( newObject.getMonthId() );
            monthTransaction.setName( newObject.getName() );
            monthTransaction.setAmount( newObject.getAmount() );
            monthTransaction.setType( newObject.getType() );
            monthTransaction.setCategoryId( newObject.getCategoryId() );

        } catch ( SQLException e ) {
            System.out.println( "Error in the update method of the Month mapper: " + e );
        } finally {
            try {
                if ( preparedStatement != null ) {
                    preparedStatement.close();
                }
            } catch ( SQLException e ) {
                System.out.println( "Error in the update method of the Month mapper: " + e );
            }
        }
        return monthTransaction;
    }

    public int deleteMonthTransaction( Connection connection, int monthTransactionId ) {
        PreparedStatement preparedStatement;
        String deleteStatement = "delete from Month_transaction_tbl where id = ?";
        try {
            preparedStatement = connection.prepareStatement( deleteStatement );
            preparedStatement.setInt( 1, monthTransactionId );
        } catch ( Exception e ) {
            System.out.println( "Exception in the delete month method in the Month mapper: " + e );
            return -1;
        }
        try {
            System.out.println( "I should update it right now" );
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch ( SQLException ex ) {
            System.out.println( "Exception in the delete month method: " + ex );
            return -2;
        }
        return 1;
    }

    public int deleteAllMonthTransactions( Connection connection ) {
        PreparedStatement preparedStatement;
        String deleteStatement = "delete from MONTH_TRANSACTION_TBL";
        try {
            preparedStatement = connection.prepareStatement( deleteStatement );
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch ( Exception e ) {
            System.out.println( "Exception in delete all method in the Month transaction mapper: " + e );
            return -1;
        }
        return 1;
    }
}
