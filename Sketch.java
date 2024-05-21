import processing.core.PApplet;

public class Sketch extends PApplet {

  // Arrays to store snowflake positions and visibility
  float[] fltSnowY = new float[20];
  float[] fltSnowX = new float[20];
  boolean[] blnSnowflakeHidden = new boolean[20];
  float fltSnowSpeed = 2.3f; // Speed of snowfall

  // Variables for player circle
  int intCircleSize = 33;
  int intCircleX;
  int intCircleY;
  int intLives = 3; // Lives of the player
  double dblClickingRadius = 20;

  // Variables to manage movement
  boolean blnMoveRight = false;
  boolean blnMoveLeft = false;
  boolean blnMoveUp = false;
  boolean blnMoveDown = false;
  boolean blnIncreaseSpeed = false;
  boolean blnDecreaseSpeed = false;

  // Variable for game end
  boolean blnGameEnded = false; // Flag to track game end

  /**
   * Sets the window size.
   */
  public void settings() {
    size(700, 650);
  }

  /**
   * Initializes variables and sets the background.
   */
  public void setup() {
    background(0);
    // Initialize snowflake x and y positions randomly
    for (int i = 0; i < fltSnowX.length; i++) {
      fltSnowX[i] = random(width);
      fltSnowY[i] = random(-500, 0); // Start snowflakes off-screen
      blnSnowflakeHidden[i] = false;
    }
    intCircleX = width / 2;
    intCircleY = height - 50;
  }

  /**
   * Draws the snowfall game.
   */
  public void draw() {
    background(0);

    if (!blnGameEnded) {

      // Display lives indicator (squares)
      fill(255);
      for (int i = 0; i < intLives; i++) {
        rect(width - 40, 20 + i * 20, 15, 15);
      }

      // Update and display snowflakes
      for (int i = 0; i < fltSnowY.length; i++) {
        if (!blnSnowflakeHidden[i]) {
          fill(255);
          ellipse(fltSnowX[i], fltSnowY[i], 32, 32); // Draw snowflake
          fltSnowY[i] += fltSnowSpeed; // Move snowflake down

          // Reset snowflake to top when it reaches bottom
          if (fltSnowY[i] > height) {
            fltSnowY[i] = 0;
            fltSnowX[i] = random(width);
          }

          // Check if snowflake is clicked
          if (dist(mouseX, mouseY, fltSnowX[i], fltSnowY[i]) < dblClickingRadius && mousePressed) {
            blnSnowflakeHidden[i] = true; // Hide snowflake
          }

          // Check collision between player and snowflake
          if (dist(intCircleX, intCircleY, fltSnowX[i], fltSnowY[i]) < intCircleSize / 2 + 15) {
            blnSnowflakeHidden[i] = true; // Hide snowflake
            intLives--; // Decrease lives
            if (intLives <= 0) {
              blnGameEnded = true; // Set game ended flag
            }
          }
        }
      }

      // Draw and update player circle
      fill(0, 0, 255);
      ellipse(intCircleX, intCircleY, intCircleSize, intCircleSize);

      // Adjust circle movement
      if (blnMoveRight) {
        intCircleX = constrain(intCircleX + 5, intCircleSize / 2, width - intCircleSize / 2);
      }
      if (blnMoveLeft) {
        intCircleX = constrain(intCircleX - 5, intCircleSize / 2, width - intCircleSize / 2);
      }
      if (blnMoveUp) {
        intCircleY = constrain(intCircleY - 5, intCircleSize / 2, height - intCircleSize / 2);
      }
      if (blnMoveDown) {
        intCircleY = constrain(intCircleY + 5, intCircleSize / 2, height - intCircleSize / 2);
      }

      // Adjust snowfall speed
      if (blnIncreaseSpeed) {
        fltSnowSpeed += 0.1f; // Increase snowfall speed
      }
      if (blnDecreaseSpeed) {
        fltSnowSpeed = max((float) 0.1, fltSnowSpeed - 0.1f); // Decrease snowfall speed but not below zero
      }
    }

    if (blnGameEnded) {
      background(255);
    }
  }

  /**
   * Handles key presses to control player movement and snowfall speed.
   */
  public void keyPressed() {
    if (key == 'd' || key == 'D') {
      blnMoveRight = true;
    } else if (key == 'a' || key == 'A') {
      blnMoveLeft = true;
    } else if (key == 'w' || key == 'W') {
      blnMoveUp = true;
    } else if (key == 's' || key == 'S') {
      blnMoveDown = true;
    } else if (keyCode == DOWN) {
      blnIncreaseSpeed = true;
    } else if (keyCode == UP) {
      blnDecreaseSpeed = true;
    }
  }

  /**
   * Handles key releases to control player movement and snowfall speed.
   */
  public void keyReleased() {
    if (key == 'd' || key == 'D') {
      blnMoveRight = false;
    } else if (key == 'a' || key == 'A') {
      blnMoveLeft = false;
    } else if (key == 'w' || key == 'W') {
      blnMoveUp = false;
    } else if (key == 's' || key == 'S') {
      blnMoveDown = false;
    } else if (keyCode == DOWN) {
      blnIncreaseSpeed = false;
    } else if (keyCode == UP) {
      blnDecreaseSpeed = false;
    }
  }
}