package com.core;

import java.util.HashMap;
import java.util.Map;

public class ImmutabilityDemo {
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<>();
		map.put("key0", "value0");
		Immutability immutableObj = new Immutability("Davinder", map);
		System.out.println(immutableObj);
		map.put("key1", "value 1");
		System.out.println(immutableObj);
		
	}
}

final class Immutability {
	
	private final String name;
	private final Map<String, String> map;
	
	public Immutability (String name,  Map<String, String> map) {
		this.name = name;
		this.map = getCopy(map);
		//this.map = map; this will break immutability
	}

	private Map<String, String> getCopy(Map<String, String> map2) {
		Map<String, String> map = new HashMap<>();
		//copy data from map2 to map, return map 
		map2.entrySet().forEach(e -> map.put(e.getKey(), e.getValue()));
		return map;
	}

	public String getName() {
		return name;
	}
	
	public Map<String, String> getMap() {
		//return map; this will break immutability
		return getCopy(map);
	}

	@Override
	public String toString() {
		return "Immutability [name=" + name + ", map=" + map + "]";
	}

	
}

