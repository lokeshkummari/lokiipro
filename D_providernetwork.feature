Feature: Validate Provider Network Module
@Department_Master_Page
  Scenario: Validate Department Details Page
  
     Then validate Department Details page
     
     @Room_Type_Master_Page
  Scenario: Validate Hospital Room Type Details Page
    
     Then validate Hospital Room Type Details page
     
      @Speciality_Master_Page
  Scenario: Validate Speciality Master Page
    
     Then validate Speciality Master  page
      
      @Service_Type_Master_Page
  Scenario: Validate Service Type Master Page
    
     Then validate Service Type Master  page
      
     @Provider_Classification_Page
  Scenario: Validate Provider Classification Page
    
     Then validate Provider Classification  page
      
      #@Service_Vat_Master
  #Scenario: Validate VAT MASTER Page
   # Given launch site
    # When Enter Credentials as Valid
  
     # | username | password | 
      #| amtpl    | Amtpl@1  | 
     #Then click on submit button
     #Then validate VAT MASTER  page
     # And do logout
     #Then close site
      @Provider_Group_Page
  Scenario: Validate Provider Group Page
   
     Then validate Provider Group  page
    
      @DB_Provider_Page
  Scenario: Validate DB Provider Page
    
     Then validate DB Provider  page
      
     @Doctor_List_Page
  Scenario: Validate Doctor List Page
    
     Then validate Doctor List  page
    
     @DB_Provider_Contract_List_Page
  Scenario: Validate Provider Contract Page
     
     Then validate Provider Contract  page
     
      @Reimbursement_Provider_Master_Page
  Scenario: Validate Reimbursement Provider Details Page
    
     Then validate Reimbursement Provider Details  page
    
      @Reimbursement_Price_List_Page
  Scenario: Validate Reimbursement Price List Page
       Given launch site
    When Enter Credentials as Valid
     | username | password | 
     | amtpl    | Amtpl@1  | 
     Then click on submit button
     Then validate Reimbursement Price List  page
     