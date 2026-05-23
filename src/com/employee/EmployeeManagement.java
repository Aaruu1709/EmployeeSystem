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

					ps.setInt(1, id);
					ps.setString(2, name);
					ps.setDouble(3, salary);

					ps.executeUpdate();

					System.out.println("Employee Added Successfully!");

					break;

				// VIEW EMPLOYEES
				case 2:

					String selectQuery = "SELECT * FROM employee";

					Statement st = con.createStatement();

					ResultSet rs = st.executeQuery(selectQuery);

					while (rs.next()) {

						System.out.println(rs.getInt("id") + " " + rs.getString("name") + " " + rs.getDouble("salary"));
					}

					break;

				// SEARCH EMPLOYEE
				case 3:

					System.out.print("Enter Employee ID: ");

					int searchId = sc.nextInt();

					String searchQuery = "SELECT * FROM employee WHERE id=?";

					PreparedStatement ps2 = con.prepareStatement(searchQuery);

					ps2.setInt(1, searchId);

					ResultSet rs2 = ps2.executeQuery();

					if (rs2.next()) {

						System.out.println(
								rs2.getInt("id") + " " + rs2.getString("name") + " " + rs2.getDouble("salary"));

					} else {

						System.out.println("Employee Not Found!");
					}

					break;

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