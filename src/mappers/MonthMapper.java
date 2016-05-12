package mappers;

import entity.Month;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MonthMapper {

    public List<Month> getMonths( Connection connection ) {
        return new ArrayList<Month>();
    }

    public Month getMonthByID( Connection connection, int monthId ) {
        return new Month();
    }

    public int insertMonth( Connection connection, Month object ) {
        String insertTableSQL;
        PreparedStatement preparedStatement;
        try {
            insertTableSQL = "INSERT INTO Month (name) "
                    + "VALUES (?)";

            preparedStatement = connection.prepareStatement( insertTableSQL );

            preparedStatement.setString( 1, object.getName() );

            preparedStatement.executeUpdate();
            return 1;
        } catch ( SQLException e ) {
            return -1;
        }
    }

    public int updateMonth( Connection connection, int monthId, Month newObject ) {
        return 1;
    }

    public int deleteMonth( Connection connection, int monthId ) {
        return 1;
    }
}
