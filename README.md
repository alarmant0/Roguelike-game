# Roguelike Game README

## Overview
Roguelike Game with dungeons, enemies and collecting loot.

## Installation and Setup
1. **Clone the Repository**: 
    - Clone this repository to your local machine using Git:
      ```
      git clone https://github.com/alarmant0/Roguelike-game
      ```

2. **Open in Java IDE**:
    - Open your preferred Java Integrated Development Environment (IDE)
    - Import the project into your IDE

3. **Build and Compile**:
    - Build the project to compile the Java code in your IDE.

## Running the Game
1. **Execute Main Class**:
    - Run the main class to start the game.

## Creating a JAR File
1. **Build JAR in IDE**:
    - In your Java IDE, look for an option to build an executable JAR file.
    - Select the Main class and specify the output location for the JAR file.

2. **Build JAR using Command Line**:
    - Navigate to the root directory of the project in a terminal or command prompt.
    - Use the following command to compile the Java code and create a JAR file:
      ```
      javac -d bin src/*.java
      jar cfe Roguelike.jar Main -C bin .
      ```

3. **Run the JAR File**:
    - Once the JAR file is created, you can run the game by executing the JAR file:
      ```
      java -jar Roguelike.jar
      ```

## Additional Notes
- Ensure you have a Java Development Kit (JDK) installed on your system to compile and run the game.
