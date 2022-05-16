import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUNIT Test Class - Responsible fore testing the MoneyMarket Class.
 */

public class MoneyMarketTest {

    @Test
    public void monthlyInterest() {
        //Test Case 1: MoneyMarket account is below $2500 threshold
        MoneyMarket mm1 = new MoneyMarket(new Profile("Sugondese", "Noots", new Date("10/6/2000")), 1400);
        assertEquals(0.93, mm1.monthlyInterest(), 0.00);

        //Test Case 2: MoneyMarket account is above $2500 threshold
        MoneyMarket mm2 = new MoneyMarket(new Profile("Ligma", "Noots", new Date("4/6/2005")), 3000);
        assertEquals(2.38, mm2.monthlyInterest(), 0.00);

        //Test Case 3: MoneyMarket account is above $2500 threshold
        MoneyMarket mm3 = new MoneyMarket(new Profile("April", "March", new Date("4/6/2005")), 4500);
        assertEquals(3.56, mm3.monthlyInterest(), 0.00);

        //Test Case 4: MoneyMarket account is below $2500 threshold
        MoneyMarket mm4 = new MoneyMarket(new Profile("July", "June", new Date("4/6/2005")), 2000);
        assertEquals(1.33, mm4.monthlyInterest(), 0.00);

        //Test Case 5: MoneyMarket account is below $2500 threshold
        MoneyMarket mm5 = new MoneyMarket(new Profile("August", "June", new Date("4/6/2005")), 1020);
        assertEquals(0.68, mm5.monthlyInterest(), 0.00);

        //Test Case 6: MoneyMarket account is above $2500 threshold
        MoneyMarket mm6 = new MoneyMarket(new Profile("December", "April", new Date("4/6/2005")), 5555);
        assertEquals(4.40, mm6.monthlyInterest(), 0.00);

        //Test Case 7: MoneyMarket account is above $2500 threshold
        MoneyMarket mm7 = new MoneyMarket(new Profile("Brock", "Obamadome", new Date("4/6/2005")), 2870);
        assertEquals(2.27, mm7.monthlyInterest(), 0.00);

    }
}
