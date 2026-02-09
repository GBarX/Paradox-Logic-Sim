# Paradox-Logic-Sim
Java Data Structure and Algorithm University Project

# ⏳ Time Loop Explorers: Data Structures Adventure

A complex, text-based strategic adventure engine built with **Java**, focusing on the practical application of custom data structures and game logic management.

## 🕹️ Game Overview
In this simulation, 3 to 5 explorers navigate through a procedurally generated dungeon to find and use **Echo Stones** in a specific sequence to break a magical time loop. The game features a unique **Paradox Stone** mechanic that allows players to perform "Time Travel" by reverting the game state to a previous turn.

## 🛠️ Technical Implementation
This project showcases deep knowledge of **Data Structures** and **Object-Oriented Programming (OOP)**:

- **Custom Linked List Stack:** Implemented a generic `LinkedListStack<T>` to manage player inventories and game states.
- **Action Logging & Rewind System:** Uses a doubly linked list structure (`ActionLog`) to record every turn. The `restoreToTurn` logic demonstrates state management by rewinding the game, including HP, inventory, and room progress.
- **Circular Turn Management:** The `ExplorersManager` uses a circular linked logic to handle turn orders, including a "Reversed Turn Order" mechanic.
- **Dynamic Event System:** A probability-based event engine in `Rooms.java` that triggers traps, healing, or stone discoveries.

## 🛡️ Security & Robustness Perspective
As an aspiring Cybersecurity professional, I implemented several "Safe Coding" practices in this project:
- **Encapsulation:** All critical game data (HP, Inventory) is protected via private fields and controlled through public methods.
- **Custom Exception Handling:** Logic is designed to handle invalid user inputs and state inconsistencies during time jumps.
- **Data Integrity:** The `EchoStoneLog` ensures that win conditions are strictly validated in a sequential order, preventing state manipulation.

## 📂 Project Structure
- `GameManager.java`: The brain of the game, handling the main loop and time rewinds.
- `ActionLog.java`: The "Time Machine" component that records and restores history.
- `Explorer.java`: Entity class with inventory management.
- `LinkedListStack.java`: A custom, from-scratch implementation of the Stack ADT.
