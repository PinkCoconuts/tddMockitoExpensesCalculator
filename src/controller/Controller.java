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

    public List<MonthTransaction> getTransactionsByMonth( String monthName ) {
        for ( Map.Entry<Integer, String> entrySet : monthMap.entrySet() ) {
            if ( entrySet.getValue().equals( monthName ) ) {
                return facade.getSpecificTransactionsByMonthID( entrySet.getKey() );
            }
        }
        return new ArrayList<MonthTransaction>();
    }

    public List<MonthTransaction> getTransactionsByCategory( String categoryName ) {
        for ( Map.Entry<Integer, String> entrySet : categoryMap.entrySet() ) {
            if ( entrySet.getValue().equals( categoryName ) ) {
                return facade.getSpecificTransactionsByCategoryID( entrySet.getKey() );
            }
        }
        return new ArrayList<MonthTransaction>();
    }

    public List<MonthTransaction> getTransactionsByType( String type ) {
        return facade.getSpecificTransactionsByType( type );
    }

}
