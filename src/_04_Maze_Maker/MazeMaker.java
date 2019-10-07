package _04_Maze_Maker;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


public class MazeMaker{
	
	private static int width;
	private static int height;
	
	private static Maze maze;
	
	private static Random randGen = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();
	
	
	public static Maze generateMaze(int w, int h){
		width = w;
		height = h;
		maze = new Maze(width, height);
		
		//4. select a random cell to start
		//5. call selectNextPath method with the randomly selected cell
		selectNextPath(new Cell(randGen.nextInt(width), randGen.nextInt(height)));
		
		maze.getCell(0,randGen.nextInt(h)).setWestWall(false);
		maze.getCell(w-1,randGen.nextInt(h)).setEastWall(false);
		
		return maze;
	}

	//6. Complete the selectNextPathMethod
	private static void selectNextPath(Cell currentCell) {
		//A. mark cell as visited
		currentCell.setBeenVisited(true);
		//B. Get an ArrayList of unvisited neighbors using the current cell and the method below
		ArrayList<Cell> un = getUnvisitedNeighbors(currentCell);
		//C. if has unvisited neighbors,
		if(!un.isEmpty()) {
			//C1. select one at random.
			Cell rand = un.get(randGen.nextInt(un.size()));
			//C2. push it to the stack
			uncheckedCells.push(rand);
			//C3. remove the wall between the two cells
			removeWalls(currentCell, rand);
			//C4. make the new cell the current cell and mark it as visited
			currentCell = rand;
			currentCell.setBeenVisited(true);
			//C5. call the selectNextPath method with the current cell
			selectNextPath(currentCell);
		}	
		//D. if all neighbors are visited
		else if(un.isEmpty()) {
			//D1. if the stack is not empty
			if(!uncheckedCells.empty()) {
				// D1a. pop a cell from the stack
				// D1b. make that the current cell
				currentCell = uncheckedCells.pop();
				// D1c. call the selectNextPath method with the current cell
				selectNextPath(currentCell);
			}
		}	
		
	}

	//7. Complete the remove walls method.
	//   This method will check if c1 and c2 are adjacent.
	//   If they are, the walls between them are removed.
	private static void removeWalls(Cell c1, Cell c2) {
		if (c1.getX() == 1 + c2.getX()){
			c1.setWestWall(false);
			c2.setEastWall(false);
		}
		if (c1.getX()+1 == c2.getX()){
			c1.setEastWall(false);
			c2.setWestWall(false);
		}
		if (c1.getY()+1 == c2.getY()){
			c1.setSouthWall(false);
			c2.setNorthWall(false);
		}
		if (c1.getY() == 1+c2.getY()){
			c2.setSouthWall(false);
			c1.setNorthWall(false);
		}
	}
	
	//8. Complete the getUnvisitedNeighbors method
	//   Any unvisited neighbor of the passed in cell gets added
	//   to the ArrayList
	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		ArrayList<Cell> unCells = new ArrayList<Cell>();
		if(maze.getCell(c.getX()+1,c.getY())!= null && !maze.getCell(c.getX()+1,c.getY()).hasBeenVisited()) {
			unCells.add(maze.getCell(c.getX()+1,c.getY()));
		}
		if(maze.getCell(c.getX()-1,c.getY())!= null && !maze.getCell(c.getX()-1,c.getY()).hasBeenVisited()) {
			unCells.add(maze.getCell(c.getX()-1,c.getY()));
		}
		if(maze.getCell(c.getX(),c.getY()+1)!= null && !maze.getCell(c.getX(),c.getY()+1).hasBeenVisited()) {
			unCells.add(maze.getCell(c.getX(),c.getY()+1));
		}
		if(maze.getCell(c.getX(),c.getY()-1)!= null && !maze.getCell(c.getX(),c.getY()-1).hasBeenVisited()) {
			unCells.add(maze.getCell(c.getX(),c.getY()-1));
		}
		
		return unCells;
	}
}
