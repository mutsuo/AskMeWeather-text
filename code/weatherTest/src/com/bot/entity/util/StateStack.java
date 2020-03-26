/**
 * 
 */
package com.bot.entity.util;

import java.util.ArrayList;
import java.util.List;

import com.bot.entity.module.dm.state.State;

/**
 *@desc:状态栈，单例模式
 *@author 邓旸
 *@date:2020年2月13日下午4:59:13
 */
public class StateStack {
	private static StateStack instance = new StateStack();
	private List<State> stateStack = new ArrayList<State>();
	/**
	 * 
	 */
	private StateStack() {
		// TODO Auto-generated constructor stub
	}
	
	public static StateStack getInstance() {
		return instance;
	}
	
	public State popStateStack() {
		if(stateStack.size()>0) {
			State state = stateStack.get(stateStack.size()-1);
			stateStack.remove(stateStack.size()-1);
			return state;
		}
		else return null;
	}
	
	public void pushStateStack(State sate) {
		stateStack.add(sate);
	}
	
	public int size() {
		return stateStack.size();
	}
	
	public boolean isEmpty() {
		if(size()<=1) return true;
		else return false;
	}
	
	public State get(int index) {
		return stateStack.get(index);
	}
}
