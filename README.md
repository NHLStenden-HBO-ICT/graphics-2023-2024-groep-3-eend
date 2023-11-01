# Graphics Raytracer: Project Eend
Dit project is een Monte Carlo-stijl raytracer dat voor elk pixel meerdere willekeurige lichtstralen afvuurt en de gemiddelde kleur berekent van de resulterende kleuren. Project Eend biedt een scala aan functies om realistische en gedetailleerde 3D-scènes te renderen.

# Basiscomponenten
In de kern van Project Eend vind je essentiële componenten die de basis vormen van de raytracer:

- Rays: Deze vertegenwoordigen de lichtstralen die door de scène worden geschoten.
- Vectoren: Definiëerd de positie, richting en eigenschappen van objecten en lichtstralen in de 3D-scène.
- Scènes: De scènes bevatten de 3D-objecten en materialen die worden weergegeven 
en zijn het canvas waarop de raytracer werkt.
- Camera: Hiermee kun je de kijkhoek en positie aanpassen om je scène vanuit verschillende perspectieven te bekijken.

# Camera bewegen
Met de toetsen Spatie en Z, Q en E kan de positie van de camera worden aangepast tijdens de live-render.

# Render genereren en opslaan
Door op de toets C te drukken tijdens de live-render, kan er een afbeelding worden gerenderd in hoge kwaliteit 
en opgeslagen in de renders folder.

# Materialen
Project Eend biedt een verscheidenheid aan materialen die de eigenschappen van objecten in de scène definiëren. 
Enkele van de beschikbare materialen zijn:

Dielectric: Voor transparante en reflecterende materialen.
Emitter: Voor lichtbronnen die licht in de scène uitstralen.
Lambertian: Voor diffuse materialen met natuurlijke kleurverspreiding.
Mirror: Voor spiegelachtige reflecties.
Normal: Een materiaal om de normaalvector van objecten weer te geven.
Textures: Inclusief CheckerTexture, SolidTexture en ImageTexture voor gedetailleerde oppervlaktetexturen.

# Anti-Aliasing
Anti-aliasing is geïmplementeerd om kartelranden in de beelden te verminderen. Dit project maakt gebruik van willekeurige lichtstraalsampling om realistische en vloeiende resultaten te bereiken.

# Triangle & Mesh
Project Eend ondersteunt de weergave van complexe geometrieën in de scène, waaronder driehoeken en mesh-objecten. Hiermee kun je gedetailleerde 3D-modellen renderen.

# Real-Time GUI met Capture-mogelijkheid
Een realtime grafische gebruikersinterface (GUI) maakt het mogelijk om de scène te bekijken terwijl deze wordt gerenderd. Bovendien kun je met deze GUI schermafbeeldingen vastleggen om je renders te bewaren en te delen.


# Multithreading
Om de prestaties te verbeteren, maakt Project Eend gebruik van multithreading. Dit maakt parallelle berekening van lichtstralen mogelijk, waardoor complexe scènes sneller kunnen worden gerenderd.

# BVH met AABB 🔗
Een opvallende feature van dit project is de implementatie van een Bounding Volume Hierarchy (BVH) met Axis Aligned Bounding Boxes (AABB). Deze structuur optimaliseert de intersectieberekeningen tussen lichtstralen en objecten in de scène. In plaats van elk object afzonderlijk te controleren, wordt een boomstructuur gebruikt om efficiëntere berekeningen mogelijk te maken.

# Light en Object-sampling
Project Eend bevat geavanceerde methoden voor licht- en object-sampling. Deze functies dragen bij aan de realistische weergave van licht en schaduwen in de scène.

Deze readme biedt een beknopt overzicht van Project Eend en zijn functies. Raadpleeg de documentatie en de broncode voor meer gedetailleerde informatie en gebruiksinstructies. We hopen dat je geniet van het verkennen en gebruiken van deze krachtige raytracer!