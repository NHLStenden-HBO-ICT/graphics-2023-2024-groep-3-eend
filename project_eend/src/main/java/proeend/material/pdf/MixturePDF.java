package proeend.material.pdf;

import proeend.math.Vector;

public class MixturePDF extends PDF {
    private PDF[] pdfs = new PDF[2];
    public MixturePDF(PDF pdf0, PDF pdf1) {
        pdfs[0] = pdf0;
        pdfs[1] = pdf1;
    }
    @Override
    public double value(Vector direction) {
        return .5*pdfs[0].value(direction)+.5*pdfs[1].value(direction);
    }

    @Override
    public Vector generate() {
        if (Math.random()<.5)
            return pdfs[0].generate();
        else
            return pdfs[1].generate();
    }
}
