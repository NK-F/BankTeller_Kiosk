/**
 * This class extends the Checking class, called the CollegeChecking class.
 * The new piece of information needed for this account is the campus code,
 * specifying which campus is being mentioned. This shares the same methods
 * as the Checking class.
 *
 * @author Nicolas Karris-Flores
 * @author Kyle Mlynarski
 */

public class CollegeChecking extends Checking {

    private final String[] campusCodeLookup = {"NEW_BRUNSWICK", "NEWARK", "CAMDEN"};
    private int campusCode;

    /**
     * @param holder     Profile of associated account holder.
     * @param balance    Initial deposit amount.
     * @param campusCode integer representation of campusCode.
     */
    public CollegeChecking(Profile holder, double balance, int campusCode) {
        super(holder, balance, "College Checking", 0.0025, 0, 0);
        this.campusCode = campusCode;
    }

    /**
     * @param holder  Profile of associated account holder.
     * @param balance Initial deposit amount.
     */
    public CollegeChecking(Profile holder, double balance) {
        super(holder, balance, "College Checking", 0.0025, 0, 0);
    }

    /**
     * @return True if valid campus code is associated with account. Else, returns false.
     */
    private boolean isValidCampusCode() {
        return (campusCodeLookup.length > 0) && (campusCode >= 0) && (campusCode <= campusCodeLookup.length - 1);
    }

    @Override
    public String toString() {
        return super.toString() + "::" + campusCodeLookup[campusCode];
    }

    /**
     * @return True if valid campus code is associated with account. Else, returns false.
     */
    @Override
    public boolean isEligible() {
        return this.isValidCampusCode();
    }
}
