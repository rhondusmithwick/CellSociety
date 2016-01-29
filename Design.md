# cellsociety
Duke CompSci 308 Cell Society Project
# Introduction
* This project allows users to specify a simulation, which will then be carried out on a grid of Cell Automata.
* The primary area of flexibility will be in the design of the cells and simulation. Each simulation
	will work on the same grid, but with differnt types of cells that have different properties. 
* All classes will touch a CellManager, and any interaction between classes will be handeled by the CellManager. 
# Overview
* Main.java will run the the program by instantiating a CellSociety object and showing its stage
* CellSociety.java will hold a GUI and run simulations based on user input
* The Simulation will be a base class that all Simulations (Fire, Segregation, ec...) will extend from
	* Each simulation subclass will parse its required XML file and sets its properties based on that
  * Each simulation subclass will have methods to change its properties 
* The Cell class will be a base class for all other cells (Fire, Segregation, etc...) to extend from
* The CellManager will manage the cells so that they can be easily changed
* The Grid class will recieve a list of cells from CellManager and allow them to be displayed 
# User Interface
# Design Details
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
# Design Considerations 
# Team Responsibilities 
