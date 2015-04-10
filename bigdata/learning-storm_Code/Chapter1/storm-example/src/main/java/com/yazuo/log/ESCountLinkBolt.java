package com.yazuo.log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

import com.yazuo.Constant;
import com.yazuo.redisSpout.ElasticSearchHandler;

public class ESCountLinkBolt implements IRichBolt {
	
	ElasticSearchHandler esHandler = null;

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {

		esHandler = new ElasticSearchHandler();
	}

	@Override
	public void execute(Tuple input) {
		String key = input.getString(0);
		Integer count = input.getInteger(1);
		List list = new ArrayList<String>();

		QueryBuilder queryBuilder = QueryBuilders.matchQuery("userName",key);
		SearchHit[] searchHits = esHandler.searchHits(queryBuilder, Constant.index, Constant.type);
		if(searchHits.length>0){
			for (SearchHit hit : searchHits) {
				esHandler.updateDocument(Constant.index, Constant.type,  hit.id(), "count", count.toString());
			}
		}else{
			String json = "{\"userName\":\""+ key +"\",\"count\":\""+ count +"\"}";
			list.add(json);
			esHandler.createIndexResponse(Constant.index, Constant.type, json );
		}
		
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
