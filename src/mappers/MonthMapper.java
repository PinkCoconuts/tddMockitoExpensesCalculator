package mappers;

import entity.Month;
import java.util.ArrayList;
import java.util.List;

public class MonthMapper {

    public List<Month> getMonths() {
        return new ArrayList<Month>();
    }

    public Month getMonthByID( int monthId ) {
        return new Month();
    }

    public int insertMonth( Month object ) {
        return 1;
    }

    public int updateMonth( int monthId ) {
        return 1;
    }

    public int deleteMonth( int monthId ) {
        return 1;
    }
}
