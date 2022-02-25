import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.util.*;
import java.awt.event.*;



public class MazeScreen extends JPanel implements ActionListener
{
  private boolean initializePaint = false;
  //private int row;
  //private int col;
  
  private char[][] maze;
  private MazeFinder mF;
  private Stack<Integer> q;
  
  private JButton showPath;
  private JButton showWholePath;
  private JButton dfs;
  private int current = 0;

  private boolean paintingShortestPath = false;
  
  private javax.swing.Timer timer;
  
  private int count = 0;

  //Stuff 2 show entire path
  private Queue<Integer> entire;
  private int wholepaint;
  private HashSet<Integer> used;
  private HashSet<Integer> paintG;
  private HashSet<Integer> paintP;
  private javax.swing.Timer timer2;

  private HashSet<Integer> shortestPath;

  private boolean currentlyPaintingEntireBFS = false;
  private boolean showingPathAfterBFS = false;
  

  


  //DFS
  private Queue<Integer> entireDFS;
  private int currentpaint = 0;
  private HashSet<Integer> DFSused;
  private HashSet<Integer> DFSpaintG;
  private HashSet<Integer> DFSpaintP;
  private javax.swing.Timer timer3;
  private boolean currentlyPaintingEntireDFS = false;
  private boolean showingPathAfterDFS = false;

  private HashSet<Integer> shortestPathDFS;

  public static boolean doingSomething = false;

  //public static boolean userInputOn = false;
  private javax.swing.Timer timer4;

  public MazeScreen(char[][] maze, int a, int b, int c, int d)
  {
    this.maze = maze;
    this.setSize(750, 750);
    this.setLayout(null);
    mF = new MazeFinder(maze, a, b, c, d);

    showPath = new JButton("Shortest Path");
    add(showPath);
    showPath.setBounds(0, 530, 200, 30);
    showPath.addActionListener(this);

    showWholePath = new JButton("BFS");
    add(showWholePath);
    showWholePath.setBounds(250, 530, 100, 30);
    showWholePath.addActionListener(this);

    dfs = new JButton("DFS");
    add(dfs);
    dfs.setBounds(400, 530, 100, 30);
    dfs.addActionListener(this);
  }
  //UserInputOn
  
  public MazeScreen(char[][] maze)
  {
    
    this.maze = maze;
    this.setSize(750, 750);
    this.setLayout(null);
    mF = new MazeFinder(maze, -1, -1, -1, -1);

    showPath = new JButton("Shortest Path");
    add(showPath);
    showPath.setBounds(0, 530, 200, 30);
    showPath.addActionListener(this);

    showWholePath = new JButton("BFS");
    add(showWholePath);
    showWholePath.setBounds(250, 530, 100, 30);
    showWholePath.addActionListener(this);

    dfs = new JButton("DFS");
    add(dfs);
    dfs.setBounds(400, 530, 100, 30);
    dfs.addActionListener(this);

    timer4 = new javax.swing.Timer(5000, this);
    doingSomething = true;
    timer4.start();

    
  }
  

  public void setInput()
  {
    Scanner s = new Scanner(System.in);
    System.out.println("Enter the row/column of both the entrance and exit. Remember that the rows/columns are shifted by 1, meaning that 0 is the first row and # of rows - 1 is the bottom row. The same is true for columns, except the 0 column is the left one and the rightmost column is # of columns - 1. Also, the entrance and exit may not be in the same location." );
    System.out.println("Enter entrance row (" + 0 + " <= row < " + maze.length + ") :");
    int a = s.nextInt();
    System.out.println("Enter entrance column (" + 0 + " <= col < " + maze[0].length + ") :");
    int b = s.nextInt();
    System.out.println("Enter exit row (" + 0 + " <= row < " + maze.length + ") :");
    int c = s.nextInt();
    System.out.println("Enter exit column (" + 0 + " <= col < " + maze[0].length + ") :");
    int d = s.nextInt();

    while(a<0 || a>= maze.length || b<0 || b>=maze[0].length || c<0 || c>= maze.length || d<0 || d>=maze[0].length || (a==c && b==d))
    {
      System.out.println("Invalid parameters");
      System.out.println("Enter entrance row");
      a = s.nextInt();
      System.out.println("Enter entrance column");
      b = s.nextInt();
      System.out.println("Enter exit row");
      c = s.nextInt();
      System.out.println("Enter exit column");
      d = s.nextInt();
    }
    
    maze[a][b] = 'W';
    maze[c][d] = 'E';
    
    mF.editLocs(a, b, c, d);
    repaint();
  }

  public static boolean locked()
  {
    return doingSomething;
  }

  public void showDFS()
  {
    q = mF.dfsCall();
    entireDFS = mF.returnEntireDFSPath();
    
    timer3 = new javax.swing.Timer(25, this);
    currentlyPaintingEntireDFS = true;
    DFSused = new HashSet<Integer>();
    DFSpaintG = new HashSet<Integer>();
    DFSpaintP = new HashSet<Integer>();
    timer3.start();
  }

  public void showWhole()
  {
    q = mF.bfs();
    entire = mF.returnEntirePath();
    
    timer2 = new javax.swing.Timer(25, this);
    currentlyPaintingEntireBFS = true;
    used = new HashSet<Integer>();
    paintG = new HashSet<Integer>();
    paintP = new HashSet<Integer>();
    timer2.start();
  }

  public void paintPath()
  {
    q = mF.bfs();
    if(q==null)
    {
      System.out.println("No Valid Path");
      doingSomething = false;
    }
    
    timer = new javax.swing.Timer(150, this);
    timer.start();
    
  }

  public void paint(Graphics g)
  {
    super.paintComponent(g);
    
    int size = 500/(Math.max(maze.length, maze[0].length));

    if(currentlyPaintingEntireBFS)
    {
      if(!used.contains(wholepaint))
      {
        used.add(wholepaint);
        paintP.add(wholepaint);
      }
      else
      {
        paintG.add(wholepaint);
        paintP.remove(wholepaint);
      }
    }
    else if(currentlyPaintingEntireDFS)
    {
      if(!DFSused.contains(currentpaint))
      {
        DFSused.add(currentpaint);
        DFSpaintP.add(currentpaint);
      }
      else
      {
        DFSpaintG.add(currentpaint);
        DFSpaintP.remove(currentpaint);
      }
    }


  
    for(int i = 0; i < maze.length; i++)
      for(int j = 0; j < maze[0].length; j++)
      {
        if(maze[i][j]=='F')
        {
          g.setColor(Color.black);
          g.fillRect(j*size, i*size, size, size);
        }
        else if(maze[i][j]=='O')
        {
          
          g.setColor(new Color(40, 140, 20));
          g.fillRect(j*size, i*size, size, size);
        }
        else if(maze[i][j]=='W')
        {
          g.setColor(Color.blue);
          g.fillRect(j*size, i*size, size, size);
        }
        else if(maze[i][j]=='E')
        {
          g.setColor(Color.red);
          g.fillRect(j*size, i*size, size, size);
        }
      }

    
    
    if(showingPathAfterBFS)
      showingPathAfterBFS = false;
    if(showingPathAfterDFS)
      showingPathAfterDFS = false;
      

    
    
    if(paintingShortestPath)
    if(q!=null && !q.isEmpty())
    {
      
      int row = current/maze[0].length;
      int col = current - row*maze[0].length;
      
      g.setColor(Color.orange);
      g.fillRect(col*size, row*size, size, size);
    }  

    if(currentlyPaintingEntireBFS)
    {
      for(int i = 0; i< maze.length; i++)
      {
        for(int j = 0; j < maze[0].length; j++)
        {
          if(currentlyPaintingEntireBFS && paintP.contains(i* maze[0].length + j))
        {
          g.setColor(Color.YELLOW);
          g.fillRect(j*size, i*size, size, size);
        }
        else if(currentlyPaintingEntireBFS && paintG.contains(i* maze[0].length + j))
        {
          
          g.setColor(Color.green);
          g.fillRect(j*size, i*size, size, size);
        }
        else if(currentlyPaintingEntireDFS)
        {
          if(DFSpaintP.contains(i* maze[0].length + j))
          {
            g.setColor(Color.YELLOW);
            g.fillRect(j*size, i*size, size, size);
          } 
          else if(DFSpaintG.contains(i* maze[0].length + j))
          {
           
            g.setColor(Color.green);
          g.fillRect(j*size, i*size, size, size);
          }
        }
        else if(showingPathAfterBFS)
        {
          if(shortestPath.contains(i* maze[0].length + j))
          {
            g.setColor(Color.cyan);
            g.fillRect(j*size, i*size, size, size);
          }
        }
        else if(showingPathAfterDFS)
        {
          if(shortestPathDFS.contains(i* maze[0].length + j))
          {
            g.setColor(Color.cyan);
            g.fillRect(j*size, i*size, size, size);
          }
        }
        }
      }
    }
    if(currentlyPaintingEntireDFS)
    {
      for(int i = 0; i< maze.length; i++)
      {
        for(int j = 0; j < maze[0].length; j++)
        {
          if(currentlyPaintingEntireBFS && paintP.contains(i* maze[0].length + j))
        {
          g.setColor(Color.YELLOW);
          g.fillRect(j*size, i*size, size, size);
        }
        else if(currentlyPaintingEntireBFS && paintG.contains(i* maze[0].length + j))
        {
          
          g.setColor(Color.green);
          g.fillRect(j*size, i*size, size, size);
        }
        else if(currentlyPaintingEntireDFS)
        {
          if(DFSpaintP.contains(i* maze[0].length + j))
          {
            g.setColor(Color.YELLOW);
            g.fillRect(j*size, i*size, size, size);
          } 
          else if(DFSpaintG.contains(i* maze[0].length + j))
          {
           
            g.setColor(Color.green);
          g.fillRect(j*size, i*size, size, size);
          }
        }
        else if(showingPathAfterBFS)
        {
          if(shortestPath.contains(i* maze[0].length + j))
          {
            g.setColor(Color.cyan);
            g.fillRect(j*size, i*size, size, size);
          }
        }
        else if(showingPathAfterDFS)
        {
          if(shortestPathDFS.contains(i* maze[0].length + j))
          {
            g.setColor(Color.cyan);
            g.fillRect(j*size, i*size, size, size);
          }
        }
        }
      }
    }

    
  }

  public void actionPerformed(ActionEvent ae)
  {
    if(ae.getSource()==showPath && !doingSomething)
    {
      paintingShortestPath = true;
      paintPath();
      doingSomething = true; 
      
    }
    else if(ae.getSource()==timer)
    {
      if(q!=null && !q.isEmpty())
      {
        current = q.pop();
        repaint();
        count++; 
      }
      else
      {
        if(count!=0)
        System.out.println(count-1 + " Blocks Traveled!");
        else
        System.out.println("No Blocks Traveled");

        count = 0;
        paintingShortestPath = false;
        repaint();
        timer = null;
        doingSomething = false;
      }
    }
    else if(ae.getSource()==showWholePath && !doingSomething)
    {
      showWhole();
      doingSomething = true;
    }
    else if(ae.getSource()==timer2)
    {
      if(currentlyPaintingEntireBFS)
      {
        if(!entire.isEmpty())
        {
          wholepaint = entire.poll();
          repaint();
        }
        else
        {
          
          currentlyPaintingEntireBFS = false;
          showingPathAfterBFS = true;
          timer2 = new javax.swing.Timer(2500, this);
          timer2.start();
    
        }
      }
      else if(showingPathAfterBFS)
      {
        timer2 = new javax.swing.Timer(8000, this);
        timer2.start();
        
        shortestPath = new HashSet<Integer>();
        //q = mF.bfs();
        if(q==null)
        System.out.println("No path found");
        else
        {
          while(!q.isEmpty())
          {
            shortestPath.add(q.pop());
          }
        }

        repaint();
        
      }
      else
      {
        repaint();
        System.out.println("Blocks visited before success/failure: " + mF.getBFSN());
        int x = mF.getlengthBFS();
        if(x!=0)
        System.out.println("Path length: " + x);
        else
        System.out.println("Path length: N/A");
        timer2 = null;
        doingSomething = false;
      }
    }
    else if(ae.getSource()==dfs  && !doingSomething)
    {
 
      showDFS();
      doingSomething = true;
    }
    else if(ae.getSource()==timer3)
    {
      if(currentlyPaintingEntireDFS)
      {
        if(!entireDFS.isEmpty())
        {
          currentpaint = entireDFS.poll();
          repaint();

        }
        else
        {
          currentlyPaintingEntireDFS = false;
          showingPathAfterDFS = true;
          timer3 = new javax.swing.Timer(2500, this);
          timer3.start();
    
        }
      }
      else if(showingPathAfterDFS)
      {
        timer3 = new javax.swing.Timer(8000, this);
        timer3.start();
        
        shortestPathDFS = new HashSet<Integer>();
        
        if(q==null)
        System.out.println("No path found");
        else
        {
          while(!q.isEmpty())
          {
            shortestPathDFS.add(q.pop());
          }
        }

        repaint();
      }
      else
      {
        repaint();
        timer3 = null;
        System.out.println("Blocks visited before success/failure: " + mF.getDFSN());
        int x = mF.getlengthDFS();
        if(x!=0)
        System.out.println("Path length: " + x);
        else
        System.out.println("Path length: N/A");


        doingSomething = false;
      }
    }
    else if(ae.getSource()==timer4)
    {
      
      setInput();
      timer4 = null;
      doingSomething = false;
    }
    
  }
}
    

