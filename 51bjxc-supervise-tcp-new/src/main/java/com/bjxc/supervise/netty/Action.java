package com.bjxc.supervise.netty;

import com.bjxc.supervise.Action.ActionContext;

public interface Action {
	void action(ActionContext context);
}
