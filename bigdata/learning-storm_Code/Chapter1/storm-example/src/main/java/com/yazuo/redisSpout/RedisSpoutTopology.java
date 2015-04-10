package com.yazuo.redisSpout;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;

public class RedisSpoutTopology {
	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
		TopologyBuilder builder = new TopologyBuilder();
		
		// set the spout class
		builder.setSpout("spout", new RedisStormSpout(), 1);
		// set the bolt class
		builder.setBolt("RedisStormBolt", new RedisStormBolt(), 2).shuffleGrouping("spout");
		// set elasticsearch bolt
		builder.setBolt("ESStormBolt", new ESStormBolt(), 1).shuffleGrouping("RedisStormBolt");

		

//		builder.setBolt("es-bolt", new EsBolt("storm/docs", hashConf),  1).shuffleGrouping("RedisStormBolt");
		Config conf = new Config();
		conf.setDebug(false);
		conf.setNumWorkers(3);

//		 localSubmit(builder, conf);
		remoteSubmit(builder, conf);

	}

	private static void localSubmit(TopologyBuilder builder, Config conf) {
		// create an instance of LocalCluster class for
		// executing topology in local mode.
		LocalCluster cluster = new LocalCluster();

		// LearningStormTopolgy is the name of submitted topology.
		cluster.submitTopology("RedisElasticSearchStormToplogy", conf, builder.createTopology());
		try {
			Thread.sleep(113000);
		} catch (Exception exception) {
			System.out.println("Thread interrupted exception : " + exception);
		}
		// kill the LearningStormTopology
		cluster.killTopology("RedisElasticSearchStormToplogy");
		// shutdown the storm test cluster
		cluster.shutdown();
	}

	private static void remoteSubmit(TopologyBuilder builder, Config conf) {
		// create an instance of LocalCluster class for
		// executing topology in local mode.
		StormSubmitter remoteClustor = new StormSubmitter();
		try {
			remoteClustor.submitTopology("RedisElasticSearchStormToplogy", conf, builder.createTopology());
		} catch (AlreadyAliveException e) {
			e.printStackTrace();
		} catch (InvalidTopologyException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(3000);
		} catch (Exception exception) {
			System.out.println("Thread interrupted exception : " + exception);
		}
	}
}
