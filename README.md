
# Kinokülastus

## Kirjeldus
Rakendus hoiab andmebaasis infot Kinoseansside, neile vastavate filmide, külastuste ja külastajate kohta. Back-endi saab
teha GET päringu kõikide tulevaste seansside tagastamiseks (loogika KinokavaService) ja Külastaja ID järgi soovituse saamiseks (SoovitusService).
Soovitus tehakse selle põhjal, millist žanrit on külastaja varem kõige rohkem vaadanud ja valitakse tulevastest seanssidese
sama žanriga kõige varasem. Front-endis avalehel saab filtreerida tulevasi filmiseansse žanri, vanusepiirangu, alguaskellaaja
ja -kuupäeva järgi. Filmid kuvatakse tabelina. Samuti on olemas võimalus lasta filmi soovitada, selleks tuleb sisestada külastaja
ID (näidisandmete korral ID = 1 või 2) ning seejärel kuvatakse soovitus backend GET päringu abil. Iga seansi järel on nupp "Vali", mis suunab kinoplaanile, selles
vaates saab sisestada piletite arvu ning algoritm kuvab skeemil soovitatud istekohad jälgides, et kohad oleks kõrvuti ja võimalikult kinosaali keskel.
Näidisandmed luuakse andmebaasi DataLoader klassis backendis.

## Ajakulu
Rakenduse loomisele on kulunud suurusjärk 5 tundi, mistõttu esineb puudusi nt erindite töötlemisel, soovitusalgoritmides jne. 
Filmisoovitamine võiks juhul kui kasutaja vaadatuima žanri seansse ei ole tulekul soovitada kasutaja vaadatavuselt järgmise žanri seanssi jne.
Istekohtade soovitamine alati ei tagasta kõige rohkem keskmiseid kohti istereas ja eeldab, et kõik kohad paevad ühes reas olema.
Hetkel kinosaaliga seonduv loogika, sh hõivatud istekohtade genrereerimine, istekoha soovitusalgoritm on lahendatud front-endis, 
edasi arendamisel tuleks viia back endi ja luua andmebaasis tabel kinosaali jaoks, kus oleks iga koha kohta märgitud selle hõivatus jne.
Samuti tuleks kood katta automaattestidega. Soovituse kuvamine tuleks ID sisestamise asemel lahendada nt kasutaja autentimisega,
sest kasutaja reeglina ei tea oma ID-d andmebaasis.

## Tehnoloogiad
- Spring-boot ja Maven back endis
- Vue.js front end
- Rest päringud front end ja back end vahel
- Java 21
- Hsqldb andmebaas

# Back-end üles seadmine
- Veendu, et JDK on määratud Java 21
- Liigu vajadusel projekti juurkausta
```
cd /kinoKylastus
```
- käivita
```
  mvn spring-boot:run
```

# Front-end üles seadmine 
### liigume front end kausta
```
cd /frontend-kinokylastus
```
### npm installimine
```
npm install
```
### Rakenduse käivitamine (arendusrežiim)
```
npm run serve
```

### rakendus on kättesaadav http://localhost:8080/

## Küsimused
Küsimuste või probleemide korral võib julgesti võtta ühendust mattias.kimst@mail.ee

