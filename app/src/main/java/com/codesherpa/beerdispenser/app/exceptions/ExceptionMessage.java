package com.codesherpa.beerdispenser.app.exceptions;

public class ExceptionMessage {
    public static final String ATTENDEE_POST_500 = "Error creating attendee ";
    public static final String ATTENDEE_DELETE_500 = "Error deleting attendee ";
    public static final String ATTENDEE_GET_500 = "Error fetching attendee ";
    public static final String ATTENDEE_LIST_500 = "Error fetching attendee ";

    // Beer controller
    public static final String BEER_GET_500 = "Error fetching beer ";
    public static final String BEER_POST_500 = "Error creating beer ";
    public static final String BEER_DELETE_500 = "Error deleting beer ";
    public static final String BEER_LIST_500 = "Error fetching attendee ";

    // Tap controller  
    public static final String TAP_GET_500 = "Error fetching tap ";
    public static final String TAP_POST_500 = "Error creating tap ";
    public static final String TAP_DELETE_500 = "Error deleting tap ";
    public static final String TAP_LIST_500 = "Error fetching attendee ";

    // Promoter controller
    public static final String PROMOTER_GET_500 = "Error fetching promoter "; 
    public static final String PROMOTER_POST_500 = "Error creating promoter ";
    public static final String PROMOTER_DELETE_500 = "Error deleting promoter ";
    public static final String PROMOTER_LIST_500 = "Error fetching attendee ";

    // Serving controller
    public static final String SERVING_GET_500 = "Error fetching serving ";
    public static final String SERVING_POST_500 = "Error deleting serving ";
    public static final String SERVING_PUT_500 = "Error updating serving ";
    public static final String SERVING_DELETE_500 = "Error deleting serving ";
    public static final String SERVING_LIST_500 = "Error fetching attendee ";

    
    // Validation error messages
    public static final String ATTENDEE_ID_INVALID = "AttendeeId should be greater than 1";
    public static final String ATTENDEE_NAME_NULL = "Attendee name should not be null";
}
