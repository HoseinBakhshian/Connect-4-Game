import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Win_Alert implements ActionListener{

	private static JButton alert_button;
	private static JFrame alert_frame;
	private static JLabel label_alert;

	public void alert(String result)
	{
		alert_frame = new JFrame();
		alert_frame.setResizable(false);
		alert_frame.setSize( 400, 400);
		alert_frame.setLocationRelativeTo(null);
		alert_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		alert_frame.getContentPane().setLayout(null);
		alert_frame.getContentPane().setBackground(new Color(173, 216, 230));

		alert_button = new JButton("NEW GAME");
		alert_button.setBounds(100, 170, 200, 60);
		alert_button.addActionListener(this);
		alert_button.setFont(new Font("Arial Black", Font.BOLD, 20));
		alert_button.setBackground(Color.gray);
		alert_button.setFocusPainted(false);              
		alert_button.setBorderPainted(false);

		label_alert = new JLabel(result);
		label_alert.setBounds(100,50,200,75);
		label_alert.setFont(new Font("Arial Black", Font.BOLD, 20));
		label_alert.setHorizontalTextPosition(SwingConstants.CENTER);
		label_alert.setHorizontalAlignment(SwingConstants.CENTER);


		alert_frame.add(label_alert);
		alert_frame.add(alert_button);	
		alert_frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if(e.getSource()==alert_button)
		{
			alert_frame.dispose();
			new Run();

		}

	}




}
