package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection conn;

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserir(Department obj) {
		PreparedStatement pst = null;

		try {

			pst = conn.prepareStatement("insert department (id, name) values (?,?)",
					java.sql.Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, obj.getId());
			pst.setString(2, obj.getName());

			int rowsAffected = pst.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = pst.getGeneratedKeys();
				System.out.println("ID gerados pela inserção: " + rs);
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

	}

	@Override
	public void update(Department obj) {
		PreparedStatement pst = null;

		try {
			pst = conn.prepareStatement("update department set name=? where id=?",
					java.sql.Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, obj.getName());
			pst.setInt(2, obj.getId());

			int rowsAffected = pst.executeUpdate();
			System.out.println(rowsAffected);

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement pst = null;

		try {
			String sql = "delete from department where id = ?";
			pst = conn.prepareStatement(sql);

			pst.setInt(1, id);

			int rowsAffeted = pst.executeUpdate();
			System.out.println("Total de linhas deletadas: " + rowsAffeted);

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

	}

	@Override
	public Department findByID(Integer id) {
		PreparedStatement pst = null;
		ResultSet rs1 = null;

		try {
			String sql = "Select d.id, d.name from department d where id=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);

			rs1 = pst.executeQuery();
			while (rs1.next()) {
				Department dept = new Department();
				dept.setId(rs1.getInt("Id"));
				dept.setName(rs1.getString("Name"));
				return dept;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement pst = null;
		ResultSet rs2 = null;

		try {
			String sql = "Select d.id, d.name from department as d";
			pst = conn.prepareStatement(sql);
			
			rs2 = pst.executeQuery();
			List<Department> listDept = new ArrayList<Department>();
			
			while (rs2.next()) {
				Department dept = new Department();
				dept.setId(rs2.getInt("Id"));
				dept.setName(rs2.getString("Name")); 
				listDept.add(dept);
			}
			return listDept;		
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		
	}

}
