package hamcrestTests;

import entity.Category;
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
                return areMatching;
            }

            @Override
            public void describeTo( Description description ) {
                description.appendText( theExpected.toString() + " /Type : " + type );
            }

        };
    }
}
