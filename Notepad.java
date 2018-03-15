import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

class Notepad extends JFrame {
	private static final long serialVersionUID = 1L;
	JTextArea textArea = new JTextArea();
	
	JMenuItem newItem = new JMenuItem("New");
	JMenuItem openItem = new JMenuItem("Open");
	JMenuItem saveItem = new JMenuItem("Save");
	JMenuItem exitItem = new JMenuItem("Exit");
	
	class NewListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			textArea.setText("");
		}
		
	}
	
	class OpenListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();
            int ret = chooser.showOpenDialog(null);
            if (ret==JFileChooser.APPROVE_OPTION) {
            	File file = chooser.getSelectedFile();
            	try (FileInputStream fis = new FileInputStream(file)) {
            		byte[] data = new byte[(int) file.length()];
            		fis.read(data);
            		setTitle(file.toString() + " - Notepad");
            		textArea.setText(new String(data, "UTF-8"));
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(Notepad.this, "File Not Found");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(Notepad.this, "IO Exception");
				}
            }
            
		}
		
	}
	
	class SaveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();
            int ret = chooser.showOpenDialog(null);
            if (ret==JFileChooser.APPROVE_OPTION) {
            	File file = chooser.getSelectedFile();
            	try (FileOutputStream fos = new FileOutputStream(file)) {
            		fos.write(textArea.getText().getBytes());
            		setTitle(file.toString() + " - Notepad");
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(Notepad.this, "File Not Found");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(Notepad.this, "IO Exception");
				}
            }
		}
		
	}
	
	class ExitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
		
	}
	
	public Notepad() {
		
		newItem.addActionListener(new NewListener());
		openItem.addActionListener(new OpenListener());
		saveItem.addActionListener(new SaveListener());
		exitItem.addActionListener(new ExitListener());
		JMenu menu = new JMenu("File");
		menu.add(newItem);
		menu.add(openItem);
		menu.add(saveItem);
		menu.addSeparator();
		menu.add(exitItem);
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
		
		this.getContentPane().add(textArea);

		Icon icon = UIManager.getIcon("FileView.fileIcon");
		this.setIconImage(((ImageIcon)icon).getImage());
		
		this.setSize(640, 320);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Notepad();
	}
}       