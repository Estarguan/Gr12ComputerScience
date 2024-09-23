package recursionExercises;

public class recursionExercise {

	public static int question1(int customer) {
		if (customer == 0) {
			return 0;
			
		}
		if(customer == 1) {
			return 1;
		}
		if (customer % 10 == 0) {
		
			return 3+question1(customer-1);
		}if (customer % 2 == 0) {
			return 1 + question1(customer-1);
		}if (customer%2 == 1) {
			return 2 + question1(customer-1);
		}
	}
	
	public static void main(String[] args) {
		
	}
}
