package proeend;

import proeend.math.Ray;
import proeend.math.Vector;
import proeend.material.pdf.PDF;

public class ScatterRecord {
    public Vector attenuation;
    public PDF pdf;
    public boolean skipPDF;
    public Ray skipRay;


}
