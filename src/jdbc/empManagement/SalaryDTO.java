package jdbc.empManagement;

public class SalaryDTO {
	private int salary = 0;
	private double commission_pct = 0.0;
	private int retirementMoney = 0;	
	
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
	public int getRetirementMoney() {
		return retirementMoney;
	}
	public void setRetirementMoney(int retirementMoney) {
		this.retirementMoney = retirementMoney;
	}
}
