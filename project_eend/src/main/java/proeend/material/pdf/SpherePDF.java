package proeend.material.pdf;

import proeend.math.Vector;
/**
 * Implementatie van het PDF (Probability Density Function)-interface voor het genereren van willekeurige richtingen
 * op basis van een gelijkmatige verdeling over het oppervlak van een bol.
 * Dit PDF berekent een constante PDF-waarde voor alle richtingen en genereert willekeurige richtingen
 * overeenkomstig een gelijkmatige verdeling over een bol.
 */
public class SpherePDF implements PDF {
    /**
     * Bereken de PDF-waarde voor de opgegeven richting, die constant is en onafhankelijk is van de richting.
     * @param direction De richting waarvoor de PDF-waarde wordt berekend.
     * @return De constante PDF-waarde voor alle richtingen.
     */
    public double value(Vector direction) {
        return 1/(4*Math.PI);
    }
    /**
     * Genereer een willekeurige richting overeenkomstig een gelijkmatige verdeling over het oppervlak van een bol.
     * @return Een willekeurige richting gegenereerd op basis van een uniforme verdeling over een bol.
     */
    public Vector generate() {
        return Vector.randomOnUnitSphere();
    }
}
