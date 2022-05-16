/**
 * This class creates and manipulates the Profile object, which holds
 * the profile's first name, last name, and date of birth (dob). This
 * class compares profiles and prints all information out.
 *
 * @author Nicolas Karris-Flores
 * @author Kyle Mlynarski
 */

public class Profile {
    private String fname;
    private String lname;
    private Date dob;

    //Constructor which accepts DOB as String

    /**
     * @param fname First Name as String
     * @param lname Last Name as String
     * @param dob   String representing date of birth
     */
    public Profile(String fname, String lname, String dob) {
        Date tdob = new Date(dob);
        this.fname = fname;
        this.lname = lname;
        this.dob = tdob;
    }

    //Constructor which accepts DOB as Date

    /**
     * @param fname First Name as String
     * @param lname Last Name as String
     * @param dob   Date object representing date of birth
     */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Profile p) {
            return (this.fname.toLowerCase().equals(p.fname.toLowerCase())) && (this.lname.toLowerCase().equals(p.lname.toLowerCase())) && (this.dob.compareTo(p.dob) == 0);
        }
        return false;
    }

    @Override
    public String toString() {
        return fname + " " + lname + " " + dob;
    }
}
