package com.mkyong.common.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.SearchHits;

import static org.elasticsearch.common.xcontent.XContentFactory.*;
/**
 * 
 *
 * @version ： 1.0
 * 
 * @author ： 苏若年 <a href="mailto:DennisIT@163.com">发送邮件</a>
 * 
 * @since ： 1.0 创建时间: 2013-4-8 上午11:34:04
 * 
 * @function： TODO
 *
 */
public class ElasticSearchHandler {
    private static String ip = "192.168.232.181";
    
	private Client client;
	private static Client client1 = new TransportClient().addTransportAddress(new InetSocketTransportAddress(ip, 9300));;

	public ElasticSearchHandler() {
		// 使用本机做为节点
		// this("127.0.0.1");
		this(ip);
	}

	public ElasticSearchHandler(String ipAddress) {
		// 集群连接超时设置
		/*
		 * Settings settings =
		 * ImmutableSettings.settingsBuilder().put("client.transport.ping_timeout"
		 * , "10s").build(); client = new TransportClient(settings);
		 */
		// 1, The TransportClient connects remotely to an elasticsearch cluster
		// using the transport module. It does not join the cluster,
		client = new TransportClient().addTransportAddress(new InetSocketTransportAddress(ipAddress, 9300));
		// 2. Node Clientedit : Instantiating a node based client is the
		// simplest way to get a Client that can execute operations against
		// elasticsearch.
		// Node node = nodeBuilder().node();
		// client = node.client();

	}

	/**
	 * 建立索引,索引建立好之后,会在elasticsearch-0.20.6\data\elasticsearch\nodes\0创建所以你看
	 * 
	 * @param indexName
	 *            为索引库名，一个es集群中可以有多个索引库。 名称必须为小写
	 * @param indexType
	 *            Type为索引类型，是用来区分同索引库下不同类型的数据的，一个索引库下可以有多个索引类型。
	 * @param jsondata
	 *            json格式的数据集合
	 * 
	 * @return
	 */
	public void createIndexResponse(String indexname, String type, List<String> jsondata) {
		// 创建索引库 需要注意的是.setRefresh(true)这里一定要设置,否则第一次建立索引查找不到数据
		IndexRequestBuilder requestBuilder = client.prepareIndex(indexname, type).setRefresh(true);
		for (int i = 0; i < jsondata.size(); i++) {
			requestBuilder.setSource(jsondata.get(i)).execute().actionGet();
		}

	}

	/**
	 * 创建索引
	 * 
	 * @param client
	 * @param jsondata
	 * @return
	 */
	public IndexResponse createIndexResponse(String indexname, String type, String jsondata) {
		IndexResponse response = client.prepareIndex(indexname, type).setSource(jsondata).execute().actionGet();
		return response;
	}

	/**
	 * 更新索引
	 */
	public UpdateRequestBuilder updateIndexResponse(String indexname, String type, String id) {
		UpdateRequestBuilder response = client.prepareUpdate(indexname, type, id);
		return response;
	}

	/**
	 * 更新索引
	 */
	public static void updateDocument(String index, String type, String id, String field, String newValue) {
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index(index);
		updateRequest.type(type);
		updateRequest.id(id);
		try {
		updateRequest.doc(jsonBuilder()
		        .startObject()
		            .field(field, newValue)
		        .endObject());
			client1.update(updateRequest).get();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 查询方法
	 */
	public SearchHit[]  searchHits(QueryBuilder queryBuilder, String indexname, String type) {
		SearchResponse searchResponse = client.prepareSearch(indexname).setTypes(type).setQuery(queryBuilder).execute()
				.actionGet();
		SearchHits hits = searchResponse.getHits();
		System.out.println("查询到记录数=" + hits.getTotalHits());
		SearchHit[] searchHists = hits.getHits();
		return searchHists;
	}
	/**
	 * 执行搜索
	 * 
	 * @param queryBuilder
	 * @param indexname
	 * @param type
	 * @return
	 */
	public List<Medicine> searcher(QueryBuilder queryBuilder, String indexname, String type) {
		List<Medicine> list = new ArrayList<Medicine>();
		SearchHit[] searchHists = this.searchHits(queryBuilder, indexname, type);
		if (searchHists.length > 0) {
			for (SearchHit hit : searchHists) {
				SearchHitField field = hit.field("id");
				Integer id = (Integer) hit.getSource().get("id");
				String name = (String) hit.getSource().get("name");
				String function = (String) hit.getSource().get("funciton");
				list.add(new Medicine(id, name, function));
			}
		}
		return list;
	}

	public static void main(String[] args) {
		ElasticSearchHandler esHandler = new ElasticSearchHandler();
		String indexname = "indexdemo";
		String type = "typedemo";

		// String data = "nnnn";
		// List aa = new ArrayList<String>();
		// aa.add("{\"name\":\""+data+"\"}");
		// esHandler.createIndexResponse("indexdemo", "storm", aa );
		// if(true) return;
		//
		// List<String> jsondata = DataFactory.getInitJsonData();
		// esHandler.createIndexResponse(indexname, type, jsondata);

		// 查询条件
		QueryBuilder queryBuilder = QueryBuilders.matchQuery("name", "感冒");
		/*
		 * QueryBuilder queryBuilder = QueryBuilders.boolQuery()
		 * .must(QueryBuilders.termQuery("id", 1));
		 */
		List<Medicine> result = esHandler.searcher(queryBuilder, indexname, type);
		for (int i = 0; i < result.size(); i++) {
			Medicine medicine = result.get(i);
			System.out.println("(" + medicine.getId() + ")药品名称:" + medicine.getName() + "\t\t" + medicine.getFunction());
		}
	}
}

class Medicine {

	private Integer id;
	private String name;
	private String function;

	public Medicine() {
		super();
	}

	public Medicine(Integer id, String name, String function) {
		super();
		this.id = id;
		this.name = name;
		this.function = function;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}
}

class DataFactory {

	public static DataFactory dataFactory = new DataFactory();

	private DataFactory() {

	}

	public DataFactory getInstance() {
		return dataFactory;
	}

	public static List<String> getInitJsonData() {
		List<String> list = new ArrayList<String>();
		String data1 = JsonUtil.obj2JsonData(new Medicine(1, "银花 感冒 颗粒", "功能主治：银花感冒颗粒 ，头痛,清热，解表，利咽。"));
		String data2 = JsonUtil.obj2JsonData(new Medicine(2, "感冒  止咳糖浆", "功能主治：感冒止咳糖浆,解表清热，止咳化痰。"));
		String data3 = JsonUtil.obj2JsonData(new Medicine(3, "感冒灵颗粒", "功能主治：解热镇痛。头痛 ,清热。"));
		String data4 = JsonUtil.obj2JsonData(new Medicine(4, "感冒  灵胶囊", "功能主治：银花感冒颗粒 ，头痛,清热，解表，利咽。"));
		String data5 = JsonUtil.obj2JsonData(new Medicine(5, "仁和 感冒 颗粒", "功能主治：疏风清热，宣肺止咳,解表清热，止咳化痰。"));
		list.add(data1);
		list.add(data2);
		list.add(data3);
		list.add(data4);
		list.add(data5);
		return list;
	}
}

class JsonUtil {

	/**
	 * 实现将实体对象转换成json对象
	 * 
	 * @param medicine
	 *            Medicine对象
	 * @return
	 */
	public static String obj2JsonData(Medicine medicine) {
		String jsonData = null;
		try {
			// 使用XContentBuilder创建json数据
			XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
			jsonBuild.startObject().field("id", medicine.getId()).field("name", medicine.getName())
					.field("funciton", medicine.getFunction()).endObject();
			jsonData = jsonBuild.string();
			System.out.println(jsonData);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonData;
	}

}