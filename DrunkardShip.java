import java.util.Random;

/**
 * Drunkard ship class
 */
public class DrunkardShip extends SpaceShip {
    private Random randObj; // random object

    /**
     * Drunkard ship constructor
     */
    public DrunkardShip() {
        super();
        shipType = 2;
        randObj = new Random();
    }

    /**
     * @return - a random true or false
     */
    private boolean getRandomBool() {
        return randObj.nextBoolean();
    }

    /**
     * @return - a random number from the two number -1 and 1
     */
    private int randIntFromArray() {
        int[] myIntArray = {TURN_RIGHT, TURN_LEFT};
        int randomIndex = randObj.nextInt(myIntArray.length);
        return myIntArray[randomIndex];
    }

    /**
     * increments current Energy level (for each round) and makes sure it's below Max Energy level
     */
    private void incEnergy() {
        curEnergy += 1;
        checkAndUpdateCurEnergy();
    }

    /**
     * this method determines the actions the ship is capable of doing
     * and it puts them to work
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        super.doAction(game);
        int turn = randIntFromArray();
        boolean accel = getRandomBool();
        this.getPhysics().move(accel, turn);
        fire(game);
        incEnergy();
    }
}
