package com.vaca.chatmygirl.utils;


import com.vaca.chatmygirl.bean.SortModel;

import java.util.Comparator;

public class PinyinComparator implements Comparator<SortModel> {

	public int compare(SortModel o1, SortModel o2) {//@--# 升序
		if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
