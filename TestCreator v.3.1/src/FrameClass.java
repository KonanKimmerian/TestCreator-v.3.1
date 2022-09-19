import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class FrameClass {
    JFrame frame;

    public FrameClass(String name) {
        this.frame = createDefault(name);
//        frame.add(new DrawingArea(frame));
    }

    public JFrame createDefault(String name){
        JFrame frame1 = new JFrame(name);
        frame1.setUndecorated(false);
        frame1.pack();
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame1.setLayout(null);
        frame1.setVisible(true);
        return frame1;
    }

    public JButton createButton(String name, Rectangle bounds, ActionListener listener){
        JButton button=new JButton(name);
        button.setBounds(bounds);
        frame.add(button);
        button.addActionListener(listener);
        button.setVisible(true);
        return button;

    }

    public JLabel createLabel(String text,Rectangle bounds,Color color){
        JLabel label=new JLabel();
        label.setText(text);
        label.setBounds(bounds);
        label.setForeground(color);
        frame.add(label);
        label.setVisible(true);
        return label;
    }

    public JTextField createField(Rectangle bounds){
        JTextField field=new JTextField();
        field.setBounds(bounds);
        frame.add(field);
        field.setVisible(true);
        return field;
    }

    public java.awt.List createAwtList(Rectangle bounds, String text){
        java.awt.List list=new java.awt.List(5);
        list.setBounds(bounds);
        list.add(text);
        frame.add(list);
        return list;
    }




}
