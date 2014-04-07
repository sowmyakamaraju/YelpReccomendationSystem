package edu.umkc.kdm.mid.hbase;


import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;

public class DAO {

	private static Configuration conf = null;
	public DAO(){
		conf = HBaseConfiguration.create();
		conf.clear();
		conf.set("hbase.zookeeper.quorum", "localhost");
		conf.set("hbase.zookeeper.property.clientPort","2181");
		conf.set("hbase.master", "localhost:60001");	
	}

	public String getOneRecord (HTable table, String rowKey) throws IOException{
        Get get = new Get(rowKey.getBytes());
        Result rs = table.get(get);
        String res="";
        for(KeyValue kv : rs.raw()){
           res+=new String(kv.getValue())+",";
        }
        
        return res;
	}
	
}
