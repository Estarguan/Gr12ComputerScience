package recursionExercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class recursionExercise {

<<<<<<< HEAD
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
=======
    public static int bubbles(int customers){
        if (customers <= 0)return 0;
        if (customers == 1) return 2;

        if (customers % 10 == 0)return 3 + bubbles(customers-1);

        if (customers %2 == 1) return 2 + bubbles(customers-1);
        if (customers %2 == 0) return 1 + bubbles(customers -1);
        return 0;
    }

    public static double sumDiff(int num){
        if (num == 1) return 1;
        if (num == -1)return -1;
        if (num == 2)return -0.5;
        if (num == -2)return 0.5;
        if (Math.abs(num % 2) == 1){
            if (num > 0) {return Math.round(1.0/num * 100000)/100000.0 + sumDiff (num - 2);}
            else { return Math.round(1.0/num*100000)/100000.0 + sumDiff(num+2);}
        }else{
            if (num > 0){return Math.round(-1.0/num * 100000)/100000.0 + sumDiff(num-2);}
            else{return Math.round(-1.0/num*100000)/100000.0 + sumDiff(num+2);}
        }

    }

    public static int multiply(int num1, int num2){
        if (num2 < 0){
            num2 *= -1;
            num1 *= -1;
        }
        if (num2 == 1){
            return num1;
        }else{
            return num1 + multiply(num1,num2-1);
        }
    }

    public static int find(String line){
        if (line.length() == 1){
            return (Character.isUpperCase(line.charAt(0)) || !Character.isLetter(line.charAt(0))) ? 1 : 0;
        }else{
            return((!(Character.isLetter(line.charAt(line.length()-1))) || Character.isUpperCase(line.charAt(line.length()-1))) ? 1 : 0) + find(line.substring(0,line.length()-1));
        }
    }

    public static String insert(String line, char letter){
        String tempLine = line.toUpperCase();
        if (line.length() == 0) return "";
        if (line.length() == 1) return Character.isLetter(line.charAt(0)) ? ""+ line.charAt(0) : "";
        if (line.length() == 2){
            if (tempLine.charAt(0) == tempLine.charAt(1)){
                return "" + line.charAt(0) + letter + line.charAt(1);
            }else{
                return "" + line.substring(0,2);
            }
        }
        if (tempLine.charAt(line.length()-1) == tempLine.charAt(line.length()-2)){
            return "" + insert(line.substring(0,line.length()-1),letter) + letter + line.charAt(line.length()-1);
        }
        if (!Character.isLetter(line.charAt(line.length()-2))){
            return "" + insert(line.substring(0,line.length()-2),letter) + line.charAt(line.length()-1);
        }
        return "" + insert(line.substring(0,line.length()-1),letter) + line.charAt(line.length()-1);
    }

    public static String commas(String line){
        if (!Character.isLetter(line.charAt(line.length()-1))){
            line += 'a';
            return commas(line);
        }if (line.length() == 2){
            if (line.charAt(0) == '-')return "-";
            if (line.charAt(line.length()-1) == 'd') return ""+line.charAt(0) + ',';
            return "" + line.charAt(0);
        }
        //abcde
        if (line.charAt(line.length()-1) == 'c'){
            char lastNum = line.charAt(line.length()-2);
            line = line.substring(0,line.length()-2);
            line += 'a';
            return commas(line) + ',' + lastNum;
        }else{
            char lastNum = line.charAt(line.length()-2);
            char newChar = (char)((int)(line.charAt(line.length()-1))+1);
            line = line.substring(0,line.length()-2);
            return commas(line + newChar) + lastNum;
        }
    }

    public static double amongUs(String[] numbers,int numCount,int curIdx,double sum){

        if (curIdx == numbers.length){
            return  Math.round((sum / numCount) * 100.0) / 100.0;
        }

        if (Character.isLetter(numbers[curIdx].charAt(0))){
            return amongUs(numbers,numCount,curIdx+1,sum);
        }return amongUs(numbers,numCount+1,curIdx+1,sum+Double.parseDouble(numbers[curIdx]));

    }


    public static void main (String [] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Which question you want to do. (Insert number from 1-7)");
        int num = Integer.parseInt(in.readLine());
        if (num == 1){
            System.out.println(bubbles(Integer.parseInt(in.readLine())));
        }else if (num == 2){
            System.out.println(sumDiff(Integer.parseInt(in.readLine())));
        }else if (num == 3){
            System.out.println(multiply(Integer.parseInt(in.readLine()),Integer.parseInt(in.readLine())));
        }else if (num == 4){
            System.out.println(find(in.readLine()));
        }else if (num == 5){
            System.out.println(insert(in.readLine(),in.readLine().charAt(0)));
        }else if (num == 6){
            System.out.println(commas(in.readLine()));
        }else if (num == 7){
            String line = in.readLine();
            String[] arr = line.split(" ");
            System.out.printf("%.2f",amongUs(arr,0,0,0));
        }

    }
>>>>>>> refs/remotes/origin/master
}
