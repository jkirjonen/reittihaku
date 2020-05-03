package com.reittiopasrest.reittiopas;

import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Pysäkki objekti sisältää pysäkin nimen ja listan pysäkeistä mihin sieltä pääsee, myös matka-ajat mukana
 */

class Pysakki {
    String nimi;
    ArrayList<Pair<String, Integer>> mihin;
    JSONArray array;

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public ArrayList<Pair<String, Integer>> getMihin() { return mihin; }

    public void setMihin(ArrayList<Pair<String, Integer>> mihin) { this.mihin = mihin; }

    public Pysakki(String nimi, JSONArray array) {
        this.nimi = nimi;
        ArrayList<Pair<String,Integer>> mihin = new ArrayList<>();
        for (int i=0;i<array.length();i++) {
            JSONObject obj = array.getJSONObject(i);
            if (obj.getString("mista").equalsIgnoreCase(nimi)) {
                Pair<String, Integer> pair = new Pair<>(obj.getString("mihin"), obj.getInt("kesto"));
                mihin.add(pair);
            }

        }
        this.mihin = mihin;


    }
    public String toString(){
        return nimi;
    }

}
