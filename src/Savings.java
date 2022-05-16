import java.text.DecimalFormat;

/**
 * This class is an extended class of Account, it is a specific type
 * of account, the savings account. This returns a fee and the
 * monthly interest of both the savings account and the money
 * market account.
 *
 * @author Nicolas Karris-Flores
 * @author Kyle Mlynarski
 */

public class Savings extends Account {
    protected String type;
    protected double rate;
    protected double fee;
    protected int minBalance;
    protected boolean isLoyal;

    /**
     * @param holder     Profile of associated account holder.
     * @param balance    Initial deposit amount.
     * @param type       Account Type as string.
     * @param isLoyal    Boolean representing if holder is a loyal customer.
     * @param rate       Interest rate as double.
     * @param fee        Default fee value of fee charged to account of type.
     * @param minBalance minimum Balance for fee to be waived.
     */
    protected Savings(Profile holder, double balance, String type, boolean isLoyal, double rate, double fee, int minBalance) {
        super(holder, balance);
        this.type = type;
        this.isLoyal = isLoyal;
        this.rate = rate;
        this.fee = fee;
        this.minBalance = minBalance;
    }

    public Savings(Profile holder, double balance, boolean isLoyal) {
        this(holder, balance, "Savings", isLoyal, 0.003, 6, 300);
    }

    @Override
    public String toString() {
        if (isLoyal) {
            return this.type + "::" + super.toString() + "::Loyal";
        }
        return this.type + "::" + super.toString();
    }

    @Override
    public double monthlyInterest() {
        DecimalFormat df = new DecimalFormat("0.00");
        double monthsInYear = 12.0;
        double threshold = 2500.0;
        if (isLoyal) {
            if (type.equals("Money Market Savings") && balance < threshold) {
                return Double.parseDouble(df.format((rate / monthsInYear) * balance));

            }
            double loyalty = 0.0015;
            return Double.parseDouble(df.format(((rate + loyalty) / monthsInYear) * balance));

        }
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
