package MP1;

public class Stone {
    private String type;
    private int echoNumber;

    public Stone(String type) {
        this.type = type;
        this.echoNumber = -1;
    }

    public Stone(String type, int echoNumber) {
        this.type = type;
        this.echoNumber = echoNumber;
    }

    public String getType() {
        return type;
    }

    public int getEchoNumber() {
        return echoNumber;
    }

    @Override
    public String toString() {
        if (type.equals("ECHO")) {
            return "Echo Stone " + echoNumber;
        } else {
            return "Paradox Stone";
        }
    }
}