package hamcrestTests;

import entity.Category;
import entity.Month;
import entity.MonthTransaction;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class CustomAbstractEntityClassMatcher {

    public static Matcher matches( final Object expected ) {

        return new BaseMatcher() {

            protected Object theExpected = expected;
            protected String type = "none";

            @Override
            public boolean matches( Object o ) {
                boolean areMatching = false;
                if ( theExpected instanceof Category && o instanceof Category ) {
                    Category expectedCategory = ( Category ) theExpected;
                    Category matcherCategory = ( Category ) o;
                    type = "Category";
                    if ( (expectedCategory.getId() == matcherCategory.getId())
                            && (expectedCategory.getName().equals( matcherCategory.getName() )) ) {
                        areMatching = true;
                    }
                }
                if ( theExpected instanceof Month && o instanceof Month ) {
                    Month expectedMonth = ( Month ) theExpected;
                    Month matcherMonth = ( Month ) o;
                    type = "Month";
                    if ( (expectedMonth.getId() == matcherMonth.getId())
                            && (expectedMonth.getName().equals( matcherMonth.getName() )) ) {
                        areMatching = true;
                    }
                }
                if ( theExpected instanceof MonthTransaction && o instanceof MonthTransaction ) {
                    MonthTransaction expectedMonthTransaction = ( MonthTransaction ) theExpected;
                    MonthTransaction matcherMonthTransaction = ( MonthTransaction ) o;
                    type = "MonthTransaction";
                    if ( (expectedMonthTransaction.getId() == matcherMonthTransaction.getId())
                            && (expectedMonthTransaction.getName().equals( matcherMonthTransaction.getName() ))
                            && (expectedMonthTransaction.getAmount() == matcherMonthTransaction.getAmount())
                            && (expectedMonthTransaction.getCategoryId() == matcherMonthTransaction.getCategoryId())
                            && (expectedMonthTransaction.getMonthId() == matcherMonthTransaction.getMonthId())
                            && (expectedMonthTransaction.getType().equals( matcherMonthTransaction.getType() )) ) {
                        areMatching = true;
                    }
                }
                return areMatching;
            }

            public boolean not( Object o ) {
                return !matches( o );
            }

            @Override
            public void describeTo( Description description ) {
                description.appendText( theExpected.toString() + " /Type : " + type );
            }

        };
    }
}
