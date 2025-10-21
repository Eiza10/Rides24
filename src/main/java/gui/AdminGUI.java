package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import domain.*;
import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class AdminGUI extends JFrame {
	
    private Admin admin;
	private static final long serialVersionUID = 1L;
	private static final String ETIQUETAS = "Etiquetas";

	private JPanel jContentPane = null;

    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	protected JLabel jLabelSelectOption;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton jButtonLogOut;
	private JButton jButtonHandle;
	
	/**
	 * This is the default constructor
	 */
	public AdminGUI(Admin ad) {
		super();

		this.admin=ad;
		
		// this.setSize(271, 295);
		this.setSize(495, 290);
		jLabelSelectOption = new JLabel(ResourceBundle.getBundle(ETIQUETAS).getString("DriverAndTravelerGUI.Welcome")+" "+admin.getName());
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		
		rdbtnNewRadioButton = new JRadioButton("English");
		rdbtnNewRadioButton.addActionListener(createLocaleActionListener("en"));
		buttonGroup.add(rdbtnNewRadioButton);
		
		rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
		rdbtnNewRadioButton_1.addActionListener(createLocaleActionListener("eus"));
		buttonGroup.add(rdbtnNewRadioButton_1);
		
		rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
		rdbtnNewRadioButton_2.addActionListener(createLocaleActionListener("es"));
		buttonGroup.add(rdbtnNewRadioButton_2);
	
		panel = new JPanel();
		panel.add(rdbtnNewRadioButton_1);
		panel.add(rdbtnNewRadioButton_2);
		panel.add(rdbtnNewRadioButton);
		
		jContentPane = new JPanel();
		jContentPane.setLayout(new GridLayout(6, 2, 0, 0));
		jContentPane.add(jLabelSelectOption);
		
		jButtonLogOut = new JButton(ResourceBundle.getBundle(ETIQUETAS).getString("DriverAndTravelerGUI.JButtonLogOut")); //$NON-NLS-1$ //$NON-NLS-2$
		jButtonLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		jButtonHandle = new JButton(ResourceBundle.getBundle(ETIQUETAS).getString("AdminGUI.jButtonHandle")); //$NON-NLS-1$ //$NON-NLS-2$
		jButtonHandle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new CheckComplaintAdminGUI();
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonHandle);
		jContentPane.add(jButtonLogOut);
		jContentPane.add(panel);
		
		
		setContentPane(jContentPane);
		setTitle(ResourceBundle.getBundle(ETIQUETAS).getString("MainGUI.MainTitle") + " - Admin :"+ admin.getName());
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});
	}
	
	/**
	 * Creates a locale change action listener
	 * @param localeCode the locale code (e.g., "en", "es", "eus")
	 * @return ActionListener that changes locale and refreshes UI
	 */
	private ActionListener createLocaleActionListener(String localeCode) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale(localeCode));
				System.out.println("Locale: " + Locale.getDefault());
				paintAgain();
			}
		};
	}
	
	private void paintAgain() {
		jLabelSelectOption.setText(ResourceBundle.getBundle(ETIQUETAS).getString("DriverAndTravelerGUI.Welcome")+" "+admin.getName());
		jButtonLogOut.setText(ResourceBundle.getBundle(ETIQUETAS).getString("DriverAndTravelerGUI.JButtonLogOut"));
		jButtonHandle.setText(ResourceBundle.getBundle(ETIQUETAS).getString("AdminGUI.jButtonHandle"));
		this.setTitle(ResourceBundle.getBundle(ETIQUETAS).getString("MainGUI.MainTitle")+ " - admin :"+admin.getName());
	}
	
} // @jve:decl-index=0:visual-constraint="0,0"

