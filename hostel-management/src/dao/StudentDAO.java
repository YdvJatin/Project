package dao;

import db.DBConnection;
import model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public static void add(Student s) {
        String sql = "INSERT INTO students(name, rollno, course, year, phone) VALUES(?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, s.getName());
            pst.setString(2, s.getRollno());
            pst.setString(3, s.getCourse());
            pst.setString(4, s.getYear());
            pst.setString(5, s.getPhone());
            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(Student s) {
        String sql = "UPDATE students SET name=?, rollno=?, course=?, year=?, phone=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, s.getName());
            pst.setString(2, s.getRollno());
            pst.setString(3, s.getCourse());
            pst.setString(4, s.getYear());
            pst.setString(5, s.getPhone());
            pst.setInt(6, s.getId());
            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(int id) {
        String sql = "DELETE FROM students WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Student> getAll() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("rollno"),
                        rs.getString("course"),
                        rs.getString("year"),
                        rs.getString("phone")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
