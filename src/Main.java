import java.awt.Point;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {
	private static List<String> okColors = Arrays.asList(new String[] { "4eafff", "68bbff", "6abcff", "67baff", "66b9ff", "65b9ff", "69bbff", "4caeff", "48acff", "4badff", "50b0ff", "4aadff", "0054a8", "4daeff", "4fb0ff", "4fafff", "32bcff", "096dd2", "8fb7cb", "61d0ff", "49adff", "a3ddff", "83d5ff", "218df9", "0053a7", "50b8ff", "46aaff", "103860", "004ea4", "2791fe", "3aa2ff", "218cf9", "49acff", "3aa1ff", "0050a0", "4eb5ff", "0053a6", "50b3ff", "50b7ff", "004fa3", "46abff", "49b8ff", "0058ae", "2790ff", "1e8bff", "1b8bfa", "1d87fe", "1b88fd", "1f89fa", "1f8afc" });
	private static List<String> openChestColors = Arrays.asList(new String[] { "ffe05d", "ffc846", "fec745", "fedf5c", "ffc946", "fedf5d", "fec845", "fec846", "fee05c", "ffe15d", "fee05d", "ffe05c", "ffdf5d", "fec946", "fec945", "ffca47", "ffc845", "fee15c", "ffca46", "feca46", "fee15d", "ffcb47", "fdc645", "fec746", "ffc746", "ffdf5c", "fecb46", "fece5e", "fed166", "f1d669", "fdc748", "f8da5f", "ecdc94", "fdc950", "fece5c", "bcab62", "fecc46", "c7b35f", "fecd47", "d9ca85", "e8d175", "fdc94c", "ffcd47", "fed673", "ffcc47", "fdc646", "feca47", "fdde5d", "fed36d", "fee5a3", "ffcc49", "ffbe3c", "ffbe3b", "ffcb48", "fecb48", "ffcb49", "ffcc48", "ffc240", "ffbf3d", "fdd98b", "febe3b", "ffc644", "ffc441", "ffc13f", "ffc341", "ffc947", "fdbd3c", "febd3b", "fecb49", "ffca48", "ffc13e", "ffbd3b", "ffc745", "ffc543", "f6d066", "fecc49", "ffcd4a", "fdc453", "e5bc64", "fccf55", "ffc744", "fdc75c", "aa812a", "b0862c", "b68a2d", "fdc045", "fcd889", "fec244", "d8b571", "f2cf6d", "fcc95b", "fdcd6b", "fdc658", "fdcd4b", "c4993d", "fad05d", "fec44a", "e7c367", "fdc24c", "fdc350", "fdbf42", "ffbf3c", "fec947", "ffc03e", "febe3c", "fdd689", "fdbd3e", "fdcd4f", "fdca65", "fdbd3b", "fdbe40", "fdbd3d", "fdd789", "fece69", "febf3d", "ffbd3c", "fcc860", "fec03d", "ffc03d", "fdcc68", "feca48", "fdcd65", "fdd98d", "fdd88a", "febd3c", "fdc148", "fec240", "fdca5c", "fec442", "ffc340", "fec140", "fec03e", "ffc442", "fbbd41", "fecc48", "fcd98d", "fcbc3b", "ffc140", "febf3c", "fcc148", "ffc544", "ffc542", "fec441", "fec643", "ffc241", "fec544", "aa822a", "fdc44a", "fcbd3e", "ffc145", "fcbd3f", "c59a3d", "fccd65", "b1872c", "fcbe40", "fdc860", "e7c466", "fccb4a", "fccc68", "f9d05d", "fdbc3c", "fec148", "fcc95c", "fcd789", "f2ce6c", "fec744", "b68b2d", "fbcc65", "fdbc3b", "facb64", "fcd88a", "fec644", "fcc24e", "fec13f", "fec03f", "f3d070", "fec543", "fdc54d", "e0c16f", "fcca65", "fcdfa1", "fac75a", "fcc456", "fcbd3d", "fcbc3d", "fdce69", "fdd688", "fccd4f", "fbdd9c", "fddf9f", "fccb67", "fcbc3c", "fecb47", "d7b570", "fdcb63", "fcd78a", "fcde9e", "fbcc50", "fdd88b", "fccc4b", "fbd059", "facf58", "fbcb4b", "fbcc4e", "fdd056", "fcd686", "fccf54", "f7d063", "fbcc4d", "fcd683", "fccd4e", "fcdd9b", "b28c3c", "fcd788", "fcdf9e", "fbd788", "dcb96d", "e5bb64", "dcb86d", "fcd98b", "fcd588", "d0b47d", "cfb47d", "d8b670", "fcd689", "fddd9c", "fcdc9d", "fbde9e", "9f7928", "b28d3c", "fcdd9c", "eecf77", "eece77", "fcd47d", "fdcd68", "fdd98c", "997426", "b78b2e", "a07a28", "fdd98a", "916e24", "987426", "916f24", "f8d063", "fbca68", "fccd6c", "fcbb3b", "fdd176", "fbbb3d", "fbbc3d", "fbd179", "fbbb3b", "fcbd3c", "fdc95f", "feca60", "fdc346", "fcc54f", "feca5f", "fbd074", "fdcc65", "fbbc3f", "fccd6d", "fcc24c", "fdc24d", "fcc24d", "fdcd6a", "fbc351", "fcc049", "fbc251", "fcc04a", "fec149", "fdbf45", "fcbf42", "fcbf44", "fcc350", "fdc149", "fccd6a", "fec345", "ffc343", "fac759", "fcc75c", "fdc14b", "fdc95c", "fdc657", "fac85a", "fcc75b", "fbca67", "fac758", "fbcb64", "fbcb63", "facf72", "fbd072", "fdcf72", "fecc64", "fbc85a", "fbc456", "fcd076", "fcce72", "fec34f", "fcc557", "fec242", "fdc960", "fcc75d", "fec553", "fbc862", "fbc861", "fccc67", "fbd076", "fdca5b", "fcd179", "fcc65a" });
	private static String ADB_DEVICE_IP = "10.0.1.18";
	private static Point battleTab;
	private static Point battleButton;
	private static Point twoVTwoButton;
	private static Point battleChat;
	private static Point exitBattle;
	private static Point exit2v2Battle;
	private static Point quick2v2Match;
	private static Point topLeftBattlefield;
	private static Point bottomRightBattlefield;
	private static Point chestsFullYes;
	private static Point close2v2Chat;
	private static Point center;
	private static Map<String, Point> chatOpts = new HashMap<String, Point>();
	private static Map<String, Point> cards = new HashMap<String, Point>();
	private static Map<String, Point> chests = new HashMap<String, Point>();
	public static void main(String[] args){
		if(connectToDevice() && checkClashRoyaleInstalled()){
			launchClashRoyale();
			System.out.println("App loaded!");
			initializeButtons();
			for(int i = 0; i < 500; i++){
				openChests();
				startBattle(false);
			}
		}
	}
	private static void initializeButtons(){
		Point screenSize = getScreenSize();
		battleTab = new Point((int)(screenSize.x * 0.5), (int)(screenSize.y * 0.96875));
		battleButton = new Point((int)(screenSize.x * 0.33), (int)(screenSize.y * 0.65));
		twoVTwoButton = new Point((int)(screenSize.x * 0.66), (int)(screenSize.y * 0.65));
		battleChat = new Point((int)(screenSize.x * 0.1), (int)(screenSize.y * 0.85));
		exitBattle = new Point((int)(screenSize.x * 0.5), (int)(screenSize.y * 0.895));
		exit2v2Battle = new Point((int)(screenSize.x * 0.1), (int)(screenSize.y * 0.95));
		quick2v2Match = new Point((int)(screenSize.x * 0.66), (int)(screenSize.y * 0.5625));
		topLeftBattlefield = new Point((int)(screenSize.x * 0.1), (int)(screenSize.y * 0.3));
		bottomRightBattlefield = new Point((int)(screenSize.x * 0.88), (int)(screenSize.y * 0.74));
		chestsFullYes = new Point((int)(screenSize.x * 0.5), (int)(screenSize.y * 0.605));
		close2v2Chat = new Point((int)(screenSize.x * 0.94), (int)(screenSize.y * 0.025));
		center = new Point((int)(screenSize.x * 0.5), (int)(screenSize.y * 0.5));

		chatOpts.put("happy", new Point((int)(screenSize.x * 0.27), (int)(screenSize.y * 0.8)));
		chatOpts.put("angry", new Point((int)(screenSize.x * 0.5), (int)(screenSize.y * 0.8)));
		chatOpts.put("sad", new Point((int)(screenSize.x * 0.7), (int)(screenSize.y * 0.8)));
		chatOpts.put("funny", new Point((int)(screenSize.x * 0.9), (int)(screenSize.y * 0.8)));
		chatOpts.put("gl", new Point((int)(screenSize.x * 0.32), (int)(screenSize.y * 0.86)));
		chatOpts.put("wp", new Point((int)(screenSize.x * 0.6), (int)(screenSize.y * 0.86)));
		chatOpts.put("wow", new Point((int)(screenSize.x * 0.88), (int)(screenSize.y * 0.86)));
		chatOpts.put("ty", new Point((int)(screenSize.x * 0.32), (int)(screenSize.y * 0.91)));
		chatOpts.put("gg", new Point((int)(screenSize.x * 0.6), (int)(screenSize.y * 0.91)));
		chatOpts.put("oops", new Point((int)(screenSize.x * 0.88), (int)(screenSize.y * 0.91)));

		cards.put("1", new Point((int)(screenSize.x * 0.3125), (int)(screenSize.y * 0.9)));
		cards.put("2", new Point((int)(screenSize.x * 0.5), (int)(screenSize.y * 0.9)));
		cards.put("3", new Point((int)(screenSize.x * 0.6875), (int)(screenSize.y * 0.9)));
		cards.put("4", new Point((int)(screenSize.x * 0.875), (int)(screenSize.y * 0.9)));

		chests.put("crown", new Point((int)(screenSize.x * 0.54), (int)(screenSize.y * 0.17)));
		chests.put("1", new Point((int)(screenSize.x * 0.12), (int)(screenSize.y * 0.75)));
		chests.put("2", new Point((int)(screenSize.x * 0.38), (int)(screenSize.y * 0.75)));
		chests.put("3", new Point((int)(screenSize.x * 0.64), (int)(screenSize.y * 0.75)));
		chests.put("4", new Point((int)(screenSize.x * 0.9), (int)(screenSize.y * 0.75)));
	}
	private static String getPixelColor(Point pixel){
		Utils.run("adb shell rm /sdcard/screen.dump");
		Utils.run("adb shell screencap /sdcard/screen.dump");
		Point screenSize = getScreenSize();
		int offset = screenSize.x*pixel.y+pixel.x+3;
		String rgbHex = Utils.run("adb shell dd if=/sdcard/screen.dump bs=4 count=1 skip=" + offset + " 2>/dev/null | hexdump").substring(8, 16).replaceAll(" ","");
		Utils.run("adb shell rm /sdcard/screen.dump");
		return rgbHex;
	}
	private static void openChests(){
		tap(battleTab);
		chests.forEach((index, chest) -> {
			String rgbHex = getPixelColor(chest);
		    if(openChestColors.contains(rgbHex)){
		    	for(int i = 0; i <= 12; i++){
		    		tap(chest);
		    	}
		    }
		});
	}
	private static void startBattle(boolean twoVTwo){
		tap(battleTab);
		tap((twoVTwo ? twoVTwoButton : battleButton));
		if(twoVTwo) tap(quick2v2Match);
		tap(chestsFullYes);
		sleep(3000);
		Date endTime = new Date(System.currentTimeMillis()+4*60000);
		System.out.println("Battle started! Assuming full OT, game ends at " + endTime + ".");
		tap(center);
		tap(center);
		tap(battleChat);
		tap(chatOpts.get("happy"));
		tap(battleChat);
		tap(chatOpts.get("gg"));
		Thread finishedChecker = new Thread(){
			public void run(){
				boolean gameEnded = false;
				while(!gameEnded){
					String rgbHex = getPixelColor(exitBattle);
					gameEnded = okColors.contains(rgbHex);
				}
			}
		};
		finishedChecker.setDaemon(true);
		finishedChecker.start();
		while((new Date(System.currentTimeMillis())).getTime() < endTime.getTime()){ //Battle ends if back button does not exit
			drag(cards.get(String.valueOf(getRandomNumberInRange(1,4))), randomPoint(), getRandomNumberInRange(100,750));
			sleep(getRandomNumberInRange(300,1000));
			if(!finishedChecker.isAlive()){
				System.out.println("Battle ended early due to OK button being found!");
				break;
			}
		}
		if(twoVTwo) tap(close2v2Chat);
		tap(battleChat);
		tap(chatOpts.get("happy"));
		tap(battleChat);
		tap(chatOpts.get("gg"));
		sleep(1500);
		tap((twoVTwo ? exit2v2Battle : exitBattle));
		sleep(1500);
		System.out.println("Battle ended and exitted!");
	}
	private static void drag(Point from, Point where, int milliseconds){
		Utils.run("adb shell input swipe " + from.x + " " + from.y + " " + where.x + " " + where.y + " " + milliseconds);
	}
	private static void tap(Point where){
		Utils.run("adb shell input tap " + where.x + " " + where.y);
	}
	private static boolean connectToDevice(){
		String ip = ADB_DEVICE_IP;
		if(ip.isEmpty()){
			System.out.print("Enter IP address to ADB into(or type 'usb'): ");
			Scanner scan = new Scanner(System.in);
			ip = scan.next();
			scan.close();
		}
		if(!ip.toLowerCase().trim().equals("usb")){
//			Utils.run("adb disconnect");
//			Utils.run("adb kill-server");
			String connectResult = Utils.run("adb connect " + ip);
			if(connectResult.contains("connected to ")){
				System.out.println("Successfully connected to " + ip + "!");
				return true;
			}else{
				System.out.println("An error connecting occurred. The error is:\n" + connectResult);
			}
		}else{
			String connectResult = Utils.run("adb usb");
			if(connectResult.contains("restarting in USB mode")){
				System.out.println("Successfully connected to USB device!");
				return true;
			}else{
				System.out.println("An error connecting occurred. The error is:\n" + connectResult);
			}
		}
		return false;
	}
	private static boolean checkClashRoyaleInstalled(){
		String packageList = Utils.run("adb shell pm list packages");
		if(packageList.contains("com.supercell.clashroyale")){
			return true;
		}else{
			System.out.println("Clash Royale is not installed or cannot be found.");
			System.out.println(packageList);
		}
		return false;
	}
	private static void launchClashRoyale(){
		Utils.run("adb shell monkey -p com.supercell.clashroyale -c android.intent.category.LAUNCHER 1");
		while(!Utils.run("adb shell dumpsys activity top").contains("android.webkit.WebView")){
			sleep(1);
		}
	}
	private static void sleep(int milliseconds){
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private static Point getScreenSize(){
		String screenSize = Utils.run("adb shell wm size").replaceAll("Physical size: ", "").trim();
		return new Point(Integer.parseInt(screenSize.split("x")[0]),Integer.parseInt(screenSize.split("x")[1]));
	}
	private static Point randomPoint(){
	    int minX = topLeftBattlefield.x;
	    int minY = topLeftBattlefield.y;
	    int maxX = bottomRightBattlefield.x;
	    int maxY = bottomRightBattlefield.y;

	    int rndX = getRandomNumberInRange(minX, maxX);
	    int rndY = getRandomNumberInRange(minY, maxY);
	    return new Point(rndX, rndY);
	}
	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
}
