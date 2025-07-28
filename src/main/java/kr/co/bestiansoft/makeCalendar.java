package kr.co.bestiansoft;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class makeCalendar {



	public static void main(String[] args) {

		try {

			makeCalendar mc = new makeCalendar();
			mc.makeCalData();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	public Connection getPostgreSQLConnection() throws Exception
	{
		Class.forName("org.postgresql.Driver");
		return DriverManager.getConnection("jdbc:postgresql://3.39.79.144:5432/bestian?currentSchema=kgst&useUnicode=true&characterEncoding=utf8&autoReconnect=true","best","best1234");
	}





	public void makeCalData() throws Exception {

		Connection conn = null;
		conn = getPostgreSQLConnection();
		PreparedStatement pstmt = null;

        LocalDate curDate = LocalDate.now();
        LocalDate startDate = curDate.minusYears(10);
        LocalDate endDate = curDate.plusYears(50);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // date roof
        while (!startDate.isAfter(endDate)) {

            String formattedDate = startDate.format(formatter);
            DayOfWeek dayOfWeek = startDate.getDayOfWeek();
            String koreanDayOfWeek = getKoreanDayOfWeek(dayOfWeek);
            String englishDayOfWeek = startDate.getDayOfWeek().toString();

            String sqlstr = " INSERT INTO kgst.com_calendar    (yyyymmdd, day, daynm1, daynm2)  VALUES(?,?,?,?)";

            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setString(1, formattedDate);
            pstmt.setInt(2, dayOfWeek.getValue());
            pstmt.setString(3, englishDayOfWeek);
            pstmt.setString(4, koreanDayOfWeek);
            int rowsInserted = pstmt.executeUpdate();

            // print
            System.out.println(formattedDate + " " + koreanDayOfWeek + " " + englishDayOfWeek);

            // one day increase
            startDate = startDate.plusDays(1);
        }


	}


	 // korean Day Conversion method
    private static String getKoreanDayOfWeek(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return "월요일";
            case TUESDAY:
                return "화요일";
            case WEDNESDAY:
                return "수요일";
            case THURSDAY:
                return "목요일";
            case FRIDAY:
                return "금요일";
            case SATURDAY:
                return "토요일";
            case SUNDAY:
                return "일요일";
            default:
                return "";
        }
    }

}
