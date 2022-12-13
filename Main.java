import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.*;

class Notepad implements ActionListener{
    JFrame frame;
    JTextArea text;
    JMenuBar menuBar;
    JMenu file, edit, close;
    JMenuItem newfile, open, save, print;
    JMenuItem cut, copy, paste;
    JMenuItem closeeditor;

    Notepad() {
        frame = new JFrame("NotePad");
        frame.setBounds(300, 100, 1000, 800);
        text = new JTextArea();
        menuBar = new JMenuBar();
        file = new JMenu("File");
        edit = new JMenu("Edit");
        close = new JMenu("Close");

        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(close);

        newfile = new JMenuItem("New");
        newfile.addActionListener(e->text.setText(""));
        open = new JMenuItem("Open");
        open.addActionListener(this);
        save = new JMenuItem("Save");
        save.addActionListener(this);
        print = new JMenuItem("Print");
        print.addActionListener(this);

        file.add(newfile);
        file.add(open);
        file.add(save);
        file.add(print);

        cut = new JMenuItem("Cut");
        cut.addActionListener(e->text.cut());
        copy = new JMenuItem("Copy");
        copy.addActionListener(e->text.copy());
        paste = new JMenuItem("Paste");
        paste.addActionListener(e->text.paste());

        edit.add(cut);
        edit.add(copy);
        edit.add(paste);

        closeeditor = new JMenuItem("Exit");
        closeeditor.addActionListener(e->System.exit(0));

        close.add(closeeditor);


        frame.setJMenuBar(menuBar);
        frame.add(text);

        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Print")){
            try{
                text.print();
            }catch (PrinterException error){
                throw new RuntimeException(error);
            }
        }
        else if(e.getActionCommand().equals("Open")) {
            JFileChooser jFileChooser = new JFileChooser("C:");

            int ans = jFileChooser.showOpenDialog(null);
            if (ans == JFileChooser.APPROVE_OPTION) {
                File file = new File(jFileChooser.getSelectedFile().getAbsolutePath());
                String s1 = "", s2 = "";
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    s2 = bufferedReader.readLine();
                    while ((s1 = bufferedReader.readLine()) != null) {
                        s2 += s1 + "\n";
                    }
                    text.setText(s2);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        else if(e.getActionCommand().equals("Save")){
                JFileChooser jFileChooser=new JFileChooser("C:");
                int ans=jFileChooser.showSaveDialog(null);
                if(ans==jFileChooser.APPROVE_OPTION)
                {
                    File file=new File(jFileChooser.getSelectedFile().getAbsolutePath());
                    BufferedWriter writer=null;
                    try {
                        writer=new BufferedWriter(new FileWriter(file,false));
                        writer.write((text.getText()));
                        writer.flush();
                        writer.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
    }
}
public class Main {
    public static void main(String[] args) {
        Notepad notepad = new Notepad();
    }
}