package com.netprog;

public class SimulationFile {

	public int UID;//file identifier
	public float size;//unit Mb
	public int polularity;//range from 1 to 10
	public int LoadAmount;//Amount Loaded
	SimulationFile(int UID, float size, int popularity){
		this.UID=UID;
		this.size=size;
		this.polularity=popularity;
		this.LoadAmount=0;
		
	}
}
