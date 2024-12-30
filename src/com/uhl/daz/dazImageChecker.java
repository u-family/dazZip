package com.uhl.daz;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.uhl.utils.FileUtils;


/**
 * 
 * Support directory
 * 1: read directory
 * 
 * ls 
 * 
 * 
 * 
 */
public class dazImageChecker {

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
//		PostgreSQLJDBC app = new PostgreSQLJDBC();
//		app.connect();
		String dir = "D:\\Daz 3D\\Applications\\Data\\DAZ 3D\\My DAZ 3D Library\\Runtime\\Support";
		HashMap<String, File> files = new HashMap<>();
		try {
//			files = FileUtils.listFilesUsingDirectoryStream(dir);
//			System.out.println(files);
			
			System.out.println("Files in the directory:");
			
			File[] filesList = FileUtils.ListFiles(dir);
			for (File file : filesList) {
				String fileName  = file.getName();
				String type = fileName.substring( fileName.length() - 3 ).toUpperCase();
//				System.out.println(type + " : "  + fileName);
				if (fileName.startsWith("DAZ_3D_") ) {
					String baseName = fileName.substring( 0, fileName.length() - 4 );//.toUpperCase();
					if ( type.equals("DSX") ) {
						files.put(baseName, file);
					} else if ( type.equals("JPG") || type.equals("PNG") ) {
//						String baseName = fileName.substring( 0, fileName.length() - 4 ).toUpperCase();
//						System.out.println(baseName);
						if (files.containsKey(baseName)) {
							files.remove(baseName);
//							files.remove(baseName + ".DSX");
							
						}
					}
				}

//				System.out.println(file.getName());
			}
			System.out.println(files.size());
			
//			for (String file : (String)files.entrySet()) {
//				String imgName = file.replace("DAZ_3D_", "");
//				imgName = imgName.substring(0,imgName.indexOf("_")) + ".jpg";
//				System.out.println(file + " " + imgName + " ");
//			}
			for (Map.Entry<String, File> entry : files.entrySet()) { 
				String imgName = entry.getKey().replace("DAZ_3D_", "");
				imgName = imgName.substring(0,imgName.indexOf("_")) + ".jpg".trim();
				String toFile = entry.getValue().getName().replace(".dsx", ".jpg");
				System.out.println("File Name: " + entry.getKey() + ", File Path: " + entry.getValue().getPath()); 
				System.out.println(imgName + " " + toFile);
				
				String imgPath = "D:\\Daz 3D\\Applications\\Data\\DAZ 3D\\InstallManager\\Thumbnails\\";
				
				Path fromPath = null;
				System.out.println("imgPath : " + imgPath + imgName);
				try {
					fromPath = new File(imgPath, imgName).toPath();
				} catch (Exception e) {
					imgName = imgName.replace(".jpg", ".png").trim();
					System.out.println("imgPath : " + imgPath + imgName);
					fromPath = new File(imgPath, imgName).toPath();
				}
				Path toPath = new File(dir, toFile).toPath();
				System.out.println(toPath);
				FileUtils.fileCopy(fromPath, toPath);
			}
			
			System.out.println(files.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// D:\Daz 3D\Applications\Data\DAZ 3D\InstallManager\Thumbnails
		
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
