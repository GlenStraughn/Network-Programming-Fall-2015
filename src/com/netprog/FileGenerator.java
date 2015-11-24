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
	
	public FileGenerator(ArrayList<Node> nodeList, Tracker tracker, int timeBetweenNewFilesinMilliSec)
	{
		creationDelay = timeBetweenNewFilesinMilliSec;
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
		//tracker.
		
		for (int i = 0; i < nodes.size(); i++)
		{
			nodes.get(i).onNewFileDetected(nextID);
		}
	}

	@Override
	public void run() {
		
		long time = Calendar.getInstance().get(Calendar.MILLISECOND);
		
		Random rng = new Random();
		
		while(true)
		{
			while(time - Calendar.getInstance().get(Calendar.MILLISECOND) < creationDelay)
			{
				// This space intentionally left blank
			}
			
			float size = rng.nextFloat()*rng.nextInt(10000);
			int popularity = 1;
			SimulationFile file = new SimulationFile(nextID, size, popularity);
			
			nextID++;
			
			
		}
	}
}
