import java.io.File;

public class Driver {
	public static void main(String [] args) {
		Polynomial p = new Polynomial();

		System.out.println(p.evaluate(3));
		double [] c1 = {6,5}; 
		int [] e1 = {0, 3};
		Polynomial p1 = new Polynomial(c1, e1);

		double [] c2 = {-2,-9};
		int[] e2 = {1,4};
		Polynomial p2 = new Polynomial(c2, e2);
		Polynomial s = p1.add(p2);

		System.out.println("s(0.1) = " + s.evaluate(0.1));

		if(s.hasRoot(1))
			System.out.println("1 is a root of s");
		else
			System.out.println("1 is not a root of s");
		
		double[] m1 = {1,2,3,4,5};
		int[] t1 = {0,1,2,3,4};

		double[] m2 = {2,3,4,5,6};
		int[] t2 = {1,2,3,4,5};

		Polynomial r1 = new Polynomial(m1, t1);
		Polynomial r2 = new Polynomial(m2, t2);
		Polynomial r3 = r1.multiply(r2);
		Polynomial f1 = new Polynomial();
		System.out.println("r3(2) = " + r3.evaluate(2));
		try {
			File input = new File("input.txt");
			f1 = new Polynomial(input);
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }  


		f1.saveToFile("output.txt");

	}
}