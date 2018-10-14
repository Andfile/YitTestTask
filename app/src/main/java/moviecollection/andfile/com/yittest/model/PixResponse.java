package moviecollection.andfile.com.yittest.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PixResponse{

	@SerializedName("hits")
	private List<HitsItem> hits;

	@SerializedName("total")
	private int total;

	@SerializedName("totalHits")
	private int totalHits;

	public void setHits(List<HitsItem> hits){
		this.hits = hits;
	}

	public List<HitsItem> getHits(){
		return hits;
	}

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setTotalHits(int totalHits){
		this.totalHits = totalHits;
	}

	public int getTotalHits(){
		return totalHits;
	}

	@Override
 	public String toString(){
		return 
			"PixResponse{" + 
			"hits = '" + hits + '\'' + 
			",total = '" + total + '\'' + 
			",totalHits = '" + totalHits + '\'' + 
			"}";
		}
}