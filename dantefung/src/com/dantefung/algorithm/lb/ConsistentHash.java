package com.dantefung.algorithm.lb;

import java.util.Collection;
import java.util.HashSet;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHash<T> {

	private final HashFunction hashFunction;//hash算法接口，需要实现
	private final int numberOfReplicas;//虚拟节点因子
	private final SortedMap<Integer, ServerInfo> circle = new TreeMap<Integer, ServerInfo>();//存储服务器的容器
	
	//构造一致性算法实例，正常情况下，实际环境应该提供单例即可
	public ConsistentHash(HashFunction hashFunction, int numberOfReplicas,
			Collection<ServerInfo> nodes) {
		this.hashFunction = hashFunction;
		this.numberOfReplicas = numberOfReplicas;

		for (ServerInfo node : nodes) {
			add(node);
		}
	}
	
	//添加虚拟节点
	public void add(ServerInfo node) {
		circle.put(hashFunction.hash(node.ip), node);
		for (int i = 0; i < numberOfReplicas; i++) {
			circle.put(hashFunction.hash(node.ip + i), node);
		}
	}
	//删除某个物理机器所有的虚拟节点
	public void remove(T node) {
		for (int i = 0; i < numberOfReplicas; i++) {
			circle.remove(hashFunction.hash(node.toString() + i));
		}
	}
	
	//根据需要存储的数据的key获取顺时针方向最近的一个存储服务器
	public ServerInfo get(Object key) {
		if (circle.isEmpty()) {
			return null;
		}
		int hash = hashFunction.hash(key);
		if (!circle.containsKey(hash)) {
			SortedMap<Integer, ServerInfo> tailMap = circle.tailMap(hash);
			hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
		}
		return circle.get(hash);
	}

	static class HashFunction {
		int hash(Object key) {
			// md5加密后，hashcode
			return MD5.md5(key.toString()).hashCode();
		}
	}

	public static void main(String[] args) {

		HashSet<ServerInfo> set = new HashSet<ServerInfo>();
		set.add(new ServerInfo("192.168.1.1",9001));
		set.add(new ServerInfo("192.168.1.2",9002));
		set.add(new ServerInfo("192.168.1.3",9003));
		set.add(new ServerInfo("192.168.1.4",9004));

		ConsistentHash<ServerInfo> consistentHash = new ConsistentHash<ServerInfo>(new HashFunction(), 1000, set);

		System.out.println(consistentHash.get("192.168.1.4").ip);
	}
}