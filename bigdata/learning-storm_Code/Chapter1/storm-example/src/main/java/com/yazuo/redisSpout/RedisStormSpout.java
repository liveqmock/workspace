package com.yazuo.redisSpout;

import java.util.Map;

import redis.clients.jedis.Jedis;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class RedisStormSpout extends BaseRichSpout {
	private static final long serialVersionUID = 1L;
	private SpoutOutputCollector spoutOutputCollector;
	static final Jedis jedis = new Jedis("192.168.232.181", 6379);
	static final String redisKey = "myspout";

	public void open(Map conf, TopologyContext context, SpoutOutputCollector spoutOutputCollector) {
		// Open the spout
		this.spoutOutputCollector = spoutOutputCollector;
	}

	public void nextTuple() {
		System.out.println("message from "+this.getClass().getName());
		try {
			Thread.sleep(100);
			String rpop = jedis.rpop(redisKey);
			System.err.println(rpop);
			if(rpop!=null){
				spoutOutputCollector.emit(new Values(redisKey, rpop));
			}else{
				Thread.sleep(5000);
				System.err.println("no work to do, sleep for a little while ! ");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// emit the tuple with field name "site"
		declarer.declare(new Fields(redisKey, "value"));
	}
}
