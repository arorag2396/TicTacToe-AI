import java.util.Scanner;

/**
 *
 * @author gaa721
 */
public class Player {
    
    
   String name;
   int ox;
   boolean win;
   
   public Player(String a,int ox)
   {
       
       name =a;
       this.ox = ox;
       win=false;
       
   }
   
  public void  getInput(int[][] a)
  {
      
        Scanner get = new Scanner(System.in);
       int k=get.nextInt();
         a[(int)(k/3)][k%3] = ox;
       
        
  }
   
   public String toString()
   {
       
       return name;
       
   }
   
   
}
