package proeend.material.pdf;

import proeend.math.Vector;

/**
 * Implementatie van het PDF (Probability Density Function)-interface voor het genereren van willekeurige richtingen
 * op basis van een gemengde PDF bestaande uit twee andere PDF's.
 * Dit PDF berekent de gemiddelde waarschijnlijkheidsdichtheid en genereert willekeurige richtingen
 * op basis van een gewogen keuze tussen de twee onderliggende PDF's.
 */
public class MixturePDF implements PDF {
    private PDF[] pdfs = new PDF[2];

    /**
     * Initialiseert een nieuwe MixturePDF met twee onderliggende PDF's.
     *
     * @param pdfA De eerste PDF in de gemengde PDF.
     * @param pdfB De tweede PDF in de gemengde PDF.
     */
    public MixturePDF(PDF pdfA, PDF pdfB) {
        pdfs[0] = pdfA;
        pdfs[1] = pdfB;
    }

    /**
     * Bereken de PDF-waarde voor de opgegeven richting door het gemiddelde te nemen van de PDF-waarden
     * van de twee onderliggende PDF's.
     *
     * @param direction De richting waarvoor de PDF-waarde wordt berekend.
     * @return De gemiddelde PDF-waarde voor de opgegeven richting.
     */
    public double value(Vector direction) {
        return .5 * pdfs[0].value(direction) + .5 * pdfs[1].value(direction);
    }

    /**
     * Genereer een willekeurige richting volgens de gemengde PDF, waarbij een van de onderliggende PDF's
     * wordt gekozen op basis van een willekeurige selectie.
     *
     * @return Een willekeurige richting gegenereerd op basis van de gemengde PDF.
     */
    public Vector generate() {
        if (Math.random() < 0.5)
            return pdfs[0].generate();
        else
            return pdfs[1].generate();
    }
}
