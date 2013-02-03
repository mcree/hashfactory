package org.hashfactory.client;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.beust.jcommander.JCommander;

public class CmdLine {

	static Logger logger = LoggerFactory.getLogger(CmdLine.class);

	public static String usage(ApplicationContext ctx) {
		ProgramModuleRegistry registry = ctx
				.getBean(ProgramModuleRegistry.class);

		String res = "";
		res += "Usage: cmd <mode> <args>\n";
		res += "Where mode is:\n";
		for (ProgramModule pm : registry.getModules()) {
			res += "\t" + pm.getCmdLineOpts().getSelector() + "\n";
		}

		return res;
	}

	public static void printUsage(ApplicationContext ctx) {
		logger.info(usage(ctx));
		System.err.println(usage(ctx));
	}

	public static void parseAndRun(String[] args, ApplicationContext ctx) {
		if (args.length == 0) {
			printUsage(ctx);
			return;
		}

		ProgramModuleRegistry registry = ctx
				.getBean(ProgramModuleRegistry.class);

		for (ProgramModule pm : registry.getModules()) {
			CmdLineOpts opts = pm.getCmdLineOpts();
			if (opts.getSelector().equalsIgnoreCase(args[0])) {
				new JCommander(opts, Arrays.copyOfRange(args, 1, args.length));
				logger.info("running " + pm);
				pm.run(opts);
				return;
			}
		}

		printUsage(ctx);
		return;
	}

	public ProgramModule getProgramModule() {
		return programModule;
	}

	public CmdLineOpts getCmdLineOpts() {
		return cmdLineOpts;
	}

	private ProgramModule programModule;
	private CmdLineOpts cmdLineOpts;

	/**
	 * @param programModule
	 * @param cmdLineOpts
	 */
	private CmdLine(ProgramModule programModule, CmdLineOpts cmdLineOpts) {
		super();
		this.programModule = programModule;
		this.cmdLineOpts = cmdLineOpts;
	}

}