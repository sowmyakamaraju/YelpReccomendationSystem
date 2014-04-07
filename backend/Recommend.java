package edu.umkc.kdm.mid.reccomend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

public class Recommend {

	public static String[] getBussinessList(String userName, Model model)
			throws TasteException {
		List<String> bussinesslist = new ArrayList<String>();
		try {
			List<RecommendedItem> bussiness_list = model.model().recommend(model.getStrTolng().toLongID(userName), 10);
			for (RecommendedItem business : bussiness_list) {
				bussinesslist.add(model.getStrTolng().toStringID(
						business.getItemID()));
			}
		} catch (TasteException e) {
			throw e;
		}
		return bussinesslist.toArray(new String[bussinesslist.size()]);
	}

	
}
