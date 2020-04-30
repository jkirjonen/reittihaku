package com.reittiopasrest.reittiopas;

import org.javatuples.Pair;

import java.util.ArrayList;

class Reittihaku {
    String lahto;
    String loppu;
    ArrayList<Aikataulu> aikataulut;
    ArrayList<Pysakki> pysakit;
    ArrayList<Pair<String, Integer>> reitti = new ArrayList<>();


    public ArrayList<Pair<String, Integer>> reittihaku(String lahto, String loppu, ArrayList<Aikataulu> aikataulut, ArrayList<Pysakki> pysakit, ArrayList<String> visited) {
        boolean perilla = false;
        ArrayList<String> visited2 = new ArrayList<>();
        visited2 = visited;

        for(String s:visited2){
            if(s.equalsIgnoreCase(lahto))
                return reitti;
        }
        visited2.add(lahto);

        for (Pysakki pysakki : pysakit) {
            if (pysakki.getNimi().equalsIgnoreCase(lahto)) {
                if(pysakki.getMihin().size() == 0){
                    return reitti;
                }
                ArrayList<Pair<String, Integer>> mihin = pysakki.getMihin();
                Pair<String,Integer> nopein = mihin.get(0);
                int pienin = 100;

                for(Pair pair : mihin){
                    if (pair.getValue0().toString().equalsIgnoreCase(loppu)) {
                        reitti.add(pair);
                        perilla = true;
                        return reitti;
                    }else {

                        for (Aikataulu taulu : aikataulut) {
                            //System.out.println(taulu.getPysakki().getNimi());
                            if (taulu.getPysakki().getNimi().equalsIgnoreCase(pair.getValue0().toString())) {
                                for (Pair table : taulu.getAikataulu()) {
                                    if (table.getValue0().toString().equalsIgnoreCase(loppu)) {
                                        if (Integer.parseInt(table.getValue1().toString()) < pienin) {
                                            pienin = Integer.parseInt(table.getValue1().toString());
                                            nopein = pair;
                                            //System.out.println("testi " + table.getValue0().toString() + taulu.getPysakki().getNimi());

                                        }
                                    }

                                }
                            }
                        }
                    }

                }
                if(!perilla){
                    reitti.add(nopein);
                    reittihaku(nopein.getValue0(),loppu,aikataulut,pysakit,visited2);
                }

            }

        }
        return reitti;
    }
}
