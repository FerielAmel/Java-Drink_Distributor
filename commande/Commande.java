package commande;

import java.util.*;
import stock.Stock;
import caisse.Caisse;

public class Commande implements CommandeRemote {
	
	Stock stock = new Stock();
	Caisse caisse = new Caisse();
    public Commande(){
    }

    public ArrayList<stock.StockRemote.Boisson> getMenu(){
		return stock.getBoissons();
	}
	
	public Map<Integer, Integer>  acheterBoisson(int idBoisson, Map<Integer, Integer> monnaie){
		stock.StockRemote.Boisson boisson =  stock.getBoisson(idBoisson);
		if (boisson != null){
			Map<Integer, Integer> restePieces =  caisse.rendreMonnaie(monnaie, boisson.prix);
			
			if (restePieces != null){
				/*Le paiement a ete effectue avec succes*/
				 stock.acheterBoisson(idBoisson);
				return restePieces;
			}
			else{
				/*Echec du paiement*/
				return null;
			}
		}
		return null;
	}
	
	public int insererBoisson(String nom, int prix, int quantite){
		return stock.insererBoisson(nom, prix, quantite);
	}

	public void remplirCaisse(Map<Integer, Integer> monnaie){
		caisse.remplirCaisse(monnaie);
	}

	public int viderCaisse(Map<Integer, Integer> monnaie){
		return caisse.viderCaisse(monnaie);
	}

	@Override
	public Map<Integer, Integer> recupererCaisse() {
		return  caisse.contenuCaisse();
	}

}
