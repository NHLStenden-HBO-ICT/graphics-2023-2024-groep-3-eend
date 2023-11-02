# Graphics Raytracer: Project Eend
Dit project is een Monte Carlo-stijl raytracer die voor elke pixel een ray schiet die op willekeurige wijs afkaatst
en de gemiddelde kleur van het totaal gevonden resultaat neemt.
In deze readme worden de onderdelen van dit project beschreven.

## Basiscomponenten
Om te beginnen vormen deze onderdelen de basis van de raytracer:

- Rays: Deze vertegenwoordigen de lichtstralen die door de scène worden geschoten.
- Vectoren: Definiëren de positie, richting en eigenschappen van objecten en lichtstralen in de 3D-scène.
- Scènes: De scènes bevatten de 3D-objecten en materialen die worden weergegeven 
en zijn het canvas waarop de raytracer werkt.
- Camera: Hiermee kunnen de kijkhoek en positie worden aangepast om de scène vanuit 
verschillende perspectieven te bekijken.

## Materialen
Er zijn verschillende materialen beschikbaar binnen de raytracer, waaronder:

Dielectric: Voor transparante materialen.
Emitter: Voor lichtbronnen.
Lambertian: Voor ruige materialen.
Mirror: Voor reflecterende materialen.

## Textures
Ook zijn er verschillende texturen te gebruiken, 
inclusief CheckerTexture, SolidTexture en ImageTexture voor gedetailleerde oppervlaktetexturen.

## Anti-Aliasing
Anti-aliasing is geïmplementeerd om kartelranden in de beelden te verminderen, 
door middel van subpixel-raysampling.

## Triangle & Mesh
Om complexe geometrieën in de scène te kunnen renderen wordt er gebruik gemaakt van driehoeken.
Hiermee kun je gedetailleerde 3D-modellen weergeven.

## Real Time GUI
Een Real Time grafische gebruikersinterface maakt het mogelijk om de scène live te bekijken. 
Met de toetsen Spatie, Z, Q en E of de pijltjes kan de positie van de camera worden aangepast. 
Bovendien kan er op de toets C gedrukt worden om een afbeelding te laten renderen in hogere kwaliteit.

## Multithreading
Om de prestaties te verbeteren, wordt gebruik gemaakt van multithreading. 
Dit maakt parallelle berekening van lichtstralen mogelijk, waardoor complexe scènes sneller kunnen worden gerenderd.

## BVH met AABB
Verder is er een Bounding Volume Hierarchy met Axis Aligned Bounding Boxes geïmplementeerd. 
Deze structuur optimaliseert de intersectieberekeningen van lichtstralen en objecten in de scène. 
In plaats van elk object afzonderlijk te controleren, 
wordt een boomstructuur gebruikt om efficiëntere berekeningen mogelijk te maken.

## Importance-sampling
Om indirect licht beter te kunnen nabootsen wordt in deze raytracer gebruik gemaakt van importance sampling.
Dit houdt in dat er bij bepaalde objecten prioriteit gesteld kan worden van hoe 
groot de kans is dat het licht richting het object gaat.
Hiermee kan bepaald worden of bijvoorbeeld een ander object het licht zou blokkeren,
waardoor in combinatie met de Monte Carlo-stijl van afkaatsing een realistische schaduw gerendered wordt.

## GEBRUIK
- Open dit project in IntelliJ >>>
- Ga in de Main class naar de functie main() >>>
- Klik op het groene pijltje om het project op te starten.

