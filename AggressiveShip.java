import oop.ex2.SpaceShipPhysics;

/**
 * Aggressive Ship class
 */
public class AggressiveShip extends SpaceShip {
    private final static double ANGLE_TO_FIRE = 0.21;

    /**
     * Aggressive Ship constructor
     */
    public AggressiveShip() {
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
     * when the ship makes an angle of 0.21 radians with the closest ship to it, it tries to fire.
     *
     * @param closestShipPhysics - closest ship to this ship
     * @param game               - the game main driver
     */
    private void tryFire(SpaceShipPhysics closestShipPhysics, SpaceWars game) {
        if (Math.abs(Math.toRadians(this.getPhysics().angleTo(closestShipPhysics))) < ANGLE_TO_FIRE) {
            fire(game);
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
        tryFire(closestShipPhysics, game);
        incEnergy();
    }
}
