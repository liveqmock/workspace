package com.yazuo.redisSpout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

public class ESStormBolt implements IRichBolt {
	
	ElasticSearchHandler esHandler = null;

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {

		esHandler = new ElasticSearchHandler();
	}

	@Override
	public void execute(Tuple input) {
		System.out.println("message from "+this.getClass().getName());
		String data = input.getString(0);
//		List aa = new ArrayList<String>();
//		aa.add("{\"name\":\""+data+"\"}");
		esHandler.createIndexResponse("indexdemo", "storm", data );
	}

	@Override
	public void cleanup() {
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {

	}

	@Override
	public Map<String, Object> getComponentConfiguration() {

		return null;
	}

}
