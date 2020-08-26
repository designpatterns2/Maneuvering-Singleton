package com.oranda.pacdasher.uimodel.ghosts.states;

import com.oranda.pacdasher.uimodel.PosAndDirection;
import com.oranda.pacdasher.uimodel.ghosts.GhostStrategy;
import com.oranda.pacdasher.uimodel.ghosts.MoveAttempt;

public interface MoveState {
    MoveAttempt move(GhostStrategy context, PosAndDirection posAndDirection);
}
