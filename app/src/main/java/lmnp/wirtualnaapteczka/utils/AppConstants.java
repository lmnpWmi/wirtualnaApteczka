package lmnp.wirtualnaapteczka.utils;

/**
 * Constants shared in whole application.
 *
 * @author Sebastian Nowak
 * @createdAt 26.12.2017
 */
public final class AppConstants {
    /** Default settings. */
    public static final int FIRST_ITEM_INDEX = 0;
    public static final int RECENTLY_USED_MEDICINES_DEFAULT_AMOUNT = 5;
    public static final int MINIMUM_PASSWORD_LENGTH = 6;
    public static final boolean IN_DEVELOPER_MODE = true;

    /** For activity results. */
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_VOICE_INPUT_MEDICINE_NAME = 2;
    public static final int REQUEST_VOICE_INPUT_MEDICINE_NOTES = 3;
    public static final int GOOGLE_SIGN_IN = 4;
    public static final int PERMISSION_REQUEST = 5;

    public static final String ADD_MEDICINE_INVOKING_CLASS = "add_medicine_invoking_class";
    public static final String APP_SETTINGS = "app_settings";
    public static final String EXIT = "exit";
    public static final String GOOGLE_DOCS_READER_URL = "https://docs.google.com/viewer?url=";
    public static final String INVOKING_CLASS = "invoking_class";
    public static final String IS_FIRST_LAUNCH = "is_first_launch";
    public static final String MEDICINE = "medicine";
    public static final String MEDICINE_PHOTOS_DIR = "medicine_photos";
    public static final String MEDICINE_PHOTO_URI = "medicine_photo_uri";
    public static final String REGULATIONS_URL = "https://wirtualna-apteczka.herokuapp.com/resources/images/regulamin.pdf";
    public static final String URL_LINK = "url_link";

    /** For authentication. */
    public static final String EMAIL = "email";
    public static final String LOGGED_IN = "remember_me";
    public static final String LOGIN_TYPE = "login_type";
    public static final String PASSWORD = "password";

    private AppConstants() {
    }
}
