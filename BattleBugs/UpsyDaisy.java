package BattleBugs;
import java.util.ArrayList;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.Color;
import info.gridworld.actor.Actor;

public class UpsyDaisy extends BattleBug2012
{
    public UpsyDaisy(int str, int def, int spd, String name, Color col)
    {
            super(str, def, spd, name, col);
    }
    public void act()
    {

        
        Location goTo = new Location(5, 5); // Starting Location
        ArrayList<Location> puLocs = getPowerUpLocs(); // ALL powerup locations
        ArrayList<Actor> actors = getActors();  // Nearby actors/entites
        

        //CHECK TO SEE if there exists a PowerUp Location, if so then store the first location from the List into goTo
        if (puLocs.size() != 0) {
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
        else
            turnTo(dir);

    }

    public void sortClosest() {

    }

    
}