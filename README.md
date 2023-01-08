# Algoritmizálás (15 pont)
Készíts egy metódust, ami a paraméterként kapott listában lévő egész számokból csak az átlaguknál nagyobbakat teszi
bele az eredménylistába. A metódus neve legyen `aboveAverage`.

# Komplex OOP feladat (50 pont)
Ebben a feladatban egy gépkölcsönző cég nyilvántartását kell megvalósítani. Hogy jelen inflációs időszakban egyszerű 
legyen árat emelni, a kölcsönzés óránkénti alapdíját a `Settings` osztályban tároljuk konstansként, a kölcsönözhető
gépek attribútumaként pedig egy szorzószámot, ami megadja, hogy az alapdíj hányszorosába kerül a gép kölcsönzése.

Hozd létre a `RentableTool` ősosztályt, amit ne lehessen példányosítani. Konstruktorában az azonosítóját, a nevét és a 
kölcsönzési díj szorzóját várja. Ezen kívül van LocalDateTime típusú `rentFrom` és `rentTo` attribútuma. Ezek a 
kölcsönzés kezdetét és várható végét jelölik. Van még egy `totalIncome` attribútuma, amely a gép által eddig
bevételként szereplő összeget tárolja. Hozz létre az attribútumokhoz gettereket is!

A `rent` metódus beállítja a `rentFrom` és `rentTo` attribútumokat. A metódust kétféle paraméterlistával lehessen hívni:
ha két időértéket adunk meg, akkor az első a kölcsönzési időpont, a második a visszahozás várható időpontja. Ha csak 
egyet, akkor a kölcsönzés időpontja legyen az aktuális időpont, a visszahozás várható ideje pedig a paraméterül kapott.
Amennyiben a gép már kölcsönözve van, akkor a metódus dobjon `AlreadyRentedException`-t, amit az `IllegalStateException`
-ből származtatunk.

A `calculateRentalFee` metódus kiszámolja a kölcsönzés díját a következő szabályok szerint:
a kölcsönzés óradíj alapú, minden megkezdett óra egésznek számít,
az első három óra díja teljes díj,
az utána következő órákra a teljes díj 80%-a,
de a 25. órától már csak 50%-a.

A `giveBack` metódus a gép visszahozását jelenti. A kölcsönzés díját az aktuális időpont  alapján kalkulálva 
hozzáadja a `totalIncome` attribútumhoz, továbbá null-ra állítja a `rentFrom` és `rentTo` attribútumokat. Ha a gép nem 
volt kölcsönözve, akkor a metódus nem csinál semmit.

A `HandyTool` és a `BrandedHandyTool` osztályok a `RentableTool` osztályból származnak. Konstruktorukban csak az 
azonosítót és a nevet várják. A `HandyTool` osztály szorzószáma egységesen 1.0, a `BrandedHandyTool` osztályé
pedig 1.5. A `HardTool` osztály olyan nagyobb szerszámokat, eszközöket jelöl, amelyeket jellemzően hosszabb időtartamra
szoktak kölcsönözni. Konstruktorában a szülőosztály konstruktorának mindhárom paraméterét várja. A `calculateRentalFee`
metódust írjuk felül úgy, hogy a díj szimplán a megkezdett órák és a szorzószámmal kalkulált óradíj szorzata legyen, 
kedvezmények nélkül.

A `RentalCompany` osztály attribútuma egy `RentableTool` elemeket tartalmazó lista, a listát az `addTool` metódussal 
lehet feltölteni ill. bővíteni. A `findToolById` metódus azonosító alapján visszadja az adott objektum referenciáját. A
`listFreeTools` metódus visszaadja a jelen pillanatban kölcsönözhető gépeket tartalmazó listát (a "tool" objektumok 
eredeti referenciájával). A `calculateTotalIncome` metódus visszaadja a gépek által eddig bevételként szerzett összeget.
A `listExpiredToolsToFileAsCsv(Path path)` metódus a paraméterül kapott fájlba írja a lejárt kölcsönzéseket. Ha a fájl
már létezik, írja felül. A fájl első sorának tartalma: `id;name;rentTo`. A következő sorokban a lejárt kölcsönzések
azonosítója, neve és várható visszahozás (múltbeli) ideje szerepeljen, például:   
_id;name;rentTo_   
_A1;concrete mixer;2020-01-03T08:00_      
_A2;concrete mixer;2020-01-03T08:00_   


# Adatbáziskezelés (35 pont)
Ebben a feladatban levelezős feladatmegoldó verseny eredményeit fogjuk tárolni adatbázisban.
A versenyzők minden hónapban beküldenek az arra a hónapra kiadott tíz feladat közül egyet 
vagy többet. A beküldött feladatok pontozását a javítók adatbázisba töltik.

Készítsd el az `Rating`, `RatingRepository` és `RatingService` osztályokat! Hozd létre a kapcsolódó adattáblát 
és a migrációs állományokat is! Ebben a feladatban az adatbázis elérésre vonatkozó részt a tesztekben átírhatod.

A `Rating` osztály az adatbázis egy rekordját fogja reprezentálni. Minden rekordhoz tartozik egy rögzítéskor 
az SQL szerver által generált azonosító, a versenyző álneve, a hónap sorszáma (1-12, kivéve 7, 8), a feladat sorszáma 
(1-10) és a kapott pontszám (0-10). A 0 pontszám azt jelenti, hogy a beküldött feladatban nem volt értékelhető rész. 
Ha az adott feladat nem lett beküldve, azt a hozzá tartozó rekord hiánya jelzi. A `Rating` legyen példányosítható 
azonosítóval és anélkül is!

A `RatingRepository` osztály tartalmazza az adatbázisműveleteket. A `save` metódus egy `Rating` objektumot ment az 
adatbázisba, és visszaadja az általa generált azonosítót. A `getMonthFullScore` metódus egy versenyző álneve és a 
hónap alapján visszaadja a beküldött feladatok összesített pontszámát.
A `getRatingsByProblem` a  hónap és a feladat sorszáma alapján map-ként visszadja a megoldást beküldők nevét és az 
adott feladatban elért pontjukat.

A `RatingService` osztály adattagja egy `RatingRepository` példány. A `save` metódus a `RatingRepository` azonos 
nevű metódusát hívja meg úgy, hogy a `RatingRepository``save` metódusának meghívása előtt ellenőrizze, hogy a paraméterül
kapott `Rating` objektum valid-e, illetve hogy a versenyző az előző hónapban kapott-e pontot. (A verseny szabálya szerint 
a versenyzőnek minden hónapban be kell küldenie legalább egy feladat megoldását, és a megoldások összpontszáma nem lehet 
nulla.) Ha a feltételek nem teljesülnek, a visszaadott id érték legyen -1, egyébként pedig hívja meg a `RatingRepository` 
`save` metódusát. A verseny szeptembertől következő év júniusig tart, így a `RatingService` `save` metódusának nem kell 
ellenőriznie a 9. hónapban, illetve az 1. hónapot értelemszerűen a 12. előzi meg.
A `getRatingsByTask` metódus legyen delegálva a `RatingRepository` felé.
Írjunk egy `getRatingsByTaskAsCsv` metódust, amely a `getRatingsByTask` metódus segítségével visszaadja a
megoldást beküldők nevét és az adott feladatban elért pontjukat stringként.  Egy sorban a versenyző nickneve és a 
pontszáma szerepeljen pontosvesszővel elválasztva. A fejlécsor "NAME;POINTS" legyen. 
A `getMonthFullScore` metódus legyen delegálva a `RatingRepository` felé úgy, hogy ha a visszatérési érték null, akkor
nullát adjon vissza.
