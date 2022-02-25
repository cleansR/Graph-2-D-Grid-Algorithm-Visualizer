import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.util.*;
import java.awt.event.*;

public class Visuals extends JFrame implements ActionListener
{
  
  private JFrame j;
  private JPanel mz;
  private char[][] maze;
  private JButton restart;
  private JButton changeSize;
  private JButton changeOdds;
  private JButton setInput;
  private int[] locs;
  private JPanel title;
  private int percentage = 60;
  private boolean userInputOn = false; 
  private boolean locked = false;
  
  public Visuals(int r, int c)
  {
    locs = new int[4];
    if(!userInputOn)
    generateMaze(r, c);
    else
    generateMazeWithInput(r, c);

    
    initialize(locs[0], locs[1], locs[2], locs[3]);
  }

  public void initialize(int a, int b, int c, int d)
  {
    mz = null;
    j = new JFrame();
    
    j.setSize(800, 800);
    j.setVisible(true);
    j.setDefaultCloseOperation(EXIT_ON_CLOSE);
    j.setLayout(null);
    
    if(!userInputOn)
    {
      
      //System.out.println(a + " " + b + " " + c + " " + d);
      mz = new MazeScreen(maze, a, b, c, d);
      
    }
    
    else
    {
      mz = new MazeScreen(maze);
    }
    

    j.add(mz);
    mz.setBounds(150, 72, 750, 750);
    
    changeSize = new JButton("Change grid size");
    changeSize.setBounds(140, 20, 180, 30);
    changeSize.addActionListener(this); 
    j.add(changeSize);


    restart = new JButton("New Maze");
    restart.setBounds(10, 20, 120, 30);
    restart.addActionListener(this);
    j.add(restart);

    changeOdds = new JButton("Change Color Ratio");
    changeOdds.setBounds(330, 20, 200, 30);
    changeOdds.addActionListener(this);
    j.add(changeOdds);

    setInput = new JButton("Manual Set Entrance/Exit");
    j.add(setInput);
    setInput.setBounds(540, 20, 220, 30);
    setInput.addActionListener(this);

   
    
  }

  public void reinitialize()
  {
    j = null;
    mz = null;
    restart = null;
    changeOdds = null;
    changeSize = null;
    setInput = null;
  }

  public void generateMaze(int rs, int cs)
  {
    maze = new char[rs][cs];

    for(int i = 0; i <maze.length; i++)
    {
      for(int j = 0; j <maze[0].length; j++)
      {
        int temp = (int) (Math.random() * 100);
        if(temp<percentage)
        maze[i][j] = 'O';
        else
        maze[i][j] = 'F';
      }
    }

    int[] entrance = genLoc(rs, cs);
    int[] exit = genLoc(rs, cs);
    while(exit[0]==entrance[0] && exit[1]==entrance[1])
    {
      exit = genLoc(rs, cs);
    }

    maze[entrance[0]][entrance[1]] = 'W';
    maze[exit[0]][exit[1]] = 'E';

    

    locs[0] = entrance[0]; locs[1] = entrance[1]; locs[2] = exit[0]; locs[3] = exit[1];
  }

  public void generateMazeWithInput(int rs, int cs)
  {
    maze = new char[rs][cs];

    for(int i = 0; i <maze.length; i++)
    {
      for(int j = 0; j <maze[0].length; j++)
      {
        int temp = (int) (Math.random() * 100);
        if(temp<percentage)
        maze[i][j] = 'O';
        else
        maze[i][j] = 'F';
      }
    }
  }

  public int[] genLoc(int rSize, int cSize)
  {
    int choice = (int) (Math.random() * 4);

    int r = -1;
    int c = -1;
    
    if(choice==0)
    {
      r = 0;
      c = ( (int) ( Math.random() * cSize ));
    }
    else if(choice ==1)
    {
      r = rSize-1;
      c = ( (int) ( Math.random() * cSize ));
    }
    else if(choice ==2)
    {
      r = ( (int) ( Math.random() * rSize ));
      c = 0;
    }
    else if(choice ==3)
    {
      r = ( (int) ( Math.random() * rSize ));
      c = cSize-1;
    }

    int[] temp = {r, c};
    return temp;
  }

  public void actionPerformed(ActionEvent ae)
  {
    if(!MazeScreen.locked() && !locked)
    {
        if(ae.getSource()==restart)
        {
          locked = true;
      j.dispose();
       mz = null;
      locs = new int[4];
      
      if(userInputOn)
      generateMazeWithInput(maze.length, maze[0].length);
      else
      generateMaze(maze.length, maze[0].length);

      reinitialize();

      initialize(locs[0], locs[1], locs[2], locs[3]);
      locked = false;
    }
    else if(ae.getSource()==changeSize)
    {
      locked = true;
      Scanner s = new Scanner(System.in);
      System.out.println("Enter # of Rows (2-50) ");
      int r = s.nextInt();
      System.out.println("Enter # of Columns (2-50) ");
      int c = s.nextInt();

      while(r<2 || r>50 || c<2 || c>50)
      {
        System.out.println("Enter valid dimensions ");

        System.out.println("Enter # of Rows (2-50) ");
        r = s.nextInt();
        System.out.println("Enter # of Columns (2-50) ");
        c = s.nextInt();
      }


      j.dispose();
      mz = null;
      locs = new int[4];
      if(userInputOn)
      generateMazeWithInput(r, c);
      else
      generateMaze(r, c);

      reinitialize();

      initialize(locs[0], locs[1], locs[2], locs[3]);
      locked = false;
    }
    else if(ae.getSource()==changeOdds)
    {
      locked = true;
      Scanner s = new Scanner(System.in);
      
      System.out.println("Enter % Chance of Empty Square (0-100): ");
      int p = s.nextInt();
      while(p<0 || p>100)
      {
        System.out.println("Enter valid number (0-100): ");
        p = s.nextInt();
      }

      percentage = p;
      
      j.dispose();
      mz = null;
      locs = new int[4];

      if(userInputOn)
      generateMazeWithInput(maze.length, maze[0].length);
      else
      generateMaze(maze.length, maze[0].length);

      reinitialize();

      initialize(locs[0], locs[1], locs[2], locs[3]);
      locked = false;
    }
    else if(ae.getSource()==setInput)
    {
      locked = true;
      if(!userInputOn)
      {
        userInputOn = true;
        j.dispose();
        mz = null;

        int temp = (int) (Math.random() * 100);
        if(temp<percentage)
        maze[locs[0]][locs[1]] = 'O';
        else
        maze[locs[0]][locs[1]] = 'F';
        temp = (int) (Math.random() * 100);
        if(temp<percentage)
        maze[locs[2]][locs[3]] = 'O';
        else
        maze[locs[2]][locs[3]] = 'F';
        
        
        reinitialize();
        
        initialize(-1, -1, -1, -1);
        
      }
      else
      {
        int rowEnt = -1;
        int colEnt = -1;
        int rowEx = -1 ;
        int colEx = -1;

        for(int i = 0; i <maze.length; i++)
        {
          for(int j = 0; j<maze.length; j++)
          {
            if(maze[i][j]=='W')
            {
              rowEnt = i;
              colEnt = j;
            }
            else if(maze[i][j]=='E')
            {
              rowEx = i;
              colEx = j;
            }
          }
        }
        
        int temp = (int) (Math.random() * 100);
        if(temp<percentage)
        maze[rowEnt][colEnt] = 'O';
        else
        maze[rowEnt][colEnt] = 'F';
        temp = (int) (Math.random() * 100);
        if(temp<percentage)
        maze[rowEx][colEx] = 'O';
        else
        maze[rowEx][colEx] = 'F';
        

        userInputOn = false;
        j.dispose();
        mz = null;

        locs = new int[4];
       

        int[] entrance = genLoc(maze.length, maze[0].length);
    int[] exit = genLoc(maze.length, maze[0].length);
    while(exit[0]==entrance[0] && exit[1]==entrance[1])
    {
      exit = genLoc(maze.length, maze[0].length);
    }

    maze[entrance[0]][entrance[1]] = 'W';
    maze[exit[0]][exit[1]] = 'E';

    

    locs[0] = entrance[0]; locs[1] = entrance[1]; locs[2] = exit[0]; locs[3] = exit[1];

      

        reinitialize();
        initialize(locs[0], locs[1], locs[2], locs[3]);
      }
     locked = false;  
    }
    }

  }
}