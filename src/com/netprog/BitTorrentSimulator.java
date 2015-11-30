package com.netprog;
//=======================================================//

import java.io.BufferedInputStream;

// BitTorrentSimulator
// 	~ Main class for free rider detection algorithm
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
// Written for Computer Network Programming
// Dr. Eric Chan Tin
//-------------------------------------------------------//
// DATE: 11.09.2015
// IDE: Eclipse (Java)
//=======================================================//

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.netprog.tracker.Tracker;

public class BitTorrentSimulator {
	private final static String NEW_NODE_BEGIN = "<node>";
	private final static String NEW_NODE_END = "</node>";
	private final static String PROPERTY_DELIMITER = "=";
	private final static String NODE_CONFIG_FILE = "nodes.config";
	private File settingsFile;
//	private File nodeDescFile; // Node description file
	private int duration;

	public BitTorrentSimulator() {
		int duration;
		Tracker tracker = new Tracker();
		ArrayList<Node> nodes = makeNodeList(tracker);
		
		GeneratorThread genThread = new GeneratorThread(nodes, tracker, 1000);
		genThread.start();
		
		int cycles = 0;
		duration = 10000000;
		while(cycles < duration)
		{
			for(int i = 0; i < nodes.size(); i++)
			{
				nodes.get(i).update();
			}
			cycles++;
			
		}
		
		tracker.writeNodesInfo2csv();
		
		System.exit(0); // I don't really know how/when extra threads terminate, so better safe than sorry.
	}
	
//-----------------------------------------------------------------------------------------------//

	public static void main(String args[])
	{
		BitTorrentSimulator bitSim = new BitTorrentSimulator();
	}
	
	protected class GeneratorThread extends Thread
	{
		FileGenerator fileGen;
		
		public GeneratorThread(ArrayList<Node> nodeList, Tracker tracker, int waitTime)
		{
			fileGen = new FileGenerator(nodeList, tracker, waitTime);
		}
		
		public void run()
		{
			while(true)
			{
				fileGen.run();
			}
		}		
	}

	private static ArrayList<Node> makeNodeList(Tracker tracker) {
		ArrayList<Node> nodesList = new ArrayList<>();
		if (null == tracker) {
			return nodesList;
		}

		final String NODE_ID = "NodeID";
		final String PUBLISH_PROB = "PublishPro";
		final String DOWNLOAD_PROB = "DownloadPro";
		BufferedReader br = null;
		Node newNode = null;
		try {
			br = new BufferedReader(new InputStreamReader((BitTorrentSimulator.class.getResourceAsStream(NODE_CONFIG_FILE))));
			String tempString = "";
			while ((tempString = br.readLine()) != null) {
				if (NEW_NODE_BEGIN.equals(tempString.trim())) {
					
					boolean eof = false; // End of file.
					
					String NodeID = "ERROR: Name uninitialized";
					Float publishPro = 0f;
					Float downloadPro = 0f;
					
					while(!NEW_NODE_END.equals(tempString.trim()))
					{
						tempString = br.readLine();
						if(null == tempString)
						{
							eof = true;
							break;
						}
						
						if (tempString.trim().startsWith(NODE_ID))
						{
							NodeID = tempString.split(PROPERTY_DELIMITER)[1];
						}
							
						if (tempString.trim().startsWith(PUBLISH_PROB))
						{
							publishPro = Float.parseFloat(tempString.split(PROPERTY_DELIMITER)[1]);
						}
								
						if (tempString.trim().startsWith(DOWNLOAD_PROB))
						{
							downloadPro = Float.parseFloat(tempString.split(PROPERTY_DELIMITER)[1]);	
						}	
					}
					
					if(!eof)
					{
						newNode = new Node(NodeID, publishPro, downloadPro, tracker);
						nodesList.add(newNode);
					}
					
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return nodesList;
	}
}