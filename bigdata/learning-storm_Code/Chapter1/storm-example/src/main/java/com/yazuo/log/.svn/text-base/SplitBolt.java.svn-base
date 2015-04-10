package com.yazuo.log;

import org.apache.commons.lang.StringUtils;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class SplitBolt extends BaseBasicBolt {

	public void cleanup() {
	}

	/**
	 * The bolt will receive the line from the words file and process it to
	 * Normalize this line
	 * 
	 * The normalize will be put the words in lower case and split the line to
	 * get all words in this
	 */
	public void execute(Tuple input, BasicOutputCollector collector) {
		 String sentence = input.getString(0);
		System.err.println("sentence: " + sentence);
		//15;20150409111454;goodInfoPage;125;
		if (StringUtils.isNotEmpty(sentence)) {
			if (sentence.contains(";")) {
				String[] split = sentence.split(";");
				String word = split[2].trim();  
				if (!word.isEmpty()) {
					System.err.println("word: " + word);
					collector.emit(new Values(word));
				}

			}
		}
	}

	/**
	 * The bolt will only emit the field "word"
	 */
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}
}
