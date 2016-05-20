package mappers.dropped;

import entity.MonthTransaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappers.MonthTransactionMapper;

public class DroppedMapper {

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
}
