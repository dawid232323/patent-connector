import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddInventionDemandComponent } from 'app/features/invention-demand/features/add-invention-demand/add-invention-demand.component';



@NgModule({
  declarations: [
    AddInventionDemandComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    AddInventionDemandComponent
  ]
})
export class InventionDemandModule { }
