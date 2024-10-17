# HiFive Game Project

## Overview

The **HiFive Game** is a strategic card game developed as part of the SWEN30006 Project 2 assignment. This project aims to create a more intelligent and maintainable software design using various object-oriented programming principles and design patterns. The HiFive game consists of multiple rounds where players attempt to discard cards to maximize their score. The primary goal was to refactor an initially monolithic and poorly designed codebase into a modular, maintainable, and extendable software application.

## Features

1. **Player Types:**
   - Human Player
   - Basic Computer Player
   - Clever Computer Player: Designed to make more strategic decisions by evaluating both immediate scoring potential and future probabilities.

2. **Card Types:**
   - Standard Cards
   - Wild Cards with Decorator Pattern: Allows cards to have additional properties, enhancing versatility.

3. **Game Rules:**
   - The game revolves around the concept of forming combinations to achieve the target number of 5.
   - Players aim to maximize their score each round by strategically discarding cards.
   
## Design Patterns

The following design patterns were used to achieve a well-structured and modular architecture:

1. **Factory Pattern** - Used for the creation of player entities (`PlayerFactory`) and decorated card instances (`CardDecoratorFactory`), making it easy to add new types of players and card functionalities.

2. **Strategy Pattern** - Implemented to handle different scoring rules, allowing for flexible switching between different scoring strategies.

3. **Composite Pattern** - Applied for the score calculation (`CompositeCalculator`) to determine the highest score by combining multiple scoring strategies.

4. **Decorator Pattern** - Used to add wild card functionalities, enabling cards to take on multiple values without modifying the original card class.

5. **Singleton Pattern** - Applied to the `FactoryCalculator` class to ensure consistent creation of calculator objects and efficient memory usage.

## Clever Computer Player

The **Clever Computer Player** was introduced to enhance gameplay through a strategic two-stage approach:

1. **Maximizing Immediate Score**: Evaluates each card in hand and discards the one that maximizes the score achievable with the remaining cards.
2. **Considering Future Potential**: If no immediate benefit is found, the player evaluates the probabilities of forming winning combinations using the remaining cards in the deck.

The Clever Computer Player uses game metrics and discarded cards to make informed decisions, balancing both immediate gains and potential future outcomes. This makes the gameplay more challenging and dynamic.

## Improvements Over the Original Design

The original implementation of the HiFive game suffered from several issues:

- **High Coupling & Lack of Modularity**: All functionalities were crammed into a single monolithic class.
- **No Encapsulation**: Player behaviors, scoring mechanisms, and game logic were bundled together, leading to low maintainability.
- **Absence of Object-Oriented Principles**: No use of inheritance, polymorphism, or proper abstraction.

### Refactoring Highlights

- **Single Responsibility Principle**: Each game entity was refactored into a distinct class with a single responsibility, such as `Player`, `Card`, and `ScoreCalculator`.
- **Player Class Refactoring**: Player logic was encapsulated into different classes (`HumanPlayer`, `BasicComputerPlayer`, `CleverComputerPlayer`), with shared behavior abstracted into a common `Player` base class.
- **Scoring Mechanism**: The scoring rules were encapsulated using the Strategy and Composite patterns, making it easy to extend or modify how scores are calculated.

## How to Run

1. **Requirements**:
   - Java 11 or above.
   - Dependencies: Ensure that the `JGameGrid` library is included in the classpath for graphical rendering.

2. **Running the Game**:
   - Compile all Java files.
   - Run the `Driver` class to start the game.

## Files in the Repository

- **HiFive.java**: The main game logic class (refactored).
- **Driver.java**: Entry point for running the game.
- **Player Classes**: Includes `HumanPlayer`, `BasicComputerPlayer`, and `CleverComputerPlayer`.
- **Score Calculators**: Classes for different scoring strategies (`FiveCalculator`, `SumFiveCalculator`, etc.).
- **Decorator Classes**: Classes for adding wild card properties to standard cards.

## Future Enhancements

- **Learning Mechanism**: Introduce a learning mechanism for the Clever Computer Player to adapt strategies over time.
- **GUI Enhancements**: Improve the game’s user interface for a better player experience.
- **Additional Game Modes**: Add new game modes to further increase replayability and challenge.

## Contributors

- **Miles Li** (liyueming828@gmail.com)
- **Skylar Khant** (kyishink@student.unimelb.edu.au)
- **Ngoc Thanh Lam Nguyen** (ngocthanhlam@student.unimelb.edu.au)

## License

This project is licensed under the MIT License.
