### cellsociety Lab 2/11 Discussion
Rhondu Smithwick (rss44), Justin (jtb43)

### Rhondu
* For Rhondu, refactoring the Cell type method changeState because it was marked as having the most duplicated code.
* The Cell subclasses ie(FireCell, PredatorPreyCell, etc.) each share a changeState method that is very similar in function and format. The problem is that they use Enums, which cannot be extended from the super Class Cell. One way we thought of approaching this is to implement an Interface within the Cell class. The other way is changing the design to use a map or similar.
* The changeState methods for each of the cell types are four of the project’s longest methods. If extracting that method to a super Class were to work, that could eliminate a good amount of code length. The rest of the project raised very few objections.
* After attempt to change and discussion with Professor Duvall, decided that changing would take a lot of effort for only a portion of the design.

### Justin
* stateLogic method very long
	* good logic and code, just need extraction to helper methods
* extract lines 88- 99 to method createStateLists(List<Cell> empty,
 List<Cell> fish, List<Cell> shark)
* Lines 110, 121, and 128 are basically the same
		extract to method getRandomCell(List<cell> cells) that returns a
		random cell from that list
* All the ifs after line 102 are the same
	* Extract to method process() that will take in parameters for what setNextState to, setChangeTo, and what to return
* Lines 116- 117 and Lines 134-135 are the same except for what they return
	* extract to method cleanUp(State s) that will do the set satisfied and then return the state
