package walke.demolibrary.log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author walker
 * @date 2023/9/12
 * @description 时间工具类：
 */
public class MyDate {

    public static String getLogFileName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
        String date = format.format(new Date(System.currentTimeMillis()));
        return date;// 2012年10月03日 23:41:31
    }

    public static String getDateEN() {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = format1.format(new Date(System.currentTimeMillis()));
        return date1;// 2012-10-03 23:41:31
    }
}
