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
import {MatInputModule} from "@angular/material/input";
import {ReactiveFormsModule} from "@angular/forms";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";
import {MatFormFieldModule, MatHint, MatLabel} from "@angular/material/form-field";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatIcon} from "@angular/material/icon";
import {MatButton, MatFabButton, MatMiniFabButton} from "@angular/material/button";
import {
	PatentListingParamsService
} from "app/features/patent/features/patent-listing/service/patent-listing-params.service";
import {PatentListingService} from "app/features/patent/features/patent-listing/service/patent-listing.service";
import {PatentCardComponent} from './features/patent-listing/partials/patent-card/patent-card.component';
import {MatChipsModule} from "@angular/material/chips";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {PatentService} from "app/features/patent/service/patent.service";
import {PatentDetailsComponent} from './features/patent-details/patent-details.component';
import {RouterLink} from "@angular/router";
import {
	PatentBasicDataComponent
} from "app/features/patent/features/patent-details/partials/patent-basic-data/patent-basic-data.component";
import {MatDividerModule} from "@angular/material/divider";
import { PatentAdditionalDataComponent } from './features/patent-details/partials/patent-additional-data/patent-additional-data.component';
import {MatExpansionModule} from "@angular/material/expansion";
import {MatListModule} from "@angular/material/list";
import { PatentAddressBookDetailsComponent } from './features/patent-details/partials/patent-address-book-details/patent-address-book-details.component';


@NgModule({
	declarations: [
		PatentListingComponent,
		FilterPanelComponent,
		PatentCardComponent,
		PatentDetailsComponent,
		PatentBasicDataComponent,
  PatentAdditionalDataComponent,
  PatentAddressBookDetailsComponent
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
		MatProgressBarModule,
		RouterLink,
		MatDividerModule,
		MatExpansionModule,
		MatListModule
	],
	exports: [
		PatentListingComponent,
		PatentDetailsComponent
	],
	providers: [
		MatDatepickerModule,
		PatentListingParamsService,
		PatentListingService,
		PatentService
	]
})
export class PatentModule {
}
