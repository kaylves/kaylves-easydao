
package com.kaylves.easydao.utils.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.kaylves.easydao.utils.string.StringUtil;

/**
 * 日期工具类
 * 
 * @author 王单凯
 * @time 2012-11-19
 */
public class DateUtil
{
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * @param format
     *            格式
     * @param d
     * @return
     */
    public static final String formatDate( String format, Date d )
    {
        SimpleDateFormat sf = new SimpleDateFormat( format );
        return sf.format( d );
    }

    /**
     * 默认的日期格式
     * 
     * @param d
     * @return
     */
    public static final String defautlFormatDate( Date d )
    {
        return formatDate( DEFAULT_DATE_FORMAT, d );
    }

    /**
     * 默认的日期格式
     * 
     * @param d
     * @return
     */
    public static final Date defautlFormatDate( String date )
    {
        if ( StringUtil.isBlank( date ) )
        {
            return null;
        }
        try
        {
            return new SimpleDateFormat( DEFAULT_DATE_FORMAT ).parse( date );
        } catch ( ParseException e )
        {
            return null;
        }
    }

    /**
     * @author Kaylves
     * @time 2014年8月2日 下午11:16:38
     * @description
     * @param date
     * @param format
     * @return Date
     */
    public static final Date parseDate( String date, String format )
    {
        if ( StringUtil.isBlank( date ) )
        {
            return null;
        }
        try
        {
            return new SimpleDateFormat( format ).parse( date );
        } catch ( ParseException e )
        {
            return null;
        }
    }

    /**
     * 默认的日期时间格式
     * 
     * @param d
     * @return
     */
    public static final String defaultFormatDateTime( Date d )
    {
        return formatDate( DEFAULT_DATETIME_FORMAT, d );
    }

    /**
     * 日期相减得天数
     * 
     * @param beginDate
     * @param endDate
     * @return
     */
    public static int getDaySub( Date beginDate, Date endDate )
    {
        int day = 0;
        day = (int)((endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000));
        return day;
    }

    /**
     * 当前指定时间是否在系统时间之后
     * 
     * @param date
     *            format as yyyy-mm-dd
     * @return
     */
    public static boolean isAfterNowDate( String date )
    {
        java.util.Date nowDate = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd", Locale.CHINA );
        Date d;
        try
        {
            d = sdf.parse( date );
            return d.after( nowDate );
        } catch ( ParseException e )
        {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * @author Kaylves
     * @time 2013-10-21 上午09:22:58
     * @param dateString
     * @param beforeDays
     * @return
     * @throws ParseException
     *             Date
     * @description
     * @version 1.0
     */
    public static Date getDate( String dateString, int beforeDays )
            throws ParseException
    {
        DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        Date inputDate = dateFormat.parse( dateString );
        Calendar cal = Calendar.getInstance();
        cal.setTime( inputDate );
        int inputDayOfYear = cal.get( Calendar.DAY_OF_YEAR );
        cal.set( Calendar.DAY_OF_YEAR, inputDayOfYear - beforeDays );
        return cal.getTime();
    }

    /**
     * @author Kaylves
     * @time 2013-10-21 上午09:56:19
     * @param dateString
     * @param beforeDays
     * @return
     * @throws ParseException
     *             Date[]
     * @description
     * @version 1.0
     */
    public static Date[] getDateArray( String dateString, int beforeDays )
            throws ParseException
    {
        Date[] beforeDates = new Date[beforeDays];
        int end = beforeDays;
        for ( int i = 0; i < beforeDays; i++ )
        {
            beforeDates[i] = getDate( dateString, end-- );
        }
        return beforeDates;
    }

    /**
     * @author Kaylves
     * @time 2013-10-21 上午09:56:24
     * @param format
     * @param dateString
     * @param beforeDays
     * @return
     * @throws ParseException
     *             String[]
     * @description
     * @version 1.0
     */
    public static String[] getDatesFormat( String format, String dateString,
            int beforeDays ) throws ParseException
    {
        Date[] beforeDates = getDateArray( dateString, beforeDays );
        String[] resultFormats = new String[beforeDays];
        for ( int i = 0; i < beforeDays; i++ )
        {
            resultFormats[i] = formatDate( format, beforeDates[i] );
        }
        return resultFormats;
    }

    public static String[] getCurrentDateFormat( String format, int beforeDays )
    {
        Date currentDate = new Date();
        String dateString = DateUtil.defautlFormatDate( currentDate );
        Date[] beforeDates;
        String[] resultFormats = new String[beforeDays + 1];
        try
        {
            beforeDates = getDateArray( dateString, beforeDays );
            for ( int i = 0; i < beforeDays; i++ )
            {
                resultFormats[i] = formatDate( format, beforeDates[i] );
            }
            resultFormats[beforeDays] = DateUtil.formatDate( format,
                    currentDate );
        } catch ( ParseException e )
        {
            return null;
        }
        return resultFormats;
    }

}
