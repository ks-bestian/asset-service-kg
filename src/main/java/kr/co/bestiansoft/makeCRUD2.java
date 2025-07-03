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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class makeCRUD2 {



	public static void main(String[] args) {

		makeCRUD2 o = new makeCRUD2();
		try {
//			o.generateSQL("sponet.co.kr:3306"	//dbIP
//				     ,"dev_bm_manager"	//db name
//				     , "best"	//account
//				     , "best1234"	//password
//				     , "c:\\starproject_file"	//Storage path
//				     );
			
			Map<String, Long> map = o.getTableRowCount("192.168.0.31:1433"	//dbIP
			     ,"sed"	//db name
			     , "admin_id"	//account
			     , "admin_pass"	//password
			     , "c:\\starproject_file"	//Storage path
			     , "cnt5" //Table_Div Table Column Name(cnt1 / cnt2 / cnt3)
		     );
			
			for(String tableName : map.keySet()) {
				Long cnt = map.get(tableName);
				System.out.println(tableName + " " + cnt);
			}
			
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
	
	private Connection getMSSQLConnection(String ip, String db, String user, String pwd) throws Exception
	{
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		return DriverManager.getConnection("jdbc:sqlserver://" + ip + ";database=" + db, user, pwd);
	}
	
	public List<String> getTableNames(Statement stmt) throws SQLException {
		String sql = "SELECT TABLE_NAME\r\n"
				+ "FROM INFORMATION_SCHEMA.TABLES\r\n"
				+ "WHERE TABLE_SCHEMA = 'dbo'\r\n"
				+ "ORDER BY TABLE_NAME";

		ResultSet rs = stmt.executeQuery(sql);
		List<String> ret = new ArrayList<>();
		while(rs.next()) {
			String name = rs.getString(1);
			ret.add(name);
		}
		rs.close();
		return ret;
	}
	
	public Long getRowCount(Statement stmt, String tableName) throws SQLException {
		String sql = "SELECT COUNT(*)\r\n"
				+ "FROM " + tableName + "\r\n"
				+ "";
		ResultSet rs = stmt.executeQuery(sql);
		Long ret = null;
		while(rs.next()) {
			ret = rs.getLong(1);
		}
		rs.close();
		return ret;
	}
	
	public void saveRowCount(Statement stmt, String tableName, Long cnt, String colname) throws SQLException {
		String sql = "MERGE INTO test_div AS a\r\n"
				+ "USING (SELECT 1 AS dual) AS b\r\n"
				+ "   ON (a.table_name = '" + tableName + "')\r\n"
				+ " WHEN MATCHED THEN\r\n"
				+ "   UPDATE SET a." + colname + " = " + cnt + "\r\n"
				+ " WHEN NOT MATCHED THEN\r\n"
				+ "   INSERT(table_name, " + colname + ") VALUES('" + tableName + "', " + cnt + ");\r\n";
		stmt.executeUpdate(sql);
	}
	
	public Map<String, Long> getTableRowCount(String host, String db, String user, String pwd, String filePath, String colname) {
		Connection conn = null;
		Statement stmt = null;

		try{
			Map<String, Long> map = new HashMap<>();
			
			conn = getMSSQLConnection(host,db, user, pwd);
			stmt = conn.createStatement();
			List<String> tableNames = getTableNames(stmt);

			for(String tableName : tableNames) {
				Long cnt = getRowCount(stmt, tableName);
				map.put(tableName, cnt);
				saveRowCount(stmt, tableName, cnt, colname);
			}

			stmt = null;
			conn = null;

			return map;
			
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
		
		return null;
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

			//table list
			//ResultSet rsTables = dmd.getTables(conn.getCatalog(), null, "%", null);

			ArrayList<String> tableList = new ArrayList<String>();
			tableList.add("ebs_bp_instance");


			for(int i=0;i<tableList.size();i++) {

			    ArrayList<String> arrColumns = new ArrayList<String>();
			    ArrayList<String> arrPkColumns = new ArrayList<String>();
				String tableName = tableList.get(i);

				//column list
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
	/** Find a column whether automatic increase in the table
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

	/** File writing
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


	/** Update query
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


	/** Comparison of PK Column
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

	/** delete  query
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

	/** select query (where PK)
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

	/** select  query
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



}
