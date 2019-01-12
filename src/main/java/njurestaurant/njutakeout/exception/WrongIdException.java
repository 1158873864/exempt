package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class WrongIdException extends Exception {
    private WrongResponse response = new WrongResponse(10160, "wrong id.");

    public WrongResponse getResponse() {
        return response;
    }
}
