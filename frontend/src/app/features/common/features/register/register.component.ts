import { Component } from '@angular/core';
import {CreateEntrepreneurDto} from "app/features/common/features/register/types/registration.types";

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

	handleFormSubmit(registerDto: CreateEntrepreneurDto): void {
		console.log(registerDto);
	}
}
