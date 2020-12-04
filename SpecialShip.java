import oop.ex2.SpaceShipPhysics;

/**
 * special ship class , behaviour expressed in README
 */
public class SpecialShip extends SpaceShip {
    private final static double DISTANCE_TO_TELEPORT = 0.19;
    private final static int MAX_ENERGY_VAL = 210;
    private final static int CUR_ENERGY_VAL = 190;
    private SpaceShip firstCloseShip;
    private boolean isFirstPass = true; // first pass in do action method

    /**
     * Special ship constructor
     */
    public SpecialShip() {
        super();
        shipType = 3;
    }

    /**
     * this methods decides the turning direction of the spaceship
     * depending on the angle it makes with its first closest ship
     *
     * @param firstCloseShipPhysics - physics obj of the first closest ship to this one
     * @return -1 to turn right, 1 to turn left, 0 otherwise
     */
    private int direction(SpaceShipPhysics firstCloseShipPhysics) {
        double angle = this.getPhysics().angleTo(firstCloseShipPhysics);
        if (angle < 0 && angle >= -Math.PI) {
            return TURN_RIGHT;
        } else if (angle > 0 && angle <= Math.PI) {
            return TURN_LEFT;
        } else {
            return DONT_TURN;
        }
    }

    /**
     * increments current Energy level by 10 (for each round) and makes sure it's below Max Energy level
     */
    private void incEnergy() {
        curEnergy += 10;
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
        maxEnergy = MAX_ENERGY_VAL;
        curEnergy = CUR_ENERGY_VAL;
        SpaceShip closestShip = game.getClosestShipTo(this);
        SpaceShipPhysics closestShipPhysics = closestShip.getPhysics();
        if (isFirstPass) {
            firstCloseShip = closestShip;
        }
        isFirstPass = false;
        SpaceShipPhysics firstCloseShipPhysics = firstCloseShip.getPhysics();
        int turn = direction(firstCloseShipPhysics);
        if (this.getPhysics().distanceFrom(closestShipPhysics) <= DISTANCE_TO_TELEPORT) {
            teleport();
        }
        this.getPhysics().move(true, turn);
        shieldOn();
        fire(game);
        incEnergy();
    }
}
