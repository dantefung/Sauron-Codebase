package com.dantefung.dp.iterator;
//考虑到容器的可替换性，因此要有这样的一个类来统一。
public interface Collection {
	void add(Object o);
	int size();
	Iterator iterator();
}
