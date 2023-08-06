import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import stock.StockRemote;
import commande.Commande;

public class client extends JFrame {

	private JTextArea screenTextArea;
	private static Commande cmd = new Commande();
	ArrayList<StockRemote.Boisson> liste;
	private String choix = "0";
	private String etatActuel = "0";
	private String indexChoix = "";
	private int idBoisson;
	private Map<Integer, Integer> monnaie;
	private Map<Integer, Integer> reste;
	private String[] labels = {"200", "100", "50", "20", "10", "5"};
	private JTextField[] fields;
	private String[] columnNames = {"ID", "Nom", "Prix", "Quantit� disponible"};
	private Object[][] data;
	private JTable table;
	private String sauv="";
	private JTextField nameField;
	private String name="";
	private int quantity=0;
	private int price=0;
	
    public client() {
    	monnaie = new HashMap<>();
        reste = new HashMap<>();
        
        setTitle("Distributeur de boissons");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 1200);
        setLayout(new GridLayout(5, 1));
        

        

        /*****************Screen Display********************/
        JPanel screenPanel = new JPanel();
        screenPanel.setPreferredSize(new Dimension(850, 350));
        screenPanel.setLayout(new FlowLayout());
        screenTextArea = new JTextArea();
        screenTextArea.setEditable(false);
        screenTextArea.setPreferredSize(new Dimension(300, 400));
        screenPanel.add(screenTextArea);
        screenTextArea.append("1- Afficher le menu \n2- Acheter une boisson \n3- Mode administrateur\n\n Choix : ");
       
        liste = cmd.getMenu();
        data = new Object[liste.size()+10][4];
    	for (int i = 0; i < liste.size(); i++) {
    	    StockRemote.Boisson element = liste.get(i);
    	    data[i][0] = element.id;
    	    data[i][1] = element.nom;
    	    data[i][2] = element.prix;
    	    data[i][3] = element.quantite_dispo;
    	}
        
        table = new JTable(data, columnNames);
        table.setPreferredSize(new Dimension(300, 500));
        JScrollPane scrollPane = new JScrollPane(table); // Wrap the table in a JScrollPane
        
        screenPanel.add(scrollPane);
        
        add(screenPanel);
        
        
        
        /*****************Numeric Keypad******************/    
        JPanel keypadPanel = new JPanel();
        keypadPanel.setPreferredSize(new Dimension(300, 130));
        keypadPanel.setLayout(new GridLayout(4, 3, 5, 5));

        for (int i = 1; i <= 9; i++) {
            JButton button = new JButton(Integer.toString(i));
            final int index = i;
            button.setBackground(Color.BLACK); 
            button.setForeground(Color.WHITE);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	screenTextArea.append(Integer.toString(index));
                	indexChoix = indexChoix + Integer.toString(index);
                }
            });
            keypadPanel.add(button);
        }

        JButton okButton = new JButton("OK");
        okButton.setBackground(Color.BLACK); // Set the background color
        okButton.setForeground(Color.WHITE);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	gestionMenu(indexChoix);
            	indexChoix = "";
            }
        });
        keypadPanel.add(okButton);

        JButton zeroButton = new JButton("0");
        zeroButton.setBackground(Color.BLACK); // Set the background color
        zeroButton.setForeground(Color.WHITE);
        zeroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenTextArea.append("0");
            	indexChoix = indexChoix + "0";
            }
        });
        keypadPanel.add(zeroButton);

        JButton annulerButton = new JButton("Annuler");
        annulerButton.setBackground(Color.BLACK); // Set the background color
        annulerButton.setForeground(Color.WHITE);
        annulerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenTextArea.setText("");
                choix = "0";
                etatActuel = "0";
                screenTextArea.append("1- Afficher le menu \n2- Acheter une boisson \n3- Mode administrateur\n\n Choix : ");
            }
        });
        keypadPanel.add(annulerButton);

        JPanel numericKeypadPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        numericKeypadPanel.add(keypadPanel);
        add(numericKeypadPanel);



        /****************Coins disposal********************/
        JPanel spinBoxesPanel = new JPanel();
        spinBoxesPanel.setPreferredSize(new Dimension(700, 50));
        spinBoxesPanel.setLayout(new FlowLayout());

        
        JSpinner[] spinners = new JSpinner[labels.length];

        for (int i = 0; i < labels.length; i++) {
            JLabel labelBox = new JLabel(labels[i]);
            spinners[i] = new JSpinner();
            spinners[i].setPreferredSize(new Dimension(60, 40));
            spinBoxesPanel.add(spinners[i]);
            spinBoxesPanel.add(labelBox);
        }

        add(spinBoxesPanel);

        JButton confirmerButton = new JButton("Confirmer");
        confirmerButton.setPreferredSize(new Dimension(100, 40));
        confirmerButton.setBackground(Color.BLACK);
        confirmerButton.setForeground(Color.WHITE);
        confirmerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < labels.length; i++) {
                    Object nombre = spinners[i].getValue();
                    int intNombre = (int) nombre;
                    /* Mise � jour de la map monnaie */
                    monnaie.put(Integer.parseInt(labels[i]), intNombre);
                }
                gestionMenu("confirmer");
            }
        });
        spinBoxesPanel.add(confirmerButton);
        
        
        
    
        
        
        /*********************Coins returned**************************/
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setPreferredSize(new Dimension(600, 50));
        fieldsPanel.setLayout(new FlowLayout());
        JLabel label = new JLabel("Contenu caisse/ Monnaie");
        fieldsPanel.add(label);
        
        fields = new JTextField[labels.length];
        for (int i = 0; i < labels.length; i++) {
            JLabel labelBox = new JLabel(labels[i]);
            fields[i] = new JTextField();
            fields[i].setHorizontalAlignment(SwingConstants.RIGHT);
            fields[i].setPreferredSize(new Dimension(60, 40));
            fieldsPanel.add(fields[i]);
            fieldsPanel.add(labelBox);
        }

        add(fieldsPanel);
        
        
        
        /***********************Drink name*******************************/
        JPanel namePanel = new JPanel();
        
        // Create a label
        JLabel nameLabel = new JLabel("Nom de la boisson:");
        namePanel.add(nameLabel);
        
        // Create a text input field
        nameField = new JTextField(30);
        nameField.setPreferredSize(new Dimension(150, 30));
        namePanel.add(nameField);
        
        add(namePanel);
        
        
        
        /**************************************************************/
        pack(); 
        setLocationRelativeTo(null);
        
    }
    
    
    /*******************************Gestion du menu******************************************/
    private void gestionMenu(String choix) {
    	screenTextArea.setText("");
    	switch(etatActuel){
    	/*Dans le cas o� je suis dans le menu principal*/
    	case "0":
    		switch(choix){
    	    case "1":
    	    	 /*On recupere la liste des boissons*/
    	    	liste = cmd.getMenu();
    	    	for (int i = 0; i < liste.size(); i++) {
    	    	    StockRemote.Boisson element = liste.get(i);
    	    	    data[i][0] = Integer.toString(element.id);
    	    	    data[i][1] = element.nom;
    	    	    data[i][2] = Integer.toString(element.prix);
    	    	    data[i][3] = Integer.toString(element.quantite_dispo);
    	    	}

    	    	// Repaint the table to reflect the changes
    	    	table.repaint();
    	    	screenTextArea.append("Le menu est affich� � droite.\n");
    	        etatActuel = "0";
    	    	break;
    	    case "2":
    	    	screenTextArea.append("Entrez l'id de la boisson: ");
    	    	etatActuel = "2";
    	    	break;
    	    case "3":
    	    	screenTextArea.append("1- Ins�rer des boissons \n2- Afficher le contenu de la caisse \n3- Remplir la caisse \n4- Vider la caisse\n\n Choix : ");
    	    	etatActuel = "admin";
    	    	break;
    	    default:
    	    }
    		break;
    	/*Dans le cas o� je suis dans la s�l�ction de boissons*/
    	case "2":
    		switch(choix){
    		case "confirmer":
    			reste = cmd.acheterBoisson(idBoisson, monnaie);
    			if (reste != null){
    				for (int i = 0; i < labels.length; i++) {
        	            Integer nombre = reste.get(Integer.parseInt(labels[i]));
        	            fields[i].setText(Integer.toString(nombre));
        	        }
    				liste = cmd.getMenu();
        	    	for (int i = 0; i < liste.size(); i++) {
        	    	    StockRemote.Boisson element = liste.get(i);
        	    	    data[i][0] = Integer.toString(element.id);
        	    	    data[i][1] = element.nom;
        	    	    data[i][2] = Integer.toString(element.prix);
        	    	    data[i][3] = Integer.toString(element.quantite_dispo);
        	    	}

        	    	// Repaint the table to reflect the changes
        	    	table.repaint();
        			screenTextArea.setText("Payement effectu� avec succ�s.\nR�cup�rez votre boisson ainsi que votre monnaie!\n");
    			}
    			else{
    				screenTextArea.setText("Erreur au payement, veuillez r�essayer.\n");
    			}
    			
    			break;
    		default:
    			idBoisson = Integer.parseInt(choix);
    			screenTextArea.append("Boisson s�l�ctionn�e:"+ choix +"\nEntrez la monnaie et appuyer sur confirmer.\n");
    		}
    		break;
    	/*Dans le cas o� je suis en mode admin*/
    	case "admin":
    		if (sauv.equals("insertion")){
    			screenTextArea.setText("Entrez la quantit�: ");
    			name = nameField.getText();
    			price = Integer.parseInt(choix);
    			sauv = "insertion fin";
    		}
    		else if(sauv.equals("insertion fin")){
    			quantity = Integer.parseInt(choix);
    			cmd.insererBoisson(name, price, quantity);
    			screenTextArea.setText("El�ments ins�r�s avec succ�s!");
    			liste = cmd.getMenu();
    	    	for (int i = 0; i < liste.size(); i++) {
    	    	    StockRemote.Boisson element = liste.get(i);
    	    	    data[i][0] = Integer.toString(element.id);
    	    	    data[i][1] = element.nom;
    	    	    data[i][2] = Integer.toString(element.prix);
    	    	    data[i][3] = Integer.toString(element.quantite_dispo);
    	    	}

    	    	// Repaint the table to reflect the changes
    	    	table.repaint();
    	    	sauv = "";
    		}
    		else{
	    		switch(choix){
	    		case "1":
	    			screenTextArea.setText("Ins�rez le nom de la boisson.\nEntrez le prix: ");
	    			sauv = "insertion";
	    			break;
	    		case "2":
	    			screenTextArea.setText("Le contenu de la caisse est affich�.\n");
	    			reste = cmd.recupererCaisse();
	    			for (int i = 0; i < labels.length; i++) {
	    	            Integer nombre = reste.get(Integer.parseInt(labels[i]));
	    	            fields[i].setText(Integer.toString(nombre));
	    	        }
	    			break;
	    		case "3":
	    			screenTextArea.setText("Indiquez combien vous voulez inserer.\nPuis appuyer sur confirmer");
	    			sauv = "3";
	    			break;
	    		case "4":
	    			screenTextArea.setText("Indiquez combien vous voulez retirer.\nPuis appuyer sur confirmer");
	    			sauv = "4";
	    			break;
	    		case "confirmer":
	    			if (sauv.equals("3")){
	    			/*Cas insertion*/
	    				cmd.remplirCaisse(monnaie);
	    				screenTextArea.setText("Pi�ces ajout�es avec succ�s.");
	    			}
	    			else{
	    			/*Cas retrait*/
	    				if (cmd.viderCaisse(monnaie) == 1){
	    					screenTextArea.setText("Pi�ces retir�es avec succ�s.");	
	    				}
	    				else{
	    					screenTextArea.setText("Erreur, la quantit� demand�e exc�de la quantit� disponible.");
	    				}
	    				
	    			}
	    			break;
	    		default:
	    		}
	    	}
    		break;
    	default:
    	}
	    
    }

    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
        		cmd.insererBoisson("Eau", 25, 5);
        		cmd.insererBoisson("Coca Cola", 70, 2);
        		cmd.insererBoisson("Jus Rouiba", 45, 5);
        		cmd.insererBoisson("Fanta Pomme", 50, 3);
        		cmd.insererBoisson("Sprite", 60, 4);
        		cmd.insererBoisson("Mouzaia", 100, 3);
        		cmd.insererBoisson("Ifruit", 75, 7);
        		
        		client gui = new client();
                gui.setVisible(true);
            }
        });
    }

}
