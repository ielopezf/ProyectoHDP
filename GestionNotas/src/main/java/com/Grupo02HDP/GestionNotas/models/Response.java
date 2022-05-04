package com.Grupo02HDP.GestionNotas.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Response {

    private boolean status;
    private boolean error;
    private String message;
    private String exception;
    private String token;
    private String url;
    private List<Object> dataset;

    public Response() {
        this.status = false;
        this.error = false;
        this.message = null;
        this.exception = null;
        this.url = null;
        this.dataset = new ArrayList<Object>();
    }

}
