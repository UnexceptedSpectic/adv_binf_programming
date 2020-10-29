package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringReader;
import java.util.Base64;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Base64EncoderDecoder extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextArea plainTextArea = new JTextArea();
	private JTextArea encodedTextArea = new JTextArea();
	private JButton encodeButton = new JButton("Encode");
	private JButton decodeButton = new JButton("Decode");

	private JMenuBar getMyMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu loadJMenu = new JMenu("Load");
		JMenuItem loadPlainTextItem = new JMenuItem("Plain text");
		JMenuItem loadEncryptedTextItem = new JMenuItem("Encrypted text");

		loadPlainTextItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadFileTo(plainTextArea);
			}
		});

		loadEncryptedTextItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadFileTo(encodedTextArea);
			}
		});

		loadJMenu.add(loadPlainTextItem);
		loadJMenu.add(loadEncryptedTextItem);

		JMenu saveJMenu = new JMenu("Save");
		JMenuItem savePlainTextItem = new JMenuItem("Plain text");
		JMenuItem saveEncryptedTextItem = new JMenuItem("Encrypted text");

		savePlainTextItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveToFile(plainTextArea);
			}
		});

		saveEncryptedTextItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveToFile(encodedTextArea);
			}
		});

		saveJMenu.add(savePlainTextItem);
		saveJMenu.add(saveEncryptedTextItem);

		menuBar.add(loadJMenu);
		menuBar.add(saveJMenu);

		return menuBar;
	}

	public void saveToFile(JTextArea jTextArea) {
		JFileChooser jFileChooser = new JFileChooser();

		if (jFileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION
				|| jFileChooser.getSelectedFile() == null) {
			return;
		}

		File chosenFile = jFileChooser.getSelectedFile();

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(chosenFile));
			BufferedReader reader = new BufferedReader(new StringReader(jTextArea.getText()));
			String nextLine = "";
			while ((nextLine = reader.readLine()) != null) {
				writer.write(nextLine + "\n");
			}
			writer.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void loadFileTo(JTextArea textArea) {
		JFileChooser jFileChooser = new JFileChooser();

		if (jFileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION
				|| jFileChooser.getSelectedFile() == null) {
			return;
		}

		File chosenFile = jFileChooser.getSelectedFile();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(chosenFile));
			String line = reader.readLine();
			StringBuffer stringBuffer = new StringBuffer();

			while (line != null) {
				stringBuffer.append(line + "\n");
				line = reader.readLine();
			}

			reader.close();
			textArea.setText(stringBuffer.toString());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private JPanel getCenterPannel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2, 5, 0));
		panel.add(new JScrollPane(plainTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		encodedTextArea.setLineWrap(true);
		panel.add(new JScrollPane(encodedTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		return panel;
	}

	private JPanel getBottomPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		panel.add(encodeButton);
		panel.add(decodeButton);

		encodeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				encodedTextArea.setText(Base64.getEncoder().encodeToString(plainTextArea.getText().getBytes()));
			}
		});

		decodeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				plainTextArea
						.setText(new String(Base64.getDecoder().decode(encodedTextArea.getText().trim().getBytes())));
			}
		});
		return panel;
	}

	public Base64EncoderDecoder() {
		super("Base64 Encoder-Decoder");
		setSize(1000, 750);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		setJMenuBar(getMyMenuBar());
		getContentPane().add(getCenterPannel(), BorderLayout.CENTER);
		getContentPane().add(getBottomPanel(), BorderLayout.SOUTH);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Base64EncoderDecoder();
	}
}
