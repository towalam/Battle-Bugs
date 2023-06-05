package BattleBugs;
import java.util.ArrayList;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.Color;
import java.sql.Array;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock;

public class UpsyDaisy extends BattleBug2012
{


    public UpsyDaisy(int str, int def, int spd, String name, Color col)
    {
            super(str, def, spd, name, col);
            
    }

    public void act()
    {
        
        int numActs = 0;

        Location goTo = new Location(5, 5); // Starting Location
        ArrayList<Location> puLocs = getPowerUpLocs(); // ALL powerup locations
        ArrayList<Actor> actors = getActors();  // Nearby actors/entites

        ArrayList<BattleBug> enemies = new ArrayList<BattleBug>();
        ArrayList<Actor> obstacles = new ArrayList<Actor>();

        ArrayList<Location> futureRocks = this.getNumAct() / 40 < 26 ? offBounds() : null;

        if (futureRocks != null && this.getNumAct() % 40 >= 35) {
            for (Location current : futureRocks) {
                for (int i = 0; i < puLocs.size(); i++) {
                    if (current.equals(puLocs.get(i))) {
                        puLocs.remove(i);
                    }
                }
            }
        }
        

        //      Puts nearby actors into respective arrays
        for (Actor current : actors) {
            if (current instanceof BattleBug) {
                BattleBug enemy = (BattleBug) current;
                if (!enemy.isDead())
                    enemies.add((BattleBug) current);
                
                else
                    obstacles.add(enemy);
            }

            else if (current instanceof Rock || current instanceof Regulator)
                obstacles.add(current);
            
            
        }        
        

        if (puLocs.size() != 0) { // If it's not empty, then set the goTo to the nearest powerup
            sortPowerUpsLocs(puLocs);
            goTo = puLocs.get(0);
            
        }

        if (!enemies.isEmpty()) {

            if (this.getAmmo() != 0 && !enemies.get(0).isDead() && this.getStrength() + 3 >= enemies.get(0).getDefense()) {
                goTo = enemies.get(0).getLocation();

                int maxRange = this.getStrength() < 10 ? 2 : 3;
                if (this.getStrength() >= 20) {
                    maxRange = 4;
                }

                if (thisDistance(enemies.get(0)) < maxRange) {
                    if (getDirectionToward(goTo) != getDirection()) {
                        turnTo(getDirectionToward(goTo));
                        numActs++;
                    }
                    
                    attack();
                }
            }

        }

        //      DO NOT WRITE BELOW THESE
        //Call the getDirectionToward() method and store the result in a variable named dir.
        int dir = getDirectionToward(goTo);
        if (numActs == 0) {
            if (dir == getDirection()) {
                move();
            }
            else {
                turnTo(dir);
            }
        }

    }

    //      Making the rock "bounds"
    private ArrayList<Location> offBounds() {
        ArrayList<Location> output = new ArrayList<Location>();
        int acts = this.getNumAct() / 40; 
    
        for (int i = acts; i < 27 - acts && acts < 27; i++) {
            output.add(new Location(i, acts));
            output.add(new Location(acts, i));

            output.add(new Location(26 - acts, i));
            output.add(new Location(i, 26 - acts));
        }

        
        return output;
    }
    

    //      Getting the Locations of different powerups (Start)
    //      Strength
    private ArrayList<Location> getStrengthLocs(ArrayList<PowerUp> allPowerUps) {
        
        ArrayList<Location> output = new ArrayList<Location>();

        for (PowerUp current : allPowerUps) {
            if (current.getColor().equals(Color.RED))
                output.add(current.getLocation());
        }

        return output;
    }
 
    //      Defense
    private ArrayList<Location> getDefenseLocs(ArrayList<PowerUp> allPowerUps) {
        
        ArrayList<Location> output = new ArrayList<Location>();

        for (PowerUp current : allPowerUps) {
            if (current.getColor().equals(Color.GREEN))
                output.add(current.getLocation());
        }

        return output;
    }

    //      Speed
    private ArrayList<Location> getSpeedLocs(ArrayList<PowerUp> allPowerUps) {
        
        ArrayList<Location> output = new ArrayList<Location>();

        for (PowerUp current : allPowerUps) {
            if (current.getColor().equals(Color.BLUE))
                output.add(current.getLocation());
        }

        return output;
    }
    //      (End)

    //      Distance between two locations 
    private int distanceLocs(Location L1, Location L2) {
        

        return (int) Math.sqrt(Math.pow(L1.getRow() - L2.getRow(), 2) + Math.pow(L1.getCol() - L2.getCol(), 2));
    }

    //      Distance between the player and the desired Location
    private int thisDistance(Location L1) {
        return (int) Math.sqrt(Math.pow(L1.getRow() - this.getLocation().getRow(), 2) + Math.pow(L1.getCol() - this.getLocation().getCol(), 2));
    }

    private int thisDistance(Actor a) {
        return (int) Math.sqrt(Math.pow(a.getLocation().getRow() - this.getLocation().getRow(), 2) + Math.pow(a.getLocation().getCol() - this.getLocation().getCol(), 2));
    }

    //      Sorting PowerUps based on Locations (least to greatest distance) <-- Selection Sort
    private void sortPowerUpsLocs(ArrayList<Location> powerUps) {

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

    //helper method for getting near by rocks
    private ArrayList<Location> nearbyRocks(ArrayList<Rock> rocks)
    {
        ArrayList<Location> output = new ArrayList<Location>();
        Location bugLocation = super.getLocation();
        for(Rock current: rocks)
        {
            Location location = current.getLocation();
            if(distanceLocs(bugLocation, location) <= 2)
            {
                output.add(location);
            }
        }
        return output;
    }

    private ArrayList<Location> getLocations(ArrayList<Actor> actors) {
        ArrayList<Location> output = new ArrayList<Location>();
        for (Actor current : actors) {
            output.add(current.getLocation());
        }
        return output;
    }

    //      Finding the safe Locations
    private ArrayList<Location> safeSpots(ArrayList<Actor> actors) {
        ArrayList<Location> output = new ArrayList<Location>();

        output = getLocations(getNeighbors());

        for (Actor current : actors) {
            if (current instanceof PowerUp && thisDistance(current.getLocation()) <= 3)
                output.add(current.getLocation());
                
        }



        return output;
    }

    //      Going around obstacles??
    //      Helper method
    private ArrayList<Location> locationSurround() {
        ArrayList<Location> output = new ArrayList<Location>();

        int dist = this.getSpeed() < 20 ? 3 : 4;
        int row = this.getLocation().getRow() - dist;
        int col = this.getLocation().getCol() - dist;

        while (row < 0) {
            row++;
        }
        

        while (row > 26) {
            row--;
        }

        while (col < 0) {
            col++;
        }

        while (col > 26) {
            col--;
        }

        for (int i = col; i <= this.getLocation().getCol() + dist && i <= 26; i++) {
            for (int j = row; j <= this.getLocation().getRow() + dist && j <= 26; j++) {
                output.add(new Location(i, j));
            }
        }

        

        return output;
    }
    

    
}