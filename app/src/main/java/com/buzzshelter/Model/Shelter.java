package com.buzzshelter.Model;

/**
 * Shelter Class
 */

public class Shelter {
    private final String name;
    private final String capacity;
    private final String restrictions;
    private final String longitude;
    private final String latitude;
    private final String address;
    private final String phoneNumber;
    private int vacancy;

    /**
     * Constructor for shelter
     * @param name name of shelter
     * @param capacity capacity of shelter
     * @param restrictions restrictions of shelter
     * @param longitude longitude of shelter
     * @param latitude latitude of shelter
     * @param address address of shelter
     * @param phoneNumber phoneNumber of shelter
     */
    public Shelter(String name, String capacity, String restrictions, String longitude,
                   String latitude, String address, String phoneNumber) {
        this.name = name;
        this.capacity = capacity;
        this.restrictions = restrictions;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.vacancy = 0;
        if (!"".equals(capacity)) {
            String[] capacities = capacity.split(",");
            for (String capacity1 : capacities) {
                String numVacancy = capacity1.replaceAll("\\D+", "");
                this.vacancy += Integer.parseInt(numVacancy);
            }
        }
    }

    /**
     * String representation of shelter
     * @return shelter string
     */
    public String toString() {
        return name + " - " + restrictions;
    }

    /**
     * Getter for name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for capacity
     * @return capacity
     */
    public String getCapacity() {
        return capacity;
    }

    /**
     * Getter for restrictions
     * @return restrictions
     */
    public String getRestrictions() {
        return restrictions;
    }

    /**
     * Getter for longitude
     * @return longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Getter for latitude
     * @return latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Getter for address
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Getter for phoneNumber
     * @return phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Getter for vacancy
     * @return vacancy
     */
    public int getVacancy() {
        return this.vacancy;
    }

    /**
     * Setter for vacacny
     * @param vacancy the new vacancy
     */
    public void setVacancy(int vacancy) {
        this.vacancy = vacancy;
    }

    /**
     * Verifies if two shelters are equivalent
     * @param object the shelter to check against
     * @return whether the shelter are equivalent
     */
    @Override
    public boolean equals(Object object) {
        return !((object == null)
                || !(object instanceof Shelter))
                && this.getName().equals(((Shelter) object).getName());
    }
}
