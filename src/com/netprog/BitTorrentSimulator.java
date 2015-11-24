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

	public static void main(String args[]) {
		Tracker tracker = new Tracker();
		// ArrayList<Node> nodes; //= makeNodeList(nodeDescFile, tracker);
		ArrayList<Node> nodes = makeNodeList(tracker);
		
	}

	private static ArrayList<Node> makeNodeList(Tracker tracker) {
		ArrayList<Node> nodesList = new ArrayList<>();
		if (null == tracker) {
			return nodesList;
		}

		final String NODE_ID = "NodeID";
		final String PUBLISH_PROB = "PublicPro";
		final String DOWNLOAD_PROB = "DownloadPro";
		BufferedReader br = null;
		Node newNode = null;
		try {
			br = new BufferedReader(new InputStreamReader((BitTorrentSimulator.class.getResourceAsStream(NODE_CONFIG_FILE))));
			String tempString = "";
			while ((tempString = br.readLine()) != null) {
				if (NEW_NODE_BEGIN.equals(tempString.trim())) {
					tempString = br.readLine();
					if (null != tempString && tempString.trim().startsWith(NODE_ID)) {
						String NodeID = tempString.split(PROPERTY_DELIMITER)[1];
						
						tempString = br.readLine();
						if (null != tempString && tempString.trim().startsWith(PUBLISH_PROB)) {
							Float PublicPro = Float.parseFloat(tempString.split(PROPERTY_DELIMITER)[1]);
							
							tempString = br.readLine();
							if (null != tempString && tempString.trim().startsWith(DOWNLOAD_PROB)) {
								Float DownloadPro = Float.parseFloat(tempString.split(PROPERTY_DELIMITER)[1]);
								newNode = new Node(NodeID, PublicPro, DownloadPro, tracker);
								nodesList.add(newNode);
							}
						}
						
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