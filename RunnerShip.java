import oop.ex2.SpaceShipPhysics;

/**
 * a Runner Ship class
 */
public class RunnerShip extends SpaceShip {
    private final static double DISTANCE_TO_TELEPORT = 0.25;
    private final static double ANGLE_TO_TELEPORT = 0.23;

    /**
     * a Runner Ship constructor
     */
    public RunnerShip() {
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
            return TURN_LEFT;
        } else if (angle > 0 && angle <= Math.PI) {
            return TURN_RIGHT;
        } else {
            return DONT_TURN;
        }
    }

    /**
     * when this ship gets closer than 0.25 units from its closest ship and the closest ship makes an angle
     * of less than 0.23 radians, the runner ship tries to teleport
     *
     * @param closestShipPhysics - physics obj of the closest ship to this one
     */
    private void tryTeleport(SpaceShipPhysics closestShipPhysics) {
        if (this.getPhysics().distanceFrom(closestShipPhysics) < DISTANCE_TO_TELEPORT &&
                Math.abs(Math.toRadians(closestShipPhysics.angleTo(this.getPhysics()))) < ANGLE_TO_TELEPORT) {
            teleport();
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
        tryTeleport(closestShipPhysics);
        int turn = direction(closestShipPhysics);
        this.getPhysics().move(true, turn);
        incEnergy();
    }
}
