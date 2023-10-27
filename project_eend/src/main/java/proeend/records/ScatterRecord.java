package proeend.records;

import proeend.math.Ray;
import proeend.math.Vector;
import proeend.material.pdf.PDF;
/**
 * De `ScatterRecord` klasse houdt informatie bij over lichtstralen die willekeurige kanten op verspreiden.
 */
public class ScatterRecord {

    public Vector attenuation; // De verzwakking van het licht.
    public PDF pdf; // Hoe waarschijnlijk het is dat er verstrooiing plaats vindt.
    public boolean skipPDF; // Geeft aan of de PDF overgeslagen moet worden.
    public Ray skipRay; // De ray die overgeslagen moet worden bij de verstrooiing.


    /**
     * Initialiseert een nieuw `ScatterRecord` object met standaardwaarden.
     */
    public ScatterRecord (){
        attenuation = new Vector();
        skipRay = new Ray(new Vector(), new Vector());
    }
}
