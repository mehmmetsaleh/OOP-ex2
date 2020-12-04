import oop.ex2.SpaceShipPhysics;

/**
 * Basher ship class
 */
public class BasherShip extends SpaceShip {
    private final static double DISTANCE_TO_SHIELD = 0.19;

    /**
     * Basher ship constructor
     */
    public BasherShip() {
        super();
        shipType = 2;
    }

    /**
     * this methods decides the turning direction of the spaceship
     * depending on the angle it makes with its closest ship
     *
     * @param closestShipPhysics - physics obj of the closest ship to this one
     * @return -1 to turn right, 1 to turn left, 0 otherwise
     */
    private int direction(SpaceShipPhysics closestShipPhysics) {
        double angle = this.getPhysics().angleTo(closestShipPhysics);
        if (angle < 0 && angle >= -Math.PI) {
            return TURN_RIGHT;
        } else if (angle > 0 && angle <= Math.PI) {
            return TURN_LEFT;
        } else {
            return DONT_TURN;
        }
    }

    /**
     * if distance between this ship and its closest one is 0.19 units, it tries to put shield up
     *
     * @param closestShipPhysics - the closest ship to this one
     */
    private void turnShieldsOn(SpaceShipPhysics closestShipPhysics) {
        if (this.getPhysics().distanceFrom(closestShipPhysics) <= DISTANCE_TO_SHIELD) {
            shieldOn();
            shipType = 3;
        } else {
            isShieldOn = false;
            shipType = 2;
        }
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
        SpaceShip closestShip = game.getClosestShipTo(this);
        SpaceShipPhysics closestShipPhysics = closestShip.getPhysics();
        int turn = direction(closestShipPhysics);
        this.getPhysics().move(true, turn);
        turnShieldsOn(closestShipPhysics);
        incEnergy();
    }
}
