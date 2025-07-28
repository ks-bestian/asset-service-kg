package kr.co.bestiansoft;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbTranSenegal {


	public static final String JSON_ROOT_PATH = "D:/imsi/";

	public static void main(String[] args) {

		try {


		} catch (Exception e) {
			e.printStackTrace();
		}

	}





	public Connection getPostgreSQLConnection() throws Exception
	{
		Class.forName("org.postgresql.Driver");
		return DriverManager.getConnection("jdbc:postgresql://3.39.79.144:5432/bestian?currentSchema=kgst&useUnicode=true&characterEncoding=utf8&autoReconnect=true","best","best1234");
	}

	public void makeJsonData() throws Exception {

		Connection conn = null;
		conn = getPostgreSQLConnection();
		String query = "select lng_id , lng_ty1 , lng_ty2 , lng_ty3  from com_lng_code ";


	    PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        List<Map<String, String>> messageList = new ArrayList<>();

        while (rs.next()) {
            Map<String, String> row = new HashMap<>();
            row.put("MSG_CODE", rs.getString("lng_id"));
            row.put("MESSAGE_TEXT_KG", rs.getString("lng_ty1"));
            row.put("MESSAGE_TEXT_RU", rs.getString("lng_ty2"));
            row.put("MESSAGE_TEXT_KR", rs.getString("lng_ty3"));
            messageList.add(row);
        }


	}




}
