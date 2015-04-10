package com.yazuo.log;
import com.yazuo.redisSpout.RedisStormSpout;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class LogTopology {
	public static void main(String[] args) throws AlreadyAliveException,
	InvalidTopologyException {

		// Topology definition
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("word-reader", new RedisStormSpout());
//		builder.setSpout("word-reader", new WordReader());
		builder.setBolt("word-normalizer", new WordNormalizer()).shuffleGrouping("word-reader");
		builder.setBolt("word-counter", new LogCounter(), 2).fieldsGrouping("word-normalizer", new Fields("word"));
//		builder.setBolt("es-update", new ESLogStormBolt()).fieldsGrouping("word-counter", new Fields("word"));
		builder.setBolt("es-update", new ESLogStormBolt(), 1).shuffleGrouping("word-counter");

		// Configuration
		Config conf = new Config();
//		conf.put("wordsFile", args[0]);
//		conf.put("wordsFile", "/opt/apache-storm-0.9.2-incubating/examples/words.txt");
		conf.setDebug(true);
		conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
//		
		localmode(builder, conf);
		
//		remoteMode(builder, conf);
	}

	private static void localmode(TopologyBuilder builder, Config conf) {
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("Getting-Started-Toplogie", conf, builder.createTopology());
		try {
			Thread.sleep(333003330);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cluster.killTopology("Getting-Started-Toplogie");
		cluster.shutdown();
	}

	private static void remoteMode(TopologyBuilder builder, Config conf) {
		conf.put("wordsFile", "/opt/apache-storm-0.9.2-incubating/examples/words.txt");
		StormSubmitter remoteClustor = new StormSubmitter();
		try {
			remoteClustor.submitTopology("words-counter-topology", conf, builder.createTopology());
		} catch (AlreadyAliveException e) {
			e.printStackTrace();
		} catch (InvalidTopologyException e) {
			e.printStackTrace();
		}
	}
}
