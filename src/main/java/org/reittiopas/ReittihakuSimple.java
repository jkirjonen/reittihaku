package org.reittiopas;

import org.javatuples.Pair;

import java.util.ArrayList;

class ReittihakuSimple {
    ArrayList<Pair<String,Integer>> reitti = new ArrayList<>();
    ArrayList<Pysakki> pysakit = new ArrayList<>();

    public ArrayList<Pair<String,Integer>> reittiHaku(String lahto, String loppu, ArrayList<Pysakki> pysakit) {
        this.pysakit = pysakit;
        haku(lahto,loppu);
        return reitti;
    }

    public void haku (String lahto, String loppu){
        boolean perilla = false;
        ArrayList<Pair<String, Integer>> mihin;

        for (Pysakki stop : pysakit) {
            if (stop.nimi.equalsIgnoreCase(lahto)) {
                mihin = stop.getMihin();
                Pair<String, Integer> nopein = mihin.get(0);

                for (Pair pair : mihin) {
                    if (pair.getValue0().toString().equalsIgnoreCase(loppu)) {
                        reitti.add(pair);
                        perilla = true;
                        break;
                        } else {
                            if (Integer.parseInt(pair.getValue1().toString()) < nopein.getValue1()) {
                                nopein = pair;
                            }
                        }
                    }
                if(!perilla){
                    reitti.add(nopein);
                    haku(nopein.getValue0(),loppu);
                }
            }
            }
        }

}
