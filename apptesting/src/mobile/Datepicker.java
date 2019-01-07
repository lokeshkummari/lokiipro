package mobile;

import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Datepicker
{
	public static void main(String[] args) throws Exception
	{
		//Get expected data from keyboard
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter day");
		int ed=Integer.parseInt(sc.nextLine());
		System.out.println("Enter month");
		String em=sc.nextLine();
		System.out.println("Enter year");
		int ey=sc.nextInt();
		//launch site
		System.setProperty("webdriver.chrome.driver","G:\\lokeshseleniumjava\\chromedriver.exe");
		ChromeDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://jqueryui.com/datepicker/");
		Thread.sleep(5000);
		//operate date-picker
		driver.switchTo().frame(0);
		WebElement e=driver.findElement(By.xpath("//*[@id='datepicker']"));
		driver.executeScript("arguments[0].scrollIntoView();",e);
		e.click();//to get date-picker
		Thread.sleep(5000);
		//year
		while(2>1)
		{
			String temp1=driver.findElement(By.className("ui-datepicker-year")).getText();
			int ay=Integer.parseInt(temp1);
			if(ey<ay)
			{
				driver.findElement(By.xpath("//*[text()='Prev']")).click();
				Thread.sleep(1000);
			}
			else if(ey>ay)
			{
				driver.findElement(By.xpath("//*[text()='Next']")).click();
				Thread.sleep(5000);
			}
			else
			{
				//month (come to January)
				while(2>1)
				{
					String temp2=driver.findElement(By.className("ui-datepicker-month")).getText();
					String am=temp2.toLowerCase();
					em=em.toLowerCase();
					if(!am.equals("january"))
					{
						driver.findElement(By.xpath("//*[text()='Prev']")).click();
						Thread.sleep(1000);
					}
					else
					{
						break;
					}
				}
				//month (come to expected month) from January
				while(2>1)
				{
					String temp2=driver.findElement(By.className("ui-datepicker-month")).getText();
					String am=temp2.toLowerCase();
					em=em.toLowerCase();
					if(!am.equals(em))
					{
						driver.findElement(By.xpath("//*[text()='Next']")).click();
						Thread.sleep(1000);
					}
					else
					{
						break;
					}
				}
				break;//to terminate from outer loop
				
			}
		}
		//day
		driver.findElement(By.xpath("//a[text()='"+ed+"']")).click();
		Thread.sleep(5000);
		//close site
		driver.close();
	}
}
