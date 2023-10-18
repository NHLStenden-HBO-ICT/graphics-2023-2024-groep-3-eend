package proeend.hittable;

import proeend.math.Interval;
import proeend.math.Ray;
import proeend.math.Vector;
import proeend.records.HitRecord;

/**
 * Abstracte klasse die dient als basis voor hittable objecten in een 3D-scène.
 * Hittable objecten kunnen worden geraakt door een lichtstraal (Ray).
 */
public abstract class Hittable{

    public abstract BoundingBox getBoundingbox();

    /**
     * Constructor voor een hittable object zonder een gespecificeerd materiaal.
     */
    public Hittable(){}

    /**
     * Bepaalt of een lichtstraal een van de hittable objecten in de lijst treft en
     * vult het HitRecord met de informatie over de dichtstbijzijnde botsing.
     *
     * @param ray   De lichtstraal die wordt getest op botsingen.
     * @param rayT  Het interval waarin mogelijke botsingen worden gecontroleerd.
     * @param rec   Het HitRecord dat wordt gevuld met informatie over de botsing.
     * @return True als de lichtstraal een botsing heeft met een van de objecten in de lijst, anders false.
     */
    public abstract boolean hit(Ray ray, Interval rayT, HitRecord rec);


    /**
     * Berekent de kansdichtheid functiewaarde (PDF) voor het raken van een object in de lijst met
     * behulp van de meegegeven oorsprong en richting van een lichtstraal.
     * @param origin    De oorsprong van de lichtstraal.
     * @param direction De richting van de lichtstraal.
     * @return De PDF-waarde voor het raken van een object in de lijst.
     */
    public double pdfValue(Vector origin, Vector direction) {
        return 0;
    }

    /**
     * Genereert een willekeurige richting op basis van de objecten in de lijst en de meegegeven oorsprong.
     *
     * @param origin De oorsprong voor het genereren van de willekeurige richting.
     * @return Een willekeurige richting gebaseerd op de objecten in de lijst,
     * of de standaardrichting [1, 0, 0] als er geen objecten zijn.
     */
    public Vector random(Vector origin) {
        return new Vector(1,0,0);
    }





}
