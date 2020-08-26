package com.oranda.pacdasher.uimodel.ghosts.states;

import com.oranda.pacdasher.uimodel.Const;
import com.oranda.pacdasher.uimodel.PosAndDirection;
import com.oranda.pacdasher.uimodel.ghosts.*;
import com.oranda.pacdasher.uimodel.util.UIModelConsts;

public class ReturningState implements MoveState {

    @Override
    public MoveAttempt move(GhostStrategy context, PosAndDirection posAndDirection) {
        if (context.getVirtualTimeSinceReturning() > Ghost.TIME_RETURNING)
        {
            Const.logger.fine("setting back to normal, "
                    + "virtualTimeSinceReturning is "
                    + context.getVirtualTimeSinceReturning());
            context.setGhostState(UIModelConsts.GhostState.NORMAL_GHOST_STATE);
        }
        else
        {
            return null; // i.e. don't move
        }
        return GMoveAttemptReturning.getInstance();
    }
}
