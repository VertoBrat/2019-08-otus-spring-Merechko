package ru.photorex.hw12.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LibraryUtil {

	private static final Logger log = LoggerFactory.getLogger(LibraryUtil.class);

	private LibraryUtil(){}

	public static void sleepRandomly(int maxTime, TimeUnit unit) {
		try {
			Random random = new Random();
			unit.sleep(random.ints(0, maxTime + 1).findFirst().getAsInt());
		} catch (InterruptedException ex) {
			log.info(ex.getMessage());
		}
	}
}
