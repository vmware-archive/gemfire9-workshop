package io.pivotal.app.insert;

import io.pivotal.domain.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;


public class BulkInsertPerson {
	private Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	private ClientCache cache = null;
	private final int BATCH_SIZE = 10000;
	private final int SAMPLE_SIZE = 100000;

	Random r = new Random(System.currentTimeMillis());

	int starting_integer = 1;
	int increment_rate = 1;
	int pause_per_insert = 1;
	boolean is_fast_run = true;

	private BulkInsertPerson(boolean is_fast_run, int starting_integer, int increment_rate, int pause_per_insert) {
		ClientCacheFactory ccf = new ClientCacheFactory();
		ccf.set("cache-xml-file", "config/query-client.xml");
		cache = ccf.create();

		this.is_fast_run = is_fast_run;
		this.starting_integer = starting_integer;
		this.increment_rate = increment_rate;
		this.pause_per_insert = pause_per_insert;
	}

	public void run() {
		Region<String, Person> peopleRegion = cache.getRegion("people");

		long start = System.currentTimeMillis();

		int key = starting_integer;
		if (is_fast_run) {
			fastRun(peopleRegion, key);
		} else {
			slowRun(peopleRegion, key);
		}

		long end = System.currentTimeMillis() - start;

		float elapsedTimeSec = end / 1000F;

		logger.log(Level.INFO, String.format("Elapsed time in seconds %f", elapsedTimeSec));
	}

	private void fastRun(Region<String, Person> peopleRegion, int key) {

		Map<String, Person> buffer = new HashMap<String, Person>();

		for (int i = 0; i < SAMPLE_SIZE; i++) {
			Person person = generatePerson(key);
			buffer.put(String.valueOf(key), person);

			if ((i % BATCH_SIZE) == 0) {
				// ready to insert a batch into our region
				peopleRegion.putAll(buffer);
				buffer.clear();

				System.out.println("Inserted " + BATCH_SIZE + " records");
			}
			key = key + increment_rate;
		}

		// there may be existing records to flush so this takes care of it
		if (!buffer.isEmpty()) {
			peopleRegion.putAll(buffer);
			buffer.clear();
		}
	}

	private void slowRun(Region<String, Person> peopleRegion, int key) {

		for (int i = 0; i < SAMPLE_SIZE; i++) {
			Person person = generatePerson(key);
			peopleRegion.put(String.valueOf(key), person);

			if (i % 50 == 0) {
				System.out.println("Inserted " + i + " records so far");
			}
			try {
				Thread.sleep(pause_per_insert * 1000);
			} catch (InterruptedException e) {
				// exit
			}

			key = key + increment_rate;
		}
	}

	private Person generatePerson(int key) {
		int firstNameIx = r.nextInt(firstNames.size());
		int lastNameIx = r.nextInt(lastNames.size());
		return new Person(key, String.format("%s %s", firstNames.get(firstNameIx), lastNames.get(lastNameIx)));
	}

	public static void main(String[] args) {
		boolean is_fast_run = true;
		if (args.length > 1) {
			is_fast_run = args[0].equalsIgnoreCase("Fast");
		}
		int starting_integer = 1;
		if (args.length > 2) {
			starting_integer = Integer.parseInt(args[1]);
		}
		int increment_rate = 1;
		if (args.length > 3) {
			increment_rate = Integer.parseInt(args[2]);
		}
		int pause_per_insert = 1;
		if (args.length > 4) {
			pause_per_insert = Integer.parseInt(args[3]);
		}

		BulkInsertPerson test = new BulkInsertPerson(is_fast_run, starting_integer, increment_rate, pause_per_insert);
		test.run();
	}

	@SuppressWarnings("serial")
	List<String> firstNames = new ArrayList<String>() {
		{
			add("Roanna");
			add("Lacota");
			add("Jaden");
			add("Indira");
			add("Jolie");
			add("Yvette");
			add("Lydia");
			add("Shoshana");
			add("Mary");
			add("Victor");
			add("Ethan");
			add("Brody");
			add("Carly");
			add("Kai");
			add("Freya");
			add("Beverly");
			add("Brenna");
			add("Hammett");
			add("Stephanie");
			add("Zachery");
			add("Libby");
			add("Leandra");
			add("Lance");
			add("Talon");
			add("Giacomo");
			add("Quinn");
			add("Karyn");
			add("Chantale");
			add("Mikayla");
			add("Macaulay");
			add("Tatiana");
			add("Jordan");
			add("Mariam");
			add("Kenyon");
			add("Allistair");
			add("Madison");
			add("Nola");
			add("Stephanie");
			add("Casey");
			add("Amy");
			add("Caleb");
			add("Sarah");
			add("Blossom");
			add("Sigourney");
			add("Brielle");
			add("Kendall");
			add("Mariko");
			add("Cairo");
			add("Melodie");
			add("Ryder");
			add("Joan");
			add("Keefe");
			add("Igor");
			add("Penelope");
			add("Beck");
			add("Camden");
			add("Erica");
			add("Baxter");
			add("Rama");
			add("Clio");
			add("Kane");
			add("Salvador");
			add("Harding");
			add("Holly");
			add("Lynn");
			add("Channing");
			add("Yetta");
			add("Bo");
			add("Christine");
			add("Beatrice");
			add("Odette");
			add("Ocean");
			add("Wang");
			add("Jena");
			add("Germane");
			add("Hyatt");
			add("Gail");
			add("Hermione");
			add("Zenia");
			add("Callum");
			add("Mufutau");
			add("Alfonso");
			add("Oprah");
			add("Quynn");
			add("Germane");
			add("Fiona");
			add("Hall");
			add("Caryn");
			add("Georgia");
			add("Christian");
			add("Miranda");
			add("Renee");
			add("Zachary");
			add("Daniel");
			add("Tucker");
			add("Phyllis");
			add("Perry");
			add("Timon");
			add("Steel");
			add("Amena");
		}
	};

	@SuppressWarnings("serial")
	List<String> lastNames = new ArrayList<String>() {
		{
			add("Hendricks");
			add("Landry");
			add("Hogan");
			add("Foley");
			add("Booth");
			add("Owen");
			add("Harrison");
			add("Dickson");
			add("Reilly");
			add("Little");
			add("Good");
			add("Rodriquez");
			add("Le");
			add("Simpson");
			add("Moss");
			add("Hull");
			add("Patterson");
			add("Neal");
			add("Brennan");
			add("Barrett");
			add("Sanders");
			add("Langley");
			add("Stanley");
			add("Russo");
			add("Spears");
			add("Jensen");
			add("Faulkner");
			add("Mccullough");
			add("Tyler");
			add("Cash");
			add("Hester");
			add("Morales");
			add("Nichols");
			add("Leblanc");
			add("Mckee");
			add("Dillon");
			add("Rosa");
			add("Guerra");
			add("Mason");
			add("Valentine");
			add("Morris");
			add("Leblanc");
			add("Duffy");
			add("Hooper");
			add("Martinez");
			add("Strong");
			add("Bradford");
			add("Emerson");
			add("Peterson");
			add("Pollard");
			add("Conway");
			add("Franco");
			add("Chavez");
			add("Camacho");
			add("Gates");
			add("Gentry");
			add("Hoffman");
			add("Atkinson");
			add("Parsons");
			add("Perez");
			add("Grant");
			add("Mcclure");
			add("Pickett");
			add("Warner");
			add("Yates");
			add("Owens");
			add("Reid");
			add("Farmer");
			add("Navarro");
			add("Whitney");
			add("Baker");
			add("Jenkins");
			add("Williams");
			add("Holt");
			add("Larson");
			add("Sharp");
			add("Moore");
			add("Durham");
			add("Vinson");
			add("Wiggins");
			add("Peterson");
			add("Palmer");
			add("Noble");
			add("Dudley");
			add("Crosby");
			add("Talley");
			add("Kim");
			add("Dominguez");
			add("Romero");
			add("Barr");
			add("Guerrero");
			add("Craig");
			add("Johnson");
			add("James");
			add("Rosario");
			add("Ashley");
			add("Craft");
			add("Melendez");
			add("Mcknight");
			add("Oneal");
		}
	};
}
