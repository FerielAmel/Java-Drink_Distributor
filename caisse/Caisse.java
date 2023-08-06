package caisse;
import java.util.Map;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;

import java.util.Iterator;


public class Caisse implements CaisseRemote {
	
	/*Liste des pi�ces autoris�es au paiement*/
	private List<Integer> piecesAutorisees;
	/*Contenu de la caisse en termes de monnaie*/
	private Map<Integer, Integer> contenuCaisse;
	/*Liste de la monnaie � rendre*/
	private Map<Integer, Integer> restePieces;

    public Caisse() {
        // Initialize the list with some values
    	piecesAutorisees = new ArrayList<>();
        contenuCaisse = new HashMap<>();
        restePieces = new HashMap<>();
        piecesAutorisees.add(200);
        piecesAutorisees.add(100);
        piecesAutorisees.add(50);
        piecesAutorisees.add(20);
        piecesAutorisees.add(10);
        piecesAutorisees.add(5);
        contenuCaisse.put(200, 1);
        contenuCaisse.put(100, 2);
        contenuCaisse.put(50, 3);
        contenuCaisse.put(20, 3);
        contenuCaisse.put(10, 5);
        contenuCaisse.put(5, 10);
        restePieces.put(200, 0);
        restePieces.put(100, 0);
        restePieces.put(50, 0);
        restePieces.put(20, 0);
        restePieces.put(10, 0);
        restePieces.put(5, 0);
    }
  

	@Override
	public Map<Integer, Integer> rendreMonnaie(Map<Integer, Integer> monnaie, int prixProduit) {
		int montant = 0;
		int resteTotal = 0;
		if (validitePieces(monnaie)){
			montant = validiteMontant(monnaie, prixProduit);
			if (montant > 0){
				resteTotal = montant - prixProduit;
				if(validiteMonnaie(monnaie, resteTotal)){
					/*Le paiement a �t� effectu� avec succ�s*/
					return restePieces;
				}
				else{
					/*Echec du paiement*/
					annulerPaiement(monnaie);
				}
			}
		}
		return null;
	}

	@Override
	public boolean validitePieces(Map<Integer, Integer> monnaie) {
		
		boolean pieceValide = true;
		Iterator<Map.Entry<Integer, Integer>> iterator = monnaie.entrySet().iterator();
		
        while ((iterator.hasNext()) && (pieceValide)) {
            Map.Entry<Integer, Integer> entry = iterator.next();
            int valeur = entry.getKey();
            pieceValide = piecesAutorisees.contains(valeur);
        }
		return pieceValide;
	}

	@Override
	public int validiteMontant(Map<Integer, Integer> monnaie, int prixProduit) {
		int montant = 0;
		for (Map.Entry<Integer, Integer> entry : monnaie.entrySet()) {
            int valeur = entry.getKey();
            int nombre = entry.getValue();
            montant = montant + valeur * nombre;
        }
		if(montant >= prixProduit){
			return montant;
		}
		else{
			return 0;
			
		}
	}


	@Override
	public boolean validiteMonnaie(Map<Integer, Integer> monnaie,int reste) {
		int resteMonnaie = reste;
		for (Map.Entry<Integer, Integer> entry : contenuCaisse.entrySet()) {
			int valeur = entry.getKey();
			/*On prend en consid�ration les pi�ces introduites par l'utilisateur dans le calcul de la monnaie*/
			Integer monnaieClient = monnaie.get(valeur);
			if(monnaieClient == null){
				monnaieClient = 0;
			}
			contenuCaisse.put(valeur, entry.getValue()+ monnaieClient);
            int nombre = entry.getValue();
            int nombrePieces = Math.min(nombre, resteMonnaie/valeur);
            resteMonnaie -= nombrePieces * valeur;
            /*Mettre � jour la Map pour y mettre la monnaie � rendre*/
            restePieces.put(valeur, nombrePieces);
        }
		if(resteMonnaie == 0){
        	return true;
        }
		else{
			return false;
		}
	}

	@Override
	public int viderCaisse(Map<Integer, Integer> monnaie) {
		for (Map.Entry<Integer, Integer> entry : contenuCaisse.entrySet()) {
			int valeur = entry.getKey();
			int nombre = entry.getValue();
			Integer monnaieAdmin = monnaie.get(valeur);
			if(monnaieAdmin == null){
				monnaieAdmin = 0;
			}
			if(nombre >=  monnaieAdmin){
				contenuCaisse.put(valeur, entry.getValue() - monnaieAdmin);
			}
			else{
				/*Il n'y a pas assez de monnaie � retirer*/
				return 0;
			}
         }
		return 1;
	}

	@Override
	public void remplirCaisse(Map<Integer, Integer> monnaie) {
		for (Map.Entry<Integer, Integer> entry : contenuCaisse.entrySet()) {
			int valeur = entry.getKey();
			Integer monnaieAdmin = monnaie.get(valeur);
			if(monnaieAdmin == null){
				monnaieAdmin = 0;
			}
			contenuCaisse.put(valeur, entry.getValue() + monnaieAdmin);
            }
	}


	@Override
	public void annulerPaiement(Map<Integer, Integer> monnaie) {
		for (Map.Entry<Integer, Integer> entry : contenuCaisse.entrySet()) {
			int valeur = entry.getKey();
			Integer monnaieClient = monnaie.get(valeur);
			if(monnaieClient == null){
				monnaieClient = 0;
			}
			/*Annuler les mises � jour faites sur le contenu de la caisse plus t�t*/
			contenuCaisse.put(valeur, entry.getValue() - monnaieClient);
            }
        }


	@Override
	public Map<Integer, Integer> contenuCaisse() {
		return contenuCaisse;
	}

}
