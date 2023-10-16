package proeend.material.pdf;

import proeend.material.Material;
import proeend.math.Vector;

public class BRDF extends Material {
    private Vector ColorDiffuse = new Vector(.5,.5,.5);
    private Vector ColorSpectral;
    public BRDF() {

    }

    public BRDF(Vector kd) {
        this.ColorDiffuse = kd;
    }
}
