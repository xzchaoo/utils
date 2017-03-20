package com.xzchaoo.utils.concurrent.n;

/**
 * Created by Administrator on 2017/3/20.
 */
public class IndexedItem<ITEM> {
	public final int index;
	public final ITEM item;

	public IndexedItem(int index, ITEM item) {
		this.index = index;
		this.item = item;
	}
}
