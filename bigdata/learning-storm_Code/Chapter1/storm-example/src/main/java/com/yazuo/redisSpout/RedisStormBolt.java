package com.yazuo.redisSpout;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class RedisStormBolt extends BaseBasicBolt{

	private static final long serialVersionUID = 1L;

	public void execute(Tuple input, BasicOutputCollector collector) {
		// Get the field "site" from input tuple.
		String test = input.getString(1);
		// print the value of field "site" on console.
		System.err.println("song testing redis pop: " + test);	
		System.out.println("message from "+this.getClass().getName());
		collector.emit(new Values(test));
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("value"));
	}
}
