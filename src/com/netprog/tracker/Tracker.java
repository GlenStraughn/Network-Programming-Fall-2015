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

/**
 * 
 */

/**
 * @author Tao
 *
 */
public class Tracker {

	private static List<Node> nodesList = new ArrayList<>();

	private static Map<String, NodeMetaData> nodesMetaDataMap = new HashMap<>();

	private static Properties settings = new Properties();

	public Tracker() {
		loadSettings();
	}

	private void loadSettings() {
		// TODO Auto-generated method stub
		try {
			FileInputStream fis = new FileInputStream("settings.props");
			settings.load(fis);
			System.out.println(settings.getProperty("test"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected List<Node> sendPeerList() {
		List<Node> peerList = new ArrayList<>();
		return peerList;
	}

	public static Properties getSettings() {
		return settings;
	}

	public static void setSettings(Properties settings) {
		Tracker.settings = settings;
	}

}
