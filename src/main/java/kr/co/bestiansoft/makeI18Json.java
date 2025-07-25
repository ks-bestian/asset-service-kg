package kr.co.bestiansoft;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.io.FileWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class makeI18Json {


	public static final String JSON_ROOT_PATH = "C:/imsi/";

	public static void main(String[] args) {

		try {

			makeI18Json mc = new makeI18Json();
			mc.makeJsonData();

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

        makeFile(messageList,"kg");
        makeFile(messageList,"ru");
        makeFile(messageList,"kr");

	}


	public void makeFile(List<Map<String, String>> messageList,String lang) throws Exception {

		String jsonPath = JSON_ROOT_PATH+lang+".json";

	    //Map<String, Map<String, String>> root = new HashMap<>();
	    //Map<String, String> msgMap = new LinkedHashMap<>();

	    Map<String, Object> root = new HashMap<>();
	    Map<String, String> msgMap = new HashMap<>();

        for(Map<String, String> map:messageList) {

        	if("kg".equals(lang)) {
        		msgMap.put(map.get("MSG_CODE"), map.get("MESSAGE_TEXT_KG"));
        	} else if("ru".equals(lang)) {
        		msgMap.put(map.get("MSG_CODE"), map.get("MESSAGE_TEXT_RU"));
        	} else {
        		msgMap.put(map.get("MSG_CODE"), map.get("MESSAGE_TEXT_KR"));
        	}
        }

        root.putAll(msgMap);
        root.put("msg", msgMap);


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(root);

       // String jsonOutput2 = gson.toJson(msgMap);

        try (FileWriter writer = new FileWriter(jsonPath)) {
            writer.write(jsonOutput);
            System.out.println(jsonPath+" : json file generated!");
        }
	}



}
