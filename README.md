# RetailSync-Java

A robust eCommerce desktop application built with **Java Swing** and **MySQL**. This system provides a full-featured retail management interface with real-time database synchronization via JDBC.

## Features
- **User Authentication:** Secure login/signup for Customers and Sellers.
- **Product Management:** Dynamic catalog with search and filtering.
- **Transaction Logic:** Integrated cart system with persistent wallet tracking.
- **Seller Dashboard:** Specialized interface for inventory and order management.

## Technical Architecture
- **Language:** Java
- **UI Framework:** Java Swing / AWT
- **Database:** MySQL 8.0
- **Driver:** JDBC (Connector/J)

## Setup
1. Execute `tables.sql` in your MySQL environment.
2. Configure credentials in `src/ecommerce/GlobalVariables.java`.
3. Run `Main.java` to launch the application.