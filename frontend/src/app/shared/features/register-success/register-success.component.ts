import {Component, Input} from '@angular/core';
import {MatIcon} from "@angular/material/icon";

@Component({
	selector: 'app-register-success',
	standalone: true,
	imports: [
		MatIcon
	],
	templateUrl: './register-success.component.html',
	styleUrl: './register-success.component.scss'
})
export class RegisterSuccessComponent {

	@Input() registeredUserEmail!: string;

}
