package model.dao;

import model.dao.impl.SellerDAOJDBC;

public class DAOFactory {

	public static SellerDao createSellerDao() {
		return new SellerDAOJDBC();
	}
}
 