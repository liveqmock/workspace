package com.yazuo.words;
import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class TopologyMain {
	public static void main(String[] args) throws AlreadyAliveException,
	InvalidTopologyException {

		// Topology definition
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("word-reader", new WordReader());
		builder.setBolt("word-normalizer", new WordNormalizer()).shuffleGrouping("word-reader");
		builder.setBolt("word-counter", new WordCounter(), 2).fieldsGrouping("word-normalizer", new Fields("word"));

		// Configuration
		Config conf = new Config();
//		conf.put("wordsFile", args[0]);
		conf.put("wordsFile", "./target/classes/com/yazuo/analysisLog/words.txt");
		conf.setDebug(true);
		conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
//		
//		LocalCluster cluster = new LocalCluster();
//		cluster.submitTopology("Getting-Started-Toplogie", conf, builder.createTopology());
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		cluster.killTopology("Getting-Started-Toplogie");
//		cluster.shutdown();
		
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
