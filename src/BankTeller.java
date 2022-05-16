import java.util.Scanner;
import java.util.StringTokenizer;


/**
 * This class handles all the inputs from the user, and starts calling the necessary
 * methods in other classes. This takes the input line, with or without the necessary
 * information, and performs tasks and handles exceptions given the user's input.
 *
 * @author Nicolas Karris-Flores
 * @author Kyle Mlynarski
 */

public class BankTeller {
    private AccountDatabase sessionDB;

    /**
     * Method responsible for handling user input (commands) and terminating program execution
     */
    public void run() {
        boolean exit = false;
        String line;
        int tokenCount;
        String command;
        Scanner userIn = new Scanner(System.in);
        sessionDB = new AccountDatabase();

        System.out.println("Bank Teller is running.");

        while (!exit) {
            line = userIn.nextLine();
            StringTokenizer inputParser = new StringTokenizer(line, " ");

            tokenCount = inputParser.countTokens();

            if (tokenCount >= 1) {
                command = inputParser.nextToken();

                switch (command) {
                    case "O" -> processOpenAccountCmd(inputParser);
                    case "C" -> processCloseAccountCmd(inputParser);
                    case "D" -> processDepositCmd(inputParser);
                    case "W" -> processWithdrawCmd(inputParser);
                    case "P" -> processPrintCmd();
                    case "PT" -> processPrintByTypeCmd();
                    case "PI" -> processPrintByInterestCmd();
                    case "UB" -> processUpdateBalanceCmd();
                    case "Q" -> exit = true;
                    default -> System.out.println("Invalid command!");
                }
            }
        }

        System.out.println("Bank Teller is terminated.");
    }

    /**
     * @param inputParser StringTokenizer object which includes data which has not yet been parsed.
     */
    private void processOpenAccountCmd(StringTokenizer inputParser) {
        int tokenCount = inputParser.countTokens();
        if (tokenCount >= 5) {
            String acctType = inputParser.nextToken();
            acctType = acctType.trim();
            String fname = inputParser.nextToken();
            String lname = inputParser.nextToken();
            String dob = inputParser.nextToken();

            Date tdob = new Date(dob);

            if (isValidDate(tdob)) {
                double depositAmount = -1.00;
                try {
                    depositAmount = Double.parseDouble(inputParser.nextToken());
                } catch (NumberFormatException e) {
                }
                if (isInitialValidDepositAmount(depositAmount)) {
                    Profile holder = new Profile(fname, lname, tdob);
                    switch (acctType) {
                        case "C" -> openCheck(holder, depositAmount);
                        case "MM" -> openMoneyMarket(holder, depositAmount);
                        case "CC" -> {
                            int campusCode = Integer.parseInt(inputParser.nextToken());
                            openCollegeCheck(holder, depositAmount, campusCode);
                        }
                        case "S" -> {
                            String isLoyalTemp = inputParser.nextToken();
                            boolean isLoyal = isLoyalTemp.equals("1");
                            openSavings(holder, depositAmount, isLoyal);
                        }
                    }
                }
            }
        } else {
            System.out.println("Missing data for opening an account.");
        }
    }

    /**
     * @param inputParser StringTokenizer object which includes data which has not yet been parsed.
     */
    private void processCloseAccountCmd(StringTokenizer inputParser) {
        int tokenCount = inputParser.countTokens();
        if (tokenCount >= 4) {
            String acctType = inputParser.nextToken();
            String fname = inputParser.nextToken();
            String lname = inputParser.nextToken();
            String dob = inputParser.nextToken();

            Date tdob = new Date(dob);

            Profile holder = new Profile(fname, lname, tdob);

            boolean result = false;

            switch (acctType) {
                case "C" -> {
                    Checking tCheck = new Checking(holder, 0.00);
                    result = sessionDB.close(tCheck);
                }
                case "MM" -> {
                    MoneyMarket tMoneyMarket = new MoneyMarket(holder, 0.00);
                    result = sessionDB.close(tMoneyMarket);
                }
                case "CC" -> {
                    CollegeChecking tCollegeChecking = new CollegeChecking(holder, 0.00);
                    result = sessionDB.close(tCollegeChecking);
                }
                case "S" -> {
                    Savings tSavings = new Savings(holder, 0.00, false);
                    result = sessionDB.close(tSavings);
                }
            }
            if (result) {
                System.out.println("Account closed.");
            } else {
                System.out.println("Account is closed already.");
            }
        } else {
            System.out.println("Missing data for closing an account.");
        }
    }

    /**
     * @param inputParser StringTokenizer object which includes data which has not yet been parsed.
     */
    private void processDepositCmd(StringTokenizer inputParser) {
        int tokenCount = inputParser.countTokens();
        if (tokenCount >= 5) {
            String acctType = inputParser.nextToken();
            String fname = inputParser.nextToken();
            String lname = inputParser.nextToken();
            String dob = inputParser.nextToken();

            Date tdob = new Date(dob);

            if (isValidDate(tdob)) {
                double depositAmount = -1.00;
                try {
                    depositAmount = Double.parseDouble(inputParser.nextToken());
                } catch (NumberFormatException e) {
                }
                if (isValidDepositAmount(depositAmount)) {
                    Profile holder = new Profile(fname, lname, tdob);
                    switch (acctType) {
                        case "C" -> depositCheck(holder, depositAmount);
                        case "MM" -> depositMoneyMarket(holder, depositAmount);
                        case "CC" -> depositCollegeCheck(holder, depositAmount);
                        case "S" -> depositSavings(holder, depositAmount);
                    }
                }
            }
        }
    }

    /**
     * @param inputParser StringTokenizer object which includes data which has not yet been parsed.
     */
    private void processWithdrawCmd(StringTokenizer inputParser) {
        int tokenCount = inputParser.countTokens();
        if (tokenCount >= 5) {
            String acctType = inputParser.nextToken();
            String fname = inputParser.nextToken();
            String lname = inputParser.nextToken();
            String dob = inputParser.nextToken();

            Date tdob = new Date(dob);

            if (isValidDate(tdob)) {
                double depositAmount = -1.00;
                try {
                    depositAmount = Double.parseDouble(inputParser.nextToken());
                } catch (NumberFormatException e) {
                }
                if (isValidWithdrawlAmount(depositAmount)) {
                    Profile holder = new Profile(fname, lname, tdob);
                    switch (acctType) {
                        case "C" -> withdrawCheck(holder, depositAmount);
                        case "MM" -> withdrawMoneyMarket(holder, depositAmount);
                        case "CC" -> withdrawCollegeCheck(holder, depositAmount);
                        case "S" -> withdrawSavings(holder, depositAmount);
                    }
                }
            }
        } else {
            System.out.println("Missing data for withdrawing from an account.");
        }
    }

    /**
     * Method handles print command.
     */
    private void processPrintCmd() {
        if (sessionDB.dbIsNotEmpty()) {
            System.out.println("*list of accounts in the database*");
            sessionDB.print();
            System.out.println("*end of list*");
        } else {
            System.out.println("Account Database is empty!");
        }
    }

    /**
     * Method handles Print by Type command.
     */
    private void processPrintByTypeCmd() {
        if (sessionDB.dbIsNotEmpty()) {
            System.out.println("*list of accounts by account type.");
            sessionDB.printByAccountType();
            System.out.println("*end of list.");
        } else {
            System.out.println("Account Database is empty!");
        }
    }

    private void processPrintByInterestCmd() {
        if (sessionDB.dbIsNotEmpty()) {
            System.out.println("*list of accounts with fee and monthly interest");
            sessionDB.printFeeAndInterest();
            System.out.println("*end of list.");
        } else {
            System.out.println("Account Database is empty!");
        }
    }

    /**
     * Method handles update balance command.
     */
    private void processUpdateBalanceCmd() {
        if (sessionDB.dbIsNotEmpty()) {
            sessionDB.updateAccountBalances();
            System.out.println("*list of accounts with updated balance");
            sessionDB.print();
            System.out.println("*end of list.");
        } else {
            System.out.println("Account Database is empty!");
        }
    }

    /**
     * @param holder        Profile of account holder.
     * @param depositAmount Initial deposit amount.
     */
    private void openCheck(Profile holder, double depositAmount) {
        Checking tCheck = new Checking(holder, depositAmount);
        if (sessionDB.open(tCheck)) {
            System.out.println("Account opened.");
        } else {
            if (sessionDB.reopen(tCheck)) {
                System.out.println("Account reopened.");
            } else {
                System.out.println(holder + " same account(type) is in the database.");
            }
        }
    }

    /**
     * @param holder        Profile of account holder.
     * @param depositAmount Deposit amount.
     */
    private void depositCheck(Profile holder, double depositAmount) {
        Checking tCheck = new Checking(holder, depositAmount);
        if (sessionDB.acctExists(tCheck)) {
            sessionDB.deposit(tCheck);
            System.out.println("Deposit - balance updated.");
        } else {
            System.out.println(tCheck.holder.toString() + " " + tCheck.type + " is not in the database.");
        }
    }

    /**
     * @param holder        Profile of account holder.
     * @param depositAmount Withdrawl amount.
     */
    private void withdrawCheck(Profile holder, double depositAmount) {
        Checking tCheck = new Checking(holder, depositAmount);
        if (sessionDB.acctExists(tCheck)) {
            if (sessionDB.withdraw(tCheck)) {
                System.out.println("Withdraw - balance updated.");
            } else {
                System.out.println("Withdraw - insufficient fund.");
            }
        } else {
            System.out.println(tCheck.holder.toString() + " " + tCheck.type + " is not in the database.");
        }
    }

    /**
     * @param holder        Profile of account holder.
     * @param depositAmount Initial deposit amount.
     */
    private void openCollegeCheck(Profile holder, double depositAmount, int campusCode) {
        CollegeChecking tCollegeChecking = new CollegeChecking(holder, depositAmount, campusCode);
        if (!sessionDB.open(tCollegeChecking)) {
            if (sessionDB.reopen(tCollegeChecking)) {
                System.out.println("Account reopened.");
            } else {
                if (sessionDB.acctExists(tCollegeChecking)) {
                    System.out.println(holder + " same account(type) is in the database.");
                } else {
                    System.out.println("Invalid campus code.");
                }
            }
        } else {
            System.out.println("Account opened.");
        }
    }

    /**
     * @param holder        Profile of account holder.
     * @param depositAmount Deposit amount.
     */
    private void depositCollegeCheck(Profile holder, double depositAmount) {
        CollegeChecking tCollegeChecking = new CollegeChecking(holder, depositAmount);
        if (sessionDB.acctExists(tCollegeChecking)) {
            sessionDB.deposit(tCollegeChecking);
            System.out.println("Deposit - balance updated.");
        } else {
            System.out.println(tCollegeChecking.holder.toString() + " " + tCollegeChecking.type + " is not in the database.");
        }
    }

    /**
     * @param holder        Profile of account holder.
     * @param depositAmount Withdrawl amount.
     */
    private void withdrawCollegeCheck(Profile holder, double depositAmount) {
        CollegeChecking tCollegeChecking = new CollegeChecking(holder, depositAmount);
        if (sessionDB.acctExists(tCollegeChecking)) {
            if (sessionDB.withdraw(tCollegeChecking)) {
                System.out.println("Withdraw - balance updated.");
            } else {
                System.out.println("Withdraw - insufficient fund.");
            }
        } else {
            System.out.println(tCollegeChecking.holder.toString() + " " + tCollegeChecking.type + " is not in the database.");
        }
    }

    /**
     * @param holder        Profile of account holder.
     * @param depositAmount Initial deposit amount.
     */
    private void openMoneyMarket(Profile holder, double depositAmount) {
        if (depositAmount >= 2500.00) {
            MoneyMarket tMoneyMarket = new MoneyMarket(holder, depositAmount);
            if (sessionDB.open(tMoneyMarket)) {
                System.out.println("Account opened.");
            } else {
                if (sessionDB.reopen(tMoneyMarket)) {
                    System.out.println("Account reopened.");
                } else {
                    System.out.println(holder + " same account(type) is in the database.");
                }
            }
        } else {
            System.out.println("Minimum of $2500 to open a MoneyMarket account.");
        }
    }

    /**
     * @param holder        Profile of account holder.
     * @param depositAmount Deposit amount.
     */
    private void depositMoneyMarket(Profile holder, double depositAmount) {
        MoneyMarket tMoneyMarket = new MoneyMarket(holder, depositAmount);
        if (sessionDB.acctExists(tMoneyMarket)) {
            sessionDB.deposit(tMoneyMarket);
            System.out.println("Deposit - balance updated.");
        } else {
            System.out.println(tMoneyMarket.holder.toString() + " " + tMoneyMarket.type + " is not in the database.");
        }
    }

    /**
     * @param holder        Profile of account holder.
     * @param depositAmount Withdrawl amount.
     */
    private void withdrawMoneyMarket(Profile holder, double depositAmount) {
        MoneyMarket tMoneyMarket = new MoneyMarket(holder, depositAmount);
        if (sessionDB.acctExists(tMoneyMarket)) {
            if (sessionDB.withdraw(tMoneyMarket)) {
                System.out.println("Withdraw - balance updated.");
            } else {
                System.out.println("Withdraw - insufficient fund.");
            }
        } else {
            System.out.println(tMoneyMarket.holder.toString() + " " + tMoneyMarket.type + " is not in the database.");
        }
    }

    /**
     * @param holder        Profile of account holder.
     * @param depositAmount Initial deposit amount.
     */
    private void openSavings(Profile holder, double depositAmount, boolean isLoyal) {
        Savings tSavings = new Savings(holder, depositAmount, isLoyal);
        if (sessionDB.open(tSavings)) {
            System.out.println("Account opened.");
        } else {
            if (sessionDB.reopen(tSavings)) {
                System.out.println("Account reopened.");
            } else {
                System.out.println(holder + " same account(type) is in the database.");
            }
        }
    }

    /**
     * @param holder        Profile of account holder.
     * @param depositAmount Deposit amount.
     */
    private void depositSavings(Profile holder, double depositAmount) {
        Savings tSavings = new Savings(holder, depositAmount, true);
        if (sessionDB.acctExists(tSavings)) {
            sessionDB.deposit(tSavings);
            System.out.println("Deposit - balance updated.");
        } else {
            System.out.println(tSavings.holder.toString() + " " + tSavings.type + " is not in the database.");
        }
    }

    /**
     * @param holder        Profile of account holder.
     * @param depositAmount Withdrawl amount.
     */
    private void withdrawSavings(Profile holder, double depositAmount) {
        Savings tSavings = new Savings(holder, depositAmount, true);
        if (sessionDB.acctExists(tSavings)) {
            if (sessionDB.withdraw(tSavings)) {
                System.out.println("Withdraw - balance updated.");
            } else {
                System.out.println("Withdraw - insufficient fund.");
            }
        } else {
            System.out.println(tSavings.holder.toString() + " " + tSavings.type + " is not in the database.");
        }
    }

    /**
     * @param depositAmount double depositAmount
     * @return True if deposit amount is valid. Else, returns false
     */
    private boolean isInitialValidDepositAmount(double depositAmount) {
        if (depositAmount > 0) {
            return true;
        } else if (depositAmount == -1.00) {
            System.out.println("Not a valid amount.");
            return false;
        } else {
            System.out.println("Initial deposit cannot be 0 or negative.");
            return false;
        }
    }

    /**
     * @param depositAmount double depositAmount
     * @return True if deposit amount is valid. Else, returns false
     */
    private boolean isValidDepositAmount(double depositAmount) {
        if (depositAmount > 0) {
            return true;
        } else if (depositAmount == -1.00) {
            System.out.println("Not a valid amount.");
            return false;
        } else {
            System.out.println("Deposit - amount cannot be 0 or negative.");
            return false;
        }
    }

    /**
     * @param depositAmount double depositAmount
     * @return True if withdrawl amount is valid. Else, returns false
     */
    private boolean isValidWithdrawlAmount(double depositAmount) {
        if (depositAmount > 0) {
            return true;
        } else if (depositAmount == -1.00) {
            System.out.println("Not a valid amount.");
            return false;
        } else {
            System.out.println("Withdraw - amount cannot be 0 or negative.");
            return false;
        }
    }

    /**
     * @param tdob Date object
     * @return true if date is valid. Else, returns false.
     */
    private boolean isValidDate(Date tdob) {
        if (tdob.isValid()) {
            return true;
        } else {
            System.out.println("Date of birth invalid.");
            return false;
        }
    }

}
