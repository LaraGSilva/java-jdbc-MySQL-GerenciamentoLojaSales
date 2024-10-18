package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;

	}

	@Override
	public void inserir(Seller obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findByID(Integer id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		

		try {
			String sql = 
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();

			while (rs.next()) {
				Department dept = new Department();
				dept.setId(rs.getInt("Id"));
				dept.setName(rs.getString("Name"));

				Seller sel = new Seller();
				sel.setId(rs.getInt("Id"));
				sel.setName(rs.getString("Name"));
				sel.setEmail(rs.getString("Email"));
				sel.setBirthDate(rs.getDate("BirthDate"));
				sel.setBaseSalary(rs.getDouble("Basesalary"));
				sel.setDepartment(dept);
				return sel;
			}
			return null;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally{
			DB.closeResultSet(rs);
			DB.closeStatement(pst);
		}

	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
