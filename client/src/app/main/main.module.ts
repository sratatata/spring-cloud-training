import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MainPanelComponent } from './component/main-panel/main-panel.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    MainPanelComponent
  ],
  exports: [
    MainPanelComponent
  ]
})
export class MainModule { }
