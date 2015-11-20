package com.netprog;

public class SimulationFile {

	public int UID;//file identifier
	public float size;//unit Mb
	public int polularity;//range from 1 to 10
	public float LoadAmount;//Amount Loaded
	
	public SimulationFile(int UID, float size, int popularity){
		this.UID=UID;
		this.size=size;
		this.polularity=popularity;
		this.LoadAmount=0;
		
	}
}
