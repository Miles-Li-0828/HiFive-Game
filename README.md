# HiFive Game Project

## Overview

The **HiFive Game** is a card game designed as part of a software engineering project at the University of Melbourne. This project focuses on enhancing a previously flawed implementation by applying sound software engineering principles and object-oriented design patterns. Our improvements aimed to make the codebase more modular, maintainable, and extendable.

### Game Rules
- **Objective**: Players aim to maximize their scores by forming combinations of cards that match specific scoring criteria.
- **Setup**: Each player starts with two cards, and the game progresses through four rounds. Players must draw a card, evaluate their hand, and then decide which card to discard to maximize their score.
- **Scoring**: Various scoring algorithms are applied based on the cards each player retains. Points are awarded based on card combinations that meet the game’s criteria.

The project includes the core gameplay of HiFive, player types (including a clever AI), and multiple scoring strategies, which were designed and implemented using key design patterns to ensure code quality and maintainability.

## Table of Contents

1. [Features](#features)
2. [Installation](#installation)
3. [Usage](#usage)
4. [Key Design Patterns](#key-design-patterns)
5. [Clever Computer Player](#clever-computer-player)
6. [Future Enhancements](#future-enhancements)

## Features

- **Refactored Game Structure**: The original monolithic `HiFive` class was broken down into smaller, more specialized classes, each responsible for a specific aspect of the game.
- **Multiple Player Types**: The game supports different player types:
  - Human Player
  - Basic Computer Player
  - Clever Computer Player (using a strategic decision-making algorithm)
- **Scoring System**: A flexible scoring system that allows for multiple scoring rules through the implementation of different scoring algorithms.
- **Wild Card Implementation**: Wild cards with additional functionalities were implemented using a decorator pattern, allowing the flexibility to add new card behaviors.

## Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/Miles-Li-0828/HiFive-Game.git
   ```
2. Ensure that you have **Java JDK 11** or higher installed.

## Usage

To run the game:

1. Compile the project using:
   ```sh
   javac -d bin src/hifive/*.java
   ```
2. Run the game:
   ```sh
   java -cp bin hifive.Driver
   ```

The `Driver` class initializes the game with the chosen player modes and deck configuration.

## Key Design Patterns

### 1. Factory Pattern
The **Factory Pattern** was applied to handle the initialization of different player entities. The `PlayerFactory` class was introduced to facilitate the creation of various types of players like `HumanPlayer`, `BasicComputerPlayer`, and `CleverComputerPlayer`.

### 2. Strategy Pattern
The **Strategy Pattern** was employed to manage different scoring rules, encapsulating four different scoring algorithms (`FiveCalculator`, `SumFiveCalculator`, `DifferenceFiveCalculator`, and `NoFiveCalculator`). This approach allows for easy extension by adding new scoring strategies in the future.

### 3. Composite Pattern
The **Composite Pattern** was used to calculate player scores by combining different scoring calculators in the `CompositeCalculator` class. This design enables a flexible scoring system that calculates the maximum score by aggregating all possible scoring methods.

### 4. Decorator Pattern
The **Decorator Pattern** was applied to implement wild card functionalities. The `CardDecorator` class extends the base `Card` class, enabling additional behaviors and characteristics for wild cards like `WildCardA`, `WildCardJ`, `WildCardQ`, and `WildCardK`.

### 5. Singleton Pattern
The **Singleton Pattern** was used for the `FactoryCalculator` class to ensure consistency between calculators and reduce memory usage. This approach ensures there is only one instance of the `FactoryCalculator` throughout the application.

## Clever Computer Player

The **Clever Computer Player** utilizes a greedy algorithm to maximize the score in each round. The implementation involves:

- **Card Evaluation**: The Clever Computer Player evaluates all cards in its hand, simulating potential scores when discarding one card.
- **Score Calculation**: Uses the `CompositeCalculator` to determine the score for each possible combination, selecting the card that results in the highest score.
- **Greedy Approach**: The player makes locally optimal decisions in each round, selecting the best two cards and discarding the least favorable one.

### Limitations
- The player’s strategy does not consider the cards played by other players since HiFive consists of only four rounds, and tracking all cards would increase the game's complexity without significant gains.

## Future Enhancements

- **Extended AI Strategies**: Introduce additional decision-making algorithms for computer players, allowing them to learn and adapt during gameplay.
- **Improved User Interface**: Develop a graphical interface to enhance the gaming experience.
- **Network Play**: Extend the game to support multiplayer functionality over a network.
