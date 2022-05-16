import org.junit.Test;
import static org.junit.Assert.*;
/**
 * JUNIT Test Class - Responsible fore testing the AccountDatabase Class.
 */

public class AccountDatabaseTest {

    @Test
    public void open() {
        //Test 1: Can't open new account because one is already open
        AccountDatabase sessionDB = new AccountDatabase();
        Checking account1 = new Checking(new Profile("Michael", "Stevens", new Date("8/4/1998")), 2600);
        boolean start1 = sessionDB.open(account1);
        assertFalse(sessionDB.open(account1));

        //Test 2: Can open a 5th account by adding 4 more slots into the array
        Checking account2 = new Checking(new Profile("May", "JokesOnYou", new Date("8/4/1998")), 2600);
        Checking account3 = new Checking(new Profile("Stinky", "Yoyo", new Date("7/4/1998")), 2600);
        Checking account4 = new Checking(new Profile("Jessica", "Stones", new Date("6/4/1998")), 2600);
        Checking accountMax = new Checking(new Profile("Mad", "Max", new Date("5/4/1998")), 2600);
        sessionDB.open(account2);
        sessionDB.open(account3);
        sessionDB.open(account4);
        assertTrue(sessionDB.open(accountMax));

    }

    @Test
    public void close() {
        AccountDatabase sessionDB = new AccountDatabase();

        //Test 1: Can't close an account that is not in the Database
        Checking account1 = new Checking(new Profile("Michael", "Stevens", new Date("8/4/1998")), 2600);
        assertFalse(sessionDB.close(account1));

        //Test 2: Can close a regular account
        Checking account2 = new Checking(new Profile("May", "JokesOnYou", new Date("8/4/1998")), 2600);
        sessionDB.open(account2);
        assertTrue(sessionDB.close(account2));

        //Test 3: Can't close an already closed account
        Checking account3 = new Checking(new Profile("Stinky", "Yoyo", new Date("7/4/1998")), 2600);
        sessionDB.open(account3);
        sessionDB.close(account3);
        assertFalse(sessionDB.close(account3));
    }

}
