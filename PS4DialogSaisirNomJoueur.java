package Evenementielle.AJS.Puissance4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class PS4DialogSaisirNomJoueur extends JDialog implements ActionListener{

    PS4DialogSaisirNomJoueur(JFrame parent, String titre, RecupNoms nomsJoueurs, boolean modal){
        super(parent, titre, modal);

        setSize(350,150);
        setLocationRelativeTo(null);
        setResizable(false);

        this.nomsJoeurs = nomsJoueurs;

        Container cont = getContentPane();
        cont.setLayout(new FlowLayout());

        JPanel pan1 = new JPanel();
        lab1 = new JLabel("Nom joueur 1");
        nomJ1 = new JTextField(20);
        pan1.add(lab1);
        pan1.add(nomJ1);

        JPanel pan2 = new JPanel();
        lab2 = new JLabel("Nom joueur 2");
        nomJ2 = new JTextField(20);
        pan2.add(lab2);
        pan2.add(nomJ2);

        btnOk = new JButton("Ok");
        btnOk.addActionListener(this);

        cont.add(pan1);
        cont.add(pan2);
        cont.add(btnOk);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });


        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnOk){
            if(!Objects.equals(nomJ1.getText(), "") && !Objects.equals(nomJ2.getText(), "")){
                setVisible(false);
                nomsJoeurs.nomJ1 = nomJ1.getText();
                nomsJoeurs.nomJ2 = nomJ2.getText();
                nomsJoeurs.curNomJ = nomsJoeurs.nomJ1;
                SELF.dispose();
            }
        }
    }

    private JButton btnOk;
    private JTextField nomJ1, nomJ2;
    private JLabel lab1, lab2;
    private final PS4DialogSaisirNomJoueur SELF = this;
    private RecupNoms nomsJoeurs;
}
