package testing;

import facade.Facade;

public class Tester {

    public static void main( String[] args ) {
        new Tester().tester();
    }

    private void tester() {
        Facade facade = Facade.getInstance( null, null, null );
        System.out.println( "Did we initialize connection : " + facade.initializeConnection() );
    }
}
