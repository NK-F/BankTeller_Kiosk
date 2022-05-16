import java.text.DecimalFormat;

/**
 * This class creates and manipulates the Account object, which holds the
 * Profile object, the boolean variable closed, and the double variable
 * balance. This class compares two accounts, can withdraw from an account,
 * and can print all Account information as a String.
 *
 * @author Nicolas Karris-Flores
 * @author Kyle Mlynarski
 */

public abstract class Account {
    protected Profile holder;
    protected boolean closed;
    protected double balance;

    /**
     * @param holder  Profile of associated account holder.
     * @param balance Initial deposit amount.
     */
    public Account(Profile holder, double balance) {
        this(balance);
        this.holder = holder;
    }

    /**
     * @param balance Initial deposit amount.
     */
    public Account(double balance) {
        this();
        this.balance = balance;
    }

    /**
     * Default Constructor.
     */
    public Account() {
        this.closed = false;
    }

    /**
     * @param obj Object
     * @return True if objects are logically equivalent. Else, returns false.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Account a) {
            if (this.holder.equals(a.holder)) {
                if (this.getType().equals(a.getType())) {
                    return true;
                } else if ((this.getType().equals("Checking")) || (this.getType().equals("College Checking"))) {
                    if ((a.getType().equals("Checking")) || (a.getType().equals("College Checking"))) {
                        return true;
                    }
                }
            }
            return this.holder.equals(a.holder) && (this.getType().equals(a.getType()));
        }
        return false;
    }

    /**
     * @return String representation of Account Object.
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("###,###.00");
        if (this.closed) {
            return holder + "::Balance $" + df.format(this.balance) + "::CLOSED";
        }
        return holder + "::Balance $" + df.format(this.balance);
    }

    /**
     * @param amount double amount to be withdrawn from account object.
     */
    public void withdraw(double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
        }
    }

    /**
     * re-opens this account object
     */
    public void reopenAccount() {
        this.closed = false;
    }

    /**
     * closes this account object
     */
    public void closeAccount() {
        this.closed = true;
    }

    /**
     * @return true if account is in closed state. Else, returns false.
     */
    public boolean isAccountClosed() {
        return this.closed;
    }

    /**
     * @return returns true for generic account objects.
     */
    public boolean isEligible() {
        return true;
    }

    /**
     * @param amount double amount to be deposited into account object.
     */
    public void deposit(double amount) {
        this.balance += amount;
    }

    //return the monthly interest

    /**
     * @return double monthly interest earned per this account object.
     */
    public abstract double monthlyInterest();

    //return the monthly fee

    /**
     * @return double monthly fee charged for this account object
     */
    public abstract double fee();

    //return the account type (class name)

    /**
     * @return Account Type as string
     */
    public abstract String getType();
}
