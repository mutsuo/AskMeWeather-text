/**
 * 
 */
package test;

import java.util.List;
import java.util.Scanner;

import com.bot.entity.Bot;

/**
 *@desc:一句话被描述
 *@author 邓旸
 *@date:2020年2月17日下午3:01:33
 */
public class BotTest {
 
	/**
	 *@desc:一句话描述
	 *@param args
	 *@return:void
	 * @throws Exception 
	 *@trhows
	 */
	public static void main(String[] args) throws Exception {
		Bot bot = Bot.getInstance();
		Scanner sc = new Scanner(System.in);
		String utterance = sc.next();
		while(!utterance.equals("退出")) {
			List<String> replys = bot.start(utterance);
			System.out.println("\n----------------------------------------------------------------");
			for(String reply: replys) {
				System.out.println(reply);
			}
			System.out.println("----------------------------------------------------------------\n");
			utterance = sc.next();
		}
		
	}

}
