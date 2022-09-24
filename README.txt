IDE : intelliJ community edition latest version
Java: 15.0.1
JavaFX Maven project
API handling : restAssured library
Design pattern : Tried to apply the default patter provided by JavaFX: MVC ( model, view, controller)
Running: Just Import and run the app from AppLauncher or through the snapshot jar in the target folder

Handling the add new pet :
	- user should enter uniuqe id "which could be auto generated but i found the API accepts doublicatios of ID!!"
	- user can enter name
	- user can enter status
note : user should enter rest of pet's details but to save time only the above data could be inserted

Handling the Update details case :
	- create metod to call the update API PUT
	- allow user to update the Pets name and stauts for a specific pet"using pet ID"


Handling Deletion case:
	- find pet by id to check if it's exist or already deleted
	- if the API returns 200==> so we can delete;
	


Handling Pets finder 
	- get required data ( id / stauts )" strings "
	- if string.equalsIgnoreCase (available, sold or pending ) ==> call the get by status API
		else ==> call get by ID API
		
		cases : 
		sold ==> should display all sold pets 
		available ==> ** ** ** available **
		pending ==> ** ** ** pending **
		xyz ==> not found 
		1213131==> not found 
		correct ID ==> Pet Data 



Enhancement may need to be done : 
	- adding themes, photos, styles 
	- controller should be splitted for more than one class
	- listing pet details should include all pets details not just the pet's name




