package com.nenton.schools.data.network.errors;

/**
 * Created by serge on 08.04.2017.
 */

public class ForbiddenApiError extends ApiError{
    public ForbiddenApiError(){
        super("Неверный логин или пароль");
    }
}
