package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class UsernameIsExistentException extends Exception {
    private WrongResponse response = new WrongResponse(10100, "the username is existent.");

    public WrongResponse getResponse() {
        return response;
    }
}
