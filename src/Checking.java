import java.text.DecimalFormat;

/**
 * This class is an extended class of the account class, called the Checking class.
 * This class is the basic account type, returning the fee amount, the monthly
 * interest amount, and a String sentence of the information held.
 *
 * @author Nicolas Karris-Flores
 * @author Kyle Mlynarski
 */

public class Checking extends Account {
    protected String type;
    protected double rate;
    protected double fee;
    protected int minBalance;

    /**
     * @param holder     Profile of associated account holder.
     * @param balance    Initial deposit amount.
     * @param type       Account Type as string.
     * @param rate       Interest rate as double.
     * @param fee        Default fee value of fee charged to account of type.
     * @param minBalance minimum Balance for fee to be waived.
     */
    protected Checking(Profile holder, double balance, String type, double rate, double fee, int minBalance) {
        super(holder, balance);
        this.type = type;
        this.rate = rate;
        this.fee = fee;
        this.minBalance = minBalance;
    }

    /**
     * @param holder  Profile of associated account holder.
     * @param balance Initial deposit amount.
     */
    public Checking(Profile holder, double balance) {
        this(holder, balance, "Checking", 0.001, 25, 1000);
    }

    @Override
    public String toString() {
        return this.type + "::" + super.toString();
    }

    @Override
    public double monthlyInterest() {
        DecimalFormat df = new DecimalFormat("0.00");
        double monthsInYear = 12;
        return Double.parseDouble(df.format((rate / monthsInYear) * balance));
    }

    @Override
    public double fee() {
        if (balance > minBalance) {
            return 0;
        } else {
            return fee;
        }
    }

    @Override
    public String getType() {
        return type;
    }

}
