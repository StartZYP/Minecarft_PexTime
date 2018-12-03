package Top.q44920040.Minecarft_PexTime;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class Dao {
    private static Connection connection = null;
    private static Statement statement = null;
    private static String DataPathName = null;
    Dao(String DatabasePath){
        DataPathName = DatabasePath;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:"+DataPathName+".db");
            statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS PexTimeTable " +
                    "(Pex        VARCHAR(200)," +
                    "PlayerName        VARCHAR(100), " +
                    "PexexpireTime DATETIME, " +
                    "PexReName VARCHAR(200))";
            statement.executeUpdate(sql);
            statement.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }



    public boolean AddSQLiteData(String PlayerName,String Pex,String onTime,String PexReName){
        boolean returnb=false;
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String[] a =onTime.split("-");
            String sql1 = "SELECT PlayerName,Pex,PexReName,PexexpireTime FROM PexTimeTable where PlayerName='"+PlayerName+"'AND Pex='"+Pex+"' and PexReName='"+PexReName+"'";
            String sql3 = "INSERT INTO PexTimeTable(Pex,PlayerName,PexexpireTime,PexReName)values('"+Pex+"','"+PlayerName+"',datetime('now','localtime','+"+a[0]+" day','+"+a[1]+" hour','+"+a[2]+" minute'),'"+PexReName+"')";
            ResultSet rs = statement.executeQuery(sql1);
            if (rs.next()){
//                System.out.println("确认有叠加数据");
//                System.out.println(sql1);
                String PlayerNameTemp = rs.getString("PlayerName");
                String pexTemp = rs.getString("pex");
                String PexReNameTemp = rs.getString("PexReName");
                String PexexpireTimeTemp = rs.getString("PexexpireTime");
                if (PlayerName.equalsIgnoreCase(PlayerNameTemp)&&Pex.equalsIgnoreCase(pexTemp)&&PexReName.equalsIgnoreCase(PexReNameTemp)){
//                    System.out.println("数据校验正确开始叠加");
                    String sql2 = "update PexTimeTable set PexexpireTime=datetime('"+PexexpireTimeTemp+"','+"+a[0]+" day','+"+a[1]+" hour','+"+a[2]+" minute') where PlayerName='"+PlayerName+"' and Pex='"+Pex+"' and PexReName='"+PexReName+"'";
                    int num =statement.executeUpdate(sql2);
                    if (num>0){
                        returnb=true;
                    }
                }
            }else {
//                System.out.println("无叠加数据");
//                System.out.print(sql3);
                int num =statement.executeUpdate(sql3);
                if (num>0){
                    returnb=true;
                }
                connection.commit();
                statement.close();
            }
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return returnb;
    }

    public static ArrayList<String> GetToDaySQLiteData(String UserName){
        ArrayList<String> TodayUserarray = new ArrayList<>();
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql = "select * from PexTimeTable where PlayerName='"+UserName+"' AND PexexpireTime<datetime('now','localtime')";
            ResultSet rs = statement.executeQuery(sql);
//            System.out.print(sql);
            while (rs.next()){
                String PlayerName = rs.getString("PlayerName");
                String pex = rs.getString("pex");
                TodayUserarray.add(PlayerName+"|"+pex);
            }
            rs.close();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return TodayUserarray;
    }


    public ArrayList<String> GetUserSQLiteData(String UserName){
        ArrayList<String> Userarray = new ArrayList<>();
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql = "select * from PexTimeTable where PlayerName='"+UserName+"'";
            ResultSet rs = statement.executeQuery(sql);
//            System.out.print(sql);
            while (rs.next()){
                String PlayerName = rs.getString("PlayerName");
                String pex = rs.getString("pex");
                String PexReName = rs.getString("PexReName");
                String PexexpireTime = rs.getString("PexexpireTime");
                Userarray.add(PlayerName+"|"+pex+"|"+PexReName+"|"+PexexpireTime);
            }
            rs.close();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return Userarray;
    }


    public static boolean DeleteSQlite(String Player,String pex){
        boolean returnb=false;
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql = "delete from PexTimeTable where Pex='"+pex+"' and PlayerName='"+Player+"';";
//            System.out.print(sql);
            int num =statement.executeUpdate(sql);
            if (num>0){
                returnb=true;
            }
            connection.commit();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return returnb;
    }

}
