package controller;

import entity.Category;
import entity.Month;
import entity.MonthTransaction;
import facade.Facade;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import utilities.PerformanceLogger;

public class Controller {

    //Database facade
    private Facade facade;

    //logger
    private String loggerName = "expensesCalculator";
    private String loggerPath = "/ExpensesCalculator.log";
    private static Logger logger = null;
    private static PerformanceLogger performanceLogger = null;

    //frontend Mapping
    private Map<Integer, String> monthMap;
    private Map<Integer, String> categoryMap;

    public Controller() {
        //logger initialization
        performanceLogger = new PerformanceLogger();
        logger = performanceLogger.initLogger( loggerName, loggerPath );

        //facade initialization
        this.facade = Facade.getInstance();
        facade.initializeConnection( logger );
    }

    /*
     * Control Category functionality
     */
    public List<Category> getCategories() {
        categoryMap = new HashMap();
        List<Category> categories = facade.getCategories( logger );

        for ( int i = 0; i < categories.size(); i++ ) {
            categoryMap.put( categories.get( i ).getId(), categories.get( i ).getName() );
        }

        return categories;
    }

    public Category getCategoryByID( int categoryID ) {
        return facade.getCategoryByID( logger, categoryID );
    }

    public boolean addCategory( String categoryName ) {
        Category category = new Category( 0, categoryName );
        if ( facade.insertCategory( logger, category ) != null ) {
            return true;
        }
        return false;
    }

    public boolean updateCategory( String stringID, String name ) {
        int id = Integer.parseInt( stringID );
        Category category = new Category( id, name );
        if ( facade.updateCategory( logger, id, category ) != null ) {
            return true;
        }
        return false;
    }

    public boolean deleteCategory( int categoryID ) {
        return facade.deleteCategory( logger, categoryID );
    }

    public boolean deleteAllCategories() {
        return false;
    }

    /*
     * Control month functionality
     */
    public List<Month> getMonths() {
        monthMap = new HashMap();
        List<Month> months = facade.getMonths( logger );

        for ( int i = 0; i < months.size(); i++ ) {
            monthMap.put( months.get( i ).getId(), months.get( i ).getName() );
        }

        return months;
    }

    public Month getMonthByID( int monthID ) {
        return facade.getMonthByID( logger, monthID );
    }

    public boolean addMonth( String monthName ) {
        Month month = new Month( 0, monthName );
        if ( facade.insertMonth( logger, month ) != null ) {
            return true;
        }
        return false;
    }

    public boolean updateMonth( String stringID, String name ) {
        int id = Integer.parseInt( stringID );
        Month month = new Month( id, name );
        if ( facade.updateMonth( logger, id, month ) != null ) {
            return true;
        }
        return false;
    }

    public boolean deleteMonth( int monthID ) {
        return facade.deleteMonth( logger, monthID );
    }

    public boolean deleteAllMonths() {
        return false;
    }

    /*
     * Control month transaction functionality
     */
    public List<MonthTransaction> getMonthTransactions() {
        return facade.getAllTransactions( logger );
    }

    public List<MonthTransaction> getMonthTransactions( String monthName,
            String categoryName, String type ) {

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
        return facade.getAllTransactions( logger, monthId, categoryId, type );
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
        if ( facade.insertMonthTransaction( logger, monthTransaction ) != null ) {
            return true;
        }
        return false;
    }

    public boolean updateMonthTransactions( String stringID, String name, String month, String category,
            String type, String stringAmount ) {

        int id = Integer.parseInt( stringID );
        double amount = Double.parseDouble( stringAmount );
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
        if ( facade.updateMonthTransaction( logger, id, monthTransaction ) != null ) {
            return true;
        }
        return false;
    }

    public boolean deleteMonthTransaction( int monthTransactionID ) {
        return facade.deleteMonthTransaction( logger, monthTransactionID );
    }

    public boolean deleteAllMonthTransactions() {
        return false;
    }

}
