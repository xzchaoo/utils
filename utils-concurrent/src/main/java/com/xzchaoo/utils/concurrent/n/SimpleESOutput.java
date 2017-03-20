package com.xzchaoo.utils.concurrent.n;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Administrator on 2017/3/20.
 */
class ESOutputItem<OUT> {
	public final int index;
	public final OUT out;

	public ESOutputItem(int index, OUT out) {
		this.index = index;
		this.out = out;
	}
}

public class SimpleESOutput<OUT> implements ESOutput<OUT> {
	private static final Comparator<ESOutputItem> COMPARATOR = new Comparator<ESOutputItem>() {
		public int compare(ESOutputItem o1, ESOutputItem o2) {
			return (o1.index < o2.index) ? -1 : ((o1.index == o2.index) ? 0 : 1);
		}
	};
	private ConcurrentLinkedQueue<ESOutputItem> q = new ConcurrentLinkedQueue<ESOutputItem>();
	private final boolean needOrder;

	public SimpleESOutput(boolean needOrder) {
		this.needOrder = needOrder;
	}

	public void add(int index, OUT out) {
		q.add(new ESOutputItem<OUT>(index, out));
	}

	public List<OUT> getList() {
		ESOutputItem[] array = q.toArray(new ESOutputItem[0]);
		if (needOrder) {
			Arrays.sort(array, COMPARATOR);
		}
		List<OUT> list = new ArrayList<OUT>(array.length);
		for (ESOutputItem<OUT> esoi : array) {
			list.add(esoi.out);
		}
		return list;
	}
}
