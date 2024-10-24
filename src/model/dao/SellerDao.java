package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDao {
	
	void inserir(Seller obj);
	void update(Seller obj);
	void deleteById(Integer id);
	Seller findByID(Integer id);
	List<Seller> findAll();
	List<Seller> findByDept(Department department);

}
