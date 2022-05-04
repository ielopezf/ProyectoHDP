package com.Grupo02HDP.GestionNotas.utils;

public class SQLException {

    public static String getException(String S){
        String message;
        if(S.equals("org.hibernate.exception.ConstraintViolationException: could not execute statement")){
            message = "Oops! Looks like the data that you are trying to save is already saved.";
        }else{
            message = S;
        }
        return message;
    }
}
