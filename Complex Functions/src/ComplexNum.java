public class ComplexNum {

    public double real, imaginary;
    public static final ComplexNum ONE = new ComplexNum(1, 0), ZERO = new ComplexNum(0, 0);

    public ComplexNum(double real, double imaginary){

        this.real = real;
        this.imaginary = imaginary;

    }

    public ComplexNum add(ComplexNum n) {
        return new ComplexNum(this.real + n.real, this.imaginary + n.imaginary);
    }

    public ComplexNum subtract(ComplexNum n) {
        return new ComplexNum(this.real - n.real, this.imaginary - n.imaginary);
    }

    public ComplexNum multiply(ComplexNum n) {
        return new ComplexNum(this.real * n.real - this.imaginary * n.imaginary, this.real * n.imaginary + this.imaginary * n.real);
    }

    public ComplexNum divide(ComplexNum n) {
        if(n.equals(ZERO)) return null;
        ComplexNum numerator = multiply(new ComplexNum(n.real, -n.imaginary));
        double denominator = n.real * n.real + n.imaginary * n.imaginary;
        return new ComplexNum(numerator.real / denominator, numerator.imaginary / denominator);
    }

    public boolean equals(ComplexNum n) {
        return (this.real == n.real && this.imaginary == n.imaginary);
    }

    //    public ComplexNum power(int p) {
//        switch(p) {
//            case 0:
//                return ONE;
//            case 1:
//                return this;
//            case -1:
//                return ONE.divide(this);
//            default:
//                break;
//        }
//        if(p > 0) {
//            ComplexNum n = this;
//            for(; p > 1; p--) n = n.multiply(this);
//            return n;
//        }
//        return ONE.divide(power(-p));
//    }

    public ComplexNum power(double p) {
        if(this.equals(ZERO)) return ((p > 0) ? ZERO : null);
        if(this.equals(ONE)) return ONE;
        double[] polarValues = this.toPolar();
        return toRect(Math.pow(polarValues[0], p), polarValues[1] * p);
    }

    private double[] toPolar() {
        return new double[]{Math.sqrt(real * real + imaginary * imaginary), Math.atan(imaginary / real) + ((real < 0) ? Math.PI : 0)};
    }

    private ComplexNum toRect(double r, double theta) {
        return new ComplexNum(r * Math.cos(theta), r * Math.sin(theta));
    }

    @Override
    public String toString() {
        if(real == 0) {
            if(imaginary == 0) return "0";
            if(imaginary == 1) return "i";
            if(imaginary == -1) return "-i";
            return imaginary + "i";
        } else {
            if(imaginary == 0) {
                return "" + real;
            } else {
                return "" + real + ((imaginary > 0) ? " + " + ((imaginary == 1) ? "" : imaginary) : " - " + ((imaginary == -1) ? "" : (-imaginary))) + "i";
            }
        }
    }

}
