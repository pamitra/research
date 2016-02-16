package nn.conversion.qr.model;

public class Eatery {

	private String restaurant;
	private String location;
	private String speciality;
	private String budget;
	
	public String getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public String getBudget() {
		return budget;
	}
	public void setBudget(String budget) {
		this.budget = budget;
	}

	
    public String toString() {
        return String.format("%s - %s - %s - %s", restaurant, location, speciality, budget);
    }
	
	
	
}
