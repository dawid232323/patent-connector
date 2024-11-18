import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {PatentListingComponent} from './features/patent-listing/patent-listing.component';
import {FilterPanelComponent} from './features/patent-listing/partials/filter-panel/filter-panel.component';
import {MatCard, MatCardActions, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {MatFormField, MatInput, MatInputModule} from "@angular/material/input";
import {ReactiveFormsModule} from "@angular/forms";
import {MatDatepicker, MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";
import {MatFormFieldModule, MatHint, MatLabel} from "@angular/material/form-field";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatIcon} from "@angular/material/icon";
import {MatButton, MatFabButton, MatMiniFabButton} from "@angular/material/button";


@NgModule({
	declarations: [
		PatentListingComponent,
		FilterPanelComponent
	],
	imports: [
		CommonModule,
		MatCard,
		MatCardHeader,
		MatCardTitle,
		MatCardContent,
		MatInputModule,
		ReactiveFormsModule,
		MatFormFieldModule,
		MatDatepickerModule,
		MatNativeDateModule,
		MatLabel,
		MatHint,
		MatCheckboxModule,
		MatIcon,
		MatFabButton,
		MatMiniFabButton,
		MatCardActions,
		MatButton
	],
	exports: [
		PatentListingComponent
	],
	providers: [
		MatDatepickerModule
	]
})
export class PatentModule {
}
