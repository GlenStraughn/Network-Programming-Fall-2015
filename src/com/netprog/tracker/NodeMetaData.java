package com.netprog.tracker;

public class NodeMetaData {
	private double score = 0.0;
	private Integer nodeID;
	private boolean freeRiderFlag = false;
	

	public NodeMetaData(Integer nodeID, double score) {
		this.nodeID = nodeID;
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
	}
	
	public void decreaseScore(double decrease) {
		this.score = score - decrease;
	}

	public void reCalculateFreeRider(){
		if (this.score >= 0) {
			freeRiderFlag = false;
		}else{
			freeRiderFlag = true;
		}
	}
	
	public boolean isFreeRider(){
		return freeRiderFlag;
	}
}
