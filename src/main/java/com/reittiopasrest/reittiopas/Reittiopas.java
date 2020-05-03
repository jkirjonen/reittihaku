package com.reittiopasrest.reittiopas;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;


public class Reittiopas {
    String lahto;
    String loppu;

    public String reittiopas(String lahto, String loppu) throws Exception {
        this.lahto = lahto;
        this.loppu = loppu;

        // Haetaan pysakki tiedot jsonista
        ObjectMapper mapper = new ObjectMapper();
        String json = new Jiisoni().jiisoni;
        JSONObject jsonObject = new JSONObject(json);
        JSONArray pysakitJson = jsonObject.getJSONArray("pysakit");
        JSONArray tietJson = jsonObject.getJSONArray("tiet");
        JSONObject linjastotJson = jsonObject.getJSONObject("linjastot");

        ArrayList<Pysakki> pysakit = new ArrayList<>();
        ArrayList<String> visited = new ArrayList<>();

        // Luodaan lista pysakki objekteja
        for (int i=0;i<pysakitJson.length();i++) {
            String nimi = pysakitJson.getString(i);
            Pysakki pysakki = new Pysakki(nimi,tietJson);
            pysakit.add(pysakki);
        }


        ArrayList<Aikataulu> aikataulut = new ArrayList<>();

        // luodaan aikataulut jokaiselle pysakille ja listataan ne
        for(Pysakki pysakki:pysakit){
            Aikataulu aikataulu = new Aikataulu();
            aikataulu.luoAikataulu(pysakki,pysakit);
            aikataulut.add(aikataulu);
        }

        ArrayList<Linja> linjat = new ArrayList<>();
        List<String> linjatemp = new ArrayList<>(linjastotJson.keySet());

        // Luodaan bussilinjastot molempiin suuntiin
        for(int i=0;i<2;i++) {
            String suunta = "M";
            if(i == 1){
                suunta = "T";
            }
            for (String vari : linjatemp) {
                ArrayList<Pysakki> linja = new ArrayList<>();
                List<Object> list = linjastotJson.getJSONArray(vari).toList();
                if(i == 1){
                    Collections.reverse(list);
                }
                for (Object obj : list) {
                    for (Pysakki stop : pysakit) {
                        if (obj.toString().equalsIgnoreCase(stop.getNimi())) {
                            linja.add(stop);
                        }
                    }
                }
                String nimi = vari + suunta;
                Linja linja1 = new Linja(nimi, linja);
                linjat.add(linja1);
            }
        }

        // Tehdään reittihaku annetuilla parametreillä lahto ja loppu käyttäen hyväksi luotuja aikatauluja
        Reittihaku reittihaku = new Reittihaku();
        ArrayList<Pair<String, Integer>> reitti = reittihaku.reittihaku(lahto,loppu,aikataulut,pysakit,visited);

        for(Pysakki pysakki:pysakit){
            if(pysakki.getNimi().equalsIgnoreCase(lahto)){
                reitti.add(0,new Pair<>(pysakki.getNimi(),0));
                break;
            }
        }

        ArrayList<Pair<String,String>> linjareitti = new ArrayList<>();

        // Sovitetaan saatu nopein reitti bussilinjoihin
        for(int pair=0;pair<reitti.size();pair++){
            linjalooppi:
            for(int linja=0;linja<linjat.size();linja++){
                for(int pysakki=0;pysakki<linjat.get(linja).getPysakit().size();pysakki++){
                    if(reitti.get(pair).getValue0().equalsIgnoreCase(String.valueOf(linjat.get(linja).getPysakit().get(pysakki)))){
                        if(pair < reitti.size()-1 && pysakki < linjat.get(linja).getPysakit().size()-1){
                            if(reitti.get(pair +1).getValue0().equalsIgnoreCase(String.valueOf(linjat.get(linja).getPysakit().get(pysakki +1)))){
                                Pair<String,String> reitille = new Pair<>(reitti.get(pair).getValue0(),linjat.get(linja).vari);
                                linjareitti.add(reitille);
                                if(pair == reitti.size() -2){
                                    Pair<String,String> viimeinen = new Pair<>(reitti.get(pair +1).getValue0(),linjat.get(linja).vari);
                                    linjareitti.add(viimeinen);
                                }
                                break linjalooppi;
                        }

                        }
                    }

                }

            }

        }

        JSONArray jsonArray = new JSONArray();

        // Luodaan saadusta bussilinja reitistä JSonArray poistaen linjojen suunta merkit
        for(Pair pair:linjareitti){
            JSONObject jsonObject1 = new JSONObject().put(pair.getValue0().toString(),pair.getValue1().toString().substring(0,pair.getValue1().toString().length() -1));
            jsonArray.put(jsonObject1);
        }

        return jsonArray.toString();




    }


}

