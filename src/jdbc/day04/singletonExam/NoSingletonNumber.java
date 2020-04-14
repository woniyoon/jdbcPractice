package jdbc.day04.singletonExam;

public class NoSingletonNumber {

	private int cnt = 0;
	
	public int getNextNumber() {
		return ++cnt;
	}
	
}










/*
 
 	>>>>> 싱글톤 패턴(Singleton pattern) <<<<<<
		
		프로그래밍 세계에 OOP 의 개념이 생기면서 객체 자체에 대한 많은 연구와 패턴(pattern)들이 생겨났다. 
		singleton pattern은 인스턴스가 사용될 때에 매번 새로운 인스턴스를 만들어 내는 것이 아니라, 
		동일 인스턴스 1개만을 사용하도록 하는 것이다. 
		
		소프트웨어 디자인 패턴에서 싱글턴 패턴(Singleton pattern)을 따르는 클래스란? 
		생성자가 여러 차례 호출되더라도 실제로 생성되는 객체는 하나뿐이고, 최초 생성 이후에 호출된 생성자는 최초의 생성자가 생성한 객체를 리턴해준다. 
		이와 같은 디자인 유형을 가지는 클래스를 싱글톤 패턴(Singleton pattern)을 따르는 클래스라고 한다. 
		그러므로 싱글톤 패턴(Singleton pattern)을 따르는 클래스는 인스턴스가 사용될 때에 매번 새로운 인스턴스를 만들어 내는 것이 아니라, 
		최초에 생성되어진 동일 인스턴스 1개만을 사용하도록 하는 것이다.  
		
		프로그램상에서 하나만 사용되어야 하는 객체를 만들때 매우 유용하다.
		예를 들어 동일한 커넥션 객체(Connection conn)를 만들때 사용하면 된다. 
		Singleton 패턴을 이용하면, 하나의 커넥션 객체(Connection conn)를 만들어서 여러 DAO 에서 사용할 수 있게 된다.


 * */
 