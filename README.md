# Hardware Directory Optimization
## Overview
This project presents a store management system for High's Hardware and Gardening. The system is designed to automate several logistical tasks, enhance the customer experience, and streamline operations. It achieves this through the manipulation and processing of text files (.txt) as its primary data storage and interaction method.

## Key Features

### Inventory Management:
- Adding new items to the store's inventory with details like name, cost, quantity, and aisle location.
- Finding the cost and quantity of items in stock.
- Selling items and updating the inventory accordingly.
### Staff Management:
- Hiring new staff members with attributes like name, age, role, and availability.
- Firing existing staff members.
- Promoting or demoting staff to different roles.
### Schedule Optimization:
- Automatically generating optimal weekly schedules for staff based on their availability and required shift coverage.
### Plank Sawing:
- Efficiently dividing long planks of lumber into smaller planks with prime number lengths, maximizing utilization and minimizing waste.
## Key Steps and Findings

### Data Input/Output: 
- All data interactions occur through .txt files. The FileUtils class handles file reading, writing, and parsing.
### Core Models: 
- The Item class represents items in inventory, and the Staff class represents store employees. These classes encapsulate relevant data and provide methods for accessing and manipulating it.
### Sub-programs:
- StaffScheduler: Implements an algorithm to create optimal staff schedules.
- SawPrimePlanks: Uses a recursive approach to break down long planks into prime length pieces.
### Command-Line Interface: 
- The Store class implements the IStore interface and provides a command-line interface to execute various actions like "ADD," "SELL," "HIRE," "SCHEDULE," etc.
## Results and Conclusion

The developed store management system successfully automates key aspects of High's Hardware's operations. The staff scheduling algorithm ensures balanced workload distribution, and the plank sawing optimization improves inventory management and reduces waste.

## Key Improvements:

- Increased operational efficiency through task automation.
- Improved staff satisfaction with more equitable scheduling.
- Resource optimization through efficient plank utilization.
## Possible Future Enhancements:

- Integration with a graphical user interface (GUI) for improved user experience.
- Incorporation of a database for more scalable and efficient data management.
- Addition of advanced features like sales tracking, customer loyalty programs, and integration with online ordering platforms.
