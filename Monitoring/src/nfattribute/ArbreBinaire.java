/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nfattribute;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Ramzi
 * @param <T>
 */
public class ArbreBinaire<T extends Comparable> {
    private T neoud;
	private ArbreBinaire <T> aBG;
	private ArbreBinaire <T> aBD;
	private boolean sommet = false;
	
	ArbreBinaire(T value)
	{
		this.neoud = value;
		this.aBD = null;
		this.aBG= null;
	}
	
	public void inserer(T i)
	{
		if(this.getNeoud() == null)
		{
			this.construireArbre(i);
			this.sommet = true;
			System.out.println("sommet fait sa valeur est : "+i+"\n");
		}
		else
		{
			if(((String)i).charAt(0) == 'D' || ((String)i).charAt(0) == 'F')
			{
				if(this.getaBD() != null) {
					this.getaBD().inserer(i);
				}
				else
				{
					ArbreBinaire <T> a = new ArbreBinaire <T>(i);
					this.setaBG(a);
				}
				
			}
			else
			{
				if(this.getaBD() == null)
				{
					ArbreBinaire <T> a = new ArbreBinaire <T>(i);
					this.setaBD(a);
				}
				else
				{
					this.getaBD().inserer(i);
				}
			}
		}
	}
	
	public boolean estVide()
	{
		if (this.neoud == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void construireArbre(T i)
	{
		this.setNeoud(i);
		this.aBD = null;
		this.aBG= null;
	}
	
	public void afficherArbre()
	{
		if(this.getaBG() != null)
		{
			this.getaBG().afficherArbre();
			//System.out.println("G");
		}
		
		System.out.print(this.getNeoud()+((this.sommet) ? " <== sommet" : ""));
		
		if(this.getaBD() != null)
		{
			this.getaBD().afficherArbre();
			//System.out.println("D");
		}
	}
	
	public boolean recherche(T x)
	{
		if(this.getNeoud() != null)
		{
			if(this.getNeoud().compareTo(x) == 0)
			{
				System.out.println("Recherche effectué avec Succès : elt "+this.getNeoud()+" trouvé ");
				return true;
			}

			if(this.getNeoud().compareTo(x) > 0 && this.getaBG() != null)
			{
				return this.getaBG().recherche(x);
			}
			
			if(this.getNeoud().compareTo(x) < 0 && this.getaBD() != null)
			{
				return this.getaBD().recherche(x);
			}
		}
		System.out.println("Recherche effectué avec Succès : elt "+x+" non trouvé ");
		return false;
	}
	
	public T getNeoud() {
		return neoud;
	}

	public void setNeoud(T neoud) {
		this.neoud = neoud;
	}

	public ArbreBinaire<T> getaBG() {
		return aBG;
	}

	public void setaBG(ArbreBinaire<T> aBG) {
		this.aBG = aBG;
	}

	public ArbreBinaire<T> getaBD() {
		return aBD;
	}

	public void setaBD(ArbreBinaire<T> aBD) {
		this.aBD = aBD;
	}
	

}
