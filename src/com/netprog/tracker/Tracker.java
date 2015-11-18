package com.netprog.tracker;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	//list of all nodes
	private List<Node> nodesList = new ArrayList<>();

	//a map between metadata and nodes
	private Map<String, NodeMetaData> nodesMetaDataMap = new HashMap<>();
	
	// Map for files and a map for listing nodes that hold said files
	private Map<Integer, SimulationFile> fileMap;
	private Map<Integer, ArrayList<Node>> nodesWithFile; // Nodes in the list associated with the file UID have that
	

	//settings from the file settings.props. Can be invoked within any classes
	private static Properties settings = new Properties();
	
	//file path of the settings file
	private String settingsPath = "settings.props";

	public Tracker() {
		loadSettings("DEFAULT");//loading settings file when initialize the Tracker
	}

	private void loadSettings(String settings) {
		try {
			FileInputStream fis = new FileInputStream(settingsPath);
			settings.load(fis);
			
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
		List<Node> peerList = new ArrayList<>();
		
		return peerList;
	}

	/**
	 * @return the settings
	 */
	public static Properties getSettings() {
		return settings;
	}

	/**
	 * @param settings the settings to set
	 */
	public static void setSettings(Properties settings) {
		Tracker.settings = settings;
	}

	void addFile(SimulationFile file)
	{
		//fileMap.
	}
}
