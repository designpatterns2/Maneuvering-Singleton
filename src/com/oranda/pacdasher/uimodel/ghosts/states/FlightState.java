package com.oranda.pacdasher.uimodel.ghosts.states;

import com.oranda.pacdasher.uimodel.PosAndDirection;
import com.oranda.pacdasher.uimodel.UIModel;
import com.oranda.pacdasher.uimodel.ghosts.GMoveAttemptFlight;
import com.oranda.pacdasher.uimodel.ghosts.GhostStrategy;
import com.oranda.pacdasher.uimodel.ghosts.MoveAttempt;

public class FlightState implements MoveState {
    @Override
    public MoveAttempt move(GhostStrategy context, PosAndDirection posAndDirection) {
        if (UIModel.getVirtualTime()%2 == 1)
        {
            return null; // i.e. don't move
        }
        return GMoveAttemptFlight.getInstance();
    }
}
