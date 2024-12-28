import {Component, Host} from '@angular/core';
import {MatProgressSpinner} from "@angular/material/progress-spinner";

@Component({
	selector: 'app-loading-cover',
	standalone: true,
	imports: [
		MatProgressSpinner
	],
	templateUrl: './loading-cover.component.html',
	styleUrl: './loading-cover.component.scss',
	host: {'class': 'fixed flex justify-center items-center z-30 w-full min-h-screen'}
})
export class LoadingCoverComponent {

}
