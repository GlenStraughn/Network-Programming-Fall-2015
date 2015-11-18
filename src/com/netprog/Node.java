package com.netprog;

import java.util.Random;

public class Node {
	public int NodeID;//Node identifier
	private SimulationFile[] SFile=new SimulationFile[100];//list of files
	public SimulationFile[] PFile=new SimulationFile[100];//list of published files
	private float PublicPro=0;	 // Possible values on [0, 1]
	private float DownloadPro=0; // Possible values on [0, 1]
	
	//Initialize Node
	Node(int NodeID){	
		this.NodeID=NodeID;
	}
	Node(int NodeID, float PublicPro, float DownloadPro){
		this.NodeID=NodeID;
		this.PublicPro=PublicPro;
		this.DownloadPro=DownloadPro;
	}
	public float getPublicPro(){
		return this.PublicPro;
	}
	public float getDownloadPro(){
		return this.DownloadPro;
	}
	
	//Add simulation files in Node, upper bound 100;
	public void AddFile(SimulationFile SF, int index){
		this.SFile[index]=SF;
	}
	
	void onNewFileDetected(int fileID)
	{
		Random rng = new Random();
		
		float dieRoll = rng.nextFloat();
		if(dieRoll < PublicPro)
		{
			
		}
	}
}
