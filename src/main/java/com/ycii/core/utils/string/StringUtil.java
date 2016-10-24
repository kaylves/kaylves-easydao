
package com.ycii.core.utils.string;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.ycii.core.utils.date.DateUtil;

/**
 * @author 王单凯
 */
public class StringUtil extends StringUtils
{

    private static SimpleDateFormat sdf = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss" );

    public static String toTime( Date date )
    {
        return sdf.format( date );
    }

    /**
     * function:判断字符串是否为空或者为null
     * 
     * @param value
     * @return
     */
    public static boolean nullOrEmpty( String value )
    {
        return value == null || "".equals( value ) || "null".equals( value );
    }

    public static boolean nullOrEmpty( String[] array )
    {
        for ( String temp : array )
        {
            if ( nullOrEmpty( temp ) )
            {
                return true;
            }
        }
        return false;
    }

    /**
     * @time 2013-9-11 上午11:36:28
     * @param value
     * @return boolean
     * @description
     * @version 1.0
     */
    public static boolean notEmpty( String value )
    {
        return !nullOrEmpty( value );
    }

    /**
     * empty to null
     * 
     * @param value
     * @return
     */
    public static String emptyToNull( String value )
    {
        return nullOrEmpty( value ) ? null : value;
    }

    /**
     * @function if value is null then return empty else return target
     *           value.toString();
     * @param value
     * @return
     */
    public static String nullToEmpty( Object value )
    {
        return value == null ? "" : value.toString();
    }

    /**
     * @function if value is null then return empty else return target
     *           value.toString();
     * @param value
     * @return
     */
    public static String nullToEmpty( String value )
    {
        return nullOrEmpty( value ) ? "" : value;
    }

    /**
     * 判断是否为null
     * 
     * @param <T>
     * @param t
     * @return
     */
    public static <T> T isNull( T t )
    {
        return t == null ? null : t;
    }

    /**
     * 判断是否为null或者0
     * 
     * @param value
     * @return
     */
    public static boolean nullOrZero( Integer value )
    {
        return value == null || value == 0;
    }

    /**
     * @param list
     * @param split
     * @return
     */
    public static String getStringSplit( List<String> list, String split )
    {
        if ( list.size() == 0 )
        {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for ( String t : list )
        {
            sb.append( "'" + t + "'" ).append( split );
        }
        return sb.substring( 0, sb.length() - 1 );
    }

    /**
     * @param list
     * @param split
     * @return
     */
    public static String list2StringSplit( List<String> list, String split )
    {
        if ( list.size() == 0 )
        {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for ( String t : list )
        {
            sb.append( t ).append( split );
        }
        return sb.substring( 0, sb.length() - 1 );
    }

    public static String getStringSplit( String[] val )
    {
        StringBuffer sqlStr = new StringBuffer();
        for ( String s : val )
        {
            if ( StringUtils.isNotBlank( s ) )
            {
                sqlStr.append( "," );
                sqlStr.append( "'" );
                sqlStr.append( s.trim() );
                sqlStr.append( "'" );
            }
        }
        return sqlStr.toString().substring( 1 );
    }

    public static String getDefaultSplit( String value )
    {
        if ( isBlank( value ) )
        {
            return "";
        }
        String[] splitArray = value.split( "," );
        StringBuffer sqlStr = new StringBuffer();
        for ( String s : splitArray )
        {
            sqlStr.append( "," );
            sqlStr.append( "'" );
            sqlStr.append( s.trim() );
            sqlStr.append( "'" );
        }
        return sqlStr.toString().substring( 1 );
    }

    public static boolean isNumeric( String str )
    {
        Pattern pattern = Pattern.compile( "[0-9]*" );
        return pattern.matcher( str ).matches();
    }

    public static Double isDouble( String value )
    {
        Double v = null;
        try
        {
            v = Double.valueOf( value );
        } catch ( NumberFormatException e )
        {
            return null;
        }
        return v;
    }

    public static double doCaculate( double first, double second, char method )
    {
        double result = 0;
        switch ( method )
        {
            case '+':
                result = first + second;
                break;
            case '-':
                result = first - second;
                break;
            case '*':
                result = first * second;
                break;
            case '/':
                result = first / second;
                break;
            default:
                break;
        }
        return result;
    }

    public static String getRandomCode( int length )
    {
        StringBuilder code = new StringBuilder();
        for ( int i = 0; i < length; i++ )
        {
            code.append( randomChar() );
        }
        return code.toString();
    }

    public static char randomChar()
    {
        Random r = new Random();
        String s = "0123456789";
        return s.charAt( r.nextInt( s.length() ) );
    }

    public static Double defaultFormatNumber( Double value )
    {
        DecimalFormat df = new DecimalFormat( "#.00" );
        return Double.valueOf( df.format( value ) );
    }

    /**
     * @time 2013-11-22 下午03:51:34
     * @param user
     * @return String
     * @description
     * @since 1.0
     */
    public static String generateUserTimestamp( String prefix )
    {
        if ( isNotEmpty( prefix ) )
        {
            return prefix + DateUtil.formatDate( "yyyyMMddHHmmss", new Date() );
        }
        return null;
    }

    public static String generateUUID()
    {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll( "-", "" );
    }

    public static boolean compareObject( Object obj, Object obj2 )
    {
        return nullToEmpty( obj ).equals( nullToEmpty( obj2 ) );
    }

    public static void main( String[] args )
    {
        System.out.println( compareObject( 1, "1" ) );
    }
}
