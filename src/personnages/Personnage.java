package personnages;

public abstract class Personnage {
	
	protected int vie;
	protected int vision;
	protected int attaque;
	protected int portee;
	protected int deplacement;
	protected int rappel;
		
	public Personnage(){
		
	}
	
	public int getVie() {
		return this.vie;
	}
	public void setVie(int vie) {
		this.vie = vie;
	}
	public int getVision() {
		return this.vision;
	}
	public void setVision(int vision) {
		this.vision = vision;
	}
	public int getAttaque() {
		return this.attaque;
	}
	public void setAttaque(int attaque) {
		this.attaque = attaque;
	}
	public int getPortee() {
		return this.portee;
	}
	public void setportee(int portee) {
		this.portee = portee;
	}
	public int getDeplacement() {
		return this.deplacement;
	}
	public void setDeplacement(int deplacement) {
		this.deplacement = deplacement;
	}
	public int getRappel() {
		return this.rappel;
	}
	public void setRappel(int rappel){
		this.rappel = rappel;
	}
	
	public void deplacer(){
		
	}
	
	public boolean isPickable(){
		return false;
	}
}
