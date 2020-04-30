package com.reittiopasrest.reittiopas;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.core.util.IOUtils;
import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.util.*;


public class Reittiopas {
    String lahto;
    String loppu;

    public String reittiopas(String lahto, String loppu) throws Exception {
        this.lahto = lahto;
        this.loppu = loppu;

        ObjectMapper mapper = new ObjectMapper();
        String json = IOUtils.toString(new FileReader(new File("reittiopas.json")));
        JSONObject jsonObject = new JSONObject(json);
        JSONArray pysakitJson = jsonObject.getJSONArray("pysakit");
        JSONArray tietJson = jsonObject.getJSONArray("tiet");
        JSONObject linjastotJson = jsonObject.getJSONObject("linjastot");

        ArrayList<Pysakki> pysakit = new ArrayList<>();
        ArrayList<String> visited = new ArrayList<>();



        for (int i=0;i<pysakitJson.length();i++) {
            String nimi = pysakitJson.getString(i);
            Pysakki pysakki = new Pysakki(nimi,tietJson);
            pysakit.add(pysakki);
        }

        //System.out.println(pysakit);


        ArrayList<Aikataulu> aikataulut = new ArrayList<>();

        for(Pysakki pysakki:pysakit){
            Aikataulu aikataulu = new Aikataulu();
            aikataulu.luoAikataulu(pysakki,pysakit);
            aikataulut.add(aikataulu);
        }

        ArrayList<Linja> linjat = new ArrayList<>();
        List<String> linjatemp = new ArrayList<>(linjastotJson.keySet());


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

        //linjat.forEach(e -> System.out.println(e.getVari() + " Pysakit: " + e.getPysakit().toString()));

        //aikataulut.forEach(e -> System.out.println(e.aikataulu.toString()));

        Reittihaku reittihaku = new Reittihaku();
        ArrayList<Pair<String, Integer>> reitti = reittihaku.reittihaku(lahto,loppu,aikataulut,pysakit,visited);

        for(Pysakki pysakki:pysakit){
            if(pysakki.getNimi().equalsIgnoreCase(lahto)){
                reitti.add(0,new Pair<>(pysakki.getNimi(),0));
                break;
            }
        }

        //reitti.forEach(e -> System.out.print(" -> " + e.getValue0()));;

        ArrayList<Pair<String,String>> linjareitti = new ArrayList<>();

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

        //System.out.println(linjareitti.size());

        //linjareitti.forEach(e -> System.out.print(e.getValue1() + " " + e.getValue0() + " -> "));


        JSONArray jsonArray = new JSONArray();

        for(Pair pair:linjareitti){
            JSONObject jsonObject1 = new JSONObject().put(pair.getValue0().toString(),pair.getValue1().toString().substring(0,pair.getValue1().toString().length() -1));
            jsonArray.put(jsonObject1);
        }

        //System.out.println(jsonArray);

        return jsonArray.toString();




    }


}

