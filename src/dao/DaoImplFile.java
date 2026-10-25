package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Amount;
import model.Employee;
import model.Product;
import model.Sale;

public class DaoImplFile implements Dao {
	
	
	@Override
	public void connect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub

	}

	@Override
	public Employee getEmployee(int employeeId, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	
		@Override
		public boolean writeInventory(ArrayList<Product> inventory) {
			// define file name based on date -> inventory_yyyy-mm-dd.txt
			LocalDate myObj = LocalDate.now();
			String fileName = "inventory_" + myObj.toString() + ".txt";

			// locate file, path and name
			File f = new File(System.getProperty("user.dir") + File.separator + "files" + File.separator + fileName);

			// try-with-resources: cierra pw (y fw) automáticamente
			try (FileWriter fw = new FileWriter(f, false);
				 PrintWriter pw = new PrintWriter(fw)) {

				// write line by line
				int counterProduct = 1;
				for (Product product : inventory) {
					// format line TO BE -> 1;Product:Manzana;Stock:10;
					pw.println(counterProduct + ";Product:" + product.getName() + ";Stock:" + product.getStock() + ";");
					counterProduct++;
				}

				// last line TO BE -> Total number of products:2;
				pw.println("Total number of products:" + inventory.size() + ";");

				return true;

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	
	
	@Override
	public ArrayList<Product> getInventory() {
		ArrayList<Product> inventory = new ArrayList<Product>();
		
		// locate file, path and name
		File f = new File(System.getProperty("user.dir") + File.separator + "files/inputInventory.txt");
		
		try {			
			// wrap in proper classes
			FileReader fr = new FileReader(f);				
			BufferedReader br = new BufferedReader(fr);
			
			// read first line
			String line = br.readLine();
			
			// process and read next line until end of file
			while (line != null) {
				// split in sections
				String[] sections = line.split(";");
				
				String name = "";
				double wholesalerPrice = 0.0;
				int stock = 0;
				
				// read each sections
				for (int i = 0; i < sections.length; i++) {
					// split data in key(0) and value(1) 
					String[] data = sections[i].split(":");
					
					switch (i) {
					case 0:
						// format product name
						name = data[1];
						break;
						
					case 1:
						// format price
						wholesalerPrice = Double.parseDouble(data[1]);
						break;
						
					case 2:
						// format stock
						stock = Integer.parseInt(data[1]);
						break;
						
					default:
						break;
					}
				}
				
				// Guardamos el producto leído directamente en la lista local:
				inventory.add(new Product(name, new Amount(wholesalerPrice), true, stock));
				
				// read next line
				line = br.readLine();
			}
			
			fr.close();
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return inventory;
	
	
		
	}

}
