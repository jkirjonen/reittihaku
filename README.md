# Reittiopas REST

Julkaisee REST rajapinnan liittyen Solidabiksen koodihaaste tehtävään. https://koodihaaste.solidabis.com/

## Toiminnallisuus

Aplikaatio parsii ensin annetusta jsonista pysäkkitiedot, tiet ja linjastot ja tekee jokaisesta pysäkistä Pysakki objektin joka sisältää tiedot pysäkin nimestä, mihin pysäkiltä pääsee ja kuinka pitkä matka on kyseessä. Tämä tehdään jokaiselle pysäkille ja ne listataan. Tämän jälkeen lasketaan ns aikataulu jokaiselle pysäkille. Jokaisella pysäkillä on oma aikataulu jossa on nopein reitti jokaiselle pysäkille. Nämä aikataulut lasketaan käyttämällä rekursiivista metodia joka tekee seuraavan pysäkki valinnan Dikjstra tyyppisesti eli valitsee aina lyhyimmän reitin niin kauan kunnes kohde on löydetty.

Kun aikataulut on saatu laskettua tehdään varsinainen reittihaku käyttäen hyväksi aikatauluja. Aloitetaan annetulta lähtö pysäkiltä esim 'A' ja katsotaan mihin pysäkeille siltä pääsee. 'A' tapauksessa pääsemme 'C' ja 'B'. Sanotaan että määränpäämme olisi vaikka 'F' niin katsomme kohde pysäkkine aikatauluista että miltä pysäkiltä on lyhyin matka 'F':lle, tässä tapauksessa se on 'C'. Sitten siirrytään rekursiivisesti 'C':lle ja tehdään sama uudestaan niin kauan kunnes ollaan perillä. tällä mallilla väistetään Dikjstra tyyppisen algoritmin ongelma väärään suuntaan lähtemisestä.

Seuraavaksi impletoidaan linjastot. Linjastoista luodaan molempiin suuntiin oma linjansa. Sitten otetaan reittihaun tulos ja katsotaan mihin linjastoihin reitti sopii. Jos reitillä on kaksi samaa pysäkkiä peräkkäin kuin jollain linjastolla niin merkataan yksi pysäkki lopulliselle reitille ja tehdään sama seuraavalle reitin pysäkille.


## Yleistä
Projekti on Spring boot projekti joka on tehty Javalla valmis rest rajapinta löytyy osoitteesta.
https://reittiopas-1588425472525.azurewebsites.net/

Tehtävälle on myös web app projekti joka löytyy
https://github.com/jkirjonen/reittihaku-webApp

Paikallisen asennuksen asennus ohjeet:

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
