package test;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Screen;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import pages.Buttons_page;
import pages.Login_Page;
import pages.Screenshot;

public class SE_gluecode 
{
	public WebDriver object;
	public WebDriverWait wait;
	public Scenario s;
	public Properties pro;
	public ExtentReports er;
	public ExtentTest et;
	public Login_Page lp;
	public Buttons_page bp;
	public Screenshot ss;
	public WebElement masters;
	String Quotation_number;
	@Before
	public void method1(Scenario s) throws Exception
	{
		this.s=s;
		pro=new Properties();
		FileInputStream fi=new FileInputStream("E:\\SE_AutomationScripts\\scripts\\Saudi_Enaya_indexes\\src\\test\\resources\\repository\\info.properties");
		pro.load(fi);
		er=new ExtentReports("Saudi_Enaya_Application.html",false);
		et=er.startTest("SaudiEnaya_Application");
	}
	@Given("^launch site$")
	public void launch_site()
	{
		System.setProperty("webdriver.chrome.driver","E:\\SE_AutomationScripts\\chromedriver.exe");
		object=new ChromeDriver();
		object.get(pro.getProperty("se_beta"));
		wait=new WebDriverWait(object,20);
		bp=new Buttons_page(object);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtUname")));
		object.manage().window().maximize();
	}
	@When("^enter username as \"(.*)\"$")
	public void usrname_se(String x)
	{
		object.findElement(By.name("txtUname")).sendKeys(x);
		
	}
	@And("^enter password as \"(.*)\"$")
	public void pwd_se(String y)
	{
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[@type='password'])[1]")));
		 object.findElement(By.xpath("(//*[@type='password'])[1]")).sendKeys(y);
	}
	@And("^select login type as \"(.*)\"$")
	public void login_type(String x)
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlLoginTo")));
	    object.findElement(By.name("ddlLoginTo")).click();
		if(x.equals("EMPLOYEE"))
		{
	 	    WebElement dd=object.findElement(By.name("ddlLoginTo"));
		    Select s=new Select(dd);
		    s.selectByVisibleText("EMPLOYEE");
		}
		else if(x.equals("PROVIDER"))
		{
			WebElement dd=object.findElement(By.name("ddlLoginTo"));
		    Select s=new Select(dd);
		    s.selectByVisibleText("PROVIDER");
		}
		else if(x.equals("CORPORATE"))
		{
			WebElement dd=object.findElement(By.name("ddlLoginTo"));
		    Select s=new Select(dd);
		    s.selectByVisibleText("CORPORATE");
		}
		else if(x.equals("REINSURANCE"))
		{
			WebElement dd=object.findElement(By.name("ddlLoginTo"));
		    Select s=new Select(dd);
		    s.selectByVisibleText("REINSURANCE");
		}
		else
		{
			WebElement dd=object.findElement(By.name("ddlLoginTo"));
		    Select s=new Select(dd);
		    s.selectByVisibleText("MEMBER");
		}
	}
	@And("^select policy type as \"(.*)\"$")
	public void policy_type(String x)
	{
		if(x.equals("Group"))
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='RBGroup']")));
		    object.findElement(By.xpath("//*[@id='RBGroup']")).click();
		}
		else
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("RBIndiviual")));
			object.findElement(By.name("RBIndiviual")).click();
		}
		
	}
	@Then("^click on submit button$")
	public void submit_button()
	{
		 wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@name='btnSubmit']")));
	    object.findElement(By.xpath("//*[@name='btnSubmit']")).click();
	}
	@And("^validate the output with \"(.*)\" criteria$")
	public void criteria(String z) throws Exception
	{
		Thread.sleep(3000);
		try
		{
			
				if(z.equals("username_blank"))
		        {
			       Thread.sleep(3000);
			       String x=object.switchTo().alert().getText();
			        if(x.equals("Please Enter Username."))
			         {
				       object.switchTo().alert().accept();
				       s.write("Blank uid test passed");
				       et.log(LogStatus.PASS,"Blank uid test passed");
			         }
		         }
		      else if(z.equals("username_invalid") && object.findElement(By.xpath("//*[text()='Invalid UserID or Password...!']")).isDisplayed())
		       {
			      s.write("invalid uid test passed");
		       }
		     else if(z.equals("password_blank"))
		       {
			      String x=object.switchTo().alert().getText();
			      if(x.equals("Please Enter Password."))
			      {
				     s.write("Blank pwd test passed");
			      }
		       }
		     else if(z.equals("password_invalid") && object.findElement(By.xpath("//*[text()='Invalid UserID and Password...!']")).isDisplayed())
		      {
			    s.write("invalid pwd test passed");
		      }
		    else if(z.equals("all_valid") && object.findElement(By.xpath("//*[@id='ImgHospital']")).isDisplayed())
		      {
			     s.write("valid credentials test passed");
			     wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
			     object.findElement(By.xpath("//*[text()='Close']")).click();
			     wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
			    /* wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Masters']")));
			     Actions a=new Actions(object);
				 WebElement masters=object.findElement(By.xpath("//*[text()='Masters']"));
			     a.moveToElement(masters).build().perform();
			     wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Currency Master']")));
			     object.findElement(By.xpath("//*[text()='Currency Master']")).click();
			     wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Currency Details")));*/
			     
		      }
		    else
		      {
			     //screenshot when login failed with today date and time
			      byte[] b=((TakesScreenshot)object).getScreenshotAs(OutputType.BYTES);
			      s.embed(b,"login credentials test failed");
			      String x=ss.screenshot(object);
		       }
       }
      catch(Exception ex)
       {
          System.out.println(ex.getMessage());
       }
	}
	@Then("^close site$")
	public void close_site()
	{
		object.close();
	}
	@When("^Enter Credentials as Valid$")
	public void login_valid(DataTable dt)
	{
		List<List<String>> data=dt.asLists(String.class);
		object.findElement(By.name("txtUname")).sendKeys(data.get(1).get(0));
		object.findElement(By.name("txtPass")).sendKeys(data.get(1).get(1));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlLoginTo")));
	    object.findElement(By.name("ddlLoginTo")).click();
	    WebElement dd=object.findElement(By.name("ddlLoginTo"));
	    Select s=new Select(dd);
	    s.selectByVisibleText("EMPLOYEE");
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='RBGroup']")));
	    object.findElement(By.id("RBGroup")).click();
	}
	@And("^Click on Currency Masters$")
	public void clickcurrency() throws Exception
	{
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		 object.findElement(By.xpath("//*[text()='Close']")).click();
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		 Actions a=new Actions(object);
		 //move to masters module
		 WebElement master=object.findElement(By.xpath("//*[text()='Masters']"));
		 a.moveToElement(master).build().perform();
		 Thread.sleep(2000);
		 //click on currency master
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Currency Master']")));
		 object.findElement(By.xpath("//*[text()='Currency Master']")).click();
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCurCode")));
	}
	 @And("^do logout$")
	 public void clickLogout() 
	 {
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgLogout")));
		 object.findElement(By.id("ImgLogout")).click();
	 }
	 @Then("^validate Currency Masters page$")
	 public void validate_currency() throws Exception
	 {
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCurCode")));
		    if(object.findElement(By.name("btnEdit")).isEnabled() && object.findElement(By.name("btnAdd")).isEnabled())
		    {
		    	if(object.findElement(By.name("btnAdd")).isEnabled())
		    	{
		    		
		    		try
		    		{
		    			object.findElement(By.name("btnAdd")).click();
		    			String currencyCode=object.findElement(By.name("txtCurCode")).getAttribute("value");
		    			System.out.println(currencyCode);
		    			Thread.sleep(2000);
		    			if(object.findElement(By.name("BtnSave")).isEnabled() && object.findElement(By.name("btnClear")).isEnabled())
				    	{
		    				try
		    				{
		    					Thread.sleep(1000);
		    					object.findElement(By.name("BtnSave")).click();
					    		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCurCode")));
					    		object.findElement(By.name("txtDescription")).sendKeys("LOK");
		    				}
		    				catch(Exception eq)
		    				{
		    					object.switchTo().alert().accept();
					    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCurCode")));
					    		object.findElement(By.name("txtDescription")).sendKeys("ATESTQ");
					    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCurSymbol")));
					    		object.findElement(By.name("txtCurSymbol")).sendKeys("#");
					    		if(object.findElement(By.name("BtnSave")).isEnabled() && object.findElement(By.name("btnClear")).isEnabled())
						    	{
						    		object.findElement(By.name("BtnSave")).click();
						    		Thread.sleep(5000);
						    		try
						    		{
						    			if(object.findElement(By.xpath("//*[contains(text(),'already Exist')]")).isDisplayed())
							    		{
							    			s.write("Currency already exist");
							    			String SE_Image=ss.screenshot(object);
							    			Thread.sleep(8000);
							    			et.log(LogStatus.PASS,"Currrency already exist"+et.addScreenCapture(SE_Image));
							    			byte[] b=((TakesScreenshot)object).getScreenshotAs(OutputType.BYTES);
										      s.embed(b,"Currency already exist");
							    		}
						    		}
						    		catch(Exception el)
						    		{
						    			s.write("New currency added test succesfully");
						    		}
						    		Thread.sleep(3000);
						    		object.findElement(By.name("btnClear")).click();
						    		Thread.sleep(3000);
						    		s.write("Currency Details Cleared Successfully");
		    				}   		
					    Thread.sleep(5000);	
					    object.findElement(By.name("btnClear")).click();
			    		Thread.sleep(3000);
				    	object.findElement(By.name("btnEdit")).click();
				    	wait.until(ExpectedConditions.elementToBeClickable(By.name("ddlCurrCode")));
				    	object.findElement(By.name("ddlCurrCode")).click();
				    	List<WebElement> currency_code=object.findElements(By.xpath("//*[@name='ddlCurrCode']/option"));
				    	int cclength=currency_code.size();
				    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Australian Dollar (AUD)-6']")));
				    	object.findElement(By.xpath("//*[text()='Australian Dollar (AUD)-6']")).click();
				    	/*WebElement Description_textbox=object.findElement(By.name("txtDescription"));
				    	wait.until(ExpectedConditions.attributeContains(Description_textbox, "value","currency"));*/
				    	Thread.sleep(8000);
				    	if(object.findElement(By.name("BtnSave")).isEnabled() && object.findElement(By.name("btnClear")).isEnabled())
				    	{
				    		object.findElement(By.name("BtnSave")).click();
				    	}
				    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
				    	Thread.sleep(5000);
				    	if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
				    	{
				    		s.write("Record Saved Successfully");
				    	}	
				    	//object.findElement(By.name("btnClear")).click();
				    	Thread.sleep(3000);	
					    object.findElement(By.name("btnClear")).click();
			    		Thread.sleep(2000);
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnEdit")));
			    		object.findElement(By.name("btnEdit")).click();
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCurrCode")));
			    		object.findElement(By.name("ddlCurrCode")).click();
			    		Thread.sleep(1000);
			    		List<WebElement> actualCurrencyCode=object.findElements(By.xpath("//*[@name='ddlCurrCode']/option"));
			    		System.out.println(actualCurrencyCode.size());
			    		for(int i=2;i<=actualCurrencyCode.size();i++)
			    		{
			    			Thread.sleep(1000);
			    			Actions a=new Actions(object);
			    			a.sendKeys(Keys.DOWN).build().perform();
			    			String acCurrencyCode=object.findElement(By.xpath("//*[@name='ddlCurrCode']/option["+i+"]")).getText();
			    			Thread.sleep(1000);
			    			if(acCurrencyCode.contains("-"+currencyCode))
			    			{
			    				a.sendKeys(Keys.ENTER).build().perform();
			    				break;
			    			}
			    		}
			    		Thread.sleep(3000);
			    		try
			    		{
			    			if(object.findElement(By.name("btnDel")).isEnabled())
				    		{
				    			object.findElement(By.name("btnDel")).click();
				    			Thread.sleep(2000);
				    			bp.click_clearButton();
				    		}
			    		}
			    		catch(Exception ex1)
			    		{
			    			object.switchTo().alert().accept();
				    		Thread.sleep(5000);
				    		if(object.findElement(By.xpath("//*[contains(text(),'Deleted Successfully')]")).isDisplayed())
				    		{
				    			s.write("Record Successfully Deleted");
				    		}
				    		Thread.sleep(2000);
			    			bp.click_clearButton();
			    			Thread.sleep(1000);
			    			masters=object.findElement(By.xpath("//*[text()='Masters']"));
			    			Actions a=new Actions(object);
			    		    a.moveToElement(masters).build().perform();
			    		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Communication Master']")));
			   			 object.findElement(By.xpath("//*[text()='Communication Master']")).click();
			    		}
			    	}
		    	}
    		}
    		catch(Exception ex)
    		{
	    		System.out.println(ex.getMessage());
    		}	
    	  }
	    }
	    else
	    {
	    	byte[] b=((TakesScreenshot)object).getScreenshotAs(OutputType.BYTES);
	    	//s.embed(b, "");
	    	String x=ss.screenshot(object);
	    	System.exit(0);
	    	System.out.println();
	    }
	 }
	 @Then("^validate Communication Details Page$")
	 public void communication_details() throws Exception
	 {
		    /*wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));*/
		    
		    //move to masters module
           Thread.sleep(2000);
           /*object.navigate().refresh();
           Thread.sleep(1000);
		    masters=object.findElement(By.xpath("//*[text()='Masters']"));*/
		    Thread.sleep(1000);
		    Actions a=new Actions(object);
		    a.moveToElement(masters).build().perform();
			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCumCode")));
			 if(object.findElement(By.name("btnAdd")).isEnabled() && object.findElement(By.name("btnEdit")).isEnabled())
			 {
				 try
				 {
					 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnAdd")));
					 object.findElement(By.name("btnAdd")).click();
					 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
					 object.findElement(By.name("BtnSave")).click();
					 Thread.sleep(3000);
					 //wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCumType")));
					 object.findElement(By.name("txtCumType")).sendKeys("by chatting");
					 
					 
				 }
				 catch(Exception ex)
				 {
					 object.switchTo().alert().accept();
					 //wait for auto generation of code in Communication Code
					 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCumType")));
					 object.findElement(By.name("txtCumType")).sendKeys("by chatting");
					 Thread.sleep(3000);
					 object.findElement(By.name("BtnSave")).click();
					 Thread.sleep(3000);
					 try
					 {
						 if(object.findElement(By.xpath("//*[text()='Record Saved Successfully....  ']")).isDisplayed())
						 {
							 s.write("Record Saved Successfully");
						 }
					 }
					 catch(Exception ex1)
					 {
						 s.write("Communication Type already Exist");
					 }
					 //clear data in the fields
					 Thread.sleep(3000);
					 object.findElement(By.name("btnClear")).click(); 
				 }
				 Thread.sleep(3000);
				 object.findElement(By.name("btnEdit")).click();
				 if(object.findElement(By.name("BtnSave")).isEnabled() && object.findElement(By.name("btnDel")).isEnabled() && object.findElement(By.name("btnClear")).isEnabled())
				 {
					 try
					 {
						 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCumCode")));
						 object.findElement(By.name("BtnSave")).click();
						 Thread.sleep(3000);
						 object.findElement(By.name("ddlCumCode")).click();
					 }
					 catch(Exception ex)
					 {
						 object.switchTo().alert().accept();
						 Thread.sleep(3000);
						 object.findElement(By.name("ddlCumCode")).click();
						 WebElement ddl=object.findElement(By.name("ddlCumCode"));
						 Select s1=new Select(ddl);
						 s1.selectByVisibleText("by chatting-8");
						 Thread.sleep(4000);
						 object.findElement(By.name("BtnSave")).click();
						 if(object.findElement(By.xpath("//*[text()='Record Saved Successfully....  ']")).isDisplayed())
						 {
							 s.write("Record Saved Successfully");
						 }
						 else
						 {
							 s.write("Record Not saved Successfully");
						 }
						 Thread.sleep(5000);
						 object.findElement(By.name("btnClear")).click();
					 }
					 Thread.sleep(3000);
					 object.findElement(By.name("btnEdit")).click();
					 Thread.sleep(4000);
					 object.findElement(By.name("ddlCumCode")).click();
					 WebElement ddl=object.findElement(By.name("ddlCumCode"));
					 Select s1=new Select(ddl);
					 s1.selectByVisibleText("by chatting-8");
					 Thread.sleep(3000);
					 object.findElement(By.name("btnDel")).click();
					 object.switchTo().alert().accept();
					 try
					 {
						 if(object.findElement(By.xpath("//*[text()='Record Deleted Successfully']")).isDisplayed())
						 {
							 s.write("Record Successfully Deleted");
						 }
					 }
					 catch(Exception ec)
					 {
						 s.write("Record Doesn't Deleted Successfully");
					 }
					 Thread.sleep(1000);
	    			masters=object.findElement(By.xpath("//*[text()='Masters']"));
	    			//Actions a=new Actions(object);
	    		    a.moveToElement(masters).build().perform();
				 } 
			 }
			 else
			 {
				 String x=ss.screenshot(object);
				s.write("Communication Details Page Test Failed");
				System.exit(0);
			 }
	 }
	 @Then("^validate ICD Benefit Batch Details Page$")
	 public void icd_benefit_batch() throws Exception
	 {
		 //homepage will be displayed
		    /*wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();*/
		   // wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    //move to masters module
		    Thread.sleep(2000);
		    /*masters=object.findElement(By.xpath("//*[text()='Masters']"));
		    Actions a=new Actions(object);
		    a.moveToElement(masters).build().perform();
		    Thread.sleep(2000);*/
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='ICD Benefit Batch']")));
		    object.findElement(By.xpath("//*[text()='ICD Benefit Batch']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtBCode")));
		    Thread.sleep(3000);
		    //if(object.findElement(By.name("btnAdd")).isEnabled() && object.findElement(By.name("btnEdit")).isEnabled())
		   // {
		    	
		    		/*wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnAdd")));
			    	object.findElement(By.name("btnAdd"));
			    	*/
		    		Robot r=new Robot();
		    		r.keyPress(KeyEvent.VK_ALT);
		    		r.keyPress(KeyEvent.VK_A);
		    		r.keyRelease(KeyEvent.VK_A);
		    		r.keyRelease(KeyEvent.VK_ALT);
		    		try
		    		{
		    			Thread.sleep(3000);
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
				    	object.findElement(By.name("BtnSave")).click();
				    	Thread.sleep(3000);
		    			object.switchTo().activeElement().sendKeys("TEST@12");
		    			
		    		}
		    		catch(Exception er)
		    		{
		    			object.switchTo().alert().accept();
		    			Thread.sleep(3000);
		    			object.switchTo().activeElement().sendKeys("TEST@12");
				    	object.findElement(By.name("ddlICDType")).click();
				    	WebElement Icd_Type_dd=object.findElement(By.name("ddlICDType"));
				    	Select s1=new Select(Icd_Type_dd);
				    	s1.selectByVisibleText("BASIC");
				    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDescription")));
				    	object.findElement(By.name("txtDescription")).sendKeys("s");
				    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnFind")));
				    	object.findElement(By.name("btnFind")).click();
				    	try
				    	{
				    		if(object.findElement(By.xpath("//*[contains(text(),'Batch Description Already Exist')]")).isDisplayed())
					    	{
					    		s.write("Batch Description Already Exist.");
					    	}
				    		Thread.sleep(4000);
					    	object.findElement(By.name("btnClear")).click();
				    	}
				    	catch(Exception ey)
				    	{
				    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='A03 - Shigellosis']")));
					    	object.findElement(By.xpath("//*[text()='A03 - Shigellosis']")).click();
					    	object.findElement(By.xpath("//*[text()='A02.1 - Salmonella sepsis']")).click();
					    	object.findElement(By.xpath("//*[contains(text(),'A03.9 - Shigellosis')]")).click();
					    	wait.until(ExpectedConditions.elementToBeClickable(By.name("btnAdd2Grid")));
					    	object.findElement(By.name("btnAdd2Grid")).click();
					    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
					    	object.findElement(By.name("BtnSave")).click();
					    	if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
					    	{
					    		s.write("Record Saved Successfully");
					    	}
					    	Thread.sleep(4000);
					    	object.findElement(By.name("btnClear")).click();
				    	}
		    		}
		    		Thread.sleep(3000);
		    		//if(object.findElement(By.name("btnEdit")).isEnabled())
		    		//{
		    		  /* r.keyPress(KeyEvent.VK_ALT);
		    		   r.keyPress(KeyEvent.VK_E);
		    		   r.keyRelease(KeyEvent.VK_E);
		    		   r.keyRelease(KeyEvent.VK_ALT);*/
		    		   object.findElement(By.name("btnEdit")).click();
		    			try
		    			{
		    				Thread.sleep(3000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
					    	object.findElement(By.name("BtnSave")).click();
					    	Thread.sleep(3000);
			    			object.switchTo().activeElement().sendKeys("TEST@12");
		    			}
		    			catch(Exception et)
		    			{
		    				Thread.sleep(3000);
		    				object.switchTo().alert().accept();
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBCode")));
			    			object.findElement(By.name("ddlBCode")).click();
			    			WebElement icd_code_dd=object.findElement(By.name("ddlBCode"));
			    			Select w=new Select(icd_code_dd);
			    			w.selectByVisibleText("TEST@12-4");
			    			Thread.sleep(4000);
			    			object.findElement(By.name("ddlICDType")).click();
			    			WebElement icd_type_dd=object.findElement(By.name("ddlICDType"));
			    			Select e=new Select(icd_type_dd);
			    			e.selectByVisibleText("BASIC");
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDescription")));
					    	object.findElement(By.name("txtDescription")).sendKeys("s");
					    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnFind")));
					    	object.findElement(By.name("btnFind")).click();
					    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='A03.2 - Shigellosis due to Shigella boydii']")));
					    	object.findElement(By.xpath("//*[text()='A03.2 - Shigellosis due to Shigella boydii']")).click();
					    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnAdd2Grid")));
					    	object.findElement(By.name("btnAdd2Grid")).click();
					    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='ICD Codes'])[2]/following::td[4]")));
					    	object.findElement(By.linkText("Delete")).click();
					    	Thread.sleep(3000);
					    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
					    	object.findElement(By.name("BtnSave")).click();
					    	if(object.findElement(By.xpath("//*[contains(text(),'Record Saved Successfully')]")).isDisplayed())
					    	{
					    		s.write("Record saved Successfully");
					    	}
					    	Thread.sleep(4000);
					    	object.findElement(By.name("btnClear")).click();
					    	
					    	try
					    	{
					    		object.findElement(By.name("btnEdit")).click();
				    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBCode")));
				    			object.findElement(By.name("ddlBCode")).click();
				    			WebElement icd_code_dd1=object.findElement(By.name("ddlBCode"));
				    			Select w1=new Select(icd_code_dd1);
				    			w1.selectByVisibleText("TEST@12-4");
				    			Thread.sleep(4000);
				    			object.findElement(By.name("ddlICDType")).click();
				    			WebElement icd_type_dd2=object.findElement(By.name("ddlICDType"));
				    			Select e1=new Select(icd_type_dd2);
				    			e1.selectByVisibleText("BASIC");
				    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDescription")));
						    	object.findElement(By.name("txtDescription")).sendKeys("s");
						    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnFind")));
						    	object.findElement(By.name("btnFind")).click();
						    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='A25.0 - Spirillosis']")));
						    	object.findElement(By.xpath("//*[text()='A25.0 - Spirillosis']")).click();
						    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnAdd2Grid")));
						    	object.findElement(By.name("btnAdd2Grid")).click();
						    	Thread.sleep(4000);
						    	object.findElement(By.name("btnDel")).click();
						    	//wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnClear")));
						    	object.findElement(By.name("btnClear")).click();
					    	}
					    	catch(Exception ei)
					    	{
					    		Thread.sleep(3000);
					    		object.switchTo().alert().accept();
					    		if(object.findElement(By.xpath("//*[@id='lblResult']")).isDisplayed())
					    		{
					    			//String x=ss.screenshot(object);
					    			Date d=new Date();
					    			SimpleDateFormat sfd=new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
					    			String errName=sfd.format(d);
					    			File src=((TakesScreenshot)object).getScreenshotAs(OutputType.FILE);
					    			File dest=new File("E:\\SE_AutomationScripts\\scripts\\Saudi_Enaya_indexes\\errName");
					    			FileHandler.copy(src,dest);
					    			s.write("Deletion succcessfully done");
					    			Thread.sleep(1000);
					    			masters=object.findElement(By.xpath("//*[text()='Masters']"));
					    			Actions a=new Actions(object);
					    		    a.moveToElement(masters).build().perform();
					    		}
					    	}
		    			}
	 }
	 @Then("^Validate Dental Benefit Batch Details Page$")
	 public void dental_benefit_batch() throws Exception
	 {
		  /*//homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));*/
		    //move to masters module
		   /* Thread.sleep(2000);
		    masters=object.findElement(By.xpath("//*[text()='Masters']"));
		    Actions a=new Actions(object);
		    a.moveToElement(masters).build().perform();*/
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Dental Benefit Batch']")));
		    object.findElement(By.xpath("//*[text()='Dental Benefit Batch']")).click();
		    Thread.sleep(3000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnAdd")));
		    if(object.findElement(By.name("btnAdd")).isEnabled() && object.findElement(By.name("btnEdit")).isEnabled())
		    {
		    	object.findElement(By.name("btnAdd")).click();
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDDescription")));
		    	try
		    	{
		    		object.findElement(By.name("BtnSave")).click();
		    		object.switchTo().activeElement().sendKeys("TEST1");
		    	}
		    	catch(Exception ex)
		    	{
		    		Thread.sleep(3000);
		    		object.switchTo().alert().accept();
		    		object.switchTo().activeElement().sendKeys("TEST1");
			    	object.findElement(By.xpath("//*[text()='K00.0 - Anodontia']")).click();
			    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnAdd2Grid")));
			    	object.findElement(By.name("btnAdd2Grid")).click();
			    	object.findElement(By.xpath("//*[text()='K00.7 - Teething syndrome']")).click();
			    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnAdd2Grid")));
			    	object.findElement(By.name("btnAdd2Grid")).click();
			    	Thread.sleep(3000);
			    	object.findElement(By.name("BtnSave")).click();
			    	try
			    	{
			    		if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')] ")).isDisplayed())
				    	{
				    		s.write("Record Successfully Saved");
				    	}
			    		Thread.sleep(4000);
			    		object.findElement(By.name("btnClear")).click();
			    	}
			    	catch(Exception ex1)
			    	{
			    		if(object.findElement(By.xpath("//*[contains(text(),'Description Already Exist')] ")).isDisplayed())
			    		{
			    			s.write("Batch Description Already Exist");
			    		}
			    		Thread.sleep(4000);
			    		object.findElement(By.name("btnClear")).click();
			    	}
			    	
		    	}
		    	if(object.findElement(By.name("btnEdit")).isEnabled())
		    	{
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnEdit")));
		    			object.findElement(By.name("btnEdit")).click();
		    			object.findElement(By.name("BtnSave")).click();
		    			Thread.sleep(3000);
		    			object.findElement(By.name("ddlDCode")).click();
		    			
		    			
		    		}
		    		catch(Exception ew)
		    		{
		    			Thread.sleep(3000);
		    			object.switchTo().alert().accept();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDCode")));
			    		object.findElement(By.name("ddlDCode")).click();
			    		WebElement dbd_dd=object.findElement(By.name("ddlDCode"));
			    		Select s1=new Select(dbd_dd);
			    		s1.selectByIndex(2);
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='K00.3 - Mottled teeth']")));
			    		object.findElement(By.xpath("//*[text()='K00.3 - Mottled teeth']")).click();
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnAdd2Grid")));
				    	object.findElement(By.name("btnAdd2Grid")).click();
				    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Delete")));
				    	object.findElement(By.linkText("Delete")).click();
				    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
				    	object.findElement(By.name("BtnSave")).click();
				    	try
				    	{
				    		if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
					    	{
					    		s.write("Record Saved Successfully");
					    	}
				    	}
				    	catch(Exception er)
				    	{
				    		System.out.println(er.getMessage());
				    	}
		    		}
		    		Thread.sleep(3000);
		    		object.findElement(By.name("btnClear")).click();
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnEdit")));
		    			object.findElement(By.name("btnEdit")).click();
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDCode")));
			    		object.findElement(By.name("ddlDCode")).click();
			    		WebElement dbd_dd=object.findElement(By.name("ddlDCode"));
			    		Select s1=new Select(dbd_dd);
			    		s1.selectByIndex(2);
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='K00.3 - Mottled teeth']")));
			    		object.findElement(By.xpath("//*[text()='K00.3 - Mottled teeth']")).click();
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnAdd2Grid")));
				    	object.findElement(By.name("btnAdd2Grid")).click();
				    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnDel")));
				    	object.findElement(By.name("btnDel")).click();
				    	Thread.sleep(3000);
				    	object.findElement(By.name("btnDel")).click();
				    	
		    		}
		    		catch(Exception eq)
		    		{
		    			Thread.sleep(3000);
		    			object.switchTo().alert().accept();
		    			if(object.findElement(By.xpath("//*[contains(text(),'Deleted Successfully')]")).isDisplayed())
		    			{
		    				s.write("Record Successfully Deleted");
		    				Thread.sleep(1000);
			    			masters=object.findElement(By.xpath("//*[text()='Masters']"));
			    			Actions a=new Actions(object);
			    		    a.moveToElement(masters).build().perform();
		    			}
		    		}	
		    	}
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
				System.out.println("Dental Batch Details Page Test Failed");
				System.exit(0);
		    }
	 }
	 @Then("^Validate Coverage Masters Page$")
	 public void coverage_masters() throws Exception
	 {
		 /*//homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));*/
		    //move to masters module
		   /* Thread.sleep(2000);
		    masters=object.findElement(By.xpath("//*[text()='Masters']"));
		    Actions a=new Actions(object);
		    a.moveToElement(masters).build().perform();*/
		    Thread.sleep(2000);
		    //click on coverage masters page
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Coverage Master']")));
		    object.findElement(By.xpath("//*[text()='Coverage Master']")).click();
		    //wait for the coverage master page visible
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Coverage Master")));
		    if(object.findElement(By.name("btnAdd")).isEnabled() && object.findElement(By.name("btnEdit")).isEnabled())
		    {
		    	object.findElement(By.name("btnAdd")).click();
		    	Thread.sleep(3000);
		    	try
		    	{
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
		    		object.findElement(By.name("BtnSave")).click();
		    		object.findElement(By.name("TxtGName")).click();
		    	}
		    	catch(Exception ex)
		    	{
		    		//to handle the alert(without entering the header field then click on save button)
		    		Thread.sleep(3000);
		    		object.switchTo().alert().accept();
		    		object.switchTo().activeElement().sendKeys("TESTING");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("TxtGType")));
		    		object.findElement(By.name("TxtGType")).sendKeys("TESTING");
		    		
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
			    		object.findElement(By.name("BtnSave")).click();
			    		try
			    		{
			    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
				    		{
				    			s.write("Record Saved Successfully");
				    		}
			    		}
			    		catch(Exception ex1)
			    		{
			    			if(object.findElement(By.xpath("//*[text()='Header Already Exist']")).isDisplayed())
			    			{
			    				s.write("header already exist");
			    			}
			    		}
			    		Thread.sleep(3000);
			    		object.findElement(By.name("btnClear")).click();
		    		}
		    		
		    	}
		    	if(object.findElement(By.name("btnEdit")).isEnabled())
		    	{
		    		try
		    		{
		    			object.findElement(By.name("btnEdit")).click();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
		    			object.findElement(By.name("BtnSave")).click();
		    			object.findElement(By.name("ddlGroupNo")).click();
		    		}
		    		catch(Exception et)
		    		{
		    			Thread.sleep(3000);
		    			object.switchTo().alert().accept();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlGroupNo")));
		    			object.findElement(By.name("ddlGroupNo")).click();
		    			WebElement cm_Code=object.findElement(By.name("ddlGroupNo"));
		    			Select s1=new Select(cm_Code);
		    			s1.selectByVisibleText("TESTING-17");
		    			Thread.sleep(5000);
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
			    		object.findElement(By.name("BtnSave")).click();
			    		try
			    		{
			    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
				    		{
				    			s.write("Record Edit and saved successfully");
				    		}
			    			Thread.sleep(3000);
			    			object.findElement(By.name("btnClear")).click();
			    		}
			    		catch(Exception e)
			    		{
			    			System.out.println(e.getMessage());
			    		}
		    		}
		    		Thread.sleep(3000);
		    		object.findElement(By.name("btnEdit")).click();
		    		try
		    		{
		    			
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlGroupNo")));
		    			object.findElement(By.name("ddlGroupNo")).click();
		    			WebElement cm_Code=object.findElement(By.name("ddlGroupNo"));
		    			Select s1=new Select(cm_Code);
		    			s1.selectByVisibleText("TESTING-17");
			    		/*object.findElement(By.name("txtsubheader")).sendKeys("Appium");
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtsubdescription")));
			    		object.findElement(By.name("txtsubdescription")).sendKeys("Mobile Automation");
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnsublimitGrid")));
			    		object.findElement(By.name("btnsublimitGrid")).click();
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
			    		object.findElement(By.name("BtnSave")).click();*/
		    			Thread.sleep(3000);
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnDel")));
			    		object.findElement(By.name("btnDel")).click();
			    		object.findElement(By.name("btnEdit")).click();
		    		}
		    		catch(Exception ew)
		    		{
		    			Thread.sleep(3000);
		    			object.switchTo().alert().accept();
		    			try
		    			{
		    				if(object.findElement(By.xpath("//*[contains(text(),'Deleted Successfully')]")).isDisplayed())
		    				{
		    					s.write("Record Deleted Successfully");
		    				}
		    			}
		    			catch(Exception ec)
		    			{
		    				System.out.println(ec.getMessage());
		    			}
		    		}
		    		try
		    		{
		    			Thread.sleep(3000);
		    			object.findElement(By.name("btnEdit")).click();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlGroupNo")));
		    			object.findElement(By.name("ddlGroupNo")).click();
		    			WebElement cm_Code=object.findElement(By.name("ddlGroupNo"));
		    			Select s1=new Select(cm_Code);
		    			s1.selectByVisibleText("TESTING-17");
		    		}
		    		catch(Exception es)
		    		{
		    			s.write("coverage master test passed");
		    			Thread.sleep(1000);
		    			masters=object.findElement(By.xpath("//*[text()='Masters']"));
		    			Actions a=new Actions(object);
		    		    a.moveToElement(masters).build().perform();
		    		}
		    	}
		    	
		    else
		    {
		    	String x=ss.screenshot(object);
		    	s.write("coverage master page test failed");
		    	System.exit(0);
		    }
	 } 
	 @Then("^Validate Coverage Codes Page$")
	 public void coverage_codes() throws Exception
	 {
		 /*//homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));*/
		    //move to masters module
		    Thread.sleep(2000);
		    /*masters=object.findElement(By.xpath("//*[text()='Masters']"));
		    Actions a=new Actions(object);
		    a.moveToElement(masters).build().perform();
		    Thread.sleep(2000);*/
		    //click on coverage codes
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Coverage Codes']")));
		    object.findElement(By.xpath("//*[text()='Coverage Codes']")).click();
		    //wait  for the coverage codes page visible
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Coverages']")));
		    //validate the ADD ,EDIT and DELETE Buttons
		    if(object.findElement(By.name("btnAdd")).isEnabled() && object.findElement(By.name("btnEdit")).isEnabled())
		    {
		    	try
		    	{
		    		try
			    	{
			    		object.findElement(By.name("btnAdd")).click();
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
			    		object.findElement(By.name("BtnSave")).click();
			    		object.findElement(By.name("btnAdd")).click();
			    		
			    	}
			    	catch(Exception ex)
			    	{
			    		Thread.sleep(3000);
			    		object.switchTo().alert().accept();
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBCode")));
				    	object.findElement(By.name("ddlBCode")).click();
				    	WebElement cc_header=object.findElement(By.name("ddlBCode"));
				    	Select s1=new Select(cc_header);
				    	s1.selectByVisibleText("ITEST-19");
				    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlICDType")));
				    	object.findElement(By.name("ddlICDType")).click();
				    	WebElement cc_ICD_Type=object.findElement(By.name("ddlICDType"));
				    	Select s2=new Select(cc_ICD_Type);
				    	s2.selectByVisibleText("BASIC");
				    	try
				    	{
				    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
				    		object.findElement(By.name("BtnSave")).click();
				    		object.findElement(By.name("btnAdd")).click();
				    	}
				    	catch(Exception eq)
				    	{
				    		Thread.sleep(3000);
				    		object.switchTo().alert().accept();
				    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDescription")));
				    		object.findElement(By.name("txtDescription")).sendKeys("s");
				    		try
				    		{
				    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
					    		object.findElement(By.name("BtnSave")).click();
					    		object.findElement(By.name("btnAdd")).click();
				    		}
				    		catch(Exception e)
				    		{
				    			Thread.sleep(3000);
				    			object.switchTo().alert().accept();
				    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnFind")));
				    			object.findElement(By.name("btnFind")).click();
				    			try
				    			{
				    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
						    		object.findElement(By.name("BtnSave")).click();
						    		object.findElement(By.name("btnAdd")).click();
				    			}
				    			catch(Exception ev)
				    			{
				    				Thread.sleep(3000);
				    				object.switchTo().alert().accept();
				    				try
			    					{
			    						wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
							    		object.findElement(By.name("BtnSave")).click();
							    		object.findElement(By.name("btnAdd")).click();
			    					}
				    				catch(Exception er)
				    				{
				    					Thread.sleep(3000);
			    						object.switchTo().alert().accept();
				    					for(int i=5;i<=8;i++)
					    				{
					    					Thread.sleep(3000);
					    					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@name='btnFind']/following::*["+i+"]")));
					    					object.findElement(By.xpath("//*[@name='btnFind']/following::*["+i+"]")).click();wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnAdd2Grid")));
				    						object.findElement(By.name("btnAdd2Grid")).click();
					    				}
				    					Thread.sleep(3000);
				    					object.findElement(By.name("BtnSave")).click();
				    				}//catch closing
				    				
				    			}//catch closing
				    		}//catch closing
				    	}//catch closing
			    	}//catch closing
			    	try
			    	{
			    		if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
				    	{
				    		s.write("Record Saved Successfully");
				    		Thread.sleep(1000);
			    			masters=object.findElement(By.xpath("//*[text()='Masters']"));
			    			Actions a=new Actions(object);
			    		    a.moveToElement(masters).build().perform();
				    	}
			    	}
			    	catch(Exception ei)
			    	{
			    		System.out.println(ei.getMessage());
			    	}
		    	}
		    	catch(Exception er)
		    	{
		    		s.write("Already Saved");
		    		Thread.sleep(1000);
	    			masters=object.findElement(By.xpath("//*[text()='Masters']"));
	    			Actions a=new Actions(object);
	    		    a.moveToElement(masters).build().perform();
		    	}
		    	
		    	
		    }//if closing
		    else
		    {
		    	String x=ss.screenshot(object);
		    	s.write("coverage master page test failed");
		    	System.exit(0);
		    }
	 }
	 @Then("^Validate Loading Masters Page$")
	 public void loading_master() throws Exception
	 {
		 /*//homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));*/
		   /* //move to masters module
		    Thread.sleep(2000);
		    masters=object.findElement(By.xpath("//*[text()='Masters']"));
		    Actions a=new Actions(object);
		    a.moveToElement(masters).build().perform();*/
		    Thread.sleep(2000);
		    //click on loading master
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='Loading Master'])[1]")));
		    object.findElement(By.xpath("(//*[text()='Loading Master'])[1]")).click();
		    //wait for loading master page visible
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='Loading Master'])[2]")));
		    if(object.findElement(By.name("btnAdd")).isEnabled() && object.findElement(By.name("btnEdit")).isEnabled())
		    {
		    	object.findElement(By.name("btnAdd")).click();
		    	try
		    	{
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
		    		object.findElement(By.name("BtnSave")).click();
		    		object.findElement(By.name("txtDescription")).sendKeys("External Fee");
		    	}
		    	catch(Exception ex)
		    	{
		    		Thread.sleep(3000);
		    		object.switchTo().alert().accept();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDescription")));
		    		object.findElement(By.name("txtDescription")).sendKeys("External Fee");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
		    		object.findElement(By.name("BtnSave")).click();
		    		
		    		try
		    		{
		    			if(object.findElement(By.xpath("//*[contains(text(),'Already Exist')]")).isDisplayed())
			    		{
			    			s.write("Loading Description already Exist");
			    		}
		    			Thread.sleep(3000);
		    			object.findElement(By.name("btnClear")).click();
		    		}
		    		catch(Exception er)
		    		{
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    			{
		    				s.write("Record Saved Successfully");
		    			}
		    			Thread.sleep(3000);
		    			object.findElement(By.name("btnClear")).click();
		    		}
		    	}
		    	Thread.sleep(2000);
		    	if(object.findElement(By.name("btnEdit")).isEnabled())
		    	{
		    		object.findElement(By.name("btnEdit")).click();
		    		try
		    		{
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
		    			object.findElement(By.name("BtnSave")).click();
		    			object.findElement(By.name("btnEdit")).click();	
		    		}
		    		catch(Exception et)
		    		{
		    			Thread.sleep(3000);
		    			object.switchTo().alert().accept();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlLoadCode")));
		    			object.findElement(By.name("ddlLoadCode")).click();
		    			WebElement lm_code=object.findElement(By.name("ddlLoadCode"));
		    			Select s1=new Select(lm_code);
		    			s1.selectByVisibleText("External Fee-52");
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
			    		object.findElement(By.name("BtnSave")).click();
			    		if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    			{
		    				s.write("Record Edit and Saved Successfully");
		    			}
		    			Thread.sleep(3000);
		    			object.findElement(By.name("btnClear")).click();
		    		}
		    		Thread.sleep(3000);
		    		object.findElement(By.name("btnEdit")).click();	
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlLoadCode")));
	    			object.findElement(By.name("ddlLoadCode")).click();
	    			WebElement lm_code=object.findElement(By.name("ddlLoadCode"));
	    			Select s1=new Select(lm_code);
	    			s1.selectByVisibleText("External Fee-52");
	    			try
	    			{
	    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnDel")));
	        			object.findElement(By.name("btnDel")).click();
	        			object.findElement(By.name("btnDel")).click();
	    			}
	    			catch(Exception ey)
	    			{
	    				Thread.sleep(3000);
	    				object.switchTo().alert().accept();
	    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Deleted Successfully')]")));
	    				if(object.findElement(By.xpath("//*[contains(text(),'Deleted Successfully')]")).isDisplayed())
	    				{
	    					s.write("Record Delete Successfully");
	    				}
	    			}
	    			try
	    			{
	    				object.findElement(By.name("btnEdit")).click();	
	    	    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlLoadCode")));
	        			object.findElement(By.name("ddlLoadCode")).click();
	        			WebElement lm_code1=object.findElement(By.name("ddlLoadCode"));
	        			Select sl=new Select(lm_code1);
	        			sl.selectByVisibleText("External Fee-52");
	    			}
	    			catch(Exception ei)
	    			{
	    				s.write("Loading Master Test Passed");
	    				Thread.sleep(1000);
		    			masters=object.findElement(By.xpath("//*[text()='Masters']"));
		    			Actions a=new Actions(object);
		    		    a.moveToElement(masters).build().perform();
	    			}
		    	}
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
		    	s.write("coverage master page test failed");
		    	System.exit(0);
		    }
	 }
	 @Then("^Validate AgeBand Masters Page$")
	 public void ageband_masters() throws Exception
	 {
		/*//homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));*/
		    //move to masters module
		    /*Thread.sleep(2000);
		    masters=object.findElement(By.xpath("//*[text()='Masters']"));
		    Actions a=new Actions(object);
		    a.moveToElement(masters).build().perform();*/
		    Thread.sleep(2000);
		    //click on loading master
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='AgeBand Master'])[1]")));
		    object.findElement(By.xpath("(//*[text()='AgeBand Master'])[1]")).click();
		    //wait for loading master page visible
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='AgeBand Master'])[2]")));
		    if(object.findElement(By.name("btnAdd")).isEnabled() && object.findElement(By.name("btnEdit")).isEnabled())
		    {
		    	object.findElement(By.name("btnAdd")).click();
		    	try
		    	{
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
		    		object.findElement(By.name("BtnSave")).click();
		    		object.findElement(By.name("txtFromAge")).sendKeys("0");
		    	}
		    	catch(Exception ex)
		    	{
		    		Thread.sleep(3000);
		    		object.switchTo().alert().accept();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtFromAge")));
		    		object.findElement(By.name("txtFromAge")).sendKeys("0");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtToAge")));
		    		object.findElement(By.name("txtToAge")).sendKeys("24");
		    		object.findElement(By.name("txtDescription")).click();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
		    		object.findElement(By.name("BtnSave")).click();
		    		
		    		
		    		try
		    		{
		    			if(object.findElement(By.xpath("//*[contains(text(),'Already Exist')]")).isDisplayed())
			    		{
			    			s.write("AgeBand Description already Exist");
			    		}
		    			Thread.sleep(3000);
		    			object.findElement(By.name("btnClear")).click();
		    		}
		    		catch(Exception er)
		    		{
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    			{
		    				s.write("Record Saved Successfully");
		    			}
		    			Thread.sleep(3000);
		    			object.findElement(By.name("btnClear")).click();
		    		}
		    	}
		    	Thread.sleep(2000);
		    	if(object.findElement(By.name("btnEdit")).isEnabled())
		    	{
		    		object.findElement(By.name("btnEdit")).click();
		    		try
		    		{
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
		    			object.findElement(By.name("BtnSave")).click();
		    			object.findElement(By.name("btnEdit")).click();	
		    		}
		    		catch(Exception et)
		    		{
		    			Thread.sleep(3000);
		    			object.switchTo().alert().accept();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlAgeBandCode")));
		    			object.findElement(By.name("ddlAgeBandCode")).click();
		    			WebElement lm_code=object.findElement(By.name("ddlAgeBandCode"));
		    			Select s1=new Select(lm_code);
		    			s1.selectByVisibleText("Age Between 0 and 24-32");
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
			    		object.findElement(By.name("BtnSave")).click();
			    		if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    			{
		    				s.write("Record Edit and Saved Successfully");
		    			}
		    			Thread.sleep(3000);
		    			object.findElement(By.name("btnClear")).click();
		    		}
		    		Thread.sleep(3000);
		    		object.findElement(By.name("btnEdit")).click();	
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlAgeBandCode")));
	    			object.findElement(By.name("ddlAgeBandCode")).click();
	    			WebElement lm_code=object.findElement(By.name("ddlAgeBandCode"));
	    			Select s1=new Select(lm_code);
	    			s1.selectByVisibleText("Age Between 0 and 24-32");
	    			try
	    			{
	    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnDel")));
	        			object.findElement(By.name("btnDel")).click();
	        			object.findElement(By.name("btnDel")).click();
	    			}
	    			catch(Exception ey)
	    			{
	    				Thread.sleep(3000);
	    				object.switchTo().alert().accept();
	    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Deleted Successfully')]")));
	    				if(object.findElement(By.xpath("//*[contains(text(),'Deleted Successfully')]")).isDisplayed())
	    				{
	    					s.write("Record Delete Successfully");
	    				}
	    			}
	    			try
	    			{
	    				object.findElement(By.name("btnEdit")).click();	
	    	    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlAgeBandCode")));
	        			object.findElement(By.name("ddlAgeBandCode")).click();
	        			WebElement lm_code1=object.findElement(By.name("ddlAgeBandCode"));
	        			Select sl=new Select(lm_code1);
	        			sl.selectByVisibleText("Age Between 0 and 24-32");
	    			}
	    			catch(Exception ei)
	    			{
	    				s.write("AgeBand Master Test Passed");
	    				Thread.sleep(1000);
		    			masters=object.findElement(By.xpath("//*[text()='Masters']"));
		    			Actions a=new Actions(object);
		    		    a.moveToElement(masters).build().perform();
	    			}
		    	}
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
		    	s.write("AgeBand master page test failed");
		    	System.exit(0);
		    }
	 }
	 @Then("^Validate Stake Holder Masters Page$")
	 public void stake_holder_master() throws Exception
	 {
		 /*//homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));*/
		    //move to masters module
		   /* Thread.sleep(2000);
		    masters=object.findElement(By.xpath("//*[text()='Masters']"));
		    Actions a=new Actions(object);
		    a.moveToElement(masters).build().perform();*/
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Stake Holder Master']")));
		    object.findElement(By.xpath("//*[text()='Stake Holder Master']")).click();
		    Thread.sleep(3000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnAdd")));
		    if(object.findElement(By.name("btnAdd")).isEnabled() && object.findElement(By.name("btnEdit")).isEnabled())
		    {
		    	object.findElement(By.name("btnAdd")).click();
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlType")));
		    	try
		    	{
		    		object.findElement(By.name("BtnSave")).click();
		    		object.switchTo().activeElement().sendKeys("TEST1");
		    	}
		    	catch(Exception ex)
		    	{
		    		Thread.sleep(3000);
		    		object.switchTo().alert().accept();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlType")));
		    		object.findElement(By.name("ddlType")).click();
		    		WebElement sh_type=object.findElement(By.name("ddlType"));
		    		Select s1=new Select(sh_type);
		    		s1.selectByIndex(1);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtName")));
		    		object.findElement(By.name("txtName")).sendKeys("K L ASIF");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtAddress")));
		    		object.findElement(By.name("txtAddress")).sendKeys("KSA");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtAddress1")));
		    		object.findElement(By.name("txtAddress1")).sendKeys("JEDDAH");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCountry")));
		    		object.findElement(By.name("ddlCountry")).click();
		    		WebElement sh_country=object.findElement(By.name("ddlCountry"));
		    		Select s2=new Select(sh_country);
		    		s2.selectByVisibleText("SAUDI ARABIA-966");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCity")));
		    		object.findElement(By.name("ddlCity")).click();
		    		WebElement sh_city=object.findElement(By.name("ddlCity"));
		    		Select s3=new Select(sh_city);
		    		Thread.sleep(2000);
		    		s3.selectByValue("JEDDAH-202");
		    		//s2.selectByIndex(20);
		    		//s2.selectByVisibleText("JEDDAH-202");
			    	object.findElement(By.name("BtnSave")).click();
			    	try
			    	{
			    		if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')] ")).isDisplayed())
				    	{
				    		s.write("Record Successfully Saved");
				    	}
			    		Thread.sleep(4000);
			    		object.findElement(By.name("btnClear")).click();
			    	}
			    	catch(Exception ex1)
			    	{
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'column does not allow nulls. INSERT fails.')]")));
			    		if(object.findElement(By.xpath("//*[contains(text(),'column does not allow nulls. INSERT fails.')]")).isDisplayed())
			    		{
			    			Date d=new Date();
			    			SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
			    			String ssname=sf.format(d)+".png";
			    			JavascriptExecutor js=(JavascriptExecutor)object;
			    			WebElement err_msg=object.findElement(By.xpath("//*[contains(text(),'column does not allow nulls. INSERT fails.')]"));
			    			js.executeScript("arguments[0].style.border='5px red solid';",err_msg);
			    			Thread.sleep(3000);
			    			File src=((TakesScreenshot)object).getScreenshotAs(OutputType.FILE);
			    			File dest=new File("E:\\SE_AutomationScripts\\scripts\\ssname");
			    			FileHandler.copy(src, dest);
			    			s.write("Save Button not allowing");
			    			//System.exit(0);
			    			
			    		}
			    		object.findElement(By.name("btnClear")).click();
			    	}
			    	
		    	}
		    	Thread.sleep(3000);
		    	if(object.findElement(By.name("btnEdit")).isEnabled())
		    	{
		    		
		    		object.findElement(By.name("btnEdit")).click();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCode")));
		    		try
		    		{
		    			Thread.sleep(5000);
		    			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnSave")));
		    			object.findElement(By.name("BtnSave")).click();
		    			object.findElement(By.name("BtnSave")).click();
		    		}
		    		catch(Exception ex)
		    		{
		    			Thread.sleep(3000);
		    			object.switchTo().alert().accept();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCode")));
			    		object.findElement(By.name("ddlCode")).click();
			    		WebElement sh_country=object.findElement(By.name("ddlCode"));
			    		Select s2=new Select(sh_country);
			    		s2.selectByVisibleText("A.C.C. POLYSTYRENE-768");
			    		Thread.sleep(5000);
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
		    			object.findElement(By.name("BtnSave")).click();
		    			Thread.sleep(3000);
		    			try
		    			{
		    				 if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')] ")).isDisplayed())
					    	{
					    		s.write("Record Successfully Saved");
					    	}
				    		Thread.sleep(4000);
				    		object.findElement(By.name("btnClear")).click();
		    			}
		    			catch(Exception er)
		    			{
		    				if(object.findElement(By.xpath("//*[contains(text(),'Cannot insert duplicate key in object')]")).isDisplayed())
		    				{
		    					Date d=new Date();
				    			SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
				    			String ssname=sf.format(d)+".png";
				    			JavascriptExecutor js=(JavascriptExecutor)object;
				    			WebElement err_msg=object.findElement(By.xpath("//*[contains(text(),'Cannot insert duplicate key in object')]"));
				    			js.executeScript("arguments[0].style.border='5px red solid';",err_msg);
				    			Thread.sleep(3000);
				    			File src=((TakesScreenshot)object).getScreenshotAs(OutputType.FILE);
				    			File dest=new File("E:\\SE_AutomationScripts\\scripts\\ssname");
				    			FileHandler.copy(src, dest);
				    			s.write("Edit Button not allowing");
		    				}
		    				Thread.sleep(4000);
				    		object.findElement(By.name("btnClear")).click();
				    		Thread.sleep(1000);
			    			masters=object.findElement(By.xpath("//*[text()='Masters']"));
			    			Actions a=new Actions(object);
			    		    a.moveToElement(masters).build().perform();
		    			}
		    			
		    		}
		    	}
		    	else
		    	{
		    		Date d=new Date();
					SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
					String ssname=sf.format(d)+".png";
					JavascriptExecutor js=(JavascriptExecutor)object;
					WebElement err_msg=object.findElement(By.xpath("//*[contains(text(),'Cannot insert duplicate key in object')]"));
					js.executeScript("arguments[0].style.border='5px red solid';",err_msg);
					Thread.sleep(3000);
					File src=((TakesScreenshot)object).getScreenshotAs(OutputType.FILE);
					File dest=new File("E:\\SE_AutomationScripts\\scripts\\ssname");
					FileHandler.copy(src, dest);
					s.write("stake holder master test failed");
		    	}
		    }
		    else
		    {
		    	Date d=new Date();
				SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
				String ssname=sf.format(d)+".png";
				JavascriptExecutor js=(JavascriptExecutor)object;
				WebElement err_msg=object.findElement(By.xpath("//*[contains(text(),'Cannot insert duplicate key in object')]"));
				js.executeScript("arguments[0].style.border='5px red solid';",err_msg);
				Thread.sleep(3000);
				File src=((TakesScreenshot)object).getScreenshotAs(OutputType.FILE);
				File dest=new File("E:\\SE_AutomationScripts\\scripts\\ssname");
				FileHandler.copy(src, dest);
				s.write("stake holder master test failed");
		    }
	 }
	 @Then("^Validate Company Details Page$")
	 public void company_masters() throws Exception
	 {
		/* //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));*/
		    //move to masters module
		   /* Thread.sleep(2000);
		    masters=object.findElement(By.xpath("//*[text()='Masters']"));
		    Actions a=new Actions(object);
		    a.moveToElement(masters).build().perform();*/
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Company Master']")));
		    object.findElement(By.xpath("//*[text()='Company Master']")).click();
		    Thread.sleep(3000);
		    if(object.findElement(By.name("btnAdd")).isEnabled() && object.findElement(By.name("btnEdit")).isEnabled())
		    {
		    	object.findElement(By.name("btnAdd")).click();
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtBCode")));
		    	try
		    	{
		    		object.findElement(By.name("BtnSave")).click();
		    		object.switchTo().activeElement().sendKeys("TEST1");
		    	}
		    	catch(Exception ex)
		    	{
		    		Thread.sleep(3000);
		    		object.switchTo().alert().accept();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtBCode")));
		    		object.switchTo().activeElement().sendKeys("ITEST");
		    		try
			    	{
			    		object.findElement(By.name("BtnSave")).click();
			    		object.switchTo().activeElement().sendKeys("TEST1");
			    	}
		    		catch(Exception ey)
		    		{
		    			Thread.sleep(3000);
		    			object.switchTo().alert().accept();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtAddress1")));
		    			object.findElement(By.name("txtAddress1")).sendKeys("KSA");
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtAddress2")));
		    			object.findElement(By.name("txtAddress2")).sendKeys("JEDDAH");
		    			try
				    	{
				    		object.findElement(By.name("BtnSave")).click();
				    		object.switchTo().activeElement().sendKeys("TEST1");
				    	}
		    			catch(Exception ew)
		    			{
		    				Thread.sleep(3000);
		    				object.switchTo().alert().accept();
		    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtPhone")));
		    				object.findElement(By.name("txtPhone")).sendKeys("9856743021");
		    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtEmail")));
		    				object.findElement(By.name("txtEmail")).sendKeys("kummaril@gmail.com");
		    				try
					    	{
					    		object.findElement(By.name("BtnSave")).click();
					    		object.switchTo().activeElement().sendKeys("TEST1");
					    	}
		    				catch(Exception er)
		    				{
		    					Thread.sleep(3000);
		    					object.switchTo().alert().accept();
		    					wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCountry")));
		    		    		object.findElement(By.name("ddlCountry")).click();
		    		    		WebElement cm_country=object.findElement(By.name("ddlCountry"));
		    		    		Select s2=new Select(cm_country);
		    		    		s2.selectByVisibleText("INDIA - 91");
		    		    		try
						    	{
						    		object.findElement(By.name("BtnSave")).click();
						    		object.switchTo().activeElement().sendKeys("TEST1");
						    	}
		    		    		catch(Exception eq)
		    		    		{
		    		    			Thread.sleep(3000);
		    		    			object.switchTo().alert().accept();
			    		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCity")));
			    		    		Thread.sleep(2000);
			    		    		object.findElement(By.name("ddlCity")).click();
			    		    		WebElement cm_city=object.findElement(By.name("ddlCity"));
			    		    		Select s3=new Select(cm_city);
			    		    		Thread.sleep(3000);
			    		    	     s3.selectByValue("DELHI - 11");
			    		    		//s3.selectByIndex(0);
			    		    		//s2.selectByVisibleText("JEDDAH-202");
			    		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
			    			    	object.findElement(By.name("BtnSave")).click();
			    			    	Thread.sleep(2000);
			    			    	try
			    			    	{
			    			    		if(object.findElement(By.xpath("//*[contains(text(),'Saved Sucessfully')] ")).isDisplayed())
			    				    	{
			    				    		s.write("Record Successfully Saved");
			    				    	}
			    			    		Thread.sleep(4000);
			    			    		object.findElement(By.name("btnClear")).click();
			    			    	}
			    			    	catch(Exception er1)
			    			    	{
			    			    		if(object.findElement(By.xpath("//*[contains(text(),'Already Exist')]")).isDisplayed())
			    			    		{
			    			    			s.write("Company name Already exist");
			    			    		}
			    			    		Thread.sleep(4000);
			    			    		object.findElement(By.name("btnClear")).click();
			    			    	}
		    		    		}//catch closing
		    				}//catch closing
		    			}//catch closing
		    		}//catch closing
		    	}//catch closing
		    	Thread.sleep(3000);
		    	if(object.findElement(By.name("btnEdit")).isEnabled())
		    	{
		    		object.findElement(By.name("btnEdit")).click();
			    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBCode")));
			    	try
			    	{
			    		object.findElement(By.name("BtnSave")).click();
			    		object.switchTo().activeElement().sendKeys("TEST1");
			    	}
			    	catch(Exception ew)
			    	{
			    		Thread.sleep(3000);
			    		object.switchTo().alert().accept();
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBCode")));
			    		object.findElement(By.name("ddlBCode")).click();
			    		WebElement cm_cmpny_code=object.findElement(By.name("ddlBCode"));
			    		Select s3=new Select(cm_cmpny_code);
			    		s3.selectByVisibleText("ITEST-2");
			    		Thread.sleep(5000);
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
				    	object.findElement(By.name("BtnSave")).click();
				    	Thread.sleep(2000);
				    	try
				    	{
				    		if(object.findElement(By.xpath("//*[contains(text(),'Saved Sucessfully')]")).isDisplayed())
					    	{
					    		s.write("Record Successfully Saved");
					    	}
				    		Thread.sleep(4000);
				    		object.findElement(By.name("btnClear")).click();
				    		Thread.sleep(1000);
			    			masters=object.findElement(By.xpath("//*[text()='Masters']"));
			    			Actions a=new Actions(object);
			    		    a.moveToElement(masters).build().perform();
				    	}
				    	catch(Exception es)
				    	{
				    		System.out.println(es.getMessage());
				    		Thread.sleep(1000);
			    			masters=object.findElement(By.xpath("//*[text()='Masters']"));
			    			Actions a=new Actions(object);
			    		    a.moveToElement(masters).build().perform();
				    	}
			    	}
		    	}
		    	else
		    	{
		    		String x=ss.screenshot(object);
					s.write("company master test failed");
		    	}
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
				s.write("company master test failed");
		    }
	 }
	 @Then("^Validate Regulatory Master Page$")
	 public void regulatory_master() throws Exception
	 {
		  /*//homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));*/
		    //move to masters module
		 /*Thread.sleep(2000);
		    //WebElement master=object.findElement(By.xpath("//*[text()='Masters']"));
		    Actions a=new Actions(object);
		    a.moveToElement(masters).build().perform();*/
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Regulatory Master']")));
		    object.findElement(By.xpath("//*[text()='Regulatory Master']")).click();
		    Thread.sleep(3000);
		    if(object.findElement(By.name("btnAdd")).isEnabled() && object.findElement(By.name("btnEdit")).isEnabled())
		    {
		    	object.findElement(By.name("btnAdd")).click();
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtRegCode")));
		    	try
		    	{
		    		object.findElement(By.name("BtnSave")).click();
		    		object.switchTo().activeElement().sendKeys("TEST1");
		    	}
		    	catch(Exception ex)
		    	{
		    		Thread.sleep(3000);
		    		object.switchTo().alert().accept();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtRegCode")));
		    		object.switchTo().activeElement().sendKeys("WHO");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDescription")));
		    		object.findElement(By.name("txtDescription")).sendKeys("World Health Organization");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
		    		object.findElement(By.name("BtnSave")).click();
		    		Thread.sleep(3000);
		    		try
		    		{
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
			    		{
			    			s.write("Record added successfully...");
			    		}
		    			Thread.sleep(2000);
		    			object.findElement(By.name("btnClear")).click();
		    		}
		    		catch(Exception ex1)
		    		{
		    			if(object.findElement(By.xpath("//*[contains(text(),'Already Exists.')]")).isDisplayed())
			    		{
			    			s.write("Regulatory code already exists..");
			    		}
		    			Thread.sleep(2000);
		    			object.findElement(By.name("btnClear")).click();
		    		}
		    	}
		    	Thread.sleep(3000);
		    	if(object.findElement(By.name("btnEdit")).isEnabled())
		    	{
		    		object.findElement(By.name("btnEdit")).click();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlRegCode")));
		    		try
			    	{
			    		object.findElement(By.name("BtnSave")).click();
			    		object.switchTo().activeElement().sendKeys("TEST1");
			    	}
		    		catch(Exception et)
		    		{
		    			Thread.sleep(3000);
		    			object.switchTo().alert().accept();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlRegCode")));
			    		object.findElement(By.name("ddlRegCode")).click();
			    		WebElement rm_code=object.findElement(By.name("ddlRegCode"));
			    		Select s3=new Select(rm_code);
			    		s3.selectByVisibleText("WHO");
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnSave")));
			    		object.findElement(By.name("BtnSave")).click();
			    		Thread.sleep(3000);
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
			    		{
			    			s.write("Record edited and saved  successfully...");
			    		}
		    			Thread.sleep(2000);
		    			object.findElement(By.name("btnClear")).click();
		    		}
		    		Thread.sleep(2000);
		    		object.findElement(By.name("btnEdit")).click();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlRegCode")));
		    		object.findElement(By.name("ddlRegCode")).click();
		    		WebElement rm_code=object.findElement(By.name("ddlRegCode"));
		    		Select s3=new Select(rm_code);
		    		s3.selectByVisibleText("WHO");
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnDel")));
		    			object.findElement(By.name("btnDel")).click();
		    			object.findElement(By.name("btnDel")).click();
		    		}
		    		catch(Exception ew)
		    		{
		    			Thread.sleep(3000);
		    			object.switchTo().alert().accept();
		    			if(object.findElement(By.xpath("//*[contains(text(),'Deleted Successfully')]")).isDisplayed())
		    			{
		    				s.write("Record Delete Successfully");
		    				try
		    				{
		    					Thread.sleep(2000);
		    		    		object.findElement(By.name("btnEdit")).click();
		    		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlRegCode")));
		    		    		object.findElement(By.name("ddlRegCode")).click();
		    		    		WebElement rm_code1=object.findElement(By.name("ddlRegCode"));
		    		    		Select s1=new Select(rm_code1);
		    		    		s3.selectByVisibleText("WHO");
		    				}
		    				catch(Exception eq)
		    				{
		    					s.write("Regulatory Master Page passed");
		    					Thread.sleep(1000);
				    			masters=object.findElement(By.xpath("//*[text()='Masters']"));
				    			Actions a=new Actions(object);
				    		    a.moveToElement(masters).build().perform();
		    				}
		    			}
		    		}
		    	}
		    	else
		    	{
		    		String x=ss.screenshot(object);
					s.write("company master test failed");
		    	}
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
				s.write("company master test failed");
		    }
	 }
	 @Then("^Validate SalesPoint Master Page$")
	 public void sales_Point_Master() throws Exception
	 {
		/*//homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);*/
		    //move to masters module
		    //WebElement master=object.findElement(By.xpath("//*[text()='Masters']"));
		    /*Actions a=new Actions(object);
		    a.moveToElement(masters).build().perform();*/
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Sales Point Master']")));
		    object.findElement(By.xpath("//*[text()='Sales Point Master']")).click();
		    Thread.sleep(3000);
		    wait.until(ExpectedConditions.visibilityOf(bp.addButton));
		    if(bp.addButton.isEnabled() && bp.editButton.isEnabled())
		    {
		    	object.findElement(By.name("btnAdd")).click();
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtSPName")));
		    	try
		    	{
		    		object.findElement(By.name("BtnSave")).click();
		    		object.switchTo().activeElement().sendKeys("TEST1");
		    	}
		    	catch(Exception ex)
		    	{
		    		Thread.sleep(3000);
		    		object.switchTo().alert().accept();
		    		try
		    		{
		    			object.switchTo().activeElement().sendKeys("Lokesh123");
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlSalesEntityName")));
		    			object.findElement(By.name("ddlSalesEntityName")).click();
		    			WebElement sales_entity_dd=object.findElement(By.name("ddlSalesEntityName"));
		    			Select s1=new Select(sales_entity_dd);
		    			s1.selectByVisibleText("LOKESHK-14142");
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtAddress1")));
						object.findElement(By.name("txtAddress1")).sendKeys("JEDDAH");
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtAddress2")));
						object.findElement(By.name("txtAddress2")).sendKeys("Saudi Arabia");
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCountry")));
						object.findElement(By.name("ddlCountry")).click();
						WebElement country_dd=object.findElement(By.name("ddlCountry"));
						Select s2=new Select(country_dd);
						s2.selectByVisibleText("SAUDI ARABIA-966");
						Thread.sleep(3000);
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCity")));
						object.findElement(By.name("ddlCity")).click();
						WebElement city_dd=object.findElement(By.name("ddlCity"));
						Select s3=new Select(city_dd);
						s3.selectByValue("JEDDAH-202");
						//s3.selectByVisibleText("JEDDAH-202");
						wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    			try
		    			{
		    				if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    				{
		    					s.write("Record Saved Successfully");
		    				}
		    				Thread.sleep(3000);
		    				wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    				bp.click_clearButton();
		    			}
		    			catch(Exception er)
		    			{
		    				if(object.findElement(By.xpath("//*[text()='Invalid procedure call or argument']")).isDisplayed())
		    				{
		    					s.write("Saved but not Displaying message");
		    				}
		    				Thread.sleep(3000);
		    				wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    				bp.click_clearButton();
		    			}
						
		    		}
		    		catch(Exception ew)
		    		{
		    			System.out.println(ex.getMessage());
		    		}    	
		    	}
		    	wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    	if(bp.editButton.isEnabled())
		    	{
		    		wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    		bp.click_editButton();
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    			bp.click_saveButton();
		    			bp.click_editButton();
		    		}
		    		catch(Exception ey)
		    		{
		    			Thread.sleep(3000);
		    			object.switchTo().alert().accept();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlSPCode")));
		    			object.findElement(By.name("ddlSPCode")).click();
		    			WebElement sp_dd=object.findElement(By.name("ddlSPCode"));
		    		    sp_dd.click();
		    		    sp_dd.sendKeys("lok");
		    		    Thread.sleep(2000);
		    		    Actions a=new Actions(object);
		    		    a.sendKeys(Keys.ENTER).build().perform();
		    			Thread.sleep(5000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    			bp.click_saveButton();
		    			Thread.sleep(2000);
		    			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("lblResult")));
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    			{
		    				s.write("Record Edit and Saved Successfully");
		    			}
		    			Thread.sleep(3000);
	    				wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
	    				bp.click_clearButton();
	    				try
	    				{
	    					wait.until(ExpectedConditions.visibilityOf(bp.editButton));
	    		    		bp.click_editButton();
	    					wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlSPCode")));
	    	    			object.findElement(By.name("ddlSPCode")).click();
	    	    			WebElement sp1_dd=object.findElement(By.name("ddlSPCode"));
	    	    			sp1_dd.click();
	    	    			sp1_dd.sendKeys("lok");
			    		    Thread.sleep(2000);
			    		    a.sendKeys(Keys.ENTER).build().perform();
	    	    			Thread.sleep(5000);
	    	    			wait.until(ExpectedConditions.visibilityOf(bp.deleteButton));
	    	    			bp.click_deleteButton();
	    	    			bp.click_saveButton();
	    				}
	    				catch(Exception eq)
	    				{
	    					Thread.sleep(3000);
	    					object.switchTo().alert().accept();
	    					if(object.findElement(By.xpath("//*[contains(text(),'Deleted Successfully')]")).isDisplayed())
	    					{
	    						s.write("Record Deleted Successfully");
	    					}
	    				}
	    				Thread.sleep(3000);
	    				try
	    				{
	    					wait.until(ExpectedConditions.visibilityOf(bp.editButton));
	    		    		bp.click_editButton();
	    					wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlSPCode")));
	    	    			object.findElement(By.name("ddlSPCode")).click();
	    	    			WebElement sp1_dd=object.findElement(By.name("ddlSPCode"));
	    	    			sp1_dd.click();
	    	    			sp1_dd.sendKeys("lok");
			    		    Thread.sleep(2000);
	    				}
	    				catch(Exception eq)
	    				{
	    					s.write("Sales Point Master Test Passed");
	    					Thread.sleep(1000);
			    			masters=object.findElement(By.xpath("//*[text()='Masters']"));
			    			//Actions a=new Actions(object);
			    		    a.moveToElement(masters).build().perform();
	    				}		
		    		}
		    	}
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
				s.write("Sales Point master Page Test Failed");
				System.exit(0);
		    }
	 }
	 @Then("^Validate Work Location Master Page$")
	 public void work_location_master() throws Exception
	 {
		/*//homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);*/
		    //move to masters module
		    //WebElement master=object.findElement(By.xpath("//*[text()='Masters']"));
		    Actions a=new Actions(object);
		   // a.moveToElement(masters).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Work Location Master']")));
		    object.findElement(By.xpath("//*[text()='Work Location Master']")).click();
		    Thread.sleep(3000);
		    wait.until(ExpectedConditions.visibilityOf(bp.addButton));
		    if(bp.addButton.isEnabled() && bp.editButton.isEnabled())
		    {
		    	object.findElement(By.name("btnAdd")).click();
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtLocCode")));
		    	try
		    	{
		    		bp.click_saveButton();
		    		object.switchTo().activeElement().sendKeys("IND");
		    	}
		    	catch(Exception ex)
		    	{
		    		Thread.sleep(3000);
		    		object.switchTo().alert().accept();
		    		object.switchTo().activeElement().sendKeys("IND");
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		try
		    		{
		    			Thread.sleep(3000);
		    			object.switchTo().alert().accept();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDescription")));
		    			object.findElement(By.name("txtDescription")).sendKeys("INDIA");
		    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
			    		bp.click_saveButton();
			    		Thread.sleep(2000);
			    		try
			    		{
			    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
				    		{
				    			s.write("Record saved Successfully");
				    		}
			    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
			    			bp.click_clearButton();
			    		}
			    		catch(Exception ew)
			    		{
			    			if(object.findElement(By.xpath("//*[contains(text(),'Already Exists.')]")).isDisplayed())
			    			{
			    				s.write("Location Code Already Exists.");
			    			}
			    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
			    			bp.click_clearButton();
			    		}				
		    		}
		    		catch(Exception ew)
		    		{
		    			System.out.println(ex.getMessage());
		    		}    	
		    	}
		    	Thread.sleep(3000);
		    	wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    	if(bp.editButton.isEnabled())
		    	{
		    		wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    		bp.click_editButton();
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    			bp.click_saveButton();
		    			bp.click_editButton();
		    		}
		    		catch(Exception er)
		    		{
		    			Thread.sleep(3000);
		    			object.switchTo().alert().accept();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlLocCode")));
		    			object.findElement(By.name("ddlLocCode")).click();
		    			WebElement location_dd=object.findElement(By.name("ddlLocCode"));
		    			Select s2=new Select(location_dd);
		    			s2.selectByVisibleText("IND");
		    			try
		    			{
		    				Thread.sleep(3000);
		    				if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    				{
		    					s.write("Record Edit and Saved Successfully");
		    				}
		    				wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
			    			bp.click_clearButton();
		    			}
		    			catch(Exception et)
		    			{
		    				System.out.println(et.getMessage());
		    			}
		    		}
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    		bp.click_editButton();
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlLocCode")));
		    			object.findElement(By.name("ddlLocCode")).click();
		    			WebElement location_dd=object.findElement(By.name("ddlLocCode"));
		    			Select s2=new Select(location_dd);
		    			s2.selectByVisibleText("IND");
		    			wait.until(ExpectedConditions.visibilityOf(bp.deleteButton));
		    			bp.click_deleteButton();
		    			bp.click_saveButton();
		    		}
		    		catch(Exception eq)
		    		{
		    			Thread.sleep(3000);
		    			object.switchTo().alert().accept();
		    			try
		    			{
		    				Thread.sleep(4000);
		    				if(object.findElement(By.xpath("//*[contains(text(),'Deleted Successfully')]")).isDisplayed())
		    				{
		    					s.write("Record Deleted Successfully");
		    				}
		    			}
		    			catch(Exception ew)
		    			{
		    				System.out.println(ew.getMessage());
		    			}
		    		}
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOf(bp.editButton));
			    		bp.click_editButton();
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlLocCode")));
		    			object.findElement(By.name("ddlLocCode")).click();
		    			WebElement location_dd=object.findElement(By.name("ddlLocCode"));
		    			Select s2=new Select(location_dd);
		    			s2.selectByVisibleText("IND");
		    		}
		    		catch(Exception er)
		    		{
		    			s.write("Work Location Master Page Test Passed");
		    			Thread.sleep(1000);
		    			masters=object.findElement(By.xpath("//*[text()='Masters']"));
		    			//Actions a=new Actions(object);
		    		    a.moveToElement(masters).build().perform();
		    		}
		    	}
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
				s.write("Work Location master Page Test Failed");
				System.exit(0);
		    }
	 }
	 @Then("^Validate Roles Master$")
	 public void roles_Master_page() throws Exception
	 {
		/*//homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);*/
		    //move to masters module
		    //WebElement master=object.findElement(By.xpath("//*[text()='Masters']"));
		    Actions a=new Actions(object);
		   // a.moveToElement(masters).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Roles Master']")));
		    object.findElement(By.xpath("//*[text()='Roles Master']")).click();
		    Thread.sleep(4000);
		    if(bp.editButton.isEnabled() && bp.addButton.isEnabled())
		    {
		    	wait.until(ExpectedConditions.visibilityOf(bp.addButton));
		    	object.findElement(By.name("btnAdd")).click();
		    	Thread.sleep(1000);
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtRoleId")));
		    	String roleId=object.findElement(By.name("txtRoleId")).getAttribute("value");
		    	System.out.println(roleId);
		    	Thread.sleep(1000);
		    	try
		    	{
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtRoleName")));
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		bp.click_addButton();
		    	}
		    	catch(Exception ew)
		    	{
		    		Thread.sleep(3000);
		    		object.switchTo().alert().accept();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Role Name']/following::input[1]")));
		    		object.findElement(By.xpath("//*[text()='Role Name']/following::input[1]")).sendKeys("LOKII");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlMenuType")));
		    		object.findElement(By.name("ddlMenuType")).click();
		    		WebElement login_type_dd=object.findElement(By.name("ddlMenuType"));
		    		Select s2=new Select(login_type_dd);
		    		s2.selectByIndex(1);
		    		Thread.sleep(3000);
		    		object.findElement(By.name("ddlModule")).click();
		    		WebElement module_dd=object.findElement(By.name("ddlModule"));
		    		Select s3=new Select(module_dd);
		    		s3.selectByIndex(2);
		    		Thread.sleep(5000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(text(),'Add')])[6]/input")));
		    		object.findElement(By.xpath("(//*[contains(text(),'Add')])[6]/input")).click();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(text(),'Edit')])[2]/input")));
		    		object.findElement(By.xpath("(//*[contains(text(),'Edit')])[2]/input")).click();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(text(),'Delete')])[2]/input")));
		    		object.findElement(By.xpath("(//*[contains(text(),'Delete')])[2]/input")).click();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(text(),'View')])[2]/input")));
		    		object.findElement(By.xpath("(//*[contains(text(),'View')])[2]/input")).click();
		    		Thread.sleep(3000);
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		try
		    		{
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    			{
		    				s.write("Record Saved Successfully");
		    			}
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    		catch(Exception eq)
		    		{
		    			if(object.findElement(By.xpath("//*[contains(text(),'Already Exists')]")).isDisplayed())
		    			{
		    				s.write("Role Name Already Exists");
		    			}
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}	
		    	}
		    	Thread.sleep(2000);
		    	if(bp.editButton.isEnabled())
		    	{
		    		bp.click_editButton();
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlRoleId")));
		    			object.findElement(By.name("btnFind")).click();
		    			bp.click_editButton();
		    		}
		    		catch(Exception eq)
		    		{
		    			Thread.sleep(3000);
		    			object.switchTo().alert().accept();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlRoleId")));
		    			object.findElement(By.name("ddlRoleId")).click();
		    			/*WebElement role_Id_dd=object.findElement(By.name("ddlRoleId"));
		    			role_Id_dd.click();*/
		    			Thread.sleep(2000);
		    			List<WebElement> dept_dd=object.findElements(By.xpath("//*[@name='ddlRoleId']/option"));
		    			System.out.println(dept_dd.size());
		    			Thread.sleep(2000);
		    			for(int i=2;i<=dept_dd.size();i++)
		    			{
		    				a.sendKeys(Keys.DOWN).build().perform();
		    				Thread.sleep(2000);
		    				String ac_role=object.findElement(By.xpath("//*[@name='ddlRoleId']/option["+i+"]")).getText();
		    				Thread.sleep(2000);
		    				if(ac_role.contains(roleId))
		    				{
		    					a.sendKeys(Keys.ENTER).build().perform();
		    					break;
		    				}
		    			}
		    			a.sendKeys(Keys.ENTER).build().perform();
		    			Thread.sleep(1000);
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnFind")));
		    			object.findElement(By.name("btnFind")).click();
		    			Thread.sleep(5000);
		    			object.findElement(By.name("ddlModule")).click();
			    		WebElement module_dd1=object.findElement(By.name("ddlModule"));
			    		Select s4=new Select(module_dd1);
			    		s4.selectByIndex(2);
			    		Thread.sleep(2000);
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(text(),'View')])[3]/input[@id='chkAllItemsV']")));
			    		object.findElement(By.xpath("(//*[contains(text(),'View')])[3]/input[@id='chkAllItemsV']")).click();
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(text(),'View')])[3]/input[@id='chkAllItemsV']")));
			    		object.findElement(By.xpath("(//*[contains(text(),'View')])[3]/input[@id='chkAllItemsV']")).click();
			    		Thread.sleep(3000);
			    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
			    		bp.click_saveButton();
			    		try
			    		{
			    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
			    			{
			    				s.write("Record Saved Successfully");
			    			}
			    			Thread.sleep(2000);
			    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
			    			bp.click_clearButton();
			    			Thread.sleep(1000);
			    			masters=object.findElement(By.xpath("//*[text()='Masters']"));
			    			//Actions a=new Actions(object);
			    		    a.moveToElement(masters).build().perform();
			    		}
			    		catch(Exception et)
			    		{
			    			System.out.println(et.getMessage());
			    		}	
		    		}
		    	}	
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
		    	System.out.println("Roles Master Test failed");
		    }
	 }
	 @Then("^Validate User Master$")
	 public void user_master() throws Exception
	 {
		/* wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		 object.findElement(By.xpath("//*[text()='Close']")).click();
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		 Actions a=new Actions(object);*/
		 //move to masters module
		 //WebElement master=object.findElement(By.xpath("//*[text()='Masters']"));
		 Actions a=new Actions(object);
		 //a.moveToElement(masters).build().perform();
		 Thread.sleep(2000);
		 //click on currency master
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='User Master']")));
		 object.findElement(By.xpath("//*[text()='User Master']")).click();
		 Thread.sleep(2000);
		 if(bp.addButton.isEnabled() && bp.editButton.isEnabled())
		 {
			 wait.until(ExpectedConditions.visibilityOf(bp.addButton));
			 bp.click_addButton();
			 Thread.sleep(2000);
			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtUName")));
			 object.findElement(By.name("txtUName")).sendKeys("TESTING12");
			 Thread.sleep(1000);
			 try
			 {
				 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtUserName")));
				 object.findElement(By.name("txtUserName")).sendKeys("testAmtpl901"); 
				 object.findElement(By.name("txtPassword")).sendKeys("Lokesh427");
				 Thread.sleep(3000);
				 object.switchTo().alert().accept();
				 object.switchTo().alert().accept();
				 Thread.sleep(2000);
				 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnAvialability")));
				 object.findElement(By.name("btnAvialability")).click();
				 Thread.sleep(2000);
				 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Available']")));
				 if(object.findElement(By.xpath("//*[text()='Available']")).isDisplayed())
				 {
					 Thread.sleep(1000);
					 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtPassword")));
					 object.findElement(By.name("txtPassword")).sendKeys("Lokesh427");
					 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCPassword")));
					 object.findElement(By.name("txtCPassword")).sendKeys("Lokesh427");
					 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtEmailId")));
					 object.findElement(By.name("txtEmailId")).sendKeys("lokesh.kummari@amtpl.com");
					 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlLoginType")));
					 WebElement loginType=object.findElement(By.name("ddlLoginType"));
					 Thread.sleep(1000);
					 loginType.click();
					 Thread.sleep(1000);
					 Select s1=new Select(loginType);
					 s1.selectByVisibleText("GROUP");
					 loginType.sendKeys(Keys.ESCAPE);
					 Thread.sleep(4000);
					 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlRoles")));
					 WebElement rolesType=object.findElement(By.name("ddlRoles"));
					 Thread.sleep(1000);
					 rolesType.click();
					 Thread.sleep(2000);
					 object.findElement(By.xpath("//*[text()='SUPER USER-1']/input[@class='rcbCheckBox']")).click();
					 Thread.sleep(2000);
					 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("chkBatchPostAcess")));
					 object.findElement(By.name("chkBatchPostAcess")).click();
					 Thread.sleep(1000);
					 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlStatus")));
					 object.findElement(By.name("ddlStatus")).click();
					 WebElement statusDecision=object.findElement(By.name("ddlStatus"));
					 Thread.sleep(1000);
					 Select s2=new Select(statusDecision);
					 s2.selectByVisibleText("ACTIVE");
					 statusDecision.sendKeys(Keys.ESCAPE);
					 Thread.sleep(2000);
					 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(@name,'txtRptfDate')])[2]")));
					 object.findElement(By.xpath("(//*[contains(@name,'txtRptfDate')])[2]")).sendKeys("28/03/2019");
					 Thread.sleep(1000);
					 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuoteLoadings")));
					 WebElement quoteLoadings=object.findElement(By.name("ddlQuoteLoadings"));
					 Thread.sleep(1000);
					 quoteLoadings.click();
					 Thread.sleep(1000);
					 Select s3=new Select(quoteLoadings);
					 s3.selectByVisibleText("Enable");
					 Thread.sleep(2000);
					 wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
					 bp.click_saveButton();
					 Thread.sleep(2000);
					 if(object.findElement(By.xpath("//*[contains(text(),'User Created Successfully')]")).isDisplayed())
					 {
						 s.write("New User Added Successfully");
						 et.log(LogStatus.PASS,"New User Added Successfully");
						 Thread.sleep(2000);
						 wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
						 bp.click_clearButton(); 
						 Thread.sleep(2000);
						 if(bp.editButton.isEnabled())
						 {
							 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnEdit")));
					    	object.findElement(By.name("btnEdit")).click();
					    	Thread.sleep(1000);
					    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlUserID")));
					    	object.findElement(By.name("ddlUserID")).sendKeys("testAmtpl901");
					    	//Actions a=new Actions(object);
					    	Thread.sleep(2000);
					    	a.sendKeys(Keys.DOWN).build().perform();
					    	Thread.sleep(1000);
					    	a.sendKeys(Keys.ENTER).build().perform();
					    	Thread.sleep(1000);
						 }
						 wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
						 bp.click_clearButton();
						 Thread.sleep(1000);
		    			masters=object.findElement(By.xpath("//*[text()='Masters']"));
		    			//Actions a=new Actions(object);
		    		    a.moveToElement(masters).build().perform();
					 }
					  
				 }
				 else
				 {
					 Thread.sleep(2000);
					 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Not available']")));
					 if(object.findElement(By.xpath("//*[text()='Not available']")).isDisplayed())
					 {
						 s.write("user already created");
						 et.log(LogStatus.PASS,"user already created");
						 Thread.sleep(2000);
						 if(bp.editButton.isEnabled())
						 {
							 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnEdit")));
					    	object.findElement(By.name("btnEdit")).click();
					    	Thread.sleep(1000);
					    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlUserID")));
					    	object.findElement(By.name("ddlUserID")).sendKeys("testAmtpl901");
					    	//Actions a=new Actions(object);
					    	Thread.sleep(2000);
					    	a.sendKeys(Keys.DOWN).build().perform();
					    	Thread.sleep(1000);
					    	a.sendKeys(Keys.ENTER).build().perform();
					    	Thread.sleep(1000);
						 }
						 wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
						 bp.click_clearButton();
					 }
					 
				 }
				 
			 }
			 catch(Exception ex)
			 {
				 System.out.println(ex.getMessage());
			 }
		 }
	 }
	 @Then("^Validate  Assign Master Page$")
	 public void assign_master() throws Exception
	 {
		/*//homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);*/
		    //move to masters module
		    //WebElement master=object.findElement(By.xpath("//*[text()='Masters']"));
		    Actions a=new Actions(object);
		    //a.moveToElement(masters).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Assign Master']")));
		    object.findElement(By.xpath("//*[text()='Assign Master']")).click();
		    Thread.sleep(4000);
		    if(bp.addButton.isEnabled() && bp.editButton.isEnabled())
		    {
		    	bp.click_addButton();
		    	try
		    	{
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtAssignName")));
		    		bp.click_saveButton();
		    		bp.click_addButton();
		    	}
		    	catch(Exception ex)
		    	{
		    		Thread.sleep(3000);
		    		object.switchTo().alert().accept();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtAssignName")));
		    		object.findElement(By.name("txtAssignName")).sendKeys("Assign1");
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		Thread.sleep(2000);
		    		try
		    		{
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
			    		{
		    				s.write("Record Saved Successfully");
			    		}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    		catch(Exception er)
		    		{
		    			if(object.findElement(By.xpath("//*[contains(text(),'already Exist')]")).isDisplayed())
		    			{
		    				s.write("Assignment Name already Exist");
		    			}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    	}
		    	Thread.sleep(2000);
		    	if(bp.editButton.isEnabled())
		    	{
		    		bp.click_editButton();
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlAssignCode")));
		    			bp.click_saveButton();
		    			bp.click_editButton();
		    		}
		    		catch(Exception eu)
		    		{
		    			Thread.sleep(3000);
		    			object.switchTo().alert().accept();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlAssignCode")));
		    			object.findElement(By.name("ddlAssignCode")).click();
		    			WebElement assign_dd=object.findElement(By.name("ddlAssignCode"));
		    			Select s1=new Select(assign_dd);
		    			s1.selectByVisibleText("Assign1-2");
		    			Thread.sleep(2000);
		    			bp.click_saveButton();
		    			try
		    			{
		    				Thread.sleep(3000);
		    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
		    				if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    				{
		    					s.write("Record Edit and Saved Successfully");
		    				}
		    				wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    				bp.click_clearButton();
		    			}
		    			catch(Exception ed)
		    			{
		    				System.out.println(ed.getMessage());
		    			}
		    		}
		    	}
		    	Thread.sleep(4000);
		    	try
		    	{
		    		wait.until(ExpectedConditions.visibilityOf(bp.editButton));
			    	bp.click_editButton();
			    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlAssignCode")));
	    			object.findElement(By.name("ddlAssignCode")).click();
	    			WebElement assign_dd=object.findElement(By.name("ddlAssignCode"));
	    			Select s1=new Select(assign_dd);
	    			s1.selectByVisibleText("Assign1-2");
	    			Thread.sleep(3000);
	    			wait.until(ExpectedConditions.visibilityOf(bp.deleteButton));
	    			bp.click_deleteButton();
	    			bp.click_editButton();
		    	}
		    	catch(Exception ep)
		    	{
		    		Thread.sleep(3000);
		    		object.switchTo().alert().accept();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Deleted Successfully')]")));
		    		if(object.findElement(By.xpath("//*[contains(text(),'Deleted Successfully')]")).isDisplayed())
		    		{
		    			s.write("Record Delete Successfully");
		    		}
		    		try
		    		{
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.editButton));
				    	bp.click_editButton();
				    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlAssignCode")));
		    			object.findElement(By.name("ddlAssignCode")).click();
		    			WebElement assign_dd=object.findElement(By.name("ddlAssignCode"));
		    			Select s1=new Select(assign_dd);
		    			s1.selectByVisibleText("Assign1-2");
		    			bp.click_addButton();
		    		}
		    		catch(Exception ei)
		    		{
		    			s.write("Assign Master Test Passed");
		    			Thread.sleep(1000);
		    			masters=object.findElement(By.xpath("//*[text()='Masters']"));
		    			//Actions a=new Actions(object);
		    		    a.moveToElement(masters).build().perform();
		    		}
		    	}
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
		    	s.write("Assign Master Test Failed");
		    }
	 }
	 @Then("^Validate  Sales Entity Type Master Page$")
	 public void sales_Entity_master() throws Exception
	 {
		   /*//homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);*/
		    //move to masters module
		    //WebElement master=object.findElement(By.xpath("//*[text()='Masters']"));
		    Actions a=new Actions(object);
		   // a.moveToElement(masters).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Sales Entity Type']")));
		    object.findElement(By.xpath("//*[text()='Sales Entity Type']")).click();
		    Thread.sleep(4000);
		    if(bp.addButton.isEnabled() && bp.editButton.isEnabled())
		    {
		    	
		    	try
		    	{
		    		bp.click_addButton();
			    	Thread.sleep(2000);
		    		/*wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDescription")));
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));*/
		    		//bp.click_saveButton();
			    	object.findElement(By.name("btnSave")).click();
		    		bp.click_addButton();
		    	}
		    	catch(Exception ex)
		    	{
		    		Thread.sleep(3000);
		    		object.switchTo().alert().accept();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDescription")));
		    		object.findElement(By.name("txtDescription")).sendKeys("Bywalking");
		    		//wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		//bp.click_saveButton();
		    		object.findElement(By.name("btnSave")).click();
		    		Thread.sleep(2000);
		    		try
		    		{
		    			if(object.findElement(By.xpath("//*[contains(text(),'saved successfully')]")).isDisplayed())
			    		{
		    				s.write("Record Saved Successfully");
			    		}
		    			//wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			//bp.click_clearButton();
		    			Thread.sleep(3000);
		    			object.findElement(By.name("btnClear")).click();
		    		}
		    		catch(Exception er)
		    		{
		    			if(object.findElement(By.xpath("//*[contains(text(),'Already Exist')]")).isDisplayed())
		    			{
		    				s.write("Assignment Name already Exist");
		    			}
		    			//wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			//bp.click_clearButton();
		    			Thread.sleep(3000);
		    			object.findElement(By.name("btnClear")).click();
		    		}
		    	}
		    	Thread.sleep(2000);
		    	if(bp.editButton.isEnabled())
		    	{
		    		bp.click_editButton();
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlSCode")));
		    			//bp.click_saveButton();
		    			object.findElement(By.name("btnSave")).click();
		    			bp.click_editButton();
		    		}
		    		catch(Exception eu)
		    		{
		    			Thread.sleep(3000);
		    			object.switchTo().alert().accept();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlSCode")));
		    			object.findElement(By.name("ddlSCode")).click();
		    			WebElement sales_dd=object.findElement(By.name("ddlSCode"));
		    			Select s1=new Select(sales_dd);
		    			s1.selectByVisibleText("Bywalking-8");
		    			//bp.click_saveButton();
		    			Thread.sleep(3000);
		    			object.findElement(By.name("btnSave")).click();
		    			try
		    			{
		    				Thread.sleep(3000);
		    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'saved successfully')]")));
		    				if(object.findElement(By.xpath("//*[contains(text(),'saved successfully')]")).isDisplayed())
		    				{
		    					s.write("Record Edit and Saved Successfully");
		    				}
		    				wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    				bp.click_clearButton();
		    			}
		    			catch(Exception ed)
		    			{
		    				System.out.println(ed.getMessage());
		    			}
		    		}
		    	}
		    	Thread.sleep(4000);
		    	try
		    	{
		    		wait.until(ExpectedConditions.visibilityOf(bp.editButton));
			    	bp.click_editButton();
			    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlSCode")));
	    			object.findElement(By.name("ddlSCode")).click();
	    			WebElement sales_dd=object.findElement(By.name("ddlSCode"));
	    			Select s1=new Select(sales_dd);
	    			s1.selectByVisibleText("Bywalking-8");
	    			Thread.sleep(3000);
	    			wait.until(ExpectedConditions.visibilityOf(bp.deleteButton));
	    			bp.click_deleteButton();
	    			bp.click_editButton();
		    	}
		    	catch(Exception ep)
		    	{
		    		Thread.sleep(3000);
		    		object.switchTo().alert().accept();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Deleted Successfully')]")));
		    		if(object.findElement(By.xpath("//*[contains(text(),'Deleted Successfully')]")).isDisplayed())
		    		{
		    			s.write("Record Delete Successfully");
		    		}
		    		try
		    		{
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.editButton));
				    	bp.click_editButton();
				    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlSCode")));
		    			object.findElement(By.name("ddlSCode")).click();
		    			WebElement sales_dd=object.findElement(By.name("ddlSCode"));
		    			Select s1=new Select(sales_dd);
		    			s1.selectByVisibleText("Bywalking-8");
		    			bp.click_addButton();
		    		}
		    		catch(Exception ei)
		    		{
		    			s.write("Sales Entity Type Master Test Passed");
		    			Thread.sleep(1000);
		    			masters=object.findElement(By.xpath("//*[text()='Masters']"));
		    			//Actions a=new Actions(object);
		    		    a.moveToElement(masters).build().perform();
		    		}
		    	}
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
		    	s.write("Sales Entity Type Master Test Failed");
		    }
	 }
	 @Then("^Validate  Sales Entity List Page$")
	 public void sales_Entity_list() throws Exception
	 {
		/*//homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);*/
		    //move to masters module
		    masters=object.findElement(By.xpath("//*[text()='Masters']"));
		    Actions a=new Actions(object);
		   // a.moveToElement(masters).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='SalesEntityList']")));
		    object.findElement(By.xpath("//*[text()='SalesEntityList']")).click();
		    Thread.sleep(4000); 
		    if(object.findElement(By.name("btnAddNewBroker")).isEnabled())
		    {
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnAddNewBroker")));
		    	object.findElement(By.name("btnAddNewBroker")).click();
		    	 Thread.sleep(4000);
		    	 try
		    	 {
		    		 ArrayList<String> a1=new ArrayList<String>(object.getWindowHandles());
		    		 object.switchTo().window(a1.get(1));
		    		 Thread.sleep(2000);
		    		 if(object.findElement(By.name("btnSave")).isEnabled())
			    	 {
		    			 object.manage().window().maximize();
		    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Type']/following::select[1][@name='ddlSource']")));
		    			 object.findElement(By.xpath("//*[text()='Type']/following::select[1][@name='ddlSource']")).click();
		    			 WebElement type_dd=object.findElement(By.xpath("//*[text()='Type']/following::select[1][@name='ddlSource']"));
		    			 Select se=new Select(type_dd);
		    			 se.selectByVisibleText("BROKER");
		    			 Thread.sleep(1000);
		    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtBrokerName")));
		    			 object.findElement(By.name("txtBrokerName")).sendKeys("TESTING@1");
		    			 Thread.sleep(1000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='Address'])[1]/following::input[1]")));
			    		 object.findElement(By.xpath("(//*[text()='Address'])[1]/following::input[1]")).sendKeys("JEDDAH");
			    		 Thread.sleep(1000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBranch")));
			    		 object.findElement(By.name("ddlBranch")).click();
			    		 Thread.sleep(1000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='JEDDAH-JED']")));
			    		 object.findElement(By.xpath("//*[text()='JEDDAH-JED']")).click();
			    		 Thread.sleep(4000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlEntityType")));
			    		 object.findElement(By.name("ddlEntityType")).click();
			    		 WebElement entity_type=object.findElement(By.name("ddlEntityType"));
			    		 Select s1=new Select(entity_type);
			    		 s1.selectByIndex(2);
			    		 Thread.sleep(2000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtEmail")));
			    		 object.findElement(By.name("txtEmail")).sendKeys("kummari@gmail.com");
			    		 Thread.sleep(5000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCityTele")));
			    		 object.findElement(By.name("ddlCityTele")).click();
			    		 Thread.sleep(10000);
			    		 WebElement city_dd=object.findElement(By.xpath("//*[text()='Ahad Almsarha-1011']"));
			    		 a.click(city_dd).build().perform();
			    		 Thread.sleep(2000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtWebSite")));
			    		 object.findElement(By.name("txtWebSite")).sendKeys("www.acessmeditech.com");
			    		 Thread.sleep(5000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtPhoneNumber")));
			    		 object.findElement(By.name("txtPhoneNumber")).sendKeys("523419876");
			    		 Thread.sleep(3000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCPerson")));
			    		 object.findElement(By.name("txtCPerson")).sendKeys("LOKii");
			    		 Thread.sleep(2000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCommType")));
			    		 object.findElement(By.name("ddlCommType")).click();
			    		 Thread.sleep(4000);
			    		 WebElement comm_type_dd=object.findElement(By.name("ddlCommType"));
			    		 Select s2=new Select(comm_type_dd);
			    		 s2.selectByVisibleText("CLAIMS");
			    		 Thread.sleep(3000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCommunicationType")));
			    		 object.findElement(By.name("ddlCommunicationType")).click();
			    		 Thread.sleep(1000);
			    		 WebElement comm_mode_type=object.findElement(By.name("ddlCommunicationType"));
			    		 Select s3=new Select(comm_mode_type);
			    		 s3.selectByVisibleText("EMAIL-4");
			    		 Thread.sleep(2000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCommunication")));
			    		 object.findElement(By.name("txtCommunication")).sendKeys("kummari@gmail.com");
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnContGrid")));
			    		 object.findElement(By.name("btnContGrid")).click();
			    		 Thread.sleep(3000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlRegulatoryTele")));
			    		 object.findElement(By.name("ddlRegulatoryTele")).click();
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'CCHI')]")));
			    		 object.findElement(By.xpath("//*[contains(text(),'CCHI')]")).click();
			    		 Thread.sleep(2000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtLicenseNo")));
			    		 object.findElement(By.name("txtLicenseNo")).sendKeys("3672632983");
			    		 Thread.sleep(2000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(@name,'txtValidFrom')])[2]")));
			    		 object.findElement(By.xpath("(//*[contains(@name,'txtValidFrom')])[2]")).sendKeys("015/03/2019");
			    		 Thread.sleep(2000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(@name,'txtValidTo')])[2]")));
			    		 object.findElement(By.xpath("(//*[contains(@name,'txtValidTo')])[2]")).sendKeys("08/03/2020");
			    		 Thread.sleep(3000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnLicAddGrid")));
			    		 object.findElement(By.name("btnLicAddGrid")).click();
			    		 Thread.sleep(4000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtBAccName")));
			    		 object.findElement(By.name("txtBAccName")).sendKeys("Lokesh12");
			    		 Thread.sleep(1000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtBName")));
			    		 object.findElement(By.name("txtBName")).sendKeys("DCB");
			    		 Thread.sleep(1000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtBBranch")));
			    		 object.findElement(By.name("txtBBranch")).sendKeys("JEDDAH");
			    		 Thread.sleep(1000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtBankActno")));
			    		 object.findElement(By.name("txtBankActno")).sendKeys("010025011623451");
			    		 Thread.sleep(1000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtIBANNo")));
			    		 object.findElement(By.name("txtIBANNo")).sendKeys("398127984");
			    		 Thread.sleep(1000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnBankAddGrid")));
			    		 object.findElement(By.name("btnBankAddGrid")).click();
			    		 Thread.sleep(3000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnSave")));
			    		 object.findElement(By.name("btnSave")).click();
			    		 Thread.sleep(3000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnDecisionStatus")));
			    		 object.findElement(By.name("btnDecisionStatus")).click();
			    		 Thread.sleep(3000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlAppStatus")));
			    		 object.findElement(By.name("ddlAppStatus")).click();
			    		 Thread.sleep(1000);
			    		 WebElement approval_status_dd=object.findElement(By.name("ddlAppStatus"));
			    		 Select s4=new Select(approval_status_dd);
			    		 s4.selectByVisibleText("VERIFY");
			    		 Thread.sleep(2000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnVerify")));
			    		 object.findElement(By.name("btnVerify")).click();
			    		 Thread.sleep(3000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Verified Successfully')]")));
			    		 if(object.findElement(By.xpath("//*[contains(text(),'Verified Successfully')]")).isDisplayed())
			    		 {
			    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlAppStatus")));
			    			 object.findElement(By.name("ddlAppStatus")).click();
			    			 WebElement approval_dd=object.findElement(By.name("ddlAppStatus"));
			    			 Select s5=new Select(approval_dd);
			    			 s5.selectByVisibleText("APPROVED");
			    			 Thread.sleep(3000);
			    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnApprove")));
			    			 object.findElement(By.name("btnApprove")).click();
			    			 Thread.sleep(2000);
			    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
			    		 }
			    		 Thread.sleep(3000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnBack")));
			    		 object.findElement(By.name("BtnBack")).click();
			    		 Thread.sleep(3000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnUpdate")));
			    		 object.findElement(By.name("btnUpdate")).click();
			    		 Thread.sleep(3000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Updated Successfully')]")));
			    		 String se_code=object.findElement(By.xpath("//*[text()='Sales Entity Code']/following::input[1]")).getAttribute("value");
			    		 object.switchTo().window(a1.get(0));
			    		 Thread.sleep(3000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnClear")));
			    		 object.findElement(By.name("btnClear")).click();
			    		 Thread.sleep(2000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Sales Entity  Code']/following::input[1]")));
			    		 object.findElement(By.xpath("//*[text()='Sales Entity  Code']/following::input[1]")).sendKeys(se_code);
			    		 Thread.sleep(2000);
			    		 a.sendKeys(Keys.DOWN).build().perform();
			    		 Thread.sleep(2000);
			    		 a.sendKeys(Keys.ENTER).build().perform();
			    		 Thread.sleep(3000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlSalesEntityType")));
			    		 object.findElement(By.name("ddlSalesEntityType")).click();
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='BROKER']")));
			    		 object.findElement(By.xpath("//*[text()='BROKER']")).click();
			    		 Thread.sleep(2000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
			    		 object.findElement(By.name("btnquery")).click();
			    		 Thread.sleep(3000);
			    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='Decision Status'])[2]/following::td[10]")));
			    		 String decision_status=object.findElement(By.xpath("(//*[text()='Decision Status'])[2]/following::td[10]")).getText();
			    		 Thread.sleep(2000);
			    		 try
			    		 {
			    			 if(decision_status.equalsIgnoreCase("ACTIVE"))
				    		 {
				    			 s.write("Sales Entity Test Passed");
				    			 Thread.sleep(1000);
					    			masters=object.findElement(By.xpath("//*[text()='Masters']"));
					    			//Actions a=new Actions(object);
					    		    a.moveToElement(masters).build().perform();
				    		 }  
			    		 }
			    		 catch(Exception et1)
			    		 {
			    			 s.write("Status in pending");
			    			 Thread.sleep(1000);
			    			masters=object.findElement(By.xpath("//*[text()='Masters']"));
			    			//Actions a=new Actions(object);
			    		    a.moveToElement(masters).build().perform();
			    		 }
			    	 } 
		    	 }
		    	 catch(Exception et)
		    	 {
		    		 s.write(et.getMessage());
		    	 }
		    	 
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
		    	s.write("sales Entity list Test Failed");
		    }
	 }
	 @Then("^Validate  Branch Master Page$")
	 public void branch_Master() throws Exception
	 {
		   /*//homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);*/
		    //move to masters module
		    //WebElement master=object.findElement(By.xpath("//*[text()='Masters']"));
		    Actions a=new Actions(object);
		   // a.moveToElement(masters).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Branch Master']")));
		    object.findElement(By.xpath("//*[text()='Branch Master']")).click();
		    Thread.sleep(4000); 
		    if(bp.addButton.isEnabled() && bp.editButton.isEnabled())
		    {
		    	bp.click_addButton();
		    	try
		    	{
		    		Thread.sleep(3000);
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		bp.click_addButton();
		    	}
		    	catch(Exception et)
		    	{
		    		Thread.sleep(3000);
		    		object.switchTo().alert().accept();
		    		object.switchTo().activeElement().sendKeys("ARAbi1");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtBranchName")));
		    		object.findElement(By.name("txtBranchName")).sendKeys("Arabian");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCountryTele")));
		    		WebElement country=object.findElement(By.name("ddlCountryTele"));
		    		country.click();
		    		Thread.sleep(2000);
		    		country.sendKeys("ind");
		    		Thread.sleep(2000);
		    		//a.sendKeys(Keys.DOWN).build().perform();
		    		a.sendKeys(Keys.ENTER).build().perform();
		    		/*List<WebElement> country=object.findElements(By.xpath("//*[@id='ddlCountryTele_DropDown']/div[1]/ul/li"));
		    		System.out.println(country.size());*/
		    		Thread.sleep(2000);
		    		
		    		/*for(int i=1;i<=country.size();i++)
		    		{
		    			Thread.sleep(3000);
		    			String actual_country=object.findElement(By.xpath("//*[@id='ddlCountryTele_DropDown']/div[1]/ul/li["+i+"]")).getText();
		    			Thread.sleep(2000);
		    			if(actual_country.equals("INDIA-91"))
		    			{
		    				a.sendKeys(Keys.ENTER).build().perform();
		    				break;
		    			}
		    			Thread.sleep(3000);
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='select'])[1]/following::span[1]/b[3]")));
		    			object.findElement(By.xpath("(//*[text()='select'])[1]/following::span[1]/b[3]")).click();
		    		}*/
		    		Thread.sleep(3000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCityTele")));
		    		object.findElement(By.name("ddlCityTele")).click();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='HYDERABAD-40']")));
		    		object.findElement(By.xpath("//*[text()='HYDERABAD-40']")).click();
		    		Thread.sleep(3000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtAddress")));
		    		object.findElement(By.name("txtAddress")).sendKeys("Mind Space");
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		Thread.sleep(2000);
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    			{
		    				s.write("Record Saved Successfully....");
		    			}
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    		catch(Exception er)
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Branch Code already Exists']")));
		    			if(object.findElement(By.xpath("//*[text()='Branch Code already Exists']")).isDisplayed())
		    			{
		    				s.write("Branch Code Already Exist");
		    			}
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    	}
		    	Thread.sleep(2000);
		    	if(bp.editButton.isEnabled())
		    	{
		    		wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    		bp.click_editButton();
		    		try
		    		{
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    			bp.click_saveButton();
		    			bp.click_clearButton();
		    		}
		    		catch(Exception ey)
		    		{
		    			Thread.sleep(2000);
		    			object.switchTo().alert().accept();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@name='ddlBranch']")));
		    			object.findElement(By.xpath("//*[@name='ddlBranch']")).click();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='ARABIAN-ARAbi1']")));
		    			object.findElement(By.xpath("//*[text()='ARABIAN-ARAbi1']")).click();
		    			Thread.sleep(5000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    			bp.click_saveButton();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    			{
		    				s.write("Record Edited and Saved Successfully....");
		    			}
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();	
		    		}
		    		Thread.sleep(2000);
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOf(bp.editButton));
			    		bp.click_editButton();
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@name='ddlBranch']")));
		    			object.findElement(By.xpath("//*[@name='ddlBranch']")).click();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='ARABIAN-ARAbi1']")));
		    			object.findElement(By.xpath("//*[text()='ARABIAN-ARAbi1']")).click();
		    			Thread.sleep(5000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.deleteButton));
		    			bp.click_deleteButton();
		    			bp.click_addButton();
		    		}
		    		catch(Exception ew)
		    		{
		    			Thread.sleep(2000);
		    			object.switchTo().alert().accept();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Deleted Successfully')]")));
		    			if(object.findElement(By.xpath("//*[contains(text(),'Deleted Successfully')]")).isDisplayed())
		    			{
		    				s.write("Record Delete Successfully");
		    			}
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();	
		    			Thread.sleep(3000);
		    			try
		    			{
		    				wait.until(ExpectedConditions.visibilityOf(bp.editButton));
				    		bp.click_editButton();
				    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@name='ddlBranch']")));
			    			object.findElement(By.xpath("//*[@name='ddlBranch']")).click();
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='ARABIAN-ARAbi1']")));
			    			object.findElement(By.xpath("//*[text()='ARABIAN-ARAbi1']")).click();
		    			}
		    			catch(Exception ed)
		    			{
		    				s.write("Branch Master Test Passed");
		    				Thread.sleep(1000);
			    			masters=object.findElement(By.xpath("//*[text()='Masters']"));
			    			//Actions a=new Actions(object);
			    		    a.moveToElement(masters).build().perform();
		    			}
		    		}
		    	}
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
		    	s.write("Branch Master Test Failed");
		    	et.log(LogStatus.FAIL,"Branch Master Test Failed"+et.addScreenCapture(x));
		    }
	 }
	 @Then("^Validate  Sponser Master Page$")
	 public void sponser_master() throws Exception
	 {
		   /*//homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);*/
		    //move to masters module
		    //WebElement master=object.findElement(By.xpath("//*[text()='Masters']"));
		    Actions a=new Actions(object);
		  //  a.moveToElement(masters).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Sponsor Master']")));
		    object.findElement(By.xpath("//*[text()='Sponsor Master']")).click();
		    Thread.sleep(4000); 
		    if(bp.addButton.isEnabled() && bp.editButton.isEnabled())
		    {
		    	wait.until(ExpectedConditions.visibilityOf(bp.addButton));
		    	bp.click_addButton();
		    	Thread.sleep(3000);
		    	try
		    	{
		    		Thread.sleep(1000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnSave")));
		    		object.findElement(By.name("btnSave")).click();
		    		bp.click_addButton();
		    	}
		    	catch(Exception ex)
		    	{
		    		Thread.sleep(1000);
		    		object.switchTo().alert().accept();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtSponsorID")));
		    		object.findElement(By.name("txtSponsorID")).sendKeys("7527342003");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtSponserName")));
		    		object.findElement(By.name("txtSponserName")).sendKeys("TEST");
		    		/*wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtSponserNameArabic")));
		    		object.findElement(By.name("txtSponserNameArabic")).sendKeys("TEST");*/
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlRegistry")));
		    		object.findElement(By.name("ddlRegistry")).click();
		    		WebElement registryType=object.findElement(By.name("ddlRegistry"));
		    		Select s1=new Select(registryType);
		    		s1.selectByVisibleText("Under Civilian-2");
		    		Thread.sleep(1000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtRegistryNo")));
		    		object.findElement(By.name("txtRegistryNo")).sendKeys("54358");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtMobile")));
		    		object.findElement(By.name("txtMobile")).sendKeys("512438796");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCustomerType")));
		    		object.findElement(By.name("ddlCustomerType")).click();
		    		WebElement customerType=object.findElement(By.name("ddlCustomerType"));
		    		Select s2=new Select(customerType);
		    		s2.selectByVisibleText("INDIVIDUAL CUSTOMERS");
		    		Thread.sleep(1000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCity")));
		    		WebElement cityList=object.findElement(By.name("ddlCity"));
		    		cityList.click();
		    		Thread.sleep(1000);
		    		cityList.sendKeys("je");
		    		Thread.sleep(2000);
		    		a.sendKeys(Keys.ENTER).build().perform();
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnSave")));
		    		object.findElement(By.name("btnSave")).click();
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtSponsorID")));
		    		String sponserID=object.findElement(By.name("txtSponsorID")).getAttribute("value");
		    		System.out.println(sponserID);
		    		Thread.sleep(1000);

		    		try
		    		{
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Sucessfully')]")));
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Sucessfully')]")).isDisplayed())
		    			{
		    				s.write("Record Saved Successfully");
		    			}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    		catch(Exception ew)
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Sponsor Id already exist.']")));
		    			if(object.findElement(By.xpath("//*[text()='Sponsor Id already exist.']")).isDisplayed())
		    			{
		    				s.write("Sponser Id already exist");
		    			}
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    			Thread.sleep(3000);
		    			if(bp.editButton.isEnabled())
			    		{
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOf(bp.editButton));
			    			bp.click_editButton();
			    			
			    			try
			    			{
			    				Thread.sleep(2000);
			    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnSave")));
			    				object.findElement(By.name("btnSave")).click();
			    				bp.click_editButton();
			    				Thread.sleep(2000);
			    			}
			    			catch(Exception ed)
			    			{
			    				Thread.sleep(1000);
			    				object.switchTo().alert().accept();
			    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlSponsor")));
			    				WebElement spnsrId=object.findElement(By.name("ddlSponsor"));
			    				spnsrId.click();
			    				Thread.sleep(1000);
			    				spnsrId.sendKeys(sponserID);
			    				Thread.sleep(5000);
			    				a.sendKeys(Keys.DOWN).build().perform();
			    				Thread.sleep(1000);
			    				a.sendKeys(Keys.ENTER).build().perform();
			    				Thread.sleep(1000);
			    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnSave")));
			    				object.findElement(By.name("btnSave")).click();
			    				Thread.sleep(2000);
			    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Record Saved Sucessfully']")));
			    				if(object.findElement(By.xpath("//*[text()='Record Saved Sucessfully']")).isDisplayed())
			    				{
			    					s.write("Record Edit and Saved Sucessfully");
			    				}
			    				Thread.sleep(1000);
			    				wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
			    				bp.click_clearButton();
			    				Thread.sleep(2000);
				    			masters=object.findElement(By.xpath("//*[text()='Masters']"));
				    			//Actions a=new Actions(object);
				    		    a.moveToElement(masters).build().perform();
			    			}
			    			
			    		}
			    		Thread.sleep(1000);
			    		/*wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnKYC")));
			    		object.findElement(By.name("btnKYC")).click();
			    		Thread.sleep(1000);
			    		ArrayList<String> winDows=new ArrayList<String>(object.getWindowHandles());
			    		Thread.sleep(1000);
			    		object.switchTo().window(winDows.get(1));
			    		Thread.sleep(2000);
			    		object.close();
			    		Thread.sleep(1000);
			    		object.switchTo().window(winDows.get(0));*/
			    	}
			   }
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
		    	s.write("Sponser Master Test Failed");
		    	et.log(LogStatus.FAIL,"Sponser Master Test Failed"+et.addScreenCapture(x));
		    }
	 }
	 @Then("^validate ICD Codes Details page$")
	 public void icd_Codes() throws Exception
	 {
		//homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Insurance']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='ICD Codes']")));
		    object.findElement(By.xpath("//*[text()='ICD Codes']")).click();
		    Thread.sleep(4000); 
		    if(bp.addButton.isEnabled() && bp.editButton.isEnabled())
		    {
		    	wait.until(ExpectedConditions.visibilityOf(bp.addButton));
		    	bp.click_addButton();
		    	try
		    	{
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		bp.click_editButton();
		    	}
		    	catch(Exception ex)
		    	{
		    		Thread.sleep(3000);
		    		object.switchTo().alert().accept();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnImport")));
		    		object.findElement(By.name("btnImport")).click();
		    		Thread.sleep(3000);
		    		ArrayList<String> a2=new ArrayList<String>(object.getWindowHandles());
		    		object.switchTo().window(a2.get(1));
		    		Thread.sleep(2000);
		    		try
		    		{
		    			if(object.findElement(By.name("FunIcdCodesImport")).isDisplayed())
		    			{
		    				Thread.sleep(2000);
		    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("FunIcdCodesImport")));
			    			object.findElement(By.name("FunIcdCodesImport")).sendKeys("C:\\Users\\amt\\Desktop\\ICDCodes.xls");
			    			Thread.sleep(2000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnUpload")));
			    			object.findElement(By.name("BtnUpload")).click();
			    			Thread.sleep(3000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnSave")));
			    			object.findElement(By.name("btnSave")).click();
			    			Thread.sleep(2000);
			    			try
			    			{
			    				if(object.findElement(By.xpath("//*[text()=' Rows Imported Sucessfully.']")).isDisplayed())
				    			{
				    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnClear")));
				    				object.findElement(By.name("BtnClear")).click();
				    				s.write("Data added from Data Import");
				    			}
			    			}
			    			
			    			catch(Exception ed)
			    			{
			    				if(object.findElement(By.xpath("//*[text()='ABTest123 ICD Code already Exist']")).isDisplayed())
			    				{
			    					wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnClear")));
				    				object.findElement(By.name("BtnClear")).click();
				    				s.write("Data added from Data Import");
			    				}
			    			}
		    			}
		    		}
		    		catch(Exception ex1)
		    		{
		    			System.out.println(ex1.getMessage());
		    		}
		    		Thread.sleep(2000);
		    		object.switchTo().window(a2.get(0));
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnClear")));
		    		object.findElement(By.name("btnClear")).click();
		    		Thread.sleep(3000);
		    		wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    		if(bp.editButton.isEnabled())
		    		{
		    			bp.click_editButton();
		    			try
		    			{
		    				Thread.sleep(2000);
		    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnFind")));
		    				object.findElement(By.name("btnFind")).click();
		    				bp.click_clearButton();
		    			}
		    			catch(Exception ei)
		    			{
		    				Thread.sleep(2000);
		    				object.switchTo().alert().accept();
		    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtICDCodes")));
		    				object.findElement(By.name("txtICDCodes")).sendKeys("ABTest123");
		    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnFind")));
		    				object.findElement(By.name("btnFind")).click();
		    				Thread.sleep(3000);
		    				wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    				bp.click_saveButton();
		    				try
		    				{
		    					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
			    				if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
			    				{
			    					s.write("Record Edit and Saved Successfully");
			    				}
			    				Thread.sleep(2000);
			    				wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
			    				bp.click_clearButton();
		    				}
		    				
		    				catch(Exception eg)
		    				{
		    					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),' The duplicate key value is (ABTest123).')]")));
		    					if(object.findElement(By.xpath("//*[contains(text(),' The duplicate key value is (ABTest123).')]")).isDisplayed())
		    					{
		    						s.write("ICD Code Already Exist");
		    					}
		    					Thread.sleep(2000);
			    				wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
			    				bp.click_clearButton();
		    				}
		    			}
		    		}
		    		
		    	}
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
		    	s.write("ICD Codes Details Test Failed");
		    }
	 }
	 @Then("^validate Dental Codes Details page$")
	 public void dental_codes() throws Exception
	 {
		//homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Insurance']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Dental Codes']")));
		    object.findElement(By.xpath("//*[text()='Dental Codes']")).click();
		    Thread.sleep(4000); 
		    if(bp.addButton.isEnabled() && bp.editButton.isEnabled())
		    {
		    	wait.until(ExpectedConditions.visibilityOf(bp.addButton));
		    	bp.click_addButton();
		    	try
		    	{
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		bp.click_editButton();
		    	}
		    	catch(Exception et)
		    	{
		    		Thread.sleep(2000);
		    		object.switchTo().alert().accept();
		    		object.switchTo().activeElement().sendKeys("AB12");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDescripiton")));
		    		object.findElement(By.name("txtDescripiton")).sendKeys("TESTING THE");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCNo")));
		    		object.findElement(By.name("txtCNo")).sendKeys("2");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtAvgValue")));
		    		object.findElement(By.name("txtAvgValue")).sendKeys("8");
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		Thread.sleep(2000);
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    			{
		    				s.write("REcord Saved Successfully");
		    			}
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    		catch(Exception ex)
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Dental Description Already Exists.']")));
		    			if(object.findElement(By.xpath("//*[text()='Dental Description Already Exists.']")).isDisplayed())
		    			{
		    				s.write("Dental Description Already Exists.");
		    			}
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    	}
		    	Thread.sleep(2000);
		    	if(bp.editButton.isEnabled())
		    	{
		    		wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    		bp.click_editButton();
		    		try
		    		{
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnFind")));
		    			object.findElement(By.name("btnFind")).click();
		    			bp.click_editButton();
		    		}
		    		catch(Exception er)
		    		{
		    			Thread.sleep(2000);
		    			object.switchTo().alert().accept();
		    			object.switchTo().activeElement().sendKeys("AB12");
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnFind")));
		    			object.findElement(By.name("btnFind")).click();
		    			Thread.sleep(3000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    			bp.click_saveButton();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    			{
		    				s.write("REcord Saved Successfully");
		    			}
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    			wait.until(ExpectedConditions.visibilityOf(bp.editButton));
			    		bp.click_editButton();
		    		}
		    	}
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
		    	s.write("Dental Codes Details Test Failed");
		    }
	 }
	 @Then("^validate Territory Details page$")
	 public void territory_details() throws Exception
	 {
		//homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Insurance']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Territory']")));
		    object.findElement(By.xpath("//*[text()='Territory']")).click();
		    Thread.sleep(3000);
		    wait.until(ExpectedConditions.visibilityOf(bp.addButton));
		    if(bp.addButton.isEnabled() && bp.editButton.isEnabled())
		    {
		    	object.findElement(By.name("btnAdd")).click();
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtTName")));
		    	try
		    	{
		    		bp.click_saveButton();
		    		object.switchTo().activeElement().sendKeys("IND");
		    	}
		    	catch(Exception ex)
		    	{
		    		Thread.sleep(3000);
		    		object.switchTo().alert().accept();
		    		object.switchTo().activeElement().sendKeys("Asia");
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		Thread.sleep(2000);
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    			{
		    				System.out.println("Record Added Successfully");
		    			}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    		catch(Exception ew)
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Territory Name Already Exists.']")));
		    			if(object.findElement(By.xpath("//*[text()='Territory Name Already Exists.']")).isDisplayed())
		    			{
		    				System.out.println("Territory Name Already Exists.");
		    			}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    	}
		    	Thread.sleep(3000);
		    	wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    	if(bp.editButton.isEnabled())
		    	{
		    		wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    		bp.click_editButton();
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    			bp.click_saveButton();
		    			bp.click_editButton();
		    		}
		    		catch(Exception er)
		    		{
		    			Thread.sleep(3000);
		    			object.switchTo().alert().accept();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlTCode")));
		    			object.findElement(By.name("ddlTCode")).click();
		    			WebElement territary_dd=object.findElement(By.name("ddlTCode"));
		    			Select s2=new Select(territary_dd);
		    			s2.selectByVisibleText("Asia-5");
		    			try
		    			{
		    				Thread.sleep(3000);
		    				if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    				{
		    					s.write("Record Edit and Saved Successfully");
		    				}
		    				wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
			    			bp.click_clearButton();
		    			}
		    			catch(Exception et)
		    			{
		    				System.out.println(et.getMessage());
		    			}
		    		}
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    		bp.click_editButton();
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlTCode")));
		    			object.findElement(By.name("ddlTCode")).click();
		    			WebElement location_dd=object.findElement(By.name("ddlTCode"));
		    			Select s2=new Select(location_dd);
		    			s2.selectByVisibleText("Asia-5");
		    			wait.until(ExpectedConditions.visibilityOf(bp.deleteButton));
		    			bp.click_deleteButton();
		    			bp.click_saveButton();
		    		}
		    		catch(Exception eq)
		    		{
		    			Thread.sleep(3000);
		    			object.switchTo().alert().accept();
		    			try
		    			{
		    				Thread.sleep(4000);
		    				if(object.findElement(By.xpath("//*[contains(text(),'Deleted Successfully')]")).isDisplayed())
		    				{
		    					s.write("Record Deleted Successfully");
		    				}
		    			}
		    			catch(Exception ew)
		    			{
		    				System.out.println(ew.getMessage());
		    			}
		    		}
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOf(bp.editButton));
			    		bp.click_editButton();
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlTCode")));
		    			object.findElement(By.name("ddlTCode")).click();
		    			WebElement location_dd=object.findElement(By.name("ddlTCode"));
		    			Select s2=new Select(location_dd);
		    			s2.selectByVisibleText("Asia-5");
		    		}
		    		catch(Exception er)
		    		{
		    			s.write("Territary Details Page Test Passed");
		    		}
		    	}
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
				s.write("Territary Details Page Test Failed");
				System.exit(0);
		    }
	 }
	 @Then("^CPT Codes Details page$")
	 public void cpt_Codes() throws Exception
	 {
		//homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Insurance']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='CPT Codes']")));
		    object.findElement(By.xpath("//*[text()='CPT Codes']")).click();
		    Thread.sleep(4000); 
		    if(bp.addButton.isEnabled() && bp.editButton.isEnabled())
		    {
		    	wait.until(ExpectedConditions.visibilityOf(bp.addButton));
		    	bp.click_addButton();
		    	try
		    	{
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		bp.click_editButton();
		    		Thread.sleep(2000);
		    	}
		    	catch(Exception et)
		    	{
		    		Thread.sleep(2000);
		    		object.switchTo().alert().accept();
		    		object.switchTo().activeElement().sendKeys("1702");
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDescription")));
		    		object.findElement(By.name("txtDescription")).sendKeys("Testing");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCNo")));
		    		object.findElement(By.name("txtCNo")).sendKeys("02");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtAvgValue")));
		    		object.findElement(By.name("txtAvgValue")).sendKeys("1");
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		Thread.sleep(2000);
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    			{
		    				s.write("Record Saved Successfully");
		    			}
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    		catch(Exception ex)
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Code is Already Exist')]")));
		    			if(object.findElement(By.xpath("//*[contains(text(),'Code is Already Exist')]")).isDisplayed())
		    			{
		    				s.write("CPT code Already Exists.");
		    			}
		    			//Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    	}
		    	Thread.sleep(2000);
		    	if(bp.editButton.isEnabled())
		    	{
		    		wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    		bp.click_editButton();
		    		try
		    		{
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnFind")));
		    			object.findElement(By.name("btnFind")).click();
		    			bp.click_editButton();
		    		}
		    		catch(Exception er)
		    		{
		    			Thread.sleep(2000);
		    			object.switchTo().alert().accept();
		    			object.switchTo().activeElement().sendKeys("1702");
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnFind")));
		    			object.findElement(By.name("btnFind")).click();
		    			Thread.sleep(3000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    			bp.click_saveButton();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    			{
		    				s.write("Record Saved Successfully");
		    			}
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    			wait.until(ExpectedConditions.visibilityOf(bp.editButton));
			    		bp.click_editButton();
		    		}
		    	}
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
		    	s.write("CPT Codes Details Test Failed");
		    }
	 }
	 @Then("^Industry Details page$")
	 public void industry_details_page() throws Exception
	 {
		   //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Insurance']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Industry']")));
		    object.findElement(By.xpath("//*[text()='Industry']")).click();
		    Thread.sleep(4000); 
		    if(bp.addButton.isEnabled() && bp.editButton.isEnabled())
		    {
		    	wait.until(ExpectedConditions.visibilityOf(bp.addButton));
		    	bp.click_addButton();
		    	try
		    	{
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		bp.click_editButton();
		    	}
		    	catch(Exception ex)
		    	{
		    		Thread.sleep(2000);
		    		object.switchTo().alert().accept();
		    		object.switchTo().activeElement().sendKeys("AI-Artificial Inteligence");
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    			{
		    				System.out.println("Record Saved Successfully");
		    			}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    		catch(Exception er)
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Industry Name Already Exists.']")));
		    			if(object.findElement(By.xpath("//*[text()='Industry Name Already Exists.']")).isDisplayed())
		    			{
		    				System.out.println("Industry Name Already Exists");
		    			}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    	}
		    	Thread.sleep(2000);
		    	if(bp.editButton.isEnabled())
		    	{
		    		wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    		bp.click_editButton();
		    		try
		    		{
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    			bp.click_saveButton();
		    			bp.click_addButton();
		    		}
		    		catch(Exception ex)
		    		{
		    			Thread.sleep(2000);
		    			object.switchTo().alert().accept();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlICode")));
		    			object.findElement(By.name("ddlICode")).click();
		    			WebElement indstry_code_dd=object.findElement(By.name("ddlICode"));
		    			Select s1=new Select(indstry_code_dd);
		    			s1.selectByValue("AIArtificial Inteligence-82");
		    			Thread.sleep(3000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    			bp.click_saveButton();
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    			{
		    				System.out.println("Record Saved Successfully");
		    			}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    			try
		    			{
		    				Thread.sleep(2000);
		    				wait.until(ExpectedConditions.visibilityOf(bp.editButton));
				    		bp.click_editButton();
				    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlICode")));
			    			object.findElement(By.name("ddlICode")).click();
			    			WebElement indstry_code_dd1=object.findElement(By.name("ddlICode"));
			    			Select s2=new Select(indstry_code_dd1);
			    			s2.selectByValue("AIArtificial Inteligence-82");
			    			wait.until(ExpectedConditions.visibilityOf(bp.deleteButton));
			    			bp.click_deleteButton();
			    			bp.click_clearButton();
		    			}
		    			catch(Exception ex1)
		    			{
		    				Thread.sleep(2000);
		    				object.switchTo().alert().accept();
		    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Record Deleted Successfully']")));
		    				if(object.findElement(By.xpath("//*[text()='Record Deleted Successfully']")).isDisplayed())
		    				{
		    					System.out.println("Record Deleted Successfully");
		    				}
		    				wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    				bp.click_clearButton();
		    				try
		    				{
		    					Thread.sleep(2000);
		    					wait.until(ExpectedConditions.visibilityOf(bp.editButton));
					    		bp.click_editButton();
					    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlICode")));
				    			object.findElement(By.name("ddlICode")).click();
				    			WebElement indstry_code_dd1=object.findElement(By.name("ddlICode"));
				    			Select s2=new Select(indstry_code_dd1);
				    			s2.selectByValue("AIArtificial Inteligence-82");
		    				}
		    				catch(Exception et)
		    				{
		    					System.out.println("Industry Details Page Test Passed");
		    				}
		    				
		    			}
		    		}
		    	}
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
		    	System.out.println("Industry Details Page Test Failed");
		    }
	 }
	 @Then("^Validate Relation Details page$")
	 public void relation_details_page() throws Exception
	 {
		    //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Insurance']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Relation']")));
		    object.findElement(By.xpath("//*[text()='Relation']")).click();
		    Thread.sleep(4000); 
		    if(bp.addButton.isEnabled() && bp.editButton.isEnabled())
		    {
		    	wait.until(ExpectedConditions.visibilityOf(bp.addButton));
		    	bp.click_addButton();
		    	try
		    	{
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		bp.click_editButton();
		    	}
		    	catch(Exception ex)
		    	{
		    		Thread.sleep(2000);
		    		object.switchTo().alert().accept();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlSex")));
		    		object.findElement(By.name("ddlSex")).click();
		    		WebElement rs_dd=object.findElement(By.name("ddlSex"));
		    		Select s1=new Select(rs_dd);
		    		s1.selectByVisibleText("MALE");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDescription")));
		    		object.findElement(By.name("txtDescription")).sendKeys("ITESTING");
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCLCode")));
		    		object.findElement(By.name("ddlCLCode")).click();
		    		WebElement rl_code_dd=object.findElement(By.name("ddlCLCode"));
		    		Select s2=new Select(rl_code_dd);
		    		s2.selectByVisibleText("E");
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCCHIRelation")));
		    		object.findElement(By.name("ddlCCHIRelation")).click();
		    		WebElement cchi_dd=object.findElement(By.name("ddlCCHIRelation"));
		    		Select s3=new Select(cchi_dd);
		    		s3.selectByVisibleText("FATHER-6");
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlmanarRelationtype")));
		    		object.findElement(By.name("ddlmanarRelationtype")).click();
		    		WebElement MRT_dd=object.findElement(By.name("ddlmanarRelationtype"));
		    		Select s4=new Select(MRT_dd);
		    		s4.selectByVisibleText("Principals");
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlOccupation")));
		    		WebElement occupation=object.findElement(By.name("ddlOccupation"));
		    		occupation.click();
		    		occupation.sendKeys("A C I");
		    		Thread.sleep(2000);
		    		a.sendKeys(Keys.ENTER).build().perform();
		    		Thread.sleep(3000);
		    		/*wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();*/
		    		object.findElement(By.name("BtnSave")).click();
		    		Thread.sleep(2000);
		    		try
		    		{
		    			//Thread.sleep(2000);
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    			{
		    				s.write("Record Saved Successfully");
		    			}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    		catch(Exception er)
		    		{
		    			if(object.findElement(By.xpath("//*[text()='Relation Already Exist']")).isDisplayed())
		    			{
		    				s.write("Relation Already Exist...");
		    			}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    		Thread.sleep(2000);
		    		if(bp.editButton.isEnabled())
		    		{
		    			wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    			bp.click_editButton();
		    			try
		    			{
		    				Thread.sleep(2000);
		    				wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    				bp.click_saveButton();
		    				bp.click_addButton();
		    			}
		    			catch(Exception ew)
		    			{
		    				Thread.sleep(2000);
		    				object.switchTo().alert().accept();
		    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlRCode")));
		    				object.findElement(By.name("ddlRCode")).click();
		    				WebElement relation_code_dd=object.findElement(By.name("ddlRCode"));
		    				Select s11=new Select(relation_code_dd);
		    				s11.selectByVisibleText("ITESTING-28");
		    				Thread.sleep(2000);
				    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCLCode")));
				    		object.findElement(By.name("ddlCLCode")).click();
				    		WebElement rl_code_dd1=object.findElement(By.name("ddlCLCode"));
				    		Select s22=new Select(rl_code_dd1);
				    		s22.selectByVisibleText("E");
				    		Thread.sleep(2000);
				    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlmanarRelationtype")));
				    		object.findElement(By.name("ddlmanarRelationtype")).click();
				    		WebElement MRT_dd1=object.findElement(By.name("ddlmanarRelationtype"));
				    		Select s41=new Select(MRT_dd1);
				    		s41.selectByVisibleText("Principals");
		    				Thread.sleep(3000);
		    				wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
				    		bp.click_saveButton();
				    		if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
			    			{
				    			s.write("Record Saved Successfully");
			    			}
			    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
			    			bp.click_clearButton();
			    			try
			    			{
			    				Thread.sleep(2000);
			    				wait.until(ExpectedConditions.visibilityOf(bp.editButton));
				    			bp.click_editButton();
				    			object.findElement(By.name("ddlRCode")).click();
			    				WebElement relation_code_dd1=object.findElement(By.name("ddlRCode"));
			    				Select s12=new Select(relation_code_dd1);
			    				s12.selectByVisibleText("ITESTING-28");
			    				Thread.sleep(3000);
			    				wait.until(ExpectedConditions.visibilityOf(bp.deleteButton));
			    				bp.click_deleteButton();
			    				bp.click_addButton();
			    			}
			    			catch(Exception eu)
			    			{
			    				Thread.sleep(2000);
			    				object.switchTo().alert().accept();
			    				if(object.findElement(By.xpath("//*[text()='Record Deleted Successfully']")).isDisplayed())
			    				{
			    					s.write("Record Deleted Successfully");
			    				}
			    				wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
			    				bp.click_clearButton();
			    				try
			    				{
			    					Thread.sleep(2000);
			    					wait.until(ExpectedConditions.visibilityOf(bp.editButton));
						    		bp.click_editButton();
						    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlRCode")));
					    			object.findElement(By.name("ddlRCode")).click();
					    			WebElement relation_code_dd1=object.findElement(By.name("ddlRCode"));
					    			Select s21=new Select(relation_code_dd1);
					    			s21.selectByValue("I-TESTING-28");
			    				}
			    				catch(Exception et)
			    				{
			    					s.write("Relation Details Page Test Passed");
			    				}
			    			}
		    			}
		    		}
		    	}
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
		    	s.write("Relation Details Page Test Failed");
		    }
	 }
	 @Then("Validate Country Details page$")
	 public void location_country_details() throws Exception
	 {
		   //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Insurance']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Location']")));
		    WebElement location=object.findElement(By.xpath("//*[text()='Location']"));
		    a.moveToElement(location).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='Country'])[1]")));
		    object.findElement(By.xpath("(//*[text()='Country'])[1]")).click();
		    Thread.sleep(2000); 
		    if(bp.saveButton.isEnabled() && bp.clearButton.isEnabled())
		    {
		    	try
		    	{
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		bp.click_editButton();
		    	}
		    	catch(Exception ex)
		    	{
		    		Thread.sleep(2000);
		    		object.switchTo().alert().accept();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlTerritory")));
		    		object.findElement(By.name("ddlTerritory")).click();
		    		WebElement territory_dd=object.findElement(By.name("ddlTerritory"));
		    		Select s1=new Select(territory_dd);
		    		s1.selectByVisibleText("KSA and Home Country - 3");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCountries")));
		    		object.findElement(By.name("ddlCountries")).click();
		    		WebElement country=object.findElement(By.name("ddlCountries"));
		    		Select s2=new Select(country);
		    		s2.selectByVisibleText("ALBANIA-355");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnATGrid")));
		    		object.findElement(By.name("btnATGrid")).click();
		    		Thread.sleep(3000);
    				wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
		    		if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
	    			{
		    			s.write("Record Saved Successfully");
	    			}
	    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
	    			bp.click_clearButton();
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlTerritory")));
		    		object.findElement(By.name("ddlTerritory")).click();
		    		WebElement territory_dd1=object.findElement(By.name("ddlTerritory"));
		    		Select s11=new Select(territory_dd1);
		    		s11.selectByVisibleText("KSA and Home Country - 3");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='Delete'])[2]")));
		    		object.findElement(By.xpath("(//*[text()='Delete'])[2]")).click();
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
		    		if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
	    			{
		    			s.write("Record Delete Successfully");
	    			}
		    	}
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
		    	s.write("Country Details Page Test Failed");
		    }
	 }
	 @Then("^Validate  City Details page$")
	 public void location_city_details_page() throws Exception
	 {
		   //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Insurance']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Location']")));
		    WebElement location=object.findElement(By.xpath("//*[text()='Location']"));
		    a.moveToElement(location).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='City']")));
		    object.findElement(By.xpath("//*[text()='City']")).click();
		    Thread.sleep(2000); 
		    if(bp.saveButton.isEnabled() && bp.clearButton.isEnabled())
		    {
		    	try
		    	{
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		bp.click_editButton();
		    	}
		    	catch(Exception ex)
		    	{
		    		Thread.sleep(2000);
		    		object.switchTo().alert().accept();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCountry")));
		    		object.findElement(By.name("ddlCountry")).click();
		    		WebElement country=object.findElement(By.name("ddlCountry"));
		    		Select s1=new Select(country);
		    		s1.selectByValue("INDIA - 91");
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCityname")));
		    		object.findElement(By.name("txtCityname")).sendKeys("pune");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnATGrid")));
		    		object.findElement(By.name("btnATGrid")).click();
		    		Thread.sleep(3000);
    				wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
		    		if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
	    			{
		    			s.write("Record Saved Successfully");
	    			}
	    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
	    			bp.click_clearButton();
	    			Thread.sleep(2000);
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCountry")));
		    		object.findElement(By.name("ddlCountry")).click();
		    		WebElement country1=object.findElement(By.name("ddlCountry"));
		    		Select s11=new Select(country1);
		    		s11.selectByValue("INDIA - 91");
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='Delete'])[1]/following::a[6]")));
		    		object.findElement(By.xpath("(//*[text()='Delete'])[1]/following::a[6]")).click();
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
		    		if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
	    			{
		    			s.write("Record Delete Successfully");
	    			}
		    	}
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
		    	s.write("City Details Page Test Failed");
		    }
	 }
	 @Then("^Validate Medicine Details page$")
	 public void medicine_master() throws Exception
	 {
		   //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Insurance']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Medicine Master']")));
		    object.findElement(By.xpath("//*[text()='Medicine Master']")).click();
		    Thread.sleep(4000); 
		    if(bp.addButton.isEnabled() && bp.editButton.isEnabled())
		    {
		    	wait.until(ExpectedConditions.visibilityOf(bp.addButton));
		    	bp.click_addButton();
		    	try
		    	{
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		bp.click_addButton();
		    	}
		    	catch(Exception er)
		    	{
		    		Thread.sleep(2000);
		    		object.switchTo().alert().accept();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtMCode")));
		    		object.findElement(By.name("txtMCode")).sendKeys("024-181-00");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDescription")));
		    		object.findElement(By.name("txtDescription")).sendKeys("ITEST");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtTradeName")));
		    		object.findElement(By.name("txtTradeName")).sendKeys("TESTING");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtPrice")));
		    		object.findElement(By.name("txtPrice")).sendKeys("3.5");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtStrengthDosage")));
		    		object.findElement(By.name("txtStrengthDosage")).sendKeys("one capsule per day");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtVolumeUnit")));
		    		object.findElement(By.name("txtVolumeUnit")).sendKeys("1.2");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtPackageTypeSize")));
		    		object.findElement(By.name("txtPackageTypeSize")).sendKeys("capsule");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtLegalStatus")));
		    		object.findElement(By.name("txtLegalStatus")).sendKeys("Active");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(@name,'txtEffectiveDate')])[2]")));
		    		object.findElement(By.xpath("(//*[contains(@name,'txtEffectiveDate')])[2]")).sendKeys("14/02/2019");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(@name,'txtExpiry')])[2]")));
		    		object.findElement(By.xpath("(//*[contains(@name,'txtExpiry')])[2]")).sendKeys("07/02/2021");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtAvgValue")));
		    		object.findElement(By.name("txtAvgValue")).sendKeys("4.7");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlRegulatoryTele")));
		    		WebElement reg_dd=object.findElement(By.name("ddlRegulatoryTele"));
		    		reg_dd.click();
		    		Thread.sleep(2000);
		    		reg_dd.sendKeys("CC");
		    		Thread.sleep(2000);
		    		a.sendKeys(Keys.DOWN).build().perform();
		    		a.sendKeys(Keys.ENTER).build().perform();
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		try
		    		{
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    			{
		    				s.write("Record Successfully Saved");
		    			}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    		catch(Exception et)
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Medicine Code is Already Exist')]")));
		    			if(object.findElement(By.xpath("//*[contains(text(),'Medicine Code is Already Exist')]")).isDisplayed())
		    			{
		    				s.write("Medicine Code is Already Exist");
		    			}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    	}
		    	Thread.sleep(2000);
		    	if(bp.editButton.isEnabled())
		    	{
		    		wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    		bp.click_editButton();
		    		try
		    		{
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnFind")));
		    			object.findElement(By.name("btnFind")).click();
		    			bp.click_saveButton();
		    		}
		    		catch(Exception ed)
		    		{
		    			Thread.sleep(2000);
		    			object.switchTo().alert().accept();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtMCode")));
		    			object.findElement(By.name("txtMCode")).sendKeys("024-181-00");
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnFind")));
		    			object.findElement(By.name("btnFind")).click();
		    			Thread.sleep(3000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
			    		bp.click_saveButton();
			    		Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    			{
		    				s.write("Record Successfully Saved");
		    			}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.editButton));
			    		bp.click_editButton();
			    		try
			    		{
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtMCode")));
			    			object.findElement(By.name("txtMCode")).sendKeys("024-181-00");
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnFind")));
			    			object.findElement(By.name("btnFind")).click();
			    			Thread.sleep(3000);
			    			wait.until(ExpectedConditions.visibilityOf(bp.deleteButton));
			    			bp.click_deleteButton();
			    			bp.click_editButton();
			    		}
			    		catch(Exception ey)
			    		{
			    			Thread.sleep(2000);
			    			object.switchTo().alert().accept();
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Deleted Successfully')]")));
			    			if(object.findElement(By.xpath("//*[contains(text(),'Deleted Successfully')]")).isDisplayed())
			    			{
			    				System.out.println("Record Deleted Successfully");
			    			}
			    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
			    			bp.click_clearButton();
			    			Thread.sleep(2000);
			    			wait.until(ExpectedConditions.visibilityOf(bp.editButton));
				    		bp.click_editButton();
				    		try
				    		{
				    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtMCode")));
				    			object.findElement(By.name("txtMCode")).sendKeys("024-181-00");
				    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnFind")));
				    			object.findElement(By.name("btnFind")).click();
				    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Medicine Code Does Not Exist']")));
				    			if(object.findElement(By.xpath("//*[text()='Medicine Code Does Not Exist']")).isDisplayed())
				    			{
				    				s.write("Medicine Master Test Passed");
				    			}
				    		}
				    		catch(Exception ew)
				    		{
				    			System.out.println(ew.getMessage());
				    		}
			    		}
			    		
		    		}
		    		
		    	}
		    	
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
		    	s.write("Medicine Master Page Test Failed");
		    } 
	 }
	 @Then("^validate Department Details page$")
	 public void department_master() throws Exception
	 {
		    //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Provider Network']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Department Master']")));
		    object.findElement(By.xpath("//*[text()='Department Master']")).click();
		    Thread.sleep(4000); 
		    wait.until(ExpectedConditions.visibilityOf(bp.addButton));
		    if(bp.addButton.isEnabled() && bp.editButton.isEnabled())
		    {
		    	object.findElement(By.name("btnAdd")).click();
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtdeptname")));
		    	String dept_no=object.findElement(By.name("txtdeptno")).getAttribute("value");
		    	s.write(dept_no);
		    	Thread.sleep(2000);
		    	try
		    	{
		    		bp.click_saveButton();
		    		object.switchTo().activeElement().sendKeys("zoology");
		    	}
		    	catch(Exception ex)
		    	{
		    		Thread.sleep(3000);
		    		object.switchTo().alert().accept();
		    		object.switchTo().activeElement().sendKeys("Zoology");
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		Thread.sleep(2000);
		    		try
		    		{
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
			    		{
			    			s.write("Record saved Successfully");
			    		}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    		catch(Exception ew)
		    		{
		    			if(object.findElement(By.xpath("//*[contains(text(),'Already Exists.')]")).isDisplayed())
		    			{
		    				s.write("Location Code Already Exists.");
		    			}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}			
		    			
		    	}
		    	Thread.sleep(3000);
		    	wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    	if(bp.editButton.isEnabled())
		    	{
		    		wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    		bp.click_editButton();
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    			bp.click_saveButton();
		    			bp.click_editButton();
		    		}
		    		catch(Exception er)
		    		{
		    			Thread.sleep(3000);
		    			object.switchTo().alert().accept();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDeptno")));
		    			object.findElement(By.name("ddlDeptno")).click();
		    			List<WebElement> dept_dd=object.findElements(By.xpath("//*[@name='ddlDeptno']/option"));
		    			System.out.println(dept_dd.size());
		    			Thread.sleep(2000);
		    			for(int i=2;i<=dept_dd.size();i++)
		    			{
		    				a.sendKeys(Keys.DOWN).build().perform();
		    				Thread.sleep(2000);
		    				String ac_dept=object.findElement(By.xpath("//*[@name='ddlDeptno']/option["+i+"]")).getText();
		    				Thread.sleep(2000);
		    				if(ac_dept.contains(dept_no))
		    				{
		    					a.sendKeys(Keys.ENTER).build().perform();
		    					break;
		    				}
		    			}
		    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    			bp.click_saveButton();
		    			try
		    			{
		    				Thread.sleep(3000);
		    				if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    				{
		    					s.write("Record Edit and Saved Successfully");
		    				}
		    				wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
			    			bp.click_clearButton();
		    			}
		    			catch(Exception et)
		    			{
		    				System.out.println(et.getMessage());
		    			}
		    		}
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    		bp.click_editButton();
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDeptno")));
		    			object.findElement(By.name("ddlDeptno")).click();
		    			List<WebElement> dept_dd=object.findElements(By.xpath("//*[@name='ddlDeptno']/option"));
		    			System.out.println(dept_dd.size());
		    			Thread.sleep(2000);
		    			for(int i=2;i<=dept_dd.size();i++)
		    			{
		    				a.sendKeys(Keys.DOWN).build().perform();
		    				Thread.sleep(2000);
		    				String ac_dept=object.findElement(By.xpath("//*[@name='ddlDeptno']/option["+i+"]")).getText();
		    				Thread.sleep(2000);
		    				if(ac_dept.contains(dept_no))
		    				{
		    					a.sendKeys(Keys.ENTER).build().perform();
		    					break;
		    				}
		    			}
		    			wait.until(ExpectedConditions.visibilityOf(bp.deleteButton));
		    			bp.click_deleteButton();
		    			bp.click_saveButton();
		    		}
		    		catch(Exception eq)
		    		{
		    			Thread.sleep(3000);
		    			object.switchTo().alert().accept();
		    			try
		    			{
		    				Thread.sleep(4000);
		    				if(object.findElement(By.xpath("//*[contains(text(),'Deleted Successfully')]")).isDisplayed())
		    				{
		    					s.write("Record Deleted Successfully");
		    				}
		    				wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
			    			bp.click_clearButton();
		    			}
		    			catch(Exception ew)
		    			{
		    				System.out.println(ew.getMessage());
		    			}
		    		}
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOf(bp.editButton));
			    		bp.click_editButton();
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDeptno")));
		    			object.findElement(By.name("ddlDeptno")).click();
		    			List<WebElement> dept_dd=object.findElements(By.xpath("//*[@name='ddlDeptno']/option"));
		    			System.out.println(dept_dd.size());
		    			Thread.sleep(2000);
		    			for(int i=2;i<=dept_dd.size();i++)
		    			{
		    				a.sendKeys(Keys.DOWN).build().perform();
		    				Thread.sleep(2000);
		    				String ac_dept=object.findElement(By.xpath("//*[@name='ddlDeptno']/option["+i+"]")).getText();
		    				if(ac_dept.contains(dept_no))
		    				{
		    					a.sendKeys(Keys.ENTER).build().perform();
		    					break;
		    				}
		    			}
		    			s.write("Department Master Page Test Passed");
		    		}
		    		catch(Exception er)
		    		{
		    			s.write("Department Master Page Test Passed");
		    		}
		    	}
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
				s.write("Department master Page Test Failed");
				System.exit(0);
		    }
	 }
	 @Then("^validate Hospital Room Type Details page$")
	 public void room_type_master() throws Exception
	 {
		    //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Provider Network']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Room Types']")));
		    object.findElement(By.xpath("//*[text()='Room Types']")).click();
		    Thread.sleep(4000); 
		    wait.until(ExpectedConditions.visibilityOf(bp.addButton));
		    if(bp.addButton.isEnabled() && bp.editButton.isEnabled())
		    {
		    	wait.until(ExpectedConditions.visibilityOf(bp.addButton));
		    	bp.click_addButton();
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtRoomCode")));
		    	String roomCode_no=object.findElement(By.name("txtRoomCode")).getAttribute("value");
		    	s.write(roomCode_no);
		    	Thread.sleep(2000);
		    	try
		    	{
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		bp.click_editButton();
		    	}
		    	catch(Exception ex)
		    	{
		    		Thread.sleep(2000);
		    		object.switchTo().alert().accept();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtRoomType")));
		    		object.findElement(By.name("txtRoomType")).sendKeys("AC-SuiteRoom");
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		Thread.sleep(2000);
		    		try
		    		{
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
			    		{
			    			s.write("Record saved Successfully");
			    		}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    		catch(Exception ew)
		    		{
		    			if(object.findElement(By.xpath("//*[contains(text(),'Already Exist')]")).isDisplayed())
		    			{
		    				s.write(" Code Already Exists.");
		    			}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}	
		    	}
		    	Thread.sleep(2000);
		    	if(bp.editButton.isEnabled())
		    	{
		    		wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    		bp.click_editButton();
		    		try
		    		{
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    			bp.click_saveButton();
		    			bp.click_editButton();
		    		}
		    		catch(Exception et)
		    		{
		    			Thread.sleep(2000);
		    			object.switchTo().alert().accept();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlRoomCode")));
		    			object.findElement(By.name("ddlRoomCode")).click();
		    			List<WebElement> roomCode_dd=object.findElements(By.xpath("//*[@name='ddlRoomCode']/option"));
		    			System.out.println(roomCode_dd.size());
		    			Thread.sleep(2000);
		    			for(int i=2;i<=roomCode_dd.size();i++)
		    			{
		    				Thread.sleep(2000);
		    				a.sendKeys(Keys.DOWN).build().perform();
		    				String ac_roomCode=object.findElement(By.xpath("//*[@name='ddlRoomCode']/option["+i+"]")).getText();
		    				Thread.sleep(2000);
		    				if(ac_roomCode.contains(roomCode_no))
		    				{
		    					a.sendKeys(Keys.ENTER).build().perform();
		    					break;
		    				}
		    			}
		    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
			    		bp.click_saveButton();
			    		Thread.sleep(2000);
			    		if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
			    		{
			    			s.write("Record saved Successfully");
			    		}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    			try
		    			{
		    				wait.until(ExpectedConditions.visibilityOf(bp.editButton));
				    		bp.click_editButton();
		    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlRoomCode")));
			    			object.findElement(By.name("ddlRoomCode")).click();
			    			List<WebElement> roomCode_dd1=object.findElements(By.xpath("//*[@name='ddlRoomCode']/option"));
			    			System.out.println(roomCode_dd1.size());
			    			Thread.sleep(2000);
			    			for(int i=2;i<=roomCode_dd1.size();i++)
			    			{
			    				Thread.sleep(2000);
				    			wait.until(ExpectedConditions.visibilityOf(bp.editButton));
					    		bp.click_editButton();
			    				a.sendKeys(Keys.DOWN).build().perform();
			    				String ac_roomCode=object.findElement(By.xpath("//*[@name='ddlRoomCode']/option["+i+"]")).getText();
			    				Thread.sleep(2000);
			    				if(ac_roomCode.contains(roomCode_no))
			    				{
			    					a.sendKeys(Keys.ENTER).build().perform();
			    					break;
			    				}
			    			}
			    			wait.until(ExpectedConditions.visibilityOf(bp.deleteButton));
			    			bp.click_deleteButton();
			    			bp.click_editButton();
		    			}
		    			catch(Exception ey)
		    			{
		    				Thread.sleep(2000);
		    				object.switchTo().alert().accept();
		    				try
			    			{
			    				Thread.sleep(4000);
			    				if(object.findElement(By.xpath("//*[contains(text(),'Deleted Successfully')]")).isDisplayed())
			    				{
			    					s.write("Record Deleted Successfully");
			    				}
			    				wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
				    			bp.click_clearButton();
			    			}
			    			catch(Exception ew)
			    			{
			    				System.out.println(ew.getMessage());
			    			}
		    				Thread.sleep(2000);
		    				wait.until(ExpectedConditions.visibilityOf(bp.editButton));
				    		bp.click_editButton();
		    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlRoomCode")));
			    			object.findElement(By.name("ddlRoomCode")).click();
			    			List<WebElement> roomCode_dd1=object.findElements(By.xpath("//*[@name='ddlRoomCode']/option"));
			    			System.out.println(roomCode_dd1.size());
			    			Thread.sleep(2000);
			    			for(int i=2;i<=roomCode_dd1.size();i++)
			    			{
			    				Thread.sleep(2000);
				    			wait.until(ExpectedConditions.visibilityOf(bp.editButton));
					    		bp.click_editButton();
			    				a.sendKeys(Keys.DOWN).build().perform();
			    				String ac_roomCode=object.findElement(By.xpath("//*[@name='ddlRoomCode']/option["+i+"]")).getText();
			    				Thread.sleep(2000);
			    				if(ac_roomCode.contains(roomCode_no))
			    				{
			    					a.sendKeys(Keys.ENTER).build().perform();
			    					break;
			    				}
			    			}
			    			s.write("Rooms Type Master tes Passed");
		    			}
		    			
		    		}
		    	}
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
				s.write("Room Type master Page Test Failed");
				System.exit(0);
		    }
	 }
	 @Then("^validate Speciality Master  page$")
	 public void speciality_master() throws Exception
	 {
		    //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Provider Network']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Speciality Master']")));
		    object.findElement(By.xpath("//*[text()='Speciality Master']")).click();
		    Thread.sleep(4000); 
		    wait.until(ExpectedConditions.visibilityOf(bp.addButton));
		    if(bp.addButton.isEnabled() && bp.editButton.isEnabled())
		    {
		    	wait.until(ExpectedConditions.visibilityOf(bp.addButton));
		    	bp.click_addButton();
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtSpeCode")));
		    	String specialityCode_no=object.findElement(By.name("txtSpeCode")).getAttribute("value");
		    	s.write(specialityCode_no);
		    	Thread.sleep(2000);
		    	try
		    	{
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		bp.click_editButton();
		    	}
		    	catch(Exception ex)
		    	{
		    		Thread.sleep(2000);
		    		object.switchTo().alert().accept();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtSpeCode")));
		    		object.findElement(By.name("txtDescription")).sendKeys("neurologist");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCPTCode")));
		    		object.findElement(By.name("txtCPTCode")).sendKeys("102E");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnAdd2Grid")));
		    		object.findElement(By.name("btnAdd2Grid")).click();
		    		Thread.sleep(3000);
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		Thread.sleep(2000);
		    		try
		    		{
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
			    		{
			    			s.write("Record saved Successfully");
			    		}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    		catch(Exception ew)
		    		{
		    			if(object.findElement(By.xpath("//*[contains(text(),'Already Exist')]")).isDisplayed())
		    			{
		    				s.write(" Code Already Exists.");
		    			}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}	
		    	}
		    	Thread.sleep(2000);
		    	if(bp.editButton.isEnabled())
		    	{
		    		wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    		bp.click_editButton();
		    		try
		    		{
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    			bp.click_saveButton();
		    			bp.click_editButton();
		    		}
		    		catch(Exception et)
		    		{
		    			Thread.sleep(2000);
		    			object.switchTo().alert().accept();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlSpeCode")));
		    			object.findElement(By.name("ddlSpeCode")).click();
		    			List<WebElement> roomCode_dd=object.findElements(By.xpath("//*[@name='ddlSpeCode']/option"));
		    			System.out.println(roomCode_dd.size());
		    			Thread.sleep(2000);
		    			for(int i=2;i<=roomCode_dd.size();i++)
		    			{
		    				Thread.sleep(2000);
		    				a.sendKeys(Keys.DOWN).build().perform();
		    				String ac_specialityCode=object.findElement(By.xpath("//*[@name='ddlSpeCode']/option["+i+"]")).getText();
		    				Thread.sleep(2000);
		    				if(ac_specialityCode.contains(specialityCode_no))
		    				{
		    					a.sendKeys(Keys.ENTER).build().perform();
		    					break;
		    				}
		    			}
		    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
			    		bp.click_saveButton();
			    		Thread.sleep(2000);
			    		if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
			    		{
			    			s.write("Record saved Successfully");
			    		}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    			s.write("Speciality Master Page Test Passed");
		    			
		    		}
		    	}
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
				s.write("Speciality master Page Test Failed");
				System.exit(0);
		    }
	 }
	 @Then("^validate Service Type Master  page$")
	 public void service_type_master() throws Exception
	 {
		   //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Provider Network']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Service Type Master']")));
		    object.findElement(By.xpath("//*[text()='Service Type Master']")).click();
		    Thread.sleep(4000); 
		    wait.until(ExpectedConditions.visibilityOf(bp.addButton));
		    if(bp.addButton.isEnabled() && bp.editButton.isEnabled())
		    {
		    	object.findElement(By.name("btnAdd")).click();
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDescription")));
		    	String service_code=object.findElement(By.name("txtServiceCode")).getAttribute("value");
		    	s.write(service_code);
		    	Thread.sleep(2000);
		    	try
		    	{
		    		bp.click_saveButton();
		    		object.switchTo().activeElement().sendKeys("Cardio");
		    	}
		    	catch(Exception ex)
		    	{
		    		Thread.sleep(3000);
		    		object.switchTo().alert().accept();
		    		object.switchTo().activeElement().sendKeys("Cardio");
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		Thread.sleep(2000);
		    		try
		    		{
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
			    		{
			    			s.write("Record saved Successfully");
			    		}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    		catch(Exception ew)
		    		{
		    			if(object.findElement(By.xpath("//*[contains(text(),'Already Exist')]")).isDisplayed())
		    			{
		    				s.write(" Code Already Exists.");
		    			}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}			
		    			
		    	}
		    	//Thread.sleep(3000);
		    	wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    	if(bp.editButton.isEnabled())
		    	{
		    		wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    		bp.click_editButton();
	    			Thread.sleep(2000);
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlServiceCode")));
	    			object.findElement(By.name("ddlServiceCode")).click();
	    			List<WebElement> service_dd=object.findElements(By.xpath("//*[@name='ddlServiceCode']/option"));
	    			System.out.println(service_dd.size());
	    			Thread.sleep(2000);
	    			for(int i=2;i<=service_dd.size();i++)
	    			{
	    				a.sendKeys(Keys.DOWN).build().perform();
	    				Thread.sleep(2000);
	    				String ac_service=object.findElement(By.xpath("//*[@name='ddlServiceCode']/option["+i+"]")).getText();
	    				Thread.sleep(2000);
	    				if(ac_service.contains(service_code))
	    				{
	    					a.sendKeys(Keys.ENTER).build().perform();
	    					break;
	    				}
	    			}
	    			Thread.sleep(2000);
	    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
	    			bp.click_saveButton();
	    			try
	    			{
	    				Thread.sleep(3000);
	    				if(object.findElement(By.xpath("//*[contains(text(),'Updated Successfully')]")).isDisplayed())
	    				{
	    					s.write("Record Edit and Saved Successfully");
	    				}
	    				wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
	    			}
	    			catch(Exception et)
	    			{
	    				System.out.println(et.getMessage());
	    			}
	    			Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    		bp.click_editButton();
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlServiceCode")));
		    			object.findElement(By.name("ddlServiceCode")).click();
		    			List<WebElement> ac_service=object.findElements(By.xpath("//*[@name='ddlServiceCode']/option"));
		    			System.out.println(ac_service.size());
		    			Thread.sleep(2000);
		    			for(int i=2;i<=ac_service.size();i++)
		    			{
		    				a.sendKeys(Keys.DOWN).build().perform();
		    				Thread.sleep(2000);
		    				String ac_service1=object.findElement(By.xpath("//*[@name='ddlServiceCode']/option["+i+"]")).getText();
		    				Thread.sleep(2000);
		    				if(ac_service1.contains(service_code))
		    				{
		    					a.sendKeys(Keys.ENTER).build().perform();
		    					break;
		    				}
		    			}
		    			wait.until(ExpectedConditions.visibilityOf(bp.deleteButton));
		    			bp.click_deleteButton();
		    			bp.click_saveButton();
		    		}
		    		catch(Exception eq)
		    		{
		    			Thread.sleep(3000);
		    			object.switchTo().alert().accept();
		    			try
		    			{
		    				Thread.sleep(4000);
		    				if(object.findElement(By.xpath("//*[contains(text(),'Deleted Successfully')]")).isDisplayed())
		    				{
		    					s.write("Record Deleted Successfully");
		    				}
		    				wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
			    			bp.click_clearButton();
		    			}
		    			catch(Exception ew)
		    			{
		    				System.out.println(ew.getMessage());
		    			}
		    		}
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOf(bp.editButton));
			    		bp.click_editButton();
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlServiceCode")));
		    			object.findElement(By.name("ddlServiceCode")).click();
		    			List<WebElement> service_dd1=object.findElements(By.xpath("//*[@name='ddlServiceCode']/option"));
		    			System.out.println(service_dd1.size());
		    			Thread.sleep(2000);
		    			for(int i=2;i<=service_dd1.size();i++)
		    			{
		    				a.sendKeys(Keys.DOWN).build().perform();
		    				Thread.sleep(2000);
		    				String ac_service=object.findElement(By.xpath("//*[@name='ddlServiceCode']/option["+i+"]")).getText();
		    				if(ac_service.contains(service_code))
		    				{
		    					a.sendKeys(Keys.ENTER).build().perform();
		    					break;
		    				}
		    			}
		    			s.write("Department Master Page Test Passed");
		    		}
		    		catch(Exception er)
		    		{
		    			s.write("Service Type Master Page Test Passed");
		    		}
		    	}
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
				s.write("Service type master Page Test Failed");
				System.exit(0);
		    } 
	 }
	 @Then("^validate Provider Classification  page$")
	 public void provider_classification() throws Exception
	 {
		   //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Provider Network']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Provider Classification']")));
		    object.findElement(By.xpath("//*[text()='Provider Classification']")).click();
		    Thread.sleep(4000);
		    if(object.findElement(By.name("btnQuery")).isEnabled() && bp.clearButton.isEnabled() && object.findElement(By.name("btnAddNew")).isEnabled())
		    {
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnAddNew")));
		    	object.findElement(By.name("btnAddNew")).click();
		    	Thread.sleep(2000);
		    	ArrayList<String> winDows=new ArrayList<String>(object.getWindowHandles());
		    	object.switchTo().window(winDows.get(1));
		    	if(object.findElement(By.name("btnSave")).isDisplayed())
		    	{
		    		object.switchTo().activeElement().sendKeys("ITEST");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlClasificarionWithChk")));
		    		WebElement classification=object.findElement(By.name("ddlClasificarionWithChk"));
		    		classification.click();
		    		classification.sendKeys("v");
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='VIP+-11']/input")));
		    		object.findElement(By.xpath("//*[text()='VIP+-11']/input")).click();
		    		Robot r=new Robot();
		    		Thread.sleep(2000);
		    		r.keyPress(KeyEvent.VK_ESCAPE);
		    		r.keyRelease(KeyEvent.VK_ESCAPE);
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Territory']")));
		    		object.findElement(By.xpath("//*[text()='Territory']")).click();
		    		Thread.sleep(1000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlterritory")));
		    		WebElement territory=object.findElement(By.name("ddlterritory"));
		    		territory.click();
		    		territory.sendKeys("wo");
		    		Thread.sleep(2000);
		    		a.sendKeys(Keys.ENTER).build().perform();
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnSave")));
		    		object.findElement(By.name("btnSave")).click();
		    		Thread.sleep(2000);
		    		if(object.findElement(By.name("btndecisionstatus")).isEnabled())
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btndecisionstatus")));
		    			object.findElement(By.name("btndecisionstatus")).click();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Decision Status(Provider Classification)']")));
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlAppStatus")));
		    			object.findElement(By.name("ddlAppStatus")).click();
		    			WebElement status=object.findElement(By.name("ddlAppStatus"));
		    			Select s1=new Select(status);
		    			s1.selectByVisibleText("VERIFY");
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Btnverify")));
		    			object.findElement(By.name("Btnverify")).click();
		    			Thread.sleep(2000);
		    			if(object.findElement(By.xpath("//*[contains(text(),'Verified Successfully')]")).isDisplayed())
		    			{
		    				object.findElement(By.name("ddlAppStatus")).click();
			    			WebElement Approval_status=object.findElement(By.name("ddlAppStatus"));
			    			Select s2=new Select(Approval_status);
			    			s2.selectByVisibleText("APPROVED");
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnApprove")));
			    			object.findElement(By.name("btnApprove")).click();
			    			Thread.sleep(2000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
		    			}//if closing
		    		}//if closing
		    		
		    	}//if closing
    	    	Thread.sleep(2000);
		    	object.switchTo().window(winDows.get(0));
		    	wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    	bp.click_clearButton();
		    	Thread.sleep(2000);
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlterritory")));
	    		WebElement territory1=object.findElement(By.name("ddlterritory"));
	    		Thread.sleep(2000);
	    		a.click(territory1).build().perform();
	    		Thread.sleep(1000);
	    		territory1.sendKeys("wo");
	    		Thread.sleep(2000);
	    		a.sendKeys(Keys.ENTER).build().perform();
	    		Thread.sleep(2000);
	    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnQuery")));
	    		object.findElement(By.name("btnQuery")).click();
	    		Thread.sleep(3000);
	    		try
	    		{
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='Decision Status'])[2]/following::td[text()='ACTIVE'][1]")));
	    			if(object.findElement(By.xpath("(//*[text()='Decision Status'])[2]/following::td[text()='ACTIVE'][1]")).isDisplayed())
	    			{
	    				s.write("Decision Status is Active");
	    			}
	    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
	    			bp.click_clearButton();
	    		}
	    		catch(Exception er)
	    		{
	    			s.write("Decision Status is Pending");
	    		}
		    	
		    }//if closing
		    else
		    {
		    	String x=ss.screenshot(object);
				s.write("Provider Classification Test Failed");
				System.exit(0);
		    } 	
	 }
	 @Then("^validate VAT MASTER  page$")
	 public void vat_master() throws Exception
	 {
		    //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Provider Network']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Service VAT Master']")));
		    object.findElement(By.xpath("//*[text()='Service VAT Master']")).click();
		    Thread.sleep(4000);
		    if(bp.addButton.isEnabled() && bp.editButton.isEnabled())
		    {
		    	wait.until(ExpectedConditions.visibilityOf(bp.addButton));
		    	bp.click_addButton();
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@name='txtCode']")));
		    	String taxCode=object.findElement(By.xpath("//*[@name='txtCode']")).getAttribute("value");
		    	System.out.println(taxCode);
		    	try
		    	{
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		bp.click_editButton();
		    	}
		    	catch(Exception ex)
		    	{
		    		Thread.sleep(2000);
		    		object.switchTo().alert().accept();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlTaxType")));
		    		object.findElement(By.name("ddlTaxType")).click();
		    		WebElement taxType_dd=object.findElement(By.name("ddlTaxType"));
		    		Select s1=new Select(taxType_dd);
		    		s1.selectByVisibleText("GENERAL AUTHORITY OF ZAKAT AND TAX~GAZT");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlScheme")));
		    		object.findElement(By.name("ddlScheme")).click();
		    		WebElement scheme_dd=object.findElement(By.name("ddlScheme"));
		    		Select s2=new Select(scheme_dd);
		    		s2.selectByVisibleText("CLAIMS");
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtVATDesc")));
		    		object.findElement(By.name("txtVATDesc")).sendKeys("claimsVat");
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(@name,'txtEFDate')])[2]")));
		    		object.findElement(By.xpath("(//*[contains(@name,'txtEFDate')])[2]")).sendKeys("13/02/2019");
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(@name,'txtETDate')])[2]")));
		    		object.findElement(By.xpath("(//*[contains(@name,'txtETDate')])[2]")).sendKeys("13/02/2020");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlRegion")));
		    		object.findElement(By.name("ddlRegion")).click();
		    		WebElement region_dd=object.findElement(By.name("ddlRegion"));
		    		Select s3=new Select(region_dd);
		    		s3.selectByVisibleText("NON SAUDI~2");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlapplicablefor")));
		    		object.findElement(By.name("ddlapplicablefor")).click();
		    		WebElement applicable_dd=object.findElement(By.name("ddlapplicablefor"));
		    		Select s4=new Select(applicable_dd);
		    		s4.selectByVisibleText("Dental-96");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlcaltype")));
		    		object.findElement(By.name("ddlcaltype")).click();
		    		WebElement calType_dd=object.findElement(By.name("ddlcaltype"));
		    		Select s5=new Select(calType_dd);
		    		s5.selectByVisibleText("Percentage");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtvatvalue")));
		    		object.findElement(By.name("txtvatvalue")).sendKeys("4.50");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnAdd2TEGrid")));
		    		object.findElement(By.name("btnAdd2TEGrid")).click();
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		try
		    		{
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    			{
		    				s.write("Record saved successfully");
		    			}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    		catch(Exception et)
		    		{
		    			System.out.println(et.getMessage());
		    		}
		    	}
		    	Thread.sleep(2000);
		    	if(bp.editButton.isEnabled())
		    	{
		    		wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    		bp.click_editButton();
		    		try
		    		{
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    			bp.click_saveButton();
		    			bp.click_editButton();
		    		}
		    		catch(Exception ey)
		    		{
		    			Thread.sleep(2000);
		    			object.switchTo().alert().accept();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCode")));
		    			List<WebElement> taxCode_list=object.findElements(By.xpath("//*[@name='ddlCode']/option"));
		    			System.out.println(taxCode_list.size());
		    			object.findElement(By.name("ddlCode")).click();
		    			Thread.sleep(2000);
		    			for(int i=2;i<=taxCode_list.size();i++)
		    			{
		    				a.sendKeys(Keys.DOWN).build().perform();
		    				String ac_taxCode=object.findElement(By.xpath("//*[@name='ddlCode']/option["+i+"]")).getText();
		    				Thread.sleep(2000);
		    				if(ac_taxCode.contains(taxCode))
		    				{
		    					a.sendKeys(Keys.ENTER).build().perform();
		    					break;
		    				}
		    			}
		    		}
		    	}
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
				s.write("Service Vat Master Test Failed");
				et.log(LogStatus.FAIL,"Service Vat Master Test Failed"+et.addScreenCapture(x));
				System.exit(0);
		    }
	 }
	 @Then("^validate Provider Group  page$")
	 public void provider_group() throws Exception
	 {
		    //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Provider Network']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Provider Group']")));
		    object.findElement(By.xpath("//*[text()='Provider Group']")).click();
		    Thread.sleep(4000); 
		    if(object.findElement(By.name("btnQuery")).isEnabled() && bp.clearButton.isEnabled() && object.findElement(By.name("btnAddNew")).isEnabled())
		    {
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnAddNew")));
		    	object.findElement(By.name("btnAddNew")).click();
		    	Thread.sleep(2000);
		    	ArrayList<String> a1=new ArrayList<String>(object.getWindowHandles());
		    	object.switchTo().window(a1.get(1));
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnSave")));
		    	if(object.findElement(By.name("btnSave")).isEnabled())
		    	{
		    		Thread.sleep(2000);
		    		object.manage().window().maximize();
		    		object.switchTo().activeElement().sendKeys("TESTING");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtHOAddress")));
		    		object.findElement(By.name("txtHOAddress")).sendKeys("JEDDAH");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtWebSite")));
		    		object.findElement(By.name("txtWebSite")).sendKeys("www.lkss.com");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCityTele")));
		    		WebElement city_dd=object.findElement(By.name("ddlCityTele"));
		            city_dd.click();
		            Thread.sleep(2000);
		            city_dd.sendKeys("je");
		            a.sendKeys(Keys.DOWN).build().perform();
		            a.sendKeys(Keys.ENTER).build().perform();
		            Thread.sleep(1000);
		            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtPhoneNo")));
		            object.findElement(By.name("txtPhoneNo")).sendKeys("543678210");
		            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtFaxNo")));
		            object.findElement(By.name("txtFaxNo")).sendKeys("8374u3yr");
		            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtContactPerson")));
		            object.findElement(By.name("txtContactPerson")).sendKeys("lokehs");
		            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDesignation")));
		            object.findElement(By.name("txtDesignation")).sendKeys("ITEST");
		            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCommType")));
		            object.findElement(By.name("ddlCommType")).click();
		            WebElement commType_dd=object.findElement(By.name("ddlCommType"));
		            Thread.sleep(3000);
		            Select s1=new Select(commType_dd);
		            s1.selectByVisibleText("EMAIL-4");
		            Thread.sleep(2000);
		            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCommInfo")));
		            object.findElement(By.name("txtCommInfo")).sendKeys("lkss23@gmail.com");
		            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnAddContactGrid")));
		            object.findElement(By.name("btnAddContactGrid")).click();
		            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnSave")));
		            object.findElement(By.name("btnSave")).click();
		            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
		            if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		            {
		            	s.write("Record Saved Successfully");
		            }
		            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnAddDbProvider")));
		            object.findElement(By.name("btnAddDbProvider")).click();
		            try
		            {
		            	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Error converting data type')]")));
			            if(object.findElement(By.xpath("//*[contains(text(),'Error converting data type')]")).isDisplayed())
			            {
			            	Date d=new Date();
			    			SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
			    			String ssname=sf.format(d)+".png";
			    			JavascriptExecutor js=(JavascriptExecutor)object;
			    			WebElement err_msg=object.findElement(By.xpath("//*[contains(text(),'Error converting data type')]"));
			    			js.executeScript("arguments[0].style.border='5px red solid';",err_msg);
			    			Thread.sleep(3000);
			    			File src=((TakesScreenshot)object).getScreenshotAs(OutputType.FILE);
			    			File dest=new File("E:\\SE_AutomationScripts\\scripts\\ssname");
			    			FileHandler.copy(src, dest);
			    			s.write("Error Message Will Be Displayed");
			    			et.log(LogStatus.ERROR,"Error Message Will Be Displayed"+et.addScreenCapture(ssname));
			    			
			            }	
		            }
		            catch(Exception ed)
		            {
		            	
		            }
		            Thread.sleep(2000);	
		            object.switchTo().activeElement().sendKeys("TESTING");
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtProviderCodeSE")));
	    			object.findElement(By.name("txtProviderCodeSE")).sendKeys("46287684");
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlProviderClassification")));
	    			WebElement prvdr_classification=object.findElement(By.name("ddlProviderClassification"));
	    			prvdr_classification.click();
	    			Thread.sleep(2000);
	    			prvdr_classification.sendKeys("v");
	    			Thread.sleep(2000);
	    			a.sendKeys(Keys.DOWN).build().perform();
	    			Thread.sleep(1000);
	    			a.sendKeys(Keys.ENTER).build().perform();
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlProviderType")));
	    			object.findElement(By.name("ddlProviderType")).click();
	    			WebElement providerType_dd=object.findElement(By.name("ddlProviderType"));
	    			Select s2=new Select(providerType_dd);
	    			s2.selectByVisibleText("DAYCARE CENTER");
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtShortName")));
	    			object.findElement(By.name("txtShortName")).sendKeys("lkms");
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtProviderAddress")));
	    			object.findElement(By.name("txtProviderAddress")).sendKeys("JEDDAH-202");
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCityTele")));
	    			WebElement city1=object.findElement(By.name("ddlCityTele"));
	    			city1.click();
	    			Thread.sleep(2000);
	    			city1.sendKeys("je");
	    			Thread.sleep(2000);
	    			a.sendKeys(Keys.DOWN).build().perform();
	    			Thread.sleep(1000);
	    			a.sendKeys(Keys.ENTER).build().perform();
	    			Thread.sleep(1000);
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtPhoneNo")));
	    			object.findElement(By.name("txtPhoneNo")).sendKeys("591432786");
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtFaxNo")));
	    			object.findElement(By.name("txtFaxNo")).sendKeys("09327yeufv");	
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='Time'])[1]")));
	    			object.findElement(By.xpath("(//*[text()='Time'])[1]")).click();
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='Time Picker'])[1]/following::tr[4]/td[1]/a[text()='09:00:00']")));
	    			object.findElement(By.xpath("(//*[text()='Time Picker'])[1]/following::tr[4]/td[1]/a[text()='09:00:00']")).click();
	    			Thread.sleep(2000);
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='Time'])[2]")));
	    			object.findElement(By.xpath("(//*[text()='Time'])[2]")).click();
	    			Thread.sleep(2000);
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='Time Picker'])[1]/following::tr[8]/td[2]/a[text()='22:00:00']")));
	    			object.findElement(By.xpath("(//*[text()='Time Picker'])[1]/following::tr[8]/td[2]/a[text()='22:00:00']")).click();
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtContactPerson")));
	    			object.findElement(By.name("txtContactPerson")).sendKeys("lokeshk");
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDesignation")));
	    			object.findElement(By.name("txtDesignation")).sendKeys("Testqq");
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDepartment")));
	    			object.findElement(By.name("txtDepartment")).sendKeys("Cardiology");
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlComType")));
	    			object.findElement(By.name("ddlComType")).click();
	    			WebElement comm_dd=object.findElement(By.name("ddlComType"));
	    			Select s3=new Select(comm_dd);
	    			s3.selectByVisibleText("EMAIL-4");
	    			Thread.sleep(2000);
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCommInfo")));
	    			object.findElement(By.name("txtCommInfo")).sendKeys("kummari@gmail.com");
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnProvContactA2Grid")));
	    			object.findElement(By.name("btnProvContactA2Grid")).click();
	    			Thread.sleep(2000);
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlRegulatoryTele")));
	    			WebElement reg_type_dd=object.findElement(By.name("ddlRegulatoryTele"));
	    			reg_type_dd.click();
	    			reg_type_dd.sendKeys("cc");
	    			Thread.sleep(2000);
	    			a.sendKeys(Keys.UP).build().perform();
	    			a.sendKeys(Keys.UP).build().perform();
	    			a.sendKeys(Keys.ENTER).build().perform();
	    			Thread.sleep(2000);
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtLicenseNo")));
	    			object.findElement(By.name("txtLicenseNo")).sendKeys("34863482q");
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(@name,'txtValidFrom')])[2]")));
	    			object.findElement(By.xpath("(//*[contains(@name,'txtValidFrom')])[2]")).sendKeys("18/02/2019");
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(@name,'txtValidTo')])[2]")));
	    			object.findElement(By.xpath("(//*[contains(@name,'txtValidTo')])[2]")).sendKeys("18/02/2020");
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnLicAddGrid")));
	    			object.findElement(By.name("btnLicAddGrid")).click();
	    			Thread.sleep(3000);
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlModeofPayment")));
	    			object.findElement(By.name("ddlModeofPayment")).click();
	    			WebElement paymentMode_dd=object.findElement(By.name("ddlModeofPayment"));
	    			Select s4=new Select(paymentMode_dd);
	    			s4.selectByVisibleText("TRANSFER");
	    			Thread.sleep(1000);
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtPaymentTerms")));
	    			object.findElement(By.name("txtPaymentTerms")).sendKeys("4");
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtBName")));
	    			object.findElement(By.name("txtBName")).sendKeys("DCB");
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtBBranch")));
	    			object.findElement(By.name("txtBBranch")).sendKeys("JEDDAH-202");
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtBAccName")));
	    			object.findElement(By.name("txtBAccName")).sendKeys("IBname");
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtBankActno")));
	    			object.findElement(By.name("txtBankActno")).sendKeys("03667380033");
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtBankAddress")));
	    			object.findElement(By.name("txtBankAddress")).sendKeys("JEDDAH");
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtIBANNo")));
	    			object.findElement(By.name("txtIBANNo")).sendKeys("38r89437023");
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlcchiprovider")));
	    			object.findElement(By.name("ddlcchiprovider")).click();
	    			WebElement cchi_provider=object.findElement(By.name("ddlcchiprovider"));
	    			Select s5=new Select(cchi_provider);
	    			s5.selectByVisibleText("OHN");
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(@name,'txtEFFDate')])[2]")));
	    			object.findElement(By.xpath("(//*[contains(@name,'txtEFFDate')])[2]")).sendKeys("19/02/2019");
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnCCHIprovAddToGrid")));
	    			object.findElement(By.name("btnCCHIprovAddToGrid")).click();
	    			Thread.sleep(2000);
	    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnSave")));
	    			object.findElement(By.name("btnSave")).click();
	    			try
	    			{
	    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Object variable or With block variable not set']")));
		    			if(object.findElement(By.xpath("//*[text()='Object variable or With block variable not set']")).isDisplayed())
		    			{
		    				Date d1=new Date();
			    			SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
			    			String ssname1=sdf.format(d1)+".png";
			    			JavascriptExecutor js=(JavascriptExecutor)object;
			    			WebElement err_msg1=object.findElement(By.xpath("//*[text()='Object variable or With block variable not set']"));
			    			js.executeScript("arguments[0].style.border='5px red solid';",err_msg1);
			    			Thread.sleep(3000);
			    			File src1=((TakesScreenshot)object).getScreenshotAs(OutputType.FILE);
			    			File dest1=new File("E:\\SE_AutomationScripts\\scripts\\ssname1");
			    			FileHandler.copy(src1, dest1);
			    			s.write("Error Message Will Be Displayed");
			    			et.log(LogStatus.ERROR,"Error Message Will Be Displayed"+et.addScreenCapture(ssname1));
			    			object.close();
	    			    }
	    			
	    			}
	    			catch(Exception ef)
	    			{
	    				System.out.println(ef.getMessage());
	    			}
		    	}
		    	object.switchTo().window(a1.get(0));
		    	
		    	
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
				s.write("Provider Group Test Failed");
				et.log(LogStatus.FAIL,"Provider Group Test Failed"+et.addScreenCapture(x));
				System.exit(0);
		    }
	 }
	 @Then("^validate DB Provider  page$")
	 public void db_provider() throws Exception
	 {
		//homepage will be displayed
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
	    object.findElement(By.xpath("//*[text()='Close']")).click();
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
	    Actions a=new Actions(object);
	    //move to masters module
	    WebElement master=object.findElement(By.xpath("//*[text()='Provider Network']"));
	    a.moveToElement(master).build().perform();
	    Thread.sleep(2000);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='DB Provider']")));
	    object.findElement(By.xpath("//*[text()='DB Provider']")).click();
	    Thread.sleep(4000); 
	    if(object.findElement(By.name("btnQuery")).isEnabled() && bp.clearButton.isEnabled() && object.findElement(By.name("btnAddNew")).isEnabled())
	    {
	    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnAddNew")));
	    	object.findElement(By.name("btnAddNew")).click();
	    	Thread.sleep(2000);
	    	ArrayList<String> a1=new ArrayList<String>(object.getWindowHandles());
	    	object.switchTo().window(a1.get(1));
	    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnSave")));
	    	if(object.findElement(By.name("btnSave")).isEnabled())
	    	{
             /* wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Error converting data type nvarchar to int.']")));
              Date d1=new Date();
  			  SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
  			  String ssname1=sdf.format(d1)+".png";
  			  JavascriptExecutor js=(JavascriptExecutor)object;
  			  WebElement err_msg1=object.findElement(By.xpath("//*[text()='Error converting data type nvarchar to int.']"));
  			  js.executeScript("arguments[0].style.border='5px red solid';",err_msg1);
  			  Thread.sleep(3000);
  			  File src1=((TakesScreenshot)object).getScreenshotAs(OutputType.FILE);
  			  File dest1=new File("E:\\SE_AutomationScripts\\scripts\\ssname1");
  			  FileHandler.copy(src1, dest1);
  			  s.write("Error Message Will Be Displayed");
  			  et.log(LogStatus.ERROR,"Error Message Will Be Displayed"+et.addScreenCapture(ssname1));*/
  			  Thread.sleep(2000);
  			  wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlGroupNoTele")));
  			  WebElement groupNo=object.findElement(By.name("ddlGroupNoTele"));
  			  groupNo.click();
  			  groupNo.sendKeys("Testing");
  			  Thread.sleep(2000);
  			  a.sendKeys(Keys.DOWN).build().perform();
  			  a.sendKeys(Keys.ENTER).build().perform();
  			  Thread.sleep(1000);
  			  wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtProviderName")));
  			  object.findElement(By.name("txtProviderName")).sendKeys("ITESTf");
  			  wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtProviderCodeSE")));
  			  object.findElement(By.name("txtProviderCodeSE")).sendKeys("1120");
  			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlProviderClassification")));
			WebElement prvdr_classification=object.findElement(By.name("ddlProviderClassification"));
			prvdr_classification.click();
			Thread.sleep(3000);
			prvdr_classification.sendKeys("v");
			Thread.sleep(2000);
			a.sendKeys(Keys.DOWN).build().perform();
			a.sendKeys(Keys.ENTER).build().perform();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlProviderType")));
			object.findElement(By.name("ddlProviderType")).click();
			WebElement providerType_dd=object.findElement(By.name("ddlProviderType"));
			Select s2=new Select(providerType_dd);
			s2.selectByVisibleText("DAYCARE CENTER");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtShortName")));
			object.findElement(By.name("txtShortName")).sendKeys("lkms");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtProviderAddress")));
			object.findElement(By.name("txtProviderAddress")).sendKeys("JEDDAH-202");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCityTele")));
			WebElement city1=object.findElement(By.name("ddlCityTele"));
			city1.click();
			Thread.sleep(2000);
			city1.sendKeys("je");
			a.sendKeys(Keys.DOWN).build().perform();
			a.sendKeys(Keys.ENTER).build().perform();
			Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtPhoneNo")));
			object.findElement(By.name("txtPhoneNo")).sendKeys("591432786");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtFaxNo")));
			object.findElement(By.name("txtFaxNo")).sendKeys("09327yeufv");	
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='Time'])[1]")));
			object.findElement(By.xpath("(//*[text()='Time'])[1]")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='Time Picker'])[1]/following::tr[4]/td[1]/a[text()='09:00:00']")));
			object.findElement(By.xpath("(//*[text()='Time Picker'])[1]/following::tr[4]/td[1]/a[text()='09:00:00']")).click();
			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='Time'])[2]")));
			object.findElement(By.xpath("(//*[text()='Time'])[2]")).click();
			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='Time Picker'])[1]/following::tr[8]/td[2]/a[text()='22:00:00']")));
			object.findElement(By.xpath("(//*[text()='Time Picker'])[1]/following::tr[8]/td[2]/a[text()='22:00:00']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtContactPerson")));
			object.findElement(By.name("txtContactPerson")).sendKeys("lokeshk");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDesignation")));
			object.findElement(By.name("txtDesignation")).sendKeys("Testqq");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDepartment")));
			object.findElement(By.name("txtDepartment")).sendKeys("Cardiology");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlComType")));
			object.findElement(By.name("ddlComType")).click();
			WebElement comm_dd=object.findElement(By.name("ddlComType"));
			Select s3=new Select(comm_dd);
			s3.selectByVisibleText("EMAIL-4");
			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCommInfo")));
			object.findElement(By.name("txtCommInfo")).sendKeys("kummari@gmail.com");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnProvContactA2Grid")));
			object.findElement(By.name("btnProvContactA2Grid")).click();
			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlRegulatoryTele")));
			WebElement reg_type_dd=object.findElement(By.name("ddlRegulatoryTele"));
			reg_type_dd.click();
			reg_type_dd.sendKeys("cc");
			Thread.sleep(2000);
			a.sendKeys(Keys.UP).build().perform();
			a.sendKeys(Keys.UP).build().perform();
			a.sendKeys(Keys.ENTER).build().perform();
			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtLicenseNo")));
			object.findElement(By.name("txtLicenseNo")).sendKeys("34863482q");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(@name,'txtValidFrom')])[2]")));
			object.findElement(By.xpath("(//*[contains(@name,'txtValidFrom')])[2]")).sendKeys("18/02/2019");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(@name,'txtValidTo')])[2]")));
			object.findElement(By.xpath("(//*[contains(@name,'txtValidTo')])[2]")).sendKeys("18/02/2020");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnLicAddGrid")));
			object.findElement(By.name("btnLicAddGrid")).click();
			Thread.sleep(3000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlModeofPayment")));
			object.findElement(By.name("ddlModeofPayment")).click();
			WebElement paymentMode_dd=object.findElement(By.name("ddlModeofPayment"));
			Select s4=new Select(paymentMode_dd);
			s4.selectByVisibleText("TRANSFER");
			Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtPaymentTerms")));
			object.findElement(By.name("txtPaymentTerms")).sendKeys("4");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtBName")));
			object.findElement(By.name("txtBName")).sendKeys("DCB");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtBBranch")));
			object.findElement(By.name("txtBBranch")).sendKeys("JEDDAH-202");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtBAccName")));
			object.findElement(By.name("txtBAccName")).sendKeys("IBname");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtBankActno")));
			object.findElement(By.name("txtBankActno")).sendKeys("03667380033");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtBankAddress")));
			object.findElement(By.name("txtBankAddress")).sendKeys("JEDDAH");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtIBANNo")));
			object.findElement(By.name("txtIBANNo")).sendKeys("38r89437023");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlcchiprovider")));
			object.findElement(By.name("ddlcchiprovider")).click();
			WebElement cchi_provider=object.findElement(By.name("ddlcchiprovider"));
			Select s5=new Select(cchi_provider);
			s5.selectByVisibleText("OHN");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(@name,'txtEFFDate')])[2]")));
			object.findElement(By.xpath("(//*[contains(@name,'txtEFFDate')])[2]")).sendKeys("19/02/2019");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnCCHIprovAddToGrid")));
			object.findElement(By.name("btnCCHIprovAddToGrid")).click();
			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnSave")));
			object.findElement(By.name("btnSave")).click();
			try
			{
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Object variable or With block variable not set']")));
				if(object.findElement(By.xpath("//*[text()='Object variable or With block variable not set']")).isDisplayed())
				{
					Date d2=new Date();
	    			SimpleDateFormat sdf1=new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
	    			String ssname2=sdf1.format(d2)+".png";
	    			JavascriptExecutor js=(JavascriptExecutor)object;
	    			WebElement err_msg2=object.findElement(By.xpath("//*[text()='Object variable or With block variable not set']"));
	    			js.executeScript("arguments[0].style.border='5px red solid';",err_msg2);
	    			Thread.sleep(3000);
	    			File src=((TakesScreenshot)object).getScreenshotAs(OutputType.FILE);
	    			File dest=new File("E:\\SE_AutomationScripts\\scripts\\ssname1");
	    			FileHandler.copy(src, dest);
	    			s.write("Error Message Will Be Displayed");
	    			et.log(LogStatus.ERROR,"Error Message Will Be Displayed"+et.addScreenCapture(ssname2));
	    			object.close();
				}
			}
			catch(Exception eg)
			{
				System.out.println(eg.getMessage());
			}
			

	    	}
	    	object.switchTo().window(a1.get(0));
	    	
	    	
	    }
	    else
	    {
	    	String x=ss.screenshot(object);
			s.write("Provider Group Test Failed");
			et.log(LogStatus.FAIL,"Provider Group Test Failed"+et.addScreenCapture(x));
			System.exit(0);
	    }
	 }
	 @Then("^validate Doctor List  page$")
	 public void doctor_list() throws Exception
	 {
		   //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Provider Network']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Doctor List']")));
		    object.findElement(By.xpath("//*[text()='Doctor List']")).click();
		    Thread.sleep(4000); 
		    if(object.findElement(By.name("btnquery")).isEnabled() && bp.clearButton.isEnabled() && object.findElement(By.name("btnAddNewDoctor")).isEnabled())
		    {
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnAddNewDoctor")));
		    	object.findElement(By.name("btnAddNewDoctor")).click();
		    	Thread.sleep(2000);
		    	ArrayList<String> winDows=new ArrayList<String>(object.getWindowHandles());
		    	object.switchTo().window(winDows.get(1));
		    	Thread.sleep(1000);
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDoctorNo")));
		    	String doctorNo=object.findElement(By.name("txtDoctorNo")).getAttribute("value");
		    	Thread.sleep(2000);
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDoctorName")));
		    	object.findElement(By.name("txtDoctorName")).sendKeys("ITEST");
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlNationality")));
		    	WebElement nationality_dd=object.findElement(By.name("ddlNationality"));
		    	nationality_dd.click();
		    	nationality_dd.sendKeys("sau");
		    	Thread.sleep(1000);
		    	a.sendKeys(Keys.ENTER).build().perform();
		    	Thread.sleep(3000);
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlSpeciality")));
		    	object.findElement(By.name("ddlSpeciality")).click();
		    	Thread.sleep(2000);
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='NEUROLOGIST-0012']")));
		    	object.findElement(By.xpath("//*[text()='NEUROLOGIST-0012']")).click();
		    	Thread.sleep(1000);
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlProviderGroupNo")));
		    	object.findElement(By.name("ddlProviderGroupNo")).click();
		    	Thread.sleep(2000);
		    	a.sendKeys(Keys.DOWN).build().perform();
		    	Thread.sleep(1000);
		    	a.sendKeys(Keys.ENTER).build().perform();
		    	Thread.sleep(2000);
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDepartment")));
		    	WebElement deptNo=object.findElement(By.name("ddlDepartment"));
		    	deptNo.click();
		    	Thread.sleep(5000);
		    	deptNo.sendKeys("cardio");
		    	Thread.sleep(3000);
		    	a.sendKeys(Keys.ENTER).build().perform();
		    	Thread.sleep(2000);
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlRegulatory")));
		    	WebElement regulatoryCode=object.findElement(By.name("ddlRegulatory"));
		    	regulatoryCode.click();
		    	Thread.sleep(3000);
		    	regulatoryCode.sendKeys("cc");
		    	Thread.sleep(2000);
		    	a.sendKeys(Keys.DOWN).build().perform();
		    	a.sendKeys(Keys.ENTER).build().perform();
		    	Thread.sleep(1000);
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtLicenseNo")));
		    	object.findElement(By.name("txtLicenseNo")).sendKeys("2672221rj");
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnaddtocontactgrid")));
		    	object.findElement(By.name("btnaddtocontactgrid")).click();
		    	Thread.sleep(2000);
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnSave")));
		    	object.findElement(By.name("btnSave")).click();
		    	Thread.sleep(2000);
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
		    	s.write("Record Saved Successfully");
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btndecisionstatus")));
		    	Thread.sleep(1000);
		    	if(object.findElement(By.name("btndecisionstatus")).isEnabled())
		    	{
		    		object.findElement(By.name("btndecisionstatus")).click();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlAppStatus")));
		    		object.findElement(By.name("ddlAppStatus")).click();
		    		WebElement approveStatus=object.findElement(By.name("ddlAppStatus"));
		    		Select s1=new Select(approveStatus);
		    		s1.selectByVisibleText("VERIFY");
		    		Thread.sleep(1000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Btnverify")));
		    		object.findElement(By.name("Btnverify")).click();
		    		Thread.sleep(1000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Verified Successfully')]")));
		    		s.write("Record Verified Successfully");
		    		Thread.sleep(1000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlAppStatus")));
		    		object.findElement(By.name("ddlAppStatus")).click();
		    		WebElement approveStatus1=object.findElement(By.name("ddlAppStatus"));
		    		Select s2=new Select(approveStatus1);
		    		s2.selectByVisibleText("APPROVED");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnApprove")));
		    		object.findElement(By.name("btnApprove")).click();
		    		Thread.sleep(1000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
		    		s.write("Record Approved and Saved Successfully");	
		    	}
		    	object.switchTo().window(winDows.get(0));
		    	wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    	bp.click_clearButton();
		    	Thread.sleep(2000);
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Doctor Code']/following::td[5][@class='linkcolor']")));
		    	String doctorCode=object.findElement(By.xpath("//*[text()='Doctor Code']/following::td[5][@class='linkcolor']")).getText();
		    	System.out.println(doctorCode);
		    	Thread.sleep(1000);
		    	if(doctorNo.equals(doctorCode))
		    	{
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='Decision Status'])[2]/following::td[10][@class='linkcolor']")));
		    		String decisionStatus=object.findElement(By.xpath("(//*[text()='Decision Status'])[2]/following::td[10][@class='linkcolor']")).getText();
		    		System.out.println(decisionStatus);
		    		Thread.sleep(1000);
		    		if(decisionStatus.equalsIgnoreCase("ACTIVE"))
		    		{
		    			s.write("Doctor List Test Passed");
		    		}
		    	}
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
				s.write("Doctor List Test Failed");
				et.log(LogStatus.FAIL,"Doctor List Test Failed"+et.addScreenCapture(x));
				System.exit(0);
		    }
	 }
	 @Then("^validate Provider Contract  page$")
	 public void provider_contract() throws Exception
	 {
		   //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Provider Network']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='DB Provider Contract List']")));
		    object.findElement(By.xpath("//*[text()='DB Provider Contract List']")).click();
		    Thread.sleep(4000);  
		    if(object.findElement(By.name("btnQuery")).isEnabled() && bp.clearButton.isEnabled())
		    {
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlProviderNoTele")));
		    	WebElement providerName=object.findElement(By.name("ddlProviderNoTele"));
		    	providerName.click();
		    	providerName.sendKeys("1315");
		    	Thread.sleep(2000);
		    	a.sendKeys(Keys.DOWN).build().perform();
		    	Thread.sleep(2000);
		    	a.sendKeys(Keys.ENTER).build().perform();
		    	Thread.sleep(1000);
		    	object.findElement(By.name("btnQuery")).click();
		    	Thread.sleep(2000);
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Contract No.']/following::td[7][@class='linkcolor']")));
		    	String contractList=object.findElement(By.xpath("//*[text()='Contract No.']/following::td[7][@class='linkcolor']")).getText();
		    	System.out.println(contractList);
		    	Thread.sleep(2000);
		    	 WebElement master1=object.findElement(By.xpath("//*[text()='Provider Network']"));
			    a.moveToElement(master1).build().perform();
			    Thread.sleep(2000);
			    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='DB Provider']")));
			    object.findElement(By.xpath("//*[text()='DB Provider']")).click();
			    Thread.sleep(2000);
			    wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlProviderNoTele")));
			    object.findElement(By.name("ddlProviderNoTele")).click();
			    object.findElement(By.name("ddlProviderNoTele")).sendKeys("1315");
			    Thread.sleep(2000);
			    a.sendKeys(Keys.DOWN).build().perform();
			    Thread.sleep(1000);
			    a.sendKeys(Keys.ENTER).build().perform();
			    Thread.sleep(1000);
			    wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnQuery")));
			    object.findElement(By.name("btnQuery")).click();
			    Thread.sleep(2000);
			    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='Provider Name'])[2]/following::td[6][@class='linkcolor']")));
			    object.findElement(By.xpath("(//*[text()='Provider Name'])[2]/following::td[6][@class='linkcolor']")).click();
			    ArrayList<String> winDows=new ArrayList<String>(object.getWindowHandles());
			    object.switchTo().window(winDows.get(1));
			    Thread.sleep(1000);
			    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='Contract No'])[2]/following::td[5]")));
			    String contractNo=object.findElement(By.xpath("(//*[text()='Contract No'])[2]/following::td[5]")).getText();
			    System.out.println(contractNo);
			    Thread.sleep(2000);
			    if(contractList.equals(contractNo))
			    {
			    	s.write("provider contract details test passed");
			    }
			    object.close();
			    Thread.sleep(1000);
			    object.switchTo().window(winDows.get(0));
			    
			    
		    	
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
				s.write("Doctor List Test Failed");
				et.log(LogStatus.FAIL,"Provider Contract Details Test Failed"+et.addScreenCapture(x));
				System.exit(0);
		    }
	 }
	 @Then("^validate Reimbursement Provider Details  page$")
	 public void reimbursement_provider() throws Exception
	 {
		    //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Provider Network']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Reim. Provider Master']")));
		    object.findElement(By.xpath("//*[text()='Reim. Provider Master']")).click();
		    Thread.sleep(4000); 
		    if(bp.addButton.isEnabled() && bp.editButton.isEnabled())
		    {
		    	wait.until(ExpectedConditions.visibilityOf(bp.addButton));
		    	bp.click_addButton();
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCode")));
		    	String ReimProvider=object.findElement(By.name("txtCode")).getAttribute("value");
		    	System.out.println(ReimProvider);
		    	Thread.sleep(2000);
		    	try
		    	{
		    		
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnSave")));
		    		object.findElement(By.name("btnSave")).click();
		    		bp.click_editButton();
		    	}
		    	catch(Exception ex)
		    	{
		    		Thread.sleep(2000);
		    		object.switchTo().alert().accept();
		    		object.switchTo().activeElement().sendKeys("341",Keys.TAB);
		    		Thread.sleep(1000);
		    		object.switchTo().activeElement().sendKeys("lokehs",Keys.TAB);
		    		Thread.sleep(1000);
		    		object.switchTo().activeElement().sendKeys("lkss",Keys.TAB);
		    		Thread.sleep(1000);
		    		object.switchTo().activeElement().sendKeys("JEDDAH-202",Keys.TAB);
		    		Thread.sleep(1000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCountry")));
		    		object.findElement(By.name("ddlCountry")).click();
		    		WebElement countryName=object.findElement(By.name("ddlCountry"));
		    		Select s1=new Select(countryName);
		    		s1.selectByValue("SAUDI ARABIA-966");
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCity")));
		    		object.findElement(By.name("ddlCity")).click();
		    		Thread.sleep(4000);
		    		WebElement cityName=object.findElement(By.name("ddlCity"));
		    		Select s2=new Select(cityName);
		    		s2.selectByVisibleText("RIYADH-101");
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnSave")));
		    		object.findElement(By.name("btnSave")).click();
		    		Thread.sleep(2000);
	    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Succesfully')]")).isDisplayed())
	    			{
	    				s.write("Record Saved Successfully");
	    			}
	    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
	    			bp.click_clearButton();
	    			Thread.sleep(1000);
	    			if(bp.editButton.isEnabled())
	    			{
	    				wait.until(ExpectedConditions.visibilityOf(bp.editButton));
	    				bp.click_editButton();
	    				try
	    				{
	    					Thread.sleep(2000);
	    					wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnFind")));
	    					object.findElement(By.name("btnFind")).click();
	    					bp.click_editButton();
	    				}
	    				catch(Exception et)
	    				{
	    					Thread.sleep(2000);
	    					object.switchTo().alert().accept();
	    					Thread.sleep(1000);
	    					object.switchTo().activeElement().sendKeys(ReimProvider);
	    					Thread.sleep(2000);
	    					wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnFind")));
	    					object.findElement(By.name("btnFind")).click();
	    					Thread.sleep(2000);
	    					wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnSave")));
	    		    		object.findElement(By.name("btnSave")).click();
	    		    		Thread.sleep(2000);
	    	    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Succesfully')]")).isDisplayed())
	    	    			{
	    	    				s.write("Record Saved Successfully");
	    	    			}
	    	    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
	    	    			bp.click_clearButton();
	    	    			Thread.sleep(1000);
	    	    			wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    				bp.click_editButton();
		    				Thread.sleep(1000);
	    					object.switchTo().activeElement().sendKeys(ReimProvider);
	    					wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnFind")));
	    					object.findElement(By.name("btnFind")).click();
	    					Thread.sleep(2000);
	    					try
	    					{
	    						wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnDelete")));
	    						object.findElement(By.name("btnDelete")).click();
	    						bp.click_addButton();
	    					}
	    					catch(Exception e1)
	    					{
	    						Thread.sleep(1000);
	    						object.switchTo().alert().accept();
	    						wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Record Deleted Successfully']")));
	    						if(object.findElement(By.xpath("//*[text()='Record Deleted Successfully']")).isDisplayed())
	    						{
	    							s.write("Record Delete Successfully");
	    						}
	    						wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
	    		    			bp.click_clearButton();
	    		    			Thread.sleep(1000);
	    		    			wait.until(ExpectedConditions.visibilityOf(bp.editButton));
			    				bp.click_editButton();
			    				Thread.sleep(1000);
		    					object.switchTo().activeElement().sendKeys(ReimProvider);
		    					wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnFind")));
		    					object.findElement(By.name("btnFind")).click();
		    					Thread.sleep(2000);
		    					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Provider Code Not Found']")));
		    					if(object.findElement(By.xpath("//*[text()='Provider Code Not Found']")).isDisplayed())
		    					{
		    						s.write("Reimbursement Provider Details Test Passed");
		    					}
	    					}//catch closing
	    				}//catch closing
	    			}//if closing
		    	}//catch closing
		    }//if closing
		    else
		    {
		    	String x=ss.screenshot(object);
				s.write("Reimbursement Provider Details Test Failed");
				et.log(LogStatus.FAIL,"Reimbursement Provider Details Test Failed"+et.addScreenCapture(x));
				System.exit(0);
		    }
	 }
	 @Then("^validate Reimbursement Price List  page$")
	 public void reimbursement_price_list() throws Exception
	 {
		   //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Provider Network']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Reim. Price List']")));
		    object.findElement(By.xpath("//*[text()='Reim. Price List']")).click();
		    Thread.sleep(4000); 
		    if(object.findElement(By.name("btnQuery")).isEnabled() && bp.clearButton.isEnabled() && object.findElement(By.name("btnAddNew")).isEnabled())
		    {
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnAddNew")));
		    	object.findElement(By.name("btnAddNew")).click();
		    	Thread.sleep(1000);
		    	ArrayList<String> winDows=new ArrayList<String>(object.getWindowHandles());
		    	object.switchTo().window(winDows.get(1));
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Reimbursement Price List']")));
		    	if(object.findElement(By.xpath("//*[text()='Reimbursement Price List']")).isDisplayed())
		    	{
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtServicepListNo")));
		    		String serviceNo=object.findElement(By.name("txtServicepListNo")).getAttribute("value");
		    		System.out.println(serviceNo);
		    		Thread.sleep(1000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(@name,'txtEFrom')])[2]")));
		    		object.findElement(By.xpath("(//*[contains(@name,'txtEFrom')])[2]")).sendKeys("12/03/2019");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtOPMDiscount")));
		    		object.findElement(By.name("txtOPMDiscount")).sendKeys("04.00");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtIPMDiscount")));
		    		object.findElement(By.name("txtIPMDiscount")).sendKeys("06.00");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlNetworkClassification")));
		    		WebElement networkClassification=object.findElement(By.name("ddlNetworkClassification"));
		    		networkClassification.click();
		    		Thread.sleep(1000);
		    		networkClassification.sendKeys("c");
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='C LTD-19']/following::input[1]")));
		    		object.findElement(By.xpath("//*[text()='C LTD-19']/following::input[1]")).click();
		    		Thread.sleep(2000);
		    		/*object.switchTo().window(winDows.get(0));
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Downloads']")));
		    		WebElement downloadList=object.findElement(By.xpath("//*[text()='Downloads']"));
		    		a.moveToElement(downloadList).build().perform();
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Data Import Templates']")));
		    		object.findElement(By.xpath("//*[text()='Data Import Templates']")).click();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(text(),'Service')])[5]/following::img[1]")));
		    		object.findElement(By.xpath("(//*[contains(text(),'Service')])[5]/following::img[1]")).click();*/
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnFileUpload")));
		    		object.findElement(By.name("btnFileUpload")).sendKeys("E:\\SE_AutomationScripts\\Service.xls");
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnUpload")));
		    		object.findElement(By.name("btnUpload")).click();
		    		Thread.sleep(1000);
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnVerify")));
			    		object.findElement(By.name("BtnVerify")).click();
			    		Thread.sleep(1000);
			    		if(object.findElement(By.xpath("//*[contains(text(),'Please check the Error')]")).isDisplayed())
			    		{
			    			Date d2=new Date();
			    			SimpleDateFormat sdf1=new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
			    			String downloadErr=sdf1.format(d2)+".png";
			    			JavascriptExecutor js=(JavascriptExecutor)object;
			    			WebElement err_msg2=object.findElement(By.xpath("//*[contains(text(),'Please check the Error')]"));
			    			js.executeScript("arguments[0].style.border='5px red solid';",err_msg2);
			    			Thread.sleep(3000);
			    			File src=((TakesScreenshot)object).getScreenshotAs(OutputType.FILE);
			    			File dest=new File("D:\\lokii\\scripts\\Saudi_Enaya_indexes\\ssname1");
			    			FileHandler.copy(src, dest);
			    			s.write("Error Message Will Be Displayed");
			    			if(object.findElement(By.xpath("//*[text()='Click here to Download']")).isDisplayed())
			    			{
			    				object.findElement(By.xpath("//*[text()='Click here to Download']")).click();
			    				Thread.sleep(3000);
			    				Screen sr=new Screen();
			    				Thread.sleep(1000);
			    				sr.click("DE:\\SE_AutomationScripts\\scripts\\rightClick.PNG");
			    				Robot r=new Robot();
			    				r.keyPress(KeyEvent.VK_ENTER);
			    				r.keyRelease(KeyEvent.VK_ENTER);
			    			}
			    		}
						
		    		}
		    		catch(Exception es)
		    		{
		    		System.out.println(es.getMessage());	
		    		}
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnSave")));
		    		object.findElement(By.name("btnSave")).click();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
		    		try
		    		{
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
			    		{
			    			s.write("Record Saved Successfully");
			    		}
			    		wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
			    		bp.click_clearButton();
			    		Thread.sleep(2000);
			    		object.switchTo().window(winDows.get(0));
			    		wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
			    		bp.click_clearButton();
			    		Thread.sleep(2000);
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Price List No']/following::td[5]")));
			    		String priceList=object.findElement(By.xpath("//*[text()='Price List No']/following::td[5]")).getText();
			    		System.out.println(priceList);
			    		Thread.sleep(1000);
			    		if(serviceNo.equals(priceList))
			    		{
			    			s.write("Reimbursement Price List Test Passed");
			    		}
		    		}
		    		catch(Exception ex)
		    		{
		    			System.out.println(ex.getMessage());
		    		}
		    	}
		    	
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
				s.write("Reimbursement Price List Test Failed");
				et.log(LogStatus.FAIL,"Reimbursement Price List Test Failed"+et.addScreenCapture(x));
				System.exit(0);
		    }
	 }
	 @And("^validate Quotation Loading Master page$")
	 public void quotation_loading_master() throws Exception
	 {
		    //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Quotation Module']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Quotation Loading Master']")));
		    object.findElement(By.xpath("//*[text()='Quotation Loading Master']")).click();
		    Thread.sleep(4000); 
		    if(bp.addButton.isEnabled() && bp.editButton.isEnabled())
		    {
		    	wait.until(ExpectedConditions.visibilityOf(bp.addButton));
		    	bp.click_addButton();
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtcode")));
		    	String Ql_code=object.findElement(By.name("txtcode")).getAttribute("value");
		    	System.out.println(Ql_code);
		    	try
		    	{
		    		Thread.sleep(1000);
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		bp.click_editButton();
		    	}
		    	catch(Exception et)
		    	{
		    		Thread.sleep(1000);
		    		object.switchTo().alert().accept();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtdescription")));
		    		object.findElement(By.name("txtdescription")).sendKeys("TestLoadings");
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlLoadings")));
		    		object.findElement(By.name("ddlLoadings")).click();
		    		WebElement loadings_DD=object.findElement(By.name("ddlLoadings"));
		    		Select s1=new Select(loadings_DD);
		    		s1.selectByVisibleText("SAMA FEE~47");
		    		Thread.sleep(1000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCalType")));
		    		object.findElement(By.name("ddlCalType")).click();
		    		WebElement calculation_DD=object.findElement(By.name("ddlCalType"));
		    		Select s2=new Select(calculation_DD);
		    		s2.selectByVisibleText("PERCENTAGE");
		    		Thread.sleep(1000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCalValue")));
		    		object.findElement(By.name("txtCalValue")).sendKeys("4.00");
		    		Thread.sleep(1000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnAdd2Grid")));
		    		object.findElement(By.name("btnAdd2Grid")).click();
		    		Thread.sleep(1000);
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		try
		    		{
		    			Thread.sleep(1000);
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    			{
		    				s.write("Record Saved Successfully");
		    			}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    		catch(Exception er)
		    		{
		    			System.out.println(er.getMessage());
		    		}
		    		Thread.sleep(1000);
		    		if(bp.editButton.isEnabled())
		    		{
		    			wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    			bp.click_editButton();
		    			try
		    			{
		    				Thread.sleep(1000);
		    				wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    				bp.click_saveButton();
		    				bp.click_editButton();
		    			}
		    			catch(Exception et1)
		    			{
		    				Thread.sleep(1000);
		    				object.switchTo().alert().accept();
		    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlcode")));
		    				object.findElement(By.name("ddlcode")).click();
		    				Thread.sleep(1000);
		    				List<WebElement> code_DD=object.findElements(By.xpath("//*[@name='ddlcode']/option"));
		    				System.out.println(code_DD.size());
		    				Thread.sleep(1000);
		    				for(int i=2;i<=code_DD.size();i++)
		    				{
		    					a.sendKeys(Keys.DOWN).build().perform();
		    					Thread.sleep(2000);
		    					String acCode=object.findElement(By.xpath("//*[@name='ddlcode']/option["+i+"]")).getText();
		    					Thread.sleep(1000);
		    					if(acCode.contains(Ql_code))
		    					{
		    						a.sendKeys(Keys.ENTER).build().perform();
		    						break;
		    					}
		    				}//loop closing
		    				Thread.sleep(2000);
		    				wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    				bp.click_saveButton();
		    				Thread.sleep(1000);
			    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
			    			{
			    				s.write("Record Saved Successfully");
			    			}
			    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
			    			bp.click_clearButton();
			    			Thread.sleep(1000);
		    			}
		    		}
		    	}
		    }
		    else
		    {
		    	String x=ss.screenshot(object);
				s.write("Quotation Loading Master Test Failed");
				et.log(LogStatus.FAIL,"Quotation Loading Master Test Failed"+et.addScreenCapture(x));
				System.exit(0);
		    }
	 }
	 @And("^validate Co-Insurance Master page$")
	 public void coInsurance_master() throws Exception
	 {
		    //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Quotation Module']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='CoInsurance Master']")));
		    object.findElement(By.xpath("//*[text()='CoInsurance Master']")).click();
		    Thread.sleep(4000); 
		    if(bp.addButton.isEnabled() && bp.editButton.isEnabled())
		    {
		    	wait.until(ExpectedConditions.visibilityOf(bp.addButton));
		    	bp.click_addButton();
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCode")));
		    	String coInsuranceCode=object.findElement(By.name("txtCode")).getAttribute("value");
		    	System.out.println(coInsuranceCode);
		    	try
		    	{
		    		Thread.sleep(1000);
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		bp.click_editButton();
		    	}
		    	catch(Exception ex)
		    	{
		    		Thread.sleep(1000);
		    		object.switchTo().alert().accept();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDescription")));
		    		object.findElement(By.name("txtDescription")).sendKeys("25%");
		    		try
		    		{
		    			Thread.sleep(1000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
			    		bp.click_saveButton();
			    		bp.click_editButton();
		    		}
		    		catch(Exception em)
		    		{
		    			Thread.sleep(1000);
		    			object.switchTo().alert().accept();
		    			Thread.sleep(1000);
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtValue")));
			    		object.findElement(By.name("txtValue")).sendKeys("25.00");	
		    		}
		    		Thread.sleep(1000);
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		Thread.sleep(1000);
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    			{
		    				s.write("Record Saved Successfully");
		    			}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    		catch(Exception eu)
		    		{
		    			System.out.println(eu.getMessage());
		    		}
		    	}
		    	Thread.sleep(1000);
		    	if(bp.editButton.isEnabled())
		    	{
		    		wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    		bp.click_editButton();
		    		Thread.sleep(1000);
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    			bp.click_saveButton();
		    			bp.click_editButton();
		    		}
		    		catch(Exception ew)
		    		{
		    			Thread.sleep(1000);
		    			object.switchTo().alert().accept();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCode")));
		    			object.findElement(By.name("ddlCode")).click();
		    			Thread.sleep(1000);
		    			List<WebElement> ac_Code=object.findElements(By.xpath("//*[@name='ddlCode']/option"));
		    			System.out.println(ac_Code.size());
		    			Thread.sleep(1000);
		    			for(int i=2;i<=ac_Code.size();i++)
		    			{
		    				Thread.sleep(1000);
		    				a.sendKeys(Keys.DOWN).build().perform();
		    				String ac_Code1=object.findElement(By.xpath("//*[@name='ddlCode']/option["+i+"]")).getText();
		    				Thread.sleep(1000);
		    				if(ac_Code1.contains("-"+coInsuranceCode))
		    				{
		    					Thread.sleep(1000);
		    					a.sendKeys(Keys.ENTER).build().perform();
		    					break;
		    				}
		    			}//loop closing
		    			Thread.sleep(1000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    			bp.click_saveButton();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    			{
		    				s.write("Record Edit and Saved Successfully");
		    			}
		    			Thread.sleep(1000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    			Thread.sleep(1000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.editButton));
			    		bp.click_editButton();
			    		object.findElement(By.name("ddlCode")).click();
		    			Thread.sleep(1000);
		    			List<WebElement> ac_Codes=object.findElements(By.xpath("//*[@name='ddlCode']/option"));
		    			System.out.println(ac_Codes.size());
		    			Thread.sleep(1000);
		    			for(int i=2;i<=ac_Codes.size();i++)
		    			{
		    				Thread.sleep(1000);
		    				a.sendKeys(Keys.DOWN).build().perform();
		    				String ac_Code1=object.findElement(By.xpath("//*[@name='ddlCode']/option["+i+"]")).getText();
		    				Thread.sleep(1000);
		    				if(ac_Code1.contains("-"+coInsuranceCode))
		    				{
		    					Thread.sleep(1000);
		    					a.sendKeys(Keys.ENTER).build().perform();
		    					break;
		    				}
		    			}//loop closing
		    			wait.until(ExpectedConditions.visibilityOf(bp.deleteButton));
		    			bp.click_deleteButton();
		    			Thread.sleep(1000);
		    			object.switchTo().alert().accept();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Deleted Successfully')]")));
		    			if(object.findElement(By.xpath("//*[contains(text(),'Deleted Successfully')]")).isDisplayed())
		    			{
		    				s.write("Record Deleted Successfully");
		    			}
		    			Thread.sleep(1000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    			try
		    			{
		    				Thread.sleep(1000);
		    				wait.until(ExpectedConditions.visibilityOf(bp.editButton));
				    		bp.click_editButton();
				    		object.findElement(By.name("ddlCode")).click();
			    			Thread.sleep(1000);
			    			List<WebElement> ac_Codes1=object.findElements(By.xpath("//*[@name='ddlCode']/option"));
			    			System.out.println(ac_Codes1.size());
			    			Thread.sleep(1000);
			    			for(int i=2;i<=ac_Codes1.size();i++)
			    			{
			    				Thread.sleep(1000);
			    				a.sendKeys(Keys.DOWN).build().perform();
			    				String ac_Code1=object.findElement(By.xpath("//*[@name='ddlCode']/option["+i+"]")).getText();
			    				Thread.sleep(1000);
			    				if(ac_Code1.contains("-"+coInsuranceCode))
			    				{
			    					Thread.sleep(1000);
			    					a.sendKeys(Keys.ENTER).build().perform();
			    					break;
			    				}
			    			}//loop closing
			    			Thread.sleep(1000);
			    			s.write("Co-Insurance Master Test Passed");
		    			}
		    			catch(Exception ef)
		    			{
		    				s.write("Co-Insurance Master Test Passed");
		    			}
		    			
		    		}
		    	}
		    	
		    }
		    else
		    {
		    	Date d=new Date();
		    	SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
		    	String coName=sdf.format(d)+".png";
		    	File src=((TakesScreenshot)object).getScreenshotAs(OutputType.FILE);
		    	File dest=new File("E:\\SE_AutomationScripts\\scripts\\coName");
		    	s.write("Co-Insurance Master Test failed");
		    	et.log(LogStatus.FAIL,"CO-Insurance Master Test Failed"+et.addScreenCapture(coName));
		    	System.exit(0);
		    }
	 }
	 @And("^validate Network Master page$")
	 public void network_master() throws Exception
	 {
		    //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Quotation Module']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Network Master New']")));
		    object.findElement(By.xpath("//*[text()='Network Master New']")).click();
		    Thread.sleep(4000); 
		    if(bp.addButton.isEnabled() && bp.editButton.isEnabled())
		    {
		    	wait.until(ExpectedConditions.visibilityOf(bp.addButton));
		    	bp.click_addButton();
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtNetworkCode")));
		    	String networkCode=object.findElement(By.name("txtNetworkCode")).getAttribute("value");
		    	System.out.println(networkCode);
		    	Thread.sleep(1000);
		    	try
		    	{
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		bp.click_editButton();
		    	}
		    	catch(Exception et)
		    	{
		    		Thread.sleep(1000);
		    		object.switchTo().alert().accept();
		    		object.switchTo().activeElement().sendKeys("Lgold");
		    		Thread.sleep(1000);
		    		try
		    		{
		    			Thread.sleep(1000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
			    		bp.click_saveButton();
			    		bp.click_editButton();
		    			
		    		}
		    		catch(Exception ea)
		    		{
		    			Thread.sleep(1000);
		    			object.switchTo().alert().accept();
		    			Thread.sleep(1000);
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlHospitalClass")));
			    		object.findElement(By.name("ddlHospitalClass")).click();
			    		WebElement hospitalClass=object.findElement(By.name("ddlHospitalClass"));
			    		Select s1=new Select(hospitalClass);
			    		s1.selectByVisibleText("PRIVATE ROOM-2");
			    		Thread.sleep(1000);
			    		try
			    		{
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
				    		bp.click_saveButton();
				    		bp.click_editButton();
			    		}
			    		catch(Exception eq)
			    		{
			    			Thread.sleep(1000);
			    			object.switchTo().alert().accept();
			    			Thread.sleep(2000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlProviderClass")));
				    		object.findElement(By.name("ddlProviderClass")).click();
				    		Thread.sleep(2000);
				    		WebElement providerClass=object.findElement(By.name("ddlProviderClass"));
				    		Select s2=new Select(providerClass);
				    		s2.selectByValue("A-14");
				    		Thread.sleep(3000);	
			    		}
		    		}
		    		
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
		    		bp.click_saveButton();
		    		Thread.sleep(1000);
		    		try
		    		{
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    			{
		    				s.write("Record Saved Successfully");
		    			}
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    		catch(Exception er)
		    		{
		    			System.out.println(er.getMessage());
		    			Thread.sleep(1000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    		}
		    		Thread.sleep(1000);
			    	if(bp.editButton.isEnabled())
			    	{
			    		wait.until(ExpectedConditions.visibilityOf(bp.editButton));
			    		bp.click_editButton();
			    		try
			    		{
			    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
			    			bp.click_saveButton();
			    			bp.click_editButton();
			    		}
			    		catch(Exception ey)
			    		{
			    			Thread.sleep(1000);
			    			object.switchTo().alert().accept();
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlNetworkCode")));
			    			object.findElement(By.name("ddlNetworkCode")).click();
			    			Thread.sleep(1000);
			    			List<WebElement> networkCodesList=object.findElements(By.xpath("//*[@name='ddlNetworkCode']/option"));
			    			System.out.println(networkCodesList.size());
			    			Thread.sleep(1000);
			    			for(int i=2;i<=networkCodesList.size();i++)
			    			{
			    				Thread.sleep(1000);
			    				a.sendKeys(Keys.DOWN).build().perform();
			    				String ac_networkCode=object.findElement(By.xpath("//*[@name='ddlNetworkCode']/option["+i+"]")).getText();
			    				Thread.sleep(1000);
			    				if(ac_networkCode.contains("-"+networkCode))
			    				{
			    					a.sendKeys(Keys.ENTER).build().perform();
			    					break;
			    				}
			    			}//loop closing
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
			    			bp.click_saveButton();
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
			    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
			    			{
			    				s.write("Record Edit and Saved Successfully");
			    			}
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
			    			bp.click_clearButton();
			    		}
			    		Thread.sleep(1000);
			    		wait.until(ExpectedConditions.visibilityOf(bp.editButton));
			    		bp.click_editButton();
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlNetworkCode")));
		    			object.findElement(By.name("ddlNetworkCode")).click();
		    			Thread.sleep(1000);
		    			List<WebElement> networkCodesList=object.findElements(By.xpath("//*[@name='ddlNetworkCode']/option"));
		    			System.out.println(networkCodesList.size());
		    			Thread.sleep(1000);
		    			for(int i=2;i<=networkCodesList.size();i++)
		    			{
		    				Thread.sleep(1000);
		    				a.sendKeys(Keys.DOWN).build().perform();
		    				String ac_networkCode=object.findElement(By.xpath("//*[@name='ddlNetworkCode']/option["+i+"]")).getText();
		    				Thread.sleep(1000);
		    				if(ac_networkCode.contains("-"+networkCode))
		    				{
		    					a.sendKeys(Keys.ENTER).build().perform();
		    					break;
		    				}
		    			}//loop closing
		    			Thread.sleep(1000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.deleteButton));
		    			bp.click_deleteButton();
		    			Thread.sleep(1000);
		    			object.switchTo().alert().accept();
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Deleted Successfully')]")));
		    			if(object.findElement(By.xpath("//*[contains(text(),'Deleted Successfully')]")).isDisplayed())
		    			{
		    				s.write("Record Delete Successfully");
		    			}
		    			Thread.sleep(1000);
		    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    			bp.click_clearButton();
		    			bp.click_editButton();
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlNetworkCode")));
		    			object.findElement(By.name("ddlNetworkCode")).click();
		    			Thread.sleep(1000);
		    			List<WebElement> networkCodesList1=object.findElements(By.xpath("//*[@name='ddlNetworkCode']/option"));
		    			System.out.println(networkCodesList1.size());
		    			Thread.sleep(1000);
		    			for(int i=2;i<=networkCodesList1.size();i++)
		    			{
		    				Thread.sleep(1000);
		    				a.sendKeys(Keys.DOWN).build().perform();
		    				String ac_networkCode=object.findElement(By.xpath("//*[@name='ddlNetworkCode']/option["+i+"]")).getText();
		    				Thread.sleep(1000);
		    				if(ac_networkCode.contains("-"+networkCode))
		    				{
		    					a.sendKeys(Keys.ENTER).build().perform();
		    					break;
		    				}
		    			}//loop closing
		    			Thread.sleep(1000);
		    			s.write("Network Master Test Passed");
			    	}
		    	}
		    	
		    }
		    else
		    {
		    	Date d=new Date();
		    	SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
		    	String coName=sdf.format(d)+".png";
		    	File src=((TakesScreenshot)object).getScreenshotAs(OutputType.FILE);
		    	File dest=new File("E:\\SE_AutomationScripts\\scripts\\NmName");
		    	s.write("Network Master Test failed");
		    	et.log(LogStatus.FAIL,"Network Master Test Failed"+et.addScreenCapture(coName));
		    	System.exit(0);
		    }
	 }
	 @And("^validate Presenter Group Details page$")
	 public void presenter_group() throws Exception
	 {
		   //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Quotation Module']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Presenter Group']")));
		    object.findElement(By.xpath("//*[text()='Presenter Group']")).click();
		    Thread.sleep(5000);
		    if(object.findElement(By.name("btnAdd")).isEnabled() && object.findElement(By.name("btnEdit")).isEnabled() && object.findElement(By.name("btnClear")).isEnabled() && object.findElement(By.name("btnaddtocontactgrid")).isEnabled())
		    {
		    	Thread.sleep(2000);
		    	wait.until(ExpectedConditions.visibilityOf(bp.addButton));
		    	bp.click_addButton();
		    	String groupNumber=object.findElement(By.name("txtgroupno")).getAttribute("value");
		    	System.out.println(groupNumber);
		    	Thread.sleep(3000);
		    	if(object.findElement(By.name("btnSave")).isEnabled() && object.findElement(By.name("btnClear")).isEnabled())
		    	{
		    		Thread.sleep(2000);
			    	try
			    	{
			    		Thread.sleep(1000);
			    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton1));
			    		bp.click_saveButton1();
			    		bp.click_editButton();
			    	}
			    	catch(Exception ex)
			    	{
			    		Thread.sleep(1000);
			    		object.switchTo().alert().accept();
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtGrpName")));
			    		object.findElement(By.name("txtGrpName")).sendKeys("ITEST0427");
			    		try
			    		{
			    			Thread.sleep(1000);
				    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton1));
				    		bp.click_saveButton1();
				    		bp.click_editButton();
			    		}
			    		catch(Exception er1)
			    		{
			    			Thread.sleep(1000);
			    			object.switchTo().alert().accept();
			    			Thread.sleep(2000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCityTele")));
				    		WebElement cityTele=object.findElement(By.name("ddlCityTele"));
				    		cityTele.click();
				    		Thread.sleep(3000);
				    		cityTele.sendKeys("je");
				    		Thread.sleep(2000);
				    		a.sendKeys(Keys.ENTER).build().perform();
				    		Thread.sleep(2000);
				    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtWebSite")));
				    		object.findElement(By.name("txtWebSite")).sendKeys("www.lkss.com");
				    		Thread.sleep(2000);
				    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtPhoneNumber")));
				    		object.findElement(By.name("txtPhoneNumber")).sendKeys("582461397");
				    		Thread.sleep(1000);
				    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtFaxNumber")));
				    		object.findElement(By.name("txtFaxNumber")).sendKeys("378ryuer37");
				    		Thread.sleep(1000);
				    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtcAddress1")));
				    		object.findElement(By.name("txtcAddress1")).sendKeys("JEDDAH");
				    		Thread.sleep(1000);
				    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtcAddress2")));
				    		object.findElement(By.name("txtcAddress2")).sendKeys("Saudi Arabia");
				    		Thread.sleep(1000);
				    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCPerson")));
				    		object.findElement(By.name("txtCPerson")).sendKeys("lokeshk");
				    		Thread.sleep(1000);
				    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDesignation")));
				    		object.findElement(By.name("txtDesignation")).sendKeys("testing");
				    		Thread.sleep(1000);
				    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCommunicationType")));
				    		object.findElement(By.name("ddlCommunicationType")).click();
				    		WebElement commType=object.findElement(By.name("ddlCommunicationType"));
				    		Select s2=new Select(commType);
				    		s2.selectByVisibleText("MOBILE-3");
				    		Thread.sleep(1000);
				    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCommunication")));
				    		object.findElement(By.name("txtCommunication")).sendKeys("582461397");
				    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnaddtocontactgrid")));
				    		object.findElement(By.name("btnaddtocontactgrid")).click();
			    		}
			    		
			    		Thread.sleep(5000);
			    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton1));
			    		bp.click_saveButton1();
			    		Thread.sleep(1000);
			    		try
			    		{
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
			    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
			    			{
			    				s.write("Record Saved Successfully");
			    			}
			    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
			    			bp.click_clearButton();
			    		}
			    		catch(Exception eo)
			    		{
			    			System.out.println(eo.getMessage());
			    		}
			    	}
		    	}
		    	Thread.sleep(2000);
    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
    			bp.click_clearButton();
		    	Thread.sleep(3000);
		    	if(bp.editButton.isEnabled())
		    	{
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOf(bp.editButton));
		    		bp.click_editButton();
		    		Thread.sleep(2000);
		    		if(object.findElement(By.name("btnSave")).isEnabled() && object.findElement(By.name("btnClear")).isEnabled())
		    		{
		    			Thread.sleep(1000);
			    		try
			    		{
			    			Thread.sleep(2000);
			    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton1));
			    			bp.click_saveButton1();
			    			bp.click_editButton();
			    		}
			    		catch(Exception ed)
			    		{
			    			Thread.sleep(1000);
			    			object.switchTo().alert().accept();
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlClientGroupName")));
			    			object.findElement(By.name("ddlClientGroupName")).click();
			    			Thread.sleep(1000);
			    			List<WebElement> cGroupName=object.findElements(By.xpath("//*[@name='ddlClientGroupName']/option"));
			    			System.out.println(cGroupName.size());
			    			Thread.sleep(1000);
			    			for(int i=2;i<=cGroupName.size();i++)
			    			{
			    				Thread.sleep(1000);
			    				a.sendKeys(Keys.DOWN).build().perform();
			    				String ac_cGroupName=object.findElement(By.xpath("//*[@name='ddlClientGroupName']/option["+i+"]")).getText();
			    				Thread.sleep(1000);
			    				if(ac_cGroupName.contains("-"+groupNumber))
			    				{
			    					a.sendKeys(Keys.ENTER).build().perform();
			    					break;
			    				}
			    			}//loop closing
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton1));
			    			bp.click_saveButton1();
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
			    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
			    			{
			    				s.write("Record Edit and Saved Successfully");
			    			}
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
			    			bp.click_clearButton();
			    			
			    		}
		    		}
		    		
		    	}
		    }
		    else
		    {
		    	Date d=new Date();
		    	SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
		    	String coName=sdf.format(d)+".png";
		    	File src=((TakesScreenshot)object).getScreenshotAs(OutputType.FILE);
		    	File dest=new File("E:\\SE_AutomationScripts\\scripts\\NmName");
		    	s.write("Presenter Group Test failed");
		    	et.log(LogStatus.FAIL,"Presenter Group Test Failed"+et.addScreenCapture(coName));
		    	System.exit(0);
		    }
	 }
	 @And("^validate Presenter Details page$")
	 public void presenter_details() throws Exception
	 {
		    //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Quotation Module']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Presenter Details']")));
		    object.findElement(By.xpath("//*[text()='Presenter Details']")).click();
		    Thread.sleep(4000);
		    if(bp.addButton.isEnabled() && bp.editButton.isEnabled()  && object.findElement(By.name("btnClear")).isEnabled() && object.findElement(By.name("btnContGrid")).isEnabled())
		    {
		    	wait.until(ExpectedConditions.visibilityOf(bp.addButton));
		    	bp.click_addButton();
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtClientNo")));
		    	String clientNumber=object.findElement(By.name("txtClientNo")).getAttribute("value");
		    	System.out.println(clientNumber);
		    	Thread.sleep(1000);
		    	try
		    	{
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton1));
		    		bp.click_saveButton1();
		    		bp.click_editButton();
		    	}
		    	catch(Exception ex)
		    	{
		    		Thread.sleep(1000);
		    		object.switchTo().alert().accept();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtClientName")));
		    		object.findElement(By.name("txtClientName")).sendKeys("ATest123");
		    		Thread.sleep(1000);
		    		try
		    		{
		    			Thread.sleep(2000);
			    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton1));
			    		bp.click_saveButton1();
			    		bp.click_editButton();
		    		}
		    		catch(Exception eq)
		    		{
		    			Thread.sleep(1000);
		    			object.switchTo().alert().accept();
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlClientGroup")));
			    		WebElement groupNumber=object.findElement(By.name("ddlClientGroup"));
			    		groupNumber.click();
			    		Thread.sleep(1000);
			    		groupNumber.sendKeys("itest");
			    		Thread.sleep(2000);
			    		a.sendKeys(Keys.DOWN).build().perform();
			    		Thread.sleep(1000);
			    		a.sendKeys(Keys.ENTER).build().perform();
			    		Thread.sleep(2000);
			    		try
			    		{
			    			Thread.sleep(2000);
				    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton1));
				    		bp.click_saveButton1();
				    		bp.click_editButton();
			    		}
			    		catch(Exception er1)
			    		{
			    			Thread.sleep(1000);
			    			object.switchTo().alert().accept();
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlIndustryTele")));
				    		object.findElement(By.name("ddlIndustryTele")).click();
				    		Thread.sleep(2000);
				    		a.sendKeys(Keys.DOWN).build().perform();
				    		Thread.sleep(1000);
				    		a.sendKeys(Keys.ENTER).build().perform();
				    		Thread.sleep(2000);
				    		try
				    		{
				    			Thread.sleep(2000);
					    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton1));
					    		bp.click_saveButton1();
					    		bp.click_editButton();
				    		}
				    		catch(Exception ew1)
				    		{
				    			Thread.sleep(1000);
				    			object.switchTo().alert().accept();
				    			Thread.sleep(2000);
				    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtShortname")));
					    		object.findElement(By.name("txtShortname")).sendKeys("IT427");
					    		Thread.sleep(2000);
					    		try
					    		{
					    			Thread.sleep(2000);
						    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton1));
						    		bp.click_saveButton1();
						    		bp.click_editButton();
					    		}
					    		catch(Exception eq2)
					    		{
					    			Thread.sleep(1000);
					    			object.switchTo().alert().accept();
					    			Thread.sleep(2000);
					    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtFaxNumber")));
						    		object.findElement(By.name("txtFaxNumber")).sendKeys("8521937460");
						    		Thread.sleep(3000);
						    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCAddress1")));
						    		object.findElement(By.name("txtCAddress1")).sendKeys("JEDDAH");
						    		Thread.sleep(2000);
						    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCAddress2")));
						    		object.findElement(By.name("txtCAddress2")).sendKeys("JEDDAH-202");
						    		Thread.sleep(1000);
						    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCAddress3")));
						    		object.findElement(By.name("txtCAddress3")).sendKeys("Saudi Arabia");
						    		Thread.sleep(1000);
						    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtEmail")));
						    		object.findElement(By.name("txtEmail")).sendKeys("it427@gmail.com");
						    		Thread.sleep(2000);
						    		try
						    		{
						    			Thread.sleep(2000);
							    		wait.until(ExpectedConditions.visibilityOf(bp.saveButton1));
							    		bp.click_saveButton1();
							    		bp.click_editButton();
						    		}
						    		catch(Exception eq3)
						    		{
						    			Thread.sleep(1000);
						    			object.switchTo().alert().accept();
						    			Thread.sleep(2000);
						    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCityTele")));
							    		object.findElement(By.name("ddlCityTele")).click();
							    		object.findElement(By.name("ddlCityTele")).sendKeys("je");
							    		Thread.sleep(2000);
							    		a.sendKeys(Keys.DOWN).build().perform();
							    		Thread.sleep(1000);
							    		a.sendKeys(Keys.ENTER).build().perform();
							    		Thread.sleep(2000);
							    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtWebSite")));
							    		object.findElement(By.name("txtWebSite")).sendKeys("www.Testl.com");
							    		Thread.sleep(3000);
							    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtPhoneNumber")));
							    		object.findElement(By.name("txtPhoneNumber")).sendKeys("597213684");
							    		Thread.sleep(1000);
							    		try
							    		{
							    			Thread.sleep(2000);
								    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnContGrid")));
								    		object.findElement(By.name("btnContGrid")).click();
								    		object.findElement(By.name("txtCPerson")).sendKeys("lokeshk");
							    		}
							    		catch(Exception eq4)
							    		{
							    			Thread.sleep(1000);
							    			object.switchTo().alert().accept();
							    			Thread.sleep(2000);
							    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCPerson")));
								    		object.findElement(By.name("txtCPerson")).sendKeys("lokeshk");
								    		Thread.sleep(1000);
								    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDesignation")));
								    		object.findElement(By.name("txtDesignation")).sendKeys("TESTING");
								    		Thread.sleep(1000);
								    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDepartment")));
								    		object.findElement(By.name("txtDepartment")).sendKeys("DENTAL");
								    		Thread.sleep(1000);
								    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCommType")));
								    		Thread.sleep(1000);
								    		object.findElement(By.name("ddlCommType")).click();
								    		Thread.sleep(1000);
								    		WebElement commType=object.findElement(By.name("ddlCommType"));
								    		Select s1=new Select(commType);
								    		s1.selectByVisibleText("CLAIMS");
								    		Thread.sleep(1000);
								    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCommunicationType")));
								    		object.findElement(By.name("ddlCommunicationType")).click();
								    		WebElement commMode=object.findElement(By.name("ddlCommunicationType"));
								    		Select s2=new Select(commMode);
								    		s2.selectByVisibleText("MOBILE-3");
								    		Thread.sleep(1000);
								    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCommunication")));
								    		object.findElement(By.name("txtCommunication")).sendKeys("597213684");
								    		Thread.sleep(2000);
								    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnContGrid")));
								    		object.findElement(By.name("btnContGrid")).click();
								    		Thread.sleep(3000);
							    		}
							    		
						    		}
						    		
					    		}
					    		
				    		}
				    		
			    		}
			    		
		    		}
		    		
		    		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnSave")));
		    		//object.findElement(By.name("btnSave")).click();
		    		try
		    		{
		    			Robot r=new Robot();
			    		r.keyPress(KeyEvent.VK_ALT);
			    		r.keyPress(KeyEvent.VK_S);
			    		r.keyRelease(KeyEvent.VK_S);
			    		r.keyRelease(KeyEvent.VK_ALT);
			    		object.findElement(By.name("btnClear")).click();
			    		
		    		}
		    		catch(Exception wx)
		    		{
		    			Thread.sleep(2000);
		    			object.switchTo().alert().accept();
		    			Thread.sleep(2000);
			    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtWebSite")));
			    		object.findElement(By.name("txtWebSite")).clear();
			    		Thread.sleep(3000);
			    		object.findElement(By.name("txtWebSite")).sendKeys("www.NTESTR.com");
			    		Thread.sleep(2000);
			    		Robot r=new Robot();
			    		r.keyPress(KeyEvent.VK_ALT);
			    		r.keyPress(KeyEvent.VK_S);
			    		r.keyRelease(KeyEvent.VK_S);
			    		r.keyRelease(KeyEvent.VK_ALT);
			    		Thread.sleep(3000);
		    		}
		    		
		    		try
		    		{
		    			Thread.sleep(2000);
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
		    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
		    			{
		    				s.write("Record Saved Successfully");
		    			}
		    			Thread.sleep(1000);
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnClear")));
		    			object.findElement(By.name("btnClear")).click();
		    			Thread.sleep(1000);
		    		}
		    		catch(Exception ey)
		    		{
		    			System.out.println(ey.getMessage());
		    		}
		    		Thread.sleep(2000);
			    	if(bp.editButton.isEnabled())
			    	{
			    		Thread.sleep(3000);
			    		wait.until(ExpectedConditions.visibilityOf(bp.editButton));
			    		bp.click_editButton();
			    		Thread.sleep(8000);
			    		try
			    		{
			    			Thread.sleep(2000);
			    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton1));
			    			bp.click_saveButton1();
			    			bp.click_editButton();
			    		}
			    		catch(Exception ef)
			    		{
			    			Thread.sleep(1000);
			    			object.switchTo().alert().accept();
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlClientName")));
			    			object.findElement(By.name("ddlClientName")).click();
			    			Thread.sleep(1000);
			    			List<WebElement> clientList=object.findElements(By.xpath("//*[@name='ddlClientName']/option"));
			    			System.out.println(clientList.size());
			    			Thread.sleep(1000);
			    			for(int i=2;i<=clientList.size();i++)
			    			{
			    				Thread.sleep(1000);
			    				a.sendKeys(Keys.DOWN).build().perform();
			    				String ac_clientList=object.findElement(By.xpath("//*[@name='ddlClientName']/option["+i+"]")).getText();
			    				Thread.sleep(1000);
			    				if(ac_clientList.contains("-"+clientNumber))
			    				{
			    					a.sendKeys(Keys.ENTER).build().perform();
			    					break;
			    				}
			    			}//loop closing
			    			Thread.sleep(2000);
			    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton1));
			    			bp.click_saveButton1();
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Saved Successfully')]")));
			    			if(object.findElement(By.xpath("//*[contains(text(),'Saved Successfully')]")).isDisplayed())
			    			{
			    				s.write("Record Edit and Saved SUccessfully");
			    			}
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOf(bp.saveButton1));
			    			bp.click_saveButton1();
			    			
			    		}
			    	}
		    	}
		    	
		    }
		    else
		    {
		    	Date d=new Date();
		    	SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
		    	String coName=sdf.format(d)+".png";
		    	File src=((TakesScreenshot)object).getScreenshotAs(OutputType.FILE);
		    	File dest=new File("E:\\SE_AutomationScripts\\scripts\\pdName");
		    	s.write("Presenter Details Test failed");
		    	et.log(LogStatus.FAIL,"Presenter Details Test Failed"+et.addScreenCapture(coName));
		    	System.exit(0);
		    }
	 }
	 @And("^click on New Lux Note$")
	 public void quotation_list() throws Exception
	 {
		   //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Quotation Module']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Quotation List']")));
		    object.findElement(By.xpath("//*[text()='Quotation List']")).click();
		    Thread.sleep(4000);
		    if(object.findElement(By.name("btnquery")).isEnabled() && bp.clearButton.isEnabled() && object.findElement(By.name("btnLUXQuote")).isEnabled())
		    {
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnLUXQuote")));
		    	object.findElement(By.name("btnLUXQuote")).click();
		    	Thread.sleep(1000);
		    	ArrayList<String> winDows=new ArrayList<String>(object.getWindowHandles());
		    	object.switchTo().window(winDows.get(1));
		    	Thread.sleep(2000);
		    	if(object.findElement(By.name("BtnSave")).isDisplayed() || object.findElement(By.name("btnAddClient")).isEnabled())
		    	{
		    		Thread.sleep(1000);
		    		try
		    		{
		    			Thread.sleep(1000);
		    			object.findElement(By.name("BtnSave")).click();
		    			object.findElement(By.name("btnAddClient")).click();
		    		}
		    		catch(Exception eb)
		    		{
		    			Thread.sleep(1000);
		    			object.switchTo().alert().accept();
		    			Thread.sleep(2000);
		    			try
			    		{
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlPresentedTo")));
			    			WebElement presenterTo=object.findElement(By.name("ddlPresentedTo"));
			    			presenterTo.click();
			    			Thread.sleep(3000);
			    			presenterTo.sendKeys("test1");
			    			Thread.sleep(2000);
			    			//a.sendKeys(Keys.DOWN).build().perform();
			    			//Thread.sleep(1000);
			    			a.sendKeys(Keys.ENTER).build().perform();
			    			Thread.sleep(5000);
			    			object.findElement(By.name("ddlPresentedTo")).sendKeys(Keys.ESCAPE);
			    			Thread.sleep(3000);
			    			
			    		}
			    		catch(Exception ex)
			    		{
			    			Thread.sleep(1000);
			    			object.switchTo().alert().accept();
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnAddClient")));
			    			object.findElement(By.name("btnAddClient")).click();
			    			Thread.sleep(2000);
			    			ArrayList<String> winDows1=new ArrayList<String>(object.getWindowHandles());
			    			Thread.sleep(1000);
			    			System.out.println(winDows1.size());
			    			Thread.sleep(2000);
			    			object.switchTo().window(winDows1.get(2));
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnGroupSave")));
			    			System.out.println("window is opened");
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtGrpName")));
			    			object.findElement(By.name("txtGrpName")).sendKeys("ITEST");
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlGroupCity")));
			    			WebElement groupCity=object.findElement(By.name("ddlGroupCity"));
			    			groupCity.click();
			    			groupCity.sendKeys("je");
			    			Thread.sleep(1000);
			    			a.sendKeys(Keys.DOWN).build().perform();
			    			Thread.sleep(1000);
			    			a.sendKeys(Keys.ENTER).build().perform();
			    			Thread.sleep(2000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtGroupWebsite")));
			    			object.findElement(By.name("txtGroupWebsite")).sendKeys("www.lkss.com");
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtGroupPhoneno")));
			    			object.findElement(By.name("txtGroupPhoneno")).sendKeys("591736842");
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtGroupFaxno")));
			    			object.findElement(By.name("txtGroupFaxno")).sendKeys("84riweuur4");
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtGroupAddress1")));
			    			object.findElement(By.name("txtGroupAddress1")).sendKeys("JEDDAH");
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtGroupAddress2")));
			    			object.findElement(By.name("txtGroupAddress2")).sendKeys("Saudi Arabia");
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtGroupCperson")));
			    			object.findElement(By.name("txtGroupCperson")).sendKeys("lokeshk");
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtGroupDesignation")));
			    			object.findElement(By.name("txtGroupDesignation")).sendKeys("TEST");
			    			Thread.sleep(2000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlGroupCommunicationtype")));
			    			object.findElement(By.name("ddlGroupCommunicationtype")).click();
			    			WebElement groupComm=object.findElement(By.name("ddlGroupCommunicationtype"));
			    			Select s1=new Select(groupComm);
			    			s1.selectByVisibleText("MOBILE-3");
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtGroupCommunication")));
			    			object.findElement(By.name("txtGroupCommunication")).sendKeys("591736842");
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnaddtocontactgrid")));
			    			object.findElement(By.name("btnaddtocontactgrid")).click();
			    			Thread.sleep(2000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnGroupSave")));
			    			object.findElement(By.name("btnGroupSave")).click();
			    			Thread.sleep(3000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlClientGroup")));
			    			WebElement clientGroup=object.findElement(By.name("ddlClientGroup"));
			    			clientGroup.click();
			    			Thread.sleep(1000);
			    			clientGroup.sendKeys("ITEST1");
			    			Thread.sleep(1000);
			    			a.sendKeys(Keys.ENTER).build().perform();
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtClientName")));
			    			object.findElement(By.name("txtClientName")).sendKeys("Test122");
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlIndustryTele")));
			    			WebElement industryTele=object.findElement(By.name("ddlIndustryTele"));
			    			Thread.sleep(1000);
			    			industryTele.click();
			    			Thread.sleep(1000);
			    			industryTele.sendKeys("ani");
			    			Thread.sleep(1000);
			    			a.sendKeys(Keys.ENTER).build().perform();
			    			Thread.sleep(2000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtShortname")));
			    			object.findElement(By.name("txtShortname")).sendKeys("T12");
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtFaxNumber")));
			    			object.findElement(By.name("txtFaxNumber")).sendKeys("65475484464");
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCAddress1")));
			    			object.findElement(By.name("txtCAddress1")).sendKeys("jEDDAH");
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCAddress2")));
			    			object.findElement(By.name("txtCAddress2")).sendKeys("Saudi Arabia");
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtEmail")));
			    			object.findElement(By.name("txtEmail")).sendKeys("test12@gmail.com");
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCityTele")));
			    			object.findElement(By.name("ddlCityTele")).click();
			    			Thread.sleep(1000);
			    			object.findElement(By.name("ddlCityTele")).sendKeys("je");
			    			Thread.sleep(1000);
			    			a.sendKeys(Keys.ENTER).build().perform();
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtWebSite")));
			    			object.findElement(By.name("txtWebSite")).sendKeys("www.lkss.com");
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtPhoneNumber")));
			    			object.findElement(By.name("txtPhoneNumber")).sendKeys("518274936");
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCPerson")));
			    			object.findElement(By.name("txtCPerson")).sendKeys("lokeshk");
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDesignation")));
			    			object.findElement(By.name("txtDesignation")).sendKeys("TEST");
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtDepartment")));
			    			object.findElement(By.name("txtDepartment")).sendKeys("DENTAL");
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCommType")));
			    			object.findElement(By.name("ddlCommType")).click();
			    			WebElement commType=object.findElement(By.name("ddlCommType"));
			    			Select s2=new Select(commType);
			    			s2.selectByVisibleText("CLAIMS");
			    			Thread.sleep(2000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlCommunicationType")));
			    			object.findElement(By.name("ddlCommunicationType")).click();
			    			WebElement commType1=object.findElement(By.name("ddlCommunicationType"));
			    			Select s3=new Select(commType1);
			    			s3.selectByVisibleText("MOBILE-3");
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtCommunication")));
			    			object.findElement(By.name("txtCommunication")).sendKeys("518274936");
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnContGrid")));
			    			object.findElement(By.name("btnContGrid")).click();
			    			Thread.sleep(2000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnSave")));
			    			Thread.sleep(2000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Record Saved Successfully')]")));
			    			if(object.findElement(By.xpath("//*[contains(text(),'Record Saved Successfully')]")).isDisplayed())
			    			{
			    				s.write("Prsenter Added Successfullt");
			    			}
			    			object.close();
			    			Thread.sleep(2000);
			    			object.switchTo().window(winDows1.get(1));
			    			Thread.sleep(1000);
			    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlPresentedTo")));
			    			WebElement presenterTo=object.findElement(By.name("ddlPresentedTo"));
			    			presenterTo.click();
			    			Thread.sleep(1000);
			    			presenterTo.sendKeys("TEST12");
			    			Thread.sleep(2000);
			    			a.sendKeys(Keys.ENTER).build().perform();
			    			Thread.sleep(1000);
			    		}
		    		}
		    		
		    	}
		    }
		    else
		    {
		    	
		    }
	 }
	 @Then("^Enter sales Enity Type as \"(.*)\" ,\"(.*)\"$")
	 public void sales_Entity_Type(String x,String y) throws Exception
		{
		 Thread.sleep(1000);
		 try
		 {
			 Thread.sleep(1000);
 			object.findElement(By.name("BtnSave")).click();
 			object.findElement(By.name("btnAddClient")).click();
		 }
		 catch(Exception tg)
		 {
			 Thread.sleep(1000);
			 object.switchTo().alert().accept();
			 Thread.sleep(2000);
			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlSource")));
				Thread.sleep(2000);
			    object.findElement(By.name("ddlSource")).click();
				if(x.equals("ACCOUNT RELATION"))
				{
			 	    WebElement dd=object.findElement(By.name("ddlSource"));
				    Select s=new Select(dd);
				    s.selectByVisibleText("ACCOUNT RELATION");
				}
				else if(x.equals("AGENT"))
				{
					WebElement dd=object.findElement(By.name("ddlSource"));
				    Select s=new Select(dd);
				    s.selectByVisibleText("AGENT");
				}
				else if(x.equals("BROKER"))
				{
					WebElement dd=object.findElement(By.name("ddlSource"));
				    Select s=new Select(dd);
				    s.selectByVisibleText("BROKER");
				}
				else if(x.equals("DIRECT SALES"))
				{
					WebElement dd=object.findElement(By.name("ddlSource"));
				    Select s=new Select(dd);
				    s.selectByVisibleText("DIRECT SALES");
				}
				else if(x.equals("REFERRAL"))
				{
					WebElement dd=object.findElement(By.name("ddlSource"));
				    Select s=new Select(dd);
				    s.selectByVisibleText("REFERRAL");
				}
				else if(x.equals("SALES MANAGERS"))
				{
					WebElement dd=object.findElement(By.name("ddlSource"));
				    Select s=new Select(dd);
				    s.selectByVisibleText("SALES MANAGERS");
				}
				else if(x.equals("TELE SALES"))
				{
					WebElement dd=object.findElement(By.name("ddlSource"));
				    Select s=new Select(dd);
				    s.selectByVisibleText("TELE SALES");
				}
				
				Thread.sleep(2000);
				try
				{
					Thread.sleep(1000);
		 			object.findElement(By.name("BtnSave")).click();
		 			object.findElement(By.name("btnAddClient")).click();
				}
				catch(Exception tf)
				{
					Thread.sleep(2000);
					object.switchTo().alert().accept();
					Thread.sleep(1000);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBroker")));
					Thread.sleep(2000);
					object.findElement(By.name("ddlBroker")).click();
					Thread.sleep(2000);
					WebElement salesEntityName_DD=object.findElement(By.name("ddlBroker"));
					Select s1=new Select(salesEntityName_DD);
					s1.selectByVisibleText(y);
					Thread.sleep(2000);
				}
				
		 }
			
		}
	 @And("^Enter payment as \"(.*)\"$")
	 public void payment_type(String x) throws Exception
		{
		 Thread.sleep(1000);
		 try
		 {
			 Thread.sleep(1000);
	 			object.findElement(By.name("BtnSave")).click();
	 			object.findElement(By.name("btnAddClient")).click();
		 }
		 catch(Exception ev)
		 {
			 Thread.sleep(1000);
			 object.switchTo().alert().accept();
			 Thread.sleep(2000);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlPaymentType")));
			    object.findElement(By.name("ddlPaymentType")).click();
				if(x.equals("MONTHLY"))
				{
			 	    WebElement dd=object.findElement(By.name("ddlPaymentType"));
				    Select s=new Select(dd);
				    s.selectByVisibleText("MONTHLY");
				}
				else if(x.equals("BI-MONTHLY"))
				{
					WebElement dd=object.findElement(By.name("ddlPaymentType"));
				    Select s=new Select(dd);
				    s.selectByVisibleText("BI-MONTHLY");
				}
				else if(x.equals("HALFYEARLY"))
				{
					WebElement dd=object.findElement(By.name("ddlPaymentType"));
				    Select s=new Select(dd);
				    s.selectByVisibleText("HALFYEARLY");
				}
				else if(x.equals("ANNUALLY"))
				{
					WebElement dd=object.findElement(By.name("ddlPaymentType"));
				    Select s=new Select(dd);
				    s.selectByVisibleText("ANNUALLY");
				}
				else
				{
					WebElement dd=object.findElement(By.name("ddlPaymentType"));
				    Select s=new Select(dd);
				    s.selectByVisibleText("CUSTOM");
				}
				Thread.sleep(2000);
				try
				 {
					 Thread.sleep(1000);
			 			object.findElement(By.name("BtnSave")).click();
			 			object.findElement(By.name("btnAddClient")).click();
				 }
				catch(Exception fq)
				{
					Thread.sleep(1000);
					object.switchTo().alert().accept();
					Thread.sleep(3000);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(@name,'txtIncepDate')])[2]")));
					object.findElement(By.xpath("(//*[contains(@name,'txtIncepDate')])[2]")).sendKeys("10/03/2019",Keys.TAB);
					Thread.sleep(6000);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtRemarks")));
					object.findElement(By.name("txtRemarks")).sendKeys("TEST");
					Thread.sleep(2000);
				}
				Thread.sleep(2000);
				try
				 {
					 Thread.sleep(1000);
			 			object.findElement(By.name("BtnSave")).click();
			 			object.findElement(By.name("btnAddClient")).click();
				 }
				catch(Exception fd)
				{
					Thread.sleep(1000);
					object.switchTo().alert().accept();
					Thread.sleep(2000);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtSizeLoading")));
					object.findElement(By.name("txtSizeLoading")).sendKeys("0");
					Thread.sleep(2000);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtinternally")));
					object.findElement(By.name("txtinternally")).sendKeys("TESTINTERNLLY");
					Thread.sleep(2000);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtQuotationSpecialNote")));
					object.findElement(By.name("txtQuotationSpecialNote")).sendKeys("TESTREMARKS");
					Thread.sleep(2000);
				}
		 }
		   
			
			
		}
	 @And("Enter Branch code as \"(.*)\"$")
	 public void brnch_code(String x) throws Exception
	 {
		 Thread.sleep(2000);
			try
			 {
				 Thread.sleep(1000);
		 			object.findElement(By.name("BtnSave")).click();
		 			object.findElement(By.name("btnAddClient")).click();
			 }
			catch(Exception fb)
			{
				Thread.sleep(1000);
				object.switchTo().alert().accept();
				Thread.sleep(2000);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQbranch")));
			    object.findElement(By.name("ddlQbranch")).click();
				if(x.equals("Head Office"))
				{
			 	    WebElement dd=object.findElement(By.name("ddlQbranch"));
				    Select s=new Select(dd);
				    s.selectByVisibleText("Head Office");
				}
				else if(x.equals("Riyadh Branch"))
				{
					WebElement dd=object.findElement(By.name("ddlQbranch"));
				    Select s=new Select(dd);
				    s.selectByVisibleText("Riyadh Branch");
				}
				else if(x.equals("Khobar Branch"))
				{
					WebElement dd=object.findElement(By.name("ddlQbranch"));
				    Select s=new Select(dd);
				    s.selectByVisibleText("Khobar Branch");
				}
				Thread.sleep(1000);
			}
		 
	 }
	 @And("^click on the add new class$")
	 public void add_new_class() throws Exception
	 {
		 Thread.sleep(2000);
			try
			 {
				 Thread.sleep(1000);
		 			object.findElement(By.name("BtnSave")).click();
		 			object.findElement(By.name("btnAddClient")).click();
			 }
			catch(Exception fa)
			{
				Thread.sleep(1000);
				object.switchTo().alert().accept();
				 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@name,'btnAddNewClass')]")));
				 object.findElement(By.xpath("//*[contains(@name,'btnAddNewClass')]")).click();
				 Thread.sleep(1000);
			}
		
		 
	 }
	 @And("^Select className as \"().*\"$")
	 public void className(String x) throws Exception
	 {
		 Thread.sleep(2000);
			try
			 {
				 Thread.sleep(1000);
		 			object.findElement(By.name("BtnSave")).click();
		 			object.findElement(By.name("btnAddClient")).click();
			 }
			catch(Exception cs)
			{
				Thread.sleep(1000);
				object.switchTo().alert().accept();
				Thread.sleep(2000);
				   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@name,'ddlClassName')]")));
				   object.findElement(By.xpath("//*[contains(@name,'ddlClassName')]")).click();
				   Thread.sleep(2000);
					if(x.equals("VIP+"))
					{
				 	    WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlClassName')]"));
					    Select s=new Select(dd);
					    Thread.sleep(1000);
					    s.selectByVisibleText("VIP+");
					}
					else if(x.equals("VIP"))
					{
						WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlClassName')]"));
					    Select s=new Select(dd);
					    Thread.sleep(1000);
					    s.selectByVisibleText("VIP");
					}
					else if(x.equals("A+"))
					{
						WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlClassName')]"));
					    Select s=new Select(dd);
					    Thread.sleep(1000);
					    s.selectByVisibleText("A+");
					}
					else if(x.equals("A"))
					{
						WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlClassName')]"));
					    Select s=new Select(dd);
					    Thread.sleep(1000);
					    s.selectByVisibleText("A");
					}
					else if(x.equals("B+"))
					{
						WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlClassName')]"));
					    Select s=new Select(dd);
					    Thread.sleep(1000);
					    s.selectByVisibleText("B+");
					}
					else if(x.equals("B"))
					{
						WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlClassName')]"));
					    Select s=new Select(dd);
					    Thread.sleep(1000);
					    s.selectByVisibleText("B");
					}
					else if(x.equals("C"))
					{
						WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlClassName')]"));
					    Select s=new Select(dd);
					    Thread.sleep(1000);
					    s.selectByVisibleText("C");
					}
					else if(x.equals("C LTD"))
					{
						WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlClassName')]"));
					    Select s=new Select(dd);
					    Thread.sleep(1000);
					    s.selectByVisibleText("C LTD");
					}
					else if(x.equals("C+"))
					{
						WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlClassName')]"));
					    Select s=new Select(dd);
					    Thread.sleep(1000);
					    s.selectByVisibleText("C+");
					}
					else
					{
						WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlClassName')]"));
					    Select s=new Select(dd);
					    Thread.sleep(1000);
					    s.selectByVisibleText("C RES");
					}
			}
		 
	 }
	 @And("^Select product as \"(.*)\"$")
	 public void product_name(String x) throws Exception
	 {
		 Thread.sleep(2000);
			try
			 {
				 Thread.sleep(1000);
		 			object.findElement(By.name("BtnSave")).click();
		 			object.findElement(By.name("btnAddClient")).click();
			 }
			catch(Exception cx)
			{
				Thread.sleep(1000);
				object.switchTo().alert().accept();
				Thread.sleep(2000);
				   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@name,'ddlProduct')]")));
				   object.findElement(By.xpath("//*[contains(@name,'ddlProduct')]")).click();
					if(x.equals("DIAMOND V1~16"))
					{
				 	    WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlProduct')]"));
					    Select s=new Select(dd);
					    Thread.sleep(1000);
					    s.selectByVisibleText("DIAMOND V1~16");
					}
					else if(x.equals("DIAMOND2 V1~17"))
					{
						WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlProduct')]"));
					    Select s=new Select(dd);
					    Thread.sleep(1000);
					    s.selectByVisibleText("DIAMOND2 V1~17");
					}
					else if(x.equals("DIAMOND3 V1~18"))
					{
						WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlProduct')]"));
					    Select s=new Select(dd);
					    Thread.sleep(1000);
					    s.selectByVisibleText("DIAMOND3 V1~18");
					}
					else if(x.equals("GOLD V1~19"))
					{
						WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlProduct')]"));
					    Select s=new Select(dd);
					    Thread.sleep(1000);
					    s.selectByVisibleText("GOLD V1~19");
					}
					else if(x.equals("GOLD2 V1~20"))
					{
						WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlProduct')]"));
					    Select s=new Select(dd);
					    Thread.sleep(1000);
					    s.selectByVisibleText("GOLD2 V1~20");
					}
					else if(x.equals("SILVER V1~21"))
					{
						WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlProduct')]"));
					    Select s=new Select(dd);
					    Thread.sleep(1000);
					    s.selectByVisibleText("SILVER V1~21");
					}
					else if(x.equals("BRONZE V1~22"))
					{
						WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlProduct')]"));
					    Select s=new Select(dd);
					    Thread.sleep(1000);
					    s.selectByVisibleText("BRONZE V1~22");
					}
					else if(x.equals("CLASSIC V1~23"))
					{
						WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlProduct')]"));
					    Select s=new Select(dd);
					    Thread.sleep(1000);
					    s.selectByVisibleText("CLASSIC V1~23");
					}
			}
		  
	 }
	 @And("^Select Nework as \"(.*)\"$")
	 public void network_name(String x) throws Exception
	 {
		 Thread.sleep(1000);
		 try
		 {
			 Thread.sleep(1000);
	 			object.findElement(By.name("BtnSave")).click();
	 			object.findElement(By.name("btnAddClient")).click();
		 }
		 catch(Exception dc)
		 {
			 Thread.sleep(1000);
			 object.switchTo().alert().accept();
			 Thread.sleep(2000);
			 Thread.sleep(2000);
			   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@name,'ddlNetwork')]")));
			   object.findElement(By.xpath("//*[contains(@name,'ddlNetwork')]")).click();
				if(x.equals("Bronze~12"))
				{
			 	    WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlNetwork')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("Bronze~12");
				}
				else if(x.equals("Classic~13"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlNetwork')]"));
				    Select s=new Select(dd);
				    Thread.sleep(3000);
				    s.selectByVisibleText("Classic~13");
				}
				else if(x.equals("Diamond KSA and WorldWide1~7"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlNetwork')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("Diamond KSA and WorldWide1~7");
				}
				else if(x.equals("Diamond KSA and WorldWide2~8"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlNetwork')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("Diamond KSA and WorldWide2~8");
				}
				else if(x.equals("Gold KSA and WorldWide1~9"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlNetwork')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("Gold KSA and WorldWide1~9");
				}
				else if(x.equals("Gold KSA and WorldWide2~10"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlNetwork')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("Gold KSA and WorldWide2~10");
				}
				else if(x.equals("Silver~11"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlNetwork')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("Silver~11");
				}
		 }
		  
	 }
	 @And("^Select Annual limit as \"(.*)\"$")
	 public void annual_limit(String x) throws Exception
	 {
		 Thread.sleep(1000);
		 try
		 {
			 Thread.sleep(1000);
	 			object.findElement(By.name("BtnSave")).click();
	 			object.findElement(By.name("btnAddClient")).click();
		 }
		 catch(Exception bf)
		 {
			 Thread.sleep(1000);
			 object.switchTo().alert().accept();
			 Thread.sleep(2000);
			   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@name,'ddlAnnualLimit')]")));
			   object.findElement(By.xpath("//*[contains(@name,'ddlAnnualLimit')]")).click();
				if(x.equals("0"))
				{
			 	    WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlAnnualLimit')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("0");
				}
				else if(x.equals("500000"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlAnnualLimit')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("500000");
				}
				else if(x.equals("1000000"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlAnnualLimit')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("1000000");
				}
		 }
		
	 }
     @And("^Select Provider classification as \"(.*)\"$")
     public void provider_classification(String x) throws Exception
     {
    	 Thread.sleep(1000);
		 try
		 {
			 Thread.sleep(1000);
	 			object.findElement(By.name("BtnSave")).click();
	 			object.findElement(By.name("btnAddClient")).click();
		 }
		 catch(Exception fg)
		 {
			 Thread.sleep(1000);
			 object.switchTo().alert().accept();
			 Thread.sleep(2000);
	    	   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@name,'ddlProviderClassification')]")));
			   object.findElement(By.xpath("//*[contains(@name,'ddlProviderClassification')]")).click();
				if(x.equals("VIP+~11"))
				{
			 	    WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlProviderClassification')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("VIP+~11");
				}
				else if(x.equals("VIP~12"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlProviderClassification')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("VIP~12");
				}
				else if(x.equals("A+~13"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlProviderClassification')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("A+~13");
				}
				else if(x.equals("A~14"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlProviderClassification')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("A~14");
				}
				else if(x.equals("B+~15"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlProviderClassification')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("B+~15");
				}
				else if(x.equals("B~16"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlProviderClassification')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("B~16");
				}
				else if(x.equals("C+~17"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlProviderClassification')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("C+~17");
				}
				else if(x.equals("C~18"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlProviderClassification')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("C~18");
				}
				else if(x.equals("C LTD~19"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlProviderClassification')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("C LTD~19");
				}
				else if(x.equals("CR~20"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlProviderClassification')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("CR~20");
				}
				else
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlProviderClassification')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("TEST~21");
				}
		 }
    	

     }
     @And("^Select loading template as \"(.*)\"$")
     public void loading_template(String x) throws Exception
     {
    	 Thread.sleep(1000);
		 try
		 {
			 Thread.sleep(1000);
	 			object.findElement(By.name("BtnSave")).click();
	 			object.findElement(By.name("btnAddClient")).click();
		 }
		 catch(Exception wq)
		 {
			 Thread.sleep(1000);
			 object.switchTo().alert().accept();
			 Thread.sleep(2000);
	    	   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@name,'ddlClassLoadings')]")));
			   object.findElement(By.xpath("//*[contains(@name,'ddlClassLoadings')]")).click();
				if(x.equals("2018 Broker Discount~13"))
				{
			 	    WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlClassLoadings')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("2018 Broker Discount~13");
				}
				else if(x.equals("2018 Broker~12"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlClassLoadings')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("2018 Broker~12");
				}
				else if(x.equals("2018 sales 5commission~16"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlClassLoadings')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("2018 sales 5commission~16");
				}
				else if(x.equals("2018 Sales max Discount~11"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlClassLoadings')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("2018 Sales max Discount~11");
				}
				else if(x.equals("2018 Sales~10"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlClassLoadings')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("2018 Sales~10");
				}
				else if(x.equals("NW10 Loading Broker~15"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlClassLoadings')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("NW10 Loading Broker~15");
				}
				else if(x.equals("NW10 Loading sales~14"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlClassLoadings')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("NW10 Loading sales~14");
				}
				else if(x.equals("TestLoadings~18"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlClassLoadings')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("TestLoadings~18");
				}
				else if(x.equals("xysx~17"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlClassLoadings')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("xysx~17");
				}
		 }
    	 
     }
     @And("^Select dentalLimit as \"(.*)\"$")
     public void dental_limit(String x) throws Exception
     {
    	 Thread.sleep(1000);
		 try
		 {
			 Thread.sleep(1000);
	 			object.findElement(By.name("BtnSave")).click();
	 			object.findElement(By.name("btnAddClient")).click();
		 }
		 catch(Exception vc)
		 {
			 Thread.sleep(1000);
			 object.switchTo().alert().accept();
			 Thread.sleep(2000);
	    	   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@name,'ddlDentalLimit')]")));
			   object.findElement(By.xpath("//*[contains(@name,'ddlDentalLimit')]")).click();
				if(x.equals("0"))
				{
			 	    WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlDentalLimit')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("0");
				}
				else if(x.equals("2000"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlDentalLimit')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("2000");
				}
				else if(x.equals("2500"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlDentalLimit')]"));
				    Select s=new Select(dd);
				    Thread.sleep(3000);
				    s.selectByVisibleText("2500");
				    Thread.sleep(1000);
				}
				else if(x.equals("3000"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlDentalLimit')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("3000");
				}
				else if(x.equals("3500"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlDentalLimit')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("3500");
				}
				else if(x.equals("4000"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlDentalLimit')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("4000");
				}
				else if(x.equals("5000"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlDentalLimit')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("5000");
				}
			 
		 }
    	 
     }
     @And("^Select Opticallimit as \"(.*)\"$")
     public void optical_limit(String x) throws Exception
     {
    	 Thread.sleep(1000);
		 try
		 {
			 Thread.sleep(1000);
	 			object.findElement(By.name("BtnSave")).click();
	 			object.findElement(By.name("btnAddClient")).click();
		 }
		 catch(Exception vf)
		 {
			 Thread.sleep(1000);
			 object.switchTo().alert().accept();
			 Thread.sleep(2000);
	    	   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@name,'ddlOpticalLimit')]")));
			   object.findElement(By.xpath("//*[contains(@name,'ddlOpticalLimit')]")).click();
				if(x.equals("400"))
				{
			 	    WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlOpticalLimit')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("400");
				}
				else if(x.equals("500"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlOpticalLimit')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("500");
				}
				else if(x.equals("750"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlOpticalLimit')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("750");
				}
				else if(x.equals("1000"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlOpticalLimit')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("1000");
				}
				else if(x.equals("1500"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlOpticalLimit')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("1500");
				}
				else if(x.equals("2000"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlOpticalLimit')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("2000");
				}
				else if(x.equals("2500"))
				{
					WebElement dd=object.findElement(By.xpath("//*[contains(@name,'ddlOpticalLimit')]"));
				    Select s=new Select(dd);
				    Thread.sleep(1000);
				    s.selectByVisibleText("2500");
				}
		 }
    	 
     }
     @Then("^click on save button$")
     public void save_button() throws Exception
     {
    	 Thread.sleep(5000);
    	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@name,'btnRadSave')]")));
    	 object.findElement(By.xpath("//input[contains(@name,'btnRadSave')]")).click();
    	 Thread.sleep(2000);
     }
     @And("^upload and verify the members info$")
     public void member_info() throws Exception
     {
    	 Thread.sleep(1000);
		/* try
		 {
			 Thread.sleep(1000);
			 //Thread.sleep(1000);
	 			object.findElement(By.name("btnDecision")).click();
	 			object.findElement(By.name("btnAddClient")).click();
	    	 Thread.sleep(5000);
		 }
		 catch(Exception vg)
		 {*/
			 Thread.sleep(1000);
			 //object.switchTo().alert().accept();
			 Thread.sleep(1000);
	    	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@name,'fuMemberImport')]")));
	    	 object.findElement(By.xpath("//*[contains(@name,'fuMemberImport')]")).sendKeys("C:\\Users\\amt\\Desktop\\lux-member\\LuxQuotationMemberUploadTemplate (2).xls");
	    	 Thread.sleep(3000);
	    	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@name,'BtnUpload')]")));
	    	 object.findElement(By.xpath("//*[contains(@name,'BtnUpload')]")).click();
	    	 Thread.sleep(4000);
	    	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@name,'BtnVerify')]")));
	    	 object.findElement(By.xpath("//*[contains(@name,'BtnVerify')]")).click();
	    	 Thread.sleep(5000);
	    	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@name,'BtnMSave')]")));
	    	 object.findElement(By.xpath("//*[contains(@name,'BtnMSave')]")).click();
	    	 Thread.sleep(3000);
		 //}
    	 
     }
     @And("^upload and verify premium$")
     public void premiums() throws Exception
     {
    	 Thread.sleep(1000);
		 /*try
		 {
			 Thread.sleep(1000);
			 object.findElement(By.name("btnDecision")).click();
	 			object.findElement(By.name("btnAddClient")).click();
		 }
		 catch(Exception vn)
		 {*/
			 Thread.sleep(1000);
			 //object.switchTo().alert().accept();
			 Thread.sleep(1000);
	    	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@name,'fuPremiumUpload')]")));
	    	 object.findElement(By.xpath("//*[contains(@name,'fuPremiumUpload')]")).sendKeys("C:\\Users\\amt\\Desktop\\lux-member\\LuxQuotationGrossPremiumUploadTemplate.xls");
	    	 Thread.sleep(3000);
	    	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@name,'btnPremiumUpload')]")));
	    	 object.findElement(By.xpath("//*[contains(@name,'btnPremiumUpload')]")).click();
	    	 Thread.sleep(4000);
	    	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@name,'btnPremiumVerify')]")));
	    	 object.findElement(By.xpath("//*[contains(@name,'btnPremiumVerify')]")).click();
	    	 Thread.sleep(5000);
	    	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@name,'btnPremiumSave')]")));
	    	 object.findElement(By.xpath("//*[contains(@name,'btnPremiumSave')]")).click();
	    	 Thread.sleep(3000);
	    	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Premiums Saved Successfully')]")));
	    	 if(object.findElement(By.xpath("//*[contains(text(),'Premiums Saved Successfully')]")).isDisplayed())
	    	 {
	    		 s.write("premiums succesfully saved");
	    	 }
		 //}
    	 
     }
     @And("^click on the save button$")
     public void saveButton() throws Exception
     {
    	 Thread.sleep(2000);
    	 wait.until(ExpectedConditions.visibilityOf(bp.saveButton));
    	 bp.click_saveButton();
     }
     @Then("^click on decision status$")
     public void decisionStatus() throws Exception
     {
    	 Thread.sleep(2000);
    	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='Quotation Details'])/following::input[5][@name='btnDecision']")));
    	 object.findElement(By.xpath("(//*[text()='Quotation Details'])/following::input[5][@name='btnDecision']")).click();
 		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlAppStatus")));
 		object.findElement(By.name("ddlAppStatus")).click();
 		WebElement approveStatus=object.findElement(By.name("ddlAppStatus"));
 		Select s1=new Select(approveStatus);
 		s1.selectByVisibleText("VERIFY");
 		Thread.sleep(1000);
 		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnVerify")));
 		object.findElement(By.name("btnVerify")).click();
 		try
 		{
 			Thread.sleep(2000);
 	 		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlAppStatus")));
 	 		object.findElement(By.name("ddlAppStatus")).click();
 	 		WebElement approveStatus1=object.findElement(By.name("ddlAppStatus"));
 	 		Select s2=new Select(approveStatus1);
 	 		s2.selectByVisibleText("APPROVED");
 	 		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnApprove")));
 	 		object.findElement(By.name("btnApprove")).click();
 	 		object.findElement(By.name("btnApprove")).click();
 	 		Thread.sleep(1000);
 		}
 		catch(Exception ew)
 		{
 			Thread.sleep(2000);
 			object.switchTo().alert().accept();
 	 		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBasepremium")));
 	 		object.findElement(By.name("ddlBasepremium")).click();
 	 		WebElement basePremium=object.findElement(By.name("ddlBasepremium"));
 	 		Select s3=new Select(basePremium);
 	 		s3.selectByVisibleText("StandardQuote");
 	 		Thread.sleep(2000);
 	 		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlAppStatus")));
 	 		object.findElement(By.name("ddlAppStatus")).click();
 	 		WebElement approveStatus1=object.findElement(By.name("ddlAppStatus"));
 	 		Select s2=new Select(approveStatus1);
 	 		s2.selectByVisibleText("APPROVED");
 	 		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnApprove")));
 	 		object.findElement(By.name("btnApprove")).click();
 	 		Thread.sleep(1000);
 	 		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BtnBack")));
 	 		object.findElement(By.name("BtnBack")).click();
 		}
     }
    @And("^click on Convert policy$")
     public void convertPolicy() throws Exception
     {
    	 Thread.sleep(2000);
    	 Quotation_number=object.findElement(By.xpath("//*[text()='QuotationNo']/following::input[1][@name='txtQuotationNo']")).getAttribute("value");
    	 System.out.println(Quotation_number);
    	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnPConvert")));
    	 object.findElement(By.name("btnPConvert")).click();
    	 Thread.sleep(1000);
    	 object.switchTo().alert().accept();
    	 Thread.sleep(2000);
    	 /*wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@name='btnDecision']/following::a[1][@id='LBButton']")));
    	 String quotationPolicy=object.findElement(By.xpath("//*[@name='btnDecision']/following::a[1][@id='LBButton']")).getText();
    	 s.write(quotationPolicy);
    	 object.close();
    	 Thread.sleep(1000);
    	 Pattern p=Pattern.compile("[0-9]+");
    	 Matcher m=p.matcher(quotationPolicy);
    	 while(m.find())
    	 {
    		 s.write(m.group());
    	 }
    	 Thread.sleep(2000);
    	 object.close();
    	 Thread.sleep(2000);*/
    	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Converted to Policy Successfully...')]")));
    	 object.findElement(By.xpath("//*[contains(text(),'Converted to Policy Successfully...')]")).click();
    	 Thread.sleep(2000);
    	 ArrayList<String> winDows=new ArrayList<String>(object.getWindowHandles());
    	 Thread.sleep(1000);
    	 object.switchTo().window(winDows.get(2));	
    	 Thread.sleep(3000);
    	 ArrayList<String> winDows1=new ArrayList<String>(object.getWindowHandles());
    	 Thread.sleep(1000);
    	 object.switchTo().window(winDows.get(0));	
    	 Thread.sleep(3000);
    	 Actions a=new Actions(object);
    	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
	    object.findElement(By.name("ddlQuotationNo")).sendKeys(Quotation_number);
	    Thread.sleep(3000);
	    a.sendKeys(Keys.DOWN).build().perform();
	    Thread.sleep(1000);
	    a.sendKeys(Keys.ENTER).build().perform();
	    Thread.sleep(2000);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
	    Thread.sleep(1000);
	    object.findElement(By.name("ddlDecisionStatus")).click();
	    Thread.sleep(1000);
	    WebElement decisionStatus=object.findElement(By.name("ddlDecisionStatus"));
	    Thread.sleep(4000);
	    Select t1=new Select(decisionStatus);
	    t1.selectByVisibleText("ALL");
	    Thread.sleep(3000);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
	    object.findElement(By.name("btnquery")).click();
	    Thread.sleep(2000);
	    String quotationStatus=object.findElement(By.xpath("(//*[text()='Approval Status'])[2]/following::tr[4]/td[7]")).getText();
	    System.out.println(quotationStatus);
	    Thread.sleep(1000);
	    if(quotationStatus.contains("Converted to Policy"))
	    {
	    	s.write("quotation is converted to policy successfully");
	    }
    	 
     }
     @And("^validate Policy Contract List$")
     public void policy_contract_list() throws Exception
     {
    	   //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("(//*[text()='Policy Admin'])[1]"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Policy Contract List']")));
		    object.findElement(By.xpath("//*[text()='Policy Contract List']")).click();
		    Thread.sleep(4000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
		    object.findElement(By.name("ddlQuotationNo")).sendKeys(Quotation_number);
		    Thread.sleep(3000);
		    a.sendKeys(Keys.DOWN).build().perform();
		    Thread.sleep(1000);
		    a.sendKeys(Keys.ENTER).build().perform();
		    Thread.sleep(3000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
		    object.findElement(By.name("btnquery")).click();
		    Thread.sleep(10000);
		    //WebElement policyNumber=object.findElement(By.xpath("//*[@class='raDiv']/following::table[16]/following::tbody[1]/tr/td[2]"));
		    //Thread.sleep(3000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='PolicyNo.']/following::tr[4]/td[2]")));
		    //Thread.sleep(2000);
		    //policyNumber.click();
		    //a.click(policyNumber).build().perform();
		    //object.findElement(By.xpath("//*[text()='PolicyNo.']/following::tr[4]/td[2]")).click();
		    WebElement policyNumber=object.findElement(By.xpath("//*[text()='PolicyNo.']/following::tr[4]/td[2]"));
		    Thread.sleep(4000);
		    policyNumber.click();
		    JavascriptExecutor js=((JavascriptExecutor)object);
		    Thread.sleep(6000);
		    js.executeScript("arguments[0].click();",policyNumber);
		    Thread.sleep(3000);
		    ArrayList<String> winDows=new ArrayList<String>(object.getWindowHandles());
		    Thread.sleep(2000);
		    object.switchTo().window(winDows.get(1));
		    Thread.sleep(4000);
		    System.out.println("Changing The Window....!");
		    Thread.sleep(2000);
		    if(object.findElement(By.name("btnSave")).isEnabled() || object.findElement(By.name("btnDelete")).isEnabled() || object.findElement(By.name("BtnDecisionstatus")).isEnabled())
		    {
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btlclassdetails")));
		    	object.findElement(By.name("btlclassdetails")).click();
		    	Thread.sleep(2000);
		    	if(bp.addButton.isEnabled() || bp.clearButton.isEnabled())
		    	{
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlClassCode")));
		    		WebElement classCode=object.findElement(By.name("ddlClassCode"));
		    		Thread.sleep(1000);
		    		classCode.click();
		    		Thread.sleep(1000);
		    		Select s1=new Select(classCode);
		    		s1.selectByVisibleText("C RES-1");
		    		Thread.sleep(2000);
		    		
		    	}
		    }
     }
     @Then("^Validate quotation page$")
     public void quotationPage() throws Exception
     {
    	//homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Quotation Module']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Quotation List']")));
		    object.findElement(By.xpath("//*[text()='Quotation List']")).click();
		    Thread.sleep(4000);
    	 Thread.sleep(1000);
    	 if(object.findElement(By.name("btnquery")).isEnabled() && object.findElement(By.name("btnClear")).isEnabled() && object.findElement(By.name("btnLUXQuote")).isEnabled())
    	 {
    		 Thread.sleep(1000);
    		 try
    		 {
    			 Thread.sleep(3000);
    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlpresentedto")));
        		 object.findElement(By.name("ddlpresentedto")).sendKeys("kaskla");
        		 object.findElement(By.name("btnquery")).click();
        		 Thread.sleep(2000);
        		 object.findElement(By.name("ddlDecisionStatus")).click();
    		 }
    		 catch(Exception et)
    		 {
    			 Thread.sleep(2000);
    			 object.switchTo().alert().accept();
    			 Thread.sleep(3000);
    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlpresentedto")));
        		 object.findElement(By.name("ddlpresentedto")).sendKeys("TEST1");
        		 Thread.sleep(3000);
        		 //Actions a=new Actions(object);
        		 a.sendKeys(Keys.ENTER).build().perform();
        		 Thread.sleep(2000);
        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
        		 object.findElement(By.name("ddlDecisionStatus")).click();
        		 Thread.sleep(2000);
        		 WebElement decisionStatus=object.findElement(By.name("ddlDecisionStatus"));
        		 Select s1=new Select(decisionStatus);
        		 s1.selectByVisibleText("ALL");
        		 Thread.sleep(2000);
        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
        		 object.findElement(By.name("ddlApprovalStatus")).click();
        		 Thread.sleep(2000);
        		 WebElement approvalStatus=object.findElement(By.name("ddlApprovalStatus"));
        		 Select s2=new Select(approvalStatus);
        		 Thread.sleep(2000);
        		 s2.selectByVisibleText("ALL");
        		 Thread.sleep(2000);
        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
        		 object.findElement(By.name("btnquery")).click();
        		 Thread.sleep(2000);
        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2019-3-12116/00']")));
        		 object.findElement(By.xpath("//*[text()='2019-3-12116/00']")).click();
        		 Thread.sleep(2000);
        		 ArrayList<String> winDows=new ArrayList<String>(object.getWindowHandles());
        		 object.switchTo().window(winDows.get(1));
        		 Thread.sleep(2000);
        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnDecision")));
        		 Thread.sleep(2000);
        		 object.close();
        		 Thread.sleep(2000);
        		 object.switchTo().window(winDows.get(0));
        		 Thread.sleep(2000);
    		 }
    		 Thread.sleep(2000);
        		 try
        		 {
        			 Thread.sleep(1000);
        			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlpresentedto")));
            		 object.findElement(By.name("ddlpresentedto")).sendKeys("dkjsbda");
            		 object.findElement(By.name("btnquery")).click();
            		 object.findElement(By.name("ddlDecisionStatus")).click();
        		 }
        		 catch(Exception et1)
        		 {
        			 Thread.sleep(1000);
        			 object.switchTo().alert().accept();
        			 Thread.sleep(2000);
        			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlpresentedto")));
            		 object.findElement(By.name("ddlpresentedto")).sendKeys("TEST1");
            		 Thread.sleep(3000);
            		 //Actions a=new Actions(object);
            		 a.sendKeys(Keys.ENTER).build().perform();
            		 Thread.sleep(2000);
            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
            		 object.findElement(By.name("ddlDecisionStatus")).click();
            		 Thread.sleep(2000);
            		 WebElement decisionStatus1=object.findElement(By.name("ddlDecisionStatus"));
            		 Select s11=new Select(decisionStatus1);
            		 s11.selectByVisibleText("ALL");
            		 Thread.sleep(2000);
            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
            		 object.findElement(By.name("ddlApprovalStatus")).click();
            		 Thread.sleep(2000);
            		 WebElement approvalStatus1=object.findElement(By.name("ddlApprovalStatus"));
            		 Select s12=new Select(approvalStatus1);
            		 s12.selectByVisibleText("PENDING");
            		 Thread.sleep(2000);
            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
            		 object.findElement(By.name("btnquery")).click();
            		 Thread.sleep(2000);
            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2019-3-12126/00']")));
            		 object.findElement(By.xpath("//*[text()='2019-3-12121/00']")).click();
            		 Thread.sleep(2000);
            		 ArrayList<String> winDows1=new ArrayList<String>(object.getWindowHandles());
            		 object.switchTo().window(winDows1.get(1));
            		 Thread.sleep(2000);
            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnDecision")));
            		 Thread.sleep(2000);
            		 object.close();
            		 Thread.sleep(2000);
            		 object.switchTo().window(winDows1.get(0));
            		 Thread.sleep(2000);
        		 }
            		 try
            		 {
            			 Thread.sleep(1000);
            			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlpresentedto")));
                		 object.findElement(By.name("ddlpresentedto")).sendKeys("lsahk");
                		 object.findElement(By.name("btnquery")).click();
                		 object.findElement(By.name("ddlDecisionStatus")).click();
            		 }
            		 catch(Exception et2)
            		 {
            			 Thread.sleep(1000);
            			 object.switchTo().alert().accept();
            			 Thread.sleep(2000);
            			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlpresentedto")));
                		 object.findElement(By.name("ddlpresentedto")).sendKeys("TEST1");
                		 Thread.sleep(3000);
                		 //Actions a=new Actions(object);
                		 a.sendKeys(Keys.ENTER).build().perform();
                		 Thread.sleep(2000);
                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                		 object.findElement(By.name("ddlDecisionStatus")).click();
                		 Thread.sleep(2000);
                		 WebElement decisionStatus12=object.findElement(By.name("ddlDecisionStatus"));
                		 Select s01=new Select(decisionStatus12);
                		 s01.selectByVisibleText("ALL");
                		 Thread.sleep(2000);
                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                		 object.findElement(By.name("ddlApprovalStatus")).click();
                		 Thread.sleep(2000);
                		 WebElement approvalStatus12=object.findElement(By.name("ddlApprovalStatus"));
                		 Select s10=new Select(approvalStatus12);
                		 s10.selectByVisibleText("APPROVED");
                		 Thread.sleep(2000);
                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                		 object.findElement(By.name("btnquery")).click();
                		 Thread.sleep(2000);
                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2019-3-12116/00']")));
                		 object.findElement(By.xpath("//*[text()='2019-3-12116/00']")).click();
                		 Thread.sleep(2000);
                		 ArrayList<String> winDows12=new ArrayList<String>(object.getWindowHandles());
                		 object.switchTo().window(winDows12.get(1));
                		 Thread.sleep(2000);
                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnDecision")));
                		 Thread.sleep(2000);
                		 object.close();
                		 Thread.sleep(2000);
                		 object.switchTo().window(winDows12.get(0));
                		 Thread.sleep(2000);
            		 }
                		 try
                		 {
                			 Thread.sleep(1000);
                			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlpresentedto")));
                    		 object.findElement(By.name("ddlpresentedto")).sendKeys("lskljdsd");
                    		 object.findElement(By.name("btnquery")).click();
                    		 object.findElement(By.name("ddlDecisionStatus")).click();
                		 }
                		 catch(Exception et3)
                		 {
                			 Thread.sleep(1000);
                			 object.switchTo().alert().accept();
                			 Thread.sleep(2000);
                			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlpresentedto")));
                    		 object.findElement(By.name("ddlpresentedto")).sendKeys("TEST1");
                    		 Thread.sleep(3000);
                    		 //Actions a=new Actions(object);
                    		 a.sendKeys(Keys.ENTER).build().perform();
                    		 Thread.sleep(2000);
                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                    		 object.findElement(By.name("ddlDecisionStatus")).click();
                    		 Thread.sleep(2000);
                    		 WebElement decisionStatus13=object.findElement(By.name("ddlDecisionStatus"));
                    		 Select s02=new Select(decisionStatus13);
                    		 s02.selectByVisibleText("ALL");
                    		 Thread.sleep(2000);
                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                    		 object.findElement(By.name("ddlApprovalStatus")).click();
                    		 Thread.sleep(2000);
                    		 WebElement approvalStatus02=object.findElement(By.name("ddlApprovalStatus"));
                    		 Select s03=new Select(approvalStatus02);
                    		 s03.selectByVisibleText("CLOSED");
                    		 Thread.sleep(2000);
                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                    		 object.findElement(By.name("btnquery")).click();
                    		 Thread.sleep(2000);
                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Records not Found !...']")));
                    		 if(object.findElement(By.xpath("//*[text()='Records not Found !...']")).isDisplayed())
                    		 {
                    			 System.out.println("Records not found");
                    		 }
                    		 Thread.sleep(2000);
                		 }
                    		 try
                    		 {
                    			 Thread.sleep(1000);
                    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlpresentedto")));
                        		 object.findElement(By.name("ddlpresentedto")).sendKeys("skslk");
                        		 object.findElement(By.name("btnquery")).click();
                        		 object.findElement(By.name("ddlDecisionStatus")).click();
                    		 }
                    		 catch(Exception et4)
                    		 {
                    			 Thread.sleep(1000);
                    			 object.switchTo().alert().accept();
                    			 Thread.sleep(2000);
                    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlpresentedto")));
                        		 object.findElement(By.name("ddlpresentedto")).sendKeys("TEST1");
                        		 Thread.sleep(3000);
                        		 //Actions a=new Actions(object);
                        		 a.sendKeys(Keys.ENTER).build().perform();
                        		 Thread.sleep(2000);
                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                        		 object.findElement(By.name("ddlDecisionStatus")).click();
                        		 Thread.sleep(2000);
                        		 WebElement decisionStatus33=object.findElement(By.name("ddlDecisionStatus"));
                        		 Select s22=new Select(decisionStatus33);
                        		 s22.selectByVisibleText("ALL");
                        		 Thread.sleep(2000);
                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                        		 object.findElement(By.name("ddlApprovalStatus")).click();
                        		 Thread.sleep(2000);
                        		 WebElement approvalStatus72=object.findElement(By.name("ddlApprovalStatus"));
                        		 Select s73=new Select(approvalStatus72);
                        		 s73.selectByVisibleText("CLOSED W/O APPROVAL");
                        		 Thread.sleep(2000);
                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                        		 object.findElement(By.name("btnquery")).click();
                        		 Thread.sleep(2000);
                        		 /*wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2019-3-12116/00']")));
                        		 object.findElement(By.xpath("//*[text()='2019-3-12116/00']")).click();
                        		 Thread.sleep(2000);*/
                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Records not Found !...']")));
                        		 if(object.findElement(By.xpath("//*[text()='Records not Found !...']")).isDisplayed())
                        		 {
                        			 System.out.println("Records not found");
                        		 }
                        		 Thread.sleep(2000);
                    		 }
                        		 try
                        		 {
                        			 Thread.sleep(1000);
                        			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlpresentedto")));
                            		 object.findElement(By.name("ddlpresentedto")).sendKeys("kshdkjd");
                            		 object.findElement(By.name("btnquery")).click();
                            		 object.findElement(By.name("ddlDecisionStatus")).click();
                        		 }
                        		 catch(Exception et5)
                        		 {
                        			 Thread.sleep(1000);
                        			 object.switchTo().alert().accept();
                        			 Thread.sleep(2000);
                        			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlpresentedto")));
                            		 object.findElement(By.name("ddlpresentedto")).sendKeys("TEST1");
                            		 Thread.sleep(3000);
                            		 //Actions a=new Actions(object);
                            		 a.sendKeys(Keys.ENTER).build().perform();
                            		 Thread.sleep(2000);
                            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                            		 object.findElement(By.name("ddlDecisionStatus")).click();
                            		 Thread.sleep(2000);
                            		 WebElement decisionStatus43=object.findElement(By.name("ddlDecisionStatus"));
                            		 Select s42=new Select(decisionStatus43);
                            		 s42.selectByVisibleText("ALL");
                            		 Thread.sleep(2000);
                            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                            		 object.findElement(By.name("ddlApprovalStatus")).click();
                            		 Thread.sleep(2000);
                            		 WebElement approvalStatus42=object.findElement(By.name("ddlApprovalStatus"));
                            		 Select s43=new Select(approvalStatus42);
                            		 s43.selectByVisibleText("CONVERTED TO POLICY");
                            		 Thread.sleep(2000);
                            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                            		 object.findElement(By.name("btnquery")).click();
                            		 Thread.sleep(2000);
                            		 /*wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2019-3-12116/00']")));
                            		 object.findElement(By.xpath("//*[text()='2019-3-12116/00']")).click();
                            		 Thread.sleep(2000);*/
                            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Records not Found !...']")));
                            		 if(object.findElement(By.xpath("//*[text()='Records not Found !...']")).isDisplayed())
                            		 {
                            			 System.out.println("Records not found");
                            		 }
                            		 Thread.sleep(2000);
                        		   }
                            		 
                                		 try
                                		 {
                                			 Thread.sleep(1000);
                                			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlpresentedto")));
                                    		 object.findElement(By.name("ddlpresentedto")).sendKeys("hldkhf");
                                    		 object.findElement(By.name("btnquery")).click();
                                    		 object.findElement(By.name("ddlDecisionStatus")).click();
                                		 }
                                		 catch(Exception et7)
                                		 {
                                			 Thread.sleep(1000);
                                			 object.switchTo().alert().accept();
                                			 Thread.sleep(2000);
                                			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlpresentedto")));
                                    		 object.findElement(By.name("ddlpresentedto")).sendKeys("TEST1");
                                    		 Thread.sleep(3000);
                                    		 //Actions a=new Actions(object);
                                    		 a.sendKeys(Keys.ENTER).build().perform();
                                    		 Thread.sleep(2000);
                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                                    		 object.findElement(By.name("ddlDecisionStatus")).click();
                                    		 Thread.sleep(2000);
                                    		 WebElement decisionStatus2=object.findElement(By.name("ddlDecisionStatus"));
                                    		 Select s3=new Select(decisionStatus2);
                                    		 s3.selectByVisibleText("PENDING");
                                    		 Thread.sleep(2000);
                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                                    		 object.findElement(By.name("ddlApprovalStatus")).click();
                                    		 Thread.sleep(2000);
                                    		 WebElement approvalStatus2=object.findElement(By.name("ddlApprovalStatus"));
                                    		 Select s4=new Select(approvalStatus2);
                                    		 s4.selectByVisibleText("ALL");
                                    		 Thread.sleep(2000);
                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                                    		 object.findElement(By.name("btnquery")).click();
                                    		 Thread.sleep(2000);
                                    		 /*wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2019-3-12116/00']")));
                                    		 object.findElement(By.xpath("//*[text()='2019-3-12116/00']")).click();
                                    		 Thread.sleep(2000);*/
                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Records not Found !...']")));
                                    		 if(object.findElement(By.xpath("//*[text()='Records not Found !...']")).isDisplayed())
                                    		 {
                                    			 System.out.println("Records not found");
                                    		 }
                                    		 Thread.sleep(2000);
                                		  }
                                    		 try
                                    		 {
                                    			 Thread.sleep(1000);
                                    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlpresentedto")));
                                        		 object.findElement(By.name("ddlpresentedto")).sendKeys("ohdjas");
                                        		 object.findElement(By.name("btnquery")).click();
                                        		 object.findElement(By.name("ddlDecisionStatus")).click();
                                    		 }
                                    		 catch(Exception et8)
                                    		 {
                                    			 Thread.sleep(1000);
                                    			 object.switchTo().alert().accept();
                                    			 Thread.sleep(2000);
                                    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlpresentedto")));
                                        		 object.findElement(By.name("ddlpresentedto")).sendKeys("gahflk");
                                        		 Thread.sleep(3000);
                                        		 //Actions a=new Actions(object);
                                        		 a.sendKeys(Keys.ENTER).build().perform();
                                        		 Thread.sleep(2000);
                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                                        		 object.findElement(By.name("ddlDecisionStatus")).click();
                                        		 Thread.sleep(2000);
                                        		 WebElement decisionstatus1=object.findElement(By.name("ddlDecisionStatus"));
                                        		 Select t1=new Select(decisionstatus1);
                                        		 t1.selectByVisibleText("PENDING");
                                        		 Thread.sleep(2000);
                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                                        		 object.findElement(By.name("ddlApprovalStatus")).click();
                                        		 Thread.sleep(2000);
                                        		 WebElement approvalstatus1=object.findElement(By.name("ddlApprovalStatus"));
                                        		 Select t12=new Select(approvalstatus1);
                                        		 t12.selectByVisibleText("PENDING");
                                        		 Thread.sleep(2000);
                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                                        		 object.findElement(By.name("btnquery")).click();
                                        		 Thread.sleep(2000);
                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Records not Found !...']")));
                                        		 if(object.findElement(By.xpath("//*[text()='Records not Found !...']")).isDisplayed())
                                        		 {
                                        			 System.out.println("Records not found");
                                        		 }
                                        		 Thread.sleep(2000);
                                    		  }
                                        		 try
                                        		 {
                                        			 Thread.sleep(1000);
                                        			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlpresentedto")));
                                            		 object.findElement(By.name("ddlpresentedto")).sendKeys("sjas");
                                            		 object.findElement(By.name("btnquery")).click();
                                            		 object.findElement(By.name("ddlDecisionStatus")).click();
                                        		 }
                                        		 catch(Exception ew1)
                                        		 {
                                        			 Thread.sleep(1000);
                                        			 object.switchTo().alert().accept();
                                        			 Thread.sleep(2000);
                                        			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlpresentedto")));
                                            		 object.findElement(By.name("ddlpresentedto")).sendKeys("TEST1");
                                            		 Thread.sleep(3000);
                                            		 //Actions a=new Actions(object);
                                            		 a.sendKeys(Keys.ENTER).build().perform();
                                            		 Thread.sleep(2000);
                                            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                                            		 object.findElement(By.name("ddlDecisionStatus")).click();
                                            		 Thread.sleep(2000);
                                            		 WebElement decisionstatus12=object.findElement(By.name("ddlDecisionStatus"));
                                            		 Select t01=new Select(decisionstatus12);
                                            		 t01.selectByVisibleText("PENDING");
                                            		 Thread.sleep(2000);
                                            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                                            		 object.findElement(By.name("ddlApprovalStatus")).click();
                                            		 Thread.sleep(2000);
                                            		 WebElement approvalstatus12=object.findElement(By.name("ddlApprovalStatus"));
                                            		 Select t10=new Select(approvalstatus12);
                                            		 t10.selectByVisibleText("APPROVED");
                                            		 Thread.sleep(2000);
                                            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                                            		 object.findElement(By.name("btnquery")).click();
                                            		 Thread.sleep(2000);
                                            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Records not Found !...']")));
                                            		 if(object.findElement(By.xpath("//*[text()='Records not Found !...']")).isDisplayed())
                                            		 {
                                            			 System.out.println("Records not found");
                                            		 }
                                            		 Thread.sleep(2000);
                                        		 }
                                            		 try
                                            		 {
                                            			 Thread.sleep(1000);
                                            			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlpresentedto")));
                                                		 object.findElement(By.name("ddlpresentedto")).sendKeys("fhrthrt");
                                                		 object.findElement(By.name("btnquery")).click();
                                                		 object.findElement(By.name("ddlDecisionStatus")).click();
                                            		 }
                                            		 catch(Exception ew5)
                                            		 {
                                            			 Thread.sleep(1000);
                                            			 object.switchTo().alert().accept();
                                            			 Thread.sleep(2000);
                                            			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlpresentedto")));
                                                		 object.findElement(By.name("ddlpresentedto")).sendKeys("TEST1");
                                                		 Thread.sleep(3000);
                                                		 //Actions a=new Actions(object);
                                                		 a.sendKeys(Keys.ENTER).build().perform();
                                                		 Thread.sleep(2000);
                                                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                                                		 object.findElement(By.name("ddlDecisionStatus")).click();
                                                		 Thread.sleep(2000);
                                                		 WebElement decisionstatus13=object.findElement(By.name("ddlDecisionStatus"));
                                                		 Select r02=new Select(decisionstatus13);
                                                		 r02.selectByVisibleText("PENDING");
                                                		 Thread.sleep(2000);
                                                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                                                		 object.findElement(By.name("ddlApprovalStatus")).click();
                                                		 Thread.sleep(2000);
                                                		 WebElement approvalstatus02=object.findElement(By.name("ddlApprovalStatus"));
                                                		 Select t03=new Select(approvalstatus02);
                                                		 t03.selectByVisibleText("CLOSED");
                                                		 Thread.sleep(2000);
                                                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                                                		 object.findElement(By.name("btnquery")).click();
                                                		 Thread.sleep(2000);
                                                		 
                                                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Records not Found !...']")));
                                                		 if(object.findElement(By.xpath("//*[text()='Records not Found !...']")).isDisplayed())
                                                		 {
                                                			 System.out.println("Records not found");
                                                		 }
                                                		 Thread.sleep(2000);
                                            		   }
                                                		 try
                                                		 {
                                                			 Thread.sleep(1000);
                                                			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlpresentedto")));
                                                    		 object.findElement(By.name("ddlpresentedto")).sendKeys("jadkjas");
                                                    		 object.findElement(By.name("btnquery")).click();
                                                    		 object.findElement(By.name("ddlDecisionStatus")).click();
                                                		 }
                                                		 catch(Exception ew4)
                                                		 {
                                                			 Thread.sleep(1000);
                                                			 object.switchTo().alert().accept();
                                                			 Thread.sleep(2000);
                                                			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlpresentedto")));
                                                    		 object.findElement(By.name("ddlpresentedto")).sendKeys("TEST1");
                                                    		 Thread.sleep(3000);
                                                    		 //Actions a=new Actions(object);
                                                    		 a.sendKeys(Keys.ENTER).build().perform();
                                                    		 Thread.sleep(2000);
                                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                                                    		 object.findElement(By.name("ddlDecisionStatus")).click();
                                                    		 Thread.sleep(2000);
                                                    		 WebElement decisionstatus33=object.findElement(By.name("ddlDecisionStatus"));
                                                    		 Select t22=new Select(decisionstatus33);
                                                    		 t22.selectByVisibleText("PENDING");
                                                    		 Thread.sleep(2000);
                                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                                                    		 object.findElement(By.name("ddlApprovalStatus")).click();
                                                    		 Thread.sleep(2000);
                                                    		 WebElement approvalstatus72=object.findElement(By.name("ddlApprovalStatus"));
                                                    		 Select t73=new Select(approvalstatus72);
                                                    		 t73.selectByVisibleText("CLOSED W/O APPROVAL");
                                                    		 Thread.sleep(2000);
                                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                                                    		 object.findElement(By.name("btnquery")).click();
                                                    		 Thread.sleep(2000);
                                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2019-3-12116/00']")));
                                                    		 object.findElement(By.xpath("//*[text()='2019-3-12116/00']")).click();
                                                    		 Thread.sleep(2000);
                                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Records not Found !...']")));
                                                    		 if(object.findElement(By.xpath("//*[text()='Records not Found !...']")).isDisplayed())
                                                    		 {
                                                    			 System.out.println("Records not found");
                                                    		 }
                                                    		 Thread.sleep(2000);
                                                		 }
                                                    		 try
                                                    		 {
                                                    			 Thread.sleep(1000);
                                                    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlpresentedto")));
                                                        		 object.findElement(By.name("ddlpresentedto")).sendKeys("ncksdnnk");
                                                        		 Thread.sleep(2000);
                                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                                                        		 object.findElement(By.name("btnquery")).click();
                                                        		 object.findElement(By.name("ddlDecisionStatus")).click();
                                                    		 }
                                                    		 catch(Exception et10)
                                                    		 {
                                                    			 Thread.sleep(1000);
                                                    			 object.switchTo().alert().accept();
                                                    			 Thread.sleep(2000);
                                                    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlpresentedto")));
                                                        		 object.findElement(By.name("ddlpresentedto")).sendKeys("TEST1");
                                                        		 Thread.sleep(3000);
                                                        		 //Actions a=new Actions(object);
                                                        		 a.sendKeys(Keys.ENTER).build().perform();
                                                        		 Thread.sleep(2000);
                                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                                                        		 object.findElement(By.name("ddlDecisionStatus")).click();
                                                        		 Thread.sleep(2000);
                                                        		 WebElement decisionstatus43=object.findElement(By.name("ddlDecisionStatus"));
                                                        		 Select t42=new Select(decisionstatus43);
                                                        		 t42.selectByVisibleText("PENDING");
                                                        		 Thread.sleep(2000);
                                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                                                        		 object.findElement(By.name("ddlApprovalStatus")).click();
                                                        		 Thread.sleep(2000);
                                                        		 WebElement approvalstatus42=object.findElement(By.name("ddlApprovalStatus"));
                                                        		 Select t43=new Select(approvalstatus42);
                                                        		 t43.selectByVisibleText("CONVERTED TO POLICY");
                                                        		 Thread.sleep(2000);
                                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                                                        		 object.findElement(By.name("btnquery")).click();
                                                        		 Thread.sleep(2000);
                                                        		/* wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2019-3-12116/00']")));
                                                        		 object.findElement(By.xpath("//*[text()='2019-3-12116/00']")).click();
                                                        		 Thread.sleep(2000);*/
                                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Records not Found !...']")));
                                                        		 if(object.findElement(By.xpath("//*[text()='Records not Found !...']")).isDisplayed())
                                                        		 {
                                                        			 System.out.println("Records not found");
                                                        		 }
                                                        		 Thread.sleep(2000);
                                                        		
                                                    		 }
    		 Thread.sleep(2000);
    		 try
    		 {
    			 Thread.sleep(1000);
    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBroker")));
        		 object.findElement(By.name("ddlBroker")).sendKeys("TEST1");
        		 object.findElement(By.name("btnquery")).click();
        		 object.findElement(By.name("ddlDecisionStatus")).click();
    		 }
    		 catch(Exception et)
    		 {
    			 Thread.sleep(1000);
    			 object.switchTo().alert().accept();
    			 Thread.sleep(2000);
    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBroker")));
        		 object.findElement(By.name("ddlBroker")).click();
        		 Thread.sleep(2000);
        		 a.sendKeys(Keys.DOWN).build().perform();
        		 Thread.sleep(2000);
        		 a.sendKeys(Keys.ENTER).build().perform();
        		 Thread.sleep(3000);
        		 //Actions a=new Actions(object);
        		// a.sendKeys(Keys.ENTER).build().perform();
        		 Thread.sleep(2000);
        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
        		 object.findElement(By.name("ddlDecisionStatus")).click();
        		 Thread.sleep(2000);
        		 WebElement decisionStatus=object.findElement(By.name("ddlDecisionStatus"));
        		 Select s1=new Select(decisionStatus);
        		 s1.selectByVisibleText("ALL");
        		 Thread.sleep(2000);
        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
        		 object.findElement(By.name("ddlApprovalStatus")).click();
        		 Thread.sleep(2000);
        		 WebElement approvalStatus=object.findElement(By.name("ddlApprovalStatus"));
        		 Select s2=new Select(approvalStatus);
        		 s2.selectByVisibleText("ALL");
        		 Thread.sleep(2000);
        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
        		 object.findElement(By.name("btnquery")).click();
        		 Thread.sleep(2000);
        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2019-1-11997/00']")));
        		 object.findElement(By.xpath("//*[text()='2019-1-11997/00']")).click();
        		 Thread.sleep(2000);
        		 ArrayList<String> winDows=new ArrayList<String>(object.getWindowHandles());
        		 object.switchTo().window(winDows.get(1));
        		 Thread.sleep(2000);
        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnDecision")));
        		 Thread.sleep(2000);
        		 object.close();
        		 Thread.sleep(2000);
        		 object.switchTo().window(winDows.get(0));
        		 Thread.sleep(2000);
    		 }
        		 try
        		 {
        			 Thread.sleep(1000);
        			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBroker")));
            		 object.findElement(By.name("ddlBroker")).sendKeys("TEST1");
            		 object.findElement(By.name("btnquery")).click();
            		 object.findElement(By.name("ddlDecisionStatus")).click();
        		 }
        		 catch(Exception et1)
        		 {
        			 Thread.sleep(1000);
        			 object.switchTo().alert().accept();
        			 Thread.sleep(2000);
        			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBroker")));
            		 object.findElement(By.name("ddlBroker")).click();
            		 Thread.sleep(2000);
            		 a.sendKeys(Keys.DOWN).build().perform();
            		 Thread.sleep(2000);
            		 a.sendKeys(Keys.ENTER).build().perform();
            		 Thread.sleep(3000);
            		 //Thread.sleep(2000);
            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
            		 object.findElement(By.name("ddlDecisionStatus")).click();
            		 Thread.sleep(2000);
            		 WebElement decisionStatus1=object.findElement(By.name("ddlDecisionStatus"));
            		 Select s11=new Select(decisionStatus1);
            		 s11.selectByVisibleText("ALL");
            		 Thread.sleep(2000);
            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
            		 object.findElement(By.name("ddlApprovalStatus")).click();
            		 Thread.sleep(2000);
            		 WebElement approvalStatus1=object.findElement(By.name("ddlApprovalStatus"));
            		 Select s12=new Select(approvalStatus1);
            		 s12.selectByVisibleText("PENDING");
            		 Thread.sleep(2000);
            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
            		 object.findElement(By.name("btnquery")).click();
            		 Thread.sleep(2000);
            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2019-1-11997/00']")));
            		 object.findElement(By.xpath("//*[text()='2019-1-11997/00']")).click();
            		 Thread.sleep(2000);
            		 ArrayList<String> winDows1=new ArrayList<String>(object.getWindowHandles());
            		 object.switchTo().window(winDows1.get(1));
            		 Thread.sleep(2000);
            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnDecision")));
            		 Thread.sleep(2000);
            		 object.close();
            		 Thread.sleep(2000);
            		 object.switchTo().window(winDows1.get(0));
            		 Thread.sleep(2000);
        		 }
            		 try
            		 {
            			 Thread.sleep(1000);
            			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBroker")));
                		 object.findElement(By.name("ddlBroker")).sendKeys("TEST1");
                		 object.findElement(By.name("btnquery")).click();
                		 object.findElement(By.name("ddlDecisionStatus")).click();
            		 }
            		 catch(Exception et2)
            		 {
            			 Thread.sleep(1000);
            			 object.switchTo().alert().accept();
            			 Thread.sleep(2000);
            			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBroker")));
                		 object.findElement(By.name("ddlBroker")).click();
                		 Thread.sleep(2000);
                		 a.sendKeys(Keys.DOWN).build().perform();
                		 Thread.sleep(2000);
                		 a.sendKeys(Keys.ENTER).build().perform();
                		 Thread.sleep(3000);
                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                		 object.findElement(By.name("ddlDecisionStatus")).click();
                		 Thread.sleep(2000);
                		 WebElement decisionStatus12=object.findElement(By.name("ddlDecisionStatus"));
                		 Select s01=new Select(decisionStatus12);
                		 s01.selectByVisibleText("ALL");
                		 Thread.sleep(2000);
                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                		 object.findElement(By.name("ddlApprovalStatus")).click();
                		 Thread.sleep(2000);
                		 WebElement approvalStatus12=object.findElement(By.name("ddlApprovalStatus"));
                		 Select s10=new Select(approvalStatus12);
                		 s10.selectByVisibleText("APPROVED");
                		 Thread.sleep(2000);
                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                		 object.findElement(By.name("btnquery")).click();
                		 Thread.sleep(2000);
                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2018-5-11808/00']")));
                		 object.findElement(By.xpath("//*[text()='2018-5-11808/00']")).click();
                		 Thread.sleep(2000);
                		 ArrayList<String> winDows2=new ArrayList<String>(object.getWindowHandles());
                		 object.switchTo().window(winDows2.get(1));
                		 Thread.sleep(2000);
                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnDecision")));
                		 Thread.sleep(2000);
                		 object.close();
                		 Thread.sleep(2000);
                		 object.switchTo().window(winDows2.get(0));
                		 Thread.sleep(2000);
            		 }
                		 try
                		 {
                			 Thread.sleep(1000);
                			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBroker")));
                    		 object.findElement(By.name("ddlBroker")).sendKeys("TEST1");
                    		 object.findElement(By.name("btnquery")).click();
                    		 object.findElement(By.name("ddlDecisionStatus")).click();
                		 }
                		 catch(Exception et3)
                		 {
                			 Thread.sleep(1000);
                			 object.switchTo().alert().accept();
                			 Thread.sleep(2000);
                			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBroker")));
                    		 object.findElement(By.name("ddlBroker")).click();
                    		 Thread.sleep(2000);
                    		 a.sendKeys(Keys.DOWN).build().perform();
                    		 Thread.sleep(2000);
                    		 a.sendKeys(Keys.ENTER).build().perform();
                    		 Thread.sleep(3000);
                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                    		 object.findElement(By.name("ddlDecisionStatus")).click();
                    		 Thread.sleep(2000);
                    		 WebElement decisionStatus13=object.findElement(By.name("ddlDecisionStatus"));
                    		 Select s02=new Select(decisionStatus13);
                    		 s02.selectByVisibleText("ALL");
                    		 Thread.sleep(2000);
                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                    		 object.findElement(By.name("ddlApprovalStatus")).click();
                    		 Thread.sleep(2000);
                    		 WebElement approvalStatus02=object.findElement(By.name("ddlApprovalStatus"));
                    		 Select s03=new Select(approvalStatus02);
                    		 s03.selectByVisibleText("CLOSED");
                    		 Thread.sleep(2000);
                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                    		 object.findElement(By.name("btnquery")).click();
                    		 Thread.sleep(2000);
                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2018-2-09237/01']")));
                    		 object.findElement(By.xpath("//*[text()='2018-2-09237/01']")).click();
                    		 Thread.sleep(2000);
                    		 ArrayList<String> winDows3=new ArrayList<String>(object.getWindowHandles());
                    		 object.switchTo().window(winDows3.get(1));
                    		 Thread.sleep(2000);
                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnDecision")));
                    		 Thread.sleep(2000);
                    		 object.close();
                    		 Thread.sleep(2000);
                    		 object.switchTo().window(winDows3.get(0));
                    		 Thread.sleep(2000);
                		 }
                    		 try
                    		 {
                    			 Thread.sleep(1000);
                    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBroker")));
                        		 object.findElement(By.name("ddlBroker")).sendKeys("TEST1");
                        		 object.findElement(By.name("btnquery")).click();
                        		 object.findElement(By.name("ddlDecisionStatus")).click();
                    		 }
                    		 catch(Exception et4)
                    		 {
                    			 Thread.sleep(1000);
                    			 object.switchTo().alert().accept();
                    			 Thread.sleep(2000);
                    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBroker")));
                        		 object.findElement(By.name("ddlBroker")).click();
                        		 Thread.sleep(2000);
                        		 a.sendKeys(Keys.DOWN).build().perform();
                        		 Thread.sleep(2000);
                        		 a.sendKeys(Keys.ENTER).build().perform();
                        		 Thread.sleep(3000);
                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                        		 object.findElement(By.name("ddlDecisionStatus")).click();
                        		 Thread.sleep(2000);
                        		 WebElement decisionStatus33=object.findElement(By.name("ddlDecisionStatus"));
                        		 Select s22=new Select(decisionStatus33);
                        		 s22.selectByVisibleText("ALL");
                        		 Thread.sleep(2000);
                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                        		 object.findElement(By.name("ddlApprovalStatus")).click();
                        		 Thread.sleep(2000);
                        		 WebElement approvalStatus72=object.findElement(By.name("ddlApprovalStatus"));
                        		 Select s73=new Select(approvalStatus72);
                        		 s73.selectByVisibleText("CLOSED W/O APPROVAL");
                        		 Thread.sleep(2000);
                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                        		 object.findElement(By.name("btnquery")).click();
                        		 Thread.sleep(2000);
                        		 /*wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2019-3-12116/00']")));
                        		 object.findElement(By.xpath("//*[text()='2019-3-12116/00']")).click();
                        		 Thread.sleep(2000);*/
                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Records not Found !...']")));
                        		 if(object.findElement(By.xpath("//*[text()='Records not Found !...']")).isDisplayed())
                        		 {
                        			 System.out.println("Records not found");
                        		 }
                        		 Thread.sleep(2000);
                    		 }
                        		 try
                        		 {
                        			 Thread.sleep(1000);
                        			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBroker")));
                            		 object.findElement(By.name("ddlBroker")).sendKeys("TEST1");
                            		 object.findElement(By.name("btnquery")).click();
                            		 object.findElement(By.name("ddlDecisionStatus")).click();
                        		 }
                        		 catch(Exception et5)
                        		 {
                        			 Thread.sleep(1000);
                        			 object.switchTo().alert().accept();
                        			 Thread.sleep(2000);
                        			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBroker")));
                            		 object.findElement(By.name("ddlBroker")).click();
                            		 Thread.sleep(2000);
                            		 a.sendKeys(Keys.DOWN).build().perform();
                            		 Thread.sleep(2000);
                            		 a.sendKeys(Keys.ENTER).build().perform();
                            		 Thread.sleep(3000);
                            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                            		 object.findElement(By.name("ddlDecisionStatus")).click();
                            		 Thread.sleep(2000);
                            		 WebElement decisionStatus43=object.findElement(By.name("ddlDecisionStatus"));
                            		 Select s42=new Select(decisionStatus43);
                            		 s42.selectByVisibleText("ALL");
                            		 Thread.sleep(2000);
                            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                            		 object.findElement(By.name("ddlApprovalStatus")).click();
                            		 Thread.sleep(2000);
                            		 WebElement approvalStatus42=object.findElement(By.name("ddlApprovalStatus"));
                            		 Select s43=new Select(approvalStatus42);
                            		 s43.selectByVisibleText("CONVERTED TO POLICY");
                            		 Thread.sleep(2000);
                            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                            		 object.findElement(By.name("btnquery")).click();
                            		 Thread.sleep(2000);
                            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2018-5-10608/04']")));
                            		 object.findElement(By.xpath("//*[text()='2018-5-10608/04']")).click();
                            		// Thread.sleep(2000);
                            		 Thread.sleep(2000);
                            		 ArrayList<String> winDows4=new ArrayList<String>(object.getWindowHandles());
                            		 object.switchTo().window(winDows4.get(1));
                            		 Thread.sleep(2000);
                            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnDecision")));
                            		 Thread.sleep(2000);
                            		 object.close();
                            		 Thread.sleep(2000);
                            		 object.switchTo().window(winDows4.get(0));
                            		 //Thread.sleep(2000);
                            		 //Thread.sleep(2000);
                        		 }
                            		 
                                		 Thread.sleep(2000);
                                		 try
                                		 {
                                			 Thread.sleep(1000);
                                			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBroker")));
                                    		 object.findElement(By.name("ddlBroker")).sendKeys("TEST1");
                                    		 object.findElement(By.name("btnquery")).click();
                                    		 object.findElement(By.name("ddlDecisionStatus")).click();
                                		 }
                                		 catch(Exception et7)
                                		 {
                                			 Thread.sleep(1000);
                                			 object.switchTo().alert().accept();
                                			 Thread.sleep(2000);
                                			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBroker")));
                                    		 object.findElement(By.name("ddlBroker")).click();
                                    		 Thread.sleep(2000);
                                    		 a.sendKeys(Keys.DOWN).build().perform();
                                    		 Thread.sleep(2000);
                                    		 a.sendKeys(Keys.ENTER).build().perform();
                                    		 Thread.sleep(3000);
                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                                    		 object.findElement(By.name("ddlDecisionStatus")).click();
                                    		 Thread.sleep(2000);
                                    		 WebElement decisionStatus2=object.findElement(By.name("ddlDecisionStatus"));
                                    		 Select s3=new Select(decisionStatus2);
                                    		 s3.selectByVisibleText("PENDING");
                                    		 Thread.sleep(2000);
                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                                    		 object.findElement(By.name("ddlApprovalStatus")).click();
                                    		 Thread.sleep(2000);
                                    		 WebElement approvalStatus2=object.findElement(By.name("ddlApprovalStatus"));
                                    		 Select s4=new Select(approvalStatus2);
                                    		 s4.selectByVisibleText("ALL");
                                    		 Thread.sleep(2000);
                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                                    		 object.findElement(By.name("btnquery")).click();
                                    		 Thread.sleep(2000);
                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2019-1-11997/00']")));
                                    		 object.findElement(By.xpath("//*[text()='2019-1-11997/00']")).click();
                                    		 Thread.sleep(2000);
                                    		 ArrayList<String> windows4=new ArrayList<String>(object.getWindowHandles());
                                    		 object.switchTo().window(windows4.get(1));
                                    		 Thread.sleep(2000);
                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnDecision")));
                                    		 Thread.sleep(2000);
                                    		 object.close();
                                    		 Thread.sleep(2000);
                                    		 object.switchTo().window(windows4.get(0));
                                    		 Thread.sleep(2000);
                                		 }
                                    		 try
                                    		 {
                                    			 Thread.sleep(1000);
                                    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBroker")));
                                        		 object.findElement(By.name("ddlBroker")).sendKeys("TEST1");
                                        		 object.findElement(By.name("btnquery")).click();
                                        		 object.findElement(By.name("ddlDecisionStatus")).click();
                                    		 }
                                    		 catch(Exception et8)
                                    		 {
                                    			 Thread.sleep(1000);
                                    			 object.switchTo().alert().accept();
                                    			 Thread.sleep(2000);
                                    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBroker")));
                                        		 object.findElement(By.name("ddlBroker")).click();
                                        		 Thread.sleep(2000);
                                        		 a.sendKeys(Keys.DOWN).build().perform();
                                        		 Thread.sleep(2000);
                                        		 a.sendKeys(Keys.ENTER).build().perform();
                                        		 Thread.sleep(3000);
                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                                        		 object.findElement(By.name("ddlDecisionStatus")).click();
                                        		 Thread.sleep(2000);
                                        		 WebElement decisionstatus1=object.findElement(By.name("ddlDecisionStatus"));
                                        		 Select t1=new Select(decisionstatus1);
                                        		 t1.selectByVisibleText("PENDING");
                                        		 Thread.sleep(2000);
                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                                        		 object.findElement(By.name("ddlApprovalStatus")).click();
                                        		 Thread.sleep(2000);
                                        		 WebElement approvalstatus1=object.findElement(By.name("ddlApprovalStatus"));
                                        		 Select t12=new Select(approvalstatus1);
                                        		 t12.selectByVisibleText("PENDING");
                                        		 Thread.sleep(2000);
                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                                        		 object.findElement(By.name("btnquery")).click();
                                        		 Thread.sleep(2000);
                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2019-1-11997/00']")));
                                        		 object.findElement(By.xpath("//*[text()='2019-1-11997/00']")).click();
                                        		 Thread.sleep(2000);
                                        		 ArrayList<String> Windows4=new ArrayList<String>(object.getWindowHandles());
                                        		 object.switchTo().window(Windows4.get(1));
                                        		 Thread.sleep(2000);
                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnDecision")));
                                        		 Thread.sleep(2000);
                                        		 object.close();
                                        		 Thread.sleep(2000);
                                        		 object.switchTo().window(Windows4.get(0));
                                        		 Thread.sleep(2000);
                                    		 }
                                        		 try
                                        		 {
                                        			 Thread.sleep(1000);
                                        			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBroker")));
                                            		 object.findElement(By.name("ddlBroker")).sendKeys("TEST1");
                                            		 object.findElement(By.name("btnquery")).click();
                                            		 object.findElement(By.name("ddlDecisionStatus")).click();
                                        		 }
                                        		 catch(Exception ew1)
                                        		 {
                                        			 Thread.sleep(1000);
                                        			 object.switchTo().alert().accept();
                                        			 Thread.sleep(2000);
                                        			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBroker")));
                                            		 object.findElement(By.name("ddlBroker")).click();
                                            		 Thread.sleep(2000);
                                            		 a.sendKeys(Keys.DOWN).build().perform();
                                            		 Thread.sleep(2000);
                                            		 a.sendKeys(Keys.ENTER).build().perform();
                                            		 Thread.sleep(3000);
                                            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                                            		 object.findElement(By.name("ddlDecisionStatus")).click();
                                            		 Thread.sleep(2000);
                                            		 WebElement decisionstatus12=object.findElement(By.name("ddlDecisionStatus"));
                                            		 Select t01=new Select(decisionstatus12);
                                            		 t01.selectByVisibleText("PENDING");
                                            		 Thread.sleep(2000);
                                            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                                            		 object.findElement(By.name("ddlApprovalStatus")).click();
                                            		 Thread.sleep(2000);
                                            		 WebElement approvalstatus12=object.findElement(By.name("ddlApprovalStatus"));
                                            		 Select t10=new Select(approvalstatus12);
                                            		 t10.selectByVisibleText("APPROVED");
                                            		 Thread.sleep(2000);
                                            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                                            		 object.findElement(By.name("btnquery")).click();
                                            		 Thread.sleep(2000);
                                            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Records not Found !...']")));
                                            		 if(object.findElement(By.xpath("//*[text()='Records not Found !...']")).isDisplayed())
                                            		 {
                                            			 System.out.println("Records not found");
                                            		 }
                                            		 Thread.sleep(2000);
                                        		 }
                                            		 try
                                            		 {
                                            			 Thread.sleep(1000);
                                            			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBroker")));
                                                		 object.findElement(By.name("ddlBroker")).sendKeys("TEST1");
                                                		 object.findElement(By.name("btnquery")).click();
                                                		 object.findElement(By.name("ddlDecisionStatus")).click();
                                            		 }
                                            		 catch(Exception ew5)
                                            		 {
                                            			 Thread.sleep(1000);
                                            			 object.switchTo().alert().accept();
                                            			 Thread.sleep(2000);
                                            			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBroker")));
                                                		 object.findElement(By.name("ddlBroker")).click();
                                                		 Thread.sleep(2000);
                                                		 a.sendKeys(Keys.DOWN).build().perform();
                                                		 Thread.sleep(2000);
                                                		 a.sendKeys(Keys.ENTER).build().perform();
                                                		 Thread.sleep(3000);
                                                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                                                		 object.findElement(By.name("ddlDecisionStatus")).click();
                                                		 Thread.sleep(2000);
                                                		 WebElement decisionstatus13=object.findElement(By.name("ddlDecisionStatus"));
                                                		 Select r02=new Select(decisionstatus13);
                                                		 r02.selectByVisibleText("PENDING");
                                                		 Thread.sleep(2000);
                                                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                                                		 object.findElement(By.name("ddlApprovalStatus")).click();
                                                		 Thread.sleep(2000);
                                                		 WebElement approvalstatus02=object.findElement(By.name("ddlApprovalStatus"));
                                                		 Select t03=new Select(approvalstatus02);
                                                		 t03.selectByVisibleText("CLOSED");
                                                		 Thread.sleep(2000);
                                                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                                                		 object.findElement(By.name("btnquery")).click();
                                                		 Thread.sleep(2000);
                                                		 
                                                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Records not Found !...']")));
                                                		 if(object.findElement(By.xpath("//*[text()='Records not Found !...']")).isDisplayed())
                                                		 {
                                                			 System.out.println("Records not found");
                                                		 }
                                                		 Thread.sleep(2000);
                                            		 }
                                                		 try
                                                		 {
                                                			 Thread.sleep(1000);
                                                			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBroker")));
                                                    		 object.findElement(By.name("ddlBroker")).sendKeys("TEST1");
                                                    		 object.findElement(By.name("btnquery")).click();
                                                    		 object.findElement(By.name("ddlDecisionStatus")).click();
                                                		 }
                                                		 catch(Exception ew4)
                                                		 {
                                                			 Thread.sleep(1000);
                                                			 object.switchTo().alert().accept();
                                                			 Thread.sleep(2000);
                                                			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBroker")));
                                                    		 object.findElement(By.name("ddlBroker")).click();
                                                    		 Thread.sleep(2000);
                                                    		 a.sendKeys(Keys.DOWN).build().perform();
                                                    		 Thread.sleep(2000);
                                                    		 a.sendKeys(Keys.ENTER).build().perform();
                                                    		 Thread.sleep(3000);
                                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                                                    		 object.findElement(By.name("ddlDecisionStatus")).click();
                                                    		 Thread.sleep(2000);
                                                    		 WebElement decisionstatus33=object.findElement(By.name("ddlDecisionStatus"));
                                                    		 Select t22=new Select(decisionstatus33);
                                                    		 t22.selectByVisibleText("PENDING");
                                                    		 Thread.sleep(2000);
                                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                                                    		 object.findElement(By.name("ddlApprovalStatus")).click();
                                                    		 Thread.sleep(2000);
                                                    		 WebElement approvalstatus72=object.findElement(By.name("ddlApprovalStatus"));
                                                    		 Select t73=new Select(approvalstatus72);
                                                    		 t73.selectByVisibleText("CLOSED W/O APPROVAL");
                                                    		 Thread.sleep(2000);
                                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                                                    		 object.findElement(By.name("btnquery")).click();
                                                    		 Thread.sleep(2000);
                                                    		 /*wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2019-3-12116/00']")));
                                                    		 object.findElement(By.xpath("//*[text()='2019-3-12116/00']")).click();
                                                    		 Thread.sleep(2000);*/
                                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Records not Found !...']")));
                                                    		 if(object.findElement(By.xpath("//*[text()='Records not Found !...']")).isDisplayed())
                                                    		 {
                                                    			 System.out.println("Records not found");
                                                    		 }
                                                    		 Thread.sleep(2000);
                                                		 }
                                                    		 try
                                                    		 {
                                                    			 Thread.sleep(1000);
                                                    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBroker")));
                                                        		 object.findElement(By.name("ddlBroker")).sendKeys("TEST1");
                                                        		 object.findElement(By.name("btnquery")).click();
                                                        		 object.findElement(By.name("ddlDecisionStatus")).click();
                                                    		 }
                                                    		 catch(Exception et10)
                                                    		 {
                                                    			 Thread.sleep(1000);
                                                    			 object.switchTo().alert().accept();
                                                    			 Thread.sleep(2000);
                                                    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlBroker")));
                                                        		 object.findElement(By.name("ddlBroker")).click();
                                                        		 Thread.sleep(2000);
                                                        		 a.sendKeys(Keys.DOWN).build().perform();
                                                        		 Thread.sleep(2000);
                                                        		 a.sendKeys(Keys.ENTER).build().perform();
                                                        		 Thread.sleep(3000);
                                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                                                        		 object.findElement(By.name("ddlDecisionStatus")).click();
                                                        		 Thread.sleep(2000);
                                                        		 WebElement decisionstatus43=object.findElement(By.name("ddlDecisionStatus"));
                                                        		 Select t42=new Select(decisionstatus43);
                                                        		 t42.selectByVisibleText("PENDING");
                                                        		 Thread.sleep(2000);
                                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                                                        		 object.findElement(By.name("ddlApprovalStatus")).click();
                                                        		 Thread.sleep(2000);
                                                        		 WebElement approvalstatus42=object.findElement(By.name("ddlApprovalStatus"));
                                                        		 Select t43=new Select(approvalstatus42);
                                                        		 t43.selectByVisibleText("CONVERTED TO POLICY");
                                                        		 Thread.sleep(2000);
                                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                                                        		 object.findElement(By.name("btnquery")).click();
                                                        		 Thread.sleep(2000);
                                                        		/* wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2019-3-12116/00']")));
                                                        		 object.findElement(By.xpath("//*[text()='2019-3-12116/00']")).click();
                                                        		 Thread.sleep(2000);*/
                                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Records not Found !...']")));
                                                        		 if(object.findElement(By.xpath("//*[text()='Records not Found !...']")).isDisplayed())
                                                        		 {
                                                        			 System.out.println("Records not found");
                                                        		 }
                                                        		 Thread.sleep(2000);
                                                    		 }
    		 Thread.sleep(2000);
    		 try
    		 {
    			 Thread.sleep(1000);
    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
        		 object.findElement(By.name("ddlQuotationNo")).sendKeys("TEST1");
        		 object.findElement(By.name("btnquery")).click();
        		 object.findElement(By.name("ddlDecisionStatus")).click();
    		 }
    		 catch(Exception et)
    		 {
    			 Thread.sleep(1000);
    			 object.switchTo().alert().accept();
    			 Thread.sleep(2000);
    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
        		 object.findElement(By.name("ddlQuotationNo")).click();
        		 Thread.sleep(2000);
        		 a.sendKeys(Keys.DOWN).build().perform();
        		 Thread.sleep(2000);
        		 a.sendKeys(Keys.ENTER).build().perform();
        		 Thread.sleep(3000);
        		 //Actions a=new Actions(object);
        		 //a.sendKeys(Keys.ENTER).build().perform();
        		 Thread.sleep(2000);
        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
        		 object.findElement(By.name("ddlDecisionStatus")).click();
        		 Thread.sleep(2000);
        		 WebElement decisionStatus=object.findElement(By.name("ddlDecisionStatus"));
        		 Select s1=new Select(decisionStatus);
        		 s1.selectByVisibleText("ALL");
        		 Thread.sleep(2000);
        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
        		 object.findElement(By.name("ddlApprovalStatus")).click();
        		 Thread.sleep(2000);
        		 WebElement approvalStatus=object.findElement(By.name("ddlApprovalStatus"));
        		 Select s2=new Select(approvalStatus);
        		 s2.selectByVisibleText("ALL");
        		 Thread.sleep(2000);
        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
        		 object.findElement(By.name("btnquery")).click();
        		 Thread.sleep(2000);
        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2019-3-12124/00']")));
        		 object.findElement(By.xpath("//*[text()='2019-3-12124/00']")).click();
        		 Thread.sleep(2000);
        		 ArrayList<String> winDows=new ArrayList<String>(object.getWindowHandles());
        		 object.switchTo().window(winDows.get(1));
        		 Thread.sleep(2000);
        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnDecision")));
        		 Thread.sleep(2000);
        		 object.close();
        		 Thread.sleep(2000);
        		 object.switchTo().window(winDows.get(0));
        		 Thread.sleep(2000);
    		 }
        		 try
        		 {
        			 Thread.sleep(1000);
        			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
            		 object.findElement(By.name("ddlQuotationNo")).sendKeys("TEST1");
            		 object.findElement(By.name("btnquery")).click();
            		 object.findElement(By.name("ddlDecisionStatus")).click();
        		 }
        		 catch(Exception et1)
        		 {
        			 Thread.sleep(1000);
        			 object.switchTo().alert().accept();
        			 Thread.sleep(2000);
        			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
            		 object.findElement(By.name("ddlQuotationNo")).click();
            		 Thread.sleep(2000);
            		 a.sendKeys(Keys.DOWN).build().perform();
            		 Thread.sleep(2000);
            		 a.sendKeys(Keys.ENTER).build().perform();
            		 Thread.sleep(3000);
            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
            		 object.findElement(By.name("ddlDecisionStatus")).click();
            		 Thread.sleep(2000);
            		 WebElement decisionStatus1=object.findElement(By.name("ddlDecisionStatus"));
            		 Select s11=new Select(decisionStatus1);
            		 s11.selectByVisibleText("ALL");
            		 Thread.sleep(2000);
            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
            		 object.findElement(By.name("ddlApprovalStatus")).click();
            		 Thread.sleep(2000);
            		 WebElement approvalStatus1=object.findElement(By.name("ddlApprovalStatus"));
            		 Select s12=new Select(approvalStatus1);
            		 s12.selectByVisibleText("PENDING");
            		 Thread.sleep(2000);
            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
            		 object.findElement(By.name("btnquery")).click();
            		 Thread.sleep(2000);
            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2019-3-12124/00']")));
            		 object.findElement(By.xpath("//*[text()='2019-3-12124/00']")).click();
            		 Thread.sleep(2000);
            		 ArrayList<String> winDows1=new ArrayList<String>(object.getWindowHandles());
            		 object.switchTo().window(winDows1.get(1));
            		 Thread.sleep(2000);
            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnDecision")));
            		 Thread.sleep(2000);
            		 object.close();
            		 Thread.sleep(2000);
            		 object.switchTo().window(winDows1.get(0));
            		 Thread.sleep(2000);
        		 }
            		 try
            		 {
            			 Thread.sleep(1000);
            			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
                		 object.findElement(By.name("ddlQuotationNo")).sendKeys("TEST1");
                		 object.findElement(By.name("btnquery")).click();
                		 object.findElement(By.name("ddlDecisionStatus")).click();
            		 }
            		 catch(Exception et2)
            		 {
            			 Thread.sleep(1000);
            			 object.switchTo().alert().accept();
            			 Thread.sleep(2000);
            			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
                		 object.findElement(By.name("ddlQuotationNo")).click();
                		 Thread.sleep(2000);
                		 a.sendKeys(Keys.DOWN).build().perform();
                		 Thread.sleep(2000);
                		 a.sendKeys(Keys.ENTER).build().perform();
                		 Thread.sleep(3000);
                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                		 object.findElement(By.name("ddlDecisionStatus")).click();
                		 Thread.sleep(2000);
                		 WebElement decisionStatus12=object.findElement(By.name("ddlDecisionStatus"));
                		 Select s01=new Select(decisionStatus12);
                		 s01.selectByVisibleText("ALL");
                		 Thread.sleep(2000);
                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                		 object.findElement(By.name("ddlApprovalStatus")).click();
                		 Thread.sleep(2000);
                		 WebElement approvalStatus12=object.findElement(By.name("ddlApprovalStatus"));
                		 Select s10=new Select(approvalStatus12);
                		 s10.selectByVisibleText("APPROVED");
                		 Thread.sleep(2000);
                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                		 object.findElement(By.name("btnquery")).click();
                		 Thread.sleep(2000);
                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Records not Found !...']")));
                		 if(object.findElement(By.xpath("//*[text()='Records not Found !...']")).isDisplayed())
                		 {
                			 System.out.println("Records not found");
                		 }
                		 Thread.sleep(2000);
            		 }
                		 
                    		 try
                    		 {
                    			 Thread.sleep(1000);
                    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
                        		 object.findElement(By.name("ddlQuotationNo")).sendKeys("TEST1");
                        		 object.findElement(By.name("btnquery")).click();
                        		 object.findElement(By.name("ddlDecisionStatus")).click();
                    		 }
                    		 catch(Exception et4)
                    		 {
                    			 Thread.sleep(1000);
                    			 object.switchTo().alert().accept();
                    			 Thread.sleep(2000);
                    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
                        		 object.findElement(By.name("ddlQuotationNo")).click();
                        		 Thread.sleep(2000);
                        		 a.sendKeys(Keys.DOWN).build().perform();
                        		 Thread.sleep(2000);
                        		 a.sendKeys(Keys.ENTER).build().perform();
                        		 Thread.sleep(3000);
                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                        		 object.findElement(By.name("ddlDecisionStatus")).click();
                        		 Thread.sleep(2000);
                        		 WebElement decisionStatus33=object.findElement(By.name("ddlDecisionStatus"));
                        		 Select s22=new Select(decisionStatus33);
                        		 s22.selectByVisibleText("ALL");
                        		 Thread.sleep(2000);
                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                        		 object.findElement(By.name("ddlApprovalStatus")).click();
                        		 Thread.sleep(2000);
                        		 WebElement approvalStatus72=object.findElement(By.name("ddlApprovalStatus"));
                        		 Select s73=new Select(approvalStatus72);
                        		 s73.selectByVisibleText("CLOSED");
                        		 Thread.sleep(2000);
                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                        		 object.findElement(By.name("btnquery")).click();
                        		 Thread.sleep(2000);
                        		/* wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2019-3-12116/00']")));
                        		 object.findElement(By.xpath("//*[text()='2019-3-12116/00']")).click();
                        		 Thread.sleep(2000);*/
                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Records not Found !...']")));
                        		 if(object.findElement(By.xpath("//*[text()='Records not Found !...']")).isDisplayed())
                        		 {
                        			 System.out.println("Records not found");
                        		 }
                        		 Thread.sleep(2000);
                    		 }
                        		 try
                        		 {
                        			 Thread.sleep(1000);
                        			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
                            		 object.findElement(By.name("ddlQuotationNo")).sendKeys("TEST1");
                            		 object.findElement(By.name("btnquery")).click();
                            		 object.findElement(By.name("ddlDecisionStatus")).click();
                        		 }
                        		 catch(Exception et5)
                        		 {
                        			 Thread.sleep(1000);
                        			 object.switchTo().alert().accept();
                        			 Thread.sleep(2000);
                        			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
                            		 object.findElement(By.name("ddlQuotationNo")).click();
                            		 Thread.sleep(2000);
                            		 a.sendKeys(Keys.DOWN).build().perform();
                            		 Thread.sleep(2000);
                            		 a.sendKeys(Keys.ENTER).build().perform();
                            		 Thread.sleep(3000);
                            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                            		 object.findElement(By.name("ddlDecisionStatus")).click();
                            		 Thread.sleep(2000);
                            		 WebElement decisionStatus43=object.findElement(By.name("ddlDecisionStatus"));
                            		 Select s42=new Select(decisionStatus43);
                            		 s42.selectByVisibleText("ALL");
                            		 Thread.sleep(2000);
                            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                            		 object.findElement(By.name("ddlApprovalStatus")).click();
                            		 Thread.sleep(2000);
                            		 WebElement approvalStatus42=object.findElement(By.name("ddlApprovalStatus"));
                            		 Select s43=new Select(approvalStatus42);
                            		 s43.selectByVisibleText("CLOSED W/O APPROVAL");
                            		 Thread.sleep(2000);
                            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                            		 object.findElement(By.name("btnquery")).click();
                            		 Thread.sleep(2000);
                            		 /*wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2019-3-12116/00']")));
                            		 object.findElement(By.xpath("//*[text()='2019-3-12116/00']")).click();
                            		 Thread.sleep(2000);*/
                            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Records not Found !...']")));
                            		 if(object.findElement(By.xpath("//*[text()='Records not Found !...']")).isDisplayed())
                            		 {
                            			 System.out.println("Records not found");
                            		 }
                            		 Thread.sleep(2000);
                        		 }
                            		 try
                            		 {
                            			 Thread.sleep(1000);
                            			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
                                		 object.findElement(By.name("ddlQuotationNo")).sendKeys("TEST1");
                                		 object.findElement(By.name("btnquery")).click();
                                		 object.findElement(By.name("ddlDecisionStatus")).click();
                            		 }
                            		 catch(Exception et6)
                            		 {
                            			 Thread.sleep(1000);
                            			 object.switchTo().alert().accept();
                            			 Thread.sleep(2000);
                            			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
                                		 object.findElement(By.name("ddlQuotationNo")).click();
                                		 Thread.sleep(2000);
                                		 a.sendKeys(Keys.DOWN).build().perform();
                                		 Thread.sleep(2000);
                                		 a.sendKeys(Keys.ENTER).build().perform();
                                		 Thread.sleep(3000);
                                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                                		 object.findElement(By.name("ddlDecisionStatus")).click();
                                		 Thread.sleep(2000);
                                		 WebElement decisionStatus23=object.findElement(By.name("ddlDecisionStatus"));
                                		 Select s52=new Select(decisionStatus23);
                                		 s52.selectByVisibleText("ALL");
                                		 Thread.sleep(2000);
                                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                                		 object.findElement(By.name("ddlApprovalStatus")).click();
                                		 Thread.sleep(2000);
                                		 WebElement approvalStatus82=object.findElement(By.name("ddlApprovalStatus"));
                                		 Select s83=new Select(approvalStatus82);
                                		 s83.selectByVisibleText("CONVERTED TO POLICY");
                                		 Thread.sleep(2000);
                                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                                		 object.findElement(By.name("btnquery")).click();
                                		 Thread.sleep(2000);
                                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Records not Found !...']")));
                                		 if(object.findElement(By.xpath("//*[text()='Records not Found !...']")).isDisplayed())
                                		 {
                                			 System.out.println("Records not found");
                                		 }
                                		// Thread.sleep(2000);
                                		 Thread.sleep(2000);
                            		 }
                                		 try
                                		 {
                                			 Thread.sleep(1000);
                                			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
                                    		 object.findElement(By.name("ddlQuotationNo")).sendKeys("TEST1");
                                    		 object.findElement(By.name("btnquery")).click();
                                    		 object.findElement(By.name("ddlDecisionStatus")).click();
                                		 }
                                		 catch(Exception et7)
                                		 {
                                			 Thread.sleep(1000);
                                			 object.switchTo().alert().accept();
                                			 Thread.sleep(2000);
                                			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
                                    		 object.findElement(By.name("ddlQuotationNo")).click();
                                    		 Thread.sleep(2000);
                                    		 a.sendKeys(Keys.DOWN).build().perform();
                                    		 Thread.sleep(2000);
                                    		 a.sendKeys(Keys.ENTER).build().perform();
                                    		 Thread.sleep(3000);
                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                                    		 object.findElement(By.name("ddlDecisionStatus")).click();
                                    		 Thread.sleep(2000);
                                    		 WebElement decisionStatus2=object.findElement(By.name("ddlDecisionStatus"));
                                    		 Select s3=new Select(decisionStatus2);
                                    		 s3.selectByVisibleText("PENDING");
                                    		 Thread.sleep(2000);
                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                                    		 object.findElement(By.name("ddlApprovalStatus")).click();
                                    		 Thread.sleep(2000);
                                    		 WebElement approvalStatus2=object.findElement(By.name("ddlApprovalStatus"));
                                    		 Select s4=new Select(approvalStatus2);
                                    		 s4.selectByVisibleText("ALL");
                                    		 Thread.sleep(2000);
                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                                    		 object.findElement(By.name("btnquery")).click();
                                    		 Thread.sleep(2000);
                                    		 /*wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2019-3-12116/00']")));
                                    		 object.findElement(By.xpath("//*[text()='2019-3-12116/00']")).click();
                                    		 Thread.sleep(2000);*/
                                    		 Thread.sleep(2000);
                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2019-3-12124/00']")));
                                    		 object.findElement(By.xpath("//*[text()='2019-3-12124/00']")).click();
                                    		 Thread.sleep(2000);
                                    		 ArrayList<String> winDows01=new ArrayList<String>(object.getWindowHandles());
                                    		 object.switchTo().window(winDows01.get(1));
                                    		 Thread.sleep(2000);
                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnDecision")));
                                    		 Thread.sleep(2000);
                                    		 object.close();
                                    		 Thread.sleep(2000);
                                    		 object.switchTo().window(winDows01.get(0));
                                    		 Thread.sleep(2000);
                                		 }
                                    		 try
                                    		 {
                                    			 Thread.sleep(1000);
                                    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
                                        		 object.findElement(By.name("ddlQuotationNo")).sendKeys("TEST1");
                                        		 object.findElement(By.name("btnquery")).click();
                                        		 object.findElement(By.name("ddlDecisionStatus")).click();
                                    		 }
                                    		 catch(Exception et8)
                                    		 {
                                    			 Thread.sleep(1000);
                                    			 object.switchTo().alert().accept();
                                    			 Thread.sleep(2000);
                                    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
                                        		 object.findElement(By.name("ddlQuotationNo")).click();
                                        		 Thread.sleep(2000);
                                        		 a.sendKeys(Keys.DOWN).build().perform();
                                        		 Thread.sleep(2000);
                                        		 a.sendKeys(Keys.ENTER).build().perform();
                                        		 Thread.sleep(3000);
                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                                        		 object.findElement(By.name("ddlDecisionStatus")).click();
                                        		 Thread.sleep(2000);
                                        		 WebElement decisionstatus1=object.findElement(By.name("ddlDecisionStatus"));
                                        		 Select t1=new Select(decisionstatus1);
                                        		 t1.selectByVisibleText("PENDING");
                                        		 Thread.sleep(2000);
                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                                        		 object.findElement(By.name("ddlApprovalStatus")).click();
                                        		 Thread.sleep(2000);
                                        		 WebElement approvalstatus1=object.findElement(By.name("ddlApprovalStatus"));
                                        		 Select t12=new Select(approvalstatus1);
                                        		 t12.selectByVisibleText("PENDING");
                                        		 Thread.sleep(2000);
                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                                        		 object.findElement(By.name("btnquery")).click();
                                        		// Thread.sleep(2000);
                                        		 Thread.sleep(2000);
                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2019-3-12124/00']")));
                                        		 object.findElement(By.xpath("//*[text()='2019-3-12124/00']")).click();
                                        		 Thread.sleep(2000);
                                        		 ArrayList<String> winDows02=new ArrayList<String>(object.getWindowHandles());
                                        		 object.switchTo().window(winDows02.get(1));
                                        		 Thread.sleep(2000);
                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnDecision")));
                                        		 Thread.sleep(2000);
                                        		 object.close();
                                        		 Thread.sleep(2000);
                                        		 object.switchTo().window(winDows02.get(0));
                                        		 Thread.sleep(2000);
                                    		 }
                                        		 try
                                        		 {
                                        			 Thread.sleep(1000);
                                        			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
                                            		 object.findElement(By.name("ddlQuotationNo")).sendKeys("TEST1");
                                            		 object.findElement(By.name("btnquery")).click();
                                            		 object.findElement(By.name("ddlDecisionStatus")).click();
                                        		 }
                                        		 catch(Exception ew1)
                                        		 {
                                        			 Thread.sleep(1000);
                                        			 object.switchTo().alert().accept();
                                        			 Thread.sleep(2000);
                                        			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
                                            		 object.findElement(By.name("ddlQuotationNo")).click();
                                            		 Thread.sleep(2000);
                                            		 a.sendKeys(Keys.DOWN).build().perform();
                                            		 Thread.sleep(2000);
                                            		 a.sendKeys(Keys.ENTER).build().perform();
                                            		 Thread.sleep(3000);
                                            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                                            		 object.findElement(By.name("ddlDecisionStatus")).click();
                                            		 Thread.sleep(2000);
                                            		 WebElement decisionstatus12=object.findElement(By.name("ddlDecisionStatus"));
                                            		 Select t01=new Select(decisionstatus12);
                                            		 t01.selectByVisibleText("PENDING");
                                            		 Thread.sleep(2000);
                                            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                                            		 object.findElement(By.name("ddlApprovalStatus")).click();
                                            		 Thread.sleep(2000);
                                            		 WebElement approvalstatus12=object.findElement(By.name("ddlApprovalStatus"));
                                            		 Select t10=new Select(approvalstatus12);
                                            		 t10.selectByVisibleText("APPROVED");
                                            		 Thread.sleep(2000);
                                            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                                            		 object.findElement(By.name("btnquery")).click();
                                            		 Thread.sleep(2000);
                                            		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Records not Found !...']")));
                                            		 if(object.findElement(By.xpath("//*[text()='Records not Found !...']")).isDisplayed())
                                            		 {
                                            			 System.out.println("Records not found");
                                            		 }
                                            		 Thread.sleep(2000);
                                        		 }
                                            		 try
                                            		 {
                                            			 Thread.sleep(1000);
                                            			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
                                                		 object.findElement(By.name("ddlQuotationNo")).sendKeys("TEST1");
                                                		 object.findElement(By.name("btnquery")).click();
                                                		 object.findElement(By.name("ddlDecisionStatus")).click();
                                            		 }
                                            		 catch(Exception ew5)
                                            		 {
                                            			 Thread.sleep(1000);
                                            			 object.switchTo().alert().accept();
                                            			 Thread.sleep(2000);
                                            			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
                                                		 object.findElement(By.name("ddlQuotationNo")).click();
                                                		 Thread.sleep(2000);
                                                		 a.sendKeys(Keys.DOWN).build().perform();
                                                		 Thread.sleep(2000);
                                                		 a.sendKeys(Keys.ENTER).build().perform();
                                                		 Thread.sleep(3000);
                                                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                                                		 object.findElement(By.name("ddlDecisionStatus")).click();
                                                		 Thread.sleep(2000);
                                                		 WebElement decisionstatus13=object.findElement(By.name("ddlDecisionStatus"));
                                                		 Select r02=new Select(decisionstatus13);
                                                		 r02.selectByVisibleText("PENDING");
                                                		 Thread.sleep(2000);
                                                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                                                		 object.findElement(By.name("ddlApprovalStatus")).click();
                                                		 Thread.sleep(2000);
                                                		 WebElement approvalstatus02=object.findElement(By.name("ddlApprovalStatus"));
                                                		 Select t03=new Select(approvalstatus02);
                                                		 t03.selectByVisibleText("CLOSED");
                                                		 Thread.sleep(2000);
                                                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                                                		 object.findElement(By.name("btnquery")).click();
                                                		 Thread.sleep(2000);
                                                		 
                                                		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Records not Found !...']")));
                                                		 if(object.findElement(By.xpath("//*[text()='Records not Found !...']")).isDisplayed())
                                                		 {
                                                			 System.out.println("Records not found");
                                                		 }
                                                		 Thread.sleep(2000);
                                            		 }
                                                		 try
                                                		 {
                                                			 Thread.sleep(1000);
                                                			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
                                                    		 object.findElement(By.name("ddlQuotationNo")).sendKeys("TEST1");
                                                    		 object.findElement(By.name("btnquery")).click();
                                                    		 object.findElement(By.name("ddlDecisionStatus")).click();
                                                		 }
                                                		 catch(Exception ew4)
                                                		 {
                                                			 Thread.sleep(1000);
                                                			 object.switchTo().alert().accept();
                                                			 Thread.sleep(2000);
                                                			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
                                                    		 object.findElement(By.name("ddlQuotationNo")).click();
                                                    		 Thread.sleep(2000);
                                                    		 a.sendKeys(Keys.DOWN).build().perform();
                                                    		 Thread.sleep(2000);
                                                    		 a.sendKeys(Keys.ENTER).build().perform();
                                                    		 Thread.sleep(3000);
                                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                                                    		 object.findElement(By.name("ddlDecisionStatus")).click();
                                                    		 Thread.sleep(2000);
                                                    		 WebElement decisionstatus33=object.findElement(By.name("ddlDecisionStatus"));
                                                    		 Select t22=new Select(decisionstatus33);
                                                    		 t22.selectByVisibleText("PENDING");
                                                    		 Thread.sleep(2000);
                                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                                                    		 object.findElement(By.name("ddlApprovalStatus")).click();
                                                    		 Thread.sleep(2000);
                                                    		 WebElement approvalstatus72=object.findElement(By.name("ddlApprovalStatus"));
                                                    		 Select t73=new Select(approvalstatus72);
                                                    		 t73.selectByVisibleText("CLOSED W/O APPROVAL");
                                                    		 Thread.sleep(2000);
                                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                                                    		 object.findElement(By.name("btnquery")).click();
                                                    		 Thread.sleep(2000);
                                                    		 /*wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2019-3-12116/00']")));
                                                    		 object.findElement(By.xpath("//*[text()='2019-3-12116/00']")).click();
                                                    		 Thread.sleep(2000);*/
                                                    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Records not Found !...']")));
                                                    		 if(object.findElement(By.xpath("//*[text()='Records not Found !...']")).isDisplayed())
                                                    		 {
                                                    			 System.out.println("Records not found");
                                                    		 }
                                                    		 Thread.sleep(2000);
                                                		 }
                                                    		 try
                                                    		 {
                                                    			 Thread.sleep(1000);
                                                    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
                                                        		 object.findElement(By.name("ddlQuotationNo")).sendKeys("TEST1");
                                                        		 object.findElement(By.name("btnquery")).click();
                                                        		 object.findElement(By.name("ddlDecisionStatus")).click();
                                                    		 }
                                                    		 catch(Exception et10)
                                                    		 {
                                                    			 Thread.sleep(1000);
                                                    			 object.switchTo().alert().accept();
                                                    			 Thread.sleep(2000);
                                                    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
                                                        		 object.findElement(By.name("ddlQuotationNo")).click();
                                                        		 Thread.sleep(2000);
                                                        		 a.sendKeys(Keys.DOWN).build().perform();
                                                        		 Thread.sleep(2000);
                                                        		 a.sendKeys(Keys.ENTER).build().perform();
                                                        		 Thread.sleep(3000);
                                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlDecisionStatus")));
                                                        		 object.findElement(By.name("ddlDecisionStatus")).click();
                                                        		 Thread.sleep(2000);
                                                        		 WebElement decisionstatus43=object.findElement(By.name("ddlDecisionStatus"));
                                                        		 Select t42=new Select(decisionstatus43);
                                                        		 t42.selectByVisibleText("PENDING");
                                                        		 Thread.sleep(2000);
                                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlApprovalStatus")));
                                                        		 object.findElement(By.name("ddlApprovalStatus")).click();
                                                        		 Thread.sleep(2000);
                                                        		 WebElement approvalstatus42=object.findElement(By.name("ddlApprovalStatus"));
                                                        		 Select t43=new Select(approvalstatus42);
                                                        		 t43.selectByVisibleText("CONVERTED TO POLICY");
                                                        		 Thread.sleep(2000);
                                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
                                                        		 object.findElement(By.name("btnquery")).click();
                                                        		 Thread.sleep(2000);
                                                        		/* wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='2019-3-12116/00']")));
                                                        		 object.findElement(By.xpath("//*[text()='2019-3-12116/00']")).click();
                                                        		 Thread.sleep(2000);*/
                                                        		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Records not Found !...']")));
                                                        		 if(object.findElement(By.xpath("//*[text()='Records not Found !...']")).isDisplayed())
                                                        		 {
                                                        			 System.out.println("Records not found");
                                                        		 }
                                                        		 Thread.sleep(2000);
                                                    		 }
    	 }
    	 else
    	 {
    		 
    	 }
     }
     @And("^click on Copy Quotation button$")
     public void copy_quotation() throws Exception
     {
    	   //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Quotation Module']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Copy Quotation']")));
		    object.findElement(By.xpath("//*[text()='Copy Quotation']")).click();
		    Thread.sleep(4000);
     }
     @And("^Enter Quotation Number as \"(.*)\"$")
     public void quotation_number(String x) throws Exception
     {
    	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationno")));
    	 WebElement quotationNumber=object.findElement(By.name("ddlQuotationno"));
    	 quotationNumber.click();
    	 Thread.sleep(1000);
    	 quotationNumber.sendKeys(x);
    	 Thread.sleep(3000);
    	Actions a=new Actions(object);
    	a.sendKeys(Keys.ENTER).build().perform();
    	Thread.sleep(5000);
     }
     @And("^Validate Copy Quotation page$")
     public void copy_quotation1() throws Exception
     {
    	 try
    	 {
    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(@name,'txtNewIncepDate')])[2]")));
        	 object.findElement(By.xpath("(//*[contains(@name,'txtNewIncepDate')])[2]")).sendKeys("21/02/2019");
        	 Thread.sleep(5000); 
        	 wait.until(ExpectedConditions.visibilityOf(bp.saveButton1));
        	 bp.click_saveButton1();
    	 }
    	 catch(Exception ex)
    	 {
    		 Thread.sleep(1000);
    		 object.switchTo().alert().accept();
    		 Thread.sleep(2000);
    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(@name,'txtNewIncepDate')])[2]")));
    		 object.findElement(By.xpath("(//*[contains(@name,'txtNewIncepDate')])[2]")).sendKeys("01/03/2019");
    		 Thread.sleep(5000);
    	 }
    	 wait.until(ExpectedConditions.visibilityOf(bp.saveButton1));
    	 bp.click_saveButton1();
    	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblResult")));
    	 if(object.findElement(By.id("lblResult")).isDisplayed())
    	 {
    		 Thread.sleep(1000);
    		 String newQuotationNumber=object.findElement(By.id("lblResult")).getText();
    		 System.out.println(newQuotationNumber);
    		 Thread.sleep(3000);
    		 Pattern p=Pattern.compile("[0-9]+");
    		 Matcher m=p.matcher(newQuotationNumber);
    		 while(m.find())
    		 {
    			 System.out.println(m.group());
    		 }
    	 }
     }
     @And("^click on Quotation Details Update button$")
     public void quotation_details() throws Exception
     {
    	   //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Quotation Module']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='Quotation Details Update'])[1]")));
		    object.findElement(By.xpath("(//*[text()='Quotation Details Update'])[1]")).click();
		    Thread.sleep(1000);
     }
     @And("^Enter Quotation Number \"(.*)\"$")
     public void quoation_number(String x) throws Exception
     {
    	 Thread.sleep(1000);
    	 if(bp.saveButton1.isEnabled() && bp.clearButton.isEnabled())
    	 {
    		 Thread.sleep(2000);
    		 try
    		 {
    			 wait.until(ExpectedConditions.visibilityOf(bp.saveButton1));
    			 bp.click_saveButton1();
    			 bp.click_clearButton();
    		 }
    		 catch(Exception et)
    		 {
    			 Thread.sleep(1000);
    			 object.switchTo().alert().accept();
    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
    			 object.findElement(By.name("ddlQuotationNo")).sendKeys(x);
    			 Thread.sleep(5000);
    			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(@name,'txtNewExpiryDate')])[2]")));
    			 object.findElement(By.xpath("(//*[contains(@name,'txtNewExpiryDate')])[2]")).sendKeys(x);
    			 Thread.sleep(1000);
    			 wait.until(ExpectedConditions.visibilityOf(bp.saveButton1));
    			 bp.click_saveButton1();
    		 }
    	 }
     }
     @And("^Validate Quotation Premium Update page$")
     public void quotation_premium_update() throws Exception
     {
    	   //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Quotation Module']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='Quotation Premium Upload'])[1]")));
		    object.findElement(By.xpath("(//*[text()='Quotation Premium Upload'])[1]")).click();
		    Thread.sleep(1000);
		    if(object.findElement(By.name("btnPremiumAdd")).isEnabled() && object.findElement(By.name("btnPremiumEdit")).isEnabled())
		    {
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnPremiumAdd")));
		    	object.findElement(By.name("btnPremiumAdd")).click();
		    	Thread.sleep(2000);
		    	String templateCode=object.findElement(By.name("txtTemplateCode")).getAttribute("value");
		    	Thread.sleep(2000);
		    	System.out.println(templateCode);
		    	Thread.sleep(2000);
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("txtName")));
		    	object.findElement(By.name("txtName")).sendKeys("TEMPLATE");
		    	Thread.sleep(3000);
		    	try
		    	{
		    		Thread.sleep(1000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(@name,'txtEffectiveFrom')])[2]")));
		    		object.findElement(By.xpath("(//*[contains(@name,'txtEffectiveFrom')])[2]")).sendKeys("12/02/2019",Keys.TAB,Keys.TAB);
		    		Thread.sleep(5000);
		    		object.findElement(By.xpath("(//*[contains(@name,'txtEffectiveTo')])[2]")).sendKeys("12/02/2019");
		    		Thread.sleep(1000);
		    	}
		    	catch(Exception ex)
		    	{
		    		Thread.sleep(1000);
		    		object.switchTo().alert().accept();
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(@name,'txtEffectiveFrom')])[2]")));
		    		object.findElement(By.xpath("(//*[contains(@name,'txtEffectiveFrom')])[2]")).sendKeys("12/03/2019");
		    		Thread.sleep(1000);
		    		try
		    		{
		    			Thread.sleep(1000);
		    			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(@name,'txtEffectiveTo')])[2]")));
		    			object.findElement(By.xpath("(//*[contains(@name,'txtEffectiveTo')])[2]")).sendKeys("27/02/2019",Keys.TAB,Keys.TAB);
		    			Thread.sleep(5000);
		    			bp.click_saveButton1();
		    		}
		    		catch(Exception ey)
		    		{
		    			Thread.sleep(1000);
	    				object.switchTo().alert().accept();
	    				Thread.sleep(1000);
	    				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(@name,'txtEffectiveTo')])[2]")));
		    			object.findElement(By.xpath("(//*[contains(@name,'txtEffectiveTo')])[2]")).sendKeys("27/02/2020");
		    		}
		    	}
		    	Thread.sleep(2000);
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("fuPremiumUpload")));
		    	object.findElement(By.name("fuPremiumUpload")).sendKeys("C:\\Users\\amt\\Desktop\\ManarQuotationGrossPremiumUploadTemplate.xls");
		    	Thread.sleep(1000);
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnPremiumUpload")));
		    	object.findElement(By.name("btnPremiumUpload")).click();
		    	Thread.sleep(1000);
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnPremiumVerify")));
		    	object.findElement(By.name("btnPremiumVerify")).click();
		    	Thread.sleep(3000);
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Rows Verified Successfully')]")));
		    	if(object.findElement(By.xpath("//*[contains(text(),'Rows Verified Successfully')]")).isDisplayed())
		    	{
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnPremiumSave")));
		    		object.findElement(By.name("btnPremiumSave")).click();
		    		Thread.sleep(2000);
		    		if(object.findElement(By.xpath("//*[contains(text(),'Premiums Saved Successfully')]")).isDisplayed())
		    		{
		    			s.write("Quotation Premiums Upload Successfully");
		    		}
		    	}
		    	Thread.sleep(1000);
		    	wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
		    	bp.click_clearButton();
		    	Thread.sleep(2000);
		    	if(object.findElement(By.name("btnPremiumEdit")).isEnabled())
		    	{
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnPremiumEdit")));
		    		object.findElement(By.name("btnPremiumEdit")).click();
		    		Thread.sleep(2000);
		    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlTemplateCode")));
		    		object.findElement(By.name("ddlTemplateCode")).click();
		    		Thread.sleep(4000);
		    		List<WebElement> templateList=object.findElements(By.xpath("//div[contains(@class,'rcbWidth')]/ul/li"));
		    		System.out.println(templateList.size());
		    		Thread.sleep(3000);
		    		for(int i=1;i<=templateList.size();i++)
		    		{
		    			Thread.sleep(1000);
		    			a.sendKeys(Keys.DOWN).build().perform();
		    			String actualTemplate=object.findElement(By.xpath("//div[contains(@class,'rcbWidth')]/ul/li["+i+"]")).getText();
		    			Thread.sleep(2000);
		    			if(actualTemplate.contains("-"+templateCode))
		    			{
		    				Thread.sleep(1000);
		    				a.sendKeys(Keys.ENTER).build().perform();
		    			}
		    		}
		    		Thread.sleep(1000);
		    		Date d1=new Date();
	    			SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
	    			String ssname1=sdf.format(d1)+".png";
	    			JavascriptExecutor js=(JavascriptExecutor)object;
	    			WebElement saveButton=object.findElement(By.name("btnPremiumSave"));
	    			js.executeScript("arguments[0].style.border='5px red solid';",saveButton);
	    			Thread.sleep(3000);
	    			File src1=((TakesScreenshot)object).getScreenshotAs(OutputType.FILE);
	    			File dest1=new File("E:\\SE_AutomationScripts\\scripts\\ssnameErr1");
	    			FileHandler.copy(src1, dest1);
	    			s.write("Save Button not working");
	    			Thread.sleep(3000);
	    			wait.until(ExpectedConditions.visibilityOf(bp.clearButton));
	    			bp.click_clearButton();
		    	}
		    	
		    }
		    else
		    {
		    	Date d=new Date();
		    	SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
		    	String errName=sdf.format(d);
		    	File src=((TakesScreenshot)object).getScreenshotAs(OutputType.FILE);
		    	File dest=new File("E:\\SE_AutomationScripts\\scripts\\Saudi_Enaya_indexes\\errName");
		    	FileHandler.copy(src, dest);
		    	s.write("Quotation Premium Update test Failed");
		    }
     }
     @And("^click on Quotation Renewal List button$")
     public void quotation_renewal_list() throws Exception
     {
    	   //homepage will be displayed
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Close']")));
		    object.findElement(By.xpath("//*[text()='Close']")).click();
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgHospital")));
		    Actions a=new Actions(object);
		    //move to masters module
		    WebElement master=object.findElement(By.xpath("//*[text()='Quotation Module']"));
		    a.moveToElement(master).build().perform();
		    Thread.sleep(2000);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[text()='Quotation Renewal List'])[1]")));
		    object.findElement(By.xpath("(//*[text()='Quotation Renewal List'])[1]")).click();
		    Thread.sleep(1000);
     }
     @And("^Enter Quotation Group as \"(.*)\"$")
     public void quotation_group(String x) throws Exception
     {
    	 Thread.sleep(2000);
    	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlGroupNo")));
    	 WebElement groupNumber=object.findElement(By.name("ddlGroupNo"));
    	 Thread.sleep(1000);
    	 groupNumber.click();
    	 Thread.sleep(2000);
    	 groupNumber.sendKeys(x);
    	 Thread.sleep(2000);
    	 if(object.findElement(By.name("btnquery")).isEnabled() && object.findElement(By.name("btnquery")).isEnabled())
    	 {
    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
    		 object.findElement(By.name("btnquery")).click();
    		 
    	 }
     }
     @And("^Enter Quotation Number has \"(.*)\"$")
     public void quotation_numbers(String x) throws Exception
     {
    	 Thread.sleep(2000);
    	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ddlQuotationNo")));
    	 object.findElement(By.name("ddlQuotationNo")).sendKeys(x);
    	 Thread.sleep(2000);
    	 if(object.findElement(By.name("btnquery")).isEnabled() && object.findElement(By.name("btnquery")).isEnabled())
    	 {
    		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnquery")));
    		 object.findElement(By.name("btnquery")).click(); 
    	 }
     }
     @And("^Validate Quotation Renewal List page$")
     public void quotation_renewal() throws Exception
     {
    	 
     }
     @After()
     public void results() throws Exception
     {
    	 er.endTest(et);
    	 er.flush();
    	 Thread.sleep(2000);
    	 /*wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ImgLogout")));
		 object.findElement(By.id("ImgLogout")).click();
		 Thread.sleep(1000);
		 object.close();*/
     }

}
