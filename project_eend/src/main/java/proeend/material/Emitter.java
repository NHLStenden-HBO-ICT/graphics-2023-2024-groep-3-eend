package proeend.material;

import proeend.material.texture.SolidColor;
import proeend.material.texture.Texture;
import proeend.math.Ray;
import proeend.math.Vector;
import proeend.misc.HitRecord;

/**
 * Een materiaal dat licht (emissie) uitstraalt en wordt gebruikt om lichtbronnen te simuleren.
 */
public class Emitter extends Material {
    private Texture emission;

    /**
     * Creëer een nieuw emitterend materiaal met de opgegeven emissietextuur.
     *
     * @param emission De emissietextuur die de kleur en intensiteit van het uitgestraalde licht bepaalt.
     */
    public Emitter(Texture emission) {
        this.emission = emission;
    }

    /**
     * Creëer een nieuw emitterend materiaal met de opgegeven emissiekleur.
     *
     * @param color De emissiekleur die de kleur en intensiteit van het uitgestraalde licht bepaalt.
     */
    public Emitter(Vector color) {
        this.emission = new SolidColor(color);
    }

    /**
     * Bepaalt of er sprake is van verstrooiing bij een botsing met dit emitterende materiaal.
     *
     * @param rayIn       De invallende lichtstraal.
     * @param rec         Het HitRecord van de botsing.
     * @param attenuation Een vector die de verzwakking van het licht beschrijft.
     * @param scattered   De verstrooide lichtstraal, indien van toepassing.
     * @return True als er verstrooiing is, anders false (emitterend materiaal straalt geen licht terug).
     */
    @Override
    public boolean scatter(Ray rayIn, HitRecord rec, Vector attenuation, Ray scattered) {
        return false;
    }

    /**
     * Geeft de emissiekleur van dit emitterende materiaal op een specifiek punt weer.
     * @param u De horizontale textuurcoördinaat (indien van toepassing).
     * @param v De verticale textuurcoördinaat (indien van toepassing).
     * @param p De positie van het punt waar de emissiekleur wordt opgevraagd.
     * @return De emissiekleur van dit materiaal op het opgegeven punt.
     */
    @Override
    public Vector emit(double u, double v, Vector p) {
        return emission.value(u, v, p);
    }
}
