package com.netprog.tracker;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

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

	// a map between metadata and nodes id
	private static Map<Integer, NodeMetaData> nodesMetaDataMap = new HashMap<>();

	// Map for files and a map for listing nodes (id) that hold said files (UID of file)
	private static Map<Integer, Integer> filesMap;
	// private Map<Integer, ArrayList<Node>> nodesWithFile; // Nodes in the list
	// associated with the file UID have that

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
	//the score to be decreased each time
	public static int scoreDecrease = 1;
	//the score to be increased each time
	public static int scoreIncrease = 2;

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
	 * @param settings
	 *            the settings to set
	 */
	public static void setSettings(Properties settings) {
		Tracker.settings = settings;
	}

	public static void publishFile(Node node, SimulationFile smFile) {
		if (!nodesList.contains(node)) {
			addNode(node);
		}
		filesMap.put(node.NodeID, smFile.UID);
		nodesMetaDataMap.get(node.NodeID).increaseScore(scoreIncrease);
	}
	
	public static List<Integer> queryAndDwldFile(Node node, Integer fileUID) {
		List<Integer> resourceNodesList = new ArrayList<>();
		if (!nodesList.contains(node)) {
			addNode(node);
		}
		if (filesMap.containsValue(fileUID)) {
			Iterator<Integer> keysIterator = filesMap.keySet().iterator();
			while(keysIterator.hasNext()){
				Integer key = keysIterator.next();
				if (fileUID == filesMap.get(key)) {
					resourceNodesList.add(key);
				}
			}
		}
		if (resourceNodesList.size() > 0) {
			nodesMetaDataMap.get(node.NodeID).decreaseScore(scoreDecrease);
		}
		
		return resourceNodesList;
	}

	public static void addNode(Node node) {
		nodesList.add(node);
		NodeMetaData nodeMetaData = new NodeMetaData(node.NodeID, initScore);
		nodesMetaDataMap.put(node.NodeID, nodeMetaData);
	}
}
