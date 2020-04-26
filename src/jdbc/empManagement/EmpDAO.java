package jdbc.empManagement;

import jdbc.connection.MyDBConnection;

import java.sql.*;
import java.util.*;

public class EmpDAO implements InterEmpDAO {
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	
	private void close() {
		try {
			if(pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
			if(rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Override
	public ArrayList<EmployeeDTO> getEmps() {
		
		ArrayList<EmployeeDTO> empList = new ArrayList<EmployeeDTO>();
		
		try {
			
			conn = MyDBConnection.getConn();
			
			String selectSQL = "        select E.employee_id\n"+
					"              ,E.first_name\n"+
					"              ,E.last_name\n"+
					"              ,E.email\n"+
					"              ,E.phone_number\n"+
					"              ,\n"+
					"              case when substr(E.jubun, 7, 1)in (3, 4)\n"+
					"                then '20' || substr(E.jubun, 1, 6)\n"+
					"                else '19' || substr(E.jubun, 1, 6)\n"+
					"                end as birthdate\n"+
					"              ,D.department_id\n"+
					"              ,D.department_name\n"+
					"        from emp E, dept D\n"+
					"        where E.department_id = D.department_id \n"+
					"        order by 1";
			
			pstmt = conn.prepareStatement(selectSQL);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				EmployeeDTO empDTO = new EmployeeDTO();
				DepartmentDTO deptDTO = new DepartmentDTO();
				deptDTO.setDepartmentID(rs.getInt(7));
				deptDTO.setDepartment(rs.getString(8));
				
				empDTO.setEmpID(rs.getInt(1));
				empDTO.setFirstName(rs.getString(2));
				empDTO.setLastName(rs.getString(3));
				empDTO.setFullName(rs.getString(2), rs.getString(3));
				empDTO.setEmail(rs.getString(4));
				empDTO.setMobile(rs.getString(5));
				empDTO.setBirthDate(rs.getString(6));
				empDTO.setDepartmentInfo(deptDTO);
				empList.add(empDTO);
			}

		} catch(SQLException e){			
			e.printStackTrace();
		} finally {
			close();
		}
		
		return empList;
	}


	@Override
	public ArrayList<EmployeeDTO> getContacts() {
		ArrayList<EmployeeDTO> contactsList = new ArrayList<EmployeeDTO>();
			
			try {
				
				conn = MyDBConnection.getConn();
				
				String selectSQL = "        select D.department_name\n"+
						"              ,E.first_name\n"+ 
						"			   ,E.last_name\n"+
						"              ,E.email\n"+
						"              ,E.phone_number\n"+
						"        from emp E, dept D\n"+
						"        where E.department_id = D.department_id ";
				
				pstmt = conn.prepareStatement(selectSQL);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					EmployeeDTO empDTO = new EmployeeDTO();
					DepartmentDTO deptDTO = new DepartmentDTO();
					deptDTO.setDepartment(rs.getString(1));
					
					empDTO.setFirstName(rs.getString(2));
					empDTO.setLastName(rs.getString(3));
					empDTO.setFullName(rs.getString(2), rs.getString(3));
					empDTO.setEmail(rs.getString(4));
					empDTO.setMobile(rs.getString(5));
					empDTO.setDepartmentInfo(deptDTO);
					contactsList.add(empDTO);
				}
	
			} catch(SQLException e){			
				e.printStackTrace();
			} finally {
				close();
			}
		
		return contactsList;	
	}


	@Override
	public ArrayList<EmployeeDTO> getBPeople() {
		ArrayList<EmployeeDTO> bPeople = new ArrayList<EmployeeDTO>();
		
		try {
			
			conn = MyDBConnection.getConn();
			
			String selectSQL = "select D.department_name,\n"+
					"       E.first_name,\n"+
					"       E.last_name,\n"+
					"       to_number((substr(E.jubun, 3, 2))) || '월' || substr(E.jubun, 5, 2) || '일' as birthday\n"+
					"from emp E, dept D\n"+
					"where substr(jubun, 3, 2) = extract(month from sysdate) AND\n"+
					"      E.department_id = D.department_id\n"+
					"order by birthday";
			
			pstmt = conn.prepareStatement(selectSQL);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				EmployeeDTO empDTO = new EmployeeDTO();
				DepartmentDTO deptDTO = new DepartmentDTO();
				deptDTO.setDepartment(rs.getString(1));
				
				empDTO.setFirstName(rs.getString(2));
				empDTO.setLastName(rs.getString(3));
				empDTO.setFullName(rs.getString(2), rs.getString(3));
				empDTO.setBirthDate(rs.getString(4));
				empDTO.setDepartmentInfo(deptDTO);
				bPeople.add(empDTO);
			}

		} catch(SQLException e){			
			e.printStackTrace();
		} finally {
			close();
		}
		
		return bPeople;
	}
	

	@Override
	public ArrayList<EmployeeDTO> getLoyalEmps() {
		ArrayList<EmployeeDTO> loyalEmps = new ArrayList<EmployeeDTO>();
		
		try {
			
			conn = MyDBConnection.getConn();
			
			String selectSQL = "select D.department_name,\n"+
					"       E.first_name,\n"+
					"       E.last_name,\n"+
					"       extract(month from hire_date) || '월' || extract(day from hire_date) || '일'\n"+
					"from emp E, dept D\n"+
					"where extract(month from hire_date) = extract(month from sysdate) AND\n"+
					"      E.department_id = D.department_id\n"+
					"order by 4 desc";
			
			pstmt = conn.prepareStatement(selectSQL);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				EmployeeDTO empDTO = new EmployeeDTO();
				DepartmentDTO deptDTO = new DepartmentDTO();
				deptDTO.setDepartment(rs.getString(1));
				
				empDTO.setFirstName(rs.getString(2));
				empDTO.setLastName(rs.getString(3));
				empDTO.setFullName(rs.getString(2), rs.getString(3));
				empDTO.setHiredDate(rs.getString(4));
				empDTO.setDepartmentInfo(deptDTO);
				loyalEmps.add(empDTO);
			}

		} catch(SQLException e){			
			e.printStackTrace();
		} finally {
			close();
		}
		
		return loyalEmps;
	}


	@Override
	public EmployeeDTO getSupervisor(HashMap<String, String> paraMap) {
		EmployeeDTO supervisor = null;
		
		try {
			
			conn = MyDBConnection.getConn();
			
			String selectSQL = "select D.department_name,\n"+
					"       E.first_name,\n"+
					"       E.last_name\n"+
					"from emp E, emp E2, dept D\n"+
					"where E.employee_id = E2.manager_id \n"+
					"      AND E2.department_id = D.department_ID \n"+
					"      AND lower(?) = lower(E2.first_name)\n"+
					"      AND lower(?) = lower(E2.last_name)";
			
			
			pstmt = conn.prepareStatement(selectSQL);
			pstmt.setString(1, paraMap.get("first_name"));
			pstmt.setString(2, paraMap.get("last_name"));
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				supervisor = new EmployeeDTO();
				DepartmentDTO deptDTO = new DepartmentDTO();
				deptDTO.setDepartment(rs.getString(1));
				
				supervisor.setFirstName(rs.getString(2));
				supervisor.setLastName(rs.getString(3));
				supervisor.setFullName(rs.getString(2), rs.getString(3));
				supervisor.setDepartmentInfo(deptDTO);		
			}
			
			
		} catch(SQLException e){			
			e.printStackTrace();
		} finally {
			close();
		}
		
		return supervisor;
	}


	@Override
	public AdminDTO adminLogin(HashMap<String, String> adminMap) {
		AdminDTO admin = null;

		try {
			
			conn = MyDBConnection.getConn();
			

			String selectSQL = "select D.department_name\n"+
					"      ,D.department_id\n"+
					"	  ,E.first_name\n"+
					"      ,E.last_name\n"+
					"	  ,E.email\n"+
					"	  ,E.employee_id\n"+
					"from emp E, dept D\n"+
					"where lower(E.email) = lower(?) AND E.employee_id = ? AND E.department_id = D.department_id";
			
			
			pstmt = conn.prepareStatement(selectSQL);
			pstmt.setString(1, adminMap.get("adminID"));
			pstmt.setString(2, adminMap.get("password"));
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				admin = new AdminDTO();
				DepartmentDTO deptDTO = new DepartmentDTO();
				deptDTO.setDepartment(rs.getString(1));
				deptDTO.setDepartmentID(rs.getInt(2));
				
				admin.setFirstName(rs.getString(3));
				admin.setLastName(rs.getString(4));
				admin.setFullName(rs.getString(3), rs.getString(4));
				admin.setEmail(rs.getString(5));
				admin.setEmpID(rs.getInt(6));
				admin.setDepartmentInfo(deptDTO);		
			}
			
			
		} catch(SQLException e){			
			e.printStackTrace();
		} finally {
			close();
		}
		
		return admin;
	}


	@Override
	public EmployeeDTO inquireSalary(String emp_firstName, String emp_lastName) {
		EmployeeDTO emp = null;
		
		
		try {
			conn = MyDBConnection.getConn();
			
			String selectSQL = "select *\n"+
					"from\n"+
					"(select nvl(D.department_name, '인턴')   as department_name\n"+
					"      ,nvl(D.department_id, '-1')  as department_id\n"+
					"	  ,E.first_name as first_name\n"+
					"      ,E.last_name  as last_name\n"+
					"      ,nvl(E.salary + (E.salary*E.commission_pct), E.salary) as salary\n"+
					"      ,E.commission_pct\n" +
					"from emp E left join dept D\n"+
					"on E.department_id = D.department_id\n"+
					")\n"+
					"where first_name = ? AND last_name = ?";

			pstmt = conn.prepareStatement(selectSQL);
			pstmt.setString(1, emp_firstName);
			pstmt.setString(2, emp_lastName);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				emp = new EmployeeDTO();
				DepartmentDTO dept = new DepartmentDTO();
				SalaryDTO salary = new SalaryDTO();
				
				dept.setDepartment(rs.getString(1));
				dept.setDepartmentID(rs.getInt(2));
				emp.setFirstName(rs.getString(3));
				emp.setLastName(rs.getString(4));
				emp.setFullName(rs.getString(3), rs.getString(4));
				salary.setSalary(rs.getInt(5));
				salary.setCommission_pct(rs.getDouble(6));
				
				emp.setDepartmentInfo(dept);
				emp.setSalaryInfo(salary);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return emp;
	}
}










