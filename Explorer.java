package MP1;

public class Explorer {
    private String name;
    private int hitPoints;
    private int treasurePoints;
    private LinkedListStack<Stone> inventory;

    public Node<Explorer> prev;
    public Node<Explorer> next;

    public Explorer(String name) {
        this.name = name;
        this.hitPoints = 3;
        this.treasurePoints = 0;
        this.inventory = new LinkedListStack<>();
    }

    public String getName() {
        return name;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getTreasurePoints() {
        return treasurePoints;
    }

    public LinkedListStack<Stone> getInventory() {
        return inventory;
    }

    public void takeDamage(int damage) {
        this.hitPoints -= damage;
        if (this.hitPoints < 0) {
            this.hitPoints = 0;
        }
    }

    public void heal(int amount) {
        this.hitPoints += amount;
    }

    public void addTreasure(int amount) {
        this.treasurePoints += amount;
    }

    public void addStone(Stone stone) {
        inventory.push(stone);
    }

    public boolean isAlive() {
        return this.hitPoints > 0;
    }

    public boolean hasEchoStone() {
        Node<Stone> current = inventory.top();
        while (current != null) {
            if (current.data.getType().equals("ECHO")) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public boolean hasParadoxStone() {
        Node<Stone> current = inventory.top();
        while (current != null) {
            if (current.data.getType().equals("PARADOX")) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println(name + "'s Inventory:");
        Node<Stone> current = inventory.top();
        if (current == null) {
            System.out.println("  Empty");
            return;
        }
        while (current != null) {
            System.out.println("  - " + current.data.toString());
            current = current.next;
        }
    }

    public void setHitPoints(int hp) {
        this.hitPoints = hp;
    }

    public void setTreasurePoints(int points) {
        this.treasurePoints = points;
    }

    public void clearInventory() {
        this.inventory = new LinkedListStack<>();
    }
}