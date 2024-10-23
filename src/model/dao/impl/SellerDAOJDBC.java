package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			String sql = "SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id " + "WHERE seller.Id = ?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();

			while (rs.next()) {
				Department dept = instantiateDepartment(rs);

				Seller sel = instantiateSeller(rs, dept);
				return sel;
			}
			return null;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pst);
		}

	}

	private Seller instantiateSeller(ResultSet rs, Department dept) throws SQLException {
		Seller sel = new Seller();
		sel.setId(rs.getInt("Id"));
		sel.setName(rs.getString("Name"));
		sel.setEmail(rs.getString("Email"));
		sel.setBirthDate(rs.getDate("BirthDate"));
		sel.setBaseSalary(rs.getDouble("Basesalary"));
		sel.setDepartment(dept);
		return sel;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dept = new Department();
		dept.setId(rs.getInt("Id"));
		dept.setName(rs.getString("Name"));
		return dept;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name";
			
		
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			
			List<Seller> listAll = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			while(rs.next()) {
				Department dept = map.get(rs.getInt("DepartmentId"));
				
				if(dept == null) {
					dept = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dept);
				}
				
				Seller obj = instantiateSeller(rs, dept);
				listAll.add(obj);
			}
			
			return listAll;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}

	}

	@Override
	public List<Seller> findByDept(Department department) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id " + "WHERE DepartmentId = ? " + "ORDER BY Name";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, department.getId());
			rs = pst.executeQuery();

			List<Seller> listDeptName = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));

				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}

				Department dept = instantiateDepartment(rs);
				Seller obj = instantiateSeller(rs, dept);
				listDeptName.add(obj);
			}
			return listDeptName;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pst);
			DB.closeConnection();
		}
	}

}
