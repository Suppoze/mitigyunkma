#Mit igyunk ma?
Egy app, ami megmondja az alkohol hatékonyságát a százalék, ár és űrtartalom alapján. Minél kisebb indexet kapunk eredményül, annál hatékonyabb az általunk vizsgált szesz. Az index mértékegysége ugyanis Ft/mol.

##Funkciók
 - Vidd fel az ital alkoholszázalékát, az árát, és az űrtartalmát, és számold ki az indexet
 - Ital mentése, szerkesztése és törlése
 - Mentett italok megtekintése mentés ideje és hatékonyság szerint rendezve

###Tervezett funkciók
 - Lista átalakítása: lehetőség a rendezésre minden szempont alapján
 - Lista index szám színezése hatékonyság alapján
 - Keresés?

##Alkalmazott technológiák
Ez az alkalmazás a teljes mértékben [Kotlin](https://kotlinlang.org/) nyelven íródott, sok helyen kihasználva az [Anko](https://github.com/Kotlin/anko) függvénykönyvár által nyújtott lehetőségeket. Az italokat [Realm](https://realm.io/) adatbázis tárolja az eszközön.

A kódban példát találhatsz a következők Kotlinban való megvalósítására:
 - Custom view-k
 - Recycler view adapter Realm resultset forrással
 - Egyéb mások, úgy Kotlin eleve.

##Képernyőképek
![](http://i.imgur.com/QY2smpH.png?1)
![](http://i.imgur.com/s2gkCEw.png?1)
![](http://i.imgur.com/nzzQrQk.png?1)
