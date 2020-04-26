package org.reittiopas;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.core.util.IOUtils;
import org.javatuples.Pair;
import org.json.*;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class App {

    public static void main(String[] args) throws Exception {
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

        System.out.println(pysakit);


        ArrayList<Aikataulu> aikataulut = new ArrayList<>();

        for(Pysakki pysakki:pysakit){
            Aikataulu aikataulu = new Aikataulu();
            aikataulu.luoAikataulu(pysakki,pysakit);
            aikataulut.add(aikataulu);
        }

        //aikataulut.forEach(e -> System.out.println(e.aikataulu.toString()));

        String lahto = "A";
        String loppu = "K";

        Reittihaku reittihaku = new Reittihaku();
        ArrayList<Pair<String, Integer>> reitti = reittihaku.reittihaku(lahto,loppu,aikataulut,pysakit,visited);

        System.out.print(lahto);reitti.forEach(e -> System.out.print(" -> " + e.getValue0()));;



    }


}

class Reittihaku {
    String lahto;
    String loppu;
    ArrayList<Aikataulu> aikataulut;
    ArrayList<Pysakki> pysakit;
    ArrayList<Pair<String, Integer>> reitti = new ArrayList<>();


    public ArrayList<Pair<String, Integer>> reittihaku(String lahto, String loppu, ArrayList<Aikataulu> aikataulut, ArrayList<Pysakki> pysakit,ArrayList<String> visited) {
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

