package mobile;

public class Test3 
{
	public static void main(String[] args) 
	{
	   String x="3 point 7";
	   x=x.replace("point",".");
      String y[]=x.split(" ");
      for(String r:y)
      {
    	  System.out.println(r);
      }
      String z="";
      z=z.join("",y);
      System.out.println(z);
      x=z;
      System.out.println(x);
      x=x.replace(" . ",".");
      System.out.println(x);
       
	}

}
