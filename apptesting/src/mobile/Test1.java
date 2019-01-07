package mobile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;

public class Test1 {

	public static void main(String[] args) throws Exception
	{
		//get input from keyboard
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter Mobile Num?..");
		String mbno=sc.nextLine();
		//start appium server
		Runtime.getRuntime().exec("cmd.exe /c start cmd /k \"appium -a 127.0.0.1 -p 4723\"");
		URL u=new URL("http://127.0.0.1:4723/wd/hub");
		DesiredCapabilities dc=new DesiredCapabilities();
		dc.setCapability(CapabilityType.BROWSER_NAME,"");
		dc.setCapability("deviceName","YT910TQQPR");
		dc.setCapability("platformName","android");
		dc.setCapability("platformVersion","4.3");
		dc.setCapability("appPackage","com.sonyericsson.android.socialphonebook");
		dc.setCapability("appActivity","com.sonyericsson.android.socialphonebook.DialerEntryActivity");
		File f=new File("G:\\seleniumprogrmas\\apptesting\\details.txt");
		AndroidDriver driver;
		while(true)
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
		//app automation
		try
		{
			WebDriverWait wait=new WebDriverWait(driver,20);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@content-desc='Backspace']")));
			for(int i=0;i<mbno.length();i++)
			{
				char d=mbno.charAt(i);


				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@text='"+d+"']")));
				driver.findElement(By.xpath("//*[@text='"+d+"']")).click();
			}
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@resource-id='com.sonyericsson.android.socialphonebook:id/call_button']")));
			driver.findElement(By.xpath("//*[@resource-id='com.sonyericsson.android.socialphonebook:id/call_button']")).click();
			Thread.sleep(10000);
			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@text='End call']")));
			driver.findElement(By.xpath("//*[@text='End call']")).click();
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		//close app
		driver.closeApp();
		//close appium server
		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");
	}
}
