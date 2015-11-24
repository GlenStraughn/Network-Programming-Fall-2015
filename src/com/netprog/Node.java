package com.netprog;

import java.util.Random;
import java.util.ArrayList;

import com.netprog.tracker.*;


public class Node implements Updatable {
	public String nodeName;//Node identifier
	private ArrayList<SimulationFile> sFile = new ArrayList<>();//list of files
	private ArrayList<DownloadingFile> downloadingFiles = new ArrayList<>();
	public ArrayList<SimulationFile> pFile = new ArrayList<>();//list of published files
	private float publishProb=0;	// Prob. of publishing.  Possible values on [0, 1]
	private float downloadProb=0;	// Prob. of Downloading.  Possible values on [0, 1]
	
	Tracker tracker;
	
	//Initialize Node
	Node(String NodeID, Tracker tracker){	
		this.nodeName = NodeID;
		this.tracker = tracker;
	}
	Node(String NodeID, float PublicPro, float DownloadPro, Tracker tracker){
		this.nodeName = NodeID;
		this.publishProb=PublicPro;
		this.downloadProb=DownloadPro;
		
		this.tracker = tracker;
	}
	public float getPublicProb(){
		return this.publishProb;
	}
	public float getDownloadProb(){
		return this.downloadProb;
	}
	
	//Add simulation files in Node, upper bound 100;
	public void AddFile(SimulationFile SF){
		sFile.add(SF);
	}
	
	void onNewFileDetected(int fileID)
	{
		Random rng = new Random();
		
		float dieRoll = rng.nextFloat();
		if(dieRoll < downloadProb)
		{
			SimulationFile temp = tracker.queryAndDwldFile(this, fileID);
			downloadingFiles.add(new DownloadingFile(new SimulationFile(temp.UID, temp.size, temp.polularity)));
		}
	}
	
	
	public void update()
	{
		for(int i = 0; i < downloadingFiles.size(); i++)
		{
			DownloadingFile dFile = downloadingFiles.get(i);
			if(dFile.elapsedTime >= dFile.delay)
			{
				SimulationFile sf = downloadingFiles.get(i).sFile;
				
				// Simulate Download
				if(sf.LoadAmount < sf.size)
				{
					sf.LoadAmount += sf.polularity;
				}
				else // Finished downloading
				{
					downloadingFiles.remove(i);
					sFile.add(sf);
					
					Random rng = new Random();
					
					if(rng.nextFloat() <= publishProb) // Check if file is to be published
					{
						tracker.publishFile(this, sf);
					}
				}
			}
			else
			{
				dFile.elapsedTime++;
			}
		}
	}
	
	private class DownloadingFile // Used to track download delay
	{
		public int delay;
		public int elapsedTime;
		public SimulationFile sFile;
		
		public DownloadingFile(SimulationFile file)
		{
			sFile = file;
			Random rng = new Random((int)(sFile.size*100)); // Wait a maximum period of 100*(time_to_download)
			elapsedTime = 0;
		}
	}
}
