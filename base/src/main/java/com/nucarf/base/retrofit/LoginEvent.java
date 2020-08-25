package com.nucarf.base.retrofit;

public class LoginEvent {
    private Class c;
    public LoginEvent(Class c){
        this.c = c;
    }

    public Class getEventClass() {
        return c;
    }
}
