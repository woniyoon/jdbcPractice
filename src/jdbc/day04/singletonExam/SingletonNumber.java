package jdbc.day04.singletonExam;

public class SingletonNumber {
	
	//---> 첫 번째로 호출 (작동) : static 변수
	private static SingletonNumber singleton = null;

	//---> 네 번째로 호출
	private int cnt = 0;
	
	//---> 두 번째로 호출 (작동) : static 초기화 블럭 (인스턴스 생성과 관계없이 실행되어지는 곳, 인스턴스변수는 절대 사용할 수 X)
	//	static 초기화블럭 : 해당 클래스가 객체로 생성되기 전, 딱 한 번만 호출
	//					 	=>  새로운 객체를 매번 생성하더라도 호출 X
	static {
		
		System.out.println(">>> SingletonNumber의 static 초기화 블럭 호출! ");

		singleton = new SingletonNumber();
				
//		cnt = 1; 		: 오류남 (Cannot make a static reference to the non-static field cnt)
	}

	// ---> 다섯 번째로 호출
	private SingletonNumber() {
		System.out.println(">>> SingletonNumber의 private 생성자 호출! ");
		
	}
	
	// ---> 세 번째로 호출 
	public static SingletonNumber getInstance() {
		return singleton;
	}
	
	// ---> 여섯 번째로 호출(작동)
	public int getNextNumber() {
		return ++cnt;
	}
	
}







/*
	!!! === singleton 패턴에서 중요한 것은 다음의 3가지 이다 === !!!
	
	 == 첫번째,
	    private 변수로 자기 자신의 클래스 인스턴스를 가지도록 해야 한다. 
	   접근제한자가 private 이므로 외부 클래스에서는 직접적으로 접근이 불가하다. 
	   또한 static 변수로 지정하여  SingletonNumber 클래스를 사용할때 
	   객체생성은 딱 1번만 생성되도록 해야 한다.         
	   
	== 두번째,
	    생성자 private으로 지정 & 외부에서 인스턴스를 생성불가 
	  
	== 세번째,
		static 메소드를 생성(지금은 getInstance() )하여 외부에서 해당 클래스의 객체를 사용할 수 있도록 해준다.

*/