# Peer Code Review
### Dupicated code
After seeing the cases of duplicated code  [here](https://www.cs.duke.edu/courses/compsci308/spring16/classwork/05_codereview/codepro_reports/cellsociety_team16.html), I choose the first case between SegregationSimulation and PredatorPreySimulation. I realize that we used the same if statement to check if the simulation type matches in every implemepted simulation class. I way to overcome this is create a boolean method in the abstract class, as follows:
```java
protected boolean doesTypeMatch(String myType){
    	return (getType() == null || !getType().equals(myType));
    }
```
### Check list
The only issue found by the check list is a bad exception handle in XMLParser. I plan on implementing an XMLException class to overcome this minor issue.
