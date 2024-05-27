import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;


public class Polynomial {
    double [] coefficients;
    int [] exponents;

    public Polynomial() {
        coefficients = new double[1];
        coefficients[0] = 0;
        exponents = new int[1];
        exponents[0] = 0;

    }


    public Polynomial(double[] coefficients, int[] exponents){
        this.coefficients = new double[coefficients.length];
        for (int i = 0; i < coefficients.length; i++){
            this.coefficients[i] = coefficients[i];
        }
        this.exponents = new int[exponents.length];
        for (int j = 0; j < exponents.length; j++){
            this.exponents[j] = exponents[j];
        }
     }
    


    public Polynomial(File file) {
        try {
            Scanner poly = new Scanner(file);
            String data = poly.nextLine();
            poly.close();

            String [] splitted = data.split("(?=\\+)|(?=-)");
            this.coefficients = new double[splitted.length];
            this.exponents = new int[splitted.length];

            int counter = 0;
            for (int i = 0; i < splitted.length ; i++){
                if (splitted[i].contains("x")){
                    String [] comp = splitted[i].split("x");
                    double d = Double.parseDouble(comp[0]);
                    int e = Integer.parseInt(comp[1]);
                    coefficients[counter] = d;
                    exponents[counter] = e;
                    counter++;
                }
                else {
                    double d = Double.parseDouble(splitted[i]);
                    coefficients[counter] = d;
                    exponents[counter] = 0;
                    counter++;
                }
            }
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }        
    }

    public void saveToFile(String filename){
        try {
            String to_write = "";
            if (exponents[0] == 0){
                to_write += String.valueOf(coefficients[0]) + "+";
            }

            for (int i = 1; i < coefficients.length; i++){
                to_write += String.valueOf(coefficients[i]) + "x" + String.valueOf(exponents[i]) + "+";
            }

            to_write = to_write.replace("+-", "-").substring(0, to_write.length()-1);
            FileWriter write_file = new FileWriter(filename);
            write_file.write(to_write);
            write_file.close();
        } catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    public Polynomial convert_to_zero(){
        int maxi = exponents[exponents.length-1];
        double [] temp_coefficients = new double[maxi+1];
        int [] temp_exponents = new int[maxi+1];

        int tracker = 0;
        for (int i = 0; i <= maxi; i++){
            temp_exponents[i] = i;
            if (exponents[tracker] == i){
                temp_coefficients[i] = coefficients[tracker];
                tracker += 1;
            } else {
                temp_coefficients[i] = 0;
            }
        }
        return new Polynomial(temp_coefficients, temp_exponents);
    }

    public Polynomial convert_to_nonzero(){

        int counter = 0;
        for (int i = 0; i < coefficients.length; i++){
            if (coefficients[i] != 0){
                counter++;
            }
        }

        double [] temp_coefficients = new double[counter];
        int [] temp_exponents = new int[counter];

        counter = 0;
        for (int i = 0; i < coefficients.length; i++){   
            if (coefficients[i] != 0){
                temp_coefficients[counter] = coefficients[i];
                temp_exponents[counter] = i;
                counter++;
            }
        }
        return new Polynomial(temp_coefficients, temp_exponents);
    }
    

    public Polynomial add_zero(Polynomial poly){
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
        return new Polynomial(to_output, this.exponents);
    }


    public Polynomial add(Polynomial poly){
        Polynomial temp = new Polynomial(this.coefficients, this.exponents);
        Polynomial a = temp.convert_to_zero();
        Polynomial b = poly.convert_to_zero();
        Polynomial c = a.add_zero(b).convert_to_nonzero();
        return c;
    }


    public Polynomial multiplyComponent(double coef, int power){
        double [] temp_coefficients = new double[coefficients.length];
        int [] temp_exponents = new int[coefficients.length];
        for (int i = 0; i < coefficients.length; i++){
            temp_coefficients[i] = this.coefficients[i] * coef;
            temp_exponents[i] = this.exponents[i] + power;
        }
        return new Polynomial(temp_coefficients, temp_exponents);
    }


    public Polynomial multiply(Polynomial poly) {
        Polynomial result = new Polynomial();
        for (int i = 0; i < poly.coefficients.length; i++) {
            Polynomial temp = this.multiplyComponent(poly.coefficients[i], poly.exponents[i]);
            result = result.add(temp);
        }
        return result;
    }


    public double evaluate(double x) {
		double result = 0;
		for (int i = 0; i < coefficients.length; i++) {
			result += Math.pow(x, exponents[i]) * coefficients[i];
		}
		return result;
	}

    public boolean hasRoot(double x){
        return Math.abs(evaluate(x)) < 0.00001;
    }


}