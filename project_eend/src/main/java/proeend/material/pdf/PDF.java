package proeend.material.pdf;

import proeend.math.Vector;

/**
 * Een interface die wordt gebruikt om de waarschijnlijkheid dichtheid functie (PDF) van een materiaal te modelleren.
 * Implementeer deze interface om aangepaste PDF's te maken voor het genereren van willekeurige stralingsrichtingen.
 */
public interface PDF {
    /**
     * Bereken de PDF-waarde voor een opgegeven richting.
     * @param direction De richting waarvoor de PDF-waarde wordt berekend.
     * @return De waarde van de PDF voor de opgegeven richting.
     */
    double value(Vector direction);

    /**
     * Genereer een willekeurige richting volgens het PDF en het onderliggende Hittable-object.
     * @return Een willekeurige richting gegenereerd op basis van het Hittable-object.
     */
    Vector generate();
}
