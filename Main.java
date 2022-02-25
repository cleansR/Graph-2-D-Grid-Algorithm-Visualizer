import java.util.*;
 
public class Main 
{
  public static void main(String[] args) 
  {

    int r = 50;
    int c = 50;
  

    Visuals v = new Visuals(r, c);
    System.out.println("This program is intended to show the algorithms of BFS and DFS on a grid maze.");
    System.out.println("The default size of the maze is 50x50, and the maze is randomly generated");
    System.out.println("It is randomly generated with a 60% chance of a square being open and 40% chance of being closed.");
    System.out.println("The dark green squares are open squares while the black squares are closed/walls. The blue square is the entrance and the red square is the exit.");
    System.out.println("-------------------------------------------------------------------------------------------");
    System.out.println("The top 3 buttons do as follows: ");
    System.out.println("New maze: generates a new maze with the same size and odds as the current maze");
    System.out.println("Change grid size: allows for the changing of the dimensions of the maze, from 2 to 50 blocks. You will be prompted for the number of rows, which is the vertical length and the number of columns, the horizontal length. Once you enter a number, press enter");
    System.out.println("Change ratio of colors: allows for the changing of the likelyhood that a randomly generated square of the maze is open. You will be promopted for a number from 0 to 100. For reference, entering 0 means that every square will be closed and 100 means every square will be open. Once you enter a number, press enter");
    System.out.println("Manual Set Entrance/Exit: Allows you to change to manual input of entrance and exit locations. It is preset to off (random generation) and pressing it keeps it enabled until pressing it again.");
    System.out.println("-------------------------------------------------------------------------------------------");
    System.out.println("There are also buttons below the randomly generated maze, but they might be glitchy so hover your mouse around the area under the 50x50 original maze to show them. There should be three.");
    System.out.println("Shortest path: shows the shortest path from the entrance to the exit");
    System.out.println("DFS: shows the visualization of the depth-first search algorithm to attempt to find the exit. ");
    System.out.println("BFS: shows the visualization of the breadth-first search algorithm to attempt to find the exit. ");
    System.out.println("For reference, when using either BFS or DFS, a light green square indicates that it has been visited and fully explored, while a yellow square indicates that it has been visited, but not explored yet.");
    System.out.println("Both of these algorithms also reveal the path that it determined to reach the exit, if it exists. It will be highlighted in cyan after DFS/BFS finishes.");
    System.out.println("-------------------------------------------------------------------------------------------");
    System.out.println("You cannot press any buttons while any of the 3 algorithms are running.");
    System.out.println("Make sure to ensure that the screen is large enough by adjusting the borders.");
    System.out.println("Enjoy :)");

  }
}