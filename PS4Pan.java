package Evenementielle.AJS.Puissance4;

import javax.swing.*;
import java.awt.*;

public class PS4Pan extends JPanel {
    PS4Pan(int nbLigne, int nbColonne) {
        this.nbLigne = nbLigne;
        this.nbColonne = nbColonne;
        resetPS4();
    }

    /**
     * Dessine les boules vides qui formeent la grille
     * @param g: Graphics g
     */
    private void dessinePlateau(Graphics g) {
        int posDepX;
        int posDepY = 50; //50 pour l'espace où sort les boules à jouer
        for (int i = 0; i < nbLigne; i++) {
            posDepX = 0;
            for (int j = 0; j < nbColonne; j++) {
                if (j == curColonne) {
                    g.setColor(Color.red);
                } else {
                    g.setColor(Color.darkGray);
                }
                g.drawOval(posDepX, posDepY, w, h);
                posDepX += w;
            }
            posDepY += h;
        }
    }

    /**
     * Dessine les boules;
     * @param g: Graphics g
     */
    private void dessineBoule(Graphics g) {
        for (int i = 0; i < nbBouleJouer; i++) {
            g.setColor(couleurs[i % 2]); //Selection de la couleur de la boule
            //Si c'est le dernier élément du tableau
            //Alors c'est la boule courante
            //Sinon on replce les boules déjà jouées
            if (i == nbBouleJouer - 1) {
                g.fillOval(curBoulePosX, 0, w, h);
            } else {
                g.fillOval(posX[i], posY[i], w, h);
            }
        }
    }

    /**
     * On dessine le plateau
     * On dessine les boules
     * @param g: Graphics g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        dessinePlateau(g);
        dessineBoule(g);
    }

    /**
     * @return true si il ya une victoire de l'un des joueurs sinon false
     */

    public boolean verification() {
        int v = 0;
        int indexCouleur = curBouleCouleurIndex == 0 ? 1 : 0;

        //Gain vertical
        for (int i = 0, l = curLigne[curColonne]; l > i; i++) {
            if (lignesColonnes[i][curColonne] == indexCouleur) {
                if (++v >= 4) {
                    return true;
                }
            } else {
                v = 0;
            }
        }

        //Gain horizontal
        v = 0;
        for (int i = 0, l = curLigne[curColonne] - 1; i < nbColonne; i++) {
            if (lignesColonnes[l][i] == indexCouleur) {
                if (++v >= 4) {
                    return true;
                }
            } else {
                v = 0;
            }
        }

        //Gain oblique 1 "/"
        int d = curColonne - (curLigne[curColonne] - 1);
        v = 0;
        if (d >= 0) {
            for (int l = 0; d < nbColonne; d++, l++) {
                if (lignesColonnes[l][d] == indexCouleur) {
                    if (++v >= 4) {
                        return true;
                    }
                } else {
                    v = 0;
                }
            }
        } else {
            for (int i = Math.abs(d), c = 0; i < nbColonne; i++, c++) {
                if (lignesColonnes[i][c] == indexCouleur) {
                    if (++v >= 4) {
                        return true;
                    }
                } else {
                    v = 0;
                }
            }
        }

        //Gain oblique 2 "\"
        d = curColonne + (curLigne[curColonne] - 1);
        v = 0;
        if (d < nbLigne) {
            for (int i = 0; i < (d + 1); i++) {
                if (lignesColonnes[i][d - i] == indexCouleur) {
                    if (++v >= 4) {
                        return true;
                    }
                } else {
                    v = 0;
                }
            }
        } else {
            for (int i = (d - nbLigne) + 1, t = 1; i < nbLigne; i++, t++) {
                if (lignesColonnes[i][nbColonne - i] == indexCouleur) {
                    if (++v >= 4) {
                        return true;
                    }
                } else {
                    v = 0;
                }
            }
        }

        return false;
    }

    /**
     * @param sens: le sens de déplacement de la boule
     *              Si sens = true alors la boule se déplace vers la gauche
     *              sinon vers la droite
     * @param desc: Si true la boule courante est jouée; il prime sur sens.
     */

    public void moveCurrentBoule(boolean sens, boolean desc) {
        if (!desc) {
            if (sens) {
                //Si la boule a atteint le bord gauche alors il passe de l'autre côté au nivau du bord droit
                if (curColonne == 0) {
                    curColonne = (nbColonne - 1);
                    curBoulePosX = curColonne * 50;
                } else {
                    curBoulePosX -= 50;
                    curColonne--;
                }
            }
            //Si la boule a atteint le bord droit alors il passe de l'autre côté au nivau du bord gauche
            else {
                if (curColonne == (nbColonne - 1)) {
                    curBoulePosX = 0;
                    curColonne = 0;
                } else {
                    curBoulePosX += 50;
                    curColonne++;
                }
            }
        } else {
            //Si le nombre de boules jouer est égale au nombre de cases sur la plateau
            //Alors il y a match null
            if (nbBouleJouer == nbLigne * nbColonne) {
                matchNull = true;
            }

            //On supposes que la colonne est plein
            colonePlein = true;
            if (curLigne[curColonne] == nbColonne) return; // Une colonne est pleine

            //Si le programme ici alors la colonne n'est pas plein
            colonePlein = false;
            curLigne[curColonne]++; //On augmente de 1 le nombre de boules joues dans la colonne ciurante
            posX[nbBouleJouer - 1] = curBoulePosX; //On stock son abscisse
            posY[nbBouleJouer - 1] = ((nbColonne + 1) - curLigne[curColonne]) * 50; //On stock son ordonnée
            lignesColonnes[curLigne[curColonne] - 1][curColonne] = curBouleCouleurIndex; //On stock l'indice de
            // la couleur de la boule (jaune = 0, bleu = 1)
            nbBouleJouer++; //On augmente de 1 le nombre de boules jouées
            curBouleCouleurIndex = curBouleCouleurIndex == 0 ? 1 : 0; //On change l'indice de la boule
            // Si l'indice était 0 alors il devient 1 et vice versa
        }
        repaint();
    }

    public int getCurBouleCouleurIndex() {
        return curBouleCouleurIndex;
    }

    public boolean isColonePlein() {
        return colonePlein;
    }

    public int getNbBouleJouer() {
        return nbBouleJouer;
    }

    public boolean isMacthNull() {
        return matchNull;
    }

    /**
     * permet de réinitailiser le jeu;
     */
    public void resetPS4() {
        nbBouleJouer = 1;
        lignesColonnes = new int[nbLigne][nbColonne];
        curLigne = new int[nbColonne];
        for (int i = 0; i < nbLigne; i++) {
            for (int j = 0; j < nbColonne; j++) {
                lignesColonnes[i][j] = -1;
            }
        }
        for (int i = 0; i < nbColonne; i++) {
            curLigne[i] = 0;
        }

        int t = nbLigne * nbColonne;
        posX = new int[t];
        posY = new int[t];

        curColonne = 0;
        curBoulePosX = 0;

        matchNull = false;
        colonePlein = false;
    }

    private int w = 50; //La largeur des boules
    private int h = 50; //La hauteur des boules

    private int nbLigne, nbColonne; //Nombre de lignes et nombre de colonnes

    static private int[][] lignesColonnes; //Stoque les positions de chaque index couleur;
    // Utile pour vérifier s'il ya victoire

    static private int[] curLigne; //Stoque le nombre de boules dans une colonne
    private int curColonne; //La colonne de la boule courante

    private int curBoulePosX = 0; //L'abscisse de la boule qui doit être jour

    private int[] posX, posY; //Stoquent les positions de chaque boule; Utile pour redessiner le panneau

    static private int nbBouleJouer = 1; //Nombre de boules jouées.Par défaut le car le jeu commence
    // avec une boule prete à etre jouée

    static private boolean colonePlein = false; //Devient vrai lorsqu'une colonne est pleine
    private boolean matchNull = false;

    private int curBouleCouleurIndex = 0; //L'indice de la couleur dans le tableau couleurs de la boule courante
    static private Color[] couleurs = {Color.yellow, Color.blue};
}