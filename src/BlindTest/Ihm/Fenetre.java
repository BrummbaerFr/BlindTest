/**
 * Classe Fenetre, elle gère l'interaction avec l'utilisateur et créé les Lecteur permettant de lire les
 * fichiers audios choisis par l'utilisateur.
 *
 * @author Richard BLONDEL
 * @version 1.0
 * @since 23-04-2017
 */
package BlindTest.Ihm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import BlindTest.Metier.Lecteur;


public class Fenetre extends JFrame implements ActionListener {
	private JLabel message;

	private JFileChooser chooser;
	private JButton choisirDossier;
	private ArrayList<File> listeFichiers;

	private JButton montrerTitre;
	private JButton lire;
	private JButton trouve;

	private int cpt = 0;

	private Lecteur lecteur;

	/**
	 * Constructeur de la fenêtre.
	 * On y initialise tous les boutons et le label qui permettent l'interaction.
	 * L'utilisateur choisit le dossier contenant les musiques et peut ensuite
	 * les jouer en cliquant sur "Lire" et "Trouvé !".
	 */
	public Fenetre() {
		this.setTitle("Blindtest !");
		this.setSize(400, 500);

		this.setLayout(new GridLayout(5, 1, 0, 15));
		this.setDefaultCloseOperation(3);

		this.message = new JLabel("Choisissez votre dossier contenant les musiques", SwingConstants.CENTER);
		this.add(this.message);

		this.choisirDossier = new JButton("Parcourir ...");
		this.choisirDossier.addActionListener(this);
		this.add(this.choisirDossier);

		this.montrerTitre = new JButton("Révéler le titre");
		this.montrerTitre.addActionListener(this);
		this.add(this.montrerTitre);

		this.lire = new JButton("Lire");
		this.lire.addActionListener(this);
		this.add(this.lire);

		this.trouve = new JButton("Trouvé !");
		this.trouve.addActionListener(this);
		this.add(this.trouve);

		this.setVisible(true);
	}

	/**
	 * Gère les évènements liés aux boutons de la fenêtre.
	 * Effectue l'action correspondante au bouton cliqué :
	 * - choisir un dossier (Parcourir ...)
	 * - lire les fichiers audio (Lire)
	 * - arrêter la lecture (Trouvé !)
	 * - révéler le titre du fichier (Révéler le titre)
	 *
	 * @param e l'évènement déclenché par le bouton cliqué
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.choisirDossier) {
			this.chooser = new JFileChooser();
			this.chooser.setCurrentDirectory(new File("."));
			this.chooser.setDialogTitle("Choisissez un dossier ...");
			this.chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			this.chooser.setAcceptAllFileFilterUsed(false);

			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				this.listeFichiers = new ArrayList<>(Arrays.asList(this.chooser.getSelectedFile().listFiles()));
				this.message.setText("Dossier choisi ! Cliquez sur Lire pour lancer la lecture");
			} else {
				this.message.setText("Aucun dossier choisi.");
			}
		} else if (e.getSource() == this.lire) {
			if (this.listeFichiers != null && this.cpt < this.listeFichiers.size()) {
				this.lecteur = new Lecteur(this.listeFichiers.get(this.cpt++).getAbsolutePath());
				this.lecteur.jouer();

				this.message.setText("Qui devinera le titre et l'interprète ?");
			}
		} else if (e.getSource() == this.trouve) {
			this.lecteur.pause();
			this.lecteur.fermer();

			this.message.setText("Bien joué ! C'était " +
					this.listeFichiers.get(this.cpt - 1).getName().substring(0, this.listeFichiers.get(this.cpt - 1).getName().length() - 4));
		} else if (e.getSource() == this.montrerTitre) {
			this.message.setText("Le titre est : " + this.listeFichiers.get(this.cpt - 1));
		}
	}
}
