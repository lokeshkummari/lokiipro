Feature:SE_LoginPage
  Scenario Outline: validate login page
    Given launch site
     When enter username as "<username>"
      And enter password as "<password>"
      And select login type as "EMPLOYEE"
      And select policy type as "Group"
     Then click on submit button
      And validate the output with "<criteria>" criteria
      #And close site
    Examples: 
      | username | password | criteria         | 
      |          | Amtpl@1  | username_blank   | 
      | amtp     | Amtpl@1  | username_invalid | 
      | amtpl    | Amtpl@1  | all_valid        | 
  #| amtpl    |          | password_blank   | 
  # | amtpl    | Amtpl@3  | password_invalid |
     
  
     
