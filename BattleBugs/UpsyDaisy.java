package BattleBugs;
import java.util.ArrayList;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.Color;
import java.sql.Array;

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

        ArrayList<BattleBug> enemies = new ArrayList<BattleBug>();
        ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

        for (Actor current : actors) {
            if (current instanceof BattleBug) {
                enemies.add((BattleBug) current);
            }
            else if (current instanceof Projectile) {
                projectiles.add((Projectile) current);
            }
        }
        
        

        if (puLocs.size() != 0) { // If it's not empty, then set the goTo to the nearest powerup
            sortPowerUpsLocs(puLocs);
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

    }

    

    //      Getting the Locations of different powerups (Start)
    //      Strength
    public ArrayList<Location> getStrengthLocs(ArrayList<PowerUp> allPowerUps) {
        
        ArrayList<Location> output = new ArrayList<Location>();

        for (PowerUp current : allPowerUps) {
            if (current.getColor().equals(Color.RED))
                output.add(current.getLocation());
        }

        return output;
    }
 
    //      Defense
    public ArrayList<Location> getDefenseLocs(ArrayList<PowerUp> allPowerUps) {
        
        ArrayList<Location> output = new ArrayList<Location>();

        for (PowerUp current : allPowerUps) {
            if (current.getColor().equals(Color.GREEN))
                output.add(current.getLocation());
        }

        return output;
    }

    //      Speed
    public ArrayList<Location> getSpeedLocs(ArrayList<PowerUp> allPowerUps) {
        
        ArrayList<Location> output = new ArrayList<Location>();

        for (PowerUp current : allPowerUps) {
            if (current.getColor().equals(Color.BLUE))
                output.add(current.getLocation());
        }

        return output;
    }
    //      (End)

    //      Distance between two locations 
    public int distanceLocs(Location L1, Location L2) {
        

        return (int) Math.sqrt(Math.pow(L1.getRow() - L2.getRow(), 2) + Math.pow(L1.getCol() - L2.getCol(), 2));
    }

    //      Distance between the player and the desired Location
    public int thisDistance(Location L1) {
        return (int) Math.sqrt(Math.pow(L1.getRow() - this.getLocation().getRow(), 2) + Math.pow(L1.getCol() - this.getLocation().getCol(), 2));
    }

    //      Sorting PowerUps based on Locations (least to greatest distance) <-- Selection Sort
    public void sortPowerUpsLocs(ArrayList<Location> powerUps) {

        for (int i = 0; i < powerUps.size() - 1; i++) {

            int index = i;

            for (int j = i + 1; j < powerUps.size(); j++) {
                if (thisDistance(powerUps.get(j)) < thisDistance(powerUps.get(index))) {
                    index = j;
                }
            }

            Location temp = powerUps.get(i);
            powerUps.set(i, powerUps.get(index));
            powerUps.set(index, temp);
        }
    }

    //      Attacking Stretegically
    public void dodgeAttack() {
        
    }

    

    

    

    

    
}