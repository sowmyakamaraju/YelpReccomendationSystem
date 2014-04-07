package edu.umkc.kdm.mid.reccomend;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.csv.CSVParser;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.MemoryIDMigrator;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.Recommender;

public class Model {
	private Recommender recommender = null;
	private MemoryIDMigrator strTolng = new MemoryIDMigrator();
	private DataModel model;
	public MemoryIDMigrator getStrTolng() {
		return strTolng;
	}
	public Recommender model()
	{
		String[] line;

		try {
			Map<Long, List<Preference>> userPrefLists = new HashMap<Long, List<Preference>>();
			CSVParser csv = new CSVParser(new InputStreamReader(
					new FileInputStream(
							"/home/cloudera/Desktop/review.csv"),
					"UTF-8"));
			
			List<Preference> userPrefrence;
			while ((line = csv.getLine()) != null) {
				String user_id = line[0];
				String bussiness_id = line[1];
                if(Integer.parseInt(line[7])>=4)
                {
				long userLong = strTolng.toLongID(user_id);
				strTolng.storeMapping(userLong, user_id);

				long bussinessLong = strTolng.toLongID(bussiness_id);
				strTolng.storeMapping(bussinessLong, bussiness_id);

				if ((userPrefrence = userPrefLists.get(userLong)) == null) {
					userPrefrence = new ArrayList<Preference>();
					userPrefLists.put(userLong, userPrefrence);
				}
				userPrefrence.add(new GenericPreference(userLong, bussinessLong, 1));
                }
                }

			FastByIDMap<PreferenceArray> prefrenceHashMap = new FastByIDMap<PreferenceArray>();

			for (Entry<Long, List<Preference>> entry : userPrefLists.entrySet()) {
				prefrenceHashMap.put(entry.getKey(),new GenericUserPreferenceArray(entry.getValue()));
			}

			model = new GenericDataModel(prefrenceHashMap);
			recommender = new GenericBooleanPrefItemBasedRecommender(model,new LogLikelihoodSimilarity(model));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return recommender;
		
	}

}
