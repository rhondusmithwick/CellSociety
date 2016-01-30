### cellsociety
Duke CompSci 308 Cell Society Project
### Introduction
* This project allows users to specify a simulation, which will then be carried out on a grid of Cell Automata.
* The primary area of flexibility will be in the design of the cells and simulation. Each simulation
	will work on the same grid, but with differnt types of cells that have different properties. 
* All classes will touch a CellManager, and any interaction between classes will be handeled by the CellManager. 

### Overview
* Main.java will run the the program by instantiating a CellSociety object and showing its stage
* CellSociety.java will hold a GUI and run simulations based on user input
	* Fields: cellManager, grid, GUI
  * Methods: init, getSimulation, beginSimulation
* The Simulation will be a base class that all Simulations (Fire, Segregation, ec...) will extend from
	* Each simulation subclass will parse its required XML file and sets its properties based on that
  * Each simulation subclass will have methods to change its properties 
  * Methods: init, updateCells
* The Cell class will be a base class for all other cells (Fire, Segregation, etc...) to extend from
	* Extended from Rectangle
  * Key inherited methods: setX(double x), getX(), setY(double y), getY(), 
  * Methods: handleUpdate(double elapsedTime), setImage(Image image)
* The CellManager will manage the cells so that they can be easily changed
	* Fields: List<Cell> theCells, Group group? 
* The Grid class will recieve a list of cells from CellManager and allow them to be displayed 
--- Is it redudndant to have both a grid and a cellManager? 
--- I say this because the CellManager itself could be the Grid because we could have it contain a Group
--- This is especially true if we have each individual Cell holding its own X and Y value


### User Interface
The user interface will consist of one larger rectangular screen of a fixed size that will contain two smaller screens 
within it. The screen on the left will be the grid for the simlation. This left screen will...
* Display a grid made of state-changing squares controled by simulation parameters and rules specificed within 
the update method of the grid squares (called cells for this project) defined in the Cell class for the simulation type.
* Be a fixed overall size with the number and size of the squares within the grid varying based on user input. 
The screen on the right will be the options menu for the user. This right screen will...
* Include a drop down menu from which users can select a simulation. 
* Include a text box/console where users can specify the size for the grid dictating the number of squares/cells seen on the 
left screen, but which will not change the overall size of the left screen. This text box/console will also be used to 
display error messages indicating bad inputs or empty data errors. 
* Contain a pause and resume button for users to be able to periodically stop the simulation.
* Contain a step forward button which will allow users to skip the simulation forward a set number of steps at one time, ie.
a certain number of update steps will be called before the grid itself is updated to reflect the new state. 
* Include a fast-forward and a slow-down button which will increase the step rate adnd slow down the step rate respectively.


Resetting the size will restart the simulation, whenever a simulation is restarted,swuare size wil be recalculated
Selecting a simulation while another is running will 
shut down the current simulation and begin the newly selected one, which will all be monitored by the Cell Society class
listening for user input and invoking the end method of the old simulation class to end it and create the new requested
simulation class. 
### Design Details
Basic flow:
- Main is run
- Main starts cell society
- Cell society gets input from the user via the gui of the simulation type and size of the grid
- Cell society runs the chosen simulation, with the cell manager and grid size as a parameter
- The simulation loads the simulation's xml file and then creates the cells with the properties defined in the xml file
- The simulation adds the cells to cell manager and returns cell manager to cell society
- Cell society loads the initial cells onto the grid
- Cell society listens from input from the gui to start the simulation
- Cell society calls updateCells on cellManager
- CellManager calls updateProperties on each cell
- Cell society updates new cells onto the grid
- Simulation countinues until end conditions are met ()
- The simulation 
* Main
- Simply runs cell society, procedure remains constant for each kind of simulation
* Cell Society 
- Gets 
* Cell Manager
* Cell
* Grid
* AbstractSimulation 

### Design Considerations 
We ran into several issues that could not be fully decided until a deeper understanding of the project has been reached through coding.
1. Is it redundant to have both a Grid and a CellManager?
	* Reasons for consideration: inexperience with creating a Grid, dryness of code
	* This issue came about because we currently do not know what the Grid will look like. 
  * If each Cell holds its own X and Y value, then we need some external class to determine its neighbors 
    	(hence the Grid or CellManager). 
  * The Grid might already be able to do exactly what the CellManager would do simply by holding a list of Cells.
2. Is it neccessary to have different Simulation classes?
	* Reasons for consideration: dryness of code, Little knowledge of XML, little knowledge of how simulations differ from each other
	* Each Cell subclass will hold the rules for its simulation.
  * However, is it possible to bind the initial configuration without first knowing what the configuration
  parameters actually are?
  	* For example, the parameters for a Fire Simulation might be very different from a Water simulation.
  * We are currently leaning towards having separate Simulation classes: each one will parse its specific
  XML file, and binds its known parameters. 
  * Another question: Can we dynamically determine parameters at runtime using XML?
3. Clashing of coding styles	
	* Should we have a Constants class?
  	* Dependa on whether many clases will need to access these constants, how many constants we will have
    * Group decided no for now, but open to changing in future
  * Standard coding styles
  	* camelCase
    * everything private until needed by another class
4. GitHub standards
	* Major changes to another group member's code must be approved by that group member 
  	* If clash, other group member mediates.
  * Small refactoring approval is not rrequired, but is nice.
  * Big edits should happen in individual branches; small refactoring may be done directly on master.
  
### Team Responsibilities 
