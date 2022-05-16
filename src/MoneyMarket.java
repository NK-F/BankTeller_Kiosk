/**
 * This class is an extended class of the savings class, called the Money
 * Market class. This type of account is a savings account that automatically
 * has the loyal characteristic true unless under the circumstance of having
 * a balance below $2500.
 *
 * @author Nicolas Karris-Flores
 * @author Kyle Mlynarski
 */

public class MoneyMarket extends Savings {
    private int numWithdrawls;

    /**
     * @param holder  Profile of associated account holder.
     * @param balance Initial deposit amount.
     */
    public MoneyMarket(Profile holder, double balance) {
        super(holder, balance, "Money Market Savings", true, 0.008, 10, 2500);
        this.numWithdrawls = 0;
    }

    @Override
    public void reopenAccount() {
        this.isLoyal = this.balance > 2500.00;
        super.reopenAccount();
    }

    @Override
    public String toString() {
        return super.toString() + "::withdrawl: " + numWithdrawls;
    }

    @Override
    public double fee() {
        if (numWithdrawls <= 3) {
            return super.fee();
        } else {
            return fee;
        }
    }
}
