- The server's coordinates begin at 0, 0 and my PA03 implementation began at 1,1. Coordinate numbers were adjusted in
all relevant methods (including displays for Pa03)
- the reportDamage methods now also updates the player's remaining shots instead of having two separate method calls 
in the model for this
- Added a getter method for the orientation of a ship in the ship interface and abstract class
- All relevant tests for pa03 were updated with these changes in place