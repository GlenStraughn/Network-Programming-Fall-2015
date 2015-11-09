package com.netprog.tracker;

public class NodeMetaData {
	private double score = 0.0;
	private String nodeName = "";

	public NodeMetaData(String nodeName) {
		this.nodeName = nodeName;
	}

	private NodeMetaData() {
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

}
