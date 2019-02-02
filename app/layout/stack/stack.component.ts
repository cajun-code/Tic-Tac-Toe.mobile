import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'ns-stack',
  templateUrl: './stack.component.html',
  styleUrls: ['./stack.component.css'],
  moduleId: module.id,
})
export class StackComponent  {
  userPiece = 'X';
  AIPiece='O';
  reset: boolean =  false;
  gameStartedState: boolean = false;
  textColorX= 'red';
  textColorO= 'black';
  infoBoxText= 'Your peice is defaulted to X. Please select your piece and  press START button to start game.';

  onRadioTap(piece: string)
  {
    if (this.gameStartedState==false)
    {
      this.userPiece=piece;
      switch(piece)
      {
        case 'X':
          this.userPiece='X';
          this.AIPiece='O';
          this.textColorX='red';
          this.textColorO='black';
          this.infoBoxText= 'Your peice is X. Please select your piece and  press START button to start game.';
          console.log("rdio button X is pressed");
        break;
      case 'O':
        this.userPiece='O';
        this.AIPiece='X';
        this.textColorX='black';
        this.textColorO='red';
        this.infoBoxText= 'Your peice is O. Please select your piece and  press START button to start game.';
        console.log("rdio button O is pressed");
      break;
      default:
        console.log("bad radio buttion input");
      break;
    } 
    }
    else
    {
      this.infoBoxText="Game in Progress, your piece is "+this.userPiece;
    }
  }

  onStartTap(){
    this.onResetTap();
    this.gameStartedState = true;
  }

  onResetTap(){
    this.gameStartedState = false;
  }

}
