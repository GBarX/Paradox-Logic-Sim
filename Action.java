package MP1;

public class Action {
    private String explorerName;
    private String actionType;
    private int roomNumber;
    private int stoneNumber;
    private int turnNumber;
    private String eventDescription;
    private int hpChange;
    private int treasureChange;
    private String stoneFound;

    public Action(String explorerName, String actionType, int roomNumber, int turnNumber) {
        this.explorerName = explorerName;
        this.actionType = actionType;
        this.roomNumber = roomNumber;
        this.turnNumber = turnNumber;
        this.stoneNumber = -1;
        this.eventDescription = "";
        this.hpChange = 0;
        this.treasureChange = 0;
        this.stoneFound = null;
    }

    public Action(String explorerName, String actionType, int roomNumber, int stoneNumber, int turnNumber) {
        this.explorerName = explorerName;
        this.actionType = actionType;
        this.roomNumber = roomNumber;
        this.stoneNumber = stoneNumber;
        this.turnNumber = turnNumber;
        this.eventDescription = "";
        this.hpChange = 0;
        this.treasureChange = 0;
        this.stoneFound = null;
    }

    public void setEventInfo(String description, int hpChange, int treasureChange, String stoneFound) {
        this.eventDescription = description;
        this.hpChange = hpChange;
        this.treasureChange = treasureChange;
        this.stoneFound = stoneFound;
    }

    public String getExplorerName() {
        return explorerName;
    }

    public String getActionType() {
        return actionType;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getStoneNumber() {
        return stoneNumber;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public int getHpChange() {
        return hpChange;
    }

    public int getTreasureChange() {
        return treasureChange;
    }

    public String getStoneFound() {
        return stoneFound;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    @Override
    public String toString() {
        if (actionType.equals("USE_ECHO_STONE")) {
            return "Turn " + turnNumber + ": " + explorerName + " used Echo Stone " + stoneNumber + " in Room "
                    + roomNumber;
        } else if (actionType.equals("MOVE")) {
            String result = "Turn " + turnNumber + ": " + explorerName + " moved to Room " + roomNumber;
            if (!eventDescription.isEmpty()) {
                result += " | " + eventDescription;
            }
            return result;
        } else if (actionType.equals("USE_PARADOX_STONE")) {
            return "Turn " + turnNumber + ": " + explorerName + " used Paradox Stone in Room " + roomNumber;
        }
        return "Turn " + turnNumber + ": " + explorerName + " performed " + actionType;
    }
}