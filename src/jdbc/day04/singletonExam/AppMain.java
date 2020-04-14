package jdbc.day04.singletonExam;

public class AppMain {

	public void a_method() {
		NoSingletonNumber aObj = new NoSingletonNumber();
		
		System.out.println("a_method에서 cnt값 호출 : "+ aObj.getNextNumber());
	}

	public void b_method() {
		NoSingletonNumber bObj = new NoSingletonNumber();
		
		System.out.println("b_method에서 cnt값 호출 : "+ bObj.getNextNumber());
	}

	public void c_method() {
		NoSingletonNumber cObj = new NoSingletonNumber();
		
		System.out.println("c_method에서 cnt값 호출 : "+ cObj.getNextNumber());
	}
	
	public void d_method() {
		SingletonNumber dObj = SingletonNumber.getInstance();
		
		System.out.println("c_method에서 cnt값 호출 : "+ dObj.getNextNumber());
	}
	public void e_method() {
		SingletonNumber eObj = SingletonNumber.getInstance();
		
		System.out.println("c_method에서 cnt값 호출 : "+ eObj.getNextNumber());
	}
	public void f_method() {
		SingletonNumber fObj = SingletonNumber.getInstance();
		
		System.out.println("c_method에서 cnt값 호출 : "+ fObj.getNextNumber());
	}
	
	
	
	
	public static void main(String[] args) {
		
		AppMain ma = new AppMain();

		ma.a_method();
		ma.b_method();
		ma.c_method();
	
		ma.d_method();
		ma.e_method();
		ma.f_method();
		
		
	}

}
