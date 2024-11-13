// Estar Guan
// October 2, 2024
// Assignment #2 - Recursion: Save the Cat
//
//Ms. Wong's cat is stuck in the sink and Ms. Wong needs to get the cat out of the sink
//There are lots of toys in Ms. Wong's house that she cannot step on so she has to form a path to reach the cat
//This code takes in the layout of Ms. Wong's house and calculates the minimum number of steps it takes Ms. Wong
//To reach her cat and the steps she should take to get there.
//If no path to the cat, it should output no path
package Assignment2;
import java.util.*;
import java.io.*;

public class assignment2 {
	public static String minPath = "";

	//Description: This method is a recursive method that takes in the layout of Ms. Wong's house and calculates the shortest path
	//for Ms. Wong to get to her sink for her cat and also the minimum number of steps it takes.
	//Parameters: This method takes in the house layout as a 2d char array, the current minimum number of steps, the number of steps on current path
	//the current row, current column an array of all possible directions, the number of rows and columns in the grid, an array of boolean to check where you've been and the current path
	//Return: This method returns an int representing the minimum number of steps that needs to be taken in the house
	public static int dfs(char[][] grid, int minSteps, int steps, int r, int c, int[][] dir,int row, int col,boolean[][] visited, String curPath) {

		if (grid[r][c] == 'S') {
			if (steps < minSteps){
				minPath = curPath;
			}
			minSteps = Math.min(steps,minSteps);
			return minSteps;
		}

		for (int i = 0; i < 4; i++) {
			int changeRow = dir[i][0];
			int changeCol = dir[i][1];
			int tempR = r+changeRow, tempC = c+changeCol;
			if (tempR < 0 || tempC < 0 || tempR >=row || tempC >= col) {
				continue;
			}if (visited[tempR][tempC]) {
				continue;
			}
			if (grid[tempR][tempC] == 'X') {
				continue;
			}if (grid[tempR][tempC] == '-' || grid[tempR][tempC] == 'S') {
				if (i == 0)curPath+="S ";
				if (i==1)curPath+="N ";
				if (i==2)curPath+="E ";
				if (i==3)curPath+="W ";
				visited[tempR][tempC] = true;
				minSteps =  dfs(grid,minSteps,steps+1,tempR,tempC,dir,row,col,visited,curPath);
				visited[tempR][tempC] = false;
				curPath = curPath.substring(0,curPath.length()-2);

			}
		}
		return minSteps;
	}
	public static void main(String[] args)throws IOException {
		//To Do: Save the shortest path in string form
		int[][] dir = {{1,0},{-1,0},{0,1},{0,-1}};
		BufferedReader in = new BufferedReader(new FileReader("assignment2.txt"));
		int testCases = Integer.parseInt(in.readLine());
		for (int z = 0; z < testCases; z++) {
			System.out.println("Layout #" + (z+1) + ":\n");

			//Asking for input, setting all the visisted arrays to false everywhere
			int row = Integer.parseInt(in.readLine());
			int col = Integer.parseInt(in.readLine());
			char grid[][] = new char[row][col];
			boolean visited[][] = new boolean[row][col];
			boolean litterVisited[][] = new boolean[row][col];
			for (int i = 0; i < row; i++) {
				String line = in.readLine();
				for (int j = 0; j < col; j++) {
					grid[i][j] = line.charAt(j);
					visited[i][j] = false;
					litterVisited[i][j] = false;
				}
			}
			visited[0][0] = true;

			//Printing out each grid layout
			for (int i = 0; i < grid.length;i++){
				for (char j : grid[i]){
					System.out.print(j);
				}
				System.out.println("");
			}
			System.out.println("");
			int steps = dfs(grid,Integer.MAX_VALUE,0,0,0,dir,row,col,visited, "");
			if (steps == Integer.MAX_VALUE) {
				System.out.println("No path");
			}else {
				System.out.println("Fastest # of steps: " + steps);
				System.out.println("Direction: " + minPath);
			}
			System.out.println("");

		}
		//Close file reader
		in.close();
	}

}
