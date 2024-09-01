import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RegisterComponent} from "app/features/common/features/register/register.component";
import {
	RegisterFormComponent
} from "app/features/common/features/register/partials/register-form/register-form.component";
import {ReactiveFormsModule} from "@angular/forms";
import {MatError, MatFormField, MatHint, MatLabel, MatPrefix, MatSuffix} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatIcon} from "@angular/material/icon";
import {MatButton, MatFabButton} from "@angular/material/button";
import {TitleCaseDirective} from "app/shared/directive/title-case.directive";
import {RegisterSuccessComponent} from "app/shared/features/register-success/register-success.component";
import {
	RegisterResearchInstitutionWorkerComponent
} from './features/register-research-institution-worker/register-research-institution-worker.component';
import { RegisterInstitutionFormComponent } from './features/register-research-institution-worker/partials/register-institution-form/register-institution-form.component';
import {MatOption, MatSelect} from "@angular/material/select";


@NgModule({
	declarations: [
		RegisterComponent,
		RegisterFormComponent,
		RegisterResearchInstitutionWorkerComponent,
  RegisterInstitutionFormComponent
	],
	imports: [
		CommonModule,
		ReactiveFormsModule,
		MatFormField,
		MatInput,
		MatLabel,
		MatError,
		MatIcon,
		MatSuffix,
		MatButton,
		MatFabButton,
		MatPrefix,
		TitleCaseDirective,
		RegisterSuccessComponent,
		MatSelect,
		MatOption,
		MatHint
	],
	exports: [
		RegisterComponent,
		RegisterResearchInstitutionWorkerComponent
	]
})
export class CommonFeaturesModule {
}
