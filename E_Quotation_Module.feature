Feature: Validate Quotation Module
  @Quotation_Loading_Master_Page
  Scenario: Validate Quotation Loading Master Page
     Given launch site
     When Enter Credentials as Valid
  
      | username | password | 
      | amtpl    | Amtpl@1  | 
     Then click on submit button
      And validate Quotation Loading Master page
      And do logout
     Then close site
      
    
    @Co-Insurance_Master_Page
  Scenario: Validate Co-Insurance Master Page
     Given launch site
     When Enter Credentials as Valid
  
      | username | password | 
      | amtpl    | Amtpl@1  | 
     Then click on submit button
      And validate Co-Insurance Master page
      And do logout
     Then close site
     
    @Network_Master_Page
  Scenario: Validate Network Master Page
       Given launch site
     When Enter Credentials as Valid
  
      | username | password | 
      | amtpl    | Amtpl@1  | 
     Then click on submit button
     And validate Network Master page
     And do logout
     Then close site
     
    @Presenter_Group
  Scenario: Validate Presenter Group Details Page
       Given launch site
     When Enter Credentials as Valid
  
      | username | password | 
      | amtpl    | Amtpl@1  | 
     Then click on submit button
      And validate Presenter Group Details page
       And do logout
     Then close site
    @Presenter_Details
  Scenario: Validate Presenter Details Page
       Given launch site
     When Enter Credentials as Valid
  
      | username | password | 
      | amtpl    | Amtpl@1  | 
     Then click on submit button
      And validate Presenter Details page
     And do logout
     Then close site
     @Quotation_List
  Scenario: Validate Quotation List Page
      Given launch site
     When Enter Credentials as Valid
  
      | username | password | 
      | amtpl    | Amtpl@1  | 
     Then click on submit button
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
     # And validate Policy Contract List
      And do logout
     Then close site
      
     @Quotation_Details_Update
  Scenario: Validate Quotation Details Update Page
      Given launch site
     When Enter Credentials as Valid
  
      | username | password | 
      | amtpl    | Amtpl@1  | 
     Then click on submit button
      And click on Quotation Details Update button
      And Enter Quotation Number "2019-2-12034/00"
       And do logout
     Then close site
    
      @Quotation_Premium_Upload
  Scenario: Validate Quotation Premium Update Page
   
      And Validate Quotation Premium Update page
     
      @Quotation_Renewal_List
  Scenario: Validate Quotation Renewal List Page
    
      And click on Quotation Renewal List button
      And Enter Quotation Group as "Itest427"
      And Enter Quotation Number has ""
      And Validate Quotation Renewal List page
     
      # @Copy_Quotation
      #Scenario: Validate Copy Quotation Page
      #Given launch site
      #When Enter Credentials as Valid
  
      #| username | password | 
      #| amtpl    | Amtpl@1  | 
     #Then click on submit button
     # And click on Copy Quotation button
     # And Enter Quotation Number as "2018-4-10378/02"
     # And Validate Copy Quotation page
     #Then do logout
     # And close site
     
