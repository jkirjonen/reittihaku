package com.reittiopasrest.reittiopas;

import org.javatuples.Pair;

import java.util.ArrayList;

/**
 * Aikatalussa luodaan pysakille aikataulu joka sisältää nopeimman reitin jokaiselle pysäkille.
 */
class Aikataulu {
    Pysakki pysakki;
    ArrayList<Pair<String, Integer>> aikataulu;
    ArrayList<Pysakki> pysakit;

    public Pysakki getPysakki() {
        return pysakki;
    }

    public void setPysakki(Pysakki pysakki) {
        this.pysakki = pysakki;
    }

    public ArrayList<Pair<String, Integer>> getAikataulu() {
        return aikataulu;
    }

    public void setAikataulu(ArrayList<Pair<String, Integer>> aikataulu) {
        this.aikataulu = aikataulu;
    }

    public ArrayList<Pysakki> getPysakit() {
        return pysakit;
    }

    public void setPysakit(ArrayList<Pysakki> pysakit) {
        this.pysakit = pysakit;
    }

    // luodaan valmis aikataulu jossa jokaisen pysäkin matka arvo on 1000
    public void luoAikataulu(Pysakki pysakki, ArrayList<Pysakki> pysakit ) {
        this.pysakki = pysakki;
        this.pysakit = pysakit;
        ArrayList<Pair<String, Integer>> aikataulu = new ArrayList<>();

        for (Pysakki py : pysakit) {

            if (!py.getNimi().equalsIgnoreCase(pysakki.getNimi())) {
                Pair<String, Integer> stop = new Pair<>(py.getNimi(), 1000);
                aikataulu.add(stop);
            }
        }

        // tehdään matkahaku jokaselle pysäkille ja jos matkahaun tulos on lyhyempi kuin olemassa oleva asetetaan se
        for (int i = 0; i < aikataulu.size(); i++) {
            ArrayList<String> visited = new ArrayList<>();
            Integer aika = matkahaku(pysakki.getNimi(), aikataulu.get(i).getValue0(),visited);
            Integer comp = Integer.parseInt(aikataulu.get(i).getValue1().toString());

            if (aika.intValue() < comp.intValue() && aika.intValue() > 0) {
                Pair<String,Integer> pair = new Pair<>(aikataulu.get(i).getValue0(),aika);
                aikataulu.set(i,pair);
            }
        }

        this.aikataulu = aikataulu;
    }

    // Rekursiivinen metodi joka käyttää Dikjstra tyyppistä valintaa
    public Integer matkahaku (String lahto, String loppu, ArrayList<String> visited){
        boolean perilla = false;
        Integer aika = 0;
        ArrayList<Pair<String, Integer>> mihin = new ArrayList<>();
        ArrayList<String> visited2 = new ArrayList<>();
        visited2 = visited;

        for(String s:visited2){
            if(s.equalsIgnoreCase(lahto))
                return 0;
        }
        visited2.add(lahto);

        Integer vert = 100;

        for (Pysakki stop : pysakit) {
            if (stop.nimi.equalsIgnoreCase(lahto)) {
                mihin = stop.getMihin();
                if(mihin.size() == 0){
                    break;
                }
                Pair<String, Integer> nopein = mihin.get(0);

                for (Pair pair : mihin) {
                    if (pair.getValue0().toString().equalsIgnoreCase(loppu)) {
                        aika = aika + Integer.parseInt(pair.getValue1().toString());
                        if(aika < vert){
                            vert = aika;
                            perilla = true;
                            return aika;
                        }


                    } else {
                        if (Integer.parseInt(pair.getValue1().toString()) < nopein.getValue1()) {
                            nopein = pair;
                        }
                    }
                }
                if(!perilla){
                    aika = aika + nopein.getValue1();
                    aika = aika + matkahaku(nopein.getValue0(),loppu,visited2);

                }
            }
        }

        return aika;
    }

}
