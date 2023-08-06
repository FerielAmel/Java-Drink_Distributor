package caisse;

import java.util.Map;

public interface CaisseRemote {
	public Map<Integer, Integer> rendreMonnaie(Map<Integer, Integer> monnaie, int prixProduit);
	public boolean validitePieces(Map<Integer, Integer> monnaie);
	public int validiteMontant(Map<Integer, Integer> monnaie, int prixProduit);
	public void annulerPaiement(Map<Integer, Integer> monnaie);
	public boolean validiteMonnaie(Map<Integer, Integer> monnaie, int reste);
	public int viderCaisse(Map<Integer, Integer> monnaie);
	public void remplirCaisse(Map<Integer, Integer> monnaie);
	public Map<Integer, Integer> contenuCaisse();
}
