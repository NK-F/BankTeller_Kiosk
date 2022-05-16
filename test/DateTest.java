import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUNIT Test Class - Responsible fore testing the Date Class.
 */

public class DateTest {

    @org.junit.Test
    public void isValid() {
        //Test case 1: Day is not in month
        Date date1 = new Date("3/32/2011");
        assertFalse(date1.isValid());

        //Test Case 2: Date is leap year NOT in 2022
        Date date2 = new Date("2/29/2021");
        assertFalse(date2.isValid());

        //Test Case 3: The month is less than 1
        Date date3 = new Date("0/6/2022");
        assertFalse(date3.isValid());

        //Test Case 4: The month is greater than 12
        Date date4 = new Date("17/14/2008");
        assertFalse(date4.isValid());

        //Test Case 5: The date with 31-day month has over 31-date
        Date date5 = new Date("5/32/2021");
        assertFalse(date5.isValid());

        //Test Case 6: The day is less than 1
        Date date6 = new Date("6/0/2021");
        assertFalse(date6.isValid());
        
        //Test Case 7: The year is greater than the present year
        Date date7 = new Date("6/10/4000");
        assertFalse(date7.isValid());

        //Test Case 8: The year is a negative year
        Date date8 = new Date("6/10/-2018");
        assertFalse(date8.isValid());

        //Test Case 9: The year is 0
        Date date9 = new Date("6/10/0");
        assertFalse(date9.isValid());

    }
}
