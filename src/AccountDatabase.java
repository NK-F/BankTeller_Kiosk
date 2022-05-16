/**
 * This class holds all account information and manipulates the accounts and
 * the account list based on the commands called by the BankTeller class.
 *
 * @author Nicolas Karris-Flores
 * @author Kyle Mlynarski
 */

public class AccountDatabase {
    private Account[] accounts;
    private int numAcct;

    private final static int CAPACITY = 4;
    private final static int NOT_FOUND = -1;


    /**
     * Default Constructor
     */
    public AccountDatabase() {
        this.accounts = new Account[CAPACITY];
        this.numAcct = 0;
    }

    /**
     * @param account An account object which will be compared against the database.
     * @return index of the account if it exists in the database, else NOT_FOUND.
     */
    private int find(Account account) {
        for (int i = 0; i < numAcct; i++) {
            if (accounts[i].equals(account)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Expands the size of the [Array] accounts. Called when the size requested exceeds the available space
     * in [Array] accounts.
     */
    private void grow() {
        Account[] clone = new Account[numAcct];
        for (int i = 0; i < numAcct; i++) {
            clone[i] = accounts[i];
        }

        accounts = new Account[numAcct + 4];

        for (int i = 0; i < numAcct; i++) {
            accounts[i] = clone[i];
        }
    }

    /**
     * @param account An account object which will be opened if certain parameters are met.
     * @return true if Account does not exist in the database, and customer is eligible for
     * specified account type. Else returns false.
     */
    public boolean open(Account account) {
        if (numAcct == accounts.length) {
            grow();
        }

        if (find(account) == NOT_FOUND) {
            if (account.isEligible()) {
                accounts[numAcct] = account;
                numAcct++;
                return true;
            }
        }
        return false;
    }

    /**
     * @param account An account object which will be re-opened if such an account is found to exist in the database.
     * @return true if account exists in the database and is in closed state. Else returns false.
     */
    public boolean reopen(Account account) {
        int index = find(account);
        if (index != NOT_FOUND) {
            if (accounts[index].isAccountClosed()) {
                accounts[index].reopenAccount();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * @param account An account object which will be closed if such an account is found to exist in the database.
     * @return true if account exists and is not in closed state. Else returns false.
     */
    public boolean close(Account account) {
        int index = find(account);

        if (index == NOT_FOUND) {
            return false;
        } else {
            if (accounts[index].isAccountClosed()) {
                return false;
            } else {
                accounts[index].closeAccount();
                return true;
            }
        }
    }

    /**
     * @param account An account with a non-zero balance.
     */
    public void deposit(Account account) {

        if (acctExists(account)) {
            int indexAccount = find(account);
            accounts[indexAccount].balance += account.balance;
        }

    }

    /**
     * @param account An account with a non-zero balance.
     * @return If the balance of the argument account is less than the balance of the logically equivelent account in
     * the database, then true. Else returns false.
     */
    public boolean withdraw(Account account) {

        if (acctExists(account)) {
            int indexAccount = find(account);
            if ((accounts[indexAccount].balance - account.balance) > 0) {
                accounts[indexAccount].balance -= account.balance;
                return true;
            }
        }
        return false;
    }

    /**
     * @return If the number of accounts in the database is non-zero, returns true. Else returns false.
     */
    public boolean dbIsNotEmpty() {
        return numAcct > 0;
    }

    /**
     * @param account An account object which will be logically compared to all elements of the database to find a
     *                match.
     * @return If a logically equivalent account exists in the database, returns true. Else returns false.
     */
    public boolean acctExists(Account account) {
        int index = find(account);

        return index != NOT_FOUND;
    }

    /**
     * Prints all accounts in the database in the order which they currently exist in the database.
     */
    public void print() {
        for (int i = 0; i < numAcct; i++) {
            System.out.println(accounts[i].toString());
        }
    }

    /**
     * Prints all accounts in the database ordered by account type.
     */
    public void printByAccountType() {
        Account[] arrayToSort = new Account[numAcct];

        for (int i = 0; i < numAcct; i++) {
            arrayToSort[i] = accounts[i];
        }

        sortByAccountType(arrayToSort);

        for (int i = 0; i < numAcct; i++) {
            System.out.println(arrayToSort[i].toString());
        }
    }

    /**
     * Prints all accounts in the database with interest and fees.
     */
    public void printFeeAndInterest() {
        for (int i = 0; i < numAcct; i++) {
            System.out.println(accounts[i] + "::fee $" + accounts[i].fee() + "::monthly interest $" + accounts[i].monthlyInterest());
        }
    }

    /**
     * Updates balance of all accounts in database to account for monthly interest payments and fees.
     */
    public void updateAccountBalances() {
        for (int i = 0; i < numAcct; i++) {
            double svcAdjustment = accounts[i].monthlyInterest() - accounts[i].fee();

            if (svcAdjustment > 0) {
                accounts[i].deposit(svcAdjustment);
            } else if (svcAdjustment < 0) {
                svcAdjustment = svcAdjustment * -1;
                accounts[i].withdraw(svcAdjustment);
            }
        }
    }

    /**
     * @param arrayToSort An unsorted array of account objects
     */
    private void sortByAccountType(Account[] arrayToSort) {
        Account temp;

        for (int i = 0; i < numAcct; i++) {
            for (int j = 1; j < (numAcct - i); j++) {
                if (arrayToSort[j - 1].getType().compareTo(arrayToSort[j].getType()) > 0) {
                    temp = arrayToSort[j - 1];
                    arrayToSort[j - 1] = arrayToSort[j];
                    arrayToSort[j] = temp;
                }
            }
        }
    }

}
