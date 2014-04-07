package com.umkc.kdm.mid.rest;

import edu.umkc.kdm.mid.hbase.DAO;
import edu.umkc.kdm.mid.reccomend.Model;
import static edu.umkc.kdm.mid.reccomend.Recommend.getBussinessList;
import java.io.IOException;
import java.util.ArrayList;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.mahout.cf.taste.common.TasteException;

@Path("/generic")
public class GenericResource {

    @GET
    @Path("/recomend")
    @Produces("application/json")
    public ArrayList<String> recomend(@QueryParam("name") String name) 
    {
        String userName = "1ieuYcKS7zeAv_U15AB13A";
		Model model = new Model();
                ArrayList<String> results=new ArrayList<String>();
		try {
			String[] list = getBussinessList(userName, model);
			System.out.println("Recommended Bussiness for " + userName
					+ " \n\n");
			DAO dl=new DAO();
			HTable table=null;
			try {
				table = new HTable("business");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
                        
			for (String result : list) {
				try {
					results.add(dl.getOneRecord(table, result));
				   	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (TasteException e) {
			e.printStackTrace();
		}
        return results;
    }
}
