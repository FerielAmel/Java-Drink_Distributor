package stock;

import java.util.ArrayList;

public interface StockRemote {
	
	public class Boisson {
		public Boisson(int id, String nom, int prix, int quantite_dispo){
			this.id = id;
			this.nom = nom;
			this.prix = prix;
			this.quantite_dispo = quantite_dispo;
		}
		public int id;
		public String nom;
		public int prix;
		public int quantite_dispo;
	}
	

	public ArrayList<Boisson> getBoissons();
	public Boisson getBoisson(int idBoisson);
	public boolean acheterBoisson(int idBoisson);
	
	public int insererBoisson(String nom, int prix, int quantite);
	public boolean majBoisson(Boisson boisson);
	public boolean supprBoisson(int idBoisson);
	
}