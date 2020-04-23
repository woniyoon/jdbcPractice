package jdbc.empManagement;

import jdbc.connection.MyDBConnection;

import java.sql.*;
import java.util.ArrayList;

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
}










