package tools;

public class Tests {

	public Tests() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int byteCountOnString(){
		int size = 0;
		
		String data = "<14>Jul 10 15:53:53 192.168.40.2 1,2012/05/24 19:50:13,001901000207,THREAT,url,1,2012/05/24 19:50:12,2.1.39.128,2.101.35.223,0.0.0.0,0.0.0.0,IOC-Filter,,,web-browsing,vsys1,Niprnet,Internet,ethernet1/22,ethernet1/21,Splunklog-Universal,2012/05/24 19:50:13,856029,1,44231,80,0,0,0x8000,tcp,alert,'google.com/',(9999),search-engines,informational,client-to-server\000";
		byte[] bytes = data.getBytes();
		System.out.println(bytes.length);
		
		data = "@SATX,UNCLASSIFIED,2012/05/24 19:50:13,001901000207,2012/05/24 19:50:12,2.1.39.128,2.101.35.223,856029,44231,80,0x8000,'goole.com/',(9999),search-engines,informational¨";
		byte[] bytes2 = data.getBytes();
		System.out.println(bytes2.length);
		
		
		return size;
	}
	
	
	public static void main(String[] args){
		Tests t = new Tests();
		t.byteCountOnString();
		
		
	}
	

}
