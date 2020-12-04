import java.awt.Image;

import oop.ex2.*;

/**
 * The API spaceships need to implement for the SpaceWars game.
 * It is your decision whether SpaceShip.java will be an interface, an abstract class,
 * a base class for the other spaceships or any other option you will choose.
 *
 * @author oop
 */
abstract public class SpaceShip {
    // Data Members and Constants
    protected final static int TURN_RIGHT = -1;
    protected final static int TURN_LEFT = 1;
    protected final static int DONT_TURN = 0;
    private final static int SHIELD_ENERGY = 3;
    private final static int TELEPORT_ENERGY = 140;
    private final static int FIRE_ENERGY = 19;
    private final static int ROUNDS_COOLOFF_NUM = 7;
    private final static int MIN_HEALTH = 0;
    private final static int COLLIDE_SHOT_ENERGY_DAMAGE = 10;
    private final static int BASHING_ENERGY_GAIN = 18;
    private final static int MAX_ENERGY_VAL = 210;
    private final static int CUR_ENERGY_VAL = 190;
    private final static int MAX_HEALTH_VAL = 22;
    protected int maxEnergy = MAX_ENERGY_VAL;
    protected int curEnergy = CUR_ENERGY_VAL;
    private int health = MAX_HEALTH_VAL;
    private static int roundCounter = 0;
    private SpaceShipPhysics shipPhys;
    protected boolean isShieldOn = false;
    protected int shipType; // ship type (human or enemy - shielded or not)
    private int lastRoundShot;
    private boolean isFirstShot = true;

    /**
     * SpaceShip constructor which initialises a SpaceShipPhysics object.
     */
    public SpaceShip() {
        shipPhys = new SpaceShipPhysics();
    }

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        roundCounter++;
    }

    /**
     * if the current Energy level goes above the Max Energy level, it sets
     * the Current Energy level to current Max Energy Level
     */
    protected void checkAndUpdateCurEnergy() {
        if (curEnergy > maxEnergy) {
            curEnergy = maxEnergy;
        }
    }

    /**
     * manages health and Energy loss when colliding with other ship
     * or being shot at from another ship
     */
    private void collideOrGetShot() {
        if (health > MIN_HEALTH) {
            health--;
        }
        if (maxEnergy < COLLIDE_SHOT_ENERGY_DAMAGE) {
            maxEnergy = 0;
        } else {
            maxEnergy -= COLLIDE_SHOT_ENERGY_DAMAGE;
            checkAndUpdateCurEnergy();
        }
    }

    /**
     * This method is called every time a collision with this ship occurs
     */
    public void collidedWithAnotherShip() {
        if (!isShieldOn) {
            collideOrGetShot();
        } else {
            maxEnergy += BASHING_ENERGY_GAIN;
            curEnergy += BASHING_ENERGY_GAIN;
        }
    }

    /**
     * This method is called whenever a ship has died. It resets the ship's
     * attributes, and starts it at a new random position.
     */
    public void reset() {
        maxEnergy = MAX_ENERGY_VAL;
        curEnergy = CUR_ENERGY_VAL;
        health = MAX_HEALTH_VAL;
        shipPhys = new SpaceShipPhysics();
        isFirstShot = true;
    }

    /**
     * Checks if this ship is dead.
     *
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        return health == MIN_HEALTH;
    }

    /**
     * Gets the physics object that controls this ship.
     *
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return shipPhys;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {
        if (!isShieldOn) {
            collideOrGetShot();
        }
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    public Image getImage() {
        if (shipType == 0) {
            return GameGUI.SPACESHIP_IMAGE; //human spaceship without shield
        } else if (shipType == 1) {
            return GameGUI.SPACESHIP_IMAGE_SHIELD; // human spaceship with shield on
        } else if (shipType == 2) {
            return GameGUI.ENEMY_SPACESHIP_IMAGE; // enemy spaceship without shield
        } else if (shipType == 3) {
            return GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD; // enemy spaceship with shield on
        } else {
            return null;
        }
    }

    /**
     * @return true if it's possible now to fire a shot after seven rounds passed,
     * false otherwise
     */
    private boolean isMoreThanSevenRounds() {
        return (roundCounter - lastRoundShot) >= ROUNDS_COOLOFF_NUM;
    }

    /**
     * Attempts to fire a shot.
     *
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
        if (isFirstShot && curEnergy >= FIRE_ENERGY) {
            game.addShot(shipPhys);
            curEnergy -= FIRE_ENERGY;
            lastRoundShot = roundCounter;
            isFirstShot = false;
        } else if (!isFirstShot && isMoreThanSevenRounds() && curEnergy >= FIRE_ENERGY) {
            game.addShot(shipPhys);
            curEnergy -= FIRE_ENERGY;
            lastRoundShot = roundCounter;
        }
    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        if (curEnergy >= SHIELD_ENERGY) {
            isShieldOn = true;
            curEnergy -= SHIELD_ENERGY;
        } else {
            isShieldOn = false;
        }
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
        if (curEnergy >= TELEPORT_ENERGY) {
            shipPhys = new SpaceShipPhysics();
            curEnergy -= TELEPORT_ENERGY;
        }
    }
}
