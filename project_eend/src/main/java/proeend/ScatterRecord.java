package proeend;

import proeend.math.Ray;
import proeend.math.Vector;
import proeend.material.pdf.PDF;

public class ScatterRecord {

    public ScatterRecord (){
        attenuation = new Vector();
        skipRay = new Ray(new Vector(), new Vector());
    }
    public Vector attenuation;
    public PDF pdf;
    public boolean skipPDF;
    public Ray skipRay;


}
