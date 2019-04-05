package test;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions(features= {//"E:\\SE_AutomationScripts\\scripts\\Saudi_Enaya_indexes\\src\\test\\resources\\features\\A_loginpage.feature",
		                    // "E:\\SE_AutomationScripts\\scripts\\Saudi_Enaya_indexes\\src\\test\\resources\\features\\B_masterspage.feature",
		                     //"E:\\SE_AutomationScripts\\scripts\\Saudi_Enaya_indexes\\src\\test\\resources\\features\\C_insurancepage.feature",
		                     "E:\\SE_AutomationScripts\\scripts\\Saudi_Enaya_indexes\\src\\test\\resources\\features\\D_providernetwork.feature",
		                     //"E:\\SE_AutomationScripts\\scripts\\Saudi_Enaya_indexes\\src\\test\\resources\\features\\E_Quotation_Module.feature"
		                     },
                            monochrome=true,
                            plugin= {"pretty","html:target/SE_Application_results","rerun:target/details.txt"},tags= {"@Reimbursement_Price_List_Page"}
                             ) 

/*@RunWith(Cucumber.class)
@CucumberOptions(features= {"@target/details.txt"},
                            monochrome=true,
                            plugin= {"pretty","html:target/result3","rerun:target/details.txt"}
                                   )*/

public class SE_Runner_Class {

}
//tags= {"@Stake_Holder_Master_Page"}