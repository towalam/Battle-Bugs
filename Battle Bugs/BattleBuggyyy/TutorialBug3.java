package BattleBugs;
import java.util.ArrayList;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.Color;
import info.gridworld.actor.Actor;

public class TutorialBug3 extends BattleBug2012
{
    public TutorialBug3(int str, int def, int spd, String name, Color col)
    {
            super(str, def, spd, name, col);
    }
    public void act()
    {
        //declare a Location named goTo and initialize it with the location (3,3)
        Location goTo = new Location(3, 3);
        
        //Call the getPowerUpLocs() method and store the result in a variable named puLocs.
        ArrayList<Location> puLocs = getPowerUpLocs();

        //Call the getActors() method to get all the Actors that are within 3 units of your BattleBug, store the result in a variable named actors
        ArrayList<Actor> actors = getActors();
        
        //Declare and instantiate a new ArrayList that can only hold objects of type BattleBug 
        //Name it enemies, this will store the nearby enemies.
        ArrayList<BattleBug> enemies = new ArrayList<BattleBug>();
        
        //You will now traverse through actors and add the actors that are BattleBugs into the enemies ArrayList.
        //use the keyword  instanceof    to determine if the current Actor is a BattleBug
        //When you add it into the enemies ArrayList do not forget to Cast the current Actor into a BattleBug
        for (Actor actor : actors) {
            if (actor instanceof BattleBug) {
                enemies.add((BattleBug) actor);
            }
        }
        
        //Acknowledge that you want to attack the enemy BattleBug FIRST and then get the PowerUp
        //Determine a value for goTo, 
        //first check to see if there exists an enemy, if so then set goTo to be the Location of the first enemy in enemies
        //if there are no enemies check to see if there exists a PowerUp Location, if so then store the first location from the List into goTo
        if (!enemies.isEmpty())
            goTo = enemies.get(0).getLocation();
        else if (!puLocs.isEmpty()) {
            goTo = puLocs.get(0);
        }
        
        
        
        //Call the getDirectionToward() method and store the result in a variable named dir.
        int dir = getDirectionToward(goTo);

        //Using the getDirection() method check to see if your bug is facing the desired direction dir
        
        
        //If so then call the move() method
        //if not then call turnTo() method towards the desired direction dir.
        if (dir == getDirection()) {
            move();
        } 
        else {
            turnTo(dir);
        }
        

        //Call the attack method.  
        attack();
        
        //(This would be unwise in the real game because you would run out of ammo very quickly, but do it for the sake of the tutorial)
       
        
        




    }
}