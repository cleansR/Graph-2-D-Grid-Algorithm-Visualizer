import java.util.*;
public class MazeFinder
{
  private char[][] maze;
  
  private int entrance;
  private int exit;
  private Queue<Integer> visited;
  private Queue<Integer> visitedDFS;

  private int numBFS = 0;
  private int numDFS = 0;

  private int lengthBFS = 0;
  private int lengthDFS = 0;

  private boolean found = false;


  public MazeFinder(char[][] m, int entrow, int entcol, int exrow, int excol)
  {
    maze = m;
    entrance = getNum(entrow, entcol);
    exit = getNum(exrow, excol);
  }


  public void editLocs(int entr, int entc, int exr, int exc)
  {
    entrance = getNum(entr, entc);
    exit = getNum(exr, exc);

    //maze[entr][entc] = 'W';
    //maze[exr][exc] = 'E';
  }

  public int getNum(int row, int col)
  {
    if(row==-1 && col==-1)
    return -1;
    else
    return (row* maze[0].length + col);
  }

  public int getDFSN()
  {
    return numDFS;
  }
  public int getBFSN()
  {
    return numBFS;
  }
  public int getlengthBFS()
  {
    return lengthBFS;
  }
  public int getlengthDFS()
  {
    return lengthDFS;
  }

  public Queue<Integer> returnEntirePath()
  {
    
    return visited;
  }

  public Queue<Integer> returnEntireDFSPath()
  {
    
    return visitedDFS;
  }

  public void printPath(Queue<Integer> path)
  {
    while(!path.isEmpty())
    {
      int temp = (int) path.poll();

      int row = temp/maze[0].length;
      int col = temp - row*maze[0].length;
    }
  }
  
  public Stack<Integer> bfs()
  {
    
    lengthBFS = 0;
    numBFS = 0;
    boolean[] marked = new boolean[maze.length * maze[0].length];
    int[] pathing = new int[maze.length * maze[0].length];
    Queue<Integer> q = new LinkedList<Integer>();
    visited = new LinkedList<Integer>();


    //F = wall, O = open, W = entrance, E = exit
    q.add(entrance);
    visited.add(entrance);
    numBFS++;
    marked[entrance] = true;
    pathing[entrance] = entrance;

    while(!q.isEmpty())
    {
      int current = q.poll();
      //visited.add(current);
      
      

      int row = current/maze[0].length;
      
      int col = current - row*maze[0].length;
      

      //4 Directional bfs
      
      if(row!= 0 && (maze[row-1][col]=='O' || maze[row-1][col]=='E') && !marked[getNum(row-1, col)])
      {
        numBFS++;
          q.add(getNum(row-1, col));
          visited.add(getNum(row-1, col));
          pathing[getNum(row-1, col)] = (int) current;
          marked[getNum(row-1, col)] = true;

          if(getNum(row-1, col)==exit)
          {
            //numBFS++;
            visited.add(current);
            visited.add(exit);
            Stack<Integer> path = new Stack<Integer>();
            for(int i = getNum(row-1, col); i!=pathing[i]; i = pathing[i])
            {
              lengthBFS++;
              path.push(i);
          
            }
            path.push(entrance);
            return path;
          }
      }
      if (row!= maze.length-1 && (maze[row+1][col]=='O' || maze[row+1][col]=='E') && !marked[getNum(row+1, col)])
      {
        numBFS++;
          q.add(getNum(row+1, col));
          visited.add(getNum(row+1, col));
          pathing[getNum(row+1, col)] = (int) current;
          marked[getNum(row+1, col)] = true;


          if(getNum(row+1, col)==exit)
          {
            //numBFS++;
            visited.add(current);
            visited.add(exit);
            Stack<Integer> path = new Stack<Integer>();
            for(int i = getNum(row+1, col); i!=pathing[i]; i = pathing[i])
            {
              lengthBFS++;
              path.push(i);
          
            }
            path.push(entrance);
            return path;
          }
      }
      if (col!= 0 && (maze[row][col-1]=='O' || maze[row][col-1]=='E') && !marked[getNum(row, col-1)])
      {
        numBFS++;
          q.add(getNum(row, col-1));
          visited.add(getNum(row, col-1));
          pathing[getNum(row, col-1)] = (int) current;
          marked[getNum(row, col-1)] = true;


          if(getNum(row, col-1)==exit)
          {
           // numBFS++;
            visited.add(current);
            visited.add(exit);
            Stack<Integer> path = new Stack<Integer>();
            for(int i = getNum(row, col-1); i!=pathing[i]; i = pathing[i])
            {
              lengthBFS++;
              path.push(i);
          
            }
            path.push(entrance);
            return path;
          }
      }
      if (col!= maze[0].length-1 && (maze[row][col+1]=='O' || maze[row][col+1]=='E') && !marked[getNum(row, col+1)])
      {
        numBFS++;
          q.add(getNum(row, col+1));
          visited.add(getNum(row, col+1));
          pathing[getNum(row, col+1)] = (int) current;
          marked[getNum(row, col+1)] = true;

          if(getNum(row, col+1)==exit)
          {
           // numBFS++;
            visited.add(current);
            visited.add(exit);
            Stack<Integer> path = new Stack<Integer>();
            for(int i = getNum(row, col+1); i!=pathing[i]; i = pathing[i])
            {
              lengthBFS++;
              path.push(i);
         
            }
            path.push(entrance);
            return path;
          }
      }
      visited.add(current);
    }

  
  
    return null;
    
  }

  public Stack<Integer> dfsCall()
  {
    
    lengthDFS = 0;
    numDFS = 0;
    visitedDFS = new LinkedList<Integer>();
    int[] pathing = new int[maze.length * maze[0].length];
    pathing[entrance] = entrance;
    dfs(entrance, new boolean[maze.length * maze[0].length], pathing);
    

    if(found)
    {
      int k = 0;
      
      Stack<Integer> path = new Stack<Integer>();
      for(int i = exit; i!=pathing[i]; i = pathing[i])
      {
        
        lengthDFS++;
        path.push(i);
      
      }
      path.push(entrance);
      found = false;
      return path;
    }
    else
    return null;
  }

  public void dfs(int current, boolean[] marked, int[] pathing)
  {
    if(found)
    return;

    visitedDFS.add(current);
    

    int row = current/maze[0].length;
    int col = current - row*maze[0].length;

  
    if(current==exit)
    {
      visitedDFS.add(current);
      numDFS++;
      found = true;
      return;
    }
    
    if(row!= 0 && (maze[row-1][col]=='O' || maze[row-1][col]=='E') && !marked[getNum(row-1, col)])
    {
        
        pathing[getNum(row-1, col)] = (int) current;
        marked[getNum(row-1, col)] = true;
        dfs(getNum(row-1, col) , marked, pathing);
    }
    if (row!= maze.length-1 && (maze[row+1][col]=='O' || maze[row+1][col]=='E') && !marked[getNum(row+1, col)])
    {
        
        pathing[getNum(row+1, col)] = (int) current;
        marked[getNum(row+1, col)] = true;
        dfs(getNum(row+1, col), marked, pathing);
    }
    if (col!= 0 && (maze[row][col-1]=='O' || maze[row][col-1]=='E') && !marked[getNum(row, col-1)])
    {
        
        pathing[getNum(row, col-1)] = (int) current;
        marked[getNum(row, col-1)] = true;
        dfs(getNum(row, col-1), marked, pathing);
    }
    if (col!= maze[0].length-1 && (maze[row][col+1]=='O' || maze[row][col+1]=='E') && !marked[getNum(row, col+1)])
    {
        
        pathing[getNum(row, col+1)] = (int) current;
        marked[getNum(row, col+1)] = true;
        dfs(getNum(row, col+1) , marked, pathing);
    }

    visitedDFS.add(current);
    numDFS++;
    



  }
  
}


