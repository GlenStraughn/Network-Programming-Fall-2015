package com.netprog.tracker;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.netprog.Node;
import com.netprog.SimulationFile;

/**
 * Tracker.java: keep tracking status of each node
 */

/**
 * @author Tao
 *
 */
public class Tracker {

	// list of all nodes
	private static List<Node> nodesList = new ArrayList<>();

	// a map between nodes unique name and metadata
	private static Map<String, NodeMetaData> nodesMetaDataMap = new HashMap<>();

	// Map for files and a map for listing nodes (id) that hold said files (UID
	// of file)
	private static Map<SimulationFile, ArrayList<String>> filesMap;

	// settings from the file settings.props. Can be invoked within any classes
	private static Properties settings = new Properties();

	// file path of the settings file
	private String settingsPath = "settings.props";

	// record number of nodes
	public static int numberOfNodes = 3;
	// initial probility for nodes publish a file
	public static double initPubProb = 0.4;
	// initial probility for nodes download a file
	public static double initDwldProb = 0.4;
	// initial score for each nodes
	public static int initScore = 1;
	// the score to be decreased each time
	public static int scoreDecrease = 1;
	// the score to be increased each time
	public static int scoreIncrease = 2;

	// Delimiter used in CSV file
	public static final String COMMA_DELIMITER = ",";
	public static final String NEW_LINE_SEPARATOR = "\n";

	// CSV file header
	private static final String FILE_HEADER = "Node,Score,isFreeRider,#Downloaded,#Published";

	public Tracker() {
		loadSettings();// loading settings file when initialize the Tracker
		numberOfNodes = Integer.parseInt(settings.getProperty("NumberOfNodes"));
		initPubProb = Double.parseDouble(settings.getProperty("InitPubProb"));
		initDwldProb = Double.parseDouble(settings.getProperty("InitDwldProb"));
		initScore = Integer.parseInt(settings.getProperty("InitScore"));
		scoreDecrease = Integer.parseInt(settings.getProperty("ScoreDecrease"));
		scoreIncrease = Integer.parseInt(settings.getProperty("ScoreIncrease"));
	}

	private void loadSettings() {
		try {
			settings.load(getClass().getResourceAsStream(settingsPath));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @return a list of peers
	 */
	protected List<Node> sendPeerList() {
		return nodesList;
	}

	/**
	 * @return the settings
	 */
	public static Properties getSettings() {
		return settings;
	}

	/**
	 * Node client invoke this method to publish a file, then their score will
	 * be increased automatically
	 * 
	 * @param node:
	 *            the node to publish this file
	 * @param smFile
	 */
	public static void publishFile(Node node, SimulationFile smFile) {
		if (!nodesList.contains(node)) {
			addNode(node);
		}

		for (SimulationFile sf : filesMap.keySet()) {
			if (sf.equals(smFile)) {
				sf.polularity++;
				List<String> nodesList = filesMap.get(sf);
				if (null == nodesList) {
					nodesList = new ArrayList<>();
				}
				nodesList.add(node.nodeName); // add node to file resource list
				break;
			}
		}

		// update meta data score and number of files published
		nodesMetaDataMap.get(node.nodeName).increaseScore(scoreIncrease*smFile.size);
		nodesMetaDataMap.get(node.nodeName).addNumPub();

		// print the info about this node to screen
		nodesMetaDataMap.get(node.nodeName).printNodeMetaData();
	}

	/**
	 * Node client invoke this method to query and download a file,then their
	 * score will be decreased automatically
	 * 
	 * @param node
	 * @param fileUID
	 * @return a list of node ids
	 */
	public static SimulationFile queryAndDwldFile(Node node, Integer fileUID) {
		if (!nodesList.contains(node)) {
			addNode(node);
		}
		for (SimulationFile sf : filesMap.keySet()) {
			if (sf.UID == fileUID) {
				// update meta data score and number of files download
				nodesMetaDataMap.get(node.nodeName).decreaseScore(scoreDecrease*sf.size);
				nodesMetaDataMap.get(node.nodeName).addNumDwln();
				// print the info about this node to screen
				nodesMetaDataMap.get(node.nodeName).printNodeMetaData();
				return sf;
			}
		}

		return null;
	}

	/**
	 * add a node to tracker
	 * 
	 * @param node
	 */
	public static void addNode(Node node) {
		nodesList.add(node);
		NodeMetaData nodeMetaData = new NodeMetaData(node.nodeName, initScore);
		nodesMetaDataMap.put(node.nodeName, nodeMetaData);
	}

	public void writeNodesInfo2csv() {
		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter("" + new Date().getTime());
			// Write the CSV file header
			fileWriter.append(FILE_HEADER.toString());

			// Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);

			// Write a new student object list to the CSV file
			for (NodeMetaData nodeMetaData : nodesMetaDataMap.values()) {
				fileWriter.append(nodeMetaData.nodeMetaData2csv());
			}
			System.out.println("CSV file was created successfully !!!");
		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}
	}
	
}
