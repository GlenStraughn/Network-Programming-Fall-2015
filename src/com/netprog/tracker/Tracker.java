package com.netprog.tracker;

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

	private static Properties settings;

	public Tracker() {
		loadSettings();
	}

	private void loadSettings() {
		// TODO Auto-generated method stub

	}

	protected List<Node> sendPeerList() {
		List<Node> peerList = new ArrayList<>();
		return peerList;
	}

}
