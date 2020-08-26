/**
 *  PacDasher application. For explanation of this class, see below. 
 *  Copyright (c) 2003-2005 James McCabe. Email: code@oranda.com 
 *  http://www.oranda.com/java/pacdasher/
 * 
 *  PacDasher is free software under the Aladdin license (see license  
 *  directory). You are free to play, copy, distribute, and modify it
 *  except for commercial purposes. You may not sell this code, or
 *  compiled versions of it, or anything which incorporates either of these.
 * 
 */
 
package com.oranda.pacdasher.uimodel.ghosts;

import com.oranda.pacdasher.uimodel.*;
import com.oranda.pacdasher.uimodel.ghosts.states.*;
import com.oranda.pacdasher.uimodel.util.*;
import com.oranda.pacdasher.uimodel.util.UIModelConsts.GhostState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GhostStrategy
{
    protected static ArrayList<DirectionCode> possibleDirections;
        
    protected Ghost ghost;
    protected Random randomGenerator;
    protected int virtualTimeBeganReturning;
    private GhostState ghostState;
    private MoveState moveState;
    
    static
    {
        possibleDirections = new ArrayList<DirectionCode>(4);
        possibleDirections.add(DirectionCode.UP);
        possibleDirections.add(DirectionCode.LEFT);
        possibleDirections.add(DirectionCode.DOWN);
        possibleDirections.add(DirectionCode.RIGHT);
    }
    
    public GhostStrategy(Ghost ghost)
    {
        this.ghost = ghost;        
        this.randomGenerator = new Random();
        moveState = new NormalState();
    }

    public void move(PosAndDirection posAndDirection,
        XY pacDasherXy, DirectionCode pacDasherDirectionCode)
    {  

        MoveAttempt moveAttempt = moveState.move(this, posAndDirection);

        if(moveAttempt == null) return;

        tryMove(posAndDirection, pacDasherXy, 
            pacDasherDirectionCode, moveAttempt);
    }

    public void tryMove(PosAndDirection posAndDirection,
        XY pacDasherXy, DirectionCode pacDasherDirectionCode,
        MoveAttempt moveAttempt)
    {
        XY xy = (XY) ghost.getXY().clone();

        if ((xy.getX()%UIModelConsts.X_TILE_SIZE == 0 && ghost.isMovingHorizontally())
            || (xy.getY()%UIModelConsts.Y_TILE_SIZE == 0 && ghost.isMovingVertically()))
        {
            //Const.logger.fine("move() xy " + xy);
            //resetPossibleDirections();
            // if at a possible turning point, decide upon a new direction
            tryMoveDirections(posAndDirection,
                (java.util.List) possibleDirections.clone(),
               pacDasherXy, moveAttempt);
            //XY newXy = posAndDirection.move();    
        } 
        else 
        {
            XY newXy = posAndDirection.move();
            if (newXy.equals(xy))
            {
                // move unsuccessful  
                // next line includes posAndDirection.move()              
                tryMoveDirections(posAndDirection,
                    (java.util.List) possibleDirections.clone(),
                    pacDasherXy, moveAttempt);    
            }
        }        
    }

    /* 
     * Calls itself recursively until there is a move
     */
    public void tryMoveDirections(PosAndDirection posAndDirection,
        java.util.List allowedDirections, 
        XY pacDasherXy, MoveAttempt moveAttempt)
    {
                  
        if (allowedDirections.isEmpty())
        {
            return; // allow escape from infinite recursion
        }
        XY xy = (XY) ghost.getXY().clone();
        DirectionCode desiredDirectionCode 
                = moveAttempt.propose(ghost, pacDasherXy, allowedDirections);             
        posAndDirection.setDesiredDirectionCode(desiredDirectionCode);
        XY newXy = posAndDirection.move();     
        if (xy.equals(newXy))
        {
            // move unsuccessful
            allowedDirections.remove(desiredDirectionCode); 
            tryMoveDirections(posAndDirection, allowedDirections, 
                pacDasherXy, moveAttempt);
        }
    }
    
    /*
     * Ghosts have different returning times.
     */
    protected int getVirtualTimeBeganReturning()
    {
        return virtualTimeBeganReturning;
    }
    
    public int getVirtualTimeSinceReturning()
    {
        return UIModel.getVirtualTime() 
            - getVirtualTimeBeganReturning();
    }
              
    public void markVirtualTimeBeganReturning()
    {
        virtualTimeBeganReturning = UIModel.getVirtualTime();
    }
    
    public GhostState getGhostState()
    {
        return this.ghostState;
    }
    
    public void setGhostState(GhostState ghostState)
    {
        this.ghostState = ghostState;
        if (ghostState == GhostState.RETURNING_GHOST_STATE) {
            moveState = new ReturningState();
            markVirtualTimeBeganReturning();
        } else if (ghostState == GhostState.NORMAL_GHOST_STATE) {
            moveState = new NormalState();
        } else if (ghostState == GhostState.FLIGHT_GHOST_STATE) {
            moveState = new FlightState();
        } else {
            moveState = new ScatterState();
        }
    }
}
 