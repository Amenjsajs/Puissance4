package Evenementielle.AJS.Puissance4;

import Evenementielle.AJS.Puissance4.Utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

import static Evenementielle.AJS.Puissance4.Utils.Utils.getAppIcon;

public class PS4Fen extends JFrame{
    PS4Fen(String titre, boolean visible){
        setTitle(titre);
        setSize(630,500);
        setResizable(false);
        setLocationRelativeTo(null);

        recupNoms = new RecupNoms();

        setNomsJoueurs(this,Libelles.LIB_DIALOG_NOM_JOUEUR,recupNoms,true);

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
        pan = new PS4Pan(nbLigne,nbColonne,recupNoms);
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

        JLabel labTextCurJoueur = new JLabel(Libelles.LIB_LAB_JOUEUR_COURANT);
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

        JLabel labTextScore = new JLabel(Libelles.LIB_LAB_SCORE);
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

        //Panneau où s'affiche les feux d'artifice (3)
        JPanel panFeuArtifice = new JPanel();
        panFeuArtifice.setBackground(Color.black);
        panFeuArtifice.setPreferredSize(new Dimension(250,240));
        panFeuArtifice.setLayout(new FlowLayout());
        JLabel labFeuArtifice = new JLabel();
        panFeuArtifice.add(labFeuArtifice);
        tableAffichage.add(panFeuArtifice);
        //Fin (3)

        //Panneau du bouton de réinitailisation du jeu (4)
        JPanel panBtnReset = new JPanel();
        panBtnReset.setPreferredSize(new Dimension(250,60));
        JButton btnReset = new JButton(Libelles.LIB_BTN_RESET);
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(FEN,Libelles.LIB_DIALOG_RESET) == 0){
                    new PS4DialogSaisirNomJoueur(FEN,Libelles.LIB_DIALOG_NOM_JOUEUR,recupNoms,true);
                    pan.resetPS4();
                    scoreJ1 = 0;
                    scoreJ2 = 0;
                    labScoreJ1.setText(recupNoms.nomJ1.toUpperCase());
                    affScoreJ1.setText("0");
                    labScoreJ2.setText(recupNoms.nomJ2.toUpperCase());
                    affScoreJ2.setText("0");
                    labCurrentJoueur.setText(recupNoms.curNomJ.toUpperCase());
                }
                FEN.requestFocus();
            }
        });
        panBtnReset.add(btnReset);
        tableAffichage.add(panBtnReset);
        // Fin (4)

        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                //Flèche clavier gauche pour aller à gauche
                if(e.getKeyCode() == 39){
                    pan.moveCurrentBoule(false,false);
                }
                //Fin Flèche clavier gauche pour aller à gauche

                //Flèche clavier droit pour aller à drooite
                if(e.getKeyCode() == 37){
                    pan.moveCurrentBoule(true,false);
                }
                //Fin Flèche clavier droit pour aller à drooite

                //Flèche clavier bas ou Espace pour jouer la boule
                if(e.getKeyCode() == 40 || e.getKeyCode() == 32){
                    pan.moveCurrentBoule(false,true);

                    //S'il ya victoire
                    if(pan.isVictoire()){
                        String tmpCurJoueur = recupNoms.curNomJ;
                        if(recupNoms.curNomJ.equals(recupNoms.nomJ1)){
                            recupNoms.curNomJ = recupNoms.nomJ2;
                            affScoreJ2.setText(""+ ++scoreJ2);
                        }else {
                            recupNoms.curNomJ = recupNoms.nomJ1;
                            affScoreJ1.setText(""+ ++scoreJ1);
                        }

                        //Affichage des feux d'artifice
                        int indiceImg = Utils.getRandomIntInclusive(1,11);
                        ImageIcon iconFeuArtifice = Utils.getAppIcon("feu-d-artifice-"+indiceImg+".gif");
                        labFeuArtifice.setIcon(iconFeuArtifice);
                        labFeuArtifice.setVisible(true);

                        //On affiche le message de victoire et on demande si le jeu continue
                        int rep = JOptionPane.showConfirmDialog(e.getComponent(),Libelles.LIB_DIALOG_VICTOIRE+recupNoms.curNomJ+Libelles.LIB_DIALOG_CONTINUER);

                        //Si oui
                        if(rep == 0){
                            //On réinitialise le jeu
                            pan.resetPS4();
                            labFeuArtifice.setVisible(false);
                        }else {
                            System.exit(0);
                        }
                        recupNoms.curNomJ = tmpCurJoueur;
                    }

                    //Si la colonne courante n'est pas pleine
                    if(!pan.isColonePlein()){
                        //On met à jour le nom du joueur courant
                        labCurrentJoueur.setText(recupNoms.curNomJ.toUpperCase());
                    }

                    //S'il y a match null
                    if(pan.isMacthNull()){
                        //On affiche le message de match nul et on demande si le jeu continue
                        int rep = JOptionPane.showConfirmDialog(e.getComponent(),Libelles.LIB_DIALOG_MATCH_NULL+Libelles.LIB_DIALOG_CONTINUER);
                        //Si oui
                        if(rep == 0){
                            //On réinitialise le jeu
                            pan.resetPS4();
                        }else {
                            System.exit(0);
                        }
                    }
                }
                //Fin Flèche clavier bas ou Espace pour jouer la boule

                //Flèche clavier haut pour annuler un mouvement
                if(e.getKeyCode() == 38){
                    pan.undo();
                    pan.repaint();
                    labCurrentJoueur.setText(recupNoms.curNomJ.toUpperCase());
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

    public PS4DialogSaisirNomJoueur setNomsJoueurs(JFrame fen, String msg, RecupNoms recupNoms, boolean modal){
        return new PS4DialogSaisirNomJoueur(fen, msg, recupNoms, modal);
    }

    private PS4Pan pan;
    private int nbLigne, nbColonne;
    private JPanel plateau, tableAffichage;
    private PS4DialogSaisirNomJoueur nomsJoueurs;
    private RecupNoms recupNoms;
    private JLabel labCurrentJoueur;
    private int scoreJ1 = 0, scoreJ2 = 0;
    private final PS4Fen FEN = this;
}
