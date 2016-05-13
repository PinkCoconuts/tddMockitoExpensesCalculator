package mappers;

import entity.Month;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MonthMapper {

    public MonthMapper() {
    }

    public List<Month> getMonths( Connection connection ) {
        ArrayList<Month> months = new ArrayList();

        Month month = new Month();
        PreparedStatement preparedStatement = null;
        String selectSQL = "SELECT ID, NAME FROM MONTH_TBL ";
        try {
            preparedStatement = connection.prepareStatement( selectSQL );
            ResultSet rs = preparedStatement.executeQuery();
            while ( rs.next() ) {
                month.setId( rs.getInt( "ID" ) );
                month.setName( rs.getString( "NAME" ) );
                months.add( month );
            }
            rs.close();
            preparedStatement.close();
        } catch ( SQLException ex ) {
            System.out.println( "Error in the getMonthById method: " + ex );
            Logger.getLogger( MonthMapper.class.getName() ).log( Level.SEVERE, null, ex );
        }
        return months;
    }

    public Month getMonthByID( Connection connection, int monthId ) {
        Month month = new Month();
        PreparedStatement preparedStatement = null;
        String selectSQL = "SELECT NAME FROM MONTH_TBL "
                + "WHERE ID = ?";
        try {
            preparedStatement = connection.prepareStatement( selectSQL );
            preparedStatement.setInt( 1, monthId );
            ResultSet rs = preparedStatement.executeQuery();
            if ( rs.next() ) {
                month.setId( monthId );
                month.setName( rs.getString( "NAME" ) );
            }
            rs.close();
            preparedStatement.close();
        } catch ( SQLException ex ) {
            System.out.println( "Error in the getMonthById method: " + ex );
            Logger.getLogger( MonthMapper.class.getName() ).log( Level.SEVERE, null, ex );
        }
        return month;
    }

    public Month insertMonth( Connection connection, Month object ) {
        int nextId = 0;
        String sqlIdentifier = "select month_id_seq.nextval from dual";
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
        insertTableSQL = "INSERT INTO Month_tbl (id, name) "
                + "VALUES (?, ?)";
        try {
            preparedStatement = connection.prepareStatement( insertTableSQL );

            preparedStatement.setInt( 1, nextId );
            preparedStatement.setString( 2, object.getName() );

            preparedStatement.executeUpdate();
            preparedStatement.close();
            object.setId( nextId );
            return object;
        } catch ( SQLException e ) {
            System.out.println( "Exception in insert Month mapper: " + e );
            return null;
        }
    }

    public Month updateMonth( Connection connection, int monthId, Month newObject ) {
        Month month = new Month();
        PreparedStatement preparedStatement = null;
        String updateQuery = "UPDATE MONTH_TBL SET NAME = ? WHERE ID = ?";

        try {
            preparedStatement = connection.prepareStatement( updateQuery );

            preparedStatement.setString( 1, newObject.getName() );
            preparedStatement.setInt( 2, monthId );

            preparedStatement.executeUpdate();
            month.setId( monthId );
            month.setName( newObject.getName() );
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
        return month;
    }

    public int deleteMonth( Connection connection, int monthId ) {
        PreparedStatement preparedStatement;
        String deleleStatement = "delete from Month_tbl where id = ?";
        try {
            preparedStatement = connection.prepareStatement( deleleStatement );
            preparedStatement.setInt( 1, monthId );
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

    public int deleteAllMonths( Connection connection ) {
        PreparedStatement preparedStatement;
        String deleleStatement = "delete from Month_tbl";
        try {
            preparedStatement = connection.prepareStatement( deleleStatement );
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch ( Exception e ) {
            System.out.println( "Exception in delete all method in the Month mapper: " + e );
            return -1;
        }
        return 1;
    }
}
