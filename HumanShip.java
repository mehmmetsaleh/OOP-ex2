/**
 * a human controlled ship class
 */
public class HumanShip extends SpaceShip {
    /**
     * HumanShip constructor
     */
    public HumanShip() {
        super();
        shipType = 0;
    }

    /**
     * this methods decides the turning direction of the spaceship
     * depending on the keyboard input from the user.
     *
     * @param game - the game main driver
     * @return 1 to turn left, -1 to turn right ,0 otherwise
     */
    private int direction(SpaceWars game) {
        if (game.getGUI().isLeftPressed()) {
            return TURN_LEFT;
        } else if (game.getGUI().isRightPressed()) {
            return TURN_RIGHT;
        }
        return DONT_TURN;
    }

    /**
     * this method checks if teleport is pressed, if that's the case it tries to teleport
     *
     * @param game - the game main driver
     */
    private void checkTeleport(SpaceWars game) {
        if (game.getGUI().isTeleportPressed()) {
            teleport();
        }
    }

    /**
     * this method checks if shield button is pressde, if that's the case it tries to put shields up for
     * the current round
     *
     * @param game - the game main driver
     */
    private void checkShield(SpaceWars game) {
        if (game.getGUI().isShieldsPressed()) {
            shieldOn();
            shipType = 1;
        }
    }

    /**
     * this method checks of gun button is pressed, if that's the case it tries to shoot.
     *
     * @param game - the game main driver
     */
    private void checkShooting(SpaceWars game) {
        if (game.getGUI().isShotPressed()) {
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
        // teleport
        checkTeleport(game);
        // accelerate and turn
        boolean accel = game.getGUI().isUpPressed();
        int turn = direction(game);
        this.getPhysics().move(accel, turn);
        // shield
        checkShield(game);
        if (!game.getGUI().isShieldsPressed()) {
            isShieldOn = false;
            shipType = 0;
        }
        // fire a shot
        checkShooting(game);
        // energy regeneration
        incEnergy();
    }
}
