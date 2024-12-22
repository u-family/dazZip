package com.uhl.daz;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ImageCheck01 {

	private final String url = "jdbc:postgresql://localhost:5432/mydb";
	private final String user = "postgres";
	private final String password = "<add your password>";

	public Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("Connected to the PostgreSQL server successfully.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return conn;
	}

	public static void main(String[] args) {
		PostgreSQLJDBC app = new PostgreSQLJDBC();
		app.connect();
	}

	public void test() {
		// File path is passed as parameter
		File file = new File("C:\\Users\\pankaj\\Desktop\\test.txt");

		// Note: Double backquote is to avoid compiler
		// interpret words
		// like \test as \t (ie. as a escape sequence)

		// Creating an object of BufferedReader class
		BufferedReader br = new BufferedReader(new FileReader(file));

		// Declaring a string variable
		String st;
		// Condition holds true till
		// there is character in a string
		while ((st = br.readLine()) != null)

			// Print the string
			System.out.println(st);
	}

	public readFile() {
        String fileName = "file.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
     }

	public static void readDiretory(String[] args) {
		File folder = new File("directory_path");
		String[] extensions = new String[] { "txt" };
		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				for (String extension : extensions) {
					if (name.endsWith("." + extension)) {
						return true;
					}
				}
				return false;
			}
		};
		File[] listOfFiles = folder.listFiles(filter);
		for (File file : listOfFiles) {
			if (file.isFile()) {
				System.out.println(file.getName());
			}
		}
	}
}
