package com.netprog.tracker;

import javax.sound.midi.Track;

public class NodeMetaData {
	private double score = 0.0;
	private String nodeName;
	private boolean freeRiderFlag = false;
	private int numDwln = 0; //keep tracking the number of files node download
	private int numPub = 0;	//keep tracking the number of files node published
	

	public NodeMetaData(String nodeName, double score) {
		this.nodeName = nodeName;
		this.score = score;
	}

	private NodeMetaData() {
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
	
	public void increaseScore(double increase) {
		this.score = score + increase;
		reCalculateFreeRider();
	}
	
	public void decreaseScore(double decrease) {
		this.score = score - decrease;
		reCalculateFreeRider();
	}

	public void reCalculateFreeRider(){
		if (this.score >= 0) {
			freeRiderFlag = false;
		}else{
			freeRiderFlag = true;
		}
	}
	

	/**
	 * @return the freeRiderFlag
	 */
	public boolean isFreeRiderFlag() {
		return freeRiderFlag;
	}

	/**
	 * @param freeRiderFlag the freeRiderFlag to set
	 */
	public void setFreeRiderFlag(boolean freeRiderFlag) {
		this.freeRiderFlag = freeRiderFlag;
	}

	/**
	 * @return the numDwln
	 */
	public int getNumDwln() {
		return numDwln;
	}

	/**
	 * @param numDwln the numDwln to set
	 */
	public void setNumDwln(int numDwln) {
		this.numDwln = numDwln;
	}

	/**
	 * @return the numPub
	 */
	public int getNumPub() {
		return numPub;
	}

	/**
	 * @param numPub the numPub to set
	 */
	public void setNumPub(int numPub) {
		this.numPub = numPub;
	}
	
	
	public void printNodeMetaData(){
		StringBuilder metaInfoBuilder = new StringBuilder();
		metaInfoBuilder.append("Node: ");
		metaInfoBuilder.append(nodeName);
		metaInfoBuilder.append(", ");
		metaInfoBuilder.append("Score: ");
		metaInfoBuilder.append(score);
		metaInfoBuilder.append(", ");
		metaInfoBuilder.append("isFreeRider: ");
		metaInfoBuilder.append(freeRiderFlag);
		metaInfoBuilder.append(", ");
		metaInfoBuilder.append("#Downloaded: ");
		metaInfoBuilder.append(numDwln);
		metaInfoBuilder.append(", ");
		metaInfoBuilder.append("#Published: ");
		metaInfoBuilder.append(numPub);
		
		System.out.println(metaInfoBuilder.toString());
	}
	

	public void addNumDwln() {
		numDwln ++;
	}
	
	public void addNumPub() {
		numPub ++;
	}
	
	public String nodeMetaData2csv(){
		StringBuilder metaInfoBuilder = new StringBuilder();
		metaInfoBuilder.append(nodeName);
		metaInfoBuilder.append(Tracker.COMMA_DELIMITER);
		metaInfoBuilder.append(score);
		metaInfoBuilder.append(Tracker.COMMA_DELIMITER);
		metaInfoBuilder.append(freeRiderFlag);
		metaInfoBuilder.append(Tracker.COMMA_DELIMITER);
		metaInfoBuilder.append(numDwln);
		metaInfoBuilder.append(Tracker.COMMA_DELIMITER);
		metaInfoBuilder.append(numPub);
		metaInfoBuilder.append(Tracker.NEW_LINE_SEPARATOR);
		return metaInfoBuilder.toString();
	}

	/**
	 * @return the nodeName
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * @param nodeName the nodeName to set
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	
}
