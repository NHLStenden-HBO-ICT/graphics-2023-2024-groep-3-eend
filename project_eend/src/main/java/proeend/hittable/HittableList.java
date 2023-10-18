package proeend.hittable;

import proeend.math.Vector;
import proeend.records.HitRecord;
import proeend.math.Interval;
import proeend.math.Ray;
import java.util.ArrayList;
import java.util.List;

/**
 * Een lijst van hittable objecten die kunnen worden getroffen door een lichtstraal in een 3D-scène.
 */
public class HittableList extends Hittable {

    private final List<Hittable> objects = new ArrayList<>();
    private BoundingBox boundingBox;
    @Override
    public BoundingBox getBoundingbox() {
        return boundingBox;
    }
    public HittableList(){
        this.boundingBox = null;
    }

    public HittableList(Hittable object){
        this();
        add(object);
    }

    public List<Hittable> getObjects() {
        return objects;
    }

    /**
     * Voegt een hittable object toe aan de lijst.
     *
     * @param object Het hittable object dat aan de lijst wordt toegevoegd.
     */
    public void add(Hittable object) {
        objects.add(object);
        if(boundingBox == null){
            boundingBox = object.getBoundingbox().pad();
        }else{
            boundingBox = new BoundingBox(boundingBox, object.getBoundingbox()).pad();
        }
    }

    /**
     * Maakt de lijst van hittable objecten leeg.
     */
    public void clear() {
        objects.clear();
        boundingBox = null;
    }

    /**
     * Bepaalt of een lichtstraal een van de hittable objecten in de lijst treft en
     * vult het HitRecord met de informatie over de dichtstbijzijnde botsing.
     * @param ray   De lichtstraal die wordt getest op botsingen.
     * @param rayT  Het interval waarin mogelijke botsingen worden gecontroleerd.
     * @param rec   Het HitRecord dat wordt gevuld met informatie over de botsing.
     * @return True als de lichtstraal een botsing heeft met een van de objecten in de lijst, anders false.
     */
    @Override
    public boolean hit(Ray ray, Interval rayT, HitRecord rec) {
        HitRecord tempRec = new HitRecord();
        boolean hasHitSomething = false;
        double closestSoFar = rayT.getMax();

        for (Hittable object: objects) {
            if (object.hit(ray, new Interval(rayT.getMin(), closestSoFar),tempRec)) {
                hasHitSomething = true;
                closestSoFar = tempRec.t;
                rec.copy(tempRec);
            }
        }
        return hasHitSomething;
    }
    /**
     * Berekent de kansdichtheid functiewaarde (PDF) voor het raken van een object
     * in de lijst met behulp van de meegegeven oorsprong en richting van een lichtstraal.
     * @param origin    De oorsprong van de lichtstraal.
     * @param direction De richting van de lichtstraal.
     * @return De PDF-waarde voor het raken van een object in de lijst.
     */
    @Override
    public double pdfValue(Vector origin, Vector direction) {
        for (Hittable object : objects) {
            return object.pdfValue(origin,direction);
        }
        return 0;
    }

    /**
     * Genereert een willekeurige richting vector op basis van de objecten in de lijst en de meegegeven oorsprong.
     * @param origin De oorsprong voor het genereren van de willekeurige richting.
     * @return Een willekeurige richting gebaseerd op de objecten in de lijst.
     */
    @Override
    public Vector random(Vector origin) {
        for (Hittable object : objects) {
            return object.random(origin);
        }
        return new Vector(1,0,0);
    }
}
