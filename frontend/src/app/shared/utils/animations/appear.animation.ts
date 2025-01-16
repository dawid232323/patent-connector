import {animate, style, transition, trigger} from "@angular/animations";

export const fastAppearAnimation = trigger('fastAppearAnimation', [
	transition(':enter', [
		style({
			width: '100%',
			opacity: .25,
		}),
		animate('100ms ease-out', style({
			width: '100%',
			opacity: 1,
		}))
	]),
	transition(':leave', [
		style({
			width: '100%',
			opacity: 1,
		}),
		animate('80ms ease-out', style({
			width: '100%',
			opacity: .15,
		}))
	])
]);
