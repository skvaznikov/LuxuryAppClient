package de.akvilonsoft.luxuryapp;

import android.content.Context;

/**
 * Created by Manager on 18.04.2016.
 */
public class Coupon {

    private Integer id;
    private String name;
    private String beschreibung;

    public String getBeschreibung_lang() {
        return beschreibung_lang;
    }

    public void setBeschreibung_lang(String beschreibung_lang) {
        this.beschreibung_lang = beschreibung_lang;
    }

    private String beschreibung_lang;
    private String additional;

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }



    public Coupon() {
        super();
    }

    public Coupon(String name, String beschreibung) {
        super();
        this.name = name;
        this.beschreibung = beschreibung;
    }

    public Coupon(Integer id,String name, String beschreibung, String beschreibung_lang) {
        super();
        this.id = id;
        this.name = name;
        this.beschreibung = beschreibung;
        this.beschreibung_lang = beschreibung_lang;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public void setName(String name) {
        this.name = name;
    }



}
