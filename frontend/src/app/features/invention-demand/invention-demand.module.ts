import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {
	AddInventionDemandComponent
} from 'app/features/invention-demand/features/add-invention-demand/add-invention-demand.component';
import {
	InventionDemandFormComponent
} from 'app/features/invention-demand/features/add-invention-demand/partials/invention-demand-form/invention-demand-form.component';
import {ReactiveFormsModule} from "@angular/forms";
import {MatAutocomplete, MatAutocompleteTrigger, MatOptgroup, MatOption} from "@angular/material/autocomplete";
import {MatChip, MatChipRemove, MatChipSet} from "@angular/material/chips";
import {MatError, MatFormField, MatHint, MatLabel, MatPrefix, MatSuffix} from "@angular/material/form-field";
import {MatIcon} from "@angular/material/icon";
import {MatInput} from "@angular/material/input";
import {NgxEditorModule} from "ngx-editor";
import {RouterLink} from "@angular/router";
import {MatButton} from "@angular/material/button";


@NgModule({
	declarations: [
		InventionDemandFormComponent,
		AddInventionDemandComponent,
	],
	imports: [
		CommonModule,
		ReactiveFormsModule,
		MatAutocomplete,
		MatAutocompleteTrigger,
		MatChip,
		MatChipRemove,
		MatChipSet,
		MatError,
		MatFormField,
		MatHint,
		MatIcon,
		MatInput,
		MatLabel,
		MatOption,
		MatPrefix,
		NgxEditorModule,
		MatOptgroup,
		MatSuffix,
		RouterLink,
		MatButton
	],
	exports: [
		AddInventionDemandComponent,
		InventionDemandFormComponent,
	]
})
export class InventionDemandModule {
}
