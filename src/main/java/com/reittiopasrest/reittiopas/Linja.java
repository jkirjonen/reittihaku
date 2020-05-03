package com.reittiopasrest.reittiopas;

import java.util.ArrayList;

/**
 * Linja sisältää värin ja linjalla sijaitsevat pysäkit
 */

public class Linja{
    String vari;
    ArrayList<Pysakki> pysakit;

    public Linja(String vari, ArrayList<Pysakki> pysakit) {
        this.vari = vari;
        this.pysakit = pysakit;
    }

    public String getVari() {
        return vari;
    }

    public void setVari(String vari) {
        this.vari = vari;
    }

    public ArrayList<Pysakki> getPysakit() {
        return pysakit;
    }

    public void setPysakit(ArrayList<Pysakki> pysakit) {
        this.pysakit = pysakit;
    }



}
