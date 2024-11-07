# BookWay
BookWay is a Java-based application designed for managing book rental transactions efficiently. This system allows users to register, log in, and seamlessly rent and return books while providing detailed records. With a user-friendly interface, BookWay streamlines the rental process, enhancing the experience for both users and administrators.

### Features
BookWay consists of two main role:

**Admin Features:**
-	Login: Admins can securely log in to the system.
-	Add Book: Easily add new books to the database to expand the collection.
-	View Books: Access the complete list of available books in the database.

**User Features:**
-	Registration: Users can create accounts to access rental services.
-	Login: Users can log in to their accounts.
-	View Books: Users can browse through the available books.
-	Rent Book: Facilitate book rental transactions.
-	Return Book: Manage the return of previously rented books.

### Database Structure
The application utilizes a structured database with the following tables:
1. appuser: Manages user accounts (user ID, username, password, role).
2. books: Stores book details (book ID, title, author, publication year, price).
3. history: Records rental transactions (transaction ID, username, book ID, book title, price, phone number, payment method, rental date, return date).
4. status: Tracks the rental status of books (book ID, rental status).
5. tmp: Temporary storage for name variables.

### Technologies Used
-	Java 17
-	JavaFX SDK 19

### Getting Started
1. Clone the repository to your local machine.
2. Ensure you have Java 17 and JavaFX SDK 19 installed.
