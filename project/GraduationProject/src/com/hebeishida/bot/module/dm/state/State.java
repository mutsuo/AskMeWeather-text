/**
 * 
 */
package com.hebeishida.bot.module.dm.state;

/**
 *@desc:状态类
 *@author 邓旸
 *@date:2020年2月10日下午7:28:57
 */
public class State {
	private StateContent stateContent = new StateContent();
	private int turnId;	//轮次号
	private int dialogId;	//会话编号
	private int stateType;	//状态类型
	private int role;	//角色
	
	//系统状态：0~5
	public final static int STATE_START = 0;	//开始
	public final static int STATE_END = 1;	//正常结束
	public final static int STATE_INTURRUPT = 2;	//异常终止
	//机器人类型：6~9
	public final static int STATE_TASK_BOT = 6;
	public final static int STATE_CHAT_BOT = 7;
	//角色：10~
	public final static int ROLE_USER = 10;
	public final static int ROLE_BOT = 11;
	public final static int ROLE_SYS = 12;

	/**
	 * 
	 */
	public State() {
		this.turnId = 0;
		this.dialogId = 0;
		this.stateType = -1;
		this.role = -1;
	}
	
	public State(State state) {
		this.turnId = state.turnId;
		this.dialogId = state.dialogId;
		this.stateType = state.stateType;
		this.role = state.role;
		this.stateContent = state.getStateContent();
	}

	public StateContent getStateContent() {
		return stateContent;
	}

	public void setStateContent(StateContent stateContent) {
		this.stateContent = stateContent;
	}

	public int getTurnId() {
		return turnId;
	}

	public void setTurnId(int turnId) {
		this.turnId = turnId;
	}

	public int getDialogId() {
		return dialogId;
	}

	public void setDialogId(int dialogId) {
		this.dialogId = dialogId;
	}

	public int getStateType() {
		return stateType;
	}

	public void setStateType(int stateType) {
		this.stateType = stateType;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
	
}
