package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class IsExistentException extends Exception{
    private WrongResponse response = new WrongResponse(10110, "Name is existent.");

    public WrongResponse getResponse() {
        return response;
    }
}
