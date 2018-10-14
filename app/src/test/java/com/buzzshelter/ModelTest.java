package com.buzzshelter;

import android.content.Context;

import com.buzzshelter.Model.DatabaseHelper;
import com.buzzshelter.Model.Model;
import com.buzzshelter.Model.User;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Test Class for Model
 */
public class ModelTest {

    private Model testModel;

    private DatabaseHelper db;
    private Context context;

    private String testUsername;
    private String testPassword;
    private User testUser;

    /**
     * Sets up the model for testing.
     */
    @Before
    public void setUp() {
        testModel = Model.getInstance();
        context = mock(Context.class);
        db = mock(DatabaseHelper.class);
        DatabaseHelper.setInstance(db);
        testUsername = "Bob";
        testPassword = "123";
        testUser = mock(User.class);
    }

    /**
     * Tests validateUser method when all parameters are null.
     * Duy Huynh
     */
    @Test
    public void testValidateUser_NullParams() {
        boolean validated = testModel.validateUser(null, null, null);
        assertEquals(false, validated);
    }

    /**
     * Tests validateUser method when the specified user cannot be found.
     * Duy Huynh
     */
    @Test
    public void testValidateUser_NoUserFound() {
        when(db.fetchSpecificUserByID(testUsername)).thenReturn(null);
        boolean validated = testModel.validateUser(testUsername, testPassword, context);
        assertEquals(false, validated);
    }

    /**
     * Tests validateUser method when the password entered is incorrect.
     * Duy Huynh
     */
    @Test
    public void testValidateUser_IncorrectPassword() {
        when(db.fetchSpecificUserByID(testUsername)).thenReturn(testUser);
        when(testUser.getPassword()).thenReturn("");

        boolean validated = testModel.validateUser(testUsername, testPassword, context);
        assertEquals(false, validated);
    }

    /**
     * Tests validateUser method when the password entered is correct.
     * Duy Huynh
     */
    @Test
    public void testValidateUser_CorrectPassword() {
        when(db.fetchSpecificUserByID(testUsername)).thenReturn(testUser);
        when(testUser.getPassword()).thenReturn(testPassword);

        boolean validated = testModel.validateUser(testUsername, testPassword, context);
        assertEquals(true, validated);
    }

}
