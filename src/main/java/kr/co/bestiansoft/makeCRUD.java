package kr.co.bestiansoft;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;


public class makeCRUD {



	public static void main(String[] args) {

		makeCRUD o = new makeCRUD();
		try {
			o.generateSQL("sponet.co.kr:3306"	//db아이피
					     ,"dev_bm_manager"	//db명
					     , "best"	//계정
					     , "best1234"	//비밀번호
					     , "c:\\starproject_file"	//저장경로
					     );
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	private Connection getMySQLConnection(String ip, String db, String user, String pwd) throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://"+ip+"/"+db+"?user="+user+"&password="+pwd);
	}

	private Connection getPostgreSQLConnection(String ip, String db, String user, String pwd) throws Exception
	{
		Class.forName("org.postgresql.Driver");
		return DriverManager.getConnection("jdbc:postgresql://3.39.79.144:5432/bestian?currentSchema=kgst&useUnicode=true&characterEncoding=utf8&autoReconnect=true",user,pwd);
	}

	private Connection getOracleConnection(String ip, String db, String user, String pwd) throws Exception
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		return DriverManager.getConnection("jdbc:oracle:thin:@"+ip+":1521:"+db, user, pwd);
	}

	public void generateSQL(String host, String db, String user, String pwd, String filePath)
	{
		Connection conn = null;
		Statement stmt = null;

		File dir = new File(filePath);
		if (!dir.exists()){
			dir.mkdirs();
		}

		try{
			conn = getPostgreSQLConnection(host,db, user, pwd);

			stmt = conn.createStatement();

			//테이블 목록
			//ResultSet rsTables = dmd.getTables(conn.getCatalog(), null, "%", null);

			ArrayList<String> tableList = new ArrayList<String>();
			tableList.add("ebs_bp_define");
			tableList.add("ebs_bp_instance");
			tableList.add("ebs_bp_service");
			tableList.add("ebs_bp_step");
			tableList.add("ebs_bp_tasks");


			for(int i=0;i<tableList.size();i++) {

			    ArrayList<String> arrColumns = new ArrayList<String>();
			    ArrayList<String> arrPkColumns = new ArrayList<String>();
				String tableName = tableList.get(i);

				//컬럼 목록
				ResultSet rs = stmt.executeQuery("SELECT * FROM "+tableName);
				ResultSetMetaData rsmd = rs.getMetaData();

				DatabaseMetaData dm = conn.getMetaData( );
				ResultSet rsPkColumns = dm.getExportedKeys( "" , "" , tableName );
				while( rsPkColumns.next( ) )
				{
				  String pkey = rsPkColumns.getString("PKCOLUMN_NAME");
				  arrPkColumns.add(pkey);
				}


				for(int j=1;j<=rsmd.getColumnCount();j++) {

					String columnName = rsmd.getColumnName(j);
					String columnTypeName = rsmd.getColumnTypeName(j);
					int columnType = rsmd.getColumnType(j);
					String columnSize = rsmd.getColumnDisplaySize(j)+"";

					arrColumns.add(columnName);
				}


				//makeVo(arrColumns);
				System.out.println(getSelectQuery(tableList.get(i),arrColumns));
				System.out.println(getInsertQuery(tableList.get(i),arrColumns,"uid"));
				System.out.println(getUpdateQuery(tableList.get(i),arrColumns,arrPkColumns));
				System.out.println(getDeleteQuery(tableList.get(i),arrColumns,arrPkColumns));

			}

			stmt = null;
			conn = null;

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if (conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				conn = null;
			}


		}
	}


	private static String getInsertQuery(String tableName, ArrayList<String> columns, String autoincrementColumn)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\tINSERT INTO ")
		  .append(tableName.toUpperCase())
		  .append("\n\t     ( ");
		StringBuffer sb1 = new StringBuffer();
		for (String columnName : columns)
		{
			if (columnName.equals(autoincrementColumn)){
				continue;
			}
			if (sb1.length()>0){
				sb1.append("\n\t     , ");
			}else{
				sb1.append("\n\t       ");
			}
			sb1.append(columnName.toUpperCase());
		}
		sb.append(sb1)
		  .append("\n\t     ) ")
		  .append("\n\tVALUES ")
		  .append("\n\t     ( ");
		StringBuffer sb2 = new StringBuffer();
		for (String columnName : columns)
		{
			if (columnName.equals(autoincrementColumn)){
				continue;
			}
			if (sb2.length()>0){
				sb2.append("\n\t     ,  ");
			}else{
				sb2.append("\n\t       ");
			}
			//sb2.append(" ? ");
			sb2.append("#{"+columnName.toUpperCase()+"}");
		}
		sb.append(sb2)
		  .append("\n\t    ) ");
		System.out.println(sb.toString());
		return sb.toString();
	}
	/** 테이블에서 자동증가여부 컬럼 찾는다
	 * @param conn
	 * @param tableName
	 * @return
	 */
	private String getAutoIncrementColumn(Connection conn, String tableName){
		ResultSet rs = null;
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE 1 = 2");
			ResultSetMetaData meta = rs.getMetaData();
			for (int i = 1 ; i < meta.getColumnCount(); i++)
			{
				if (meta.isAutoIncrement(i)){
					return meta.getColumnName(i);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if (rs!=null){rs.close();}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/** 파일쓰기
	 * @param src
	 * @param fileName
	 * @param txt
	 */
	private void writeFile(File src, String fileName, String txt)
	{
		File f = new File(src, fileName);
		FileWriter fw = null;
		try {
			fw = new FileWriter(f);
			fw.write(txt);
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				if (fw!=null){fw.close();}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}


	/** 업데이트문
	 * @param tableName
	 * @param columns
	 * @param pkColumns
	 * @return
	 */
	private String getUpdateQuery(String tableName, ArrayList<String> columns, ArrayList<String> pkColumns)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\tUPDATE ")
		  .append(tableName.toUpperCase())
		  .append("\n\t   SET ");
		StringBuffer sb1 = new StringBuffer();
		for (String columnName : columns)
		{
			if (isPKColunm(columnName, pkColumns)){
				continue;
			}
			if (sb1.length()>0){
				sb1.append("\n\t     , ");
			}
			sb1.append(columnName.toUpperCase())
			   .append(" = ")
			   .append("#{"+columnName.toUpperCase()+"}");
		}
		sb.append(sb1)
		  .append("\n\t WHERE ");
		StringBuffer sb2 = new StringBuffer();
		for (String columnName : pkColumns)
		{
			if (sb2.length()>0){
				sb2.append("\n\t   AND ");
			}
			sb2.append(columnName.toUpperCase())
			   .append(" = ")
			   .append("#{"+columnName.toUpperCase()+"}");
		}
		sb.append(sb2);
		System.out.println(sb.toString());
		return sb.toString();
	}


	/** pk 컬럼인지여부 비교
	 * @param target
	 * @param arrPK
	 * @return
	 */
	private boolean isPKColunm(String target, List<String> arrPK){

		for (String pk:arrPK){
			if (target.equals(pk)){
				return true;
			}
		}
		return false;
	}

	/** delete 문
	 * @param tableName
	 * @param columns
	 * @param pkColumns
	 * @return
	 */
	private String getDeleteQuery(String tableName, ArrayList<String> columns, ArrayList<String> pkColumns)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\tDELETE\n\t  FROM ")
		  .append(tableName.toUpperCase());
		sb.append("\n\t WHERE ");
		StringBuffer sb2 = new StringBuffer();
		for (String columnName : pkColumns)
		{
			if (sb2.length()>0){
				sb2.append("\n\t   AND ");
			}
			sb2.append(columnName.toUpperCase())
			   .append(" = ")
			   .append("#{"+columnName.toUpperCase()+"}");
		}
		sb.append(sb2);
		System.out.println(sb.toString());
		return sb.toString();
	}

	/** select 문(where절 PK)
	 * @param tableName
	 * @param columns
	 * @param pkColumns
	 * @return
	 */
	private static String getSelectQueryByPK(String tableName, ArrayList<String> columns, ArrayList<String> pkColumns)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\tSELECT ");
		StringBuffer sb1 = new StringBuffer();
		for (String columnName : columns)
		{
			if (sb1.length()>0){
				sb1.append("\n\t     , ");
			}
			sb1.append(columnName.toUpperCase());
		}
		sb.append(sb1)
		  .append("\n\t  FROM ")
		  .append(tableName.toUpperCase())
		  .append("\n\t WHERE ");
		StringBuffer sb2 = new StringBuffer();
		for (String columnName : pkColumns)
		{
			if (sb2.length()>0){
				sb2.append("\n\t   AND ");
			}
			sb2.append(columnName.toUpperCase())
			   .append(" = ")
			   .append("#{"+columnName.toUpperCase()+"}");
		}
		sb.append(sb2);
		System.out.println(sb.toString());
		return sb.toString();
	}

	/** select 문
	 * @param tableName
	 * @param columns
	 * @return
	 */
	private String getSelectQuery(String tableName, ArrayList<String> columns)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\tSELECT ");
		StringBuffer sb1 = new StringBuffer();
		for (String columnName : columns)
		{
			if (sb1.length()>0){
				sb1.append("\n\t     ,  ");
			}
			sb1.append(columnName.toUpperCase());
		}
		sb.append(sb1)
		  .append("\n\t  FROM ")
		  .append(tableName.toUpperCase())
		  .append("\n\t WHERE ");
		StringBuffer sb2 = new StringBuffer();
		for (String columnName : columns)
		{
			if (sb2.length()>0){
				sb2.append("\n\t   AND ");
			}
			sb2.append(columnName.toUpperCase())
			   .append(" = ")
			   .append("#{"+columnName.toUpperCase()+"}");
		}
		sb.append(sb2);
		System.out.println(sb.toString());
		return sb.toString();
	}


	private String makeVo(ArrayList<String> columns)
	{
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		String privateStr = "\nprivate String ";//private String fileId;
		for (String columnName : columns)
		{
			privateStr+=toCamelCase(columnName)+";";


			sb.append(privateStr);
			privateStr = "\nprivate String ";
		}
		System.out.println(sb.toString());
		return sb.toString();
	}

	@SuppressWarnings("deprecation")
	public static String toCamelCase(String input) {
        if (input == null || input.isEmpty()) {
            return input; // 입력이 비어있으면 그대로 반환
        }

        // 공백, 밑줄, 대시로 단어를 나눈 뒤 첫 글자를 대문자로 변환
        String capitalized = WordUtils.capitalizeFully(input, ' ', '_', '-');

        // 공백, 밑줄, 대시 제거 후 첫 글자를 소문자로 변환
        String camelCase = capitalized.replaceAll("[\\s_\\-]", "");
        return camelCase.substring(0, 1).toLowerCase() + camelCase.substring(1);
    }

}
