import { Component, OnInit } from '@angular/core';
import { text } from '@angular/core/src/render3';
import { injectArgs } from '@angular/core/src/di/injector_compatibility';
import { Page, layout } from 'tns-core-modules/ui/page/page';

@Component({
  selector: 'ns-grid',
  templateUrl: './grid.component.html',
  styleUrls: ['./grid.component.css'],
  moduleId: module.id,
})
export class GridComponent  {
  
  onGameMoveTap(index: number){
   //getChildAt(index).set(text,"X");
  }

}
