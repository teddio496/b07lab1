public class Polynomial {
    double [] coefficients;
    public Polynomial() {
        coefficients = new double[1];
        coefficients[0] = 0;
    }

    public Polynomial(double[] coefficients){
        this.coefficients = new double[coefficients.length];
        for (int i = 0; i < coefficients.length; i++){
            this.coefficients[i] = coefficients[i];
        }
     }

    public Polynomial add(Polynomial poly){

        double[] add_to;
        double[] add_from;
        if (coefficients.length < poly.coefficients.length){
            add_to = poly.coefficients;
            add_from = coefficients;
        } else {
            add_to = coefficients;
            add_from = poly.coefficients;
        }
        double[] to_output = add_to.clone();
        for (int i = 0; i < add_from.length; i++){
            to_output[i] += add_from[i];
        }
        return new Polynomial(to_output);
    }


    public double evaluate(double x) {
		double result = 0;
		for (int i = 0; i < coefficients.length; i++) {
			result += Math.pow(x, i + 1) * coefficients[i];
		}
		return result;
	}

    public boolean hasRoot(double x){
        return Math.abs(evaluate(x)) < 0.00001;
    }
}