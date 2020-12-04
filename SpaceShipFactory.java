/**
 * A Factory Class for SpaceShips.
 */
public class SpaceShipFactory {
    private final static char human = 'h';
    private final static char runner = 'r';
    private final static char basher = 'b';
    private final static char aggressive = 'a';
    private final static char drunkard = 'd';
    private final static char special = 's';

    /**
     * this static function creates the spaceships specified in the command
     * line
     *
     * @param args - symbols representing SpaceShips names in order to be created
     * @return array of type SpaceShip including all created objects of the specified spaceships from the
     * command line
     */
    public static SpaceShip[] createSpaceShips(String[] args) {
        SpaceShip[] myShips = new SpaceShip[args.length];
        for (int i = 0; i < args.length; i++) {
            switch (args[i].charAt(0)) {
                case human:
                    myShips[i] = new HumanShip();
                    break;
                case drunkard:
                    myShips[i] = new DrunkardShip();
                    break;
                case runner:
                    myShips[i] = new RunnerShip();
                    break;
                case aggressive:
                    myShips[i] = new AggressiveShip();
                    break;
                case basher:
                    myShips[i] = new BasherShip();
                    break;
                case special:
                    myShips[i] = new SpecialShip();
                    break;
            }
        }
        return myShips;
    }
}
