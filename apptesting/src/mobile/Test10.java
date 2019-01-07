package mobile;

import java.net.URL;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

import io.appium.java_client.android.AndroidDriver;

public class Test10 
{
	public static void main(String[] args) throws Exception
	{
		//site is common for both computer/mobile
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter platform like computer/mobile");
		String p=sc.nextLine();
		//object declaration
		WebDriver driver=null;
		//object creation
		if(p.equalsIgnoreCase("computer"))
		{
			System.setProperty("webdriver.chrome.driver","G:\\lokeshseleniumjava\\chromedriver.exe");
			driver=new ChromeDriver();
			driver.manage().window().maximize();
		}
		else
		{
			//start appium server
			Runtime.getRuntime().exec("cmd.exe /c start cmd /k \"appium -a 127.0.0.1 -p 4723\"");
			URL u=new URL("http://127.0.0.1:4723/wd/hub");
			DesiredCapabilities dc=new DesiredCapabilities();
			dc.setCapability(CapabilityType.BROWSER_NAME,"chrome");
			dc.setCapability("deviceName","YT910TQQPR");
			dc.setCapability("platformName","android");
			dc.setCapability("platformVersion","4.3");
			//launch app in ARD 
			while(2>1)
			{
				try
				{
					driver=new AndroidDriver(u,dc);
					break;
				}
				catch(Exception ex)
				{
					
				}
			}
		}
		//launch site using chrome
       driver.get("http://newtours.demoaut.com/");
		Thread.sleep(3000);
		//click register link
		driver.findElement(By.linkText("REGISTER")).click();
		//Fill registration form
		driver.findElement(By.name("firstName")).sendKeys("lokesh");
		driver.findElement(By.name("lastName")).sendKeys("kummari");
		driver.findElement(By.name("phone")).sendKeys("7729082549");
		driver.findElement(By.name("userName")).sendKeys("lokesh.kummari@yahoo.com");
		driver.findElement(By.name("address1")).sendKeys("prakash nagar road");
		driver.findElement(By.name("address2")).sendKeys("mayur marg");
		driver.findElement(By.name("city")).sendKeys("hyderabad");
		driver.findElement(By.name("state")).sendKeys("Telangana");
		driver.findElement(By.name("postalCode")).sendKeys("500016");
		Thread.sleep(5000);
		//Drop-Down automation
		WebElement e=driver.findElement(By.name("country"));
		Select s=new Select(e);
		s.selectByVisibleText("INDIA");
		//Automate further elements
		driver.findElement(By.name("email")).sendKeys("lok11");
		driver.findElement(By.name("password")).sendKeys("Lokrockz34@");
		driver.findElement(By.name("confirmPassword")).sendKeys("Lokrockz34@");
		driver.findElement(By.name("register")).click();
		Thread.sleep(5000);
		if(p.equalsIgnoreCase("computer"))
		{
			driver.close();
		}
		else
		{
			//close site
			driver.close();
			//stop server
			Runtime.getRuntime().exec("taskkill /F /IM node.exe");
			Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");
		}
	}
}
