package com.codecool.snake;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.Snake;

import java.util.ArrayList;
import java.util.List;

public class GameLoop {
//    private Snake snake;
    private List<Snake> snakes;

    private boolean running = false;
    private Game game;


//     public GameLoop(Snake snake) {
//         this.snake = snake;
//         this.game = game;

    public GameLoop(List<Snake> snakes) {
        this.snakes = snakes;
    }

    public void start() {
        running = true;
    }

    public void stop() {
        running = false;
    }

    public void step() {
        if(running) {


//             snake.step();
            for (Snake snake : snakes){
                //check if is a game over
                snake.step();
            }
            for (GameEntity gameObject : Globals.getInstance().display.getObjectList()) {
                if (gameObject instanceof Animatable) {
                    ((Animatable) gameObject).step();
                }
            }
            checkCollisions();
        }
        Globals.getInstance().display.frameFinished();
      // updates HealthBar Snake1 
        Globals.getInstance().game.getHealthBarSnake().setWidth(2*Globals.getInstance().game.getSnakeHealth());
    }

    private void checkCollisions() {
        List<GameEntity> gameObjs = Globals.getInstance().display.getObjectList();
        for (int idxToCheck = 0; idxToCheck < gameObjs.size(); ++idxToCheck) {
            GameEntity objToCheck = gameObjs.get(idxToCheck);
            if (objToCheck instanceof Interactable) {
                for (int otherObjIdx = idxToCheck + 1; otherObjIdx < gameObjs.size(); ++otherObjIdx) {
                    GameEntity otherObj = gameObjs.get(otherObjIdx);
                    if (otherObj instanceof Interactable){
                        if(objToCheck.getBoundsInParent().intersects(otherObj.getBoundsInParent())){
                            ((Interactable) objToCheck).apply(otherObj);
                            ((Interactable) otherObj).apply(objToCheck);
                        }
                    }
                }
            }
        }
    }
}
