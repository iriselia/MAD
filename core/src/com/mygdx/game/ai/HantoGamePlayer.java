package com.mygdx.game.ai;

import com.mygdx.hanto.util.HantoMoveRecord;

public interface HantoGamePlayer
{
	/**
	 * Make the player's next move.
	 * @param opponentsMove this is the result of the opponent's last move, in response
	 * 	to your last move. This will be null if you are making the first move of the game.
	 * @return your move
	 */
	HantoMoveRecord makeMove(HantoMoveRecord opponentsMove);
}