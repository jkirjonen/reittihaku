# Reittiopas REST

Julkaisee REST rajapinnan liittyen Solidabiksen koodihaaste tehtävään. https://koodihaaste.solidabis.com/

Projekti on Spring boot projekti joka on tehty Javalla. 

Asennus ohjeet:

  1. Asenna Java -> https://openjdk.java.net/install/
  2. Asenna Maven -> https://maven.apache.org/install.html
  
Juuri kansiosta komenna 
    
    mvn clean install
    
    mvn spring-boot:run
    
jonka jälkeen voit tehdä kutsuja esim curlilla tai selaimella.

    curl -i http://localhost:8080/testi
    
    
## Rajapintakuvaus

Rajapintaan voi tehdä kutsuja seuraavasti:

    /lahto?lahto=a

Asettaa lähtö pysäkiksi A, kutsu vastaa 'lähto asetettu'.

    /loppu?loppu=f

Asettaa määränpääksi pysäkin F, kutsu vastaa 'loppu asetettu'.

    /reittihaku

Tekee reittihaun annetuilla parametreillä, kutsu palauttaa json array reitistä josta selviää nopein reitti pisteden välillä ja mitä linjaa tulisi käyttää. 
  
    [{"A":"vihreä"},{"C":"vihreä"},{"E":"keltainen"},{"F":"keltainen"}]
    

    /testi

Tekee testi haun jossa lahto ja loppu valmiiksi asetettuina. Palauttaa reitin json array muodossa


    /reittiopas.json

Palauttaa tehtävässä käytetyn datan
