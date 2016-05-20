package controller;

import entity.Category;
import entity.Month;
import entity.MonthTransaction;
import facade.Facade;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {

    private Facade facade;
    private Map<Integer, String> monthMap;
    private Map<Integer, String> categoryMap;

    public Controller() {
        this.facade = Facade.getInstance( null, null, null );
        facade.initializeConnection();
    }

    public List<Category> getCategories() {
        categoryMap = new HashMap();
        List<Category> categories = facade.getCategories();

        for ( int i = 0; i < categories.size(); i++ ) {
            categoryMap.put( categories.get( i ).getId(), categories.get( i ).getName() );
        }

        return categories;
    }

    public List<Month> getMonths() {
        monthMap = new HashMap();
        List<Month> months = facade.getMonths();

        for ( int i = 0; i < months.size(); i++ ) {
            monthMap.put( months.get( i ).getId(), months.get( i ).getName() );
        }

        return months;
    }

    public List<MonthTransaction> getMonthTransactions() {
        return facade.getMonthTransactions();
    }

    public List<MonthTransaction> getMonthTransactions( String monthName, String categoryName, String type ) {
        int monthId = 0, categoryId = 0;
        if ( !monthName.equals( "-ALL-" ) ) {
            for ( Map.Entry<Integer, String> entrySet : monthMap.entrySet() ) {
                if ( entrySet.getValue().equals( monthName ) ) {
                    monthId = entrySet.getKey();
                }
            }
        }
        if ( !categoryName.equals( "-ALL-" ) ) {
            for ( Map.Entry<Integer, String> entrySet : categoryMap.entrySet() ) {
                if ( entrySet.getValue().equals( categoryName ) ) {
                    categoryId = entrySet.getKey();
                }
            }
        }
        if ( type.equals( "-ALL-" ) ) {
            type = "";
        }
        return facade.getMonthTransactions( monthId, categoryId, type );
    }

    public boolean addMonthTransactions( String name, String month, String category,
            String type, double amount ) {

        int monthId = 0, categoryId = 0;
        for ( Map.Entry<Integer, String> entrySet : monthMap.entrySet() ) {
            if ( entrySet.getValue().equals( month ) ) {
                monthId = entrySet.getKey();
            }
        }

        for ( Map.Entry<Integer, String> entrySet : categoryMap.entrySet() ) {
            if ( entrySet.getValue().equals( category ) ) {
                categoryId = entrySet.getKey();
            }
        }

        MonthTransaction monthTransaction = new MonthTransaction( 0, name, type, monthId, categoryId, amount );
        if ( facade.insertMonthTransaction( monthTransaction ) != null ) {
            return true;
        }
        return false;
    }

    public boolean deleteMonthTransaction( int monthTransactionID ) {
        return facade.deleteMonthTransaction( monthTransactionID );
    }

    public Category getCategoryByID( int categoryID ) {
        return facade.getCategoryByID( categoryID );
    }

    public Month getMonthByID( int monthID ) {
        return facade.getMonthByID( monthID );
    }
    
    
    public boolean updateMonthTransactions( String stringID, String name, String month, String category,
            String type, String stringAmount ) {

        int id= Integer.parseInt( stringID );
        double amount= Double.parseDouble( stringAmount);
        int monthId = 0, categoryId = 0;
        for ( Map.Entry<Integer, String> entrySet : monthMap.entrySet() ) {
            if ( entrySet.getValue().equals( month ) ) {
                monthId = entrySet.getKey();
            }
        }

        for ( Map.Entry<Integer, String> entrySet : categoryMap.entrySet() ) {
            if ( entrySet.getValue().equals( category ) ) {
                categoryId = entrySet.getKey();
            }
        }

        MonthTransaction monthTransaction = new MonthTransaction( id, name, type, monthId, categoryId, amount );
        if ( facade.updateMonthTransaction(id, monthTransaction ) != null ) {
            return true;
        }
        return false;
    }
    
      public boolean deleteMonth( int monthID ) {
        return facade.deleteMonth(monthID );
    }

}
