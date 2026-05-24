package com.employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class EmployeeManagement {

	static final String URL = "jdbc:mysql://localhost:3306/brainworksdb";

	static final String USER = "root";

	static final String PASSWORD = "root";

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
			// here con is db connection object

			while (true) {

				System.out.println("\n===== EMPLOYEE MANAGEMENT SYSTEM =====");

				System.out.println("1. Add Employee");
				System.out.println("2. View Employees");
				System.out.println("3. Search Employee");
				System.out.println("4. Update Employee");
				System.out.println("5. Delete Employee");
				System.out.println("6. Exit");

				System.out.print("Enter Choice: ");

				int choice = sc.nextInt();

				switch (choice) {

				// ADD EMPLOYEE
				case 1:

					System.out.print("Enter ID: ");
					int id = sc.nextInt();

					sc.nextLine();

					System.out.print("Enter Name: ");
					String name = sc.nextLine();

					System.out.print("Enter Salary: ");
					double salary = sc.nextDouble();

					String insertQuery = "INSERT INTO employee VALUES(?,?,?)";

					PreparedStatement ps = con.prepareStatement(insertQuery);
					// We use PreparedStatement in JDBC to execute SQL queries safely and
//					efficiently. 
//					Instead of directly adding values into query, 
//					we use placeholders like `?` and pass values separately. 
//							This helps prevent SQL Injection attacks and also improves 
//							performance because the query can be reused multiple times. 
//							In my Employee Management System project,
//							I used PreparedStatement for insert, update, search, 
//							and delete operations.”

					ps.setInt(1, id);// We are replacing ? marks in SQL query with real values
					ps.setString(2, name);
					ps.setDouble(3, salary);
					// These methods are used to assign values to SQL query placeholders (?)
//					based on their position and data type.

					ps.executeUpdate();

					System.out.println("Employee Added Successfully!");

					break;

				// VIEW EMPLOYEES
				case 2:

					String selectQuery = "SELECT * FROM employee";

					Statement st = con.createStatement();
					// Statement is used to execute SQL queries directly without placeholders

					ResultSet rs = st.executeQuery(selectQuery);

					// ResultSet is an object in JDBC that stores data returned from a database
					// after executing a SELECT query.
					while (rs.next()) {

						System.out.println(rs.getInt("id") + " " + rs.getString("name") + " " + rs.getDouble("salary"));
					}

					break;

//					In this part of my project, I implemented the View Employees functionality.
//					First, I create a SELECT query to fetch all employee records from the database.
//					Then I use Statement to execute the query because no dynamic input is required here.
//					I execute the query using executeQuery method, which returns a ResultSet containing all
//					rows from the employee table. After that, I use a while loop with rs.next()
//					to iterate through each row one by one. Inside the loop, I retrieve employee
//					details like id, name, and salary using getter methods and display them on the console.
//					This allows us to view all employees stored in the database
//					-------------------------------------------------------------------------

				// SEARCH EMPLOYEE
				case 3:

					System.out.print("Enter Employee ID: ");

					int searchId = sc.nextInt();

					String searchQuery = "SELECT * FROM employee WHERE id=?";

					PreparedStatement ps2 = con.prepareStatement(searchQuery);

					ps2.setInt(1, searchId);

					ResultSet rs2 = ps2.executeQuery();

					if (rs2.next()) {
//“ResultSet is a JDBC object that holds the data returned by a SELECT query. 
//						It stores the result in tabular form and allows us to 
//						access data row by row using methods like next(), getInt(), and getString().”
						System.out.println(
								rs2.getInt("id") + " " + rs2.getString("name") + " " + rs2.getDouble("salary"));

					} else {

						System.out.println("Employee Not Found!");
					}

					break;

//					In this part of my project, I implemented the Search Employee functionality.
//					First, I take the employee ID from the user to find a specific record.
//					Then I create a SELECT query using a placeholder to make it dynamic and secure.
//					After that, I use PreparedStatement to pass the ID value safely into the query 
//					using setInt method. Then I execute the query using executeQuery, which returns a 
//					ResultSet. I check if the ResultSet has data using rs.next(). If data is present, 
//					I retrieve employee details like id, name, and salary using getter methods. 
//					Otherwise, I display that the employee was not found

//					---------------------------------------------------------------------

				// UPDATE EMPLOYEE
				case 4:

					System.out.print("Enter Employee ID: ");

					int updateId = sc.nextInt();

					sc.nextLine();

					System.out.print("Enter New Name: ");

					String newName = sc.nextLine();

					System.out.print("Enter New Salary: ");

					double newSalary = sc.nextDouble();

					String updateQuery = "UPDATE employee SET name=?, salary=? WHERE id=?";

					PreparedStatement ps3 = con.prepareStatement(updateQuery);

					ps3.setString(1, newName);
					ps3.setDouble(2, newSalary);
					ps3.setInt(3, updateId);

					int rows = ps3.executeUpdate();

					if (rows > 0) {

						System.out.println("Employee Updated!");

					} else {

						System.out.println("Employee Not Found!");
					}

					break;
				// In this part of my project,
//					I implemented the Update Employee functionality.
//					First, I take the employee ID from the user to identify which 
//					record needs to be updated. Then I take the new name and new salary as input.
//					After that, I create an SQL UPDATE query using placeholders to make it dynamic and secure.
//					I use PreparedStatement to pass the new values into the query using setString, 
//					setDouble, and setInt methods. Then I execute the query using executeUpdate, which returns
//					the number of rows affected. If the result is greater than zero, it means the 
//					employee data was successfully updated, otherwise it means no employee was found 
//					with that ID
//					----------------------------------------------

				// DELETE EMPLOYEE
				case 5:

					System.out.print("Enter Employee ID: ");

					int deleteId = sc.nextInt();

					String deleteQuery = "DELETE FROM employee WHERE id=?";

					PreparedStatement ps4 = con.prepareStatement(deleteQuery);

					ps4.setInt(1, deleteId);

					int deletedRows = ps4.executeUpdate();

					if (deletedRows > 0) {

						System.out.println("Employee Deleted!");

					} else {

						System.out.println("Employee Not Found!");
					}

					break;
				// In this part of my project, I implemented the Delete Employee functionality.
//					First, I take the employee ID from the user using Scanner. 
//					Then I create a SQL DELETE query using a placeholder question
//					mark to make it safe and dynamic. After that, I use PreparedStatement 
//					to pass the ID value securely into the query using setInt method. 
//					Then I execute the query using executeUpdate, which returns the number
//					of rows affected in the database. If the returned value is greater than zero,
//					it means the employee record was successfully deleted, 
//					otherwise it means no matching employee was found with that ID

				// EXIT
				case 6:

					System.out.println("Thank You!");

					con.close();

					System.exit(0);

				default:

					System.out.println("Invalid Choice!");
				}
			}

		} catch (Exception e) {

			System.out.println(e);
		}
	}
}