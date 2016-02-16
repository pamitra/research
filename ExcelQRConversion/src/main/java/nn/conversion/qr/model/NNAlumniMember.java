package nn.conversion.qr.model;

public class NNAlumniMember {
	private String name;
	private String batch;
	private String serialNo;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
    public String toString() {
        return String.format("%s - %s - %s", name, batch, serialNo);
    }
	
}
