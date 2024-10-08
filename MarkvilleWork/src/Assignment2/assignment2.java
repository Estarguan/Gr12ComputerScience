package Assignment2;
import java.util.*;
import java.io.*;

public class assignment2 {
	public static String minPath = "";
	public static int dfs(boolean[][] litterVisited, char[][] grid, int minSteps, int steps, int r, int c, int[][] dir,int row, int col,boolean[][] visited, String curPath, boolean catFound) {
		
		if (grid[r][c] == 'S') {
			litterVisited[row-1][col-1]= true;
			if (!catFound) {
				catFound = true;
			}
		}if (grid[r][c] == 'L') {
			if (catFound) {
				if (steps < minSteps){
					minPath = curPath;
				}
				minSteps = Math.min(steps,minSteps);
				return minSteps;
			}
		}
		for (int i = 0; i < 4; i++) {
			int changeRow = dir[i][0];
			int changeCol = dir[i][1];
			int tempR = r+changeRow, tempC = c+changeCol;
			if (tempR < 0 || tempC < 0 || tempR >=row || tempC >= col) {
				continue;
			}if (!catFound && visited[tempR][tempC]) {
				continue;
			}if (catFound && litterVisited[tempR][tempC]) {
				continue;
			}
			if (grid[tempR][tempC] == 'X') {
				continue;
			}if (grid[tempR][tempC] == '-' || grid[tempR][tempC] == 'S' || grid[tempR][tempC] == 'L'); {
				if (i == 0)curPath+="S ";
				if (i==1)curPath+="N ";
				if (i==2)curPath+="E ";
				if (i==3)curPath+="W ";
				if (!catFound) {
					visited[tempR][tempC] = true;
					minSteps =  dfs(litterVisited,grid,minSteps,steps+1,tempR,tempC,dir,row,col,visited,curPath,catFound);
					visited[tempR][tempC] = false;
					curPath = curPath.substring(0,curPath.length()-2);
				}
				if (catFound) {
					litterVisited[tempR][tempC] = true;
					minSteps =  dfs(litterVisited,grid,minSteps,steps+1,tempR,tempC,dir,row,col,visited,curPath,catFound);
					litterVisited[tempR][tempC] = false;
					curPath = curPath.substring(0,curPath.length()-2);
				}
									
				
			}
		}
		return minSteps;
	}
	public static void main(String[] args)throws IOException {
		//To Do: Save the shortest path in string form
		int[][] dir = {{1,0},{-1,0},{0,1},{0,-1}};
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int testCases = Integer.parseInt(in.readLine());
		

		for (int z = 0; z < testCases; z++) {
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
			int steps = dfs(litterVisited,grid,Integer.MAX_VALUE,0,0,0,dir,row,col,visited, "",false);
			if (steps == Integer.MAX_VALUE) {
				System.out.println("No path");
			}else {
				System.out.println(steps);
				System.out.println(minPath);
			}
			
			
		}
		

	}

}
