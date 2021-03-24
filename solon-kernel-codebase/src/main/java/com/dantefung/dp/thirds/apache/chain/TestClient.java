package com.dantefung.dp.thirds.apache.chain;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.Filter;
import org.apache.commons.chain.impl.ChainBase;
import org.apache.commons.chain.impl.ContextBase;

@Slf4j
class Filter1 implements Filter {
	@Override
	public boolean postprocess(Context context, Exception exception) {
		log.warn("Filter1方法执行，查看是否是ok！");
		return true;
	}
	/**
	 * 因为它也为Command，所以false表示通过给下一个执行
	 */
	@Override
	public boolean execute(Context context) throws Exception {
//		var o = context.get(KeyConstant.CHAIN_KEY);
//		if (o instanceof UserDto) {
//			return false;
//		}
		log.warn("Filter1不属于UserDto参数，不执行！");
		//return true;
		return false;
	}
}

@Slf4j
class Filter2 implements Filter {
	@Override
	public boolean postprocess(Context context, Exception exception) {
		log.warn("Filter2方法执行，查看是否是ok！");
		return true;
	}
	/**
	 * 因为它也为Command，所以false表示通过给下一个执行
	 */
	@Override
	public boolean execute(Context context) throws Exception {
		//		var o = context.get(KeyConstant.CHAIN_KEY);
		//		if (o instanceof UserDto) {
		//			return false;
		//		}
		log.warn("Filter2不属于UserDto参数，不执行！");
		//return true;
		return false;
	}
}

class Command1 implements Command {
	public boolean execute(Context arg0) throws Exception {
		System.out.println("Command1 is done! the chain will going.");
		System.out.println("key1: " + arg0.get("key1"));
		return Command.CONTINUE_PROCESSING;
	}
}

class Command2 implements Command {
	public boolean execute(Context arg0) throws Exception {
		System.out.println("Command2 is done! the chain will going.");
		System.out.println("key2: " + arg0.get("key2"));
		return Command.CONTINUE_PROCESSING;
	}
}

class Command3 implements Command {
	public boolean execute(Context arg0) throws Exception {
		System.out.println("Command3 is done! the chain not going.");
		System.out.println("key3: " + arg0.get("key3"));
		//代表执行完成, 流程不再继续.
		return Command.PROCESSING_COMPLETE;
	}
}

class CommandChain extends ChainBase {
	//增加命令的顺序也决定了执行命令的顺序
	public CommandChain() {
		addCommand(new Command1());
		addCommand(new Filter1());
		addCommand(new Command2());
		addCommand(new Filter2());
		addCommand(new Command3());
		// 这里的Command1不会执行, 因为Command3中声明流程终止
		addCommand(new Command1());
	}
}

// ============================= 测试
public class TestClient {
	public static void main(String[] args) throws Exception {
		CommandChain process = new CommandChain();
		Context ctx = new ContextBase();
		ctx.put("key1", "aaaa");
		ctx.put("key2", "bbbb");
		ctx.put("key3", "cccc");
		process.execute(ctx);
	}
}

/*
// 输出
	Command1 is done! the chain will going.
	Command2 is done! the chain will going.
	Command3 is done! the chain not going.
*/
