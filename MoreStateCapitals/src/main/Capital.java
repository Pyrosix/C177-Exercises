package main;

public class Capital {
	
	
	private String capital;
	private int pop;
	private Double area;
	
	public Capital(String newCapital, int newPop, Double newArea) {
		this.capital = newCapital;
		this.pop = newPop;
		this.area = newArea;
	}
	
	public String getCapital() {
		return capital;
	}
	
	public int getPop() {
		return pop;
	}
	
	public Double getArea() {
		return area;
	}
	
	
}
