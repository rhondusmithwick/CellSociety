# cellsociety
Duke CompSci 308 Cell Society Project

**Name:**  Rhondu Smithwick, Bruna Liborio, Tavo Loaiza

**Date started:** 01/29/2016

**Date finished:** 02/06/2016

**Hours worked:** 
Rhondu Smithwick - 40 hours
Bruna Liborio - 30 hours 
Tavo Loaiza - 20 hours

**Resources used:** Java API; Piazza; Stackoverflow.com; http://docs.oracle.com/javafx/2/layout/size_align.htm, specifically for the GUI; resources on Cell Society assignment page; links to simulation descriptions; 'lab_browser' code 

**Main class file:** Main.java

**Data (non Java) files needed:** 

XMLFiles.properties, GUIstring.properties, Fire.xml, GameOfLife.xml,  PredatorPrey.xml, Segregation.XML

**Extra data files**

FireCoal.xml, FireGasoline.xml, GameOfLifeDesert.xml, GameOfLifeOvercroweded.xml, PredatorPreyExtinction.xml, 
PredatorPreySharkPacks.xml, SegregationMinority.xml, SegregationRural.xml, or any other properly formatted XML file the user loads

**How to Use:** 

Use the drop down menu labeled "Select simulation" to load a sample simulation with default settings, or click the "Load XML File" button to load unique parameters for a simulation from a specific XML file (either from the many provided or any other properly formatted XML file created by the user).
The already created XML files are located in the resource folder of the project.
Click the "Play/pause" to start and run the simulation. This button can be used throughout the simulation to pause the simulation if it is running or to run the simulation if it is paused. 
The adjacent buttons "Step", "Slower", "Faster", "Play Again", and "Reset" can be used to control a simulation as it is running.
'Step' stops the simulation and moves forward a single frame. 
'Slower' reduces the frame rate of the simulation up to a certain limit. Extending beyond that limit displays an error message. 
'Faster' increases the frame rate of the simulation up to a certain limit. Again, extnding beyond that limit displays an error message. 
'Play Again' restarts the simulation with the most recently set parameters, specifically with the most recently set number of cells chosen or loaded by the user. The user must press the 'Play/Pause' button to start the simulation. 
'Reset' restarts the simulation with the default simulation parameters from the base XML files, discarding any changes made by the user during the simulation as well as any parameters obtained from loading an XML file. Again, the user must press the 'Play/Pause' button to start the simulation. 
The 'Change Cells Per Row/Column' button allow the user to enter in a single integer to change both the number of cells per row and number of cells per column to the entered number in the constantly square grid. 

**Keys/Mouse Input**
No keyboard keys are used in this program for specific commands, but the user should use the keys when entering a new number of cells per row/column parameter for the simulation. 
Mouse input is used to click buttons, load files, and interact with the simulation. 

**Known bugs:**
Incorporation of XML file loading was just recently added. Many bugs involving the integration have been found and fixed, but there may be a few left.
No tests have been done with incorrectly formatted XML files. We do not know what happens then. 

**Extra features:** 
- Can choose the XML simulation from a file dialogue. 
- A simulation can be paused, sped up, slowed down, stepped through, played again, or reset.
- You can change the grid size of running simulations.
- Error messages pop up when something goes wrong. 
- Type of simulation appears in text on the GUI.

**Impressions/Suggestions:**
CSS files will be added to 'jazz up' the GUI.
More property files will be created to change the buttons from text only to include pictures as well.
'Try, catch' can be added to throw exceptions.
An exceptions class can be created for the specific project. 
XML file loading integration must be further debugged. 
