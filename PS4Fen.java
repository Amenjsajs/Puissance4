package Evenementielle.AJS.Puissance4;

import Evenementielle.AJS.Puissance4.Utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class PS4Fen extends JFrame{
    PS4Fen(String titre, boolean visible){
        setTitle(titre);
        setSize(630,500);
        setResizable(false);
        setLocationRelativeTo(null);

        recupNoms = new RecupNoms();

        nomsJoueurs = new PS4DialogSaisirNomJoueur(this,"Saisir les noms des joueurs",recupNoms,true);

        Container cont = getContentPane();
        FlowLayout fl = new FlowLayout();
        cont.setLayout(fl);
        fl.setHgap(0);
        fl.setVgap(0);

        //Panneau qui contient la grille et le tableau d'affichage
        plateau = new JPanel();
        plateau.setBackground(Color.green);
        plateau.setLayout(new FlowLayout());
        cont.add(plateau);

        //Définition de la grille
        nbLigne = 7; nbColonne = 7;
        pan = new PS4Pan(nbLigne,nbColonne);
        pan.setPreferredSize(new Dimension(350,400));
        plateau.add(pan);

        //Tableau d'affichage
        tableAffichage = new JPanel();
        tableAffichage.setLayout(new FlowLayout());
        tableAffichage.setPreferredSize(new Dimension(250,400));
        plateau.add(tableAffichage);

        //Panneau où est afffiché le nom joueur courant (1)
        JPanel affTop = new JPanel();
        affTop.setPreferredSize(new Dimension(250,50));
        affTop.setLayout(new BorderLayout());

        JLabel labTextCurJoueur = new JLabel("Joueur courant ");
        labTextCurJoueur.setHorizontalAlignment(SwingConstants.CENTER);
        affTop.add(labTextCurJoueur,"North");

        labCurrentJoueur = new JLabel(recupNoms.curNomJ.toUpperCase());
        labCurrentJoueur.setHorizontalAlignment(SwingConstants.CENTER);
        affTop.add(labCurrentJoueur,"South");
        tableAffichage.add(affTop);
        //Fin (1)

        //Panneau où est affiché le score (2)
        JPanel panScore = new JPanel();
        panScore.setLayout(new BorderLayout());
        panScore.setPreferredSize(new Dimension(250,50));

        JLabel labTextScore = new JLabel("Score");
        labTextScore.setHorizontalAlignment(SwingConstants.CENTER);
        panScore.add(labTextScore,"North");
        tableAffichage.add(panScore);

        JPanel panLeftAff = new JPanel();
        panLeftAff.setLayout(new BorderLayout());
        panLeftAff.setPreferredSize(new Dimension(125,50));
        panScore.add(panLeftAff,"West");
        JLabel labScoreJ1 = new JLabel(recupNoms.nomJ1.toUpperCase());
        labScoreJ1.setHorizontalAlignment(SwingConstants.CENTER);
        panLeftAff.add(labScoreJ1,"North");
        JLabel affScoreJ1 = new JLabel(""+scoreJ1);
        affScoreJ1.setHorizontalAlignment(SwingConstants.CENTER);
        panLeftAff.add(affScoreJ1,"South");

        JPanel panRightAff = new JPanel();
        panRightAff.setLayout(new BorderLayout());
        panRightAff.setPreferredSize(new Dimension(125,50));
        panScore.add(panRightAff,"East");
        JLabel labScoreJ2 = new JLabel(recupNoms.nomJ2.toUpperCase());
        labScoreJ2.setHorizontalAlignment(SwingConstants.CENTER);
        panRightAff.add(labScoreJ2,"North");
        JLabel affScoreJ2 = new JLabel(""+scoreJ2);
        affScoreJ2.setHorizontalAlignment(SwingConstants.CENTER);
        panRightAff.add(affScoreJ2,"South");
        //Fin (2)

        JPanel panFeuArtifice = new JPanel();
        panFeuArtifice.setBackground(Color.black);
        panFeuArtifice.setPreferredSize(new Dimension(250,300));
        panFeuArtifice.setLayout(new FlowLayout());
        JLabel labFeuArtifice = new JLabel();
        panFeuArtifice.add(labFeuArtifice);
        panFeuArtifice.setVisible(false);
        tableAffichage.add(panFeuArtifice);

        addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                //Flèche clavier gauche pour aller à gauche
                if(e.getKeyCode() == 39){
                    pan.moveCurrentBoule(false,false);
                }
                //Flèche clavier droit pour aller à drooite
                if(e.getKeyCode() == 37){
                    pan.moveCurrentBoule(true,false);
                }
                //Flèche clavier bas ou Espace pour jouer la boule
                if(e.getKeyCode() == 40 || e.getKeyCode() == 32){
                    pan.moveCurrentBoule(false,true);

                    //S'il ya victoire
                    if(pan.verification()){
                        //On met à jour le score
                        if(recupNoms.curNomJ.equals(recupNoms.nomJ1)){
                            affScoreJ1.setText(""+ ++scoreJ1);
                        }else{
                            affScoreJ2.setText(""+ ++scoreJ2);
                        }

                        int indiceImg = Utils.getRandomIntInclusive(1,11);
                        ImageIcon iconFeuArtifice = Utils.getAppIcon("feu-d-artifice-"+indiceImg+".gif");
                        System.out.println(iconFeuArtifice);
                        labFeuArtifice.setIcon(iconFeuArtifice);
                        panFeuArtifice.setVisible(true);

                        //On affiche le message de victoire et on demande si le jeu continue
                        int rep = JOptionPane.showConfirmDialog(e.getComponent(),"Victoire de "+recupNoms.curNomJ+"\nContinuer le jeu?");

                        //Si oui
                        if(rep == 0){
                            //On réinitialise le jeu
                            pan.resetPS4();
                            pan.repaint();
                            panFeuArtifice.setVisible(false);
                        }
                    }

                    //Si la colonne courante n'est pas pleine
                    if(!pan.isColonePlein()){
                        //On met à jour le nom du joueur courant
                        recupNoms.curNomJ = recupNoms.curNomJ.equals(recupNoms.nomJ1) ? recupNoms.nomJ2 : recupNoms.nomJ1;
                        labCurrentJoueur.setText(recupNoms.curNomJ.toUpperCase());
                    }

                    //S'il y a match null
                    if(pan.isMacthNull()){
                        //On affiche le message de match nul et on demande si le jeu continue
                        int rep = JOptionPane.showConfirmDialog(e.getComponent(),"Match nul \nContinuez le jeu?");
                        //Si oui
                        if(rep == 0){
                            //On réinitialise le jeu
                            pan.resetPS4();
                            pan.repaint();
                        }
                    }
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        if(visible){
            setVisible(visible);
        }
    }

    private PS4Pan pan;
    private int nbLigne, nbColonne;
    private JPanel plateau, tableAffichage;
    private PS4DialogSaisirNomJoueur nomsJoueurs;
    private RecupNoms recupNoms;
    private JLabel labCurrentJoueur;
    private int scoreJ1 = 0, scoreJ2 = 0;
}
