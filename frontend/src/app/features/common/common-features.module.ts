import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RegisterComponent} from "app/features/common/features/register/register.component";
import {RegisterFormComponent} from "app/features/common/features/register/partials/register-form/register-form.component";
import {ReactiveFormsModule} from "@angular/forms";
import {MatError, MatFormField, MatLabel, MatPrefix, MatSuffix} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatIcon} from "@angular/material/icon";
import {MatButton, MatFabButton} from "@angular/material/button";
import {TitleCaseDirective} from "app/shared/directive/title-case.directive";


@NgModule({
	declarations: [
		RegisterComponent,
		RegisterFormComponent
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
		TitleCaseDirective
	],
	exports: [
		RegisterComponent
	]
})
export class CommonFeaturesModule {
}
