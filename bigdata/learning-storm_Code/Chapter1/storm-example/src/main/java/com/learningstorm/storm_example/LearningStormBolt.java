package com.learningstorm.storm_example;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

public class LearningStormBolt extends BaseBasicBolt{

	private static final long serialVersionUID = 1L;

	public void execute(Tuple input, BasicOutputCollector collector) {
		// Get the field "site" from input tuple.
		String test = input.getStringByField("site");
		// print the value of field "site" on console.
		System.err.println("site test print : " + test);	
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
	}
}
