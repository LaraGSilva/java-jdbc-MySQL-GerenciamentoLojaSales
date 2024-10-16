package application;

import java.util.Date;

import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		Department dpt = new Department(1, "Tecnologia da Informação");
		System.out.println(dpt.toString());
		
		Seller seller = new Seller(20,"Bob", "Bob@gmail.com", new Date(), 3000.0, dpt);
		System.out.println(seller.toString());
	}

}
