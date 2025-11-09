package ui;

import dao.StudentDAO;
import model.Student;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentForm extends JFrame {

    JTextField idField, nameField, rollField, courseField, yearField, phoneField;
    DefaultTableModel model;
    JTable table;

    public StudentForm() {

        FlatLightLaf.setup();  // Modern UI Theme

        setTitle("Hostel Student Management");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(12,12));

        Font fTitle = new Font("Segoe UI", Font.BOLD, 22);
        Font fLabel = new Font("Segoe UI", Font.PLAIN, 15);

        JLabel title = new JLabel("Hostel Student Management System", SwingConstants.CENTER);
        title.setFont(fTitle);
        add(title, BorderLayout.NORTH);

        // Left Side Form
        JPanel form = new JPanel();
        form.setLayout(new GridLayout(7,2,10,10));
        form.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        form.setBackground(Color.WHITE);

        JLabel l1 = new JLabel("ID:"); l1.setFont(fLabel);
        JLabel l2 = new JLabel("Name:"); l2.setFont(fLabel);
        JLabel l3 = new JLabel("Roll No:"); l3.setFont(fLabel);
        JLabel l4 = new JLabel("Course:"); l4.setFont(fLabel);
        JLabel l5 = new JLabel("Year:"); l5.setFont(fLabel);
        JLabel l6 = new JLabel("Phone:"); l6.setFont(fLabel);

        idField = new JTextField(); idField.setFont(fLabel);
        nameField = new JTextField(); nameField.setFont(fLabel);
        rollField = new JTextField(); rollField.setFont(fLabel);
        courseField = new JTextField(); courseField.setFont(fLabel);
        yearField = new JTextField(); yearField.setFont(fLabel);
        phoneField = new JTextField(); phoneField.setFont(fLabel);

        form.add(l1); form.add(idField);
        form.add(l2); form.add(nameField);
        form.add(l3); form.add(rollField);
        form.add(l4); form.add(courseField);
        form.add(l5); form.add(yearField);
        form.add(l6); form.add(phoneField);

        // Buttons Panel
        JPanel btnPanel = new JPanel(new GridLayout(1,5,10,10));
        btnPanel.setBackground(Color.WHITE);

        JButton add = niceBtn("Add");
        JButton update = niceBtn("Update");
        JButton delete = niceBtn("Delete");
        JButton view = niceBtn("View");
        JButton clear = niceBtn("Clear");

        btnPanel.add(add); btnPanel.add(update); btnPanel.add(delete); btnPanel.add(view); btnPanel.add(clear);
        form.add(new JLabel()); form.add(btnPanel);

        add(form, BorderLayout.WEST);

        // Right Table Panel
        JPanel right = new JPanel(new BorderLayout());
        right.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        model = new DefaultTableModel(new Object[]{"ID","Name","Roll No","Course","Year","Phone"},0);
        table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        right.add(new JScrollPane(table), BorderLayout.CENTER);

        add(right, BorderLayout.CENTER);

        // Action Handlers
        add.addActionListener(e -> { StudentDAO.add(getStudent()); refresh(); msg("Added Successfully"); });
        update.addActionListener(e -> { if(idField.getText().isEmpty()){msg("Enter ID"); return;} Student s = getStudent(); s.setId(Integer.parseInt(idField.getText())); StudentDAO.update(s); refresh(); msg("Updated");});
        delete.addActionListener(e -> { if(idField.getText().isEmpty()){msg("Enter ID"); return;} StudentDAO.delete(Integer.parseInt(idField.getText())); refresh(); msg("Deleted");});
        clear.addActionListener(e -> clearFields());
        view.addActionListener(e -> refresh());

        table.getSelectionModel().addListSelectionListener(e -> fillForm());

        refresh();
        setVisible(true);
    }

    JButton niceBtn(String t){
        JButton b = new JButton(t);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setFocusPainted(false);
        b.setBackground(new Color(230,230,230));
        b.setBorder(BorderFactory.createLineBorder(new Color(160,160,160),1,true));
        return b;
    }

    Student getStudent(){
        return new Student(0, nameField.getText(), rollField.getText(), courseField.getText(), yearField.getText(), phoneField.getText());
    }

    void refresh(){
        model.setRowCount(0);
        for(Student s : StudentDAO.getAll()){
            model.addRow(new Object[]{ s.getId(), s.getName(), s.getRollno(), s.getCourse(), s.getYear(), s.getPhone() });
        }
    }

    void fillForm(){
        int r = table.getSelectedRow();
        if(r==-1) return;
        idField.setText(model.getValueAt(r,0).toString());
        nameField.setText(model.getValueAt(r,1).toString());
        rollField.setText(model.getValueAt(r,2).toString());
        courseField.setText(model.getValueAt(r,3).toString());
        yearField.setText(model.getValueAt(r,4).toString());
        phoneField.setText(model.getValueAt(r,5).toString());
    }

    void clearFields(){
        idField.setText(""); nameField.setText(""); rollField.setText(""); courseField.setText(""); yearField.setText(""); phoneField.setText("");
    }

    void msg(String s){ JOptionPane.showMessageDialog(this, s); }

    public static void main(String[] args) {
        new StudentForm();
    }
}
