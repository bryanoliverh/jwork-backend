package bryanoliver.jwork;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
/**
 * Class DatabaseBonusPostgre
 *
 * @author Bryan Oliver
 * @version 11.6.2021
 */
import static bryanoliver.jwork.DatabaseConnection.connection;

public class DatabaseBonusPostgre {
    private static Connection c = null;
    private static Statement stmt = null;
    /**
     * Method to add bonus
     */
    public static Bonus addBonus (Bonus bonus) {
        c = DatabaseConnection.connection();
        try {
            stmt = c.createStatement();
            int id = bonus.getId();
            String referralCode = bonus.getReferralCode();
            int extrafee = bonus.getExtraFee();
            int minTotalFee = bonus. getMinTotalFee();
            Boolean active = bonus.getActive();

            String query =  "INSERT INTO bonus (id, referralCode, extrafee, minTotalFee, active)"
                    + "VALUES (" + id + ",'" + referralCode + "','"+ extrafee + "','"+ minTotalFee + "','" + active + "');";
            stmt.executeUpdate(query);
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
            return null;
        }
        return bonus;
    }



    /**
     * Method for deleting bonus based on id
     * @param id
     *
     */
    public static boolean removeBonus(int id) {
        c = DatabaseConnection.connection();
        try {
            stmt = c.createStatement();
            String query = "DELETE * FROM bonus WHERE id = \"+id+\";\"";
            stmt.executeUpdate(query);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return true;
    }


    /**
     * Method to obtain all bonus data on database
     *
     */
    public static ArrayList<Bonus> getBonusDatabase() {
        ArrayList<Bonus> bonus = new ArrayList<>();
        Bonus value = null;
        Connection c = DatabaseConnection.connection();

        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM bonus;");
            while (rs.next()) {
                int id = rs.getInt("id");
                String referralCode = rs.getString("referralCode");
                int extrafee = rs.getInt("extrafee");
                int minTotalFee = rs.getInt("minTotalFee");
                Boolean active = rs.getBoolean("active");
                value = new Bonus(id, referralCode, extrafee, minTotalFee, active);
                bonus.add(value);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return bonus;
    }
    /**
     * Method to obtain all bonus data on database
     *
     */
    public static Bonus getBonusByRefferralCode(String referralCode) {
        Bonus value = null;
        Connection c = DatabaseConnection.connection();

        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM bonus WHERE referralCode = '"+ referralCode + "' ;");
            while (rs.next()) {
                int id = rs.getInt("id");
                referralCode = rs.getString("referralCode");
                int extrafee = rs.getInt("extrafee");
                int minTotalFee = rs.getInt("minTotalFee");
                Boolean active = rs.getBoolean("active");
                value = new Bonus(id, referralCode, extrafee, minTotalFee, active);
            }
            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return value;
    }

    /**
     * Getter fr Last Id in database
     * @return
     */
    public static int getLastId() {
        int value = 0;
        c = connection();
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX (id) FROM bonus;");
            while (rs.next()) {
                value = rs.getInt("max");
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return value;
    }
}



