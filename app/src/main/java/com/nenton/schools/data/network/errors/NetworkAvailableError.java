package com.nenton.schools.data.network.errors;

/**
 * Created by serge on 03.01.2017.
 */
public class NetworkAvailableError extends Throwable {
    public NetworkAvailableError() {
        super("Интернет недоступен попробуйте позже");
    }
}
