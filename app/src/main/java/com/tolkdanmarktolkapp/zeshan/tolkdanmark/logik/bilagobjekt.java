package com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik;


import java.io.Serializable;

/**
 * Created by Zeshan on 14-01-2016.
 */
public class bilagobjekt implements Serializable {

    private String id="",institution="", klientsnavn="", cpr="", sprog="",dato="", tidfra="", tidtil="", tolkebruger="",tolk="", fraktura="", email="", interpreter_email="", getreference_id="";

    public bilagobjekt(String id, String klientsnavn, String cpr, String sprog, String dato, String tidfra, String tidtil, String tolkebruger, String tolk, String fraktura, String email, String interpreter_email, String getreference_id, String institution) {
        this.id = id;
        this.klientsnavn = klientsnavn;
        this.cpr = cpr;
        this.sprog = sprog;
        this.dato = dato;
        this.tidfra = tidfra;
        this.tidtil = tidtil;
        this.tolkebruger = tolkebruger;
        this.tolk = tolk;
        this.fraktura = fraktura;
        this.email = email;
        this.interpreter_email = interpreter_email;
        this.getreference_id = getreference_id;
        this.institution = institution;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKlientsnavn() {
        return klientsnavn;
    }

    public String getgetreference_id() {
        return getreference_id;
    }

    public void setKlientsnavn(String klientsnavn) {
        this.klientsnavn = klientsnavn;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getSprog() {
        return sprog;
    }

    public void setSprog(String sprog) {
        this.sprog = sprog;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public String getTidfra() {
        return tidfra;
    }

    public void setTidfra(String tidfra) {
        this.tidfra = tidfra;
    }

    public String getTidtil() {
        return tidtil;
    }

    public void setTidtil(String tidtil) {
        this.tidtil = tidtil;
    }

    public String getTolkebruger() {
        return tolkebruger;
    }

    public void setTolkebruger(String tolkebruger) {
        this.tolkebruger = tolkebruger;
    }

    public String getTolk() {
        return tolk;
    }

    public void setTolk(String tolk) {
        this.tolk = tolk;
    }

    public String getFraktura() {
        return fraktura;
    }

    public void setFraktura(String fraktura) {
        this.fraktura = fraktura;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInterpreter_email() {
        return interpreter_email;
    }

    public void setInterpreter_email(String interpreter_email) {
        this.interpreter_email = interpreter_email;
    }
}
