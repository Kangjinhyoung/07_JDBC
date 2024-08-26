package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample3 {
	public static void main(String[] args) {
		// 입력 받은 최소 급여 이상, 이하를 받는 사원의 사번, 이름, 급여를 급여 내림차순으로 조회
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String type = "jdbc:oracle:thin:@"; // 드라이버의 종류
			String host = "localhost"; // DB 서버 컴퓨터의 IP 또는 도메인 주소
			String port = ":1521"; // 프로그램 연결을 위한 구분 번호
			String dbName = ":XE"; // DBMS 이름
			String userName = "KH_KJH"; // 사용자 계정명
			String password = "KH1234";
			
			conn = DriverManager.getConnection(type + host + port + dbName , userName, password);
			
			Scanner sc = new Scanner(System.in);
			System.out.println("최소 급여 : ");
			int min = sc.nextInt();
			
			System.out.println("최대 급여 : ");
			int max = sc.nextInt();
			
			String sql = 
			"SELECT EMP_ID, EMP_NAME, SALARY FROM EMPLOYEE WHERE SALARY BETWEEN " + min + " AND " + max + " ORDER BY SALARY DESC";
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			int count = 0;
			while(rs.next()) {
				
				count++; 
				
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				int salary = rs.getInt("SALARY");
				
				System.out.printf("%s / %s / %d원 \n", empId, empName, salary);
			}
			System.out.println("총원 : " + count + " 명");
			
		} catch(Exception e) {
			e.printStackTrace();
			
		}finally {
			try {
				if(rs   != null)   rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		}
	}
}
