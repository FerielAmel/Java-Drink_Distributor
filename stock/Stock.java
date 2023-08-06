package stock;

import java.util.*;


public class Stock implements StockRemote {
	

	public int nbMaxBoissons;
	public int nbBoissons = 0;
	public ArrayList<Boisson> boissons;
	
	public Stock(int nbMaxBoissons) {
		this.nbMaxBoissons = nbMaxBoissons;
		this.boissons = new ArrayList<Boisson>() ;
	}

	public Stock() {
		this.nbMaxBoissons = 10;
		this.boissons = new ArrayList<Boisson>() ;
	}




	@Override
	public ArrayList<Boisson> getBoissons() {
		return this.boissons;
	}
	
	@Override
	public Boisson getBoisson(int idBoisson) {
		for(Boisson b: this.boissons){
			if (b.id == idBoisson) {
				return b;
			}
		}
		return null;
	}
	
	@Override
	public boolean acheterBoisson(int idBoisson) {
		// check if idBoisson exists then check if quantite_dispo > 0
		for(Boisson b: this.boissons){
			if (b.id == idBoisson) {
				if (b.quantite_dispo > 0) {
					b.quantite_dispo--;
					return true;
				}
				break;
			}
		}
		return false;
	}

	@Override
	public int insererBoisson(String nom, int prix, int quantite) {
		if(this.nbBoissons < this.nbMaxBoissons){
			this.nbBoissons++;
			int idBoisson = this.nbBoissons;
			this.boissons.add(new Boisson(idBoisson, nom, prix, quantite));
			
			return idBoisson;
		}
		return -1;
	}

	@Override
	public boolean majBoisson(Boisson boisson) {
		for(Boisson b: this.boissons){
			if (b.id == boisson.id) {
				b.nom = boisson.nom;
				b.prix = boisson.prix;
				b.quantite_dispo = boisson.quantite_dispo;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean supprBoisson(int idBoisson) {
		this.boissons.remove(idBoisson -1);
		return false;
	}

}


