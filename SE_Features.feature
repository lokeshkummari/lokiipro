Feature: Validate Saudi Enaya Application
@Masters_module
  Scenario: Validate Masters Modules
    Given launch site
     When Enter Credentials as Valid
  
      | username | password | 
      | amtpl    | Amtpl@1  | 
     Then click on submit button
     When Click on Currency Masters 
     Then validate Masters Modules
    # And do logout
    # Then close site
    
     @Insurance_module
  Scenario: Validate Insurance Modules
    Given launch site
     When Enter Credentials as Valid
  
      | username | password | 
      | amtpl    | Amtpl@1  | 
     Then click on submit button 
     When validate Insurance Modules
     #And do logout
     #Then close site
     
     @providerNetwork_module
  Scenario: Validate Provider Network Modules
    Given launch site
     When Enter Credentials as Valid
  
      | username | password | 
      | amtpl    | Amtpl@1  | 
     Then click on submit button
     When validate Provider Network Modules
     #And do logout
     #Then close site
     
     @Quotation_module
  Scenario: Validate Quotation Modules
    Given launch site
     When Enter Credentials as Valid
  
      | username | password | 
      | amtpl    | Amtpl@1  | 
     Then click on submit button 
    # Then validate Quotation Modules
    # Then Validate quotation page
     And click on New Lux Note
      Then Enter sales Enity Type as "BROKER" ,"Independent Insurance Brokers~252"
      And Enter payment as "MONTHLY" 
      And Enter Branch code as "Head Office"
      And click on the add new class
      And Select className as "C RES"
      And Select product as "DIAMOND V1~16"
      And Select Nework as "Classic~13"
      And Select Annual limit as "500000"
      And Select Provider classification as "VIP~12"
      And Select loading template as "NW10 Loading Broker~15"
      And Select dentalLimit as "2500"
      And Select Opticallimit as "1000"
      Then click on save button
      And upload and verify the members info
      And upload and verify premium
      And click on the save button
      Then click on decision status
      And click on Convert policy
    # And do logout
     #Then close site
     
     @Policy_Module
     Scenario: validate Sub-Modules in Policy Module
     Given launch site
     When Enter Credentials as Valid
  
      | username | password | 
      | amtpl    | Amtpl@1  | 
     Then click on submit button 
     And Validate Pages in Policy Module
     
     
     