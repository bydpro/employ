
import java.sql.*;
import javax.swing.*;

public class DBCon {
    static {
        try{
            //Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(ClassNotFoundException e){
            JOptionPane.showMessageDialog(null,"���ݿ������������ʧ�ܣ�����ϵϵͳ����Ա","����",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    Connection con;
    Statement st;
    ResultSet rs;
    public DBCon() {
        try{
           // con = DriverManager.getConnection("jdbc:odbc:employees");
            String url = "jdbc:mysql://localhost:3306/employees?"
                    + "user=root&password=root&useUnicode=true&characterEncoding=UTF8";
            con = DriverManager.getConnection(url);
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null,"���ݿ�����ʧ�ܣ�","����",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    public Connection getCon(){
        return con;
    }
}
