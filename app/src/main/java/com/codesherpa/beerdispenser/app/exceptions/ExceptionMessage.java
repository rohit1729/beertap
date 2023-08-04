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
    public static final String PROMOTER_EARNINGS_500 = "Error fetching attendee ";

    // Serving controller
    public static final String SERVING_GET_500 = "Error fetching serving ";
    public static final String SERVING_POST_500 = "Error deleting serving ";
    public static final String SERVING_PUT_500 = "Error updating serving ";
    public static final String SERVING_DELETE_500 = "Error deleting serving ";
    public static final String SERVING_LIST_500 = "Error fetching attendee ";

    
    // Validation error messages
    public static final String ATTENDEE_ID_INVALID = "AttendeeId should be greater than 1";
    public static final String BEER_ID_INVALID = "BeerId should be greater than 0";
    public static final String TAP_ID_INVALID = "TapId should be greater than 0";
    public static final String PROMOTER_ID_INVALID = "PromoterId should be greater than 0";
    public static final String SERVING_ID_INVALID = "ServingId should be greater than 0";
    public static final String TAP_FLOW_PER_LITRE_INVALID = "flow per litre should be greater than 0";
    public static final String BEER_PRICE_PER_LITRE_INVALID = "price per litre should be greater than 0";

    public static final String ATTENDEE_NAME_NULL = "Attendee name should not be null";
    public static final String BEER_NAME_NULL = "Beer name should not be null";
    public static final String TAP_NAME_NULL = "Tap name should not be null";
    public static final String PROMOTER_NAME_NULL = "Promoter name should not be null";
    public static final String ADMIN_NAME_NULL = "Admin name should not be null";

    public static final String ATTENDEE_NAME_BLANK = "Attendee name should not be blank";
    public static final String BEER_NAME_BLANK = "Beer name should not be blank";
    public static final String TAP_NAME_BLANK = "Tap name should not be blank";
    public static final String PROMOTER_NAME_BLANK = "Promoter name should not be blank";
    public static final String ADMIN_NAME_BLANK = "Admin name should not be blank";

    public static final String TAP_NOT_FOUND = "Tap not found for tapId ";
    public static final String ATTENDEE_NOT_FOUND = "Attendee not found for attendeeId ";
    public static final String SERVING_NOT_FOUND = "Serving not found for servingId ";
    public static final String BEER_NOT_FOUND = "Beer not found for beerId ";
    public static final String PROMOTER_NOT_FOUND = "Promoter not found for promoterId ";

    public static final String TAP_NOT_SET = "Tap is not set for tapId ";
    public static final String SERVING_END_TIME_NULL = "Serving end time should not be null";

}
