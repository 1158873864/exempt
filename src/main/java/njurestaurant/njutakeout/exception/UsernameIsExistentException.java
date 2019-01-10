package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.WrongResponse;

public class UsernameIsExistentException extends Exception {
    private WrongResponse response = new WrongResponse(10100, "Cannot found the username.");

    public WrongResponse getResponse() {
        return response;
    }
}
