package beautify.table.pojo;

import java.io.Serializable;

//此类对应数据库中的一行数据
public class Table implements Serializable {
	
	private Integer graphID;
	
	private String subj;
	
	private String prop;
	
	private String obj;

	public Integer getGraphID() {
		return graphID;
	}

	public void setGraphID(Integer graphID) {
		this.graphID = graphID;
	}

	public String getSubj() {
		return subj;
	}

	public void setSubj(String subj) {
		this.subj = subj;
	}

	public String getProp() {
		return prop;
	}

	public void setProp(String prop) {
		this.prop = prop;
	}

	public String getObj() {
		return obj;
	}

	public void setObj(String obj) {
		this.obj = obj;
	}
	
	

}
