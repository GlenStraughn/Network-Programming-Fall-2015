package com.netprog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import com.netprog.tracker.Tracker;

public class FileGenerator implements Runnable {
	
	private ArrayList<Node> nodes;
	private com.netprog.tracker.Tracker tracker;
	
	private int nextID;
	private int creationDelay;
	private int elapsedTime;
	
	public FileGenerator(ArrayList<Node> nodeList, Tracker tracker, int timeBetweenNewFilesinMilliSec)
	{
		creationDelay = timeBetweenNewFilesinMilliSec;
		elapsedTime = 0;
		nextID = 1;
		
		nodes = nodeList;
		this.tracker = tracker;
	}
	
	public void generateFile()
	{
		Random rng = new Random();
		nextID++;
		
		float fileSize = rng.nextFloat()*rng.nextInt(10000);
		
		SimulationFile simFile = new SimulationFile(nextID, fileSize, 1); // 1 for popularity since I don't remember what it does
		
		boolean repeat = true;
		do
		{
			int r = rng.nextInt(nodes.size());
			Node n = nodes.get(r);
			
			if(rng.nextFloat() <= n.getPublicProb())
			{
				n.AddFile(simFile);
				tracker.publishFile(n, simFile);
				repeat = false;
			}
		}while(repeat);
		
		for (int i = 0; i < nodes.size(); i++)
		{
			nodes.get(i).onNewFileDetected(nextID);
		}
	}

	@Override
	public void run() {
		
		Random rng = new Random();
		elapsedTime++;
		
		if(elapsedTime >= creationDelay)
		{
			generateFile();
			
			nextID++;
			
			elapsedTime = elapsedTime % creationDelay;
		}
	}
}
