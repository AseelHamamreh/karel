import stanford.karel.*;

public class Homework extends SuperKarel {
    private int consumedBeepers;
    private int numberOfMoves;

    public void run() {
        consumedBeepers = 0;
        numberOfMoves = 0;
        int collectedBeepers = 0;
        int xAxis = 1;
        int yAxis = 1;

//    part1: Checking the beepers and calculating map dimensions:
        int count = 0;
        if(frontIsBlocked()){
            turnLeft();
            while (frontIsClear()){
                if(beepersPresent()){
        
                    pickBeeper();
                    collectedBeepers++;
                }
                moveAndCount();
                yAxis++;
            }
        }
        else {
            while (frontIsClear()) {
                turnLeft();
                while (frontIsClear()) {
                    if (beepersPresent()) {
                        pickBeeper();
                        collectedBeepers++;
                    }
                    moveAndCount();
                    if (count == 0) {
                        yAxis++;
                    }
                }

                count++;
                turnRight();
                if(frontIsClear()){
                    moveAndCount();
                }
                xAxis++;
                turnRight();
                while (frontIsClear()) {
                    if (beepersPresent()) {
                        pickBeeper();
                        collectedBeepers++;
                    }
                    moveAndCount();
                }
                turnLeft();
                if (frontIsClear()) {
                    moveAndCount();
                    xAxis++;
                }
            }
        }

////        part2: Calculating the dimensions without checking beepers:
//        if (frontIsBlocked()) {
//            turnLeft();
//            while (frontIsClear()) {
//                moveAndCount();
//                yAxis++;
//            }
//        } else {
//            while (frontIsClear()) {
//                moveAndCount();
//                xAxis++;
//            }
//            turnLeft();
//            while (frontIsClear()) {
//                moveAndCount();
//                yAxis++;
//            }
//            turnAround();
//            while (frontIsClear()) {
//                moveAndCount();
//            }
//            turnLeft();
//        }

        System.out.println("x-Axis: " + xAxis + " y-Axis: " + yAxis);

//		setting beepers in bag:
        int bagBeepers = (((xAxis + yAxis) * 2) - 4) + collectedBeepers;
        setBeepersInBag(bagBeepers);
        turnAround();

//		Dividing starting with edge case:
        if (yAxis == 1) {
            divideOneAxis(xAxis);
            turnRight();
            turnRight();
        } else if (xAxis == 1) {
            divideOneAxis(yAxis);
            turnLeft();
        } else if (xAxis == 3 && yAxis == 2) {
            moveAndCount();
            turnRight();
            putBeeperAndCount();
            moveAndCount();
            putBeeperAndCount();
            turnAround();
            moveAndCount();
            turnRight();
            moveAndCount();
            turnRight();
            turnRight();
        } else if (xAxis == 2 && yAxis == 3) {
            turnRight();
            moveAndCount();
            turnRight();
            turnAround();
            putBeeperAndCount();
            moveAndCount();
            putBeeperAndCount();
            turnLeft();
            moveAndCount();
            turnLeft();
        } else if (yAxis == 2 && xAxis == 2) {
            putBeeperAndCount();
            moveAndCount();
            turnRight();
            moveAndCount();
            putBeeperAndCount();
            turnRight();
            turnRight();
            moveAndCount();
            turnLeft();
        }

        // even x even:
        else if (xAxis % 2 == 0 && yAxis % 2 == 0 ) {
            drawTwoLinesAtXAxis(xAxis);
            turnLeft();
            drawTwoLinesAtYAxis(yAxis);
            turnRight();
            while (frontIsClear()){
                moveAndCount();
            }
            turnAround();
        }

        //  even x odd
        else if(xAxis %2 == 0 && yAxis %2 != 0 ){
            drawTwoLinesAtXAxis(xAxis);
            turnLeft();
            for (int i=0; i<yAxis/2 ; i++) {
                moveAndCount();
            }
            turnLeft();
            drawLine();
            turnLeft();
            while (frontIsClear()){
                moveAndCount();
            }
            turnLeft();
        }

        // odd x even
        else if(xAxis % 2 != 0 && yAxis %2 == 0){
            turnRight();
            drawTwoLinesAtYAxis(yAxis);
            turnLeft();
            turnAround();
            for (int i=0 ; i<xAxis/2 ;i++){
                moveAndCount();
            }
            turnRight();
            drawLine();
            turnAround();
            while (frontIsClear()){
                moveAndCount();
            }
            turnRight();
            while (frontIsClear()){
                moveAndCount();
            }
            turnRight();
            turnRight();
        }

        // equaled odd x odd
        else if (yAxis == xAxis && xAxis % 2 != 0 && yAxis % 2 != 0) {
            while (frontIsClear()) {
                putBeeperAndCount();
                moveAndCount();
                turnRight();
                moveAndCount();
                turnLeft();
            }
            putBeeperAndCount();
            turnLeft();
            while (frontIsClear()) {
                moveAndCount();
            }
            turnLeft();
            while (frontIsClear()) {
                if (noBeepersPresent()) {
                    putBeeperAndCount();
                }
                moveAndCount();
                turnLeft();
                moveAndCount();
                turnRight();
            }
            putBeeperAndCount();
            turnRight();
            while (frontIsClear()) {
                moveAndCount();
            }
            turnLeft();
            turnAround();
            while (frontIsClear()) {
                moveAndCount();
            }
            turnAround();
        }

        // not equaled odd x odd
        else if (xAxis % 2 != 0 && yAxis % 2 != 0) {
            for (int i = 0; i < xAxis / 2; i++) {
                moveAndCount();
            }
            turnRight();
            drawLine();
            turnRight();
            while (frontIsClear()) {
                moveAndCount();
            }
            turnRight();
            for (int i = 1; i < xAxis / 2; i++) {
                moveAndCount();
            }
            turnRight();
            drawLine();
            turnLeft();
            while (frontIsClear()) {
                moveAndCount();
            }
            turnLeft();
        }


        int remaining = bagBeepers - consumedBeepers;
        int usedFromOwn = bagBeepers - collectedBeepers;
        System.out.println("number of used beepers from his own resources : " + usedFromOwn);
        System.out.println("number of remaining beepers : " + remaining);
        System.out.println("number of total moves : " + numberOfMoves);
    }

    private void moveAndCount() {
        if (frontIsClear()) {
            move();
            numberOfMoves++;
        }
    }

    private void putBeeperAndCount() {
        if (noBeepersPresent()) {
            putBeeper();
            consumedBeepers++;
        }
    }

    private void drawLine() {
        while (frontIsClear()) {
            if (noBeepersPresent()) {
                putBeeperAndCount();
            }
            moveAndCount();
        }
        putBeeperAndCount();
    }

    private void drawTwoLinesAtYAxis(int yAxis){
        for (int i=0; i<yAxis/2 ;i++){
            moveAndCount();
        }
        turnLeft();
        drawLine();
        turnLeft();
        moveAndCount();
        turnLeft();
        drawLine();
        turnRight();
        while (frontIsClear()){
            moveAndCount();
        }
    }

    private void drawTwoLinesAtXAxis(int xAxis){
        for (int i = 0; i < xAxis / 2; i++) {
            moveAndCount();
        }
        turnRight();
        drawLine();
        turnRight();
        moveAndCount();
        turnRight();
        drawLine();
        turnLeft();
        while (frontIsClear()){
            moveAndCount();
        }
    }

    private void divideOneAxis(int axis) {
        if (axis == 5 || axis == 6) {
            if (axis == 5) {
                for (int i = 0; i < axis / 2; i++) {
                    moveAndCount();
                }
            } else {
                for (int i = 1; i < axis / 2; i++) {
                    moveAndCount();
                }
                putBeeperAndCount();
                moveAndCount();
            }
            putBeeperAndCount();
            while (frontIsClear()) {
                moveAndCount();
            }
        } else {
            int count = 0;
            for (int i = 0; i < axis; i++) {
                if (i % 2 != 0 && count < 6) {
                    putBeeperAndCount();
                }
                if (frontIsClear()) {
                    moveAndCount();
                    count++;
                }
                if (count > 6) {
                    while (frontIsClear()) {
                        putBeeperAndCount();
                        moveAndCount();
                    }
                    if (noBeepersPresent()) {
                        putBeeperAndCount();
                    }
                }
            }
        }
    }
}