package com.buzzshelter.Model;

import android.support.annotation.Nullable;

/**
 * User Class
 */

public class User {
    private final String _id;
    private final String _name;
    private final String _password;
    private final AccountType _accountType;
    private int _numberBedClaimed;
    @Nullable
    private String _locationBedClaimed;


    /**
     * Constructor for user
     * @param id the id of the user
     * @param name the name of the user
     * @param password the password for the user
     * @param accountType the account type of the user
     */
    public User(String id, String name, String password, AccountType accountType) {
        _id = id;
        _name = name;
        _password = password;
        _accountType = accountType;
        _numberBedClaimed = 0;
        _locationBedClaimed = null;
    }


    /**
     * Getter for id
     * @return id
     */
    public String getId() {
        return _id;
    }

    /**
     * Getter for name
     * @return name
     */
    public String getName(){
        return _name;
    }

    /**
     * Getter for password
     * @return password
     */
    public String getPassword() {
        return _password;
    }

    /**
     * Getter for accountType
     * @return accountType
     */
    public AccountType getAccountType() {
        return _accountType;
    }

    /**
     * Getter for numberBedClaimed
     * @return numberBedClaimed
     */
    public int getNumberBedClaimed() {
        return _numberBedClaimed;
    }

    /**
     * Getter for locationBedClaimed
     * @return locationBedClaimed
     */
    public String getLocationBedClaimed() {
        return _locationBedClaimed;
    }

    /**
     * Setter for numberBedClaimed
     * @param beds the number of beds
     */
    public void setNumberBedClaimed(int beds) {
        _numberBedClaimed = beds;
    }

    /**
     * Setter for locationBedClaimed
     * @param shelterName shelter name
     */
    public void setLocationBedClaimed(String shelterName) {
        _locationBedClaimed = shelterName;
    }
}
