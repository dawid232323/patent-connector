import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {PatentListingComponent} from './features/patent-listing/patent-listing.component';
import {FilterPanelComponent} from './features/patent-listing/partials/filter-panel/filter-panel.component';
import {
	MatCard,
	MatCardActions,
	MatCardContent,
	MatCardHeader,
	MatCardModule,
	MatCardTitle
} from "@angular/material/card";
import {MatFormField, MatInput, MatInputModule} from "@angular/material/input";
import {ReactiveFormsModule} from "@angular/forms";
import {MatDatepicker, MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";
import {MatFormFieldModule, MatHint, MatLabel} from "@angular/material/form-field";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatIcon} from "@angular/material/icon";
import {MatButton, MatFabButton, MatMiniFabButton} from "@angular/material/button";
import {
	PatentListingParamsService
} from "app/features/patent/features/patent-listing/service/patent-listing-params.service";
import {PatentListingService} from "app/features/patent/features/patent-listing/service/patent-listing.service";
import { PatentCardComponent } from './features/patent-listing/partials/patent-card/patent-card.component';
import {MatChipsModule} from "@angular/material/chips";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatProgressBarModule} from "@angular/material/progress-bar";


@NgModule({
	declarations: [
		PatentListingComponent,
		FilterPanelComponent,
  PatentCardComponent
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
		MatButton,
		MatCardModule,
		MatChipsModule,
		MatPaginatorModule,
		MatProgressBarModule
	],
	exports: [
		PatentListingComponent
	],
	providers: [
		MatDatepickerModule,
		PatentListingParamsService,
		PatentListingService
	]
})
export class PatentModule {
}
