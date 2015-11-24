package com.netprog;
//=======================================================//
// BitTorrentSimulator
// 	~ Main class for free rider detection algorithm
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
// Written for Computer Network Programming
// Dr. Eric Chan Tin
//-------------------------------------------------------//
// DATE: 11.09.2015
// IDE: Eclipse (Java)
//=======================================================//

import java.io.File;
import java.util.*;

import com.netprog.tracker.Tracker;

public class BitTorrentSimulator
{
	private File settingsFile;
	private File nodeDescFile; // Node description file
	private int duration;
	
	public static void main(String args[])
	{
		Tracker tracker = new Tracker();
		ArrayList<Node> nodes; //= makeNodeList(nodeDescFile, tracker);
		

	}
}