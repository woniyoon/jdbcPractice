package jdbc.empManagement;

public class SalaryDTO {
	private int salary;
	private double commission_pct;
	
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public double getCommission_pct() {
		return commission_pct;
	}
	public void setCommission_pct(double commission_pct) {
		this.commission_pct = commission_pct;
	}
}
