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

    public <T> T getMonths( Connection connection, Logger logger ) {
        ArrayList<Month> months = new ArrayList();

        Month month = null;
        PreparedStatement preparedStatement = null;

        String selectSQL = "SELECT ID, NAME FROM MONTH_TBL ";

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

    public Month getMonthByID( Connection connection, Logger logger, int monthId ) {
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
        String deleteStatement = "delete from Month_tbl";
        try {
            preparedStatement = connection.prepareStatement( deleteStatement );
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch ( Exception e ) {
            System.out.println( "Exception in delete all method in the Month mapper: " + e );
            return -1;
        }
        return 1;
    }
}
