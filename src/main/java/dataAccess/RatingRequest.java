package dataAccess;

/**
 * Parameter object to group rating-related parameters.
 */
public class RatingRequest {
    private final String email;
    private final int rating;
    private final Integer reservationCode;
    private final Class<?> userClass;
    private final boolean isDriver;

    public RatingRequest(String email, int rating, Integer reservationCode, Class<?> userClass, boolean isDriver) {
        this.email = email;
        this.rating = rating;
        this.reservationCode = reservationCode;
        this.userClass = userClass;
        this.isDriver = isDriver;
    }

    public String getEmail() {
        return email;
    }

    public int getRating() {
        return rating;
    }

    public Integer getReservationCode() {
        return reservationCode;
    }

    public Class<?> getUserClass() {
        return userClass;
    }

    public boolean isDriver() {
        return isDriver;
    }
}
