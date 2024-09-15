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
import {
	RegisterInstitutionFormComponent
} from './features/register-research-institution-worker/partials/register-institution-form/register-institution-form.component';
import {MatOption, MatSelect} from "@angular/material/select";
import {ActivateAccountComponent} from './features/activate-account/activate-account.component';
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {RouterLink} from "@angular/router";
import {LoginComponent} from './features/login/login.component';
import {
	SelectBusinessBranchesComponent
} from "app/features/common/features/select-business-branches/select-business-branches.component";
import { BusinessBranchChipComponent } from './features/select-business-branches/partials/business-branch-chip/business-branch-chip.component';
import {MatCheckbox} from "@angular/material/checkbox";


@NgModule({
	declarations: [
		RegisterComponent,
		RegisterFormComponent,
		RegisterResearchInstitutionWorkerComponent,
		RegisterInstitutionFormComponent,
		ActivateAccountComponent,
		LoginComponent,
		SelectBusinessBranchesComponent,
  BusinessBranchChipComponent
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
	MatHint,
	MatCard,
	MatCardContent,
	MatCardTitle,
	MatCardHeader,
	RouterLink,
	MatCheckbox
  ],
	exports: [
		RegisterComponent,
		RegisterResearchInstitutionWorkerComponent,
		SelectBusinessBranchesComponent
	]
})
export class CommonFeaturesModule {
}
