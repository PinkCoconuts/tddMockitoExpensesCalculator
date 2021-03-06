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

        Object object = facade.getCategories( logger );

        if ( object instanceof List ) {

            List<Category> categories = ( List<Category> ) object;

            for ( int i = 0; i < categories.size(); i++ ) {
                categoryMap.put( categories.get( i ).getId(), categories.get( i ).getName() );
            }

            return categories;
        }
        return null;
    }

    public Category getCategoryByID( int id ) {
        Object object = facade.getCategoryByID( logger, id );

        if ( object instanceof Category ) {

            Category category = ( Category ) object;
            return category;
        }
        return null;
    }

    public boolean addCategory( String categoryName ) {
        if ( !categoryName.equals( "" ) ) {
            Category category = new Category( 0, categoryName );

            Object object = facade.insertCategory( logger, category );

            if ( object instanceof Boolean ) {
                if ( ( boolean ) object != false ) {
                    return true;
                }
            } else if ( object != null ) {
                return true;
            }
        }
        if ( logger != null ) {
            logger.severe( "Error in the controller, in the addCategory method: "
                    + "\"The category name can't be empty" );
        } else {
            System.out.println( "Error in the controller, in the addCategory method: "
                    + "Logger not initialized"
                    + "\nError in the addCategory method: "
                    + "\"The category name can't be empty" );
        }
        return false;
    }

    public boolean updateCategory( String stringID, String name ) {
        if ( !name.equals( "" ) ) {
            int id = Integer.parseInt( stringID );

            Category category = new Category( id, name );
            Object object = facade.updateCategory( logger, id, category );

            if ( object instanceof Boolean ) {
                if ( ( boolean ) object != false ) {
                    return true;
                }
            } else if ( object != null ) {
                return true;
            }
        }
        if ( logger != null ) {
            logger.severe( "Error in the controller, in the updateCategory method: "
                    + "The category name can't be empty" );
        } else {
            System.out.println( "Error in the controller, in the updateCategory method: "
                    + "Logger not initialized"
                    + "\nError in the updateCategory method: "
                    + "The category name can't be empty" );
        }
        return false;
    }

    public boolean deleteCategory( int categoryID ) {
        return facade.deleteCategory( logger, categoryID );
    }

    /*
     * deleteAllCategories in how included in the current GUI build.
     */
    public boolean deleteAllCategories() {
        return facade.deleteAllCategories( logger );
    }

    /*
     * Control month functionality
     */
    public List<Month> getMonths() {
        monthMap = new HashMap();

        Object object = facade.getMonths( logger );

        if ( object instanceof List ) {

            List<Month> months = ( List<Month> ) object;

            for ( int i = 0; i < months.size(); i++ ) {
                monthMap.put( months.get( i ).getId(), months.get( i ).getName() );
            }

            return months;
        }
        return null;
    }

    public Month getMonthByID( int monthID ) {
        Object object = facade.getMonthByID( logger, monthID );

        if ( object instanceof Month ) {

            Month month = ( Month ) object;
            return month;
        }
        return null;
    }

    public boolean addMonth( String monthName ) {
        if ( !monthName.equals( "" ) ) {
            Month month = new Month( 0, monthName );

            Object object = facade.insertMonth( logger, month );

            if ( object instanceof Boolean ) {
                if ( ( boolean ) object != false ) {
                    return true;
                }
            } else if ( object != null ) {
                return true;
            }
        }
        if ( logger != null ) {
            logger.severe( "Error in the controller, in the addMonth method: "
                    + "\"The month name can't be empty" );
        } else {
            System.out.println( "Error in the controller, in the addMonth method: "
                    + "Logger not initialized"
                    + "\nError in the addMonth method: "
                    + "\"The month name can't be empty" );
        }
        return false;
    }

    public boolean updateMonth( String stringID, String name ) {
        if ( !name.equals( "" ) ) {
            int id = Integer.parseInt( stringID );

            Month month = new Month( id, name );
            Object object = facade.updateMonth( logger, id, month );

            if ( object instanceof Boolean ) {
                if ( ( boolean ) object != false ) {
                    return true;
                }
            } else if ( object != null ) {
                return true;
            }
        }
        if ( logger != null ) {
            logger.severe( "Error in the controller, in the updateMonth method: "
                    + "The month name can't be empty" );
        } else {
            System.out.println( "Error in the controller, in the updateMonth method: "
                    + "Logger not initialized"
                    + "\nError in the updateMonth method: "
                    + "The month name can't be empty" );
        }
        return false;
    }

    public boolean deleteMonth( int monthID ) {
        return facade.deleteMonth( logger, monthID );
    }

    /*
     * deleteAllMonths in how included in the current GUI build.
     */
    public boolean deleteAllMonths() {
        return facade.deleteAllMonths( logger );
    }

    /*
     * Control month transaction functionality
     */
    public List<MonthTransaction> getMonthTransactions() {
        Object object = facade.getAllTransactions( logger );

        if ( object instanceof List ) {

            List<MonthTransaction> mts = ( List<MonthTransaction> ) object;
            return mts;
        }
        return null;
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

        Object object = facade.getAllTransactions( logger, monthId, categoryId, type );

        if ( object instanceof List ) {

            List<MonthTransaction> mts = ( List<MonthTransaction> ) object;
            return mts;
        }
        return null;
    }

    public boolean addMonthTransactions( String name, String month, String category,
            String type, String stringAmount ) {

        if ( !name.equals( "" ) && !stringAmount.equals( "" ) ) {
            int monthId = 0, categoryId = 0;
            try {
                double amount = Double.parseDouble( stringAmount );
                if ( amount <= 0 ) {
                    if ( logger != null ) {
                        logger.severe( "Error in the controller, in the addMonthTransactions method: "
                                + "The transaction amount must be a positive number" );
                    } else {
                        System.out.println( "Error in the controller, in the addMonthTransactions method: "
                                + "Logger not initialized"
                                + "\nError in the addMonthTransactions method: "
                                + "The transaction amount must be a positive number" );
                    }
                    return false;
                }
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
                Object object = facade.insertMonthTransaction( logger, monthTransaction );

                if ( object instanceof Boolean ) {
                    if ( ( boolean ) object != false ) {
                        return true;
                    }
                } else if ( object != null ) {
                    return true;
                }
            } catch ( NumberFormatException ex ) {
                if ( logger != null ) {
                    logger.severe( "Error in the controller, in the addMonthTransactions method: "
                            + "The transaction amount must be a number" );
                } else {
                    System.out.println( "Error in the controller, in the addMonthTransactions method: "
                            + "Logger not initialized"
                            + "\nError in the addMonthTransactions method: "
                            + "The transaction amount must be a number" );
                }
                return false;
            }
        }
        if ( logger != null ) {
            logger.severe( "Error in the controller, in the addMonthTransactions method: "
                    + "The transaction name and the amount can't be empty" );
        } else {
            System.out.println( "Error in the controller, in the addMonthTransactions method: "
                    + "Logger not initialized"
                    + "\nError in the addMonthTransactions method: "
                    + "The transaction name and the amount can't be empty" );
        }
        return false;
    }

    public boolean updateMonthTransactions( String stringID, String name, String month, String category,
            String type, String stringAmount ) {

        if ( !name.equals( "" ) && !stringAmount.equals( "" ) ) {
            int id = Integer.parseInt( stringID );
            try {
                double amount = Double.parseDouble( stringAmount );
                if ( amount <= 0 ) {
                    if ( logger != null ) {
                        logger.severe( "Error in the controller, in the updateMonthTransactions method: "
                                + "The transaction amount must be a positive number" );
                    } else {
                        System.out.println( "Error in the controller, in the updateMonthTransactions method: "
                                + "Logger not initialized"
                                + "\nError in the updateMonthTransactions method: "
                                + "The transaction amount must be a positive number" );
                    }
                    return false;
                }
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
                Object object = facade.updateMonthTransaction( logger, id, monthTransaction );

                if ( object instanceof Boolean ) {
                    if ( ( boolean ) object != false ) {
                        return true;
                    }
                } else if ( object != null ) {
                    return true;
                }
            } catch ( NumberFormatException ex ) {
                if ( logger != null ) {
                    logger.severe( "Error in the controller, in the updateMonthTransactions method: "
                            + "The transaction amount must be a number" );
                } else {
                    System.out.println( "Error in the controller, in the updateMonthTransactions method: "
                            + "Logger not initialized"
                            + "\nError in the updateMonthTransactions method: "
                            + "The transaction amount must be a number" );
                }
                return false;
            }
        }
        if ( logger != null ) {
            logger.severe( "Error in the controller, in the updateMonthTransactions method: "
                    + "\"The transaction name and the amount can't be empty" );
        } else {
            System.out.println( "Error in the controller, in the updateMonthTransactions method: "
                    + "Logger not initialized"
                    + "\nError in the updateMonthTransactions method: "
                    + "The transaction name and the amount can't be empty" );
        }
        return false;
    }

    public boolean deleteMonthTransaction( int monthTransactionID ) {
        return facade.deleteMonthTransaction( logger, monthTransactionID );
    }

    /*
     * deleteAllMonthTransactions in how included in the current GUI build.
     */
    public boolean deleteAllMonthTransactions() {
        return facade.deleteAllMonthTransactions( logger );
    }

}
