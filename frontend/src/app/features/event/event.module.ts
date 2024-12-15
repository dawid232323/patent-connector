import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AddEditEventComponent} from './add-edit-event/add-edit-event.component';
import {EventFormComponent} from './add-edit-event/partials/event-form/event-form.component';
import {ReactiveFormsModule} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {NgxEditorModule} from "ngx-editor";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {MatIconModule} from "@angular/material/icon";
import {MatChipsModule} from "@angular/material/chips";
import {MatButtonModule} from "@angular/material/button";
import {RouterLink} from "@angular/router";
import {EventService} from "app/features/event/service/event.service";


@NgModule({
	declarations: [
		AddEditEventComponent,
		EventFormComponent
	],
	imports: [
		CommonModule,
		ReactiveFormsModule,
		MatFormFieldModule,
		MatInputModule,
		MatCheckboxModule,
		NgxEditorModule,
		MatDatepickerModule,
		MatAutocompleteModule,
		MatIconModule,
		MatChipsModule,
		MatButtonModule,
		RouterLink
	],
	providers: [EventService],
	exports: [
		AddEditEventComponent
	]
})
export class EventModule {
}
