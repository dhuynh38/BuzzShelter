package com.buzzshelter.Model;
import android.os.CountDownTimer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.widget.Toast;

/**
 * Model Singleton. Duy Huynh
 */
public final class Model {

    private static final Model _instance = new Model();
    private int loginAttempts = 0;
    private int mins;
    private int secs;
    private User _currentUser = null;

    /**
     * Private Constructor
     */
    private Model() {


    }

    /**
     * Getter for Model singleton
     * @return the instance of the model
     */
    public static Model getInstance() {
        return _instance;
    }

    /**
     * Adds user to the userlist
     * @param user the user to add
     * @param context current application context
     * @return whether the user is added or not
     */
    public boolean addUser(User user, Context context) {
        DatabaseHelper db = DatabaseHelper.getInstance(context);
        if((user == null) || (context == null)) {
            Toast.makeText(context,"null is bad", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(db.fetchSpecificUserByID(user.getId()) != null) {
            Toast.makeText(context, "This User ID is in the database.", Toast.LENGTH_SHORT).show();
            return false;
        }
        db.createUSER(user);
        db.closeDB();

        Toast.makeText(context,"You are in the db", Toast.LENGTH_SHORT).show();
        return true;

    }

    /**
     * Validate if user is in the userlist
     * @param givenId the id of the user
     * @param password the password of the user
     * @param context the application context
     * @return whether the user can login or not
     */
    public boolean validateUser(String givenId, String password, Context context) {
        if((givenId == null) || (password == null) || (context == null)) {
            return false;
        }
        DatabaseHelper db = DatabaseHelper.getInstance(context);
        _currentUser = db.fetchSpecificUserByID(givenId);

        if(_currentUser != null) {
            if (password.equals(_currentUser.getPassword())) {
                db.closeDB();
                return true;
            } else {
                db.closeDB();
                return false;
            }
        }
        db.closeDB();
        return false; 
    }

    /**
     * Adds shelter to the shelter list
     * @param shelter the shelter to add
     * @param context the application context
     */
    public void addShelter(Shelter shelter, Context context) {
        if((shelter == null) || (context == null)) {
            return;
        }
        DatabaseHelper db = DatabaseHelper.getInstance(context);
        Shelter temp = db.fetchSpecificShelterByName(shelter.getName());
        if (temp != null) {
            return;
        }

        db.createSHELTER(shelter);
    }

    /**
     * Gets the shelter list
     * @param context application context
     * @return the shelter list
     */
    public Map<String, Shelter> getShelterList(Context context) {
        if(context == null) {
            return null;
        }
        DatabaseHelper db = DatabaseHelper.getInstance(context);
        Map<String, Shelter> map = db.makeShelterHashMap();
        db.closeDB();
        return map;
    }

// --Commented out by Inspection START (4/8/18 10:42 PM):
//    public HashMap<String, User> getUserList(Context context) {
//        if(context == null) {
//            return null;
//        }
//        DatabaseHelper db = DatabaseHelper.getInstance(context);
//        HashMap<String, User> map = db.makeUserHashMap();
//        db.closeDB();
//        return map;
//    }
// --Commented out by Inspection STOP (4/8/18 10:42 PM)

    /**
     * Getter for number of login attempts
     * @return loginAttempts
     */
    public int getLoginAttempts() { return loginAttempts; }

    /**
     * Setter for loginAttempts
     */
    public void setLoginAttempts() { loginAttempts = 0; }

    /**
     * Method to record failed login attempts
     * @return wait time for failed login
     */
    public String failedLogin() {
        loginAttempts++;
        if (loginAttempts == 3) {
            CountDownTimer timer = new CountDownTimer(300000, 1000) {
                @Override
                public void onTick(long time) {
                    secs = (int) (time / 1000);
                    mins = secs / 60;
                    secs = secs % 60;
                }

                @Override
                public void onFinish() {
                    loginAttempts = 0;
                }
            }.start();
        }
        return "" + mins + " minutes and " + secs + " seconds.";
    }

    /**
     * Return new list of filtered results
     * @param query the search we are looking for
     * @param context the application context
     * @return the new filtered list
     */
    public Map<String, Shelter> getFilteredResults(CharSequence query, Context context) {
        Map<String, Shelter> filteredResults = new HashMap<>();
        Iterable<Shelter> shelters = new ArrayList<>(getShelterList(context).values());
        for (Shelter shelter : shelters) {
            String restrictions = shelter.getRestrictions();
            if (("Families with Newborns".equals(query)
                    && restrictions.toLowerCase().contains("newborns"))
                    || ("Children".equals(query)
                    && "Children".equals(restrictions))
                    || ("Young Adults".equals(query)
                    && restrictions.toLowerCase().contains("young adult"))
                    || ("Any".equals(query))) {
                filteredResults.put(shelter.getName(), shelter);
            }
            if (("Female".equals(query) && !restrictions.contains("Men"))
                    || ("Male".equals(query) && !restrictions.contains("Women"))
                    || ("Any".equals(query))) {
                filteredResults.put(shelter.getName(), shelter);
            }
            if (shelter.getName().contains(query)) {
                filteredResults.put(shelter.getName(), shelter);
            }
        }
        return filteredResults;
    }

    /**
     * Determine if the user is checked in
     * @param context application context
     * @return whether user is checked in
     */
    public boolean isUserCheckedIn(Context context) {
        DatabaseHelper db = DatabaseHelper.getInstance(context);
        User user = db.fetchSpecificUserByID(_currentUser.getId());
        String location = null;
        if (user != null) {
            location = user.getLocationBedClaimed();
        }
        db.closeDB();
        return location != null;
    }

    /**
     * Allow the user to check into a location
     * @param numBeds number of beds to check in
     * @param shelterName name of shelter check in at
     * @param context application context
     */
    public void checkIn(int numBeds, String shelterName, Context context) {
        if((shelterName == null) || (context == null)) {
            return;
        }
        DatabaseHelper db = DatabaseHelper.getInstance(context);
        _currentUser.setNumberBedClaimed(numBeds);
        _currentUser.setLocationBedClaimed(shelterName);
        Shelter shelter = db.fetchSpecificShelterByName(shelterName);
        if (shelter != null) {
            shelter.setVacancy(shelter.getVacancy() - numBeds);
            db.updateSHELTER(shelter);
            db.updateUSER(_currentUser);
        }
        db.closeDB();
    }

    /**
     * Check out of a shelter
     * @param context application context
     */
    public void checkOut(Context context) {
        if (context == null) {
            return;
        }
        DatabaseHelper db = DatabaseHelper.getInstance(context);
        Shelter shelter = db.fetchSpecificShelterByName(_currentUser.getLocationBedClaimed());
        if (shelter != null) {
            shelter.setVacancy(shelter.getVacancy() + _currentUser.getNumberBedClaimed());
            _currentUser.setNumberBedClaimed(0);
            _currentUser.setLocationBedClaimed(null);
            db.updateSHELTER(shelter);
            db.updateUSER(_currentUser);
        }
        db.closeDB();
    }
}
