package pong;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Point;

/**
 * Class BallTest
 */
public class BallTest {
	private Ball ball = new Ball();
	
	/**
	 * Test to check if xDirection Value would change on PaddleCollision
	 */
	@Test
	public void testPaddleCollision() {
		ball.setLocation(new Point(600, 200));
		float initialDirection = ball.getXDirection();
		ball.changeBallDirection();
		assertEquals(initialDirection, ball.getXDirection() * -1, "Ball should change its horizontal direction on paddle hit");		
	}
	
	/**
	 * Test to check whether the ball will change its vertical direction and continue with the correct speed on a wall hit and 
	 */
	/* Code changed too much
	@Test
	public void testVerticalCollision() {
		ball.setLocation(new Point(600, 10));
		ball.setBounds(1263, 615);
		ball.setYDirection(1);
		ball.updateLocation();
		ball.updateLocation();
		ball.updateLocation();		
		//Ball Direction should change from -1 to 1
		assertEquals(1, ball.getYDirection(), "Ball should change its vertical direction on wall hit");
		//Ball should be at y = 20, 21, 22, ...
		assertEquals(20, ball.getLocation().getY());
		ball.updateLocation();
		assertEquals(30, ball.getLocation().getY());
		ball.updateLocation();
		assertEquals(40, ball.getLocation().getY());
	}
	*/
}
