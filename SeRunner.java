package test2;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features= {"E:\\SE_AutomationScripts\\scripts\\Saudi_Enaya_indexes\\src\\test\\resources\\features\\SE_Features.feature",},
                            monochrome=true,
                            plugin= {"pretty","html:target/SE_Application_results",
                            		 "json:target/cucumber-reports/SaudiEnayaApplication.json",
                         		     "junit:target/cucumber-reports/SaudiEnayaApplication.xml",
                                                                         "rerun:target/details.txt"},tags= {"@Policy_Module"}
                             ) 
public class SeRunner {

}
