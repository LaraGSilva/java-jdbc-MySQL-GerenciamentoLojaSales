package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
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

	SimpleDateFormat sdf = new SimpleDateFormat();
	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;

	}

	@Override
	public void inserir(Seller obj) {
		
		PreparedStatement pst = null;
		
		try {
	
			pst = conn.prepareStatement("INSERT INTO seller\r\n"
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId)\r\n"
					+ "VALUES\r\n"
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1,obj.getName());
			pst.setString(2, obj.getEmail());
			pst.setDate(3,new java.sql.Date(obj.getBirthDate().getTime()));
			pst.setDouble(4, obj.getBaseSalary());
			pst.setInt(5, obj.getDepartment().getId());

			int rowsAffected = pst.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = pst.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			}
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	@Override
	public void update(Seller obj) {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(
					  "UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "WHERE Id = ?");
		
			pst.setString(1,obj.getName());
			pst.setString(2, obj.getEmail());
			pst.setDate(3,new java.sql.Date(obj.getBirthDate().getTime()));
			pst.setDouble(4, obj.getBaseSalary());
			pst.setInt(5, obj.getDepartment().getId());
			
			int rowsAffected = pst.executeUpdate();
			System.out.println("As linhas afetadas são: " + rowsAffected);
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement pst = null;
		
		try {
			pst = conn.prepareStatement(
					"DELETE FROM seller\r\n"
					+ "WHERE Id = ?");
			pst.setInt(1,id);
			int rowsAffected = pst.executeUpdate();
			System.out.println("Linhas afetadas " + rowsAffected);
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}

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
			String sql = "SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id " + "ORDER BY Name";

			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();

			List<Seller> listAll = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {
				Department dept = map.get(rs.getInt("DepartmentId"));

				if (dept == null) {
					dept = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dept);
				}

				Seller obj = instantiateSeller(rs, dept);
				listAll.add(obj);
			}

			return listAll;
		} catch (SQLException e) {
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
