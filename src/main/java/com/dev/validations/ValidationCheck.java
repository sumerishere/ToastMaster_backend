package main.java.com.dev.validations;

public interface ValidationCheck {

  //email check
  public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

  // Validates names with only alphabetic characters (uppercase, lowercase), allows spaces for multiple names, and hyphens for compound names.
  public static final String NAME_REGEX = "^[A-Za-zÀ-ÖØ-öø-ÿ\\-\\s]{1,50}$";

  // Validates a typical address with alphanumeric characters, spaces, and common punctuation (dots, commas, dashes, etc.). Can be customized for more complex formats.
  public static final String ADDRESS_REGEX = "^[A-Za-z0-9À-ÖØ-öø-ÿ\\-.,'\\s]{1,100}$";

  // Validates a mobile number, allowing 7 to 15 digits. Optionally, can include country code starting with '+'.
  public static final String MOBILE_NUMBER_REGEX = "^\\+?[0-9]{7,15}$";

  // Validates a password with at least one lowercase letter, one uppercase letter, one digit, one special character, and minimum length of 8 characters.
  public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
  
} 