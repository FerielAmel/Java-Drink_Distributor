package commande;
import stock.StockRemote;
import java.util.ArrayList;
import java.util.Map;

public interface CommandeRemote {
	
	public ArrayList<StockRemote.Boisson> getMenu();
	
	public Map<Integer, Integer>  acheterBoisson(int idBoisson, Map<Integer, Integer> monnaie);
	
	public int insererBoisson(String nom, int prix, int quantite);

	public void remplirCaisse(Map<Integer, Integer> monnaie);

	public int viderCaisse(Map<Integer, Integer> monnaie);
	
	public Map<Integer, Integer>  recupererCaisse();
	
}

