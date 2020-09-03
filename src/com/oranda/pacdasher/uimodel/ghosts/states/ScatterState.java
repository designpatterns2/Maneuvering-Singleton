package com.oranda.pacdasher.uimodel.ghosts.states;

import com.oranda.pacdasher.uimodel.PosAndDirection;
import com.oranda.pacdasher.uimodel.UIModel;
import com.oranda.pacdasher.uimodel.ghosts.GMoveAttemptFlight;
import com.oranda.pacdasher.uimodel.ghosts.GMoveAttemptScatter;
import com.oranda.pacdasher.uimodel.ghosts.GhostStrategy;
import com.oranda.pacdasher.uimodel.ghosts.MoveAttempt;
import com.oranda.pacdasher.uimodel.util.UIModelConsts;

public class ScatterState implements MoveState {
    @Override
    public MoveAttempt move(GhostStrategy context, PosAndDirection posAndDirection) {
        if (UIModel.getVirtualTime()%2 == 1)
        {
            int xc = posAndDirection.nearestXCoarse();
            int leftXCBoundary = 0;
            int rightXCBoundary = UIModelConsts.MAZE_WIDTH;
            if (xc < leftXCBoundary + 2 || xc > rightXCBoundary - 4)
            {
                return null;
            }
        }

        return GMoveAttemptScatter.getInstance();
    }
}
