package com.tolkdanmarktolkapp.zeshan.tolkdanmark.logik;


import java.io.Serializable;
import java.io.StringReader;

public class regionbilagobjekt implements Serializable {

    private String id = "", klientsnavn = "", cpr = "", sprog = "", dato = "", tidfra = "", tidtil = "", tolk = "", getreference_id = "", adresse = "", postnr = "", by = "", tolkcpr = "", forbindelse = "", omfang = "", laegeeanr = "", eva1 = "", eva2 = "", eva3 = "", eva4 = "", evaComment = "", enCodedImage = "";

    public regionbilagobjekt(String id, String klientsnavn, String cpr, String sprog, String dato, String tidfra, String tidtil, String tolk, String getreference_id, String adresse, String postnr, String by, String tolkcpr, String forbindelse, String omfang, String laegeeanr, String eva1, String eva2, String eva3, String eva4, String evaComment, String enCodedImage) {
        this.id = id;
        this.klientsnavn = klientsnavn;
        this.cpr = cpr;
        this.sprog = sprog;
        this.dato = dato;
        this.tidfra = tidfra;
        this.tidtil = tidtil;
        this.tolk = tolk;
        this.getreference_id = getreference_id;
        this.adresse = adresse;
        this.postnr = postnr;
        this.by = by;
        this.tolkcpr = tolkcpr;
        this.forbindelse = forbindelse;
        this.omfang = omfang;
        this.laegeeanr = laegeeanr;
        this.eva1 = eva1;
        this.eva2 = eva2;
        this.eva3 = eva3;
        this.eva4 = eva4;
        this.evaComment = evaComment;
        this.enCodedImage = enCodedImage;
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

    public String getTolk() {
        return tolk;
    }

    public void setTolk(String tolk) {
        this.tolk = tolk;
    }

    public String getGetreference_id() {
        return getreference_id;
    }

    public void setGetreference_id(String getreference_id) {
        this.getreference_id = getreference_id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPostnr() {
        return postnr;
    }

    public void setPostnr(String postnr) {
        this.postnr = postnr;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getTolkcpr() {
        return tolkcpr;
    }

    public void setTolkcpr(String tolkcpr) {
        this.tolkcpr = tolkcpr;
    }

    public String getForbindelse() {
        return forbindelse;
    }

    public void setForbindelse(String forbindelse) {
        this.forbindelse = forbindelse;
    }

    public String getOmfang() {
        return omfang;
    }

    public void setOmfang(String omfang) {
        this.omfang = omfang;
    }

    public String getLaegeeanr() {
        return laegeeanr;
    }

    public void setLaegeeanr(String laegeeanr) {
        this.laegeeanr = laegeeanr;
    }

    public String getEva1() {
        return eva1;
    }

    public void setEva1(String eva1) {
        this.eva1 = eva1;
    }

    public String getEva2() {
        return eva2;
    }

    public void setEva2(String eva2) {
        this.eva2 = eva2;
    }

    public String getEva3() {
        return eva3;
    }

    public void setEva3(String eva3) {
        this.eva3 = eva3;
    }

    public String getEva4() {
        return eva4;
    }

    public void setEva4(String eva4) {
        this.eva4 = eva4;
    }

    public String getEvaComment() {
        return evaComment;
    }

    public void setEvaComment(String evaComment) {
        this.evaComment = evaComment;
    }

    public String getEnCodedImage(){
        return enCodedImage;
    }

    public void setEnCodedImage(String enCodedImage){
        this.enCodedImage = enCodedImage;
    }
}
