import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
public class Main {
	private static String ADB_DEVICE_IP = "10.0.1.20";
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
	public static void main(String[] args){
		if(connectToDevice() && checkClashRoyaleInstalled()){
			launchClashRoyale();
			System.out.println("App loaded!");
			initializeButtons();
			for(int i = 0; i < 500; i++){
				startBattle(false);
			}
		}
	}
	private static void initializeButtons(){
		Point screenSize = getScreenSize();
		battleTab = new Point((int)(screenSize.x * 0.5), (int)(screenSize.y * 0.98));
		battleButton = new Point((int)(screenSize.x * 0.33), (int)(screenSize.y * 0.65));
		twoVTwoButton = new Point((int)(screenSize.x * 0.66), (int)(screenSize.y * 0.65));
		battleChat = new Point((int)(screenSize.x * 0.1), (int)(screenSize.y * 0.85));
		exitBattle = new Point((int)(screenSize.x * 0.5), (int)(screenSize.y * 0.875));
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
					InputStream screencap = Utils.run(true, "adb exec-out screencap -p");
					System.out.println("Got screencap.");
					BufferedImage image;
					try {
						image = ImageIO.read(screencap); //Stream is extremely slow
						int clr =  image.getRGB((int)(image.getWidth() / 2),(int)(image.getHeight() * .895)); 
						gameEnded = (clr == -11751681 ? true : false);
						if(gameEnded){
							System.out.println("Battle ended early due to OK button being found!");
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		finishedChecker.setDaemon(true);
		finishedChecker.start();
		
		
		while((new Date(System.currentTimeMillis())).getTime() < endTime.getTime()){ //Battle ends if back button does not exit
			drag(cards.get(String.valueOf(getRandomNumberInRange(1,4))), randomPoint(), getRandomNumberInRange(100,750));
			sleep(getRandomNumberInRange(300,1000));
			if(!finishedChecker.isAlive()) break;
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
			System.out.print("Enter IP address to ADB into: ");
			Scanner scan = new Scanner(System.in);
			ip = scan.next();
			scan.close();
		}
		String connectResult = Utils.run("adb connect " + ip);
		if(connectResult.contains("connected to ")){
			System.out.println("Successfully connected to " + ip + "!");
			return true;
		}else{
			System.out.println("An error connecting occurred. The error is:\n" + connectResult);
		}
		return false;
	}
	private static boolean checkClashRoyaleInstalled(){
		if(Utils.run("adb shell pm list packages").contains("com.supercell.clashroyale")){
			return true;
		}else{
			System.out.println("Clash Royale is not installed or cannot be found.");
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
