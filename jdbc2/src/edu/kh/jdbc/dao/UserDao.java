package edu.kh.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dto.User;

// DAO (Data Access Object) : 데이터가 저장된 곳에 접근하는 용도의 객체
// -> DB에 접근하여 Java 에서 원하는 결과를 얻기 위해 SQL을 수행하고 결과 받환 받는 역할

public class UserDao {

	// 필드 - DB 접근 관련한 JDBC 객체 참조형 변수를 미리 선언
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	/** 전달 받은 Connection 을 이용해 DB에 접근하여 
	 *  전달 받은 아이디와 일치하는 User 정보 조회하기
	 *  @param conn  : Service 에서 발생한 Connection 객체
	 *  @param input : View 에서 입력 받은 아이디
	 * @return
	 */
	public User selectId(Connection conn, String input) {
	
		User user = null; // 결과 저장용 변수
		
		try {
		// SQL 작성
		String sql = "SELECT * FROM TB_USER WHERE USER_ID = ?";
		
		// PreparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		
		// ?(placeholder)에 알맞은 값을 대입
		pstmt.setString(1, input);
			
		// SQL 수행 후 결과 반환 받기
		rs = pstmt.executeQuery();
		
		// 조회 결과가 있을 경우
		// -> 중복되는 아이디가 없을 경우 1행만 조회 되기 때문에 while 보단 if 를 사용하는게 효과적!!
		if(rs.next()) {
			// 각 컬럼의 값 얻어도기
			int userNo = rs.getInt("USER_NO");
			String userId = rs.getString("USER_ID");
			String userPw = rs.getString("USER_PW");
			String userName = rs.getString("USER_NAME");
			
			// java.sql.Date 활욜
			Date enrollDate = rs.getDate("ENROLL_DATE");
			
			// 조회된 컬럼값을 이용해 User 객체 생성
			user = new User(userNo, userId, userPw, userName, enrollDate.toString());
		}
		
		} catch (SQLException e) {
		
			e.printStackTrace();
		} finally {
			// 사용한 JDBC 객체 자원 반환 (close)
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
			
			// Connection 객체는 service 에서 close!!
		}
		return user; // 결과 반환 (생성된 user 또는 null)
	}
}
