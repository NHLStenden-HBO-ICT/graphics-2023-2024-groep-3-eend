package proeend.material;

import misc.HitRecord;
import proeend.math.Ray;
import proeend.math.Vector;

public abstract class Material {

    /**
     * abstract methode die bepaalt hoe het licht weerkaatst gaat worden
     * @param rayIn
     * de inkomende Ray
     * @param rec
     * de HitRecord die de informatie van de voorgaande bounces onthoud
     * @param attenuation
     * hoe de kleur wordt vervormd door de albedo van het materiaal, moet worden gebruikt door de roepende functie
     * @param scattered
     * de uitgaande Ray, moet worden gebruikt door de roepende functie
     * @return
     * of de scatter wel of niet voldoet
     */
    public abstract boolean scatter(Ray rayIn, HitRecord rec, Vector attenuation, Ray scattered);
}
