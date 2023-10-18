package proeend.records;

import proeend.math.Ray;
import proeend.math.Vector;
import proeend.material.pdf.PDF;

public class ScatterRecord {

    /**
     * Houd de informatie over de verstrooide rays bij.
     */
    public ScatterRecord (){
        attenuation = new Vector();
        skipRay = new Ray(new Vector(), new Vector());
    }
    
    public Vector attenuation;
    public PDF pdf;
    public boolean skipPDF;
    public Ray skipRay;
}
