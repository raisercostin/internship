package ro.dcsi.internship;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvExporter {
	CsvExporter(){
		System.out.println("csv exporter called");
	}
	public void export(String inputFileName, String outputFileName) throws IOException{
		FileReader fileReader = new FileReader(inputFileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		FileWriter fileWriter = new FileWriter(outputFileName);
		String s;
		try{
			while((s = bufferedReader.readLine()) != null){
				fileWriter.append(s + " \n");
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
		fileReader.close();
		fileWriter.close();
	}
	/*
	public List<User> readUsers(String fileName){
		ArrayList<User> userArray = new ArrayList<User>();
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String s;
			while((s = bufferedReader.readLine()) != null){
				userArray.add(new User("user","email",s));
			}
			return userArray;
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<User>();
		}
	}
*/
}