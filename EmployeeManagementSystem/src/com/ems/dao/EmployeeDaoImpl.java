package com.ems.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ems.model.Employee;
@Component
public class EmployeeDaoImpl implements EmployeeDao {

	@Autowired
	Connection con;
	@Override
	public boolean save(Employee emp) throws Exception{
		// TODO Auto-generated method stub
		
		PreparedStatement ps=con.prepareStatement("insert into Employee values (?,?,?,?)");
		ps.setString(1,emp.getEmpId());
		ps.setString(2,emp.getEmpName());
		LocalDate dobRaw=emp.getDob();
//		String strDate=dobRaw.getYear()+"-"+dobRaw.getMonthValue()+"-"+dobRaw.getDayOfMonth();
		ps.setDate(3, new java.sql.Date(dobRaw.getYear()-1900,dobRaw.getMonthValue()-1,dobRaw.getDayOfMonth()));
		ps.setFloat(4, emp.getBasicSalary());
		int r=ps.executeUpdate();
		
		return r>0?true:false;
	}

	@Override
	public boolean delete(String empId) throws Exception {
		// TODO Auto-generated method stub
		
		PreparedStatement ps=con.prepareStatement("delete from employee where empid=?");
		ps.setString(1,empId);
		int r=ps.executeUpdate();
		return r>0?true:false;
	}

	@Override
	public boolean update(Employee emp) throws Exception{
		// TODO Auto-generated method stub
	
		PreparedStatement ps=con.prepareStatement("update Employee set empname=?,dob=?,salary=? where empid=?");
		ps.setString(1,emp.getEmpName());
		LocalDate dobRaw=emp.getDob();
		ps.setDate(2, new java.sql.Date(dobRaw.getYear(),dobRaw.getMonthValue(),dobRaw.getDayOfMonth()));
		ps.setFloat(3, emp.getBasicSalary());
		ps.setString(4, emp.getEmpId());
		int r=ps.executeUpdate();
		return r>0?true:false;

	}

	@Override
	public Employee getEmployee(String empId) throws Exception {
		// TODO Auto-generated method stub

		PreparedStatement ps=con.prepareStatement("select * from Employee where empid=?");
		ps.setString(1, empId);
		ResultSet rs=ps.executeQuery();
		if(rs.next()) {
			java.sql.Date rowdate=rs.getDate(3);
			Employee emp=new Employee(rs.getString(1),rs.getString(2),LocalDate.of(rowdate.getYear(),rowdate.getMonth(),rowdate.getDate()),rs.getFloat(4));
			return emp;
		}
		return null;
	}

	@Override
	public List<Employee> getAllEmployees() throws Exception {
		// TODO Auto-generated method stub
	
		PreparedStatement ps=con.prepareStatement("select * from Employee");
		List<Employee> emplist=new ArrayList<>();
		
		ResultSet rs=ps.executeQuery();
		while(rs.next()) {
			java.sql.Date rowdate=rs.getDate(3);
			Employee emp=new Employee(rs.getString(1),rs.getString(2),LocalDate.of(rowdate.getYear(),rowdate.getMonth(),rowdate.getDate()),rs.getFloat(4));
			emplist.add(emp);
		}

		return emplist;
	}


}
